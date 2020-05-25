package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.app.ActionBar;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todo.data.taskContract;

import org.w3c.dom.Text;

import java.util.Date;

public class AddTask extends AppCompatActivity {

    TextView summary;
    Button addTask;
    int mPriority=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        summary=findViewById(R.id.editTextTaskDescription);
        addTask=findViewById(R.id.addButton);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values=new ContentValues();
                values.put(taskContract.TaskEntry.COLUMN_TASK_SUMMARY,summary.getText().toString());
                values.put(taskContract.TaskEntry.COLUMN_TASK_DATE,new Date().toString());
                values.put(taskContract.TaskEntry.COLUMN_TASK_PRIORITY,mPriority);
                getContentResolver().insert(taskContract.TaskEntry.CONTENT_URI,values);
                finish();
            }
        });
    }

    public void onPrioritySelected(View view) {
        switch (view.getId()){
            case R.id.radButton1:
                mPriority=1;
                break;
            case R.id.radButton2:
                mPriority=2;
                break;
            case R.id.radButton3:
                mPriority=3;
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                break;
            case R.id.action_delete:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}