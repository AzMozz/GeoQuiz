package com.example.geoquiz

import CheatViewModel
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.geoquiz.databinding.ActivityCheatBinding

const val EXTRA_ANSWER_SHOWN = "com.example.geoquiz.answer_shown"
private const val EXTRA_ANSWER_IS_TRUE = "com.example.geoquiz.answer_is_true"

class CheatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheatBinding
    private val cheatViewModel: CheatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the answer in CheatViewModel if it's not already set
        if (!cheatViewModel.answerShown) {
            cheatViewModel.setAnswer(intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false))
        }

        // If the answer was already shown, update the UI
        if (cheatViewModel.answerShown) {
            showAnswer()
            setAnswerShownResult(true) // Ensure the result is set even after rotation
        }

        // Set up the show answer button click listener
        binding.showAnswerButton.setOnClickListener {
            showAnswer()
            cheatViewModel.answerShown = true
            setAnswerShownResult(true)
        }
    }

    private fun showAnswer() {
        val answerText = when {
            cheatViewModel.answerIsTrue.value == true -> R.string.true_button
            else -> R.string.false_button
        }
        binding.answerTextView.setText(answerText)
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}
