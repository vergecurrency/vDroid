package vergecurrency.vergewallet;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class FragmentReceive extends Fragment {



    public FragmentReceive() {
        // Required empty public constructor
    }


    public void generateQRCode(String walletAddress, ImageView vQRCode){



        QRGEncoder qrgEncoder = new QRGEncoder(walletAddress,null, QRGContents.Type.TEXT, 480);
        try{
          Bitmap qrcodeBmp = qrgEncoder.encodeAsBitmap();
          vQRCode.setImageBitmap(qrcodeBmp);

        }catch (Exception ex) {
            //TODO : catch exception properly.
            System.out.println(ex.getMessage());

            /*Snackbar snackbar = Snackbar.make(vQRCodeView, "Error generating qr code", Snackbar.LENGTH_LONG);

            snackbar.show();*/
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_receive, container, false);

        ImageView vQRCode = (ImageView) rootView.findViewById(R.id.qrcodeimage);
        generateQRCode(getResources().getString(R.string.current_qr_code), vQRCode);

        // Inflate the layout for this fragment
        return rootView;
    }
}
