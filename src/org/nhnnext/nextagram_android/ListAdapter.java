package org.nhnnext.nextagram_android;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.nextagram_android.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
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
		TextView commNum = (TextView)row.findViewById(R.id.tvCommentNum);
		
		content.setText(listData.get(position).getContents());
		commNum.setText(String.valueOf(listData.get(position).getCommentNum()));
		
		InputStream is;
		try {
			is = context.getAssets().open(listData.get(position).getImgName());
			Drawable d = Drawable.createFromStream(is, null);
			img.setImageDrawable(d);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return row;
	}
}
