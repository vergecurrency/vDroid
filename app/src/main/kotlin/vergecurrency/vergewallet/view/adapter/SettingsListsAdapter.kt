package vergecurrency.vergewallet.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.service.model.SettingsListViewData
import vergecurrency.vergewallet.service.model.SettingsListViewHeader

class SettingsListsAdapter(private val settingsListViewHeader: SettingsListViewHeader, private val itemsData: Array<SettingsListViewData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        // create a new view
        if (viewType == TYPE_HEADER) {

            val headerLayoutView = LayoutInflater.from(parent.context).inflate(R.layout.listview_item_settings_header, parent, false)
            return VHHeader(headerLayoutView)
        } else if (viewType == TYPE_ITEM) {
            val itemLayoutView = LayoutInflater.from(parent.context).inflate(R.layout.listview_item_settings, parent, false)

            itemLayoutView.setOnClickListener {
                View.OnClickListener {
                    //TODO : map each button to an action.
                }
            }


            return VHItem(itemLayoutView)
        }

        throw RuntimeException("there is no type that matches the type $viewType + make sure you're using types correctly")
    }

    private fun getItem(position: Int): SettingsListViewData {
        return itemsData[position]
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is VHHeader) {
            holder.txtViewTitle.text = settingsListViewHeader.title
        } else if (holder is VHItem) {

            holder.txtViewTitle.text = itemsData[position - 1].title
            holder.imgViewIcon.setImageResource(itemsData[position - 1].imageUrl)
            val ocl = itemsData[position - 1].onClickListener
            if (ocl != null) {
                holder.rel.setOnClickListener(ocl)
            }
        }
    }


    // inner class to hold a reference to each item of RecyclerView
    class VHItem(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {

        internal var txtViewTitle: TextView = itemLayoutView.findViewById(R.id.listview_item_title)
        internal var imgViewIcon: ImageView = itemLayoutView.findViewById(R.id.listview_item_icon)
        internal var rel: RelativeLayout = itemLayoutView.findViewById(R.id.listview_layout)

    }

    // inner class to hold a reference to each item of RecyclerView
    class VHHeader(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {

        var txtViewTitle: TextView = itemLayoutView.findViewById(R.id.listview_header_title)

    }

    //    need to override this method
    override fun getItemViewType(position: Int): Int {
        return if (isPositionHeader(position)) TYPE_HEADER else TYPE_ITEM
    }

    private fun isPositionHeader(position: Int): Boolean {
        return position == 0
    }

    // Return the size of your itemsData (invoked by the layout manager)
    override fun getItemCount(): Int {
        return itemsData.size + 1
    }

    companion object {


        private val TYPE_HEADER = 0
        private val TYPE_ITEM = 1
    }

}