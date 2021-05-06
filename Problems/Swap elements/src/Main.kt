fun main() {
    val numbers = readLine()!!.split(' ').map { it.toInt() }.toIntArray()
    // Do not touch lines above
    // Write only exchange actions here.
    val first = numbers[0]
    val lastIndex = numbers.size - 1
    numbers[0] = numbers[lastIndex]
    numbers[lastIndex] = first

    // Do not touch lines below
    println(numbers.joinToString(separator = " "))
}