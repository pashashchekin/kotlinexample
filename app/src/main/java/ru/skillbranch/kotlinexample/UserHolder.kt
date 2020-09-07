package ru.skillbranch.kotlinexample

import androidx.annotation.VisibleForTesting
import ru.skillbranch.kotlinexample.extensions.isValidPhone
import ru.skillbranch.kotlinexample.extensions.normalizePhone
import kotlin.math.log

object UserHolder {
    private val map = mutableMapOf<String, User>()

    fun registerUser(fullName: String, email: String, password: String): User {
        if (map.containsKey(email.toLowerCase()))
            throw IllegalArgumentException("A user with this email already exists")

        return User.makeUser(fullName, email = email, password = password)
            .also { user ->
                map[user.login] = user
            }
    }

    fun registerUserByPhone(fullName: String, rawPhone: String): User {
        if (rawPhone.isValidPhone()) {
            val user = User.makeUser(fullName, phone = rawPhone)
            if (!map.containsKey(rawPhone)) {
                map[user.login] = user
                return user
            } else
                throw IllegalArgumentException("A user with this phone already exists")
        } else throw IllegalArgumentException("Enter a valid phone number starting with a + and containing 11 digits")
    }

    fun loginUser(login: String, password: String): String? {
        return map[validateLogin(login)]?.let {
            if (it.checkPassword(password)) it.userInfo
            else null
        }
    }

    fun requestAccessCode(login: String) {
        map[login.normalizePhone()]?.requestAccessCode()
    }


    private fun validateLogin(login: String): String {
        return if (login.isValidPhone())
            login.normalizePhone()
        else
            login.trim()
    }


    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun clearHolder() {
        map.clear()
    }
}