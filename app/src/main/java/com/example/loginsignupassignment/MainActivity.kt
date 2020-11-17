package com.example.loginsignupassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.loginsignupassignment.model.ExistingUser
import com.example.loginsignupassignment.ui.LoggedInActivity
import com.example.loginsignupassignment.ui.SignUpActivity
import com.example.loginsignupassignment.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observeLoginStatus()

        btnLogin.setOnClickListener {
            val email = etEmailLogin.text.toString().trim()
            val password = etPasswordLogin.text.toString().trim()
            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "None of the fields can be empty", Toast.LENGTH_SHORT).show()
            }else{
                val existingUser = ExistingUser(email, password)
                showLoading()
                viewModel.loginUserInViewModel(existingUser)
            }
        }

        btnSignUpWithEmail.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeLoginStatus(){
        viewModel.loginStatus.observe(this, {
            when(it){
                "Login successful" -> {
                    hideLoading()
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoggedInActivity::class.java)
                    startActivity(intent)
                }
                "Invalid email or password" -> {
                    hideLoading()
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                }
                "Something went wrong" -> {
                    hideLoading()
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun hideLoading(){
        pbLogin.visibility = View.GONE
        etEmailLogin.isEnabled = true
        etPasswordLogin.isEnabled = true
    }

    private fun showLoading(){
        pbLogin.visibility = View.VISIBLE
        etEmailLogin.isEnabled = false
        etPasswordLogin.isEnabled = false
    }
}