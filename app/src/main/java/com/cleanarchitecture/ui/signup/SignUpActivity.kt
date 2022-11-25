package com.cleanarchitecture.ui.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cleanarchitecture.R
import com.cleanarchitecture.data.Resource
import com.cleanarchitecture.model.login.LoginModel
import com.cleanarchitecture.ui.business.BusinessActivity
import com.cleanarchitecture.ui.signup.viewmodel.SignUpViewModel
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpActivity : AppCompatActivity() {
    private val signUpViewModel: SignUpViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btnSignUp.setOnClickListener {
            if (etSignInEmail.text.toString().trim().isEmpty() && etSignInPassword.text.toString()
                    .isEmpty()
            ) {
                Toast.makeText(this, "email and password is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            signUpViewModel.createUser(
                LoginModel(
                    0,
                    "",
                    "wp",
                    "android",
                    etSignInEmail.text.toString().trim(),
                    etSignInPassword.text.toString().trim(),
                    etFirstName.text.toString(),
                    0,
                    etLastName.text.toString(),
                    "",
                    0,
                    etZipCode.text.toString()

                )
            )

        }

        lifecycleScope.launch {
            signUpViewModel.signUpState.collect{
                handleSignUp(it)
            }
        }
    }

    private fun handleSignUp(resource: Resource<LoginModel>) {
        when(resource.status){
            Resource.Status.SUCCESS ->{
                startActivity(Intent(this,BusinessActivity::class.java))
            }
            else -> {}
        }

    }
}