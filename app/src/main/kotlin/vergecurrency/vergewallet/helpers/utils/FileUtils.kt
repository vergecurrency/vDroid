package vergecurrency.vergewallet.helpers.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast

import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.InputStreamReader

import vergecurrency.vergewallet.R

object FileUtils {

    fun saveBitmapToFile(c : Context, bmp: Bitmap) {
        val root = c.externalCacheDirs
        val myDir = File("$root/saved_images")
        Log.i("Directory", "==$myDir")
        myDir.mkdirs()

        val fname = "Image-test" + ".jpg"
        val file = File(myDir, fname)
        if (file.exists()) file.delete()
        try {
            val out = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()

        } catch (e: Exception) {
            //TODO : Catch exception
            e.printStackTrace()
        }

    }

    fun share(c: Context) {
        try {
            val myFile = File("/storage/emulated/0/saved_images/Image-test.jpg")
            val mime = MimeTypeMap.getSingleton()
            val ext = myFile.name.substring(myFile.name.lastIndexOf(".") + 1)
            val type = mime.getMimeTypeFromExtension(ext)
            val sharingIntent = Intent("android.intent.action.SEND")
            sharingIntent.type = type
            sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            val fileUri = FileProvider.getUriForFile(c, c.getString(R.string.file_provider_authority), myFile)
            sharingIntent.putExtra("android.intent.extra.STREAM", fileUri)
            c.startActivity(Intent.createChooser(sharingIntent, "Share using"))
        } catch (e: Exception) {
            //FIXME : This is NOT the way to throw an exception
            Toast.makeText(c, e.message, Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }

    }


    @Throws(Exception::class)
    fun convertStreamToString(`is`: InputStream): String {
        val reader = BufferedReader(InputStreamReader(`is`))
        val sb = StringBuilder()
        var line: String? = reader.readLine()
        while (line != null) {
            sb.append(line).append("\n")
        }
        reader.close()
        return sb.toString()
    }
}
