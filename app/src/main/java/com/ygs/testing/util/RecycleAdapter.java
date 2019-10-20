package com.ygs.testing.util;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ygs.testing.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import static java.security.AccessController.getContext;

/**
 * @author Ihor Yutsyk
 *class operate with scrollable list of user queries to lose energy
 * */
public class RecycleAdapter extends   RecyclerView.Adapter<RecycleAdapter.StatusViewHolder> {
private List<Status> mDataset;


    /**
     * Class needed for placing data from bd to layout
     * provide a reference to the views for each data item
     * provide access to all the views for a data item in a view holder
     * <p>
     *     {@link #id} used for show id of energy query
     * </p>
     * <p>
     *     {@link #date} show date and time of energy query from user
     * </p>
     *  <p>
     * {@link #status} show status of energy query from user if it was failed try then status show 0,otherwise 1 - status try succeeded
     *  </p>
     */

public static class StatusViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    public TextView status;
    public TextView id;
    public TextView date;
    /**
     * @param v get`s status_view layout, then define textView var`s
     *
     * */
    public StatusViewHolder(View v) {
        super(v);
        id = v.findViewById(R.id.id);
        status = v.findViewById(R.id.energy_status);
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
        Log.i("STATS BIND",status.toString());
        holder.id.setText(String.valueOf(status.getId()));
        holder.status.setText(String.valueOf(status.getStatus()) );
        holder.date.setText(status.getStringedDate());




    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
