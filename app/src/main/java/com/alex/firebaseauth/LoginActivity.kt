package com.alex.firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.alex.firebaseauth.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener(){
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isEmpty()){
                binding.etEmail.error = "Email harus diisi"
                binding.etEmail.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.etEmail.error = "Email tidak valid"
                binding.etEmail.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty() || password.length < 6){
                binding.etPassword.error = "Password harus lebih dari 6 karakter"
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }

            loginUser(email, password)
        }

        binding.btnRegister.setOnClickListener{
            Intent(this@LoginActivity, RegisterActivity::class.java).also {
                startActivity(it)
            }

            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(email: String, password: String) {
        if(email == "admin@pcr.ac.id"){
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){
                    if(it.isSuccessful){
                        Intent(this@LoginActivity, HomeActivity::class.java).also {intent->
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        Intent(this@LoginActivity, user_rs::class.java).also { intent ->
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

//    override fun onStart() {
//        super.onStart()
//        if(auth.currentUser != null){
//            Intent(this@LoginActivity, HomeActivity::class.java).also {intent->
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                startActivity(intent)
//            }
//        }
//    }
}