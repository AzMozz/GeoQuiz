import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class CheatViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    companion object {
        private const val KEY_ANSWER_SHOWN = "answer_shown"
        private const val KEY_ANSWER_IS_TRUE = "answer_is_true"
    }

    // LiveData for whether the answer is true
    private val _answerIsTrue = MutableLiveData<Boolean>()
    val answerIsTrue: LiveData<Boolean>
        get() = _answerIsTrue

    // Property for whether the answer has been shown
    var answerShown: Boolean
        get() = savedStateHandle.get(KEY_ANSWER_SHOWN) ?: false
        set(value) = savedStateHandle.set(KEY_ANSWER_SHOWN, value)

    // Method to set the answer's truth value
    fun setAnswer(isTrue: Boolean) {
        _answerIsTrue.value = isTrue
        savedStateHandle.set(KEY_ANSWER_IS_TRUE, isTrue)
    }
}