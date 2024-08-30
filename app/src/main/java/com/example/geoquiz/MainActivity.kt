package com.example.geoquiz

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.geoquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()

    private var currentIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        quizViewModel.buttonsEnabled.observe(this) { enabled ->
            binding.trueButton.isEnabled = enabled
            binding.falseButton.isEnabled = enabled
        }

        binding.questionTextView.setOnClickListener{ view: View ->
            quizViewModel.moveToNext()
            updateQuestion()
        }

        binding.trueButton.setOnClickListener { view: View ->
            checkAnswer(true, view)
            quizViewModel.setButtonsEnabled(false)
        }

        binding.falseButton.setOnClickListener { view: View ->
            checkAnswer(false, view)
            quizViewModel.setButtonsEnabled(false)
        }

        binding.nextButton.setOnClickListener{
            quizViewModel.moveToNext()
            updateQuestion()
        }
        updateQuestion()

        binding.prevButton.setOnClickListener{
            quizViewModel.moveToPrev()
            updateQuestion()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }



    private fun nextQuestion(){
        currentIndex = (currentIndex + 1) % quizViewModel.getQuestionBankSize()
        updateQuestion()
        binding.trueButton.isEnabled = true
        binding.falseButton.isEnabled = true
    }

    private fun prevQuestion(){
        currentIndex = (currentIndex - 1 + quizViewModel.getQuestionBankSize()) % quizViewModel.getQuestionBankSize()
        updateQuestion()
    }

    private fun updateQuestion(){
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean, view : View){
        val correctAnswer = quizViewModel.currentQuestionAnswer

        val messageResId = if (userAnswer == correctAnswer){
            score++
            Log.d("QuizApp", "Correct answer! Score is now $score")
            R.string.correct_toast
        } else {
            Log.d("QuizApp", "Incorrect answer.")
            R.string.incorrect_toast
        }
        Snackbar.make(
            view,
            messageResId,
            Snackbar.LENGTH_SHORT
        ).show()
        binding.trueButton.isEnabled = false
        binding.falseButton.isEnabled = false
        if (quizViewModel.currentIndex == quizViewModel.getQuestionBankSize() - 1) {
            val percentangeScore = (score.toDouble() / quizViewModel.getQuestionBankSize()) * 100
            Log.d("QuizApp", "Quiz complete! Final score: $percentangeScore%")
            Toast.makeText(this, "You scored $percentangeScore%", Toast.LENGTH_SHORT).show()
            score = 0
        }
    }
}