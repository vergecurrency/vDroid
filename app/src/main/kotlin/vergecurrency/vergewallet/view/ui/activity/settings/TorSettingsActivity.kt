package vergecurrency.vergewallet.view.ui.activity.settings

import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_tor_settings.*
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.service.model.PreferencesManager
import vergecurrency.vergewallet.service.model.network.layers.TorManager
import vergecurrency.vergewallet.view.base.BaseActivity
import vergecurrency.vergewallet.viewmodel.TorSettingsViewModel

class TorSettingsActivity : BaseActivity() {

    internal lateinit var map: MapView
    internal lateinit var ip: TextView
    internal lateinit var switch: SwitchCompat

    internal lateinit var mViewModel: TorSettingsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tor_settings)

        Configuration.getInstance().load(applicationContext, PreferencesManager.preferences);

        mViewModel = ViewModelProviders.of(this).get(TorSettingsViewModel::class.java)

        //Get a handler to execute stuff only after setting the content view
        val handler = Handler()


        handler.postDelayed({ this.initComponents() }, 100)
    }


    private fun initComponents() {

        switch = tor_settings_obfuscate
        switch.isChecked = PreferencesManager.usingTor

        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                TorManager.startTor(this)
            }
            PreferencesManager.usingTor = isChecked

            ip.text = mViewModel.updateAndReturnIpAddress()
            initMap()

        }

        ip = tor_settings_ip_address
        ip.text = mViewModel.ipAddress

        initMap()
    }

    private fun initMap() {
        //Create the map
        map = tor_settings_map
        map.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)

        //Don't want ugly zoom buttons
        map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
        //I feel ok with multitouch tho
        map.setMultiTouchControls(true)

        initStartPoint()
    }

    private fun initStartPoint() {
        val startPoint = createGeoPoint()
        val mapController = map.controller
        mapController.setZoom(19.0)
        val startMarker = Marker(map)
        //Marker
        startMarker.position = startPoint
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        map.overlays.add(startMarker)
        //Show me where I am baby :)
        mapController.setCenter(startPoint)
    }

    private fun createGeoPoint(): GeoPoint {
        val latlong = mViewModel.updateAndReturnCoordinates()
        //If everything is okay
        if (latlong != "error") {
            val latlongArray = latlong.split(";".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            return GeoPoint(java.lang.Double.parseDouble(latlongArray[0]), java.lang.Double.parseDouble(latlongArray[1]))

        } else
            return GeoPoint(0.0, 0.0)//Otherwise just point to Null Island. See here : https://en.wikipedia.org/wiki/Null_Island


    }




}

