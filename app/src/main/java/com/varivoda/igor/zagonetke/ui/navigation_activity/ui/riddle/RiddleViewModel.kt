package com.varivoda.igor.zagonetke.ui.navigation_activity.ui.riddle

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varivoda.igor.zagonetke.data.Repository
import com.varivoda.igor.zagonetke.models.IntegerScore
import com.varivoda.igor.zagonetke.models.Riddle
import com.varivoda.igor.zagonetke.models.ScoresEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RiddleViewModel(private val repo: Repository) : ViewModel() {

    var updateScoreBool = MutableLiveData<Boolean>()
    var integerScore = MutableLiveData<IntegerScore>()
    var allRiddles = MutableLiveData<List<Riddle>>()

    fun getAllRiddles(token: String){
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.getAllRiddles(token)
            allRiddles.postValue(result)
        }
    }

    fun updateScoreForUser(token: String, scoresEntry: ScoresEntry){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.updateScoreForUser(token,scoresEntry)
            updateScoreBool.postValue(response)
        }
    }

    fun getIntegerScoreForUser(token: String,username: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.getIntegerScoreForUser(token,username)
            integerScore.postValue(response)
        }
    }

    fun resetStatsForUser(token: String, scoresEntry: ScoresEntry){
        viewModelScope.launch(Dispatchers.IO) {
            repo.resetStatsForUser(token,scoresEntry)
        }
    }
}
