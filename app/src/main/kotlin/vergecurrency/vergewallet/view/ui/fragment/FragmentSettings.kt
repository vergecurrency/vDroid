package vergecurrency.vergewallet.view.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.service.model.SettingsListViewData
import vergecurrency.vergewallet.service.model.SettingsListViewHeader
import vergecurrency.vergewallet.view.adapter.SettingsListsAdapter
import vergecurrency.vergewallet.view.base.BaseFragment
import vergecurrency.vergewallet.view.ui.activity.PinPromptActivity
import vergecurrency.vergewallet.view.ui.activity.settings.ChooseCurrencyActivity
import vergecurrency.vergewallet.view.ui.activity.settings.ChooseLanguageActivity
import vergecurrency.vergewallet.view.ui.activity.settings.ChooseThemeActivity
import vergecurrency.vergewallet.view.ui.activity.settings.DisconnectActivity
import vergecurrency.vergewallet.view.ui.activity.settings.DonateActivity
import vergecurrency.vergewallet.view.ui.activity.settings.PaperkeyActivity
import vergecurrency.vergewallet.view.ui.activity.firstlaunch.PinSetActivity
import vergecurrency.vergewallet.view.ui.activity.settings.ServiceURLActivity
import vergecurrency.vergewallet.view.ui.activity.settings.TorSettingsActivity
import vergecurrency.vergewallet.viewmodel.PinPromptedViewModel


class FragmentSettings : BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_settings, container, false)

        initWalletSettings(rootView)
        initGeneralSettings(rootView)
        initOtherSettings(rootView)

        return rootView
    }

    private fun initWalletSettings(view: View) {
        val itemsDataWallet = arrayOf(
            SettingsListViewData("Remove wallet", R.drawable.icon_disconnected, View.OnClickListener { startActivity(Intent(it.context, DisconnectActivity::class.java)) }),
            SettingsListViewData("Paperkey", R.drawable.icon_paperkey,View.OnClickListener {
                val intent = Intent(it.context, PinPromptActivity::class.java)
                intent.putExtra("nextView", "viewPassphrase")
                startActivity(intent) }),
            SettingsListViewData("Addresses", R.drawable.icon_disconnected, null), SettingsListViewData("Transaction Proposals", R.drawable.icon_disconnected, null),
            SettingsListViewData("Service URL", R.drawable.icon_disconnected,  View.OnClickListener { startActivity(Intent(it.context, ServiceURLActivity::class.java)) }))

        fillRecyclerView(view, R.id.settings_list_wallet, SettingsListViewHeader("WALLET"), itemsDataWallet)

    }


    private fun initGeneralSettings(view: View) {
        val itemsDataSettings = arrayOf(
            SettingsListViewData("Fiat Currency", R.drawable.icon_currency_exchange,  View.OnClickListener { startActivity(Intent(it.context, ChooseCurrencyActivity::class.java)) }),
            SettingsListViewData("Language", R.drawable.icon_home, View.OnClickListener {  startActivity(Intent(it.context, ChooseLanguageActivity::class.java)) }),
            SettingsListViewData("Theme", R.drawable.icon_theme,  View.OnClickListener {  startActivity(Intent(it.context, ChooseThemeActivity::class.java)) }),
            SettingsListViewData("Change wallet PIN", R.drawable.icon_lock,  View.OnClickListener {
                val intent = Intent(it.context, PinPromptActivity::class.java)
                intent.putExtra("nextView", "changePin")
                startActivity(intent)}),
            SettingsListViewData("Use fingerprint", R.drawable.icon_fingerprint, null),
                SettingsListViewData("Tor connection", R.drawable.icon_onion, View.OnClickListener { startActivity(Intent(it.getContext(), TorSettingsActivity::class.java)) }))

        fillRecyclerView(view, R.id.settings_list_settings, SettingsListViewHeader("SETTINGS"), itemsDataSettings)

    }

    private fun initOtherSettings(view: View) {
        val itemsDataOther = arrayOf(
                SettingsListViewData("Credits", R.drawable.icon_credits, null),
                SettingsListViewData("Donate", R.drawable.icon_donate, View.OnClickListener { startActivity(Intent(it.context, DonateActivity::class.java)) }),
                SettingsListViewData("Rate app", R.drawable.icon_star, null),
                SettingsListViewData("Website", R.drawable.icon_web, View.OnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.vergecurrency.com"))
                    startActivity(intent)}),
                SettingsListViewData("Contribute", R.drawable.icon_github, View.OnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.github.com/vergecurrency/vDroid"))
                    startActivity(intent)
        }))

        fillRecyclerView(view, R.id.settings_list_other, SettingsListViewHeader("OTHER"), itemsDataOther)
    }

    fun fillRecyclerView(rootView: View, recyclerViewID: Int, settingsListViewHeader: SettingsListViewHeader, itemsData: Array<SettingsListViewData>) {
        //Get the RecyclerView
        val recView = rootView.findViewById<RecyclerView>(recyclerViewID)

        recView.layoutManager = LinearLayoutManager(context)
        //Sets the adapter to the RecyclerView
        recView.adapter = SettingsListsAdapter(settingsListViewHeader, itemsData)
        recView.addItemDecoration(DividerItemDecoration(recView.context, DividerItemDecoration.VERTICAL))

    }

}

