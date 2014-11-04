package com.objectgraph.simpleadapterexample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Hashtable;


public class MyActivity extends Activity {
    SimpleAdapter mAdapter;
    ArrayList<Hashtable<String,String>> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        //---------------------------------------------------------------------------------
        // Build Sample list
        // If the data contains the id of drawable object itself,
        // simple adapter will bind it in the list.
        mList = new ArrayList<Hashtable<String, String>>();
        for (int i=0; i<100; i++) {
            Hashtable<String, String> tmpTable = new Hashtable<String, String>();
            if (i%7==0) {
                tmpTable.put("favorite", Integer.toString(R.drawable.star_selected));
            }
            else {
                tmpTable.put("favorite", Integer.toString(R.drawable.star));
            }
            tmpTable.put("title", "Title " + i);
            tmpTable.put("details", "Details go here blah blah blah hello world one two three?");
            if (i%13==0) {
                tmpTable.put("indicator", Integer.toString(R.drawable.heart));
            }
            else {
                tmpTable.put("indicator", "@null");
            }
            mList.add(tmpTable);
        }

        //---------------------------------------------------------------------------------
        // Binding 1 - data and internal button
        mAdapter = new SimpleAdapter(
                this.getApplicationContext(),
                mList,
                R.layout.cell,
                new String[]{"favorite", "title", "details", "indicator"},
                new int[]{R.id.itemFavorite, R.id.itemTitle, R.id.itemDetails, R.id.itemIndicator}
        ){
            @Override
            public View getView (final int position, View convertView, ViewGroup parent){
                View tmpView = super.getView(position, convertView, parent);
                ImageButton itemFavorite = (ImageButton)tmpView.findViewById(R.id.itemFavorite);
                itemFavorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ImageButton imgBtn = (ImageButton)view;
                        Hashtable<String,String> tbl = mList.get(position);
                        if (tbl.get("favorite").equals(Integer.toString(R.drawable.star_selected))){
                            Log.v("myapp","star is selected");
                            imgBtn.setImageResource(R.drawable.star);
                            tbl.put("favorite", Integer.toString(R.drawable.star));
                        }
                        else {
                            Log.v("myapp","star is not selected");
                            imgBtn.setImageResource(R.drawable.star_selected);
                            tbl.put("favorite", Integer.toString(R.drawable.star_selected));
                        }
                        Log.v("myapp", "clicked favorite button : " + position);
                    }
                });
                return tmpView;
            }
        };

        //---------------------------------------------------------------------------------
        // Binding 2 - for cell clicking
        ListView lvList = (ListView)findViewById(R.id.myListView);
        lvList.setAdapter(mAdapter);
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mList != null && mList.size() > i) {
                    Log.v("myapp", "clicked cell " + mList.get(i));
                }
            }
        });

        // Force render
        mAdapter.notifyDataSetChanged();
    }
}
