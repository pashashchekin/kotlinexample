package ru.skillbranch.kotlinexample.utils

fun String.normalizePhone() = replace("[^+\\d]".toRegex(), "")

fun String.isValidPhone():Boolean = normalizePhone().matches("[+]\\d{11}".toRegex())
