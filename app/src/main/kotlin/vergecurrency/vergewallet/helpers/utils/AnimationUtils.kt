package vergecurrency.vergewallet.helpers.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.view.animation.Animation
import android.widget.ImageView

import com.omega_r.libs.OmegaCenterIconButton


object AnimationUtils {


        fun ImageViewAnimatedChange(c: Context, v: ImageView, new_image: Bitmap) {
            val anim_out = android.view.animation.AnimationUtils.loadAnimation(c, android.R.anim.fade_out)
            anim_out.duration = STANDARD_DURATION
            val anim_in = android.view.animation.AnimationUtils.loadAnimation(c, android.R.anim.fade_in)
            anim_in.duration = STANDARD_DURATION
            anim_out.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationRepeat(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    v.setImageBitmap(new_image)
                    anim_in.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(animation: Animation) {}
                        override fun onAnimationRepeat(animation: Animation) {}
                        override fun onAnimationEnd(animation: Animation) {}
                    })
                    v.startAnimation(anim_in)
                }
            })
            v.startAnimation(anim_out)
        }

        fun ButtonAnimationChange(c: Context, v: OmegaCenterIconButton, color: Int) {
            val anim_out = android.view.animation.AnimationUtils.loadAnimation(c, android.R.anim.fade_out)
            anim_out.duration = STANDARD_DURATION
            val anim_in = android.view.animation.AnimationUtils.loadAnimation(c, android.R.anim.fade_in)
            anim_in.duration = STANDARD_DURATION
            anim_out.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationRepeat(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    v.backgroundTintList = ColorStateList.valueOf(color)
                    anim_in.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(animation: Animation) {}
                        override fun onAnimationRepeat(animation: Animation) {}
                        override fun onAnimationEnd(animation: Animation) {}
                    })
                    v.startAnimation(anim_in)
                }
            })
            v.startAnimation(anim_out)
        }

        private const val STANDARD_DURATION: Long = 150

}
