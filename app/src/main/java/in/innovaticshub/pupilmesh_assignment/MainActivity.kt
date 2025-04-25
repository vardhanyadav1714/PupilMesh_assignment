package `in`.innovaticshub.pupilmesh_assignment

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
 import `in`.innovaticshub.pupilmesh_assignment.presentation.PupilMeshAppNavigation
import `in`.innovaticshub.pupilmesh_assignment.ui.theme.PupilMesh_assignmentTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
             }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }

        enableEdgeToEdge()
        setContent {
            PupilMesh_assignmentTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                           Greetings(innerPadding)
                    }
                }
            }
        }

}

@Composable
fun Greetings(innerPaddingValues: PaddingValues){
    PupilMeshAppNavigation()
}