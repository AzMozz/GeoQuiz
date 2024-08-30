package com.example.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    var currentIndex = 0

    private val _buttonsEnabled = MutableLiveData<Boolean>()
    val buttonsEnabled: LiveData<Boolean>
        get() = _buttonsEnabled

    init {
        _buttonsEnabled.value = true
    }

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer


    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
        _buttonsEnabled.value = true
    }

    fun moveToPrev() {
        currentIndex = (currentIndex - 1 + questionBank.size) % questionBank.size
        _buttonsEnabled.value = true
    }

    fun setButtonsEnabled(enabled: Boolean) {
        _buttonsEnabled.value = enabled
    }

    fun getQuestionBankSize():Int {
        return questionBank.size
    }
}