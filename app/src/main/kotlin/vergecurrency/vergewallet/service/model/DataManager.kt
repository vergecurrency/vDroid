package vergecurrency.vergewallet.service.model

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration
import vergecurrency.vergewallet.helpers.utils.WalletDataIdentifierUtils
import java.lang.RuntimeException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

object DataManager {
    var xvgDataRealm: Realm? = null;


    fun loadWalletData(context: Context, walletId: UUID) {
        EncryptedPreferencesManager.getOrCreateEncryptedSharedPreferences(context, walletId)
        getOrCreateVergeDataRealm(walletId)
    }


    //Return identifier and wallet name of all existing encryptedSharedPreferences
    fun getAllEncryptedPreferences(context: Context): HashMap<UUID, String> {
        val existingWalletFiles: HashMap<UUID, String> = HashMap<UUID, String>();
        val datDir = context.dataDir.listFiles();
        if (datDir != null) {
            for (dataDir in datDir) {
                if (dataDir.absolutePath.endsWith("vergecurrency.vergewallet/shared_prefs")) {
                    val encryptedSharedPreferences = dataDir.listFiles();
                    if (encryptedSharedPreferences != null && encryptedSharedPreferences.isNotEmpty()) {
                        for (pref in encryptedSharedPreferences) {
                            if (WalletDataIdentifierUtils.isEncryptedSharedPreferences(pref.name)) {
                                val uuid = WalletDataIdentifierUtils.getUUIDFromPrefixedFileName(pref.name);
                                EncryptedPreferencesManager.getOrCreateEncryptedSharedPreferences(context, uuid)
                                if (String(EncryptedPreferencesManager.walletId!!).equals(WalletDataIdentifierUtils.getUUIDFromPrefixedFileName(pref.name).toString())) {
                                    existingWalletFiles.put(WalletDataIdentifierUtils.getUUIDFromPrefixedFileName(pref.name), String(EncryptedPreferencesManager.walletName!!))
                                }
                            }
                        }
                    }
                }
            }
        }
        return existingWalletFiles;
    }

    private fun getOrCreateVergeDataRealm(walletId: UUID) {
        val key = Base64.getDecoder().decode(EncryptedPreferencesManager.realmEncryptionKey)
        val config = RealmConfiguration.Builder()
                .name(WalletDataIdentifierUtils.getRealmFileNamePrefixedByUUID(walletId))
                .encryptionKey(key)
                .schemaVersion(0)
                .modules(VDroidRealmModule())
                .build()
        this.xvgDataRealm = Realm.getInstance(config);
    }

}