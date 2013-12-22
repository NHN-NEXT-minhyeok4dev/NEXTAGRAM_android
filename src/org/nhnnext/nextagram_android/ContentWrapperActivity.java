package org.nhnnext.nextagram_android;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.nextagram_android.R;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ContentWrapperActivity extends Activity {

	private ListView commentsList;
	private ImageView contentImage;
	private TextView tvContents;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contentwrapper);
		Dao dao = new Dao(getApplicationContext());
		String contentNumber = getIntent().getExtras().getString("ID");
		ListData tlData = dao.getDataByID(Integer.parseInt(contentNumber));
				
		contentImage = (ImageView)findViewById(R.id.imageView_upload);
		tvContents = (TextView)findViewById(R.id.tv_contents_wrapper);
		tvContents.setText(tlData.getContents());
		
		String img_path = getApplicationContext().getFilesDir().getPath() + "/" + tlData.getImgName();
		File img_load_path = new File(img_path);
		
		if(img_load_path.exists()){
			Bitmap bitmap = BitmapFactory.decodeFile(img_path);
			contentImage.setImageBitmap(bitmap);
		}
		
		
		
		// comment
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

}
