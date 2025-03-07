package com.example.socialnetwork

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.socialnetwork.data.Post
import com.example.socialnetwork.db.PostDatabase

class NewPost : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)

        // Initialisation des champs
        val postTitle = findViewById<EditText>(R.id.postTitle)
        val postDescription = findViewById<EditText>(R.id.postDescription)
        val savePostButton = findViewById<Button>(R.id.savePostButton)

        // Initialisation de la base de données
        val postDb = PostDatabase(this)

        // Gestion du clic sur le bouton
        savePostButton.setOnClickListener {
            val title = postTitle.text.toString()
            val description = postDescription.text.toString()

            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            } else {
                // Créer un nouvel objet Post
                val newPost = Post(0, title, description, 1)

                // Ajouter le post à la base de données
                val success = postDb.addPost(newPost)

                if (success) {
                    Toast.makeText(this, "Post créé avec succès", Toast.LENGTH_SHORT).show()
                    finish() // Retour à l'activité précédente
                } else {
                    Toast.makeText(this, "Erreur lors de la création du post", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}