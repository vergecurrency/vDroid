package vergecurrency.vergewallet.view.ui.activity.error

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.HtmlCompat
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.exception.DefaultUncaughtExceptionHandler
import vergecurrency.vergewallet.view.base.BaseActivity

class ErrorRecoveryActivity : BaseActivity() {
    private var errorReport: CharSequence? = null
    private lateinit var button: Button
    private lateinit var error_description: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        errorReport = intent.getStringExtra(DefaultUncaughtExceptionHandler.EXTRA_ERROR_REPORT)
        setContentView(R.layout.activity_error_report)
        this.instantiateButtons()
        this.instantiateTextView()
    }

    private fun instantiateTextView() {
        error_description = findViewById(R.id.error_description)
        error_description.text = "If you would like to report an issue with our application, please create a new issue on \n https://github.com/vergecurrency/vDroid/issues"
    }

    private fun instantiateButtons() {
        button = findViewById(R.id.button_create_wallet)
        button.setText(HtmlCompat.fromHtml(resources.getString(R.string.error_report_copy_button), HtmlCompat.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE)
        button.setOnClickListener(copyOnClickListener())
        button = findViewById(R.id.button_restore_wallet)
        button.setText(HtmlCompat.fromHtml(resources.getString(R.string.error_report_exit_button), HtmlCompat.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE)
        button.setOnClickListener(exitOnClickListener())
    }


    private fun copyOnClickListener(): View.OnClickListener {
        return View.OnClickListener {
            copyErrorReport()
            val toast = Toast.makeText(applicationContext, "Copied to clipboard", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP, 0, 25)
            toast.show()
        }
    }

    private fun exitOnClickListener(): View.OnClickListener {
        return View.OnClickListener { exit() }
    }

    private fun copyErrorReport() {
        val clipboard = getSystemService(Application.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Verge Error Report", errorReport)
        clipboard.setPrimaryClip(clip)
    }

    private fun exit() {
        finish()
        System.exit(2)
    }
}
