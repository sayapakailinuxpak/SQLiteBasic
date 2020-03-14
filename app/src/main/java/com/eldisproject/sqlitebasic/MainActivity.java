package com.eldisproject.sqlitebasic;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eldisproject.sqlitebasic.adapter.RecyclerItemsAdapter;
import com.eldisproject.sqlitebasic.database.ItemDBHelper;
import com.eldisproject.sqlitebasic.database.Scheme;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //ui component
    RecyclerView recyclerViewItems;
    EditText editTextItemName;
    TextView textViewItemAmount;
    Button buttonIncrease, buttonDecrease, buttonAdd;

    //miscellanuous
    RecyclerItemsAdapter recyclerItemsAdapter;
    SQLiteDatabase sqLiteDatabase;

    int amount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openGateToDatabase();
        initAndroidToolkitUI();
        swipingToRemoveItem();
        connectRecyclerToAdapter();

    }

    private void initAndroidToolkitUI(){
        editTextItemName = findViewById(R.id.edt_input_item);
        textViewItemAmount = findViewById(R.id.text_count_item);
        buttonIncrease = findViewById(R.id.btn_increase);
        buttonDecrease = findViewById(R.id.btn_decrease);
        buttonAdd = findViewById(R.id.btn_add);
        recyclerViewItems = findViewById(R.id.rv_items);

        buttonIncrease.setOnClickListener(this);
        buttonDecrease.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);
    }

    private void openGateToDatabase(){
        ItemDBHelper itemDBHelper = new ItemDBHelper(this);
        sqLiteDatabase = itemDBHelper.getWritableDatabase(); //we can write(modified) database
    }

    private void connectRecyclerToAdapter(){
        recyclerItemsAdapter = new RecyclerItemsAdapter(this, getAllItems());
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewItems.setAdapter(recyclerItemsAdapter);
    }

    private Cursor getAllItems(){
        return sqLiteDatabase.query(Scheme.ItemEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                Scheme.ItemEntry.COLUMN_TIMSTAMP + " DESC");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_decrease :
                decrease();
                break;
            case R.id.btn_increase :
                increase();
                break;
            case R.id.btn_add :
                addItem();
                break;
        }
    }

    private void decrease(){
        if (amount > 0){
            amount--;
            textViewItemAmount.setText(String.valueOf(amount));
        }
    }

    private void increase(){
        amount++;
        textViewItemAmount.setText(String.valueOf(amount));
    }

    private void addItem(){
        //if no input then return nothing
        if (editTextItemName.getText().toString().trim().length() == 0 || amount == 0){
            return;
        }

        String itemName = editTextItemName.getText().toString().trim();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Scheme.ItemEntry.COLUMN_ITEM_AMOUNT, amount); //column, isi
        contentValues.put(Scheme.ItemEntry.COLUMN_ITEM_NAME, itemName); //column, isi

        //inserting
        sqLiteDatabase.insert(Scheme.ItemEntry.TABLE_NAME, null, contentValues);
        recyclerItemsAdapter.swapCursor(getAllItems());

        editTextItemName.getText().clear(); //clearing all text
    }

    private void removeItem(long id){
        sqLiteDatabase.delete(Scheme.ItemEntry.TABLE_NAME, Scheme.ItemEntry._ID + "=" + id, null);
    }

    private void swipingToRemoveItem(){
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((Long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerViewItems);
    }


    //release all resource from memory
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sqLiteDatabase.isOpen()){
            sqLiteDatabase.close();
        }

        sqLiteDatabase.close();
    }
}
