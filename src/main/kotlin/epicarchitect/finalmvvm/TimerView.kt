package epicarchitect.finalmvvm

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

context(
    Context,
    LifecycleOwner,
    ViewModelStoreOwner
)
fun timerView() = LinearLayout(this@Context).apply {
    val lifecycleScope = this@LifecycleOwner.lifecycleScope
    val viewModel = ViewModelProvider(this@ViewModelStoreOwner)[TimerViewModel::class.java]
    val durationFormatter = DurationFormatter()
    gravity = Gravity.CENTER
    orientation = LinearLayout.VERTICAL
    addView(
        TextView(context).apply {
            viewModel.isRunning.onEach {
                isEnabled = it
            }.launchIn(lifecycleScope)

            viewModel.timerValue.onEach {
                text = durationFormatter.format(it)
            }.launchIn(lifecycleScope)
        },
        ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    )
    addView(
        Button(context).apply {
            viewModel.isRunning.onEach {
                text = if (it) {
                    "Pause"
                } else {
                    "Play"
                }
            }.launchIn(lifecycleScope)

            setOnClickListener {
                if (viewModel.isRunning.value) {
                    viewModel.pause()
                } else {
                    viewModel.play()
                }
            }
        },
        ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    )
}

context(
    Context,
    LifecycleOwner,
    ViewModelStoreOwner
)
fun niceTimerView() = LinearLayout(this@Context).apply {
    val viewModel = ViewModelProvider(this@ViewModelStoreOwner)[NiceTimerViewModel::class.java]
    val durationFormatter = DurationFormatter()
    gravity = Gravity.CENTER
    orientation = LinearLayout.VERTICAL
    addView(
        TextView(context).apply {
            viewModel.timerValue.bind {
                text = durationFormatter.format(it)
            }
            viewModel.isRunning.bind {
                isEnabled = it
            }
        },
        ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    )
    addView(
        Button(context).apply {
            viewModel.isRunning.bind {
                text = if (it) {
                    "Pause"
                } else {
                    "Play"
                }
            }

            setOnClickListener {
                viewModel.repeat.toggle()
            }
        },
        ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    )
}