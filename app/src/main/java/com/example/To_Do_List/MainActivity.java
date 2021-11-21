package com.example.To_Do_List;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    EditText item;
    Button add;
    ListView listView;
    ArrayList<String> itemlist=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        item=findViewById(R.id.editTextTextPersonName2);
        add=findViewById(R.id.button2);
        listView=findViewById(R.id.list);
        itemlist=database.readData(this);
        arrayAdapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemlist);
        listView.setAdapter(arrayAdapter);
        add.setOnClickListener(view -> {
            String itemname=item.getText().toString();
            itemlist.add(itemname);
            item.setText("");
            database.writeData(itemlist,getApplicationContext());
            arrayAdapter.notifyDataSetChanged();
        });
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("Delete");
            alert.setMessage("Do you want to delete this item from list?");
            alert.setCancelable(false);
            alert.setNegativeButton("No", (dialogInterface, i1) -> dialogInterface.cancel());
            alert.setPositiveButton("Yes", (dialogInterface, i12) -> {
                itemlist.remove(i);
                arrayAdapter.notifyDataSetChanged();
                database.writeData(itemlist,getApplicationContext());
            });
            AlertDialog alertDialog=alert.create();
            alertDialog.show();
        });
    }
}