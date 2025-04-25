package `in`.innovaticshub.pupilmesh_assignment.face_detection_screen.presentation

 import android.graphics.RectF
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
 import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.mediapipe.tasks.vision.core.RunningMode

import android.util.Log

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
 import `in`.innovaticshub.pupilmesh_assignment.face_detection_screen.data.FaceDetectorHelper
 import kotlin.math.max
import kotlin.math.min

@Composable
fun FaceDetectionScreen() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var faceBox by remember { mutableStateOf<RectF?>(null) }
    var isFaceInPosition by remember { mutableStateOf(false) }

    val referenceArea = remember { RectF(0.3f, 0.3f, 0.7f, 0.7f) }

    val faceDetector = remember {
        FaceDetectorHelper(
            threshold = 0.7f,
            currentDelegate = FaceDetectorHelper.Companion.DELEGATE_CPU,
            runningMode = RunningMode.LIVE_STREAM,
            context = context,
            faceDetectorListener = object : FaceDetectorHelper.DetectorListener {
                override fun onError(error: String, errorCode: Int) {
                    Log.e("FaceDetection", error)
                }

                override fun onResults(resultBundle: FaceDetectorHelper.ResultBundle) {
                    resultBundle.results.firstOrNull()?.detections()
                        ?.filter {
                            val width = it.boundingBox().width().toFloat()
                            val height = it.boundingBox().height().toFloat()
                            val aspectRatio = width / height
                            aspectRatio in 0.7f..1.5f &&
                                    it.categories()[0].score() >= 0.7f
                        }
                        ?.maxByOrNull { it.boundingBox().width() * it.boundingBox().height() }
                        ?.let { detection ->
                            val faceRect = RectF(
                                1f - (detection.boundingBox().right.toFloat() / resultBundle.inputImageWidth),
                                detection.boundingBox().top.toFloat() / resultBundle.inputImageHeight,
                                1f - (detection.boundingBox().left.toFloat() / resultBundle.inputImageWidth),
                                detection.boundingBox().bottom.toFloat() / resultBundle.inputImageHeight
                            )

                            faceBox = faceRect
                            isFaceInPosition = checkFacePosition(faceRect, referenceArea)
                        } ?: run {
                        faceBox = null
                        isFaceInPosition = false
                    }
                }
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                PreviewView(ctx).apply {
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                    scaleType = PreviewView.ScaleType.FILL_CENTER
                }
            },
            modifier = Modifier.fillMaxSize()
        ) { previewView ->
            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
            cameraProviderFuture.addListener({
                try {
                    val cameraProvider = cameraProviderFuture.get()
                    val preview = Preview.Builder()
                        .setTargetRotation(previewView.display.rotation)
                        .build()
                        .apply { setSurfaceProvider(previewView.surfaceProvider) }

                    val imageAnalysis = ImageAnalysis.Builder()
                        .setTargetRotation(previewView.display.rotation)
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                        .build()
                        .apply {
                            setAnalyzer(ContextCompat.getMainExecutor(context)) { image ->
                                faceDetector.detectLivestreamFrame(image)
                            }
                        }

                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        CameraSelector.DEFAULT_FRONT_CAMERA,
                        preview,
                        imageAnalysis
                    )
                } catch (e: Exception) {
                    Log.e("FaceDetection", "Camera setup failed: ${e.message}")
                }
            }, ContextCompat.getMainExecutor(context))
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(
                color = if (isFaceInPosition) Color.Green else Color.Red,
                topLeft = Offset(size.width * referenceArea.left, size.height * referenceArea.top),
                size = Size(
                    size.width * (referenceArea.right - referenceArea.left),
                    size.height * (referenceArea.bottom - referenceArea.top)
                ),
                style = Stroke(width = 4.dp.toPx())
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .background(if (isFaceInPosition) Color.Green else Color.Red)
                .padding(8.dp)
        ) {
            Text(
                text = if (isFaceInPosition) "✓ FACE DETECTED" else "⇲ MOVE FACE INTO FRAME",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

private fun checkFacePosition(faceRect: RectF, referenceArea: RectF): Boolean {
    val overlapLeft = max(faceRect.left, referenceArea.left)
    val overlapTop = max(faceRect.top, referenceArea.top)
    val overlapRight = min(faceRect.right, referenceArea.right)
    val overlapBottom = min(faceRect.bottom, referenceArea.bottom)

    if (overlapRight < overlapLeft || overlapBottom < overlapTop) return false

    val overlapArea = (overlapRight - overlapLeft) * (overlapBottom - overlapTop)
    val faceArea = (faceRect.right - faceRect.left) * (faceRect.bottom - faceRect.top)

    return (overlapArea / faceArea) >= 0.7f
}