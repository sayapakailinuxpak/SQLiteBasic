package com.eldisproject.sqlitebasic.adapter;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eldisproject.sqlitebasic.R;
import com.eldisproject.sqlitebasic.database.Scheme;

public class RecyclerItemsAdapter extends RecyclerView.Adapter<RecyclerItemsAdapter.ViewHolder> {
    private Context context;
    private Cursor cursor;

    public RecyclerItemsAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public RecyclerItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerItemsAdapter.ViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)){
            return;
        }

        long id = cursor.getLong(cursor.getColumnIndex(Scheme.ItemEntry._ID));
        int amount =  cursor.getInt(cursor.getColumnIndex(Scheme.ItemEntry.COLUMN_ITEM_AMOUNT));
        String name = cursor.getString(cursor.getColumnIndex(Scheme.ItemEntry.COLUMN_ITEM_NAME));

        holder.textViewItemAmount.setText(String.valueOf(amount));
        holder.textViewItemName.setText(name);
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount()-1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewItemAmount, textViewItemName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItemAmount = itemView.findViewById(R.id.text_amount_item);
            textViewItemName = itemView.findViewById(R.id.text_item_name);
        }
    }


    //method untuk cursor
    public void swapCursor(Cursor cursor){
        if (cursor != null){
            cursor.close();
        }

        this.cursor = cursor;

        if (cursor != null){
            notifyDataSetChanged();
        }

    }
}
