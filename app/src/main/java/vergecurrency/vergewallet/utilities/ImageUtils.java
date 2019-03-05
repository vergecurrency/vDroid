package vergecurrency.vergewallet.utilities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

public final class ImageUtils {

	public ImageUtils() {

	}

	/**
	 * Makes a color transparent on a given Bitmap
	 * @param bit the given bitmap
	 * @param transparentColor the color to made transparent
	 * @return the modified Bitmap
	 */
	public static Bitmap makeTransparent(Bitmap bit, int transparentColor) {
		int width =  bit.getWidth();
		int height = bit.getHeight();
		Bitmap myBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		int [] allpixels = new int [ myBitmap.getHeight()*myBitmap.getWidth()];
		bit.getPixels(allpixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(),myBitmap.getHeight());
		myBitmap.setPixels(allpixels, 0, width, 0, 0, width, height);

		for(int i =0; i<myBitmap.getHeight()*myBitmap.getWidth();i++){
			if( allpixels[i] == transparentColor)

				allpixels[i] = Color.alpha(Color.TRANSPARENT);
		}

		myBitmap.setPixels(allpixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(), myBitmap.getHeight());
		return myBitmap;
	}

	public static Bitmap invertColors(Bitmap bitmap) {
		int length = bitmap.getWidth()*bitmap.getHeight();
		int[] array = new int[length];
		bitmap.getPixels(array,0,bitmap.getWidth(),0,0,bitmap.getWidth(),bitmap.getHeight());
		for (int i=0;i<length;i++){
			// If the bitmap is in ARGB_8888 format
			if (array[i] == 0xff000000){
				array[i] = 0xffffffff;
			}
		}
	bitmap.setPixels(array,0,bitmap.getWidth(),0,0,bitmap.getWidth(),bitmap.getHeight());
	return bitmap;
	}

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
				.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	public static Bitmap convertLayoutToBitmap(View v){

		v.setDrawingCacheEnabled(true);
		v.buildDrawingCache();
		Bitmap result = v.getDrawingCache();

		return result;
	}



}
