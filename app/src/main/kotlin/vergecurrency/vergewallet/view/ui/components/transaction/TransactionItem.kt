package vergecurrency.vergewallet.view.ui.components.transaction

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

interface TransactionItem {
    val viewType: Int
    fun getView(inflater: LayoutInflater, convertView: View, parent: ViewGroup, position: Int): View
}
