package com.example.todo;

import android.app.Application;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Date;
import com.example.todo.data.taskContract;

public class MainActivity extends AppCompatActivity implements recycleViewAdapter.ItemOnClickHandler {

    FloatingActionButton fab;
    RecyclerView rv;
    Toolbar toolbar;
    LinearLayoutManager layoutManager;
    recycleViewAdapter rvAdapter;
    MyViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add Task..", BaseTransientBottomBar.LENGTH_LONG).setAction("Action", null).show();
                Intent intent=new Intent(getApplicationContext(),AddTask.class);
                startActivity(intent);
            }
        });
        rv=findViewById(R.id.rv);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        rvAdapter=new recycleViewAdapter(this);
        rv.setAdapter(rvAdapter);

        model= new ViewModelProvider(this).get(MyViewModel.class);
        final Observer<Cursor> cursorObserver=new Observer<Cursor>() {
            @Override
            public void onChanged(Cursor cursor) {
                rvAdapter.swapCursor(cursor);
            }
        };
        model.getLiveDataCursor(this).observe(this,cursorObserver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("call","2");
        model.loadCursor();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCLick(int position) {
        Toast.makeText(this,""+position,Toast.LENGTH_SHORT).show();
    }
}