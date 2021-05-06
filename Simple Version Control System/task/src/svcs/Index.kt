package svcs

import java.io.File
import java.io.FileNotFoundException

private const val INDEX_FILEPATH = "vcs/index.txt"
private const val WORKDIR = "./"
private const val COMMITS_FILEPATH = "vcs/commits"


object Index {
    private val index = File(INDEX_FILEPATH)
    private val workdir = File(WORKDIR)

    fun exists() = index.exists()

    fun add(fileName: String): Boolean {
        val file = File(fileName)
        if (!file.exists()) {
            return false
        }
        index.createParent().appendText("$fileName\n")
        return true
    }

    fun list(): List<String> = index.readLines()

    private fun files(): List<File> = index.readLines().map { File(it) }

    fun commited(commit: String): List<File> {
        val commitDir = commitDir(commit)
        val files = commitDir.listFiles() ?: throw FileNotFoundException("Commit $commit not found")
        return files.toList()
    }

    fun commit(): String? {
        val hash = hash() ?: return null

        val commitDir = commitDir(hash)
        files().forEach { it.copyTo(File(commitDir, it.name), true) }
        return hash
    }

    private fun commitDir(commit: String) = File(COMMITS_FILEPATH + File.separator + commit)

    fun hash(): String? {
        if (!exists()) return null
        val bytes = files().map { it.readBytes() }.reduce { acc, bytes -> acc + bytes }
        return sha(bytes)
    }

    fun checkout(commit: String) {
        commited(commit).forEach { it.copyTo(File(workdir, it.name), true) }
    }
}
