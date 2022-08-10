package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        Intent intent = getIntent();

        this.setTitle(intent.getStringExtra("TITLE_OF_ITEMS"));

        ListView listView = findViewById(R.id.listview2);

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, intent.getStringArrayListExtra("LIST_OF_ITEMS"));
        listView.setAdapter(arrayAdapter);
    }
}