package vergecurrency.vergewallet.helpers.utils

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.core.app.ActivityCompat

object PermissionsUtils {

    fun requestPermission(permission: String, explanationMessage: String, PERMISSION_CODE: Int, activity: Activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            AlertDialog.Builder(activity)
                    .setTitle("Permission needed")
                    .setMessage(explanationMessage)
                    .setPositiveButton("Ok") { _, _ ->
                        //Second time it's fired.
                        ActivityCompat.requestPermissions(activity,
                                arrayOf(permission), PERMISSION_CODE)
                    }
                    .setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            dialog?.dismiss()
                        }
                    })
                    .create().show()
        } else {
            //First time it's fired
            ActivityCompat.requestPermissions(activity, arrayOf(permission), PERMISSION_CODE)
        }
    }
}