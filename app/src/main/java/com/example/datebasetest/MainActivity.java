package com.example.datebasetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private MyDatabaseHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new MyDatabaseHelper(MainActivity.this,"BookDatabase1",null,1);
        Button button = (Button)findViewById(R.id.data_create);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.getWritableDatabase();
            }
        });
        Button button1 = (Button)findViewById(R.id.add_data);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name","yanchengxu");
                values.put("author","Dan Brown");
                values.put("pages",454);
                values.put("price",16.96);
                db.insert("book",null,values);
                values.clear();
            }
        });
        Button button2 = (Button)findViewById(R.id.update);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price",10.0);
                db.update("book",values,"name = ?",new String[]{"yanchengxu"});
            }
        });
        Button button3 = (Button)findViewById(R.id.delete);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = helper.getWritableDatabase();
                db.delete("book","pages>?",new String[]{"500"});
            }
        });
        Button button4 = (Button)findViewById(R.id.query);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = helper.getWritableDatabase();
                Cursor cursor = db.query("book",null,null,null,null,null,null);
                if(cursor.moveToFirst()) {
                    do {
                        String str = cursor.getString(cursor.getColumnIndex("name"));
                        Log.d("MainActivity.this", str);
                    } while (cursor.moveToNext());
                }
            }
        });
    }
}
