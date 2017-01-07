package de.x4fyr.timeline.adapter

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * @author x4fyr
 * Created on 1/2/17.
 */
open class DBHelper(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_SCHEDULE)
        db.execSQL(SQL_CREATE_EXTERNAL)
        db.execSQL(SQL_CREATE_TODO)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        throw UnsupportedOperationException("not implemented")
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        throw UnsupportedOperationException("not implemented")
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "database.db"
    }

}
