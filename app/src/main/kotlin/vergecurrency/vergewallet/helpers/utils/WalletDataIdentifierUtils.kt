package vergecurrency.vergewallet.helpers.utils

import java.util.*


object WalletDataIdentifierUtils {
    //UUID version 3 based
    private val xvgDataRealmPattern: Regex = Regex("xvg-data-[0-9A-F]{8}-[0-9A-F]{4}-[3][0-9A-F]{3}-[89AB][0-9A-F]{3}-[0-9A-F]{12}\\.realm", RegexOption.IGNORE_CASE)
    private val xvgDataEncryptedSharedPreferencesPattern: Regex = Regex("xvg-data-[0-9A-F]{8}-[0-9A-F]{4}-[3][0-9A-F]{3}-[89AB][0-9A-F]{3}-[0-9A-F]{12}\\.xml", RegexOption.IGNORE_CASE)
    private val uuidV3Pattern: Regex = Regex(".*([0-9A-F]{8}-[0-9A-F]{4}-[3][0-9A-F]{3}-[89AB][0-9A-F]{3}-[0-9A-F]{12}).*", RegexOption.IGNORE_CASE)
    private const val xvgDataPrefix = "xvg-data-";

    fun getMasterKeyAlias(walletName: String): String {
        return "xvg_master_key-${getUUID(getRealmFileNameByUsersWalletName(walletName))}";
    }

    fun isRealm(walletDataId: String): Boolean {
        return xvgDataRealmPattern.matches(walletDataId);
    }

    fun isEncryptedSharedPreferences(walletDataId: String): Boolean {
        return xvgDataEncryptedSharedPreferencesPattern.matches(walletDataId);
    }

    fun getWalletDataIdByUsersWalletName(walletName: String): String {
        return "$xvgDataPrefix${UUID.nameUUIDFromBytes(walletName.toByteArray())}";
    }

    fun getRealmFileNameByUsersWalletName(walletName: String): String {
        return "$xvgDataPrefix${UUID.nameUUIDFromBytes(walletName.toByteArray())}.realm";
    }

    fun getEncryptedSharedPreferencesNameByUsersWalletName(walletName: String): String {
        return "$xvgDataPrefix${UUID.nameUUIDFromBytes(walletName.toByteArray())}";
    }


    fun isIdEquals(walletDataId1: String, walletDataId2: String): Boolean {
        val u1 = getUUID(walletDataId1);
        val u2 = getUUID(walletDataId2);
        checkVersion(u1);
        checkVersion(u2);
        return u1 == u2;
    }

    fun containsUUID(ids: List<String>) {

    }

    @Throws(RuntimeException::class)
    private fun checkVersion(uuid: UUID) {
        if (uuid.version() != 3) {
            throw RuntimeException("UUID must be version 3 uuid: ${uuid.toString()} version : ${uuid.version()}");
        }
    }

    private fun getUUID(walletDataId: String): UUID {
        val result = uuidV3Pattern.matchEntire(walletDataId);
        val uuid = UUID.fromString(result?.groups?.get(1)?.value);
        checkVersion(uuid);
        return uuid;
    }
}