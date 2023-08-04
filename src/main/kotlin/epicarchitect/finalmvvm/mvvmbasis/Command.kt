package epicarchitect.finalmvvm.mvvmbasis

interface Command<T> : () -> T

class SimpleCommand<T>(
    private val consumer: BindingProperty<T>,
    private val action: () -> T
) : Command<T> {
    override fun invoke() = action().also {
        consumer.value = it
    }
}