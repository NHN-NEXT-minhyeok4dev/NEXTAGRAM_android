package org.nhnnext.nextagram_android;

import java.util.ArrayList;

import com.example.nextagram_android.R;

import android.os.Bundle;
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

	private ImageView uploadBtn;
	private ListView tlList;
	private ArrayList<ListData> dataList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		Dao dao = new Dao(getApplicationContext());
		try {
			
			String testJsonData = dao.getJsonTestData();
			dao.insertJsonData(testJsonData);
		} catch (Exception e) {
			Log.i("test", "dao err - " + e.getMessage());
		}
		uploadBtn = (ImageView) findViewById(R.id.Button_upload);
		uploadBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						UploadActivity.class);
				startActivity(intent);
			}
		});

		tlList = (ListView) findViewById(R.id.listView_timeline);

		dataList = dao.getDataList();

		tlList.setAdapter(new ListAdapter(this, R.layout.listdata_row, dataList));

		tlList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> aView, View view,
					int position, long id) {
				Intent intent = new Intent(getBaseContext(),
						ContentWrapperActivity.class);
				startActivity(intent);
			}
		});

	}

}
