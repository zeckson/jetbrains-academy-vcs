package svcs

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

fun File.createParent(): File {
    if (!this.exists()) {
        this.parentFile.mkdirs()
    }
    return this;
}

fun sha(bytes: ByteArray): String {
    val digest = MessageDigest.getInstance("SHA-1")
    digest.reset()
    digest.update(bytes)
    return String.format("%040x", BigInteger(1, digest.digest()))
}
