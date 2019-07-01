package vergecurrency.vergewallet.view.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vergecurrency.vergewallet.helpers.utils.ThemeUtils;
import vergecurrency.vergewallet.service.model.PreferencesManager;

public class BaseFragment extends Fragment {
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		ThemeUtils.setTheme(PreferencesManager.getCurrentTheme(),getContext(),false);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
}
