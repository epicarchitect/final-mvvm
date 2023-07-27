package epicarchitect.finalmvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class TimerViewModel : ViewModel() {
    private var timerJob: Job? = null

    private val _isRunning = MutableStateFlow(false)
    val isRunning = _isRunning.asStateFlow()

    private val _timerValue = MutableStateFlow(Duration.ZERO)
    val timerValue = _timerValue.asStateFlow()

    fun play() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            try {
                _isRunning.value = true
                while (true) {
                    ensureActive()
                    delay(1000)
                    _timerValue.value += 1.seconds
                }
            } catch (th: Throwable) {
                _isRunning.value = false
            }
        }
    }

    fun pause() {
        timerJob?.cancel()
    }
}