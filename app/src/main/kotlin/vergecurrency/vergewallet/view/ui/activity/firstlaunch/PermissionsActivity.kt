package vergecurrency.vergewallet.view.ui.activity.firstlaunch

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import kotlinx.android.synthetic.main.activity_permissions.*
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.helpers.utils.PermissionsUtils
import vergecurrency.vergewallet.service.model.PermissionConstruct
import vergecurrency.vergewallet.view.base.BaseActivity
import vergecurrency.vergewallet.wallet.WalletManager

class PermissionsActivity : BaseActivity() {

    private lateinit var nextButton: Button
    private lateinit var readStorageSwitch: SwitchCompat
    private lateinit var writeStorageSwitch: SwitchCompat
    private lateinit var locationSwitch: SwitchCompat
    private lateinit var cameraSwitch: SwitchCompat

    //todo : a permission code per request so that we'll get the result


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

    private fun initPermissionConstructs(): ArrayList<PermissionConstruct> {

        val permsArray: ArrayList<PermissionConstruct> = ArrayList()

        permsArray.add(PermissionConstruct(Manifest.permission.READ_EXTERNAL_STORAGE, 1, "This permission is needed in order to read data related to the wallet and securely stored on the phone storage", readStorageSwitch))
        permsArray.add(PermissionConstruct(Manifest.permission.WRITE_EXTERNAL_STORAGE, 2, "This permission is needed in order to write data related to the wallet and securely stored on the phone storage", writeStorageSwitch))
        permsArray.add(PermissionConstruct(Manifest.permission.ACCESS_COARSE_LOCATION, 3, "This permission is needed in order to access geolocation data to display your location when using Tor", locationSwitch))
        permsArray.add(PermissionConstruct(Manifest.permission.CAMERA, 4, "This permission is needed in order to scan a QR Code containing a Verge Address or a QR-Encoded paper key", cameraSwitch))

        return permsArray
    }


    //TODO : Next stream - test all this.
    private fun initComponents() {

        readStorageSwitch = permissions_read_storage_switch
        writeStorageSwitch = permissions_write_storage_switch
        locationSwitch = permissions_location_switch
        cameraSwitch = permissions_camera_switch

        val permissionsArray = initPermissionConstructs()


        nextButton = permission_continue
        nextButton.setOnClickListener(nextButtonListener())

        //todo : group in foreach

        for (permission in permissionsArray) {

            permission.component.setOnCheckedChangeListener { _, isChecked ->

                if (isChecked) {
                    //TODO : test why it isn't working as wished  - that means that 2 out of 4 permissions are fucking up.
                    if (!PermissionsUtils.isPermissionGranted(permission.permission, this)) {

                        PermissionsUtils.requestPermission(permission.permission, permission.explanationMessage, permission.permissionCode, this)

                    } else {
                        Toast.makeText(this, "Permission has already been granted.", Toast.LENGTH_SHORT).show()
                    }

                    permission.component.isChecked = PermissionsUtils.isPermissionGranted(permission.permission, this)

                }
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
