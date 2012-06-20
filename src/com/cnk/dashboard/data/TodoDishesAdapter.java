package com.cnk.dashboard.data;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public class TodoDishesAdapter  extends BaseAdapter {
		private Context mContext;
		private TodoDishes mTodoDishes;
		
		public TodoDishesAdapter(Context context, TodoDishes todoDishes) {
			mContext = context;
			mTodoDishes = todoDishes;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mTodoDishes.count();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return null;
		}
}
