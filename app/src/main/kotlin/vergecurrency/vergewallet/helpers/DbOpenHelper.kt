package vergecurrency.vergewallet.helpers

import android.content.Context
import android.util.Log

import org.greenrobot.greendao.database.Database
import org.greenrobot.greendao.database.DatabaseOpenHelper


/**
 * This class is necessary to avoid the default onUpgrade to drop the database on a version update.
 * What a bunch of funny guys @OpenDao!
 */
class DbOpenHelper(context: Context, name: String, version: Int) : DatabaseOpenHelper(context, name, version) {


    override fun onUpgrade(db: Database?, oldVersion: Int, newVersion: Int) {
        super.onUpgrade(db, oldVersion, newVersion)
        Log.d("DEBUG", "DB_OLD_VERSION : $oldVersion, DB_NEW_VERSION : $newVersion")
        when (oldVersion) {
            //TODO : add eventual modifications on new versions
        }
    }
}
