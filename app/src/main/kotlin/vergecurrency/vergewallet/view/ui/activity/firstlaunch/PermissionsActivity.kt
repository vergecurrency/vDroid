package vergecurrency.vergewallet.view.ui.activity.firstlaunch

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_permissions.*
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.helpers.utils.PermissionsUtils
import vergecurrency.vergewallet.view.base.BaseActivity
import vergecurrency.vergewallet.wallet.WalletManager

class PermissionsActivity : BaseActivity() {

    private lateinit var nextButton: Button
    private lateinit var readStorageSwitch: SwitchCompat
    private lateinit var writeStorageSwitch: SwitchCompat
    private lateinit var locationSwitch: SwitchCompat
    //todo : a permission code per request so that we'll get the result
    private var STORAGE_PERMISSION_CODE: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permissions)

        try {
            WalletManager.startWallet()
        } catch (e: Exception) {
            System.err.print(e)
            Toast.makeText(applicationContext, "Impossible to start wallet. Manuel you did some crap", Toast.LENGTH_LONG).show()
        }

        initComponents()

    }


    //TODO : Next stream - test all this.
    private fun initComponents() {
        nextButton = permission_continue
        nextButton.setOnClickListener(nextButtonListener())

        //todo : group in foreach
        readStorageSwitch = permissions_read_storage_switch
        writeStorageSwitch = permissions_write_storage_switch
        locationSwitch = permissions_location_switch

        readStorageSwitch.setOnCheckedChangeListener { _, isChecked ->

            val explanationMessage = "This permission is needed in order to access data related to the wallet and securely stored on the phone storage"

            if (isChecked) {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                    PermissionsUtils.requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, explanationMessage, STORAGE_PERMISSION_CODE, this)

                } else {
                    Toast.makeText(this, "Permission has already been granted.", Toast.LENGTH_SHORT).show()
                }

                readStorageSwitch.isChecked = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED

            }
        }

        writeStorageSwitch.setOnCheckedChangeListener { _, isChecked ->

            val explanationMessage = "This permission is needed in order to access data related to the wallet and securely stored on the phone storage"

            if (isChecked) {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                    PermissionsUtils.requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, explanationMessage, STORAGE_PERMISSION_CODE, this)

                } else {
                    Toast.makeText(this, "Permission has already been granted.", Toast.LENGTH_SHORT).show()
                }

                writeStorageSwitch.isChecked = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED

            }
        }

        locationSwitch.setOnCheckedChangeListener { _, isChecked ->

            val explanationMessage = "This permission is needed in order to access geolocation data to display your location when using Tor"

            if (isChecked) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {

                    PermissionsUtils.requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, explanationMessage, STORAGE_PERMISSION_CODE, this)

                } else {
                    Toast.makeText(this, "Permission has already been granted.", Toast.LENGTH_SHORT).show()
                }

                locationSwitch.isChecked = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_DENIED

            }
        }

    }

    private fun nextButtonListener(): View.OnClickListener {
        return View.OnClickListener {

            finish()
            startActivity(Intent(applicationContext, EndSetupActivity::class.java))
        }

    }

    override fun onBackPressed() {
        //on purpose
    }
}
