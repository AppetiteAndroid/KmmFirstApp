package features.details.presentation

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import features.home.model.BirdImage
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

data class BirdsDetailsScreen(
    val birdImage: BirdImage
) : Screen {
    @Composable
    override fun Content() {
        Column {
            KamelImage(
                asyncPainterResource("https://sebastianaigner.github.io/demo-image-api/${birdImage.path}"),
                "${birdImage.category} by ${birdImage.author}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().aspectRatio(1.0f),
                animationSpec = tween(200),
                onLoading = {
                    CircularProgressIndicator()
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(birdImage.category)
            Spacer(modifier = Modifier.height(5.dp))
            Text(birdImage.author)
        }
    }

}
