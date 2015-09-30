
package com.manager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import adapters.GridAdapter;
import controller.DataBaseAdapter;

public class GridMenu extends Activity {

    private ArrayList<String> wordId = new ArrayList<String>();
    private ArrayList<Bitmap> imageList = new ArrayList<Bitmap>();
    private ArrayList<String> wordsList = new ArrayList<String>();
    private ArrayList<String> audioPathList = new ArrayList<String>();
//    private ArrayList<Boolean> checkBoxValueList = new ArrayList<Boolean>();
    private DataBaseAdapter dataBaseAdapter;
    private GridView gridView;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_menu);
        gridView   = (GridView) findViewById(R.id.gridView);

        dataBaseAdapter = new DataBaseAdapter(this); //giving the current context
        dataBaseAdapter = dataBaseAdapter.open();    //opening writable database

        cursor = dataBaseAdapter.getAllWords();
        loadWordsInListView(); // load data in listView

        cursor.close();
        dataBaseAdapter.close();
        gridView.setFocusableInTouchMode(true);
        gridView.setFocusable(true);
        gridView.setAdapter(new GridAdapter(this, wordId, imageList, wordsList)); //set custom ListAdapter

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Log.v("click", "onItemClick:position=" + position + ", id=" + id);
                return;
            }
        });
    }
    public void loadWordsInListView(){
        cursor = dataBaseAdapter.getAllWords();
        if (cursor.moveToFirst())
        {
            while (!cursor.isAfterLast()) //if cursor reaches after last
            {
                long wordId = cursor.getLong(cursor.getColumnIndex("id"));

                wordsList.add(cursor.getString(cursor.getColumnIndex("word")));

                String imagePath = GridAdapter.folderPath + "/" + wordId + ".bmp";
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                imageList.add(bitmap);

                cursor.moveToNext();
            }//end while
        }//end if
    }//end loadWordsInListView

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), LauncherActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        dataBaseAdapter.close();
        startActivity(i);
        finish();
        super.onBackPressed();
    }

}
