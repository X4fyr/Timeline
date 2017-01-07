package de.x4fyr.timeline.adapter

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

import org.junit.Assert.*
import org.mockito.BDDMockito.*
import org.mockito.InOrder
import org.mockito.Mockito
import kotlin.reflect.KClass

/**
 * @author x4fyr
 * *         Created on 1/3/17.
 */
class DBHelperTest {

    @Rule
    @JvmField
    var ex = ExpectedException.none()

    @Test
    fun onCreate() {
        //given
        val context = mock(Context::class.java)
        val db = mock(SQLiteDatabase::class.java)
        val dbHelper = DBHelper(context)
        //when
        dbHelper.onCreate(db)
        //then
        then(db).should().execSQL(SQL_CREATE_SCHEDULE)
        then(db).should().execSQL(SQL_CREATE_EXTERNAL)
        then(db).should().execSQL(SQL_CREATE_TODO)
    }

    @Test
    fun onUpgrade() {
        //given
        val context = mock(Context::class.java)
        val dbHelper = DBHelper(context)
        //exceptions
        ex.expect(UnsupportedOperationException::class.java)
        ex.expectMessage("not implemented")
        //when
        dbHelper.onUpgrade(null, 1, 2)
    }

    @Test
    fun onDowngrade() {
        //given
        val context = mock(Context::class.java)
        val dbHelper = DBHelper(context)
        //exceptions
        ex.expect(UnsupportedOperationException::class.java)
        ex.expectMessage("not implemented")
        //when
        dbHelper.onDowngrade(null, 1, 2)
    }

}