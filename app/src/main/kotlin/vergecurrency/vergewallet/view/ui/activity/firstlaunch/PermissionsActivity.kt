package vergecurrency.vergewallet.view.ui.activity.firstlaunch

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_permissions.*
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.view.base.BaseActivity
import vergecurrency.vergewallet.view.ui.activity.WalletActivity
import vergecurrency.vergewallet.wallet.WalletManager

class PermissionsActivity : BaseActivity() {

    private lateinit var nextButton: Button
    private lateinit var storageSwitch: SwitchCompat
    private lateinit var locationSwitch: SwitchCompat

    private var STORAGE_PERMISSION_CODE: Int = 1
    private var LOCATION_PERMISSION_CODE: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permissions)

        try {
            WalletManager.startWallet()
        } catch (e: Exception) {
            System.err.print(e)
            Toast.makeText(applicationContext, "Impossible to start wallet. Manuel you did some crap", Toast.LENGTH_LONG).show()
        }


    }

    private fun init() {
        nextButton = permission_continue
        nextButton.setOnClickListener(nextButtonListener())

        storageSwitch = permissions_storage_switch
        locationSwitch = permissions_location_switch

        storageSwitch.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    requestStoragePermission()
                } else {
                    Toast.makeText(this, "Permission has already granted.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        /*
                switch.isChecked = PreferencesManager.usingTor

        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                TorManager.startTor(this)
            }
            PreferencesManager.usingTor = isChecked

            ip.text = mViewModel.updateAndReturnIpAddress()
            initMap()

        }
         */
    }
    //TODO : get permission + explanation argument to reuse for every permission, then move to an utility class.
    private fun requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed in order to access data related to the wallet and securely stored on the phone storage")
                    .setPositiveButton("Ok", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            ActivityCompat.requestPermissions(this@PermissionsActivity,
                                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
                        }
                    })
                    .setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            dialog?.dismiss()
                        }
                    })
                    .create().show()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
        }
    }

    private fun nextButtonListener(): View.OnClickListener {
        return View.OnClickListener {
            finish()

            startActivity(Intent(applicationContext, WalletActivity::class.java))
        }

    }

    override fun onBackPressed() {
        //on purpose
    }
}
