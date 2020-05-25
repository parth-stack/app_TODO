package com.example.todo;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;
import com.example.todo.data.taskContract;

import java.lang.reflect.Array;

public class recycleViewAdapter extends RecyclerView.Adapter<recycleViewAdapter.viewHolder> {

    ItemOnClickHandler itemOnClickHandler;
    private Cursor mCursor=null;

    public interface ItemOnClickHandler{
        public void onCLick(int position);
    }

    public recycleViewAdapter(ItemOnClickHandler mItemOnClickListener){
        this.itemOnClickHandler=mItemOnClickListener;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        mCursor.moveToPosition(position);
        int idIndex = mCursor.getColumnIndex(taskContract.TaskEntry._ID);
        final int id = mCursor.getInt(idIndex);
        holder.itemView.setTag(id);
        String summary=mCursor.getString(mCursor.getColumnIndex(taskContract.TaskEntry.COLUMN_TASK_SUMMARY));
        String date=mCursor.getString(mCursor.getColumnIndex(taskContract.TaskEntry.COLUMN_TASK_DATE));
        int priority=mCursor.getInt(mCursor.getColumnIndex(taskContract.TaskEntry.COLUMN_TASK_PRIORITY));
        holder.summaryTextView.setText(summary);
        holder.dateTextView.setText(date);
        holder.priorityTextView.setText(Integer.toString(priority));
    }

    @Override
    public int getItemCount() {
        if(mCursor==null){
            return 0;
        }
        return mCursor.getCount();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView summaryTextView;
        public TextView dateTextView;
        public TextView priorityTextView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            summaryTextView=itemView.findViewById(R.id.summary);
            dateTextView=itemView.findViewById(R.id.date);
            priorityTextView=itemView.findViewById(R.id.priority);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            itemOnClickHandler.onCLick(adapterPosition);
        }
    }

    public void swapCursor(Cursor mCursor) {
        this.mCursor = mCursor;
        notifyDataSetChanged();
    }
}
