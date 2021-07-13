package com.example.hackerthon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.FragmentTransaction
import com.example.hackerthon.fragment.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private val mOnNavigationiItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {item ->
        when(item.itemId){
            R.id.my_review -> {
                supportFragmentManager.beginTransaction().replace(R.id.frameLayout, MyReviewFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.my_like -> {
                supportFragmentManager.beginTransaction().replace(R.id.frameLayout, MyLikeFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.map -> {
                supportFragmentManager.beginTransaction().replace(R.id.frameLayout, MapFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.like -> {
                supportFragmentManager.beginTransaction().replace(R.id.frameLayout, LikeFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.setting -> {
                supportFragmentManager.beginTransaction().replace(R.id.frameLayout, SettingFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationiItemSelectedListener)

    }

}