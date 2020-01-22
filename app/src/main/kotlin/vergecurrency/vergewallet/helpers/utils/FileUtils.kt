package vergecurrency.vergewallet.helpers.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.content.FileProvider
import vergecurrency.vergewallet.R
import java.io.*

object FileUtils {

    fun saveImage(c: Context, bmp: Bitmap) {


        //c.filesDir = the folder of the app.
        val myDir = File(c.filesDir,"images")

        myDir.mkdirs()

        val addressFileName = "Verge_Generated" + ".jpg"


        val file = File(myDir, addressFileName)

        if (file.exists()) {
            file.delete()
        }
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

            val myDir = File(c.filesDir,"images")
            val addressFileName = "Verge_Generated" + ".jpg"

            val myFile =  File(myDir, addressFileName)

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
