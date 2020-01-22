package vergecurrency.vergewallet.view.ui.fragment

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.CompoundButton.OnCheckedChangeListener
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProviders

import com.google.zxing.EncodeHintType
import com.omega_r.libs.OmegaCenterIconButton

import net.glxn.qrgen.android.QRCode

import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.helpers.utils.AnimationUtils
import vergecurrency.vergewallet.helpers.utils.FileUtils
import vergecurrency.vergewallet.helpers.utils.ImageUtils
import vergecurrency.vergewallet.view.base.BaseFragment
import vergecurrency.vergewallet.viewmodel.ReceiveFragmentViewModel


class FragmentReceive : BaseFragment() {
    private var obfuscateSwitch: SwitchCompat? = null
    private var backgroundCard: ImageView? = null
    private var shareButton: OmegaCenterIconButton? = null
    private var cardLayout: RelativeLayout? = null
    private var addressTextView: EditText? = null
    private var mViewModel: ReceiveFragmentViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_receive, container, false)

        mViewModel = ViewModelProviders.of(this).get(ReceiveFragmentViewModel::class.java)
        val recAddress = mViewModel!!.receiveAddress
        initElements(rootView, recAddress)
        setListeners()

        val vQRCode = rootView.findViewById<ImageView>(R.id.qr_code_receive)
        generateQRCode(recAddress, vQRCode)

        // Inflate the layout for this fragment
        return rootView
    }

    private fun initElements(rootView: View, recAddress: String?) {
        shareButton = rootView.findViewById(R.id.receive_button_share)
        backgroundCard = rootView.findViewById(R.id.wallet_card_background_receive)
        obfuscateSwitch = rootView.findViewById(R.id.wallet_receive_switch_stealth)
        cardLayout = rootView.findViewById(R.id.wallet_receive_card_layout)
        addressTextView = rootView.findViewById(R.id.send_balance_address)
        addressTextView!!.setText(recAddress)
        val cardText = rootView.findViewById<TextView>(R.id.receive_address_card)
        cardText.text = recAddress
    }

    private fun setListeners() {
        obfuscateSwitch!!.setOnCheckedChangeListener(obfuscateSwitchListener())
        shareButton!!.setOnClickListener(shareOnClickListener())
        addressTextView!!.setOnLongClickListener(copyOnLongClickListener())
    }


    private fun obfuscateSwitchListener(): OnCheckedChangeListener {
        return OnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AnimationUtils.ImageViewAnimatedChange(context!!, backgroundCard!!, BitmapFactory.decodeResource(resources, R.drawable.bg_card_receivestealth))
                AnimationUtils.ButtonAnimationChange(context!!, shareButton!!, R.color.verge_colorPrimaryDark)
            } else {
                AnimationUtils.ImageViewAnimatedChange(context!!, backgroundCard!!, BitmapFactory.decodeResource(resources, R.drawable.bg_card_receive))
                AnimationUtils.ButtonAnimationChange(context!!, shareButton!!, R.color.verge_colorPrimary)
            }
        }
    }

    //Actually it works.
    private fun shareOnClickListener(): OnClickListener {
        return OnClickListener {
            // Here, thisActivity is the current activity
            //val a = this.activity
            //if (ContextCompat.checkSelfPermission(a!!, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                //val needExplanation = ActivityCompat.shouldShowRequestPermissionRationale(a, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                //if (!needExplanation) {
                  //  ActivityCompat.requestPermissions(this.activity!!, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE)
                //}
            //} else {
                shareImage()
            //}
        }
    }

    private fun copyOnLongClickListener(): OnLongClickListener {
        return OnLongClickListener {
            val address = addressTextView!!.text.toString()

            val clipboard = context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Address", address)
            clipboard.setPrimaryClip(clip)

            Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()

            false
        }
    }


    private fun generateQRCode(walletAddress: String?, vQRCode: ImageView) {

        try {
            //I will burn in hell. But fuck it it's just a bitmap
            vQRCode.setImageBitmap(ImageUtils.getRoundedCornerBitmap(ImageUtils.invertColors(ImageUtils.makeTransparent(QRCode.from(walletAddress).withHint(EncodeHintType.MARGIN, "0").withSize(175, 175).bitmap(), Color.WHITE)), 10))
        } catch (ex: Exception) {
            //TODO : catch exception properly.
            println(ex.message)
        }

    }

    private fun shareImage() {

        //get the card from the RelativeLayout
        val card = ImageUtils.convertLayoutToBitmap(cardLayout!!)

        FileUtils.saveImage(context!!,card)
        FileUtils.share(context!!)
    }

    companion object {

        private const val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: Int = 0
    }


}// Required empty public constructor
