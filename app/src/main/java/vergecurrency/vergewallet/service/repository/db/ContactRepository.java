package vergecurrency.vergewallet.service.repository.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import vergecurrency.vergewallet.service.model.Contact;

public class ContactRepository extends SQLiteOpenHelper {
	
	//Database Information
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "verge.db";
	private static final String TABLE_NAME = "Contact";
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_ADDRESS = "address";
	
	public ContactRepository(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
			super(context,DATABASE_NAME, factory, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + "INTEGER PRIMARYKEY, " + COLUMN_NAME + "TEXT," + COLUMN_ADDRESS + "TEXT )";
		db.execSQL(CREATE_TABLE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	public String loadHandler() {
		String result = "";
		String query =  "Select * from " + TABLE_NAME;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			int result_id = cursor.getInt(0);
			String result_name = cursor.getString(1);
			String result_address = cursor.getString(2);
			result += String.valueOf(result_id) + " " + result_name + " " + result_address + System.getProperty("line.separator");
		}
		
		cursor.close();
		db.close();
		return result;
	}
	
	public void addContact(Contact contact) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, contact.getContactId());
		values.put(COLUMN_NAME, contact.getContactName());
		values.put(COLUMN_ADDRESS, contact.getContactAddress());
		SQLiteDatabase db = this.getWritableDatabase();
		db.insert(TABLE_NAME, null, values);
		db.close();
	}
	
	
	public Contact getContactByName(String contactName) {
		String query = "Select * from" + TABLE_NAME + "WHERE"+ COLUMN_NAME + "=" + contactName ;
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		Contact contact = new Contact();
		if(cursor.moveToFirst()){
			cursor.moveToFirst();
			contact.setContactId(Integer.parseInt(cursor.getString(0)));
			contact.setContactName(cursor.getString(1));
			contact.setContactAddress(cursor.getString(2));
			cursor.close();
		} else contact = null;
		db.close();
		
		return contact;
	}
	
	public boolean deleteHandler(int id) {
		
		boolean result = false;
		String query = "Select * from " + TABLE_NAME + "WHERE" + COLUMN_ID + "=" + id;
		
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst()) {
			db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[] {cursor.getString(0)});
			cursor.close();
			result = true;
		}
		
		db.close();
		return result;
		
	}
	public boolean updateHandler(int id, String contactName, String contactAddress) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, id);
		values.put(COLUMN_NAME, contactName);
		values.put(COLUMN_ADDRESS, contactAddress);
		
		return db.update(TABLE_NAME, values, COLUMN_ID + "=" +id, null) > 0;
		
	}

}
