package com.example.hackerthon.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.hackerthon.MainViewModel
import com.example.hackerthon.R
import com.example.hackerthon.Retrofit
import com.example.hackerthon.databinding.FragmentMapBinding
import com.example.hackerthon.response.Data
import com.example.hackerthon.response.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapPointBounds
import net.daum.mf.map.api.MapView

const val DEFAULT_X = 128.41357542226126
const val DEFAULT_Y = 35.663259715391845

class MapFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: FragmentMapBinding

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_map, container, false)

        performDataBinding()
        val mapView = MapView(activity)

        val mapViewContainer: ViewGroup = v.findViewById(R.id.mapView)
        mapViewContainer.addView(mapView)


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


        return v
    }

    private fun performDataBinding() {
        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_map)
        mainViewModel = ViewModelProvider(activity as ViewModelStoreOwner)[MainViewModel::class.java]
        binding.mapViewModel = mainViewModel
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