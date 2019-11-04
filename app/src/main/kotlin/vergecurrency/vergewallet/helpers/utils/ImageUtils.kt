package vergecurrency.vergewallet.helpers.utils

import android.graphics.*
import android.view.View

object ImageUtils {

    /**
     * Makes a color transparent on a given Bitmap
     * @param bit the given bitmap
     * @param transparentColor the color to made transparent
     * @return the modified Bitmap
     */
    fun makeTransparent(bit: Bitmap, transparentColor: Int): Bitmap {
        val width = bit.width
        val height = bit.height
        val myBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val allpixels = IntArray(myBitmap.height * myBitmap.width)
        bit.getPixels(allpixels, 0, myBitmap.width, 0, 0, myBitmap.width, myBitmap.height)
        myBitmap.setPixels(allpixels, 0, width, 0, 0, width, height)

        for (i in 0 until myBitmap.height * myBitmap.width) {
            if (allpixels[i] == transparentColor)

                allpixels[i] = Color.alpha(Color.TRANSPARENT)
        }

        myBitmap.setPixels(allpixels, 0, myBitmap.width, 0, 0, myBitmap.width, myBitmap.height)
        return myBitmap
    }

    fun invertColors(bitmap: Bitmap): Bitmap {
        val length = bitmap.width * bitmap.height
        val array = IntArray(length)
        bitmap.getPixels(array, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        for (i in 0 until length) {
            // If the bitmap is in ARGB_8888 format
            if (array[i] == -0x1000000) {
                array[i] = -0x1
            }
        }
        bitmap.setPixels(array, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        return bitmap
    }

    fun getRoundedCornerBitmap(bitmap: Bitmap, pixels: Int): Bitmap {
        val output = Bitmap.createBitmap(bitmap.width, bitmap
                .height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        val color = -0xbdbdbe
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        val rectF = RectF(rect)

        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawRoundRect(rectF, pixels.toFloat(), pixels.toFloat(), paint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)

        return output
    }

    fun convertLayoutToBitmap(v: View): Bitmap {

        v.isDrawingCacheEnabled = true
        v.buildDrawingCache()

        return v.drawingCache
    }
}


