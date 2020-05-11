package vergecurrency.vergewallet.view.ui.activity.settings

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.view.base.BaseActivity
import vergecurrency.vergewallet.viewmodel.ServiceURLViewModel

class ServiceURLActivity : BaseActivity() {

    private var vwsValue: EditText? = null
    private var mViewModel: ServiceURLViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_url)

        mViewModel = ViewModelProvider(this).get(ServiceURLViewModel::class.java)

        initComponents()
    }

    private fun initComponents() {
        vwsValue = findViewById(R.id.serviceurl_field)
        updateVwsValueField()

        val defaultUrlView = findViewById<TextView>(R.id.service_url_set_default)
        val saveURLButton = findViewById<Button>(R.id.use_biometrics_button_save)

        defaultUrlView.setOnClickListener(setDefaultUrlListener())
        saveURLButton.setOnClickListener(saveURLButtonListener())
    }

    private fun saveURLButtonListener(): View.OnClickListener {
        return View.OnClickListener {
            mViewModel!!.setNewServiceURL(vwsValue!!.text.toString())
            Toast.makeText(this, "The Service URL has been updated successfully.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun setDefaultUrlListener(): View.OnClickListener {
        return View.OnClickListener {
            mViewModel!!.setDefaultServiceURL()
            Toast.makeText(this, "The Service URL has been set to its default URL.", Toast.LENGTH_SHORT).show()
            updateVwsValueField()
        }

    }

    private fun updateVwsValueField() {
        vwsValue!!.setText(mViewModel!!.currentServiceURL)
    }
}
