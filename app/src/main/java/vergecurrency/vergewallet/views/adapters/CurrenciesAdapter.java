package vergecurrency.vergewallet.views.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;

import vergecurrency.vergewallet.structs.Currency;

public class CurrenciesAdapter extends ArrayAdapter<Currency> implements View.OnClickListener{


	public CurrenciesAdapter(@androidx.annotation.NonNull Context context, int resource) {
		super(context, resource);
	}



	
	@Override
	public void onClick(View v) {

	}
}
