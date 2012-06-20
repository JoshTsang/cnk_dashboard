package com.cnk.dashboard.data;

import java.util.ArrayList;
import java.util.List;

public class TodoDishes {
	public class TodoDish {
		int mId;
		String mDishName;
		String mTable;
		
		public TodoDish(int id, String name, String table) {
			mId = id;;
			mDishName = name;
			mTable = table;
		}
		
		public String getName() {
			return mDishName;
		}
		
		public String getTable() {
			return mTable;
		}
	}
	
	List<TodoDish> mList = new ArrayList<TodoDish>();
	
	public void refresh() {
		for (int i=0; i<10; i++) {
			mList.add(new TodoDish(0, "菜" + i, "111"));
		}
	}
	
	public int count() {
		return mList.size();
	}
	
	public String getDishName(int index) {
		return mList.get(index).getName();
	}
	
	public String getTableName(int index) {
		return mList.get(index).getTable();
	}
	
	public void remove(int index) {
		mList.remove(index);
	}
}
