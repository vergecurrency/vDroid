package vergecurrency.vergewallet.view.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

import androidx.viewpager.widget.ViewPager

import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.view.adapter.WalletAdapter
import vergecurrency.vergewallet.view.base.BaseFragment
import vergecurrency.vergewallet.viewmodel.WalletFragmentViewModel

class FragmentWallet : BaseFragment() {

    private var rootView: View? = null
    private var currencyCode: ByteArray? = null
    private var mViewModel: WalletFragmentViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProvider(this).get(WalletFragmentViewModel::class.java)

        currencyCode = mViewModel!!.currencyCode

        // Inflate the layout for this fragment

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_wallet, container, false)

            initComponents()
        }
        return rootView
    }

    private fun initComponents() {

        val pager = rootView!!.findViewById<ViewPager>(R.id.wallet_cards_viewpager)
        pager.adapter = WalletAdapter(childFragmentManager, context!!)

        val balanceLabel = rootView!!.findViewById<TextView>(R.id.wallet_card_balance_label)
        balanceLabel.text = String.format("%s BALANCE", currencyCode!!)

        val balanceXVG = rootView!!.findViewById<TextView>(R.id.wallet_card_verge_balance)
        balanceXVG.text = String.format("XVG %o", mViewModel!!.balance!!)

        val balanceFiat = rootView!!.findViewById<TextView>(R.id.wallet_card_fiat_balance)
        balanceFiat.text = String.format("%s %o", currencyCode, mViewModel!!.balance)

        val changeLabel = rootView!!.findViewById<TextView>(R.id.wallet_card_change_label)
        changeLabel.text = String.format("%s/XVG", currencyCode!!)

        val changeAmount = rootView!!.findViewById<TextView>(R.id.wallet_card_change)
        changeAmount.text = String.format("%s 0.017", currencyCode!!)
    }

}// Required empty public constructor

