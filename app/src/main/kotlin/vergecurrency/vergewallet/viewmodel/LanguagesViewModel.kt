package vergecurrency.vergewallet.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import vergecurrency.vergewallet.helpers.utils.LanguagesUtils
import vergecurrency.vergewallet.service.model.Language
import java.util.*

class LanguagesViewModel(application: Application) : AndroidViewModel(application) {
    val languages: ArrayList<Language>?

    init {

        languages = LanguagesUtils.loadLanguagesFromFile(getApplication<Application>().applicationContext)
    }

}