package com.varivoda.igor.zagonetke.ui.navigation_activity.ui.riddle

import android.content.DialogInterface
import android.graphics.Typeface
import android.media.MediaPlayer
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.varivoda.igor.zagonetke.R
import com.varivoda.igor.zagonetke.models.Riddle
import com.varivoda.igor.zagonetke.models.ScoresEntry
import com.varivoda.igor.zagonetke.ui.navigation_activity.NavigationActivity
import com.varivoda.igor.zagonetke.ui.navigation_activity.ui.levels.RIDDLE_OBJECT
import com.varivoda.igor.zagonetke.ui.shared.*
import kotlinx.android.synthetic.main.popup_end.view.*
import kotlinx.android.synthetic.main.popup_krivo.view.*
import kotlinx.android.synthetic.main.popup_tocno.view.*
import kotlinx.android.synthetic.main.popup_tocno.view.picTrue
import kotlinx.android.synthetic.main.riddle_fragment.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

const val NUMBER_OF_POINTS_FOR_CORRECT_ANSWER = 10
const val MY_TYPEFACE = "fonts/spicy.otf"

class RiddleFragment : Fragment() {

    private val riddleViewModel by viewModel<RiddleViewModel>()
    private val preferences by inject<Preferences>()
    private val alphabet: CharRange = ('a'..'z')
    private lateinit var smalltobig: Animation
    private lateinit var correct: MediaPlayer
    private lateinit var wrong: MediaPlayer
    private lateinit var end: MediaPlayer
    private var currentQuestion: Int = 0
    private var riddleList: List<Riddle> = mutableListOf()
    private var textViews = mutableListOf<TextView>()
    private var navController: NavController? = null
    private var tempAnswer: String = ""
    private var currentIntegerScore = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.riddle_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        riddleViewModel.getAllRiddles(preferences.getToken())
        riddleViewModel.allRiddles.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                riddleList = it
            }
        })
        updateScoreTextView()
        riddleViewModel.integerScore.observe(viewLifecycleOwner, Observer {
            if(it!=null) {
                currentIntegerScore = it.score_value
                textViewScore.text = getString(R.string.score_text_resource, it.score_value.toString())
            }else{
                textViewScore.text = getString(R.string.error_msg)
            }
        })
    }

    private fun updateScoreTextView(){
        riddleViewModel.getIntegerScoreForUser(preferences.getToken(),preferences.getUser())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val riddle: Riddle? = arguments?.getParcelable(RIDDLE_OBJECT)
        if(riddle!=null) {
            currentQuestion = riddle.id
        }
        setRiddleData(arguments?.getParcelable(RIDDLE_OBJECT))
        (activity as NavigationActivity).supportActionBar?.title =
            getString(R.string.game_resource)

        //animacije i zvukovi
        smalltobig = AnimationUtils.loadAnimation(context, R.anim.smalltobig)
        correct = MediaPlayer.create(context, R.raw.truee)
        wrong = MediaPlayer.create(context, R.raw.falsee)
        end = MediaPlayer.create(context, R.raw.congr)

        confirmButton.setOnClickListener {
            doValidate()
        }
        deleteButton.setOnClickListener {
          deleteLetter()
        }
        navController = Navigation.findNavController(view)
        solutionEditText.isLongClickable = false
        setTypefaces(listOf(textQuestion,solutionEditText,one,two,three,four,five,six,seven,eight,nine,ten,deleteButton,confirmButton))
    }

    private fun setTypefaces(views: List<TextView>){
        val typeface = Typeface.createFromAsset(context?.assets, MY_TYPEFACE)
        views.forEach {
            it.typeface = typeface
        }
    }

    private fun setSolutionEditText(text:String){
        solutionEditText.setText(text)
    }


    private fun jokerPopup() {
        Utils.showDefaultDialog(requireContext(), getString(R.string.joker_question),negativeButtonClick, positiveButtonClick)
    }
    private val negativeButtonClick = { _: DialogInterface, _: Int ->
    }
    private val positiveButtonClick = { _: DialogInterface, _: Int ->
        preferences.setJoker()
        //server updates
        riddleViewModel.updateScoreForUser(preferences.getToken(), ScoresEntry(preferences.getUser(),currentQuestion,-50))
        riddleViewModel.updateScoreBool.observe(viewLifecycleOwner, Observer {
            if(it){
                updateScoreTextView()
            }
        })

        showSolution()
    }

    private fun showSolution() {
        riddleList[currentQuestion].answer.forEach {
            var flag = 0
            for (k in 0 until layoutParent.childCount) {
            if ((layoutParent.getChildAt(k) as TextView).text.toString()[0] == it) {
                tempAnswer += (layoutParent.getChildAt(k) as TextView).text.toString()
                layoutParent.getChildAt(k).isClickable = false
                layoutParent.getChildAt(k).visibility = View.INVISIBLE
                textViews.add(layoutParent.getChildAt(k) as TextView)
                flag = 1
                break
            }
        }
            if (flag == 0) {
                for (k in 0 until layoutParent1.childCount) {
                    if ((layoutParent1.getChildAt(k) as TextView).text
                            .toString()[0] == it
                    ) {
                        tempAnswer += (layoutParent1.getChildAt(k) as TextView).text
                            .toString()
                        layoutParent1.getChildAt(k).isClickable = false
                        layoutParent1.getChildAt(k).visibility = View.INVISIBLE
                        textViews.add(layoutParent1.getChildAt(k) as TextView)
                        break
                    }
                }
            }

        }
        setSolutionEditText(tempAnswer)
    }


    private fun deleteLetter() {
        var counter = textViews.size
        if (counter > 0) {
            for (i in 0 until layoutParent.childCount) {
                if (textViews[counter-1].id == layoutParent.getChildAt(i).id
                ) {
                    layoutParent.getChildAt(i).isClickable = true
                    layoutParent.getChildAt(i).visibility = View.VISIBLE
                }
            }
            for (i in 0 until layoutParent1.childCount) {
                if (textViews[counter-1].id == layoutParent1.getChildAt(i).id
                ) {
                    layoutParent1.getChildAt(i).isClickable = true
                    layoutParent1.getChildAt(i).visibility = View.VISIBLE
                }
            }
            textViews.removeAt(counter-1)
            tempAnswer = tempAnswer.substring(0, tempAnswer.length - 1)
            val s: String = solutionEditText.text.toString()
            counter = s.length
            if (counter > 0) {
                setSolutionEditText(s.substring(0, counter - 1))
            }
        }
    }

    private fun setRiddleData(riddle: Riddle?) {
        textQuestion.text = riddle?.riddleText
        val listOfLetters = mutableListOf<Char>()
        riddle?.answer?.forEach {
            listOfLetters.add(it)

        }
        while (listOfLetters.size<10){
            listOfLetters.add(alphabet.random())
        }
        listOfLetters.shuffle()
        var i=0
        layoutParent.forEach {
            val x = i
            (it as TextView).text = listOfLetters[i].toString()
            it.visibility = View.VISIBLE
            it.isClickable = true
            it.setOnClickListener {view ->
                tempAnswer += (view as TextView).text.toString()
                setSolutionEditText(tempAnswer)
                layoutParent.getChildAt(x).isClickable = false
                layoutParent.getChildAt(x).visibility = View.INVISIBLE
                textViews.add(layoutParent.getChildAt(x) as TextView)

            }
            i++

        }
        layoutParent1.forEach {
            val y = i-5
            (it as TextView).text = listOfLetters[i].toString()
            it.visibility = View.VISIBLE
            it.isClickable = true
            it.setOnClickListener { view ->
                tempAnswer += (view as TextView).text.toString()
                setSolutionEditText(tempAnswer)
                layoutParent1.getChildAt(y).isClickable = false
                layoutParent1.getChildAt(y).visibility = View.INVISIBLE
                textViews.add(layoutParent1.getChildAt(y) as TextView)

            }
            i++
        }

        textViews.clear()
        tempAnswer = ""
        setSolutionEditText("")

    }


    private fun doValidate() {
        if (solutionEditText.text.toString() == riddleList[currentQuestion].answer) {
            //update score on server
            riddleViewModel.updateScoreForUser(preferences.getToken(), ScoresEntry(preferences.getUser(),currentQuestion+1,NUMBER_OF_POINTS_FOR_CORRECT_ANSWER))
            val dialogView: View = layoutInflater.inflate(R.layout.popup_tocno, LinearLayout(context),false)
            val dialog = Utils.showCoreDialog(requireContext(),dialogView)
            dialog?.show()
            dialogView.picTrue.startAnimation(smalltobig)
            vibratePhone(100)
            correct.start()
            dialogView.buttonContinue.setOnClickListener {
                dialog?.dismiss()
                currentQuestion += 1
                if(riddleList.size<=currentQuestion){
                    endOfTheGame()
                }else{
                    setRiddleData(riddleList[currentQuestion])
                }
                updateScoreTextView()

            }
            setSolutionEditText("")
        } else {
            val dialogView: View = layoutInflater.inflate(R.layout.popup_krivo, LinearLayout(context),false)
            val dialog = Utils.showCoreDialog(requireContext(),dialogView)
            dialog?.show()
            dialogView.pic.startAnimation(smalltobig)
            vibratePhone(300)
            wrong.start()
            dialogView.buttonFalse.setOnClickListener{
                dialog?.dismiss()
                setRiddleData(riddleList[currentQuestion])
            }
            setSolutionEditText("")
        }
    }

    private fun endOfTheGame() {
            if(currentQuestion==riddleList.size) {
                riddleViewModel.updateScoreForUser(
                    preferences.getToken(),
                    ScoresEntry(
                        preferences.getUser(),
                        currentQuestion + 1,
                        NUMBER_OF_POINTS_FOR_CORRECT_ANSWER
                    )
                )
            }
            val dialogView: View = layoutInflater.inflate(R.layout.popup_end, LinearLayout(context),false)
            val dialog = Utils.showCoreDialog(requireContext(),dialogView)
            dialog?.show()
            vibratePhone(100)
            end.start()
            dialogView.buttonEnd.setOnClickListener {
               dialog?.dismiss()
               navController?.popBackStack()
            }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.joker_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.jokerIcon){
            if(preferences.jokerAvailable()){
                if(currentIntegerScore<50){
                   Utils.showSelectedToast(requireContext(),getString(R.string.dont_have_enough_points))

                }else{
                    jokerPopup()
                }

            }else{
                Utils.showSelectedToast(requireContext(),getString(R.string.joker_used_resource))
            }
        }else if(item.itemId==R.id.resetIcon){
            Utils.showDefaultDialog(requireContext(), getString(R.string.reset_question_resource),negativeButtonClick, resetStats)
        }
        return super.onOptionsItemSelected(item)
    }

    private val resetStats = { _: DialogInterface, _: Int ->
        resetStatsFunction()
    }

    private fun resetStatsFunction() {
        riddleViewModel.resetStatsForUser(preferences.getToken(), ScoresEntry(preferences.getUser(),0,0))
        navController?.popBackStack()
    }

    override fun onDestroy() {
        super.onDestroy()
        Utils.releaseMedia(listOf(correct,wrong,end))
    }
}
