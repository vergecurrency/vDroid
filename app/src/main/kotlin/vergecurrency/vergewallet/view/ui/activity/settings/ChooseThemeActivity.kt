package vergecurrency.vergewallet.view.ui.activity.settings

import android.os.Bundle
import android.widget.ListView

import androidx.lifecycle.ViewModelProviders

import vergecurrency.vergewallet.view.base.BaseActivity
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.view.adapter.ThemesAdapter
import vergecurrency.vergewallet.viewmodel.ThemesViewModel

class ChooseThemeActivity : BaseActivity() {


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme_change)

        val mViewModel = ViewModelProviders.of(this).get(ThemesViewModel::class.java)

        val themesList = findViewById<ListView>(R.id.activity_change_theme_listview)
        themesList.adapter = ThemesAdapter(this, mViewModel.themes)

    }

}
