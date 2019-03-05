package vergecurrency.vergewallet.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.omega_r.libs.OmegaCenterIconButton;

public final class AnimationUtils {


	public AnimationUtils() {
	}

	public static void ImageViewAnimatedChange(Context c, final ImageView v, final Bitmap new_image) {
		final Animation anim_out = android.view.animation.AnimationUtils.loadAnimation(c, android.R.anim.fade_out);
		anim_out.setDuration(standardDuration);
		final Animation anim_in  = android.view.animation.AnimationUtils.loadAnimation(c, android.R.anim.fade_in);
		anim_in.setDuration(standardDuration);
		anim_out.setAnimationListener(new Animation.AnimationListener()
		{
			@Override public void onAnimationStart(Animation animation) {}
			@Override public void onAnimationRepeat(Animation animation) {}
			@Override public void onAnimationEnd(Animation animation)
			{
				v.setImageBitmap(new_image);
				anim_in.setAnimationListener(new Animation.AnimationListener() {
					@Override public void onAnimationStart(Animation animation) {}
					@Override public void onAnimationRepeat(Animation animation) {}
					@Override public void onAnimationEnd(Animation animation) {}
				});
				v.startAnimation(anim_in);
			}
		});
		v.startAnimation(anim_out);
	}

	public static void ButtonAnimationChange(Context c, final OmegaCenterIconButton v, final int  color) {
		final Animation anim_out = android.view.animation.AnimationUtils.loadAnimation(c, android.R.anim.fade_out);
		anim_out.setDuration(standardDuration);
		final Animation anim_in  = android.view.animation.AnimationUtils.loadAnimation(c, android.R.anim.fade_in);
		anim_in.setDuration(standardDuration);
		anim_out.setAnimationListener(new Animation.AnimationListener()
		{
			@Override public void onAnimationStart(Animation animation) {}
			@Override public void onAnimationRepeat(Animation animation) {}
			@Override public void onAnimationEnd(Animation animation)
			{
				v.setBackgroundTintList(c.getResources().getColorStateList(color));
				anim_in.setAnimationListener(new Animation.AnimationListener() {
					@Override public void onAnimationStart(Animation animation) {}
					@Override public void onAnimationRepeat(Animation animation) {}
					@Override public void onAnimationEnd(Animation animation) {}
				});
				v.startAnimation(anim_in);
			}
		});
		v.startAnimation(anim_out);
	}

	private final static long standardDuration = 150;
}
