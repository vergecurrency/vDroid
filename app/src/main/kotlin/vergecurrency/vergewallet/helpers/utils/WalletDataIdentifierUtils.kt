package vergecurrency.vergewallet.helpers.utils

import android.content.Context
import vergecurrency.vergewallet.service.model.DataManager
import java.util.*


object WalletDataIdentifierUtils {
    //UUID version 4 based
    private val xvgDataRealmPattern: Regex = Regex("xvg-data-[0-9A-F]{8}-[0-9A-F]{4}-[4][0-9A-F]{3}-[89AB][0-9A-F]{3}-[0-9A-F]{12}\\.realm", RegexOption.IGNORE_CASE)
    private val xvgDataEncryptedSharedPreferencesPattern: Regex = Regex("xvg-data-[0-9A-F]{8}-[0-9A-F]{4}-[4][0-9A-F]{3}-[89AB][0-9A-F]{3}-[0-9A-F]{12}\\.xml", RegexOption.IGNORE_CASE)
    private val uuidV4Pattern: Regex = Regex(".*([0-9A-F]{8}-[0-9A-F]{4}-[4][0-9A-F]{3}-[89AB][0-9A-F]{3}-[0-9A-F]{12}).*", RegexOption.IGNORE_CASE)
    private const val xvgDataPrefix = "xvg-data-";

    fun getMasterKeyAlias(uuid: UUID): String {
        return "xvg_master_key-${uuid}";
    }

    fun isRealm(walletDataId: String): Boolean {
        return xvgDataRealmPattern.matches(walletDataId);
    }

    fun isEncryptedSharedPreferences(walletDataId: String): Boolean {
        return xvgDataEncryptedSharedPreferencesPattern.matches(walletDataId);
    }

    fun getWalletDataIdPrefixedByUUID(uuid: UUID): String {
        return "$xvgDataPrefix${uuid}";
    }

    fun getRealmFileNamePrefixedByUUID(uuid: UUID): String {
        return "$xvgDataPrefix${uuid}.realm";
    }

    fun containsUUID(uuid: UUID, context: Context): Boolean {
        return DataManager.getAllEncryptedPreferences(context)[uuid] != null
    }

    fun getUnusedUUID(context: Context): UUID {
        var uuid: UUID = UUID.randomUUID()
        while (containsUUID(uuid, context)) {
            uuid = UUID.randomUUID();
        }
        return uuid;
    }

    @Throws(RuntimeException::class)
    private fun checkVersion(uuid: UUID) {
        if (uuid.version() != 4) {
            throw RuntimeException("UUID must be version 4 uuid: ${uuid.toString()} version : ${uuid.version()}");
        }
    }

    public fun getUUIDFromPrefixedFileName(walletDataId: String): UUID {
        val result = uuidV4Pattern.matchEntire(walletDataId);
        val uuid = UUID.fromString(result?.groups?.get(1)?.value);
        checkVersion(uuid);
        return uuid;
    }
}