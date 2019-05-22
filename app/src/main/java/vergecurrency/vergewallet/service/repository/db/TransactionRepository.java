package vergecurrency.vergewallet.service.repository.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

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

	/*txid: entity?.txid ?? "",
            action: entity?.action ?? "moved",
            amount: entity?.amount ?? 0,
            fees: entity?.fees ?? 0,
            time: entity?.time ?? 0,
            confirmations: entity?.confirmations ?? 0,
            blockheight: nil,
            feePerKb: entity?.feePerKb ?? 0,
            inputs: [],
            outputs: [],
            savedAddress: entity?.address ?? "",
            createdOn: entity?.createdOn,
            message: entity?.message,
            addressTo: ""*/

	public TransactionRepository(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
		super(context,DATABASE_NAME, factory, DATABASE_VERSION);
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
				txhist.setAmount(Long.parseLong(cursor.getString(3)));


				results.add(txhist);
			}
		}

		return results;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
