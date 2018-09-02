package vergecurrency.vergewallet.views.fragments.workers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.views.fragments.beans.HeaderData;
import vergecurrency.vergewallet.views.fragments.beans.ItemData;

public class SettingsListsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public SettingsListsAdapter(HeaderData headerData, ItemData[] itemsData) {
        this.itemsData = itemsData;
        this.headerData = headerData;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        if(viewType == TYPE_HEADER){

            View headerLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_header, null);
            return new VHHeader(headerLayoutView);
        }
        else if(viewType == TYPE_ITEM) {
            View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item, null);

            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO : map each button to an action.
                }
            });


            return new VHItem(itemLayoutView);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure you're using types correctly");
    }

    private ItemData getItem(int position)
    {
        return itemsData[position];
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof VHHeader) {
            VHHeader VHHeader = (VHHeader)holder;
            VHHeader.txtViewTitle.setText(headerData.getTitle());
        } else if(holder instanceof VHItem) {

            VHItem VHItem = (VHItem)holder;
            VHItem.txtViewTitle.setText(itemsData[position-1].getTitle());
            VHItem.imgViewIcon.setImageResource(itemsData[position-1].getImageUrl());
        }
    }




    // inner class to hold a reference to each item of RecyclerView
    public static class VHItem extends RecyclerView.ViewHolder {

        TextView txtViewTitle;
        ImageView imgViewIcon;
        RelativeLayout rel;

        public VHItem(final View itemLayoutView) {
            super(itemLayoutView);
            imgViewIcon = (ImageView) itemLayoutView.findViewById(R.id.listview_item_icon);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.listview_item_title);
            rel = (RelativeLayout) itemLayoutView.findViewById(R.id.listview_layout);

        }
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class VHHeader extends RecyclerView.ViewHolder {

        public TextView txtViewTitle;

        public VHHeader(View itemLayoutView) {
            super(itemLayoutView);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.listview_header_title);
        }
    }

    //    need to override this method
    @Override
    public int getItemViewType(int position) {
        if(isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position)
    {
        return position == 0;
    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.length+1;
    }



    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private HeaderData headerData;
    private ItemData[] itemsData;

}