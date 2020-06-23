package vergecurrency.vergewallet.view.ui.activity.firstlaunch

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.view.base.BaseActivity
import vergecurrency.vergewallet.viewmodel.PermissionsViewModel

class WalletNameActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permissions)
    }
}