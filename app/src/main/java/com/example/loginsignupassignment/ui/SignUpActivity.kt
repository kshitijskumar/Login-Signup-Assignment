package com.example.loginsignupassignment.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.loginsignupassignment.R
import com.example.loginsignupassignment.model.NewUser
import com.example.loginsignupassignment.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_sign_up.*

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        observeSignUpStatus()

        btnContinueSignUp.setOnClickListener {
            val name = etName.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val email = etEmailSignUp.text.toString().trim()
            val password = etPasswordSignUp.text.toString().trim()
            val country = etCountry.text.toString().trim()
            val city = etCity.text.toString().trim()

            if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty() || country.isEmpty() || city.isEmpty()){
                Toast.makeText(this, "None of the fields can be empty", Toast.LENGTH_SHORT).show()
            }else{
                val newUser = NewUser(name, phone, email, password, country, city)
                showLoading()
                viewModel.signUpNewUserInViewModel(newUser)
            }
        }

    }

    private fun observeSignUpStatus(){
        viewModel.signUpStatus.observe(this, {
            Log.d("MainActivity", "Observed string is $it")

            when(it){
                "SignUp successful" -> {
                    hideLoading()
                    Toast.makeText(this, "SignUp successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoggedInActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                "Email already taken" -> {
                    hideLoading()
                    etEmailSignUp.error = "Email already taken"
                    Toast.makeText(this, "Email already taken", Toast.LENGTH_SHORT).show()
                }
                "Something went wrong" -> {
                    hideLoading()
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun showLoading(){
        pbSignUp.visibility = View.VISIBLE
        etName.isEnabled = false
        etPhone.isEnabled = false
        etEmailSignUp.isEnabled = false
        etPasswordSignUp.isEnabled = false
        etCountry.isEnabled = false
        etCity.isEnabled = false
    }

    private fun hideLoading(){
        pbSignUp.visibility = View.GONE
        etName.isEnabled = true
        etPhone.isEnabled = true
        etEmailSignUp.isEnabled = true
        etPasswordSignUp.isEnabled = true
        etCountry.isEnabled = true
        etCity.isEnabled = true
    }
}