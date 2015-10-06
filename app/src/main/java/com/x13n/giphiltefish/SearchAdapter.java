package com.x13n.giphiltefish;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.x13n.giphiltefish.models.GiphyImageItem;
import com.x13n.giphiltefish.models.RecyclerItem;
import com.x13n.giphiltefish.models.SectionTitleItem;
import com.x13n.giphiltefish.net.giphy.model.GiphyImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides views for the search fragment RecyclerView.
 *
 * Created by alex on 05/10/15.
 */
public class SearchAdapter extends android.support.v7.widget.RecyclerView.Adapter<RecyclerItem.ViewHolder> {

    /**
     * A list representing the items that the recycler view will render.
     */
    private final List<RecyclerItem> mModel;

    /**
     * Given a list of new images fetched from the server, append them to the list.
     */
    public void showResults(String title, List<GiphyImage> images) {
        // Remove loading indicator or results if they exist.

        mModel.add(new SectionTitleItem(title));

        for (GiphyImage image : images) {
            mModel.add(new GiphyImageItem(image));
        }

        int newItemCount = images.size() + 1;

        notifyItemRangeInserted(mModel.size() - newItemCount, newItemCount);
    }

    public SearchAdapter() {
        mModel = new ArrayList<>();
    }

    @Override
    public RecyclerItem.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // I'm using layout resource IDs for view type integers as suggested by the Google API docs,
        // because the view type is a layout resource it can be passed straight to the inflater.
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);

        if (viewType == R.layout.item_giphy_image) {
            return new GiphyImageItem.ViewHolder(view);
        } else if (viewType == R.layout.item_section_title) {
            return new SectionTitleItem.ViewHolder(view);
        }

        throw new RuntimeException("Unknown view type in onCreateViewHolder: " + viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerItem.ViewHolder holder, int position) {
        RecyclerItem item = mModel.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemViewType(int position) {
        return mModel.get(position).getLayoutId();
    }

    @Override
    public int getItemCount() {
        return mModel.size();
    }
}
