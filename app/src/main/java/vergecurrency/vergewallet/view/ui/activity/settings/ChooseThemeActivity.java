package vergecurrency.vergewallet.view.ui.activity.settings;

import android.os.Bundle;
import android.widget.ListView;

import androidx.lifecycle.ViewModelProviders;

import vergecurrency.vergewallet.view.base.BaseActivity;
import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.view.adapter.ThemesAdapter;
import vergecurrency.vergewallet.viewmodel.ThemesViewModel;

public class ChooseThemeActivity extends BaseActivity {


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_theme_change);

		ThemesViewModel mViewModel = ViewModelProviders.of(this).get(ThemesViewModel.class);

		ListView themesList = findViewById(R.id.activity_change_theme_listview);
		themesList.setAdapter(new ThemesAdapter(this, mViewModel.getThemes()));

	}

}
