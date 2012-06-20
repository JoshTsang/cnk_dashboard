package com.cnk.dashboard;

import com.cnk.dashboard.data.TodoDishes;
import com.cnk.dashboard.data.TodoDishesAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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
        mTodoDishes = new TodoDishes();
        mTodoDishes.refresh();
        mAdapter = new TodoDishesAdapter(this, mTodoDishes) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				TextView dishName;
				TextView tableName;
				
				if(convertView==null)
				{
					convertView=LayoutInflater.from(DashboardActivity.this).inflate(R.layout.item_todo, null);
				}
				
				dishName = (TextView) convertView.findViewById(R.id.dishName);
				tableName = (TextView) convertView.findViewById(R.id.tableName);
				
				dishName.setText(mTodoDishes.getDishName(position));
				tableName.setText(mTodoDishes.getTableName(position));
				
				return convertView;
				}
        };
        mTodoListView.setAdapter(mAdapter);
        mTodoListView.setOnItemClickListener(todoListClicked);
    }
    
    private OnItemClickListener todoListClicked = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long position) {
			// TODO Auto-generated method stub
			mTodoDishes.remove((int)position);
			mAdapter.notifyDataSetChanged();
		}
	};
}