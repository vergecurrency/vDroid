package vergecurrency.vergewallet.view.ui.activity.settings;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import vergecurrency.vergewallet.Constants;
import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.service.repository.ApifyService;
import vergecurrency.vergewallet.service.repository.GeocodingService;
import vergecurrency.vergewallet.service.model.network.layers.TorLayerGateway;

public class TorSettingsActivity extends AppCompatActivity {

    MapView map;
    TextView ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tor_settings);


        //Get a handler to execute stuff only after setting the content view
        final Handler handler = new Handler();


        //Handler that waits to view to be displayed before starting tor.
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TorSettingsActivity.this.run();
            }
        }, 500);
    }


    private void run() {
        ip = findViewById(R.id.tor_settings_ip_address);

        //Create the map
        map = findViewById(R.id.tor_settings_map);
        map.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);

        //Don't want ugly zoom buttons
        map.setBuiltInZoomControls(false);
        //I feel ok with multitouch tho
        map.setMultiTouchControls(true);

        //Set a decent zoom level
        IMapController mapController = map.getController();
        mapController.setZoom(9d);

        Marker startMarker = new Marker(map);

        //Try to get lat and long according to IP
        String latlong = getLatLong();
        String[] latlongArray = null;
        GeoPoint startPoint;
        //If everything is okay
        if (!latlong.equals("error")) {
            latlongArray = latlong.split(";");
            startPoint = new GeoPoint(Double.parseDouble(latlongArray[0]), Double.parseDouble(latlongArray[0]));


        }
        //Otherwise just point to Null Island. See here : https://en.wikipedia.org/wiki/Null_Island
        else startPoint = new GeoPoint(0d, 0d);

        //Marker
        startMarker.setPosition(startPoint);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(startMarker);
        //Show me where I am baby :)
        mapController.setCenter(startPoint);
    }

    public String getLatLong() {

        ApifyService adr = new ApifyService();
        GeocodingService idr = new GeocodingService();

        TorLayerGateway tlg = TorLayerGateway.getInstance();
        String IP = adr.readIP(tlg.retrieveDataFromService(Constants.IP_RETRIEVAL_ENDPOINT));
        ip.setText(IP);

        String queryLoc = String.format(Constants.IP_DATA_ENDPOINT, IP);
        return idr.readCoordinates(tlg.retrieveDataFromService(queryLoc));

    }
}

