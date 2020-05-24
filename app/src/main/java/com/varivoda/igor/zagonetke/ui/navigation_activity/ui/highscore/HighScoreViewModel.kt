package com.varivoda.igor.zagonetke.ui.navigation_activity.ui.highscore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varivoda.igor.zagonetke.data.Repository
import com.varivoda.igor.zagonetke.models.ScoresEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HighScoreViewModel(private val repository: Repository) : ViewModel() {

    var highScores = MutableLiveData<List<ScoresEntry>>()

    fun getHighScores(token: String){
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getHighScores(token)
            highScores.postValue(result)
        }
    }
}
