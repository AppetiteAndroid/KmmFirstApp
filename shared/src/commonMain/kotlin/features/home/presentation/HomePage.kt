package features.home.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import features.details.presentation.BirdsDetailsScreen
import features.home.model.BirdImage
import features.home.presentation.viewModel.BirdsViewModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource


data class HomePageScreen(
    val viewModel: BirdsViewModel
) : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        println("HomePAge")
        BirdsPage()
    }

    @Composable
    fun BirdsPage() {
        val uiState by viewModel.uiState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Row(
                Modifier.fillMaxWidth().padding(5.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                for (category in uiState.categories) {
                    Button(
                        onClick = {
                            viewModel.selectCategory(category)
                        },
                        modifier = Modifier.aspectRatio(1.0f).fillMaxSize().weight(1.0f),
                        elevation = ButtonDefaults.elevation(
                            defaultElevation = 0.dp,
                            focusedElevation = 0.dp
                        )
                    )
                    {
                        Text(category)
                    }
                }
            }
            AnimatedVisibility(uiState.selectedImages.isNotEmpty()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier.fillMaxSize().padding(horizontal = 5.dp),
                    content = {
                        items(uiState.selectedImages) {
                            BirdImageCell(it) {
                                navigator.push(BirdsDetailsScreen(it))
                            }
                        }
                    }
                )
            }
        }
    }

    @Composable
    fun BirdImageCell(image: BirdImage, onClick: () -> Unit) {
        KamelImage(
            asyncPainterResource("https://sebastianaigner.github.io/demo-image-api/${image.path}"),
            "${image.category} by ${image.author}",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().aspectRatio(1.0f).clickable {
                onClick()
            },
            animationSpec = tween(200),
            onLoading = {
                CircularProgressIndicator()
            }
        )
    }

}