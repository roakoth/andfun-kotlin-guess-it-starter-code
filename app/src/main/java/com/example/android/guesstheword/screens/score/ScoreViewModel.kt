package com.example.android.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController

class ScoreViewModel(finalScore : Int) : ViewModel(){
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    private val _eventGamePlay = MutableLiveData<Boolean>()
    val eventGamePlay: LiveData<Boolean>
        get() = _eventGamePlay
    init{
        _eventGamePlay.value = false
        Log.i("ScoreViewModel", "Final score is $finalScore")
    }
    fun onGamePlayAgain(){
        _eventGamePlay.value = true
    }
}






