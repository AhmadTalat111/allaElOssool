package com.ahmadtalat.allaelossool

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.ahmadtalat.allaelossool.databinding.FragmentContestBinding
import com.ahmadtalat.allaelossool.databinding.FragmentStartBinding


class ContestFragment : Fragment() {
    data class Question(
        val text: String,
        val answers: List<String>)
var score = 0
    // The first answer is the correct one.  We randomize the answers before showing the text.
    // All questions must have four answers.  We'd want these to contain references to string
    // resources so we could internationalize. (or better yet, not define the questions in code...)
    private val questions: MutableList<Question> = mutableListOf(
        Question(text = "ما هو الأندرويد جيت باك؟",
            answers = listOf("خطأ", "خطأ", "كل الإجابات صح", "خطأ")),
        Question(text = "الكلاس الأساسية للاي أوت؟",
            answers = listOf("خطأ", "خطأ", "الصح", "خطأ")),
        Question(text = "سؤال عربي",
            answers = listOf("خطأ", "الصح", "خطأ", "خطأ"))
    )

    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = 3//Math.min((questions.size + 1) / 2, 3)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentContestBinding>(
            inflater, R.layout.fragment_contest, container, false)

        // Shuffles the questions and sets the question index to the first question.
        //randomizeQuestions()
        setQuestion()

        // Bind this fragment class to the layout
        binding.contest = this

        // Set the onClickListener for the submitButton
        binding.submitButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId
            // Do nothing if nothing is checked (id == -1)
            if (-1 != checkedId) {
                var answerIndex = 0
                when (checkedId) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                    R.id.thirdAnswerRadioButton -> answerIndex = 2
                    R.id.fourthAnswerRadioButton -> answerIndex = 3
                }

                // The first answer in the original question is always the correct one, so if our
                // answer matches, we have the correct answer.
                if (answers[answerIndex] == currentQuestion.answers[0]) {
                    questionIndex++
                    // Advance to the next question
                    if (questionIndex < numQuestions) {
                        currentQuestion = questions[questionIndex]
                        setQuestion()
                        binding.invalidateAll()
                    }
                }
            }
        }
        return binding.root
    }

//    // randomize the questions and set the first question
//    private fun randomizeQuestions() {
//        //questions.shuffle()
//        questionIndex = 0
//        setQuestion()
//    }

    // Sets the question and randomizes the answers.  This only changes the data, not the UI.
    // Calling invalidateAll on the FragmentGameBinding updates the data.
    private fun setQuestion() {
        currentQuestion = questions[questionIndex]
        // randomize the answers into a copy of the array
        answers = currentQuestion.answers.toMutableList()
        // and shuffle them
       // answers.shuffle()
        //(activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_android_trivia_question, questionIndex + 1, numQuestions)
    }
}
