package vergecurrency.vergewallet.view.ui.activity.base;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import vergecurrency.vergewallet.excpetion.DefaultExceptionHandler;

public class VergeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        this.initExceptionHandler();
    }

    private void initExceptionHandler() {
        if (!DefaultExceptionHandler.initialized()) {
            DefaultExceptionHandler.init(getApplicationContext());
        }
        Thread.setDefaultUncaughtExceptionHandler(DefaultExceptionHandler.getInstance());
    }
}
