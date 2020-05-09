package vergecurrency.vergewallet.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VDroidTransaction(@PrimaryKey(autoGenerate = true) val id: Int,
                             @ColumnInfo(name = "txid") val address: String,
                             @ColumnInfo(name = "acto") val action: String,
                             @ColumnInfo(name = "amount") val amount: Integer,
                             @ColumnInfo(name = "fees") val fees: Integer,
                             @ColumnInfo(name = "time") val time: Integer,
                             @ColumnInfo(name = "confirmations") val confirmations: Integer,
                             @ColumnInfo(name = "blockheight") val blockheight: Integer,
                             @ColumnInfo(name = "feePerKb") val feePerKb: Integer,
                             @ColumnInfo(name = "savedAddress") val savedAddress: String,
                             @ColumnInfo(name = "createdOn") val createdOn: Integer,
                             @ColumnInfo(name = "message") val message: String,
                             @ColumnInfo(name = "addressTo") val addressTo: String

) {

}