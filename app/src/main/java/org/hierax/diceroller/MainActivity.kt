package org.hierax.diceroller

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private val sides = 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollButton: Button = findViewById(R.id.rollButton)

        initLuckyNumberInput(rollButton)
        initRollButton(rollButton)
        initDice()
    }

    private fun initLuckyNumberInput(rollButton: Button) {
        val luckyNumberInput: TextInputEditText = findViewById(R.id.luckyNumberInput)

        luckyNumberInput.addTextChangedListener(LuckyNumberWatcher(luckyNumberInput, sides,
            validValueCallback = {
                rollButton.isEnabled = true
            },
            invalidValueCallback = {
                rollButton.isEnabled = false
                setText(R.id.luckyTextView, "")
            }
        ))
    }

    private fun initRollButton(rollButton: Button) {
        rollButton.text = getString(R.string.roll_button_text)
        rollButton.isEnabled = false
        rollButton.setOnClickListener(rollDice())
    }

    private fun initDice() {
        setImageForResult(R.id.result1ImageView, sides)
        setImageForResult(R.id.result2ImageView, sides)
    }

    private fun rollDice(): (v: View) -> Unit = {
        val luckyNumberInput: TextInputEditText = findViewById(R.id.luckyNumberInput)
        val luckyNumber = luckyNumberInput.text.toString().toInt()

        val result1 = Dice(sides).roll()
        setImageForResult(R.id.result1ImageView, result1)
        val result2 = Dice(sides).roll()
        setImageForResult(R.id.result2ImageView, result2)

        val total = result1 + result2
        if (total == luckyNumber) {
            setText(R.id.luckyTextView, getString(R.string.lucky_message, total))
        } else {
            setText(R.id.luckyTextView, getString(R.string.unlucky_message))
        }
    }

    private fun setText(viewId: Int, text: String) {
        val resultTextView: TextView = findViewById(viewId)
        resultTextView.text = text
    }

    private fun setImageForResult(imageViewId: Int, result: Int) {
        when (result) {
            1 -> setImage(imageViewId, R.drawable.dice_1)
            2 -> setImage(imageViewId, R.drawable.dice_2)
            3 -> setImage(imageViewId, R.drawable.dice_3)
            4 -> setImage(imageViewId, R.drawable.dice_4)
            5 -> setImage(imageViewId, R.drawable.dice_5)
            6 -> setImage(imageViewId, R.drawable.dice_6)
        }
    }

    private fun setImage(viewId: Int, imageResourceId: Int) {
        val resultImageView: ImageView = findViewById(viewId)
        resultImageView.setImageResource(imageResourceId)
    }

}
