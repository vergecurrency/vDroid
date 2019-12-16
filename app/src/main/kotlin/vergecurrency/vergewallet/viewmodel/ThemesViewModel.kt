package vergecurrency.vergewallet.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

import java.util.ArrayList

import vergecurrency.vergewallet.service.model.Language

class ThemesViewModel(application: Application): AndroidViewModel(application) {
    private val languages: ArrayList<Language>? = null


    val themes: ArrayList<String>
        get() {
            val a = ArrayList<String>()
            a.add("Feather Theme")
            a.add("Moon Mode")
            return a
        }

}