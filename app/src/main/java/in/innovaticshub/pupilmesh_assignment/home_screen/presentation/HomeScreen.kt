package `in`.innovaticshub.pupilmesh_assignment.home_screen.presentation

 import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
 import androidx.compose.foundation.layout.Row
 import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
 import androidx.compose.foundation.layout.padding
 import androidx.compose.foundation.shape.RoundedCornerShape
 import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
 import androidx.compose.material3.Icon
 import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
 import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
 import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
 import androidx.compose.ui.graphics.Color
 import androidx.compose.ui.text.font.FontWeight
 import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
 import androidx.navigation.NavHostController
 import `in`.innovaticshub.pupilmesh_assignment.manga_screen.presentation.MangaScreen
 import `in`.innovaticshub.pupilmesh_assignment.face_detection_screen.presentation.FaceDetectionScreen


@Composable
fun HomeScreen(navController1: NavHostController) {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Manga", "Face")
     val selectedColor = Color.White
    val unselectedColor = Color(0xFFD0BCFF)
    val pillBackgroundColor = Color(0xFF2D2D2D)

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
                                .clickable { selectedItem = index }
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = item,
                                tint = if (isSelected) unselectedColor else unselectedColor
                            )
                            Text(
                                text = item,
                                color = if (isSelected) unselectedColor else unselectedColor,
                                fontSize = 12.sp
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(if (isSelected) selectedColor else Color.Transparent)
                                .clickable { selectedItem = index }
                                .padding(horizontal = 20.dp, vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = item,
                                color = if (isSelected) Color.Black else unselectedColor,
                                fontWeight = FontWeight.Bold
                            )
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
                else -> FaceDetectionScreen()
            }
        }
    }
}




