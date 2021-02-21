package org.hierax.diceroller

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText

class LuckyNumberWatcher (
    private val input: TextInputEditText,
    private val sides: Int,
    private val validValueCallback: () -> Unit,
    private val invalidValueCallback: () -> Unit)
    : TextWatcher {

    override fun afterTextChanged(s: Editable?) {
        val luckyNumber = input.text.toString().toIntOrNull()
        if (luckyNumber == null) {
            invalidValueCallback.invoke()
            input.error = "Enter a number"
            return
        }

        if (luckyNumber < 2 || luckyNumber > (sides * 2)) {
            invalidValueCallback.invoke()
            input.error = "Outside valid range"
        } else {
            validValueCallback.invoke()
        }
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // do nothing
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // do nothing
    }
}