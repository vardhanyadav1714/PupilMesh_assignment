import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import `in`.innovaticshub.pupilmesh_assignment.manga_screen.presentation.MangaViewModel

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class)
@Composable
fun MangaDetailScreen(mangaId: String?) {
    val viewModel: MangaViewModel = hiltViewModel()
    val isOnline by viewModel.isOnline.collectAsState()
    val mangaFlow = remember { viewModel.mangaPagingFlow }

    val manga = mangaFlow.collectAsLazyPagingItems()
        .itemSnapshotList
        .firstOrNull { it?.id == mangaId }

    if (manga != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 40.dp, start = 16.dp,end=16.dp,bottom=16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AsyncImage(
                    model = manga.thumb,
                    contentDescription = manga.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(140.dp)
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = manga.title,
                        style = MaterialTheme.typography.titleSmall,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = " ${manga.sub_title}",
                        style = MaterialTheme.typography.bodyMedium
                    )


                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            Spacer(modifier = Modifier.height(16.dp))


            Text(
                text = manga.summary,
                style = MaterialTheme.typography.bodyMedium
            )

            if (!isOnline) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Showing cached data - some information may be outdated",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (!isOnline) {
                    Text("No cached data available for this manga")
                    Text("Please connect to the internet to view details")
                } else {
                    CircularProgressIndicator()
                    Text("Loading manga details...")
                }
            }
        }
    }
}