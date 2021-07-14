package com.example.hackerthon

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hackerthon.response.Data

class Adapter(private val items: ArrayList<Data>): RecyclerView.Adapter<Adapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val imageViewIntroduceImage: ImageView = view.findViewById(R.id.imageData)
        val name = view.findViewById<TextView>(R.id.title)
        val content = view.findViewById<TextView>(R.id.content)
        val star = view.findViewById<TextView>(R.id.star)

        fun bind(url: String) {
            Glide.with(itemView)
                .load(url)
                .into(imageViewIntroduceImage)
        }

        fun setStar(star: Double): String {
            var doubleStar = "%.2f".format(star)
            return "★ ${doubleStar}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position].image)
        holder.name.text = items[position].name
        holder.content.text = items[position].discript
        holder.star.text = holder.setStar(items[position].star)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("title", items[position].name)
            intent.putExtra("discript", items[position].discript)
            intent.putExtra("phone", items[position].phone)
            intent.putExtra("star", holder.setStar(items[position].star))
            intent.putExtra("image", items[position].image)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }


    override fun getItemCount(): Int = items.size
}