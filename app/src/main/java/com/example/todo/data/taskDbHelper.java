package com.example.todo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class taskDbHelper extends SQLiteOpenHelper {

    public taskDbHelper(@Nullable Context context) {
        super(context, "TODO.db" , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_PETS_TABLE="CREATE TABLE "+ taskContract.TaskEntry.TABLE_NAME+" ("+
                taskContract.TaskEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                taskContract.TaskEntry.COLUMN_TASK_SUMMARY+" TEXT NOT NULL, "+
                taskContract.TaskEntry.COLUMN_TASK_DATE +" TEXT NOT NULL, "+
                taskContract.TaskEntry.COLUMN_TASK_PRIORITY+" INTEGER NOT NULL DEFAULT 1 "+
                ");";
        db.execSQL(SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
