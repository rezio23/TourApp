package com.project189.ui.splash

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.project189.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()

            // 1. All fields required
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 2. Username must be Capitalized (First letter Uppercase, rest lowercase for better display)
            // Or if you mean MUST be entered as "Capitalized" by user:
            if (!username[0].isUpperCase()) {
                Toast.makeText(this, "Username must start with a Capital letter", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 3. Email must end with @gmail.com
            if (!email.endsWith("@gmail.com")) {
                Toast.makeText(this, "Email must end with @gmail.com", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 4. Password validation (8-16 digits, Capital, Small, Number)
            val passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,16}$".toRegex()
            if (!password.matches(passwordRegex)) {
                Toast.makeText(this, "Password must be 8-16 characters with Uppercase, Lowercase, and a Number", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Real Firebase Registration
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val profileUpdates = userProfileChangeRequest {
                            displayName = username
                        }

                        user?.updateProfile(profileUpdates)
                            ?.addOnCompleteListener { profileTask ->
                                if (profileTask.isSuccessful) {
                                    Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                            }
                    } else {
                        Toast.makeText(this, "Registration Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }

        binding.tvLogin.setOnClickListener {
            finish()
        }
    }
}
