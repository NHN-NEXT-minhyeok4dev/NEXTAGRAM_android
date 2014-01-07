package org.nhnnext.nextagram_android;

import java.io.File;

import com.example.nextagram_android.R;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class ContentWrapperActivity extends Activity {

	private ImageView contentImage;
	private String contentNumber;
	private TextView detailText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contentwrapper);
		Dao dao = new Dao(getApplicationContext());
		contentNumber = getIntent().getExtras().getString("ID");
		ListData tlData = dao.getDataByID(Integer.parseInt(contentNumber));
		contentImage = (ImageView)findViewById(R.id.imageView_upload);
		detailText = (TextView)findViewById(R.id.detail_text);
		
		setTitle(tlData.getContents());
		detailText.setText("\nID : " + tlData.getId() +
				"\nOwner : " + tlData.getOwner() +
				"\nContents : " + tlData.getContents() +
				"\nImgName : " + tlData.getImgName());
		
		
		String img_path = getApplicationContext().getFilesDir().getPath() + "/" + tlData.getImgName();
		File img_load_path = new File(img_path);
		
		if(img_load_path.exists()){
			Bitmap bitmap = BitmapFactory.decodeFile(img_path);
			contentImage.setImageBitmap(bitmap);
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.content, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.action_delete:
			Dao dao = new Dao(getApplicationContext());
			dao.deleteDataByID(Integer.parseInt(contentNumber));
			finish();
			break;
		}
		return true;
	}

}
