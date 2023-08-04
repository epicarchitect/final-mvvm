package epicarchitect.finalmvvm.mvvmbasis

class ComposeCommand(
    private val commands: List<Command<*>>
) : Command<Unit> {
    override fun invoke() {
        commands.forEach(Command<*>::invoke)
    }
}