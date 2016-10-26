package de.x4fyr.timeline.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import lombok.Cleanup;

/**
 * SQLiteDatabase adapter for testability and preventing wrong use.
 */
interface SQLiteDatabaseAdapter {

    int delete(String table, String whereClause, String[] whereArgs);

    Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy,
                 String having, String orderBy);

    long insert(String table, String nullColumnHack, ContentValues values);

    /**
     * Simple implementation of the adapter.
     */
    class SQLiteDatabaseAdapterImpl implements SQLiteDatabaseAdapter {
        private final SQLiteOpenHelper helper;

        SQLiteDatabaseAdapterImpl(SQLiteOpenHelper helper) {
            this.helper = helper;
        }

        public int delete(String table, String whereClause, String[] whereArgs) {
            @Cleanup SQLiteDatabase db = helper.getWritableDatabase();
            return db.delete(table, whereClause, whereArgs);
        }

        public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy,
                            String having, String orderBy) {
            @Cleanup SQLiteDatabase db = helper.getWritableDatabase();
            return db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
        }

        public long insert(String table, String nullColumnHack, ContentValues values) {
            @Cleanup SQLiteDatabase db = helper.getWritableDatabase();
            return db.insert(table, nullColumnHack, values);
        }
    }
}
