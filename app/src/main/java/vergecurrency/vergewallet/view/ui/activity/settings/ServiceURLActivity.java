package vergecurrency.vergewallet.view.ui.activity.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.view.base.BaseActivity;
import vergecurrency.vergewallet.viewmodel.ServiceURLViewModel;

public class ServiceURLActivity extends BaseActivity {

	private EditText vwsValue;
	private ServiceURLViewModel mViewModel;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service_url);

		mViewModel = ViewModelProviders.of(this).get(ServiceURLViewModel.class);

		initComponents();
	}

	private void initComponents() {
		vwsValue = findViewById(R.id.serviceurl_field);
		updateVwsValueField();

		TextView defaultUrlView = findViewById(R.id.service_url_set_default);
		Button saveURLButton = findViewById(R.id.service_url_button_save);

		defaultUrlView.setOnClickListener(setDefaultUrlListener());
		saveURLButton.setOnClickListener(saveURLButtonListener());
	}

	private View.OnClickListener saveURLButtonListener() {
		return v -> {
			mViewModel.setNewServiceURL(vwsValue.getText().toString());
			Toast.makeText(this, "The Service URL has been updated successfully.", Toast.LENGTH_SHORT).show();
			finish();
		};
	}

	private View.OnClickListener setDefaultUrlListener() {
		return v -> {
			mViewModel.setDefaultServiceURL();
			Toast.makeText(this, "The Service URL has been set to its default URL.", Toast.LENGTH_SHORT).show();
			updateVwsValueField();
		};

	}

	private void updateVwsValueField() {
		vwsValue.setText(mViewModel.getCurrentServiceURL());
	}
}
