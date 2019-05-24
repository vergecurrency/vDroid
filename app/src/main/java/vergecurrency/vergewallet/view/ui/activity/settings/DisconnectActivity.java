package vergecurrency.vergewallet.view.ui.activity.settings;

import android.os.Bundle;
import androidx.annotation.Nullable;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.view.ui.activity.base.VergeActivity;

public class DisconnectActivity extends VergeActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disconnect);
    }
}
