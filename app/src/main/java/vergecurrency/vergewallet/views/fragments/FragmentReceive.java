package vergecurrency.vergewallet.views.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.zxing.EncodeHintType;
import com.omega_r.libs.OmegaCenterIconButton;

import net.glxn.qrgen.android.QRCode;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.helpers.AnimationUtils;
import vergecurrency.vergewallet.helpers.FileUtils;
import vergecurrency.vergewallet.helpers.ImageUtils;

public class FragmentReceive extends Fragment {



	public FragmentReceive() {
		// Required empty public constructor
	}


	public void generateQRCode(String walletAddress, ImageView vQRCode) {



		try {
			//I should burn in hell for this but I like readability.
			Bitmap qrCode = QRCode.from(walletAddress).withHint(EncodeHintType.MARGIN, "1").bitmap();
			Bitmap transparentQrCode = ImageUtils.makeTransparent(qrCode, Color.WHITE);
			Bitmap finalQrCode = ImageUtils.invertColors(transparentQrCode);
			Bitmap corneredQrCode = ImageUtils.getRoundedCornerBitmap(finalQrCode, 20);

			vQRCode.setImageBitmap(corneredQrCode);

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
			shareImage();
		};
	}


	private void shareImage() {

		//get the card from the RelativeLayout
		Bitmap card = ImageUtils.convertLayoutToBitmap(cardLayout);

		FileUtils.saveBitmapToFile(card);
		FileUtils.share(getContext());
		/*
		if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
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
		*/

	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_receive, container, false);

		initElements(rootView);
		setListeners();

		ImageView vQRCode = (ImageView) rootView.findViewById(R.id.qr_code_receive);
		generateQRCode(getResources().getString(R.string.receive_current_code), vQRCode);

		// Inflate the layout for this fragment
		return rootView;
	}

	private void initElements(View rootView) {
		shareButton = (OmegaCenterIconButton) rootView.findViewById(R.id.receive_button_share);
		backgroundCard = (ImageView) rootView.findViewById(R.id.wallet_card_background_receive);
		obfuscateSwitch = (SwitchCompat) rootView.findViewById(R.id.wallet_receive_switch_stealth);
		cardLayout = (RelativeLayout) rootView.findViewById(R.id.wallet_receive_card_layout);
	}


	private void setListeners() {
		obfuscateSwitch.setOnCheckedChangeListener(obfuscateSwitchListener());
		shareButton.setOnClickListener(shareOnClickListener());
	}


	private SwitchCompat obfuscateSwitch;
	private ImageView backgroundCard;
	private OmegaCenterIconButton shareButton;
	private RelativeLayout cardLayout;

}
