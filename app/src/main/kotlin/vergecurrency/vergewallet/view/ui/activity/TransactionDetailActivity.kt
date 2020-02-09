package vergecurrency.vergewallet.view.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat

import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.helpers.utils.TransactionUtils
import vergecurrency.vergewallet.service.model.Transaction
import vergecurrency.vergewallet.view.base.BaseActivity

class TransactionDetailActivity : BaseActivity(), View.OnClickListener {
    private var closeButton: ImageView? = null


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_detail)

        //Get Transaction
        tx = intent.getSerializableExtra(TransactionUtils.EXTRA_TRANSACTION) as Transaction

        //View Elements
        val transactionShort = findViewById<TextView>(R.id.transaction_detail_address_short)
        val transactionAmount = findViewById<TextView>(R.id.transaction_detail_amount)
        val transactionTime = findViewById<TextView>(R.id.transaction_detail_time)
        val transactionAddress = findViewById<TextView>(R.id.transaction_detail_address_item_address)
        val transactionMessage = findViewById<TextView>(R.id.transaction_detail_message_item_message)
        val transactionId = findViewById<TextView>(R.id.transaction_detail_txid_item_txid)
        val transactionConfirmations = findViewById<TextView>(R.id.transaction_detail_confirmations_item_confirmations)
        val messageLayout = findViewById<View>(R.id.transaction_detail_message_item)
        val transactionAddressExtra = findViewById<ImageView>(R.id.transaction_detail_address_item_extra)
        val transactionIdExtra = findViewById<ImageView>(R.id.transaction_detail_txid_item_extra)
        val icon = findViewById<ImageView>(R.id.transaction_detail_icon)


        transactionShort.text = String.format("%s******", tx!!.address!!.substring(0, 6))
        transactionTime.text = TransactionUtils.toFormattedDate(tx!!.time)

        //Green or red amount +/-
        if (tx!!.isReceive) {
            transactionAmount.text = arrayOf("+", java.lang.Double.valueOf(tx!!.amount).toString(), "XVG").joinToString(" ")
            transactionAmount.setTextColor(ContextCompat.getColor(this, R.color.material_green_500))
            icon.setImageResource(R.drawable.icon_tx_received)
        } else {
            transactionAmount.text = arrayOf("-", java.lang.Double.valueOf(tx!!.amount).toString(), "XVG").joinToString(" ")
            transactionAmount.setTextColor(ContextCompat.getColor(this, R.color.material_red_500))
            icon.setImageResource(R.drawable.icon_tx_sent)
        }

        transactionAddress.text = tx!!.address
        transactionConfirmations.text = Integer.toString(tx!!.confirmations)
        transactionId.text = arrayOf(tx!!.txid!!.substring(0, 15), "...", tx!!.txid!!.substring(tx!!.txid!!.length - 17)).joinToString("")
        transactionMessage.text = tx!!.account

        if (tx!!.account == null || tx!!.account == "") {
            messageLayout.visibility = View.INVISIBLE
        }

        closeButton = findViewById(R.id.transaction_detail_close_button)
        closeButton!!.setOnClickListener(this)
        transactionIdExtra.setOnClickListener(TransactionIdClick())
        transactionAddressExtra.setOnClickListener(TransactionAddressClick())
    }


    override fun onClick(v: View) {
        finish()
    }

    internal inner class TransactionIdClick : View.OnClickListener {
        override fun onClick(v: View) {
            openTransactionIdBrowserIntent()
        }
    }

    internal inner class TransactionAddressClick : View.OnClickListener {
        override fun onClick(v: View) {
            openTransactionAddressBrowserIntent()
        }
    }

    private fun openTransactionIdBrowserIntent() {
        val url = arrayOf("https://verge-blockchain.info/tx/", tx!!.txid).joinToString("")
        val webIntent = Intent(Intent.ACTION_VIEW)
        webIntent.data = Uri.parse(url)
        startActivity(webIntent)
    }

    private fun openTransactionAddressBrowserIntent() {
        val url = arrayOf("https://verge-blockchain.info/address/", tx!!.txid).joinToString("")
        val webIntent = Intent(Intent.ACTION_VIEW)
        webIntent.data = Uri.parse(url)
        startActivity(webIntent)
    }

    companion object {
        private var tx: Transaction? = null
    }
}
