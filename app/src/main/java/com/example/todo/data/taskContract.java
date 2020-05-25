package com.example.todo.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class taskContract {

    private taskContract(){}

    public static final String CONTENT_AUTHORITY="com.example.todo";
    public static final Uri BASE_CONTENT_URI= Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String PATH_TASK="task";

    public static final class TaskEntry implements BaseColumns{
        public static final Uri CONTENT_URI=Uri.withAppendedPath(BASE_CONTENT_URI,PATH_TASK);
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TASK;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TASK;

        public final static String TABLE_NAME="task";
        public final static String _ID=BaseColumns._ID;
        public final static String COLUMN_TASK_SUMMARY="summary";
        public final static String COLUMN_TASK_PRIORITY="priority";
        public final static String COLUMN_TASK_DATE="date";

        public final static int PRIORITY_LOW=3;
        public final static int PRIORITY_MEDIUM=2;
        public final static int PRIORITY_HIGH=1;

        public static boolean isValidPriority(int priority) {
            if (priority == PRIORITY_HIGH || priority == PRIORITY_MEDIUM || priority == PRIORITY_LOW) {
                return true;
            }
            return false;
        }
    }
}
