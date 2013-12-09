package org.nhnnext.nextagram_android;

import java.util.ArrayList;

import com.example.nextagram_android.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class TimelineActivity extends Activity {

	private ImageView uploadBtn;
	private ListView tlList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		uploadBtn = (ImageView)findViewById(R.id.Button_upload);
		uploadBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), UploadActivity.class);
				startActivity(intent);				
			}
		});
		
		tlList = (ListView)findViewById(R.id.listView_timeline);
		/*
		ArrayList<String> stringList = new ArrayList<String>();
		
		stringList.add("1");
		stringList.add("2");
		stringList.add("3");
		stringList.add("4");
		stringList.add("5");
		stringList.add("6");
		
		ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringList);
		
		tlList.setAdapter(stringAdapter);
		tlList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> aView, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getBaseContext(), ContentActivity.class);
				startActivity(intent);
			}
		});
		*
		*/
		
		ArrayList<ListData> dataList = new ArrayList<ListData>();
		ListData list1 = new ListData("cont", "1.png");
		dataList.add(list1);
		ListData list2 = new ListData("cont2", "1.png");
		dataList.add(list2);
		ListData list3 = new ListData("cont3", "1.png");
		dataList.add(list3);
		
		tlList.setAdapter(new org.nhnnext.nextagram_android.ListAdapter(this, R.layout.listdata_row, dataList));
		
		tlList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> aView, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getBaseContext(), ContentActivity.class);
				startActivity(intent);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

}
