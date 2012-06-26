package com.cnk.dashboard;

import com.cnk.dashboard.data.TodoDishes;
import com.cnk.dashboard.data.TodoDishesAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class DashboardActivity extends Activity {
	ListView mTodoListView;
	Button mTitleLeft;
	Button mTitleRight;
	
	TodoDishes mTodoDishes;
	TodoDishesAdapter mAdapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mTodoListView = (ListView) findViewById(R.id.todoList);
        mTitleLeft = (Button) findViewById(R.id.back_button);
        mTitleRight = (Button) findViewById(R.id.title_right_btn);
        
        mTitleLeft.setVisibility(View.GONE);
        mTitleRight.setOnClickListener(settingClicked);
        mTodoDishes = new TodoDishes(DashboardActivity.this);
        
        mAdapter = new TodoDishesAdapter(this, mTodoDishes) {

			@Override
			public View getView(int position, View convertView,
					ViewGroup parent) {
				TextView dishName;
				TextView tableName;
				TextView quantity;
				
				if(convertView == null)
				{
					convertView = LayoutInflater
							.from(DashboardActivity.this)
							.inflate(R.layout.item_todo, null);
				}
				
				dishName = (TextView) convertView.findViewById(R.id.dishName);
				tableName = (TextView) convertView.findViewById(R.id.tableName);
				quantity = (TextView) convertView.findViewById(R.id.quantity);
				
				dishName.setText(mTodoDishes.getDishName(position));
				tableName.setText(mTodoDishes.getTableName(position));
				quantity.setText(Integer.toString(
								 mTodoDishes.getQuantity(position)));
				return convertView;
			}
        };
        mTodoListView.setAdapter(mAdapter);
        mTodoListView.setOnItemClickListener(todoListClicked);
        
        new Thread() {
        	public void run() {
        		int ret = mTodoDishes.refresh();
        		handler.sendEmptyMessage(ret);
        	}
        }.start();
    }
    
    private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			mAdapter.notifyDataSetChanged();
		}
	};
	
    private OnItemClickListener todoListClicked
    	= new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long position) {
			// TODO Auto-generated method stub
			mTodoDishes.remove((int)position);
			mAdapter.notifyDataSetChanged();
		}
	};
	
	private OnClickListener settingClicked = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(DashboardActivity.this, UpdateMenuActivity.class);
			startActivity(intent);
		}
	};
}