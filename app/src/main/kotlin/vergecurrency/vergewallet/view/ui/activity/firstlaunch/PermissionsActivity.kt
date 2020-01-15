package vergecurrency.vergewallet.view.ui.activity.firstlaunch

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_permissions.*
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.helpers.utils.PermissionsUtils
import vergecurrency.vergewallet.service.model.PermissionConstruct
import vergecurrency.vergewallet.view.base.BaseActivity
import vergecurrency.vergewallet.viewmodel.PermissionsViewModel

class PermissionsActivity : BaseActivity() {

    private lateinit var nextButton: Button
    private lateinit var readStorageSwitch: SwitchCompat
    private lateinit var writeStorageSwitch: SwitchCompat
    private lateinit var locationSwitch: SwitchCompat
    private lateinit var cameraSwitch: SwitchCompat


    private lateinit var permissionsArray: ArrayList<PermissionConstruct>

    private var mViewModel: PermissionsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permissions)

        initComponents()

        mViewModel = ViewModelProviders.of(this).get(PermissionsViewModel::class.java)
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

        permissionsArray = initPermissionConstructs()


        nextButton = permission_continue
        nextButton.setOnClickListener(nextButtonListener())

        for (permission in permissionsArray) {

            permission.component.setOnCheckedChangeListener { _, isChecked ->

                if (isChecked) {
                    if (!PermissionsUtils.isPermissionGranted(permission.permission, this)) {

                        PermissionsUtils.requestPermission(permission.permission, permission.explanationMessage, permission.permissionCode, this)

                    } else {
                        Toast.makeText(this, "Permission has already been granted.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    private fun nextButtonListener(): View.OnClickListener {
        return View.OnClickListener {

            finish()
            mViewModel!!.setFirstLaunch(false)
            startActivity(Intent(applicationContext, EndSetupActivity::class.java))
        }

    }

    override fun onBackPressed() {
        //on purpose
        Toast.makeText(this, "Nope.", Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when (requestCode) {
            //this should be 1
            permissionsArray.get(0).permissionCode -> permissionsArray.get(0).component.isChecked = PermissionsUtils.isPermissionGranted(permissionsArray.get(0).permission, this)
            permissionsArray.get(1).permissionCode -> permissionsArray.get(1).component.isChecked = PermissionsUtils.isPermissionGranted(permissionsArray.get(1).permission, this)
            permissionsArray.get(2).permissionCode -> permissionsArray.get(2).component.isChecked = PermissionsUtils.isPermissionGranted(permissionsArray.get(2).permission, this)
            permissionsArray.get(3).permissionCode -> permissionsArray.get(3).component.isChecked = PermissionsUtils.isPermissionGranted(permissionsArray.get(3).permission, this)
        }

    }

}


