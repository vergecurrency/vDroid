package vergecurrency.vergewallet.view.ui.activity.error;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.text.HtmlCompat;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.view.ui.activity.base.VergeActivity;

import static vergecurrency.vergewallet.excpetion.DefaultExceptionHandler.EXTRA_ERROR_CODE;
import static vergecurrency.vergewallet.excpetion.DefaultExceptionHandler.EXTRA_ERROR_MESSAGE;
import static vergecurrency.vergewallet.excpetion.DefaultExceptionHandler.EXTRA_ERROR_REPORT;

public class ErrorRecoveryActivity extends VergeActivity {
    private CharSequence errorReport;
    private CharSequence errorMessage;
    private int errorCode;
    Button button;
    TextView error_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        errorReport = intent.getStringExtra(EXTRA_ERROR_REPORT);
        errorMessage = intent.getStringExtra(EXTRA_ERROR_MESSAGE);
        errorCode = intent.getIntExtra(EXTRA_ERROR_CODE, 0);
        setContentView(R.layout.activity_error_report);
        this.instantiateButtons();
        this.instantiateTextView();
    }

    private void instantiateTextView() {
        error_description = findViewById(R.id.error_description);
        if (errorCode != 0 && errorMessage != null) {
            error_description.setText(String.join(" : ", Integer.toString(errorCode), errorMessage));
        } else {
            error_description.setText("An unexpected error occurred");
        }

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
            toast.setGravity(Gravity.TOP,0,75);
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
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
