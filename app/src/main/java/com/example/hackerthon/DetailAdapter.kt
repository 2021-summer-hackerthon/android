package com.example.hackerthon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hackerthon.response.Comment

class DetailAdapter(private val comments: ArrayList<Comment>): RecyclerView.Adapter<DetailAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val userName = view.findViewById<TextView>(R.id.userName)
        val userComment = view.findViewById<TextView>(R.id.userComment)
        val commnetStar = view.findViewById<TextView>(R.id.commentStar)

        fun setStar(star: Double): String {
            var doubleStar = "%.2f".format(star)
            return "★ ${doubleStar}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.userName.text = comments[position].user.name
        holder.userComment.text = comments[position].comment
        holder.commnetStar.text = holder.setStar(comments[position].star)
    }

    override fun getItemCount(): Int = comments.size
}