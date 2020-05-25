package com.example.todo;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todo.data.taskContract;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;

public class MyViewModel extends ViewModel {
    Context context;
    private MutableLiveData<Cursor> mCursor;

    public LiveData<Cursor> getLiveDataCursor(Context context){
        this.context=context;
        if(mCursor==null){
            Log.v("call","1");
            mCursor=new MutableLiveData<Cursor>();
        }
        return mCursor;
    }

    public void loadCursor() {
        fetchDataAsyncTask a=new fetchDataAsyncTask();
        a.execute();
    }

    private class fetchDataAsyncTask extends AsyncTask<Void,Void,Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return context.getContentResolver().query(taskContract.TaskEntry.CONTENT_URI,null,null,null, null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            mCursor.setValue(cursor);
        }
    }

}
