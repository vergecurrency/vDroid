package vergecurrency.vergewallet.view.ui.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode

import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.view.base.BaseActivity
import vergecurrency.vergewallet.view.ui.fragment.FragmentReceive
import vergecurrency.vergewallet.view.ui.fragment.FragmentSend
import vergecurrency.vergewallet.view.ui.fragment.FragmentSettings
import vergecurrency.vergewallet.view.ui.fragment.FragmentTransactions
import vergecurrency.vergewallet.view.ui.fragment.FragmentWallet

class WalletActivity : BaseActivity() {

    private var currentItem: Int = 0

    //Listens to what has been pressed and opens up the right Fragment
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        if (item.itemId != currentItem) {
            currentItem = item.itemId
            when (item.itemId) {
                R.id.navigation_wallet -> {
                    showFragment(FragmentWallet())

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_transactions -> {
                    showFragment(FragmentTransactions())

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_send -> {
                    showFragment(FragmentSend())

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_receive -> {
                    showFragment(FragmentReceive())

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_settings -> {
                    showFragment(FragmentSettings())
                    return@OnNavigationItemSelectedListener true
                }
            }
            return@OnNavigationItemSelectedListener false
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)

        //Initialize upper text view


        //Initialize Bottom bottom_navigation view
        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED

        //Shows the wallet fragment by default
        showFragment(FragmentWallet())
    }


    //Shows the previously selected fragment.
    private fun showFragment(fragment: Fragment) {


        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit()
    }

    //Do nothing on back button, as the only back action possible atm is going to the launcher...
    override fun onBackPressed() {}
}
