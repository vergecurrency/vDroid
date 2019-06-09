package vergecurrency.vergewallet.view.ui.activity.settings;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.viewmodel.TorSettingsViewModel;

public class TorSettingsActivity extends AppCompatActivity {

	MapView map;
	TextView ip;

	TorSettingsViewModel mViewModel;

	//TODO : Oh boy you don't know all the things you have to do here!

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tor_settings);

		mViewModel = ViewModelProviders.of(this).get(TorSettingsViewModel.class);

		//Get a handler to execute stuff only after setting the content view
		final Handler handler = new Handler();

		//Handler that waits to view to be displayed before starting tor.
		handler.postDelayed(this::initComponents, 500);
	}


	private void initComponents() {
		ip = findViewById(R.id.tor_settings_ip_address);
		ip.setText(mViewModel.getIPAddress());
		initMap();
	}

	private void initMap() {
		//Create the map
		map = findViewById(R.id.tor_settings_map);
		map.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);

		//Don't want ugly zoom buttons
		map.setBuiltInZoomControls(false);
		//I feel ok with multitouch tho
		map.setMultiTouchControls(true);

		GeoPoint startPoint = createGeoPoint();

		initStartPoint(startPoint);
	}

	private GeoPoint createGeoPoint() {
		String latlong = mViewModel.getCoordinates();
		//If everything is okay
		if (!latlong.equals("error")) {
			String[] latlongArray = latlong.split(";");
			return new GeoPoint(Double.parseDouble(latlongArray[0]), Double.parseDouble(latlongArray[0]));

		}
		//Otherwise just point to Null Island. See here : https://en.wikipedia.org/wiki/Null_Island
		else return new GeoPoint(0d, 0d);


	}

	private void initStartPoint(GeoPoint startPoint) {
		IMapController mapController = map.getController();
		mapController.setZoom(9d);
		Marker startMarker = new Marker(map);
		//Marker
		startMarker.setPosition(startPoint);
		startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
		map.getOverlays().add(startMarker);
		//Show me where I am baby :)
		mapController.setCenter(startPoint);
	}


}

