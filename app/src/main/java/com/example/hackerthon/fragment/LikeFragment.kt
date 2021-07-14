package com.example.hackerthon.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.example.hackerthon.MainActivity
import com.example.hackerthon.MainViewModel
import com.example.hackerthon.R
import com.example.hackerthon.databinding.FragmentLikeBinding
import com.example.hackerthon.databinding.FragmentMapBinding


class LikeFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: FragmentLikeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_like, container, false)
        performDataBinding()

        mainViewModel.getByStarData()

        return v
    }

    private fun performDataBinding() {
        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_like)
        mainViewModel = ViewModelProvider(activity as ViewModelStoreOwner)[MainViewModel::class.java]
        binding.likeViewModel = mainViewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }
}
