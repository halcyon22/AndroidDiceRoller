package org.hierax.diceroller

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import org.hierax.diceroller.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val sides = 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLuckyNumberInput(binding.rollButton)
        initRollButton(binding.rollButton)
        initDice()
    }

    private fun initLuckyNumberInput(rollButton: Button) {
        binding.luckyNumberInput.addTextChangedListener(LuckyNumberWatcher(
            binding.luckyNumberInput, sides,
            validValueCallback = {
                rollButton.isEnabled = true
            },
            invalidValueCallback = {
                rollButton.isEnabled = false
                binding.luckyTextView.text = ""
            }
        ))
    }

    private fun initRollButton(rollButton: Button) {
        rollButton.text = getString(R.string.roll_button_text)
        rollButton.isEnabled = false
        rollButton.setOnClickListener(rollDice())
    }

    private fun initDice() {
        setImageForResult(binding.result1ImageView, sides)
        setImageForResult(binding.result2ImageView, sides)
    }

    private fun rollDice(): (v: View) -> Unit = {
        val luckyNumber = binding.luckyNumberInput.text.toString().toInt()

        val result1 = Dice(sides).roll()
        setImageForResult(binding.result1ImageView, result1)
        val result2 = Dice(sides).roll()
        setImageForResult(binding.result2ImageView, result2)

        val total = result1 + result2
        if (total == luckyNumber) {
            binding.luckyTextView.text = getString(R.string.lucky_message, total)
        } else {
            binding.luckyTextView.text = getString(R.string.unlucky_message)
        }
    }

    private fun setImageForResult(imageView: ImageView, result: Int) {
        when (result) {
            1 -> imageView.setImageResource(R.drawable.dice_1)
            2 -> imageView.setImageResource(R.drawable.dice_2)
            3 -> imageView.setImageResource(R.drawable.dice_3)
            4 -> imageView.setImageResource(R.drawable.dice_4)
            5 -> imageView.setImageResource(R.drawable.dice_5)
            6 -> imageView.setImageResource(R.drawable.dice_6)
        }
    }

}
