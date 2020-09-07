package ru.skillbranch.kotlinexample.extensions

fun String.normalizePhone() = replace("[^+\\d]".toRegex(), "")

fun String.isValidPhone():Boolean = normalizePhone().matches("[+]\\d{11}".toRegex())
