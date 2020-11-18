package com.example.loginsignupassignment.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginsignupassignment.model.ExistingUser
import com.example.loginsignupassignment.model.NewUser
import com.example.loginsignupassignment.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class MainViewModel @ViewModelInject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _signUpStatus = MutableLiveData<String>()
    val signUpStatus: LiveData<String>
    get() = _signUpStatus

    private val _loginStatus = MutableLiveData<String>()
    val loginStatus: LiveData<String>
    get() = _loginStatus


    fun signUpNewUserInViewModel(newUser: NewUser)= viewModelScope
        .launch(Dispatchers.IO){
        repository.signUpNewUserInRepo(newUser).collect {
            Log.d("MainViewModel", "Collected string is $it")
            if (it == "SignUp successful"){
                val user = repository.showSignedUpUser()
                Log.d("MainViewModel", "SignedUp user is $user")
            }
            _signUpStatus.postValue(it)
        }
    }

    fun loginUserInViewModel(existingUser: ExistingUser) = viewModelScope
        .launch(Dispatchers.IO) {
            repository.loginUserInRepo(existingUser).collect {
                Log.d("MainViewModel", "Collected string is $it")
                if (it == "Login successful"){
                    val user = repository.showLoggedInUser()
                    Log.d("MainViewModel", "Logged in user is $user")
                }
                _loginStatus.postValue(it)

            }
        }

}