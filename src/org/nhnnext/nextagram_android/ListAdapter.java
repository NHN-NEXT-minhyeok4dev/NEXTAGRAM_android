package org.nhnnext.nextagram_android;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ListAdapter extends ArrayAdapter<ListData>{

	private Context context;
	private int resource;
	private List<ListData> listData;

	public ListAdapter(Context context, int resource, List<ListData> listData) {
		super(context, resource, listData);
		// TODO Auto-generated constructor stub
		
		this.context = context;
		this.resource = resource;
		this.listData = listData;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = convertView;
		
		if(row == null){
			LayoutInflater li = ((Activity)context).getLayoutInflater();
			row = li.inflate(resource, parent, false);
		}
		
		return row;
	}
}
