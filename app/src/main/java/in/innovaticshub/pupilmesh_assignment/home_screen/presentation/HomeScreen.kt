package `in`.innovaticshub.pupilmesh_assignment.home_screen.presentation

 import android.R
 import androidx.compose.foundation.BorderStroke
 import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
 import androidx.compose.foundation.layout.Row
 import androidx.compose.foundation.layout.Spacer
 import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
 import androidx.compose.foundation.layout.height
 import androidx.compose.foundation.layout.padding
 import androidx.compose.foundation.layout.size
 import androidx.compose.foundation.shape.RoundedCornerShape
 import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
 import androidx.compose.material3.AlertDialog
 import androidx.compose.material3.Button
 import androidx.compose.material3.ButtonDefaults
 import androidx.compose.material3.Icon
 import androidx.compose.material3.MaterialTheme
 import androidx.compose.material3.OutlinedButton
 import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
 import androidx.compose.material3.TextButton
 import androidx.compose.runtime.Composable
 import androidx.compose.runtime.LaunchedEffect
 import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
 import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
 import androidx.compose.ui.graphics.Color
 import androidx.compose.ui.res.painterResource
 import androidx.compose.ui.text.font.FontWeight
 import androidx.compose.ui.text.style.TextAlign
 import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
 import androidx.hilt.navigation.compose.hiltViewModel
 import androidx.navigation.NavHostController
 import com.google.accompanist.permissions.ExperimentalPermissionsApi
 import com.google.accompanist.permissions.isGranted
 import com.google.accompanist.permissions.rememberPermissionState
 import com.google.accompanist.permissions.shouldShowRationale
 import `in`.innovaticshub.pupilmesh_assignment.manga_screen.presentation.MangaScreen
 import `in`.innovaticshub.pupilmesh_assignment.face_detection_screen.presentation.FaceDetectionScreen


@OptIn(ExperimentalPermissionsApi::class)
@Composable
 fun HomeScreen(
    navController1: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
     val cameraPermissionState = rememberPermissionState(permission = android.Manifest.permission.CAMERA)

    val selectedItem by viewModel.selectedItem
    val permissionDenied by viewModel.permissionDenied
    val showPermissionDialog by viewModel.showPermissionDialog

    val items = listOf("Manga", "Face")
    val selectedColor = Color.White
    val unselectedColor = Color(0xFFD0BCFF)
    val pillBackgroundColor = Color(0xFF2D2D2D)

    LaunchedEffect(selectedItem) {
        if (selectedItem == 1) {
            val status = cameraPermissionState.status
            when {
                status.isGranted -> viewModel.onCameraPermissionResult(true, false)
                status.shouldShowRationale -> viewModel.onCameraPermissionResult(false, true)
                else -> cameraPermissionState.launchPermissionRequest()
            }
        }
    }

    LaunchedEffect(cameraPermissionState.status) {
        if (selectedItem == 1) {
            viewModel.onCameraPermissionResult(
                isGranted = cameraPermissionState.status.isGranted,
                shouldShowRationale = cameraPermissionState.status.shouldShowRationale
            )
        }
    }

    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissDialog() },
            title = { Text("Camera Permission Required") },
            text = { Text("Face detection needs camera access to work") },
            confirmButton = {
                Button(onClick = {
                    cameraPermissionState.launchPermissionRequest()
                    viewModel.requestPermissionAgain()
                }) {
                    Text("Allow")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.dismissDialog() }) {
                    Text("Deny")
                }
            }
        )
    }

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEachIndexed { index, item ->
                    val isSelected = selectedItem == index
                    if (item == "Manga") {
                        Column(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(if (isSelected) pillBackgroundColor else Color.Transparent)
                                .clickable { viewModel.onTabSelected(index) }
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = item,
                                tint = unselectedColor
                            )
                            Text(
                                text = item,
                                color = unselectedColor,
                                fontSize = 12.sp
                            )
                        }
                    } else {
                        OutlinedButton(
                            onClick = { viewModel.onTabSelected(index) },
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (isSelected) selectedColor else Color.Transparent,
                                contentColor = if (isSelected) Color.Black else unselectedColor
                            ),
                            border = BorderStroke(1.dp, if (isSelected) selectedColor else unselectedColor),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(text = item, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when (selectedItem) {
                0 -> MangaScreen(navController1)
                1 -> {
                    if (cameraPermissionState.status.isGranted) {
                        FaceDetectionScreen()
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_menu_camera),
                                contentDescription = "Camera",
                                tint = Color.Gray,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Camera permission required",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Please grant camera access in the app Settings to use face detection",
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center
                            )

                        }
                    }

                }
            }
        }
    }
}





