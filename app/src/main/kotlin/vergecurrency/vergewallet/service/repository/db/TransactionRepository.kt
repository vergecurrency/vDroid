package vergecurrency.vergewallet.service.repository.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import vergecurrency.vergewallet.service.model.wallet.TxHistory
import java.util.*

class TransactionRepository(context: Context, name: String, factory: SQLiteDatabase.CursorFactory, version: Int) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = ("CREATE TABLE " + TABLE_NAME
                + " (" + COLUMN_ID + "INTEGER PRIMARYKEY,"
                + COLUMN_TXID + "TEXT,"
                + COLUMN_ACTION + "TEXT,"
                + COLUMN_AMOUNT + "INTEGER,"
                + COLUMN_FEES + "INTEGER,"
                + COLUMN_TIME + "INTEGER,"
                + COLUMN_CONFIRMATIONS + "INTEGER,"
                + COLUMN_BLOCKHEIGHT + "INTEGER,"
                + COLUMN_FEEPERKB + "INTEGER,"
                + COLUMN_SAVEDADDRESS + "TEXT,"
                + COLUMN_CREATEDON + "INTEGER,"
                + COLUMN_MESSAGE + "TEXT,"
                + COLUMN_ADDRESSTO + "TEXT"
                + " )")
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    fun addTx(tx: TxHistory) {
        val values = ContentValues()
        values.put(COLUMN_TXID, tx.txid)
        values.put(COLUMN_ACTION, tx.action)
        values.put(COLUMN_AMOUNT, tx.amount)
        values.put(COLUMN_FEES, tx.fees)
        values.put(COLUMN_TIME, tx.time)
        values.put(COLUMN_CONFIRMATIONS, tx.confirmations)
        values.put(COLUMN_BLOCKHEIGHT, tx.blockheight)
        values.put(COLUMN_FEEPERKB, tx.feePerKb)
        values.put(COLUMN_SAVEDADDRESS, tx.savedAddress)
        values.put(COLUMN_CREATEDON, tx.createdOn)
        values.put(COLUMN_MESSAGE, tx.message)
        values.put(COLUMN_ADDRESSTO, tx.addressTo)

        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }


    fun getByAddress(address: String): ArrayList<TxHistory> {
        val query = "Select * from" + TABLE_NAME + "WHERE" + COLUMN_SAVEDADDRESS + "like" + address

        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)

        val results = ArrayList<TxHistory>()

        if (cursor.moveToFirst()) {
            cursor.move(-1)
            while (!cursor.isLast) {
                cursor.moveToNext()
                val txhist = TxHistory()

                txhist.txid = cursor.getString(1)
                txhist.action = cursor.getString(2)
                txhist.amount = cursor.getLong(3)
                txhist.fees = cursor.getLong(4)
                txhist.time = cursor.getLong(5)
                txhist.confirmations = cursor.getLong(6)
                txhist.blockheight = cursor.getLong(7)
                txhist.feePerKb = cursor.getLong(8)
                txhist.inputs = emptyArray()
                txhist.outputs = emptyArray()
                txhist.savedAddress = cursor.getString(9)
                txhist.createdOn = cursor.getLong(10)
                txhist.message = cursor.getString(11)
                txhist.addressTo = ""

                results.add(txhist)
            }
        }
        cursor.close()
        return results
    }

    companion object {

        //Database Information
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "verge.db"
        private val TABLE_NAME = "Transactions"
        private val COLUMN_ID = "id"
        private val COLUMN_TXID = "txid"
        private val COLUMN_ACTION = "action"
        private val COLUMN_AMOUNT = "amount"
        private val COLUMN_FEES = "fees"
        private val COLUMN_TIME = "time"
        private val COLUMN_CONFIRMATIONS = "confirmations"
        private val COLUMN_BLOCKHEIGHT = "blockheight"
        private val COLUMN_FEEPERKB = "feePerKb"
        private val COLUMN_SAVEDADDRESS = "savedAddress"
        private val COLUMN_CREATEDON = "createdOn"
        private val COLUMN_MESSAGE = "message"
        private val COLUMN_ADDRESSTO = "addressTo"
    }


}
