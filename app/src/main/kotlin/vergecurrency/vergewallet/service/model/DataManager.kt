package vergecurrency.vergewallet.service.model

import android.app.Application
import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration
import vergecurrency.vergewallet.helpers.utils.WalletDataIdentifierUtils
import vergecurrency.vergewallet.wallet.WalletManager
import java.util.*
import kotlin.collections.ArrayList

object DataManager{
    var xvgDataRealm: Realm? = null;


    fun loadWalletData(context : Context, walletName: String){
        EncryptedPreferencesManager.getOrCreateEncryptedSharedPreferences(context, walletName)
        getOrCreateVergeDataRealm(walletName)
    }


    fun getAllEncryptedPreferences(context: Context): ArrayList<String> {
        val existingWalletFiles: ArrayList<String> = ArrayList();
        for (file in context.dataDir.listFiles()) {
            if (file.absolutePath.endsWith("vergecurrency.vergewallet/shared_prefs")) {
                if (file.listFiles().size > 0) {
                    for (prefs in file.listFiles()) {
                        if (WalletDataIdentifierUtils.isEncryptedSharedPreferences(file.name)) {
                            existingWalletFiles.add(file.name)
                            EncryptedPreferencesManager.getOrCreateEncryptedSharedPreferences(context, "")
                        }
                    }
                }
            }

        }
        return existingWalletFiles;
    }

    private fun getOrCreateVergeDataRealm(walletName: String) {
        val key =  Base64.getDecoder().decode(EncryptedPreferencesManager.realmEncryptionKey)
        val config = RealmConfiguration.Builder()
                .name(WalletDataIdentifierUtils.getRealmFileNameByUsersWalletName(walletName))
                .encryptionKey(key)
                .schemaVersion(0)
                .modules(VDroidRealmModule())
                .build()
        this.xvgDataRealm = Realm.getInstance(config);
    }

}