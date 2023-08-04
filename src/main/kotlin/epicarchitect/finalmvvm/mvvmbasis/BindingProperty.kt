package epicarchitect.finalmvvm.mvvmbasis

class BindingProperty<T>(initial: T) {
    private val bindings = mutableListOf<Binding<T>>()
    var value = initial
        set(value) {
            bindings.forEach { it.apply(value) }
            field = value
        }

    fun bind(binding: Binding<T>) {
        bindings.add(binding)
        binding.apply(value)
    }
}

fun interface Binding<T> {
    fun apply(value: T)
}