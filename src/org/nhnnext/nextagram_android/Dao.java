package org.nhnnext.nextagram_android;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Dao {
	private Context context;
	SQLiteDatabase db;

	public Dao(Context context) {
		this.context = context;
		db = context.openOrCreateDatabase("localdb.db",
				SQLiteDatabase.CREATE_IF_NECESSARY, null);
		try {
			String sql = "CREATE TABLE IF NOT EXISTS UserContent("
					+ "ID integer primary key autoincrement,"
					+ "Owner text not null," + "Contents text not null,"
					+ "ImgName text not null," + "CommentNum integer not null);";
			db.execSQL(sql);			
		} catch (Exception e) {
			Log.i("test", "create table err - " + e.getMessage());
		}
	}

	public void insertJsonData(String jsonData) {
		String owner;
		String contents;
		String imgName;
		int commentNum;

		JSONArray jArr;
		try {
			jArr = new JSONArray(jsonData);
			for (int i = 0; i < jArr.length(); i++) {
				JSONObject jObj = jArr.getJSONObject(i);

				owner = jObj.getString("Owner");
				contents = jObj.getString("Contents");
				imgName = jObj.getString("ImgName");
				commentNum = jObj.getInt("CommentNum");

				Log.i("test", "Owner : " + owner + " Contents : " + contents);

				String sql = "INSERT INTO UserContent(Owner, Contents, ImgName, CommentNum)"
						+ " VALUES('"
						+ owner
						+ "', '"
						+ contents
						+ "', '"
						+ imgName + "', '"
						+ commentNum + "'" + ");";

				db.execSQL(sql);
				
			}
		} catch (JSONException e) {
			Log.i("test", "JSON ERROR - " + e.getMessage());
			e.printStackTrace();
		}
	}

	public String getJsonTestData() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("");

		sb.append("[");

		sb.append("	 {");
		sb.append("	    'Owner':'owner1',");
		sb.append("	    'Contents':'cont1',");
		sb.append("	    'ImgName':'jinhee.png',");
		sb.append("	    'CommentNum':'0'");
		sb.append("	 },");
		sb.append("	 {");
		sb.append("	    'Owner':'owner2',");
		sb.append("	    'Contents':'cont2',");
		sb.append("	    'ImgName':'jinhee.png',");
		sb.append("	    'CommentNum':'1'");
		sb.append("	 },");
		sb.append("	 {");
		sb.append("	    'Owner':'owner3',");
		sb.append("	    'Contents':'cont3',");
		sb.append("	    'ImgName':'jinhee.png',");
		sb.append("	    'CommentNum':'2'");
		sb.append("	 }");

		sb.append("]");

		return sb.toString();
	}
	
	public ArrayList<ListData> getDataList(){
		ArrayList<ListData> dataList = new ArrayList<ListData>();
		String owner;
		String contents;
		String imgName;
		int commentNum;
		
		String sql = "SELECT * FROM UserContent;";
		Cursor cursor = db.rawQuery(sql, null);
		
		while(cursor.moveToNext()){
			try {
				owner = cursor.getString(1);
				contents = cursor.getString(2);
				imgName = cursor.getString(3);
				commentNum = cursor.getInt(4);
				
				dataList.add(new ListData(owner, contents, imgName, commentNum));
			} catch (Exception e) {
				Log.i("test", "getdatalist err - " + e.getMessage());
			}
		}
		cursor.close();
		
		return dataList;
		
	}
	
}
