package org.nhnnext.nextagram_android;

import java.util.ArrayList;

import com.example.nextagram_android.R;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class TimelineActivity extends Activity{

	protected static final int REQUEST_UPLOAD_FINISHED = 100;
	protected static final int REQUEST_DELETE_FINISHED = 101;
	private ListView tlList;
	private ArrayList<ListData> dataList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		initData();
		setUpTimelineList();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try{
			if(requestCode == REQUEST_UPLOAD_FINISHED || requestCode == REQUEST_DELETE_FINISHED){
				setUpTimelineList();
			}
			}catch(Exception e){
				Log.e("test", "TimelineActivity : onActivityResult ERROR:" + e);
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.action_upload:
			Intent intent = new Intent(getApplicationContext(),
					UploadActivity.class);
			startActivityForResult(intent, REQUEST_UPLOAD_FINISHED );
			break;
		case R.id.action_refresh:
			Toast.makeText(getApplicationContext(), "refreshed", Toast.LENGTH_SHORT).show();
			
			initData();
			break;
		}
		return true;
	}
}
