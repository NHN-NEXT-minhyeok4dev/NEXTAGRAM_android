package org.nhnnext.nextagram_android;

import java.util.ArrayList;

import com.example.nextagram_android.R;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class TimelineActivity extends Activity {

	protected static final int REQUEST_UPLOAD_FINISHED = 100;
	protected static final int REQUEST_DELETE_FINISHED = 101;
	private ImageView uploadBtn;
	private ListView tlList;
	private ArrayList<ListData> dataList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		try {
			Dao dao = new Dao(getApplicationContext());
			if(dao.getLength() == 0)
				initData();
			setUpTimelineList();
			setUpBtn();
		} catch (Exception e) {
			Log.i("test", "dao err - " + e.getMessage());
		}
	}

	private void setUpBtn() {
		uploadBtn = (ImageView) findViewById(R.id.Button_upload);
		uploadBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						UploadActivity.class);
				startActivityForResult(intent, REQUEST_UPLOAD_FINISHED );
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try{
			if(requestCode == REQUEST_UPLOAD_FINISHED || requestCode == REQUEST_DELETE_FINISHED){
				setUpTimelineList();
			}
			}catch(Exception e){
				Log.e("test", "onActivityResult ERROR:" + e);
			}
		
	}
	
	private void setUpTimelineList() {
		Dao dao = new Dao(getApplicationContext());
		dataList = dao.getDataList();		
		
		tlList = (ListView) findViewById(R.id.listView_timeline);
		tlList.setAdapter(new ListAdapter(this, R.layout.listdata_row, dataList));
		tlList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> aView, View view,
					int position, long id) {
				Intent intent = new Intent(getBaseContext(),
						ContentWrapperActivity.class);
				intent.putExtra("ID", dataList.get(position).getId()+"");
				startActivity(intent);
			}
		});
	}

	private void initData() {
		// local json test code
		// String testJsonData = dao.getJsonTestData();
		// dao.insertJsonData(testJsonData);

		// proxy json code with Thread
		new Thread(){
			private final Handler handler = new Handler();
			public void run(){
				Dao dao = new Dao(getApplicationContext());
				Proxy proxy = new Proxy();
				String jsonData = proxy.getJSON();
				dao.insertJsonData(jsonData);			
				
				handler.post(new Runnable(){
					public void run(){
						setUpTimelineList();
					}
				});
			}
		}.start();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		setUpTimelineList();
	}

}
