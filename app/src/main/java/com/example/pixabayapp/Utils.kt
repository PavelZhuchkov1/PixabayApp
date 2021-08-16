package com.example.pixabayapp

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class Utils {

    companion object {

        fun hideKeyboard(activity: Activity) {
            val imm: InputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            val view: View = activity.currentFocus ?: View(activity)
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun hideKeyboardFrom(context: Context?, view: View?) {
            val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view?.windowToken, 0)
        }
    }
}