package com.itidol.appone.constants

class Constants{
    companion object{
        val USER_NAME : String = "name"
        val USER_EMAIL : String = "email"
        val USER_PASSWORD : String = "password"
        val EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        val PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$"
    }
}