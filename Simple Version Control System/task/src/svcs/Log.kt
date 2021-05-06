package svcs

import java.io.File

const val LOG_FILEPATH = "vcs/log.txt"

class LogEntry(val commit: String, private val author: String, private val message: String) {
    fun print(): String = arrayOf(
            "commit $commit",
            "Author: $author",
            message
    ).joinToString("\n")
}

fun parse(lines: List<String>): LogEntry {
    val commit = lines[0].split(" ")[1]
    val author = lines[1].split(" ")[1]
    val message = lines[2]

    return LogEntry(commit, author, message)
}

object Log {
    private val log = File(LOG_FILEPATH)

    fun print(): String {
        if (!log.exists()) return "No commits yet."
        return log.readText()
    }

    fun latestHash(): String? {
        if (!log.exists()) return null;

        val lines = log.readLines().takeUnless { it.isEmpty() } ?: return null

        return parse(lines).commit
    }

    fun save(commit: String, author: String, message: String) {
        val entry = LogEntry(commit, author, message)
        var text = entry.print()
        if (log.exists()) {
            text += "\n\n"
            text += log.readText()
        }
        log.createParent().writeText(text)
    }

    fun has(commit: String): Boolean {
        if (!log.exists()) return false;

        val entry = log.readLines()
                .filter { it.isNotEmpty() }
                .chunked(3)
                .map { parse(it) }
                .findLast { it.commit == commit }

        return entry != null
    }
}