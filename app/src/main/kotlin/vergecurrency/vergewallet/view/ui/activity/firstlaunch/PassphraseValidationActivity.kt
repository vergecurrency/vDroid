package vergecurrency.vergewallet.view.ui.activity.firstlaunch

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders

import com.omega_r.libs.OmegaCenterIconButton

import vergecurrency.vergewallet.view.base.BaseActivity
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.viewmodel.PassphraseVerificationViewModel


class PassphraseValidationActivity : BaseActivity() {

    private var mViewModel: PassphraseVerificationViewModel? = null
    private var passphraseToValidate: String? = null
    private var passphraseEditText: EditText? = null
    private var validateButton: OmegaCenterIconButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_passphrase_validate)

        passphraseToValidate = intent.getStringExtra("passphrase")

        mViewModel = ViewModelProviders.of(this).get(PassphraseVerificationViewModel::class.java)

        initComponents()
    }


    private fun initComponents() {
        passphraseEditText = findViewById(R.id.passphrase_field_validate)
        passphraseEditText!!.addTextChangedListener(passphraseTextWatcher())
        validateButton = findViewById(R.id.button_passphrase_validate)
        validateButton!!.setOnClickListener(onValidateClick())

    }

    private fun passphraseTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val isAllOk = s.toString() == passphraseToValidate
                validateButton!!.isEnabled = isAllOk
                if (isAllOk) {
                    validateButton!!.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(applicationContext,R.color.verge_colorPrimary))
                } else {
                    validateButton!!.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(applicationContext,R.color.verge_colorAccent))
                }
            }

            override fun afterTextChanged(s: Editable) {

            }
        }
    }

    internal fun onValidateClick(): View.OnClickListener {
        return View.OnClickListener {
            mViewModel!!.setFirstLaunch(false)
            mViewModel!!.setPassphrase(passphraseToValidate!!)
            finish()
            startActivity(Intent(applicationContext, PermissionsActivity::class.java))
        }
    }
}
