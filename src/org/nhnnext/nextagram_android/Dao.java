package org.nhnnext.nextagram_android;

import java.net.HttpURLConnection;
import java.net.URL;
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
					+ "ID integer primary key," + "Owner text not null,"
					+ "Contents text not null," + "ImgName text not null);";
			db.execSQL(sql);
		} catch (Exception e) {
			Log.i("test", "Class Dao : create table err - " + e.getMessage());
		}
	}

	public void insertJsonData(String jsonData) {
		String owner;
		String contents;
		String imgName;
		JSONArray jArr;
		FileDownloader fileDownloader = new FileDownloader(context);

		try {
			deleteAllData();
			jArr = new JSONArray(jsonData);

			for (int i = getLength(); i < jArr.length(); i++) {
				JSONObject jObj = jArr.getJSONObject(i);
				int id = jObj.getInt("id");
				owner = jObj.getString("owner");
				contents = jObj.getString("contents");
				imgName = jObj.getString("fileName");

				String sql = "INSERT INTO UserContent" + " VALUES(" + id
						+ ", '" + owner + "', '" + contents + "', '" + imgName
						+ "');";
				try {
					db.execSQL(sql);
				} catch (Exception e) {
					Log.i("test", "Class Dao - SQL INSERT ERROR ! " + e);
				}
				try {
					fileDownloader.downFile("http://10.73.43.110:8080/images/"
							+ imgName, imgName);
				} catch (Exception e) {
					Log.i("test", "file downloader.downfile err! " + e);
				}

			}
		} catch (JSONException e) {
			Log.i("test", "Class Dao - JSON ERROR - " + e.getMessage());
			e.printStackTrace();
		}
	}

	public ArrayList<ListData> getDataList() {
		ArrayList<ListData> dataList = new ArrayList<ListData>();
		String owner;
		String contents;
		String imgName;
		int id;
		String sql = "SELECT * FROM UserContent;";
		Cursor cursor = db.rawQuery(sql, null);

		while (cursor.moveToNext()) {
			try {
				id = cursor.getInt(0);
				owner = cursor.getString(1);
				contents = cursor.getString(2);
				imgName = cursor.getString(3);
				dataList.add(new ListData(id, owner, contents, imgName));
			} catch (Exception e) {
				Log.i("test", "Class Dao - getdatalist err - " + e.getMessage());
			}
		}
		cursor.close();

		return dataList;

	}

	public int getLength() {
		try {
			String sql = "SELECT * FROM UserContent;";
			Cursor cursor = db.rawQuery(sql, null);

			return cursor.getCount();
		} catch (Exception e) {
			Log.i("test", "Class Dao : getLength() err -  " + e.getMessage());
			return 0;
		}
	}

	public ListData getDataByID(int targetid) {
		ArrayList<ListData> dataList = getDataList();
		int id;
		int cnt = 0;

		String sql = "SELECT * FROM UserContent;";
		Cursor cursor = db.rawQuery(sql, null);

		while (cursor.moveToNext()) {
			try {

				id = cursor.getInt(0);
				if (id == targetid) {
					return dataList.get(cnt);
				}
			} catch (Exception e) {
				Log.i("test", "Class Dao : getdatabyid err - " + e.getMessage());
			}
			cnt++;
		}
		cursor.close();

		Log.i("test", "Class Dao - getdatabyid err : not have contents");
		return null;
	}

	public void deleteDataByID(int targetid) {
		String sql = "DELETE FROM UserContent WHERE ID = " + targetid;

		db.execSQL(sql);
		
		URL url;
		try {
			url = new URL("http://10.73.43.110:8080/timeline/asd/" + targetid + "/delete");
			System.out.println(url);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//			
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Accept", "text/html");
			
			conn.connect();
			System.out.println(conn.getResponseCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteAllData(){
		String sql = "DELETE FROM UserContent;";
		db.execSQL(sql);
		
	}

}
