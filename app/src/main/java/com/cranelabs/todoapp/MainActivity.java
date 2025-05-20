package com.cranelabs.todoapp;

import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.cranelabs.todoapp.R;
import com.cranelabs.todoapp.adapter.TodoAdapter;
import com.cranelabs.todoapp.adapter.TodoAdapter.MyViewHolder;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import com.cranelabs.todoapp.models.TodoModel;
import com.cranelabs.todoapp.utils.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListner {
	
	private EditText mEditText;
	private TextView mTextView;
	private RecyclerView mRecyclerView;
	private FloatingActionButton mFab;
	private DatabaseHelper myDb;
	private List<TodoModel> mList;
	private TodoAdapter adapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		mEditText = findViewById(R.id.editText);
		mFab = findViewById(R.id.fab);
		mList = new ArrayList<>();
		mRecyclerView = findViewById(R.id.recyclerView);
		myDb = new DatabaseHelper(MainActivity.this);
		adapter = new TodoAdapter(myDb, MainActivity.this);
		
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		
		mFab.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
			}
		});
    }
	
	@Override
	public void onDialogClose(DialogInterface dialogInterface){
		mList = myDb.getAllTasks();
		Collections.reverse(mList);
		adapter.setTasks(mList);
		adapter.notifyDataSetChanged();
	}
}