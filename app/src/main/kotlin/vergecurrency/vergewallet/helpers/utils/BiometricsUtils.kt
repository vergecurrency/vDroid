package vergecurrency.vergewallet.helpers.utils



import android.Manifest
import android.content.Context
import android.os.Build
import androidx.core.hardware.fingerprint.FingerprintManagerCompat

object BiometricsUtils {

    // First step : is minsdk version equals or bigger than P version
    fun isBiometricsOK() : Boolean {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
    }

    // Second step : is there a sensor
    fun isSensorsOk(context: Context) : Boolean {
        val fingerprintManager: FingerprintManagerCompat = FingerprintManagerCompat.from(context)
        return fingerprintManager.isHardwareDetected
    }

    // Third step : is there a fingerprint registered
    fun isFingerPrintRegistered(context :Context ) : Boolean {
        val fingerprintManager: FingerprintManagerCompat = FingerprintManagerCompat.from(context)
        return fingerprintManager.hasEnrolledFingerprints()
    }

    // Forth step : is the fingerprint permission granted?
    fun isPermissionGranted(context : Context) : Boolean {
       return PermissionsUtils.isPermissionGranted(Manifest.permission.USE_FINGERPRINT, context)
    }

}