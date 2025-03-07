package com.example.socialnetwork

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.socialnetwork.data.Post
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialnetwork.db.PostDatabase
import android.util.Log


class HomeActivity : ComponentActivity() {
    private lateinit var adapter: PostAdapter
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val postDb = PostDatabase(this)

        postDb.createPostsTable()
        Log.d("HomeActivity", "HomeActivity démarrée")
        title = "Accueil"

        val username = findViewById<TextView>(R.id.username)
        val getUsername = intent.getStringExtra("username")
        //username.text = "Bienvenue  $getUsername"

        // Initialisation de la RecyclerView
        val postsRecyclerView = findViewById<RecyclerView>(R.id.postsRecyclerView)
        postsRecyclerView.layoutManager = LinearLayoutManager(this)

        // Récupérer les posts depuis la base de données
        //val postDb = PostDatabase(this)
        val posts = postDb.getPosts() // Récupérer les posts avec etat = 1

        // Configurer l'adaptateur pour la RecyclerView
        adapter = PostAdapter(posts)
        postsRecyclerView.adapter = adapter

        //val posts = findViewById<ListView>(R.id.posts)
        //val postsArray = listOf("Post 1", "Post 2", "Post 3", "Post 4", "Post 5")
        //val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, postsArray)
        //val postsArray = arrayListOf(
        //    Post("Post 1", "Je suis le post 1", R.drawable.blog),
        //    Post("Post 2", "Je suis le post 2", R.drawable.blog),
        //    Post("Post 3", "Je suis le post 3", R.drawable.blog),
        //)
        //val adapter = PostsAdapter(this, R.layout.single_post, postsArray)
        //posts.adapter = adapter

        //posts.setOnItemClickListener(AdapterView.OnItemClickListener{parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            //Toast.makeText(this, "Position : $position", Toast.LENGTH_LONG).show()
        //    val intentToPostDetailsActivity = Intent(this, PostDetailsActivity::class.java)
        //    startActivity(intentToPostDetailsActivity)
        //})
    }

    override fun onResume() {
        super.onResume()

        val postDb = PostDatabase(this)
        // Recharger les posts lorsque l'activité est reprise
        val posts = postDb.getPosts()
        Log.d("HomeActivity", "Nombre de posts rechargés : ${posts.size}")
        adapter.updatePosts(posts) // Met à jour les données de l'adaptateur
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add -> {
                // Redirection vers la page RegisterActivity
                val intent = Intent(this, NewPost::class.java)
                startActivity(intent)
                true
            }
            R.id.profile -> {
                // Action pour ouvrir l'écran de modification du profil
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.logout -> {
                // Déconnecte l'utilisateur
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}