package ru.skillbranch.kotlinexample

import androidx.annotation.VisibleForTesting
import ru.skillbranch.kotlinexample.utils.isValidPhone
import ru.skillbranch.kotlinexample.utils.normalizePhone

object UserHolder {
    val map = mutableMapOf<String, User>()

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
        return map[login.normalizePhone().trim()]?.let {
            if (it.checkPassword(password)) it.userInfo
            else null
        }
    }

    fun requestAccessCode(login: String) {
        map[login.normalizePhone()]?.requestAccessCode()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun clearHolder() {
        map.clear()
    }
}