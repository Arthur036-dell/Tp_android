package com.example.socialnetwork

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.socialnetwork.data.User
import com.example.socialnetwork.db.SocialNetworkDatabase

class RegisterActivity : ComponentActivity() {
    @SuppressLint("WrongViewCast")

    lateinit var db : SocialNetworkDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val confirm_password = findViewById<EditText>(R.id.confirm_password)
        val register = findViewById<Button>(R.id.register)

        db = SocialNetworkDatabase(this)

        register.setOnClickListener(View.OnClickListener {

            val usernameInput = username.text.toString()
            val passwordInput = password.text.toString()
            val confirmPasswordInput = confirm_password.text.toString()

            if(usernameInput.trim().isEmpty()) {
                Toast.makeText(this, "Veuillez saisir un nom d'utilisateur", Toast.LENGTH_LONG).show()
            }
            else if(passwordInput.trim().isEmpty()) {
                Toast.makeText(this, "Veuillez saisir un mot de passe", Toast.LENGTH_LONG).show()
            }
            else if(confirmPasswordInput.trim().isEmpty()) {
                Toast.makeText(this, "Veuillez confirmer le mot de passe", Toast.LENGTH_LONG).show()
            }
            else {
                if (passwordInput != confirmPasswordInput) {
                    Toast.makeText(this, "Veuillez saisir le mot de passe", Toast.LENGTH_LONG).show()
                }
                else {
                    val user = User(0, usernameInput, passwordInput, 1)
                    val query = db.addUser(user)
                    if(query) {
                        Toast.makeText(this, "Succès de création de compte", Toast.LENGTH_LONG).show()
                        val intentToLoginActivity = Intent(this, LoginActivity::class.java)
                        intentToLoginActivity.putExtra("username", usernameInput)
                        startActivity(intentToLoginActivity)
                    }
                }
            }
        })

        val loginText = findViewById<TextView>(R.id.loginText)
        loginText.setOnClickListener(View.OnClickListener {
            val intentToLoginActivity = Intent(this, LoginActivity::class.java)
            startActivity(intentToLoginActivity)
        })
    }
}