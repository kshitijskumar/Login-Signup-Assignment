package com.example.loginsignupassignment.repository

import android.util.Log
import com.example.loginsignupassignment.model.ExistingUser
import com.example.loginsignupassignment.model.LoginResponseModel
import com.example.loginsignupassignment.model.NewUser
import com.example.loginsignupassignment.model.SignUpResponseModel
import com.example.loginsignupassignment.retrofit.RetrofitInterface
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val retrofitInterface: RetrofitInterface
) {

    private var loggedInUserDetail = LoginResponseModel("", "", "", "", "", "", "", "")
    private var signedUpUserToken = SignUpResponseModel("")

    suspend fun signUpNewUserInRepo(newUser: NewUser) = flow{

        val response = retrofitInterface.signUpNewUser(newUser.name,
                newUser.phone,
                newUser.email,
                newUser.password,
                newUser.country,
                newUser.city)

//                if (response.isSuccessful){
//                    Log.d("MainRepo", "The token is ${response.body()?.token}")
//                    Log.d("MainRepo", "The message is ${response.body()?.message}")
//                    if (response.body()?.token != null){
//                        emit("SignUp successful")
//                    }else{
//                        emit("Something went wrong")
//                        Log.d("MainRepo", "The message is ${response.body()?.message}")
//                    }
//                }else{
//                    Log.d("MainRepoElse", "Not successful")
//                    Log.d("MainRepoElse", "Not successful ${response.message()}")
//                    emit("Email already taken")
//                }

        when(response.code()){
            201 -> {
                response.body()?.let {
                    signedUpUserToken = it.copy()
                }

                emit("SignUp successful")
                Log.d("MainRepo", "Signup token is ${response.body()?.token}")
            }
            409 -> emit("Email already taken")
            else -> emit("Something went wrong")
        }

    }.catch {
        emit("Something went wrong")
    }

    suspend fun loginUserInRepo(existingUser: ExistingUser) = flow{

        val response = retrofitInterface.loginUser(existingUser.email,
                existingUser.password)
        when(response.code()){
            201 -> {
                response.body()?.let {
                    loggedInUserDetail = it.copy()
                }

                emit("Login successful")
            }
            409 -> emit("Invalid email or password")
            else -> emit("Something went wrong")
        }

    }.catch {
        emit("Something went wrong")
    }

    fun showLoggedInUser() = loggedInUserDetail

    fun showSignedUpUser() = signedUpUserToken
}