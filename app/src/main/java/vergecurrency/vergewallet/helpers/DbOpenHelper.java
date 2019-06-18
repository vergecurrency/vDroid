package vergecurrency.vergewallet.helpers;

import android.content.Context;
import android.util.Log;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;


/**
 * This class is necessary to avoid the default onUpgrade to drop the database on a version update.
 * What a bunch of funny guys @OpenDao!
 */
public class DbOpenHelper extends DatabaseOpenHelper {


    public DbOpenHelper(Context context, String name, int version) {
        super(context, name, version);
    }


    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        Log.d("DEBUG", "DB_OLD_VERSION : " + oldVersion + ", DB_NEW_VERSION : " + newVersion);
        switch (oldVersion) {
            case 1:
            case 2:
                //TODO : add eventual modifications on new versions
        }
    }
}
