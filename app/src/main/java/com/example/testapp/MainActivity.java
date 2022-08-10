package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ParsingData obj;
    private Intent intent;
    private int listId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        // New Thread Is Started To Parse JSON file
        // Application Is Waiting For Thread To Finish Execution
        //
        Runnable runParsing = new Runnable() {
            @Override
            public void run() {
                obj = new ParsingData();
            }
        };
        Thread thread = new Thread(runParsing);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //
        // List Of Items Is Created
        // Every Item Directs To ItemsActivity.java And Passing Relevant Data
        //
        ListView listView = findViewById(R.id.listview);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.activity_item_list, R.id.listItems, obj.getDataKeys());
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                intent = new Intent(MainActivity.this, ItemsActivity.class);
                listId = (int) obj.getDataKeys().get(position);
                intent.putExtra("TITLE_OF_ITEMS", "Items with ListId: " + listId);
                intent.putExtra("LIST_OF_ITEMS", obj.getData().get(listId));
                startActivity(intent);
            }
        });


    }

}