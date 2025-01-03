package com.itidol.appone.views.utils

import com.itidol.appone.views.constants.Constants
import java.util.regex.Pattern

class Java_Regex {
    companion object{
        fun emailValidator(email: String): Boolean {
            val pattern = Pattern.compile(Constants.EMAIL_REGEX)
            val matcher = pattern.matcher(email)
            return matcher.matches()
        }
        fun passwordValidator(password: String): Boolean {
            val pattern = Pattern.compile(Constants.PASSWORD_REGEX)
            val matcher = pattern.matcher(password)
            return matcher.matches()
        }
    }
}