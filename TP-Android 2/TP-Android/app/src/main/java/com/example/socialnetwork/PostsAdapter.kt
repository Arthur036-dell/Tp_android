package com.example.socialnetwork

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import com.example.socialnetwork.data.Post

class PostsAdapter(
    val mContext: Context,
    val resource: Int,
    val values: ArrayList<Post>
) : ArrayAdapter<Post>(mContext, resource, values) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val post = values.get(position)
        val intemView = LayoutInflater.from(mContext).inflate(resource, parent, false)
        val post_title = intemView.findViewById<TextView>(R.id.post_title)
        val post_description = intemView.findViewById<TextView>(R.id.post_description)
        val more = intemView.findViewById<ImageView>(R.id.more)

        more.setOnClickListener {
            val popupMenu = PopupMenu(mContext, more)
            popupMenu.menuInflater.inflate(R.menu.list_popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { it ->
                when (it.itemId) {
                    R.id.show -> {
                        val intentToPostDetailsActivity = Intent(mContext, PostDetailsActivity::class.java)
                        mContext.startActivity(intentToPostDetailsActivity)
                        true
                    }
                    R.id.delete -> {
                        values.removeAt(position)
                        notifyDataSetChanged()
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }

        post_title.text = post.post_title
        post_description.text = post.post_description
        return intemView
    }
}