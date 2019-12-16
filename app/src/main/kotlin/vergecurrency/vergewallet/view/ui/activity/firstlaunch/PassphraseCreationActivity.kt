package vergecurrency.vergewallet.view.ui.activity.firstlaunch

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat

import com.omega_r.libs.OmegaCenterIconButton

import vergecurrency.vergewallet.helpers.utils.ValidationUtils
import vergecurrency.vergewallet.view.base.BaseActivity
import vergecurrency.vergewallet.R

class PassphraseCreationActivity : BaseActivity() {

    private var eightCharsTickImageView: ImageView? = null
    private var eightCharsLabel: TextView? = null
    private var upperLowerCaseTickImageView: ImageView? = null
    private var upperLowerCaseLabel: TextView? = null
    private var specialCharsTickImageView: ImageView? = null
    private var specialCharsLabel: TextView? = null
    private var passphraseEditText: EditText? = null
    private var confirmButton: OmegaCenterIconButton? = null

    private var passphrase: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_passphrase_create)

        initComponents()
    }


    private fun initComponents() {


        eightCharsTickImageView = findViewById(R.id.passphrase_tick_8_chars)
        eightCharsLabel = findViewById(R.id.passphrase_label_8_chars)

        upperLowerCaseTickImageView = findViewById(R.id.passphrase_tick_upper_lower)
        upperLowerCaseLabel = findViewById(R.id.passphrase_label_upper_lower)

        specialCharsTickImageView = findViewById(R.id.passphrase_ticked_special_chars)
        specialCharsLabel = findViewById(R.id.passphrase_label_special_chars)

        confirmButton = findViewById(R.id.passphrase_confirm_button)
        confirmButton!!.setOnClickListener(onConfirmButtonClick())

        passphraseEditText = findViewById(R.id.passphrase_field_enter)
        passphraseEditText!!.addTextChangedListener(passphraseTextChangedListener())
    }

    private fun passphraseTextChangedListener(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                validatePassphrase(s.toString())
            }

            override fun afterTextChanged(s: Editable) {

            }
        }

    }

    private fun validatePassphrase(passphrase: String) {
        val isEightChars = ValidationUtils.isStringEightCharsLong(passphrase)
        val isUpperLowerCase = ValidationUtils.hasStringLowercaseChar(passphrase) && ValidationUtils.hasStringUppercaseChar(passphrase)
        val isSpecialChar = ValidationUtils.isStringContainingSpecialChars(passphrase)

        if (isEightChars) {
            ImageViewCompat.setImageTintList(eightCharsTickImageView!!, ColorStateList.valueOf(ContextCompat.getColor(applicationContext, R.color.material_green_500)))
            eightCharsLabel!!.setTextColor(ContextCompat.getColor(applicationContext, R.color.material_green_500))
        } else {
            ImageViewCompat.setImageTintList(eightCharsTickImageView!!, ColorStateList.valueOf(ContextCompat.getColor(applicationContext, R.color.verge_colorAccent)))
            eightCharsLabel!!.setTextColor(ContextCompat.getColor(applicationContext, R.color.verge_colorAccent))
        }

        if (isUpperLowerCase) {
            ImageViewCompat.setImageTintList(upperLowerCaseTickImageView!!, ColorStateList.valueOf(ContextCompat.getColor(applicationContext, R.color.material_green_500)))
            upperLowerCaseLabel!!.setTextColor(ContextCompat.getColor(applicationContext, R.color.material_green_500))
        } else {
            ImageViewCompat.setImageTintList(upperLowerCaseTickImageView!!, ColorStateList.valueOf(ContextCompat.getColor(applicationContext, R.color.verge_colorAccent)))
            upperLowerCaseLabel!!.setTextColor(ContextCompat.getColor(applicationContext, R.color.verge_colorAccent))
        }

        if (isSpecialChar) {
            ImageViewCompat.setImageTintList(specialCharsTickImageView!!, ColorStateList.valueOf(ContextCompat.getColor(applicationContext, R.color.material_green_500)))
            specialCharsLabel!!.setTextColor(ContextCompat.getColor(applicationContext, R.color.material_green_500))
        } else {
            ImageViewCompat.setImageTintList(specialCharsTickImageView!!, ColorStateList.valueOf(ContextCompat.getColor(applicationContext, R.color.verge_colorAccent)))
            specialCharsLabel!!.setTextColor(ContextCompat.getColor(applicationContext, R.color.verge_colorAccent))
        }
        val isAllOk = isEightChars && isUpperLowerCase && isSpecialChar
        confirmButton!!.isEnabled = isAllOk
        if (isAllOk) {
            confirmButton!!.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(applicationContext, R.color.verge_colorPrimary))
        } else {
            confirmButton!!.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(applicationContext, R.color.verge_colorAccent))
        }


        this.passphrase = passphrase
    }

    private fun onConfirmButtonClick(): View.OnClickListener {
        return View.OnClickListener {
            val i = Intent(applicationContext, PassphraseValidationActivity::class.java)
            i.putExtra("passphrase", passphrase)
            startActivity(i)
        }
    }

}
