package com.mrright.map.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.mrright.map.R
import com.mrright.map.databinding.ActivityMapsBinding
import com.mrright.map.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.min

@AndroidEntryPoint
class MapsActivity : AppCompatActivity() {

    private lateinit var bind: ActivityMapsBinding

    private lateinit var mapFragment: SupportMapFragment
    private lateinit var officeAdapter: OfficeAdapter

    private val viewModel: MapsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(bind.root)


        mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        initViews()
        observeData()

    }

    private fun observeData() {
        viewModel.officeDetails.observe(this) {
            if (!it.isNullOrEmpty()) {
                officeAdapter.submitList(it)
                val markers = mutableMapOf<String, Marker?>()
                val latLngBounds = LatLngBounds.builder()

                val callback = OnMapReadyCallback { map ->

                    it.forEachIndexed { i, it ->

                        if (!it.latitute.isNullOrBlank() && !it.longitute.isNullOrBlank()) {
                            val latLng = LatLng(it.latitute.toDouble(), it.longitute.toDouble())
                            val options = MarkerOptions().apply {
                                position(latLng)
                                title(it.officeName)
                                snippet(it.officeName)
                            }
                            markers[it.officeName ?: i.toString()] = map.addMarker(options)
                            latLngBounds.include(latLng)
                        }

                    }

                    val width = resources?.displayMetrics?.widthPixels ?: 0
                    val height = resources?.displayMetrics?.heightPixels ?: 0
                    val minMetric = min(width, height)
                    val padding = minMetric.div(10)


                    map.animateCamera(
                        CameraUpdateFactory.newLatLngBounds(
                            latLngBounds.build(), padding,
                        )
                    )
                }
                mapFragment.getMapAsync(callback)
            }

        }
    }


    private fun initViews() {

        officeAdapter = OfficeAdapter {
            startActivity(it)
        }
        bind.rvOffices.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(context)
            adapter = officeAdapter
        }
        bind.toolbar.setNavigationOnClickListener {
            toast("On Back Pressed")
        }
    }

}