package tw.edu.pu.csim.tcyang.mole

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import tw.edu.pu.csim.tcyang.mole.ui.theme.MoleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoleTheme {
                MoleScreen()
            }
        }
    }
}

@Composable
fun MoleScreen(moleViewModel: MoleViewModel = viewModel()) {
    val counter = moleViewModel.counter
    val timeRemaining = moleViewModel.timeRemaining

    val isGameOver = timeRemaining <= 0L

    val density = LocalDensity.current
    val moleSizeDp = 150.dp
    val moleSizePx = with(density) { moleSizeDp.roundToPx() }

    Box (
        modifier = Modifier.fillMaxSize()
            .onSizeChanged { intSize ->
                moleViewModel.getArea(intSize, moleSizePx)
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 16.dp)
        ) {
            Text(
                text = "打地鼠游戏 (郭崇承)",
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = "分數: $counter \n時間: $timeRemaining 秒",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )

            if (isGameOver) {
                Text(
                    text = "游戏结束！\n最终分数: $counter",
                    fontSize = 40.sp,
                    color = androidx.compose.ui.graphics.Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 100.dp)
                )
            }
        }
    }

    Image(
        painter = painterResource(id = R.drawable.mole),
        contentDescription = "地鼠",
        modifier = Modifier
            .offset { IntOffset(moleViewModel.offsetX, moleViewModel.offsetY) }
            .size(moleSizeDp)
            .clickable(enabled = !isGameOver) {
                if (!isGameOver) {
                    moleViewModel.incrementCounter()
                }
            }
    )
}