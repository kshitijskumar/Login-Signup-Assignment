package com.example.loginsignupassignment.repository

import android.util.Log
import com.example.loginsignupassignment.model.ExistingUser
import com.example.loginsignupassignment.model.NewUser
import com.example.loginsignupassignment.retrofit.RetrofitInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val retrofitInterface: RetrofitInterface
) {

    suspend fun signUpNewUserInRepo(newUser: NewUser) = flow{
        withContext(Dispatchers.IO){
            try {
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
                    201 -> emit("SignUp successful")
                    409 -> emit("Email already taken")
                    else -> emit("Something went wrong")
                }
            }catch (e: Exception){
                Log.d("MainRepoCatch", "The error is ${e.message}")
            }
        }
    }.catch {
        emit("Something went wrong")
    }

    suspend fun loginUserInRepo(existingUser: ExistingUser) = flow{
        Log.d("MainRepo", "Login user thread is ${Thread.currentThread().name}")
            try {
                val response = retrofitInterface.loginUser(existingUser.email,
                                                                                        existingUser.password)

                when(response.code()){
                    201 -> emit("Login successful")
                    401 -> emit("Invalid email or password")
                    else -> emit("Something went wrong")
                }
            }catch (e: Exception){
                Log.d("MainRepoCatch", "Login user error message is ${e.message}")
            }

    }.catch {
        emit("Something went wrong")
    }
}