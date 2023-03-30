package com.erzhan.test.core

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class Utils {
    companion object {
        fun normalizePhoneNumber(number: String): String {
            val digitsOnly = number.trim()
                .replace("\\s+".toRegex(), "")
                .replace("+", "")
                .replace("[^\\d]".toRegex(), "")
                .replace("^0+".toRegex(), "254")
                .removePrefix("0")
                .removePrefix("254")

            if (digitsOnly.length < 9) {
                throw IllegalArgumentException("The phone number is too short.")
            }

            val normalized = "254${digitsOnly}"

            return normalized.replace(Regex("[\\s\\+\\-]"), "").chunked(3).joinToString(" ")
        }
        fun isValidPhoneNumber(phone: String): Boolean {
            val digitsOnly = phone
                .replace(Regex("\\D"), "")
                .replace(Regex("[\\s\\+\\-]"), "")
                .removePrefix("0")

            if (digitsOnly.length < 9) {
                return false
            }

            return digitsOnly.startsWith("254") || digitsOnly.startsWith("7")
        }

        fun hideKeyboard(context: Context, view: View) {
            val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}