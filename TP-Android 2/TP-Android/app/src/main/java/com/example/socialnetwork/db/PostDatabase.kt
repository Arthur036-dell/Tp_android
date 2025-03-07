package com.example.socialnetwork.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.socialnetwork.data.Post
import android.util.Log

class PostDatabase(
    context: Context,
    name: String = "sn_db",
    version: Int = 1
) : SQLiteOpenHelper(context, name, null, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        Log.d("Database", "onCreate called, creating tables")
        val createTablePost = """
        CREATE TABLE posts (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            post_title TEXT NOT NULL,
            post_description TEXT NOT NULL,
            etat INTEGER DEFAULT 1
        )
    """.trimIndent()
        db?.execSQL(createTablePost)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d("Database", "onUpgrade called: Dropping and recreating tables")
        db?.execSQL("DROP TABLE IF EXISTS posts")
        onCreate(db)
    }

    fun addPost(post: Post): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("post_title", post.post_title)
            put("post_description", post.post_description)
            //put("post_image", post.post_image)
            put("etat", post.etat) // État actif/inactif
        }
        val result = db.insert("posts", null, values)
        db.close()
        return result != -1L
    }

    fun getPosts(): List<Post> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM posts WHERE etat = 1", null) // Seuls les posts actifs
        val posts = mutableListOf<Post>()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val title = cursor.getString(cursor.getColumnIndexOrThrow("post_title"))
                val description = cursor.getString(cursor.getColumnIndexOrThrow("post_description"))
                //val image = cursor.getInt(cursor.getColumnIndexOrThrow("post_image"))
                val etat = cursor.getInt(cursor.getColumnIndexOrThrow("etat"))
                posts.add(Post(id, title, description, etat))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return posts
    }

    fun createPostsTable() {
        val db = writableDatabase
        try {
            val createTableQuery = """
            CREATE TABLE IF NOT EXISTS posts (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                post_title TEXT NOT NULL,
                post_description TEXT NOT NULL,
                etat INTEGER DEFAULT 1
            )
        """.trimIndent()
            db.execSQL(createTableQuery)
            Log.d("PostDatabase", "Table posts créée ou déjà existante")
        } catch (e: Exception) {
            Log.e("PostDatabase", "Erreur lors de la création de la table posts : ${e.message}")
        } finally {
            db.close()
        }
    }

    fun deletePost(postId: Int): Boolean {
        val db = writableDatabase
        val result = db.delete("posts", "id = ?", arrayOf(postId.toString()))
        db.close()
        return result > 0
    }
}