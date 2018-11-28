package com.uta.eprescription.dao.dbMgr;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uta.eprescription.R;
import com.uta.eprescription.activities.prescMgr.common.ViewPrescriptionActivity;
import com.uta.eprescription.models.Prescription;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<Prescription> mData;
    private String mUserId;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private AdapterView.OnItemLongClickListener mLongClickListener;
    private AdapterView adapterView;
    private Context mContext;


    // data is passed into the constructor
    public RecyclerViewAdapter(Context context, ArrayList<Prescription> data, String userId) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
        this.mUserId= userId;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_prescitem, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Prescription entry = mData.get(position);
        holder.myTextView.setText(entry.getPid());

        holder.parentLayout.setOnClickListener((view) -> {
            Intent intent = new Intent(mContext, ViewPrescriptionActivity.class);
            intent.putExtra("pid", entry.getPid());
            intent.putExtra("userId", mUserId);
            intent.putExtra("userType", mContext.getClass().getSimpleName());
            intent.putExtra("medicine", mData.get(position).getMedicine());
            intent.putExtra("startDate", mData.get(position).getStartDate());
            intent.putExtra("endDate", mData.get(position).getEndDate());
            intent.putExtra("count", mData.get(position).getCount());
            intent.putExtra("power", mData.get(position).getPower());
            intent.putExtra("status", mData.get(position).getStatus());
            mContext.startActivity(intent);
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView myTextView;
        RelativeLayout parentLayout;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.entry);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if(mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View view){
            if(mLongClickListener != null){
                mLongClickListener.onItemLongClick(adapterView, view, getAdapterPosition(), getItemId());
            }
            return true;
        }

    }

    // convenience method for getting data at click position
    public Prescription getItem(int id) {
        return mData.get(id);
    }

    public void setLongClickListener(AdapterView.OnItemLongClickListener itemLongClickListener){
        this.mLongClickListener = itemLongClickListener;
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
}
