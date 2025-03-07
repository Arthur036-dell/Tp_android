package com.example.socialnetwork

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.socialnetwork.data.Post
import android.util.Log

class PostAdapter(private var posts: List<Post>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val postTitle: TextView = view.findViewById(R.id.postTitle)
        val postDescription: TextView = view.findViewById(R.id.postDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        Log.d("PostAdapter", "Post affiché : ${post.post_title} à la position $position")
        holder.postTitle.text = post.post_title
        holder.postDescription.text = post.post_description
    }

    fun updatePosts(newPosts: List<Post>) {
        Log.d("PostAdapter", "Nombre total")
        posts = newPosts
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        Log.d("PostAdapter", "Nombre total de posts : ${posts.size}")
        return posts.size
    }
}