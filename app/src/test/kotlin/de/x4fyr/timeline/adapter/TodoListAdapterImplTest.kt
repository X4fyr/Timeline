package de.x4fyr.timeline.adapter

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import de.x4fyr.timeline.domain.elements.TodoElement
import de.x4fyr.util.nextDuration
import de.x4fyr.util.nextLocalDate
import de.x4fyr.util.nextLongPositive
import de.x4fyr.util.nextString
import org.junit.Test
import org.mockito.BDDMockito.*
import org.assertj.core.api.Assertions.*
import org.junit.Rule
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.robolectric.RobolectricTestRunner
import java.util.Random

/**
 * @author x4fyr
 * *         Created on 1/15/17.
 */
@RunWith(RobolectricTestRunner::class)
class TodoListAdapterImplTest {

    @Rule
    @JvmField
    val ex = ExpectedException.none()!!

    companion object {
        val RANDOM = Random()
        val TITLE = RANDOM.nextString(20)
        val NOTES = RANDOM.nextString(100)
        val DATE = RANDOM.nextLocalDate()
        val DURATION = RANDOM.nextDuration()
        val ID = RANDOM.nextLongPositive()
    }

    @Test
    fun saveToToDoListNullDB() {
        //given
        val dbHelper = mock(DBHelper::class.java)
        val adapter = TodoListAdapterImpl(dbHelper)
        val element = TodoElement(id = ID, title = TITLE, notes = NOTES, plannedDate = DATE, plannedDuration = DURATION)
        given(dbHelper.writableDatabase).willReturn(null)
        //exceptions
        ex.expect(RuntimeException::class.java)
        ex.expectMessage("Could not get a writable database")
        //when
        adapter.saveToToDoList(element)
        //then
        then(dbHelper).should(only()).writableDatabase
        then(dbHelper).shouldHaveNoMoreInteractions()
    }

    @Test
    fun saveToToDoListNoDBWrite() {
        //given
        val dbHelper = mock(DBHelper::class.java)
        val db = mock(SQLiteDatabase::class.java)
        val adapter = TodoListAdapterImpl(dbHelper)
        val element = TodoElement(id = ID, title = TITLE, notes = NOTES, plannedDate = DATE, plannedDuration = DURATION)
        given(dbHelper.writableDatabase).willReturn(db)
        given(db.insert(eq(TABLE_NAME_TODO), isNull(), any())).willReturn(-1)
        //exceptions
        ex.expect(RuntimeException::class.java)
        ex.expectMessage("Could not write to database")
        //when
        adapter.saveToToDoList(element)
        //then
        then(dbHelper).should(only()).writableDatabase
        then(dbHelper).shouldHaveNoMoreInteractions()
        then(db).should(only()).insert(eq(TABLE_NAME_TODO), isNull(), any())
        then(db).shouldHaveNoMoreInteractions()
    }

    @Test
    fun saveToToDoList() {
        //given
        val dbHelper = mock(DBHelper::class.java)
        val db = mock(SQLiteDatabase::class.java)
        val adapter = TodoListAdapterImpl(dbHelper)
        val element = TodoElement(title = TITLE, notes = NOTES, plannedDate = DATE, plannedDuration = DURATION)
        given(dbHelper.writableDatabase).willReturn(db)
        given(db.insert(eq(TABLE_NAME_TODO), isNull(), any())).willReturn(ID)
        //when
        val result = adapter.saveToToDoList(element)
        //then
        assertThat(result).isEqualTo(element.copy(id = ID))
        val valuesCaptor = ArgumentCaptor.forClass(ContentValues::class.java)
        then(dbHelper).should(only()).writableDatabase
        then(dbHelper).shouldHaveNoMoreInteractions()
        then(db).should(only()).insert(eq(TABLE_NAME_TODO), isNull(), valuesCaptor.capture())
        then(db).shouldHaveNoMoreInteractions()
        val values = valuesCaptor.value
        assertThat(values.size()).isEqualTo(5)
        assertThat(values.get(COLUMN_NAME_ID)).isNull()
        assertThat(values.get(COLUMN_NAME_TITLE)).isEqualTo(TITLE)
        assertThat(values.get(COLUMN_NAME_NOTES)).isEqualTo(NOTES)
        assertThat(values.get(COLUMN_NAME_PLANNED_DATE)).isEqualTo(DATE.toDateTimeAtStartOfDay().millis)
        assertThat(values.get(COLUMN_NAME_PLANNED_DURATION)).isEqualTo(DURATION.millis)
    }

    @Test
    fun deleteFromTodoListNullDB() {
        //given
        val dbHelper = mock(DBHelper::class.java)
        val adapter = TodoListAdapterImpl(dbHelper)
        val element = TodoElement(id = ID, title = TITLE, notes = NOTES, plannedDate = DATE, plannedDuration = DURATION)
        given(dbHelper.writableDatabase).willReturn(null)
        //exceptions
        ex.expect(RuntimeException::class.java)
        ex.expectMessage("Could not get a writable database")
        //when
        adapter.deleteFromTodoList(element)
        //then
        then(dbHelper).should(only()).writableDatabase
        then(dbHelper).shouldHaveNoMoreInteractions()
    }

    @Test
    fun deleteFromTodoListNoDBWrite() {
        //given
        val dbHelper = mock(DBHelper::class.java)
        val db = mock(SQLiteDatabase::class.java)
        val adapter = TodoListAdapterImpl(dbHelper)
        val element = TodoElement(id = ID, title = TITLE, notes = NOTES, plannedDate = DATE, plannedDuration = DURATION)
        given(dbHelper.writableDatabase).willReturn(db)
        given(db.delete(eq(TABLE_NAME_TODO), eq(TodoListAdapterImpl.QUERIES.DELETE), eq(arrayOf(ID.toString()))))
                .willReturn(-1)
        //exceptions
        ex.expect(RuntimeException::class.java)
        ex.expectMessage("Could not write to database")
        //when
        adapter.deleteFromTodoList(element)
        //then
        then(dbHelper).should(only()).writableDatabase
        then(dbHelper).shouldHaveNoMoreInteractions()
        then(db).should(only()).delete(eq(TABLE_NAME_TODO), eq(TodoListAdapterImpl.QUERIES.DELETE), eq(arrayOf(ID.toString())))
        then(db).shouldHaveNoMoreInteractions()
    }

    @Test
    fun deleteFromTodoList() {
        //given
        val dbHelper = mock(DBHelper::class.java)
        val db = mock(SQLiteDatabase::class.java)
        val adapter = TodoListAdapterImpl(dbHelper)
        val element = TodoElement(id = ID, title = TITLE, notes = NOTES, plannedDate = DATE, plannedDuration = DURATION)
        given(dbHelper.writableDatabase).willReturn(db)
        given(db.delete(eq(TABLE_NAME_TODO), eq(TodoListAdapterImpl.QUERIES.DELETE), eq(arrayOf(ID.toString()))))
                .willReturn(1)
        //when
        adapter.deleteFromTodoList(element)
        //then
        then(dbHelper).should(only()).writableDatabase
        then(dbHelper).shouldHaveNoMoreInteractions()
        then(db).should(only()).delete(eq(TABLE_NAME_TODO), eq(TodoListAdapterImpl.QUERIES.DELETE), eq(arrayOf(ID.toString())))
        then(db).shouldHaveNoMoreInteractions()
    }

}