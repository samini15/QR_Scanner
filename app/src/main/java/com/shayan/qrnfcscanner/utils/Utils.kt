package com.shayan.qrnfcscanner.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class Utils {

    companion object {
        /**
         * Hides the soft keyboard
         *
         * @param context Context used to get the [Context.INPUT_METHOD_SERVICE] service
         * @param view    View in the window where the keyboard is opened
         */
        fun hideSoftKeyboard(context: Context?, view: View?) {
            if (context == null || view == null) {
                return
            }
            val imm = context
                    .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        /**
         * Hides the soft keyboard
         *
         * @param view View in the window where the keyboard is opened
         */
        fun hideSoftKeyboard(view: View?) {
            if (view == null) {
                return
            }
            hideSoftKeyboard(view.context, view)
        }



        fun <T> safeCast(`object`: Any?, toClass: Class<T>?): T? {
            return if (toClass == null) null else try {
                toClass.cast(`object`)
            } catch (e: ClassCastException) {
                null
            }
        }
    }
}