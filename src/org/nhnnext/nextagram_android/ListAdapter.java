package org.nhnnext.nextagram_android;

import java.io.File;
import java.util.ArrayList;

import com.example.nextagram_android.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<ListData>{

	private Context context;
	private int resource;
	private ArrayList<ListData> listData;

	public ListAdapter(Context context, int resource, ArrayList<ListData> listData) {
		super(context, resource, listData);
		
		this.context = context;
		this.resource = resource;
		this.listData = listData;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		
		if(row == null){
			LayoutInflater li = ((Activity)context).getLayoutInflater();
			row = li.inflate(resource, parent, false);
		}
		ImageView img = (ImageView)row.findViewById(R.id.image);
		TextView content = (TextView)row.findViewById(R.id.tvContent);
		
		content.setText(listData.get(position).getContents());

		String img_path = context.getFilesDir().getPath() + "/" + listData.get(position).getImgName();
		File img_load_path = new File(img_path);
		if(img_load_path.exists()){
			Bitmap bitmap = BitmapFactory.decodeFile(img_path);
			img.setImageBitmap(bitmap);
		}
		return row;
	}
}
