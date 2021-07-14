package com.example.hackerthon

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.KeyEvent
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.hackerthon.databinding.ActivityMainBinding
import com.example.hackerthon.databinding.FragmentLikeBinding
import com.example.hackerthon.fragment.*
import com.example.hackerthon.response.Data
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapPointBounds
import net.daum.mf.map.api.MapView
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var mapViewContainer: ViewGroup

    private val mOnNavigationiItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.map -> {
                    mapViewContainer.removeAllViews()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.like -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, LikeFragment()).commitAllowingStateLoss()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.setting -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, SettingFragment()).commitAllowingStateLoss()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        performDataBinding()
        val mapView = MapView(this)

        mapViewContainer= findViewById(R.id.frameLayout)
        mapViewContainer.addView(mapView)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationiItemSelectedListener)

        defaultMarker(mapView)

        binding.searchEditText.setOnKeyListener { _, keyCode, event ->
            if ((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                val call = Retrofit.service.getSearchData( mainViewModel.search.value.toString() )

                call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            mapView.removeAllPOIItems()
                            defaultMarker(mapView)
                            showResult(it.body()?.data!!, mapView)
                        },
                        { Log.e("error", "에러") }
                    )
                true
            } else {
                false
            }
        }
    }

    private fun performDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.mainViewModel = mainViewModel
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

fun showResult(itemList: List<Data>, mMapView: MapView) {
    val mapPointBounds = MapPointBounds()
    for (i in itemList.indices) {
        val item = itemList[i]
        val poiItem = MapPOIItem()
        poiItem.itemName = item.name
        poiItem.tag = i
        if ((item.yPosition != "undefined") || (item.xPosition != "undefined")) {
            val mapPoint =
                MapPoint.mapPointWithGeoCoord(item.yPosition.toDouble(), item.xPosition.toDouble())
            poiItem.mapPoint = mapPoint
            mapPointBounds.add(mapPoint)
            poiItem.markerType = MapPOIItem.MarkerType.RedPin //마커 타입 지정
            poiItem.selectedMarkerType = MapPOIItem.MarkerType.BluePin //마커가 선택되었을때 마커 타입 지정
            poiItem.isCustomImageAutoscale = false
            poiItem.setCustomImageAnchor(0.5f, 1.0f)
            mMapView.addPOIItem(poiItem) //지도에 마커 추가
        }
    }
}