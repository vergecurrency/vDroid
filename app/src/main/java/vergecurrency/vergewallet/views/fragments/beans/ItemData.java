package vergecurrency.vergewallet.views.fragments.beans;

import android.view.View;

public class ItemData {

    private String title;
    private int imageUrl;
    private View.OnClickListener onClickListener;


    public ItemData(String title, int imageUrl, View.OnClickListener onClickListener) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.onClickListener = onClickListener;


    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {

        this.imageUrl = imageUrl;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
