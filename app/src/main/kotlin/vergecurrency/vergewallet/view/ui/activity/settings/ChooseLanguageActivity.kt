package vergecurrency.vergewallet.view.ui.activity.settings

import android.os.Bundle
import android.widget.ListView
import androidx.lifecycle.ViewModelProvider
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.view.adapter.LanguagesAdapter
import vergecurrency.vergewallet.view.base.BaseActivity
import vergecurrency.vergewallet.viewmodel.LanguagesViewModel

class ChooseLanguageActivity : BaseActivity() {


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_change)


        val mViewModel = ViewModelProvider(this).get(LanguagesViewModel::class.java)

        val languagesList = findViewById<ListView>(R.id.activity_change_language_listview)
        languagesList.adapter = LanguagesAdapter(this, mViewModel.languages!!)

    }
}
