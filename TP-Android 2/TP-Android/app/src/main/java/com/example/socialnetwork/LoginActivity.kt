package com.example.socialnetwork

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.socialnetwork.db.SocialNetworkDatabase
import android.util.Log


class LoginActivity : ComponentActivity() {

    lateinit var db : SocialNetworkDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Log.d("LoginActivity", "LoginActivity démarrée")

        val login = findViewById<Button>(R.id.login)
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        //val error = findViewById<TextView>(R.id.error)

        db = SocialNetworkDatabase(this)

        val register = findViewById<TextView>(R.id.register)

        login.setOnClickListener(View.OnClickListener {
            val usernameInput = username.text.toString()
            val passwordInput = password.text.toString()

            if(usernameInput.trim().isEmpty()) {
                Toast.makeText(this, "Veuillez saisir un nom d'utilisateur", Toast.LENGTH_LONG).show()
                //error.visibility = View.VISIBLE
            }
            else if(passwordInput.trim().isEmpty()) {
                Toast.makeText(this, "Veuillez saisir un mot de passe", Toast.LENGTH_LONG).show()
                //error.visibility = View.VISIBLE
            }
            else {
                if (db.findUser(usernameInput, passwordInput) != null) {
                    Toast.makeText(this, "Connexion établie", Toast.LENGTH_LONG).show()
                    //error.visibility = View.GONE

                    val intentToHomeActivity = Intent(this, HomeActivity::class.java)
                    intentToHomeActivity.putExtra("username", usernameInput)
                    startActivity(intentToHomeActivity)
                    username.setText("")
                    password.setText("")
                }
                else
                    Toast.makeText(this, "Veuillez saisir les identifiants corrects", Toast.LENGTH_LONG).show()
            }
        })

        register.setOnClickListener(View.OnClickListener {
            val intentToRegisterActivity = Intent(this, RegisterActivity::class.java)
            startActivity(intentToRegisterActivity)
        })
    }
}

