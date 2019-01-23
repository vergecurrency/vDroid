package vergecurrency.vergewallet.views.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.omega_r.libs.OmegaCenterIconButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import vergecurrency.vergewallet.BuildConfig;
import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.helpers.AnimationUtils;
import vergecurrency.vergewallet.helpers.ImageUtils;

public class FragmentReceive extends Fragment {


	private int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE;

	public FragmentReceive() {
		// Required empty public constructor
	}


	public void generateQRCode(String walletAddress, ImageView vQRCode) {


		QRGEncoder qrgEncoder = new QRGEncoder(walletAddress, null, QRGContents.Type.TEXT, 200);
		try {
			Bitmap qrcodeBmp = ImageUtils.invertColors(ImageUtils.makeTransparent(qrgEncoder.encodeAsBitmap(), Color.WHITE));
			vQRCode.setImageBitmap(qrcodeBmp);

		} catch (Exception ex) {
			//TODO : catch exception properly.
			System.out.println(ex.getMessage());

		}
	}

	private OnCheckedChangeListener obfuscateSwitchListener() {
		return (buttonView, isChecked) -> {
			if (isChecked) {
				AnimationUtils.ImageViewAnimatedChange(getContext(), backgroundCard, BitmapFactory.decodeResource(getResources(), R.drawable.bg_card_receivestealth));
				AnimationUtils.ButtonAnimationChange(getContext(), shareButton, R.color.verge_colorPrimaryDark);
			} else {
				AnimationUtils.ImageViewAnimatedChange(getContext(), backgroundCard, BitmapFactory.decodeResource(getResources(), R.drawable.bg_card_receive));
				AnimationUtils.ButtonAnimationChange(getContext(), shareButton, R.color.verge_colorPrimary);
			}
		};
	}

	//LOL doesn't work -> WIP
	private View.OnClickListener shareOnClickListener() {
		return v -> {
			Bitmap card = ImageUtils.convertLayoutToBitmap(cardLayout);

			Intent shareIntent = new Intent(Intent.ACTION_SEND);
			shareIntent.setType("image/jpeg");


			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			card.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

			if (ContextCompat.checkSelfPermission(this.getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
				// Permission is not granted
				if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(),
						Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
					// Show an explanation to the user *asynchronously* -- don't block
					// this thread waiting for the user's response! After the user
					// sees the explanation, try again to request the permission.
				} else {
					// No explanation needed; request the permission
					ActivityCompat.requestPermissions(this.getActivity(),
							new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
							MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
				}
			}


			File f = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
			try {
				f.createNewFile();
				FileOutputStream fo = new FileOutputStream(f);
				fo.write(bytes.toByteArray());
			} catch (IOException e) {
				e.printStackTrace();
			}
			shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
			startActivity(Intent.createChooser(shareIntent, "Share Image"));

			startActivity(Intent.createChooser(shareIntent, "Share your Verge Address"));
		};
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_receive, container, false);

		shareButton = (OmegaCenterIconButton) rootView.findViewById(R.id.receive_button_share);
		backgroundCard = (ImageView) rootView.findViewById(R.id.wallet_card_background_receive);
		obfuscateSwitch = (SwitchCompat) rootView.findViewById(R.id.wallet_receive_switch_stealth);
		cardLayout = (RelativeLayout) rootView.findViewById(R.id.wallet_receive_card_layout);

		obfuscateSwitch.setOnCheckedChangeListener(obfuscateSwitchListener());

		shareButton.setOnClickListener(shareOnClickListener());

		ImageView vQRCode = (ImageView) rootView.findViewById(R.id.qr_code_receive);
		generateQRCode(getResources().getString(R.string.receive_current_code), vQRCode);

		// Inflate the layout for this fragment
		return rootView;
	}


	private SwitchCompat obfuscateSwitch;
	private ImageView backgroundCard;
	private OmegaCenterIconButton shareButton;
	private RelativeLayout cardLayout;

}
