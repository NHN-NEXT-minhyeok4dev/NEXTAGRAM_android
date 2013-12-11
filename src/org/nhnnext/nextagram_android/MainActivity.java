package org.nhnnext.nextagram_android;

import com.example.nextagram_android.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MainActivity extends Activity{

	private ImageView okBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		okBtn = (ImageView)findViewById(R.id.Button_ok);
		okBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent okIntent = new Intent(getApplicationContext(), TimelineActivity.class);
				startActivity(okIntent);
			}
		});
		
	}

}
