val lambda: (Long, Long) -> Long = { left, right ->
    var mult = left;
    for (i in left + 1..right) {
        mult *= i;
    }
    mult;
}