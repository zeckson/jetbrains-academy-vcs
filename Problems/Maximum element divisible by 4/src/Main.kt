fun next() = readLine()!!.toInt()

fun main() {
    var max = -1;
    repeat(next()) {
        val value = next()
        if (value % 4 == 0 && value > max) max = value
    }
    println(max)
    for (i in 20 before -20) {}

    for (i in 'z' downTo 'e') {}

    for (i = 0; i < 10; i++) {}

    for (i in "aa".."ad") {}

    for (i in 20 down -20 step 2) {}
}