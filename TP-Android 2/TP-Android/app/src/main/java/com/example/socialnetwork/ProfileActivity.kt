package com.example.socialnetwork

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.socialnetwork.db.SocialNetworkDatabase

class ProfileActivity : ComponentActivity() {

    private lateinit var db: SocialNetworkDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        title = "Modifier le profil"

        // Initialiser la base de données
        db = SocialNetworkDatabase(this)

        val currentPassword = findViewById<EditText>(R.id.currentPassword)
        val newPassword = findViewById<EditText>(R.id.newPassword)
        val savePassword = findViewById<Button>(R.id.savePassword)
        val deleteAccount = findViewById<Button>(R.id.deleteAccount)

        // Changer le mot de passe
        savePassword.setOnClickListener {
            val current = currentPassword.text.toString().trim()
            val newPass = newPassword.text.toString().trim()

            if (current.isEmpty() || newPass.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Vérifier et mettre à jour le mot de passe
            val username = intent.getStringExtra("username") ?: return@setOnClickListener
            val user = db.findUser(username, current)

            if (user != null) {
                db.updatePassword(user.id, newPass)
                Toast.makeText(this, "Mot de passe mis à jour avec succès", Toast.LENGTH_SHORT).show()
                finish() // Retour à l'écran précédent
            } else {
                Toast.makeText(this, "Mot de passe actuel incorrect", Toast.LENGTH_SHORT).show()
            }
        }

        // Supprimer le compte
        deleteAccount.setOnClickListener {
            val username = intent.getStringExtra("username") ?: return@setOnClickListener
            val user = db.findUser(username, "")

            if (user != null) {
                db.deactivateAccount(user.id)
                Toast.makeText(this, "Compte supprimé", Toast.LENGTH_SHORT).show()

                // Rediriger vers la page de connexion
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Utilisateur introuvable", Toast.LENGTH_SHORT).show()
            }
        }
    }
}