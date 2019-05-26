package vergecurrency.vergewallet.service.repository.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import vergecurrency.vergewallet.service.model.vws.InputOutput;
import vergecurrency.vergewallet.service.model.vws.TxHistory;

class TransactionRepository  extends SQLiteOpenHelper {

	//Database Information
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "verge.db";
	private static final String TABLE_NAME = "Transactions";
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_TXID = "txid";
	private static final String COLUMN_ACTION = "action";
	private static final String COLUMN_AMOUNT = "amount";
	private static final String COLUMN_FEES = "fees";
	private static final String COLUMN_TIME = "time";
	private static final String COLUMN_CONFIRMATIONS = "confirmations";
	private static final String COLUMN_BLOCKHEIGHT = "blockheight";
	private static final String COLUMN_FEEPERKB = "feePerKb";
	private static final String COLUMN_SAVEDADDRESS = "savedAddress";
	private static final String COLUMN_CREATEDON = "createdOn";
	private static final String COLUMN_MESSAGE = "message";
	private static final String COLUMN_ADDRESSTO = "addressTo";


	public TransactionRepository(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
		super(context,DATABASE_NAME, factory, DATABASE_VERSION);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
				+ " (" + COLUMN_ID + "INTEGER PRIMARYKEY,"
				+ COLUMN_TXID + "TEXT,"
				+ COLUMN_ACTION+ "TEXT,"
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
				+ " )";
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public void addTx(TxHistory tx) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_TXID,tx.getTxid());
		values.put(COLUMN_ACTION, tx.getAction());
		values.put(COLUMN_AMOUNT,tx.getAmount());
		values.put(COLUMN_FEES, tx.getFees());
		values.put(COLUMN_TIME, tx.getTime());
		values.put(COLUMN_CONFIRMATIONS, tx.getConfirmations());
		values.put(COLUMN_BLOCKHEIGHT, tx.getBlockheight());
		values.put(COLUMN_FEEPERKB, tx.getFeePerKb());
		values.put(COLUMN_SAVEDADDRESS, tx.getSavedAddress());
		values.put(COLUMN_CREATEDON, tx.getCreatedOn());
		values.put(COLUMN_MESSAGE, tx.getMessage());
		values.put(COLUMN_ADDRESSTO, tx.getAddressTo());

		SQLiteDatabase db = this.getWritableDatabase();
		db.insert(TABLE_NAME, null, values);
		db.close();
	}


	public ArrayList<TxHistory> getByAddress(String address) {
		String query = "Select * from" + TABLE_NAME + "WHERE"+ COLUMN_SAVEDADDRESS + "=" + address ;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		ArrayList<TxHistory> results = new ArrayList<>();

		if(cursor.moveToFirst()) {
			cursor.move(-1);
			while(!cursor.isLast()) {
				cursor.moveToNext();
				TxHistory txhist = new TxHistory();

				txhist.setTxid(cursor.getString(1));
				txhist.setAction(cursor.getString(2));
				txhist.setAmount(cursor.getLong(3));
				txhist.setFees(cursor.getLong(4));
				txhist.setTime(cursor.getLong(5));
				txhist.setConfirmations(cursor.getLong(6));
				txhist.setBlockheight(cursor.getLong(7));
				txhist.setFeePerKb(cursor.getLong(8));
				txhist.setInputs(new InputOutput[0]);
				txhist.setOutputs(new InputOutput[0]);
				txhist.setSavedAddress(cursor.getString(9));
				txhist.setCreatedOn(cursor.getLong(10));
				txhist.setMessage(cursor.getString(11));
				txhist.setAddressTo("");

				results.add(txhist);
			}
		}

		return results;
	}

}
