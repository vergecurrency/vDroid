package vergecurrency.vergewallet.service.repository.db;

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import vergecurrency.vergewallet.service.model.VDroidContact
import vergecurrency.vergewallet.service.model.VDroidTransaction

@Database(entities = arrayOf(VDroidContact::class, VDroidTransaction::class), version = 1)
abstract class VergeWalletAppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDAO
    abstract fun transactionDao(): TransactionDAO


    val MIGRATION_0_1 = object : Migration(0, 1) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE IF NOT EXISTS V_DROID_Contact (ID INTEGER PRIMARY KEY, NAME TEXT, ADDRESS TEXT)")
            database.execSQL("CREATE TABLE IF NOT EXISTS V_DROID_TRANSACTION (ID INTEGER PRIMARY KEY, TX_ID TEXT," +
                    " ACTO TEXT, AMOUNT INTEGER, FEES INTEGER, TIME INTEGER, CONFIRMATIONS INTEGER," +
                    " BLOCK_HEIGHT INTEGER, FEE_PER_KB, CREATED_ON INTEGER, MESSAGE TEXT, ADDRESS_TO TEXT)")
        }
    }
}
