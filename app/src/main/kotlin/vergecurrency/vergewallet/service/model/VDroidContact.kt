package vergecurrency.vergewallet.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class VDroidContact(@PrimaryKey(autoGenerate = true) val id: Int,
                         @ColumnInfo(name = "address") val address: String?,
                         @ColumnInfo(name = "name") val name: String?)