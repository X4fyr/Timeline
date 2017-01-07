package de.x4fyr.timeline.adapter

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import de.x4fyr.timeline.domain.elements.ScheduledElement
import de.x4fyr.util.duration
import de.x4fyr.util.localDateTime
import de.x4fyr.util.string
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.BDDMockito.*
import org.robolectric.RobolectricTestRunner
import java.util.Random

/**
 * @author x4fyr
 *         Created on 1/3/17.
 */
@RunWith(RobolectricTestRunner::class)
class ScheduleAdapterImplTest {

    val random = Random()

    @Rule
    @JvmField
    val ex = ExpectedException.none()!!

    val start = random.localDateTime()
    val duration = random.duration()
    val title = random.string()
    val notes = random.string()


    @Test
    fun saveToScheduleNullDB() {
        //given
        val dbHelper = mock(DBHelper::class.java)
        val element = mock(ScheduledElement::class.java)
        val adapter = ScheduleAdapterImpl(dbHelper)
        given(dbHelper.writableDatabase).willReturn(null)
        //exception
        ex.expect(RuntimeException::class.java)
        ex.expectMessage("Could not get a writable database")
        //when
        adapter.saveToSchedule(element)
        //then
        then(element).shouldHaveZeroInteractions()
    }

    @Test
    fun saveToSchedule() {
        //given
        val dbHelper = mock(DBHelper::class.java)
        val db = mock(SQLiteDatabase::class.java)
        val adapter = ScheduleAdapterImpl(dbHelper)
        val element = ScheduledElement(start, duration, title, notes)
        given(dbHelper.writableDatabase).willReturn(db)
        given(db.insert(eq(TABLE_NAME_SCHEDULE), isNull(), any())).willReturn(random.nextInt(100).toLong())
        //when
        val result = adapter.saveToSchedule(element)
        //then
        assertEquals(result, element)
        val valuesCaptor = ArgumentCaptor.forClass(ContentValues::class.java)
        then(db).should(only()).insert(eq(TABLE_NAME_SCHEDULE), isNull(), valuesCaptor.capture())
        then(db).shouldHaveNoMoreInteractions()
        val values = valuesCaptor.value
        assertNotNull(values)
        assertEquals(4, values.size())
        assertEquals(start.toString(), values.get(COLUMN_NAME_START))
        assertEquals(duration.toString(), values.get(COLUMN_NAME_DURATION))
        assertEquals(title, values.get(COLUMN_NAME_TITLE))
        assertEquals(notes, values.get(COLUMN_NAME_NOTES))
    }

    @Test
    fun saveToScheduleNoDBWrite() {
        //given
        val dbHelper = mock(DBHelper::class.java)
        val db = mock(SQLiteDatabase::class.java)
        val adapter = ScheduleAdapterImpl(dbHelper)
        val element = ScheduledElement(start, duration, title, notes)
        given(dbHelper.writableDatabase).willReturn(db)
        given(db.insert(any(), any(), any())).willReturn(-1)
        //exception
        ex.expect(RuntimeException::class.java)
        ex.expectMessage("Could not write to database")
        //when
        adapter.saveToSchedule(element)
        //then
        val valuesCaptor = ArgumentCaptor.forClass(ContentValues::class.java)
        then(db).should(only()).insert(eq(TABLE_NAME_SCHEDULE), isNull(), valuesCaptor.capture())
        then(db).shouldHaveNoMoreInteractions()
        val values = valuesCaptor.value
        assertNotNull(values)
        assertEquals(4, values.size())
        assertEquals(start.toString(), values.get(COLUMN_NAME_START))
        assertEquals(duration.toString(), values.get(COLUMN_NAME_DURATION))
        assertEquals(title, values.get(COLUMN_NAME_TITLE))
        assertEquals(notes, values.get(COLUMN_NAME_NOTES))
    }

    @Ignore
    @Test
    fun deleteFromSchedule() {
        //given
        //when
        //then
    }


    @Ignore
    @Test
    fun getByTimeSpan() {
        //given
        //when
        //then
    }

}