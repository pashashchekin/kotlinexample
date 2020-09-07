package ru.skillbranch.kotlinexample.extensions

fun <T> Iterable<T>.dropLastUntil(predicate: (T) -> Boolean): List<T> {
    for (item in this) {
        if (predicate(item)) {
            val index = this.indexOf(item)
            return this.chunked(index).first()
        }
    }

    return emptyList()
}

fun String.normalizePhone() = replace("[^+\\d]".toRegex(), "")

fun String.isValidPhone(): Boolean = normalizePhone().matches("[+]\\d{11}".toRegex())



