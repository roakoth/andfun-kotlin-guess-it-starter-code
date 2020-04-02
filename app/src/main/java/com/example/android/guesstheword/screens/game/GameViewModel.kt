package com.example.android.guesstheword.screens.game

import android.app.PendingIntent
import android.content.IntentSender
import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel(){
    private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
    private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
    private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
    private val NO_BUZZ_PATTERN = longArrayOf(0)


    companion object{
        //This is when the game is over
        private const val DONE = 0L

        //This is the number of milliseconds in a second
        private const val ONE_SECOND = 1000L

        //This is the total time of the game
        private const val COUNTDOWN_TIME = 10000L

    }



    private val timer:CountDownTimer


    // The current word
     private val _word = MutableLiveData<String>()
       val word:LiveData<String>
    get() = _word

    // The current score
     private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
    get() = _score

    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
    get() = _currentTime

    val currentTimeString = Transformations.map(currentTime,
            {
                time ->
                DateUtils.formatElapsedTime(time)
            })

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinished: LiveData<Boolean>
    get() = _eventGameFinish

    init{
        _eventGameFinish.value = false
        resetList()
        nextWord()
        Log.i("GameViewModel","GameViewModel created!")
        _score.value = 0;

        //Creates timer which triggers the end of the game when it finishes
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
            _currentTime.value = (millisUntilFinished / 1000)


            }

            override fun onFinish() {
                // TODO implement what should happen when the timer finishes
                _eventGameFinish.value = true
            }
        }



        timer.start()
    }




    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel","GameViewModel destroyed!")
        timer.cancel()
    }

    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
           //_eventGameFinish.value = true
            resetList()
        }
            _word.value = wordList.removeAt(0)


    }




     fun onSkip() {
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

     fun onCorrect() {
         _score.value = (score.value)?.plus(1)
        nextWord()
    }

    fun onGameFinishComplete(){
        _eventGameFinish.value = false
    }

}

