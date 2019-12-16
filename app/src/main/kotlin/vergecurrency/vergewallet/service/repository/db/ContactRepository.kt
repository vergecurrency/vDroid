package vergecurrency.vergewallet.service.repository.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import vergecurrency.vergewallet.service.model.Contact

class ContactRepository(context: Context, name: String, factory: SQLiteDatabase.CursorFactory, version: Int) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + "INTEGER PRIMARYKEY, " + COLUMN_NAME + "TEXT," + COLUMN_ADDRESS + "TEXT )"
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    fun loadHandler(): String {
        val result = StringBuilder()
        val query = "Select * from $TABLE_NAME"
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val result_id = cursor.getInt(0)
            val result_name = cursor.getString(1)
            val result_address = cursor.getString(2)
            result.append(result_id).append(" ").append(result_name).append(" ").append(result_address).append(System.getProperty("line.separator"))
        }

        cursor.close()
        db.close()
        return result.toString()
    }

    fun addContact(contact: Contact) {
        val values = ContentValues()
        values.put(COLUMN_ID, contact.contactId)
        values.put(COLUMN_NAME, contact.contactName)
        values.put(COLUMN_ADDRESS, contact.contactAddress)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }


    fun getContactByName(contactName: String): Contact? {
        val query = "Select * from" + TABLE_NAME + "WHERE" + COLUMN_NAME + "=" + contactName

        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        var contact: Contact? = Contact()
        if (cursor.moveToFirst()) {
            cursor.moveToFirst()
            contact!!.contactId = Integer.parseInt(cursor.getString(0))
            contact.contactName = cursor.getString(1)
            contact.contactAddress = cursor.getString(2)
            cursor.close()
        } else
            contact = null
        db.close()

        return contact
    }

    fun deleteHandler(id: Int): Boolean {

        var result = false
        val query = "Select * from " + TABLE_NAME + "WHERE" + COLUMN_ID + "=" + id


        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(cursor.getString(0)))
            cursor.close()
            result = true
        }

        db.close()
        return result

    }

    fun updateHandler(id: Int, contactName: String, contactAddress: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID, id)
        values.put(COLUMN_NAME, contactName)
        values.put(COLUMN_ADDRESS, contactAddress)

        return db.update(TABLE_NAME, values, "$COLUMN_ID=$id", null) > 0

    }

    companion object {

        //Database Information
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "verge.db"
        private val TABLE_NAME = "Contact"
        private val COLUMN_ID = "id"
        private val COLUMN_NAME = "name"
        private val COLUMN_ADDRESS = "address"
    }

}
