package com.example.hackerthon.fragment

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.hackerthon.MainViewModel
import com.example.hackerthon.R
import com.example.hackerthon.Retrofit
import com.example.hackerthon.databinding.FragmentMapBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

const val REST_API_KEY = "KakaoAK 4fedf3220b69eb93f08e2acf15d03307"
const val DEFAULT_X = 128.41357542226126
const val DEFAULT_Y = 35.663259715391845


class MapFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: FragmentMapBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_map, container, false)

        val mapView = MapView(context)
        defaultMarker(mapView)

        val mapViewContainer: ViewGroup = v.findViewById(R.id.mapView)
        mapViewContainer.addView(mapView)
        performDataBinding()

        binding.searchEditText.setOnKeyListener{ _, keyCode, event ->
            if ((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                val call = Retrofit.kakaoAPI.getSearchKeyword(
                    REST_API_KEY,
                    mainViewModel.search.value.toString()
                )

                call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            mapView.removeAllPOIItems()
                            defaultMarker(mapView)
                            showResult(it.documents, mapView)
                        },
                        { Log.e("error", "에러") }
                    )
                true
            } else {
                false
            }}


        return v
    }

    private fun performDataBinding() {
        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_map)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.map = mainViewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }
}

fun defaultMarker(mapView: MapView) {
    val mapPoint: MapPoint = MapPoint.mapPointWithGeoCoord(DEFAULT_Y, DEFAULT_X)
    mapView.setMapCenterPoint(mapPoint, true)

    val marker = MapPOIItem()
    marker.itemName = "대구소프트웨어마이스터고등학교"
    marker.mapPoint = mapPoint
    marker.markerType = MapPOIItem.MarkerType.BluePin
    marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin
    mapView.addPOIItem(marker)
}