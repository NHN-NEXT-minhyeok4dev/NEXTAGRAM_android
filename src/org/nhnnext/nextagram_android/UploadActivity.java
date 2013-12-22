package org.nhnnext.nextagram_android;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.nextagram_android.R;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.provider.MediaStore.Images;
import android.provider.Settings.Secure;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class UploadActivity extends Activity implements OnClickListener{

	private ImageView image;
	private EditText content;
	private ImageView uploadBtn;
	private String fileName;
	private String filePath;
	private static final int REQUEST_PHOTO_ALBUM = 1;
	private ProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload);
		
		image = (ImageView)findViewById(R.id.iv_uploadmain);
		image.setOnClickListener(this);
		content = (EditText)findViewById(R.id.et_upload);
		
		uploadBtn = (ImageView)findViewById(R.id.iv_uploadokicon);
		uploadBtn.setOnClickListener(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try{
		if(requestCode == REQUEST_PHOTO_ALBUM){
			Uri uri = getRealPathUri(data.getData());
			filePath = uri.toString();
			fileName = uri.getLastPathSegment();
			
			Bitmap bitmap = BitmapFactory.decodeFile(filePath);
			image.setImageBitmap(bitmap);
		}
		}catch(Exception e){
			Log.e("test", "onActivityResult ERROR:" + e);
		}
	}
	
	private Uri getRealPathUri(Uri uri){
		Uri filePathUri = uri;
		if(uri.getScheme().toString().compareTo("content") == 0){
			Cursor cursor = getApplicationContext().getContentResolver().query(uri, null, null, null, null);
			if(cursor.moveToFirst()){
				int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				filePathUri = Uri.parse(cursor.getString(column_index));
			}
		}
		return filePathUri;
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.iv_uploadmain:
			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setType(Images.Media.CONTENT_TYPE);
			intent.setData(Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, REQUEST_PHOTO_ALBUM );
			break;
		case R.id.iv_uploadokicon:
			final Handler handler = new Handler();
			
			new Thread(){
				public void run(){
					String ID = Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
					handler.post(new Runnable() {
						@Override
						public void run() {
							progressDialog = ProgressDialog.show(UploadActivity.this, "", "uploading...");
						}
					});
					
					ListData tlData = new ListData(ID, content.getText().toString(), fileName, 0);
			
					ProxyUP proxyUP = new ProxyUP();
					proxyUP.uploadArticle(tlData, filePath);
				
					Dao dao = new Dao(getApplicationContext());
					Proxy proxy = new Proxy();
					String jsonData = proxy.getJSON();
					dao.insertJsonData(jsonData);	
					
					handler.post(new Runnable() {
						@Override
						public void run() {
						progressDialog.cancel();
						finish();
						}
					});
				}
			}.start();
			break;
		}
	}
	
}
