package vergecurrency.vergewallet.utilities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import androidx.core.content.FileProvider;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import vergecurrency.vergewallet.R;

public final class FileUtils {

	public static void saveBitmapToFile(Bitmap bmp) {
		String root = Environment.getExternalStorageDirectory().getAbsolutePath();
		File myDir = new File(root + "/saved_images");
		Log.i("Directory", "==" + myDir);
		myDir.mkdirs();

		String fname = "Image-test" + ".jpg";
		File file = new File(myDir, fname);
		if (file.exists()) file.delete();
		try {
			FileOutputStream out = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();

		} catch (Exception e) {
			//TODO : Catch exception
			e.printStackTrace();
		}
	}

	public static void share(Context c) {
		try {
			File myFile = new File("/storage/emulated/0/saved_images/Image-test.jpg");
			MimeTypeMap mime = MimeTypeMap.getSingleton();
			String ext = myFile.getName().substring(myFile.getName().lastIndexOf(".") + 1);
			String type = mime.getMimeTypeFromExtension(ext);
			Intent sharingIntent = new Intent("android.intent.action.SEND");
			sharingIntent.setType(type);
			sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			Uri fileUri = FileProvider.getUriForFile(c, c.getString(R.string.file_provider_authority),myFile);
			sharingIntent.putExtra("android.intent.extra.STREAM", fileUri);
			c.startActivity(Intent.createChooser(sharingIntent, "Share using"));
		} catch (Exception e) {
			//FIXME : This is NOT the way to throw an exception
			Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}
}
