package com.example.hackerthon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.hackerthon.databinding.ActivityDetailBinding
import com.example.hackerthon.response.Data

class DetailActivity : AppCompatActivity() {

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        performDataBinding()

        val imageURL = intent.getStringExtra("image")
        val title = intent.getStringExtra("title")
        val phone = intent.getStringExtra("phone")
        val star = intent.getStringExtra("star")
        val discript = intent.getStringExtra("discript")
        detailImageView = findViewById(R.id.detailImageView)

        binding.titleTextView.text = title
        binding.discript.text = discript
        binding.phone.text = phone
        binding.starTv.text = star

        bind(imageURL.toString())
    }

    fun bind(url: String) {
        Glide.with(this)
            .load(url)
            .into(detailImageView)
    }

    private fun performDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        binding.detailViewModel = detailViewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }
}