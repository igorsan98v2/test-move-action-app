package com.ygs.testing.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ygs.testing.R;

import java.util.Collection;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class RecycleAdapter extends   RecyclerView.Adapter<RecycleAdapter.StatusViewHolder> {
private List<Status> mDataset;

// Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
public static class StatusViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    public TextView status;
    public TextView id;
    public TextView date;
    public StatusViewHolder(View v) {
        super(v);
        id = v.findViewById(R.id.id);
        status = v.findViewById(R.id.status);
        date = v.findViewById(R.id.date);
    }
}

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecycleAdapter(List<Status> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecycleAdapter.StatusViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.status_view, parent, false);

        StatusViewHolder vh = new StatusViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(StatusViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Status status = mDataset.get(position);
        holder.date.setText(status.getStringedDate());
        holder.status.setText(status.getStatus());
        holder.id.setText(status.getId());


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
