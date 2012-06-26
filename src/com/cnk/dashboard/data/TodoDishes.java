package com.cnk.dashboard.data;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cnk.dashboard.constant.Server;
import com.cnk.dashboard.lib.Http;

public class TodoDishes {
	public class TodoDish {
		int mId;
		int mDishId;
		int mQuantity;
		int mTable;
		
		public TodoDish(int id, int did, int quantity, int table) {
			mId = id;;
			mDishId = did;
			mQuantity = quantity;
			mTable = table;
		}
		
		public int getId() {
			return mDishId;
		}
		
		public int getTable() {
			return mTable;
		}
	}

	private CnkDbHelper mCnkDbHelper;
	private SQLiteDatabase mDb;
	private static List<TodoDish> mList = new ArrayList<TodoDish>();
	private static int mVer;
	
	public TodoDishes(Context context) {
		mCnkDbHelper = new CnkDbHelper(context, CnkDbHelper.DATABASE_NAME,
				null, 1);
		mDb = mCnkDbHelper.getReadableDatabase();
	}
	
	public int refresh() {
		String respond;
		
		respond = Http.get(Server.ORDER_STATUS, "do=get;ver=" + mVer);
		if (respond != null) {
			JSONObject jsonObject;
			Log.d("JSON", respond);
			try {
				jsonObject = new JSONObject(respond);
				int ver = jsonObject.getInt("ver");
				if (ver == mVer) {
					return 1;
				} else {
					mVer = ver;
					mList.clear();
					JSONArray dishList = jsonObject.getJSONArray("dishes");
					for(int i=0;i<dishList.length();i++){ 
                        JSONObject dish = (JSONObject)dishList.opt(i);
                        TodoDish item = new TodoDish(dish.getInt("id"),
                        							 dish.getInt("did"),
                        							 dish.getInt("quan"),
                        							 dish.getInt("tid")); 
                        mList.add(item);
					 }
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;
		} else {
			return -1;
		}
	}
	
	public int count() {
		return mList.size();
	}
	
	public String getDishName(int index) {
		int id = mList.get(index).mDishId;
		String name = getDishNameFromDB(id);
		if (name == null) {
			return "菜名错误";
		}
		return name;
	}
	
	public String getTableName(int index) {
		return Integer.toString(mList.get(index).getTable());
	}
	
	public int getQuantity(int index) {
		return mList.get(index).mQuantity;
	}
	
	public void remove(int index) {
		mList.remove(index);
	}
	
	private String getDishNameFromDB(int id) {
		Cursor cur = mDb.query(CnkDbHelper.DISH_TABLE_NAME, new String[] {CnkDbHelper.DISH_NAME},
				  	CnkDbHelper.DISH_ID + "=" + id, null, null, null, null);
		
		if (cur.moveToNext()) {
			return cur.getString(0);
		}
		return null;
	}
}
