package vergecurrency.vergewallet.view.ui.activity.error;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import vergecurrency.vergewallet.R;

import static vergecurrency.vergewallet.excpetion.DefaultUncaughtExceptionHandler.EXTRA_ERROR_REPORT;

public class ErrorRecoveryActivity extends AppCompatActivity {
    private CharSequence errorReport;
    Button button;
    TextView error_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        errorReport = intent.getStringExtra(EXTRA_ERROR_REPORT);
        setContentView(R.layout.activity_error_report);
        this.instantiateButtons();
        this.instantiateTextView();
    }

    private void instantiateTextView() {
        error_description = findViewById(R.id.error_description);
        error_description.setText("If you would like to report an issue with our application, please create a new issue on \n https://github.com/vergecurrency/vDroid/issues");
    }

    private void instantiateButtons() {
        button = findViewById(R.id.button_create_wallet);
        button.setText(HtmlCompat.fromHtml(getResources().getString(R.string.error_report_copy_button), HtmlCompat.FROM_HTML_MODE_LEGACY), Button.BufferType.SPANNABLE);
        button.setOnClickListener(copyOnClickListener());
        button = findViewById(R.id.button_restore_wallet);
        button.setText(HtmlCompat.fromHtml(getResources().getString(R.string.error_report_exit_button), HtmlCompat.FROM_HTML_MODE_LEGACY), Button.BufferType.SPANNABLE);
        button.setOnClickListener(exitOnClickListener());
    }


    private Button.OnClickListener copyOnClickListener() {
        return v -> {
            copyErrorReport();
            Toast toast = Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 25);
            toast.show();
        };
    }

    private Button.OnClickListener exitOnClickListener() {
        return v -> {
            exit();
        };
    }

    private void copyErrorReport() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(getApplication().CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Verge Error Report", errorReport);
        clipboard.setPrimaryClip(clip);
    }

    private void exit() {
        finish();
        System.exit(2);
    }
}
