package epicarchitect.finalmvvm.mvvmbasis

import java.util.concurrent.Executors
import java.util.concurrent.Future
import kotlin.time.Duration

class RepeatCommand<T>(
    private val command: Command<T>,
    private val onActiveChange: Command<*>,
    private val delay: Duration
) {
    private val executor = Executors.newSingleThreadExecutor()
    private var repeating: Future<*>? = null
        set(value) {
            field = value
            onActiveChange()
        }

    val isActive: Boolean
        get() = repeating != null

    fun start() {
        repeating = executor.submit {
            try {
                while (true) {
                    command.invoke()
                    Thread.sleep(delay.inWholeMilliseconds)
                }
            } catch (_: Throwable) {
                repeating = null
            }
        }
    }

    fun stop() {
        repeating?.cancel(true)
        repeating = null
    }

    fun toggle() {
        if (isActive) {
            stop()
        } else {
            start()
        }
    }
}