package com.example.todo.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class taskContentProvider extends ContentProvider {
    taskDbHelper mDbHelper;

    private static final int TASKS = 100;
    private static final int TASK_ID = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(taskContract.CONTENT_AUTHORITY, taskContract.PATH_TASK, TASKS);
        sUriMatcher.addURI(taskContract.CONTENT_AUTHORITY, taskContract.PATH_TASK+"/#", TASK_ID);
    }

    @Override
    public boolean onCreate() {
        mDbHelper=new taskDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int match=sUriMatcher.match(uri);
        switch (match){
            case TASKS:
                return queryTask(uri, taskContract.TaskEntry.TABLE_NAME, projection, selection, selectionArgs, sortOrder);
            case TASK_ID:
                selection = taskContract.TaskEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return queryTask(uri, taskContract.TaskEntry.TABLE_NAME, projection, selection, selectionArgs, sortOrder);
            default:
                throw new IllegalArgumentException("Cannot query unknown URI "+uri);
        }
    }
    private Cursor queryTask(Uri uri, String tableName, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db=mDbHelper.getReadableDatabase();
        Cursor cursor = db.query( tableName, projection, selection, selectionArgs, null, null, sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match=sUriMatcher.match(uri);
        switch (match){
            case TASKS:
                return insertTask( uri, taskContract.TaskEntry.TABLE_NAME, values);
            default:
                throw new IllegalArgumentException("Insertion is not Supported for "+uri);
        }
    }
    private Uri insertTask(Uri uri, String tableName, ContentValues values) {
        String summary = values.getAsString(taskContract.TaskEntry.COLUMN_TASK_SUMMARY);
        if (summary == null) {
            throw new IllegalArgumentException("Task requires a summary");
        }
        String date = values.getAsString(taskContract.TaskEntry.COLUMN_TASK_DATE);
        if (date == null) {
            throw new IllegalArgumentException("Task requires valid date");
        }
        Integer priority = values.getAsInteger(taskContract.TaskEntry.COLUMN_TASK_PRIORITY);
        if (priority == null || !taskContract.TaskEntry.isValidPriority(priority)) {
            throw new IllegalArgumentException("Task requires valid priority");
        }
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        long id = db.insert(tableName, null, values);
        if (id == -1) {
            Log.e("cp.insertTask ", "Failed to insert row for " + uri);
            return null;
        }
        return ContentUris.withAppendedId(uri,id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
