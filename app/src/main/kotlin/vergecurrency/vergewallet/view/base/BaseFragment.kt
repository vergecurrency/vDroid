package vergecurrency.vergewallet.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import vergecurrency.vergewallet.helpers.utils.UIUtils
import vergecurrency.vergewallet.service.model.PreferencesManager

open class BaseFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        UIUtils.setTheme(PreferencesManager.currentTheme!!, context!!, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}
