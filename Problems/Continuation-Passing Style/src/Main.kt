fun square(value: Int, context: Any, continuation: (Int, Any) -> Unit) {
    val sqr = value*value
    continuation(sqr, context)
}