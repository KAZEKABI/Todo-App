;package com.cranelabs.todoapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import androidx.annotation.NonNull;
import com.cranelabs.todoapp.MainActivity;
import com.cranelabs.todoapp.models.TodoModel;
import com.cranelabs.todoapp.R;
import com.cranelabs.todoapp.AddNewTask;
import com.cranelabs.todoapp.utils.DatabaseHelper;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;
import com.cranelabs.todoapp.adapter.TodoAdapter.MyViewHolder;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder> {
	
	private List<TodoModel> mList;
	private MainActivity activity;
	private DatabaseHelper myDb;
	
	public TodoAdapter(DatabaseHelper myDb, MainActivity activity){
		this.myDb = myDb;
		this.activity = activity;
	}
	
	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
		return new MyViewHolder(v);
	}
	
	@Override
	public void onBindViewHolder(MyViewHolder holder, int position){
		final TodoModel item = mList.get(position);
		holder.mCheckBox.setText(item.getTask());
		holder.mCheckBox.setChecked(toBoolean(item.getStatus()));
		holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				if (isChecked){
					myDb.updateStatus(item.getId(), 1);
					} else {
					myDb.updateStatus(item.getId(), 0);
				}
			}
			
		});
		
	}
	
	public boolean toBoolean(int num){
		return num != 0;
	}
	
	public Context getContext(){
		return activity;
	}
	
	public void setTasks(List<TodoModel> mList){
		this.mList = mList;
		notifyDataSetChanged();
	}
	
	public void deletTask(int position){
		TodoModel item = mList.get(position);
		myDb.deleteTask(item.getId());
		mList.remove(position);
		notifyItemRemoved(position);
	}
	
	public void editItem(int position){
		TodoModel item = mList.get(position);
		
		Bundle bundle = new Bundle();
		bundle.putInt("id", item.getId());
		bundle.putString("task", item.getTask());
		
		AddNewTask task = AddNewTask.newInstance();
		task.setArguments(bundle);
		task.show(activity.getSupportFragmentManager(), task.getTag());
		
	}
	@Override
	public int getItemCount() {
		return mList.size();
	}
	
	public static class MyViewHolder extends RecyclerView.ViewHolder {
		CheckBox mCheckBox;
		
		public MyViewHolder(View itemView){
			super(itemView);
			
			mCheckBox = itemView.findViewById(R.id.checkBox);
		}
	}
}