package svcs

import java.io.File


object Config {
    private const val CONFIG_FILEPATH = "vcs/config.txt"
    private val config = File(CONFIG_FILEPATH)

    fun username(): String? {
        return if (config.exists()) {
            config.readText()
        } else {
            null
        }
    }

    fun save(username: String) {
        config.createParent().writeText(username)
    }
}
