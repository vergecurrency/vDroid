package vergecurrency.vergewallet.view.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.google.zxing.EncodeHintType;
import com.omega_r.libs.OmegaCenterIconButton;

import net.glxn.qrgen.android.QRCode;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.helpers.utils.AnimationUtils;
import vergecurrency.vergewallet.helpers.utils.FileUtils;
import vergecurrency.vergewallet.helpers.utils.ImageUtils;
import vergecurrency.vergewallet.view.base.BaseFragment;
import vergecurrency.vergewallet.viewmodel.ReceiveFragmentViewModel;

public class FragmentReceive extends BaseFragment {

	private static int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE;
	private SwitchCompat obfuscateSwitch;
	private ImageView backgroundCard;
	private OmegaCenterIconButton shareButton;
	private RelativeLayout cardLayout;
	private EditText addressTextView;
	private ReceiveFragmentViewModel mViewModel;


	public FragmentReceive() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_receive, container, false);

		mViewModel = ViewModelProviders.of(this).get(ReceiveFragmentViewModel.class);
		String recAddress = mViewModel.getReceiveAddress();
		initElements(rootView, recAddress);
		setListeners();

		ImageView vQRCode = rootView.findViewById(R.id.qr_code_receive);
		generateQRCode(recAddress, vQRCode);

		// Inflate the layout for this fragment
		return rootView;
	}

	private void initElements(View rootView, String recAddress) {
		shareButton = rootView.findViewById(R.id.receive_button_share);
		backgroundCard = rootView.findViewById(R.id.wallet_card_background_receive);
		obfuscateSwitch = rootView.findViewById(R.id.wallet_receive_switch_stealth);
		cardLayout = rootView.findViewById(R.id.wallet_receive_card_layout);
		addressTextView = rootView.findViewById(R.id.send_balance_address);
		addressTextView.setText(recAddress);
		TextView cardText = rootView.findViewById(R.id.receive_address_card);
		cardText.setText(recAddress);
	}

	private void setListeners() {
		obfuscateSwitch.setOnCheckedChangeListener(obfuscateSwitchListener());
		shareButton.setOnClickListener(shareOnClickListener());
		addressTextView.setOnLongClickListener(copyOnLongClickListener());
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

	//Actually it works.
	private OnClickListener shareOnClickListener() {
		return v -> {
			// Here, thisActivity is the current activity
			Activity a = this.getActivity();
			if (ContextCompat.checkSelfPermission(a, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

				boolean needExplanation = ActivityCompat.shouldShowRequestPermissionRationale(a, Manifest.permission.WRITE_EXTERNAL_STORAGE);
				if (!needExplanation) {
					ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
				}
			} else {
				shareImage();
			}
		};
	}

	private OnLongClickListener copyOnLongClickListener() {
		return v -> {
			String address = addressTextView.getText().toString();

			ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
			ClipData clip = ClipData.newPlainText("Address", address);
			clipboard.setPrimaryClip(clip);

			Toast.makeText(getContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();

			return false;
		};
	}


	public void generateQRCode(String walletAddress, ImageView vQRCode) {

		try {
			//I will burn in hell. But fuck it it's just a bitmap
			vQRCode.setImageBitmap(ImageUtils.getRoundedCornerBitmap(ImageUtils.invertColors(ImageUtils.makeTransparent(QRCode.from(walletAddress).withHint(EncodeHintType.MARGIN, "0").withSize(175, 175).bitmap(), Color.WHITE)), 10));
		} catch (Exception ex) {
			//TODO : catch exception properly.
			System.out.println(ex.getMessage());
		}
	}

	private void shareImage() {

		//get the card from the RelativeLayout
		Bitmap card = ImageUtils.convertLayoutToBitmap(cardLayout);

		FileUtils.saveBitmapToFile(card);
		FileUtils.share(getContext());
	}


}
