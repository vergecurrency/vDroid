package vergecurrency.vergewallet.view.ui.activity.settings;

import android.os.Bundle;
import android.widget.ListView;

import androidx.lifecycle.ViewModelProviders;

import vergecurrency.vergewallet.view.base.BaseActivity;
import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.view.adapter.LanguagesAdapter;
import vergecurrency.vergewallet.viewmodel.LanguagesViewModel;

public class ChooseLanguageActivity extends BaseActivity {


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_language_change);


		LanguagesViewModel mViewModel = ViewModelProviders.of(this).get(LanguagesViewModel.class);

		ListView languagesList = findViewById(R.id.activity_change_language_listview);
		languagesList.setAdapter(new LanguagesAdapter(this, mViewModel.getLanguages()));

	}
}
