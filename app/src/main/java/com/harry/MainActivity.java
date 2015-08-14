package com.harry;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.provider.UserDictionary;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.net.URI;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    ArrayList<String> result = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView list = (ListView) findViewById(R.id.list);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, result);
        list.setAdapter(adapter);

        final EditText add = (EditText) findViewById(R.id.edit);
        Button create = (Button) findViewById(R.id.button1);
        final Button read = (Button) findViewById(R.id.button2);
        Button update = (Button) findViewById(R.id.button3);
        Button delete = (Button) findViewById(R.id.button4);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editAdd = add.getText().toString();
//                URI uri = UserDictionary.Words.CONTENT_URI;
                ContentValues contentValues = new ContentValues();
                contentValues.put(UserDictionary.Words.WORD, editAdd);
                contentValues.put(UserDictionary.Words.FREQUENCY,1);
                getContentResolver().insert(UserDictionary.Words.CONTENT_URI, contentValues);
            }
        });
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] arr = {UserDictionary.Words.WORD,UserDictionary.Words.FREQUENCY};
                Cursor cursor = getContentResolver().query(UserDictionary.Words.CONTENT_URI, arr, null, null, null);
                cursor.moveToFirst();
                result.clear();
//                adapter.clear();
                while (cursor.moveToNext()) {
                    String s = cursor.getString(cursor.getColumnIndex(UserDictionary.Words.WORD));
                    String s2 = cursor.getString(cursor.getColumnIndex(UserDictionary.Words.FREQUENCY));

                    result.add(s+" "+s2);
                }
                adapter.notifyDataSetChanged();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
