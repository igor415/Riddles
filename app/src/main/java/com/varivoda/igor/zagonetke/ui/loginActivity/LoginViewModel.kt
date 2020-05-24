package com.varivoda.igor.zagonetke.ui.loginActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varivoda.igor.zagonetke.data.Repository
import com.varivoda.igor.zagonetke.models.ForgotPasswordEntry
import com.varivoda.igor.zagonetke.models.LoginEntry
import com.varivoda.igor.zagonetke.models.LoginResponse
import com.varivoda.igor.zagonetke.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val repo: Repository): ViewModel() {
    var loginResponse = MutableLiveData<LoginResponse>()
    val changePasswordResponse = MutableLiveData<Boolean>()
    val createUserResponse = MutableLiveData<Boolean>()

    fun validateUsernameAndPassword(username: String,password: String){
        viewModelScope.launch(Dispatchers.IO) {
            val loginEntry = LoginEntry(username,password)
            val result = repo.validateUsernameAndPassword(loginEntry)
            loginResponse.postValue(result)
        }
    }
    fun changePassword(username: String, security: String, newPassword: String){
        viewModelScope.launch(Dispatchers.IO) {
            val forgotPasswordEntry = ForgotPasswordEntry(username,security, newPassword)
            val result = repo.changePassword(forgotPasswordEntry)
            changePasswordResponse.postValue(result)
        }
    }
    fun createUser(username: String,firstName: String,lastName: String,security: String,email: String,password: String){
        viewModelScope.launch(Dispatchers.IO) {
            val user = User(username,firstName,lastName,security,email,password)
            val result = repo.createUser(user)
            createUserResponse.postValue(result)
        }
    }
}