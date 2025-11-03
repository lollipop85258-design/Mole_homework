package tw.edu.pu.csim.tcyang.mole

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

class MoleViewModel : ViewModel() {
    var counter by mutableLongStateOf(0)
    var timeRemaining by mutableLongStateOf(60L)

    var offsetX by mutableStateOf(0)
    var offsetY by mutableStateOf(0)
    private var maxOffsetX by mutableStateOf(0)
    private var maxOffsetY by mutableStateOf(0)

    private var gameJob: Job? = null

    init {
        startGameTimer()
    }

    private fun startGameTimer() {
        if (gameJob?.isActive == true) return

        gameJob = viewModelScope.launch {
            while (isActive && timeRemaining > 0L) {
                delay(1000)
                timeRemaining--
                moveMole()
            }
        }
    }

    private fun moveMole() {
        if (timeRemaining > 0L) {
            offsetX = Random.nextInt(maxOffsetX + 1)
            offsetY = Random.nextInt(maxOffsetY + 1)
        }
    }

    fun getArea(intSize: IntSize, moleSizePx: Int) {
        maxOffsetX = intSize.width - moleSizePx
        maxOffsetY = intSize.height - moleSizePx
        if (maxOffsetX < 0) maxOffsetX = 0
        if (maxOffsetY < 0) maxOffsetY = 0
    }

    fun incrementCounter() {
        if (timeRemaining > 0L) {
            counter++
        }
    }

    override fun onCleared() {
        super.onCleared()
        gameJob?.cancel()
    }
}