package org.nhnnext.nextagram_android;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.nextagram_android.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ContentActivity extends Activity {

	private ListView commentsList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content);
		
		commentsList = (ListView)findViewById(R.id.listView_comments);
		ArrayList<HashMap<String, String>> stringList = new ArrayList<HashMap<String, String>>();
		
		for(int i=0;i<5;i++){
			HashMap<String, String> commMap = new HashMap<String, String>();
			commMap.put("id", "ID");
			commMap.put("comment", "wow!");	
			stringList.add(commMap);
		}
		
		String[] from = {"id", "comment"};
		int[] to = {android.R.id.text1, android.R.id.text2};
		
		SimpleAdapter sa = new SimpleAdapter(this, stringList, android.R.layout.simple_expandable_list_item_2, from, to);
		commentsList.setAdapter(sa);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.content, menu);
		return true;
	}

}
