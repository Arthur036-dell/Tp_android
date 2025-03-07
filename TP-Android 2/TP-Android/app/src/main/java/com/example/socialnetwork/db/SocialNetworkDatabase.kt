package com.example.socialnetwork.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.socialnetwork.data.User

class SocialNetworkDatabase(
    mContext: Context,
    name: String = "sn_db",
    version: Int = 1
) : SQLiteOpenHelper (
    mContext,
    name,
    null,
    version
) {
    override fun onCreate(db: SQLiteDatabase?) {
        Log.d("Database", "onCreate239 called, creating tables")
        val createTableUser = """
            CREATE TABLE users (
                id integer PRIMARY KEY,
                name varchar(50),
                password varchar(20),
                etat INTEGER DEFAULT 1
             )
        """.trimIndent()
        db?.execSQL(createTableUser)
    }

    fun addUser(user: User): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", user.name)
            put("password", user.password)
            put("etat", user.etat)
        }
        val result = db.insert("users", null, values)
        db.close()
        return result.toInt() != -1
    }

    fun findUser(name: String, password: String): User? {
        val db = this.readableDatabase
        val cursor = db.query(
            "users",
            null,
            "name = ? AND password = ?",
            arrayOf(name, password),
            null,
            null,
            null
        )
        if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val username = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            val etat = cursor.getInt(cursor.getColumnIndexOrThrow("etat")) // Récupération de "etat"

            cursor.close()
            db.close()

            // Si l'état est 0, retourne null
            if (etat == 0) {
                return null
            }

            // Sinon, retourne l'utilisateur
            return User(id, username, password, etat)
        }
        cursor?.close()
        db.close()
        return null
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) { // Supposons que la nouvelle version est 2
            db?.execSQL("ALTER TABLE users ADD COLUMN etat INTEGER DEFAULT 1")
        }
    }

    // Mettre à jour le mot de passe
    fun updatePassword(userId: Int, newPassword: String): Boolean {
        val db = writableDatabase
        val values = ContentValues()
        values.put("password", newPassword)
        val result = db.update("users", values, "id = ?", arrayOf(userId.toString()))
        db.close()
        return result > 0
    }

    // Désactiver le compte (mettre état à 0)
    fun deactivateAccount(userId: Int): Boolean {
        val db = writableDatabase
        val values = ContentValues()
        values.put("etat", 0) // Compte désactivé
        val result = db.update("users", values, "id = ?", arrayOf(userId.toString()))
        db.close()
        return result > 0
    }
}