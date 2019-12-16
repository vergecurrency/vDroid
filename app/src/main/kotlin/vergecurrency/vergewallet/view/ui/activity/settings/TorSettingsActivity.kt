package vergecurrency.vergewallet.view.ui.activity.settings

import android.os.Bundle
import android.os.Handler
import android.widget.TextView

import androidx.lifecycle.ViewModelProviders

import org.osmdroid.api.IMapController
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

import vergecurrency.vergewallet.view.base.BaseActivity
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.viewmodel.TorSettingsViewModel

class TorSettingsActivity : BaseActivity() {

    internal lateinit var map: MapView
    internal lateinit var ip: TextView

    internal lateinit var mViewModel: TorSettingsViewModel

    //TODO : Oh boy you don't know all the things you have to do here!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tor_settings)

        mViewModel = ViewModelProviders.of(this).get(TorSettingsViewModel::class.java)

        //Get a handler to execute stuff only after setting the content view
        val handler = Handler()

        //Handler that waits to view to be displayed before starting tor.
        handler.postDelayed({ this.initComponents() }, 500)
    }


    private fun initComponents() {
        ip = findViewById(R.id.tor_settings_ip_address)
        //ip.setText(mViewModel.getIPAddress())
        initMap()
    }

    private fun initMap() {
        //Create the map
        map = findViewById(R.id.tor_settings_map)
        map.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)

        //Don't want ugly zoom buttons
        map.setBuiltInZoomControls(false)
        //I feel ok with multitouch tho
        map.setMultiTouchControls(true)

        val startPoint = createGeoPoint()

        initStartPoint(startPoint)
    }

    private fun createGeoPoint(): GeoPoint {
        //val latlong = mViewModel.getCoordinates()
        val latlong = "error"
        //If everything is okay
        if (latlong != "error") {
            val latlongArray = latlong.split(";".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            return GeoPoint(java.lang.Double.parseDouble(latlongArray[0]), java.lang.Double.parseDouble(latlongArray[0]))

        } else
            return GeoPoint(0.0, 0.0)//Otherwise just point to Null Island. See here : https://en.wikipedia.org/wiki/Null_Island


    }

    private fun initStartPoint(startPoint: GeoPoint) {
        val mapController = map.controller
        mapController.setZoom(9.0)
        val startMarker = Marker(map)
        //Marker
        startMarker.position = startPoint
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        map.overlays.add(startMarker)
        //Show me where I am baby :)
        mapController.setCenter(startPoint)
    }


}

