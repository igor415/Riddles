package com.varivoda.igor.zagonetke.ui.navigation_activity.ui.levels


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varivoda.igor.zagonetke.data.Repository
import com.varivoda.igor.zagonetke.models.ScoresEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LevelsViewModel(private val repo: Repository) : ViewModel() {

    var scoreForUser = MutableLiveData<ScoresEntry>()


    fun getScoreForUser(token: String,username: String){
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.getScoreForUser(token,username)
            scoreForUser.postValue(result)
        }
    }

}