package org.nhnnext.nextagram_android;

import java.io.File;

import com.example.nextagram_android.R;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ContentWrapperActivity extends Activity implements OnClickListener{

	private ImageView contentImage;
	private TextView tvContents;
	private ImageView deleteBtn;
	private String contentNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contentwrapper);
		Dao dao = new Dao(getApplicationContext());
		contentNumber = getIntent().getExtras().getString("ID");
		ListData tlData = dao.getDataByID(Integer.parseInt(contentNumber));
				
		contentImage = (ImageView)findViewById(R.id.imageView_upload);
		tvContents = (TextView)findViewById(R.id.tv_contents_wrapper);
		tvContents.setText(tlData.getContents());
		
		
		deleteBtn = (ImageView)findViewById(R.id.detail_deleteBtn);
		deleteBtn.setOnClickListener(this);
		String img_path = getApplicationContext().getFilesDir().getPath() + "/" + tlData.getImgName();
		File img_load_path = new File(img_path);
		
		if(img_load_path.exists()){
			Bitmap bitmap = BitmapFactory.decodeFile(img_path);
			contentImage.setImageBitmap(bitmap);
		}
		
		
		/*
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
		*/
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.detail_deleteBtn:
			Dao dao = new Dao(getApplicationContext());
			dao.deleteDataByID(Integer.parseInt(contentNumber));
//			finishActivity(REQUEST_DELETE_FINISHED);
			finish();
			break;
		case R.id.detail_modifyBtn:
			break;
		}
	}

}
