package svcs

const val HELP = "--help"

val helpInfoMap = mapOf(
        "config" to "Get and set a username",
        "add" to "Add a file to the index",
        "log" to "Show commit logs",
        "commit" to "Save changes",
        "checkout" to "Restore a file"
)


fun configAction(name: String?): String {
    return when {
        !(name.isNullOrBlank()) -> {
            Config.save(name)
            "The username is $name"
        }
        Config.username() != null -> "The username is ${Config.username()}"
        else -> "Please, tell me who you are."
    }
}

fun addAction(filename: String?): String {
    if (filename.isNullOrBlank()) {
        return if (!Index.exists()) {
            "Add a file to the index."
        } else {
            "Tracked files:\n${Index.list().joinToString("\n")}"
        }
    }

    return if (Index.add(filename)) {
        "The File '$filename' is tracked."
    } else {
        "Can't found '$filename'."
    }
}

fun commitAction(message: String?): String {
    if (message.isNullOrEmpty()) return "Message was not passed."
    val latestHash = Log.latestHash()
    val currentHash = Index.hash()
    if (latestHash == currentHash) {
        return "Nothing to commit"
    }
    val commit = Index.commit() ?: return "Failed to commit"
    val author = Config.username() ?: return "Author is not set"
    Log.save(commit, author, message)
    return "Changes are committed."
}

fun checkoutAction(commit: String?): String {
    if (commit.isNullOrBlank()) return "Commit id was not passed."
    if (!Log.has(commit)) return "Commit does not exist."

    Index.checkout(commit)
    return "Switched to commit $commit."
}

fun logAction(_it: String?): String {
    return Log.print()
}

val actionMap = mapOf(
        "config" to ::configAction,
        "add" to ::addAction,
        "log" to ::logAction,
        "commit" to ::commitAction,
        "checkout" to ::checkoutAction
)


fun printHelp() = """These are SVCS commands:
    |${helpInfoMap.map { "${it.key.padEnd(10, ' ')} ${it.value}." }.joinToString("\n")}
""".trimMargin()


fun main(args: Array<String>) {
    val command = when {
        args.isEmpty() -> HELP
        else -> args.first()
    }
    val action = actionMap[command]
    println(when {
        command == HELP -> printHelp()
        action == null -> helpInfoMap.getOrDefault(command, "'$command' is not a SVCS command.")
        else -> action(args.elementAtOrNull(1))
    })
}

