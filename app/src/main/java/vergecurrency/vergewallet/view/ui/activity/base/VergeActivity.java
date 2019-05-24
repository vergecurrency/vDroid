package vergecurrency.vergewallet.view.ui.activity.base;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import vergecurrency.vergewallet.excpetion.DefaultExceptionHandler;

public class VergeActivity extends AppCompatActivity {
    protected DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        Thread.setDefaultUncaughtExceptionHandler(exceptionHandler);
    }
}
