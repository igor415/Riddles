package com.varivoda.igor.zagonetke.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varivoda.igor.zagonetke.data.Repository
import com.varivoda.igor.zagonetke.models.Mailing_list
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel(private val repo: Repository): ViewModel(){

    var email = MutableLiveData<String>()

    fun addUserToMailingList(token: String,mailingList: Mailing_list){
        viewModelScope.launch(Dispatchers.IO) {
            repo.addUserToMailingList(token,mailingList)
        }
    }
    fun deleteUserFromMailingList(token: String,username: String){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteUserFromMailingList(token,username)
        }
    }

    fun getEmailForUser(token: String,username: String){
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.getEmailForUsername(token,username)
            email.postValue(result)
        }
    }
}