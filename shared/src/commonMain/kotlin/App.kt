import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import features.home.presentation.HomePageScreen
import features.home.presentation.viewModel.BirdsViewModel

@Composable
fun BirdAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = MaterialTheme.colors.copy(primary = Color.Black),
        shapes = MaterialTheme.shapes.copy(
            small = AbsoluteCutCornerShape(0.dp),
            medium = AbsoluteCutCornerShape(0.dp),
            large = AbsoluteCutCornerShape(0.dp)
        )
    ) {
        content()
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App() {
    BirdAppTheme {
        val viewModel = getViewModel(Unit, viewModelFactory { BirdsViewModel() })
        Navigator(HomePageScreen(viewModel)){
            SlideTransition(it)
        }
    }
}


expect fun getPlatformName(): String