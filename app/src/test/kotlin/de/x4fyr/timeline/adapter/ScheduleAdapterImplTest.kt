package de.x4fyr.timeline.adapter

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import de.x4fyr.timeline.domain.elements.ScheduledElement
import de.x4fyr.util.duration
import de.x4fyr.util.localDateTime
import de.x4fyr.util.string
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormatter
import org.junit.Test

import org.junit.Assert.*
import org.junit.Ignore
import org.junit.Rule
import org.junit.rules.ExpectedException
import org.mockito.BDDMockito.*
import org.powermock.modules.junit4.PowerMockRunner
import org.junit.runner.RunWith
import org.mockito.Matchers
import org.mockito.Mockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.api.mockito.PowerMockito
import java.util.Collections
import java.util.Random

/**
 * @author x4fyr
 * *         Created on 1/3/17.
 */
@RunWith(PowerMockRunner::class)
@PrepareForTest(Log::class)
class ScheduleAdapterImplTest {

    val random = Random()

    @Rule
    @JvmField
    val ex = ExpectedException.none()

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
        PowerMockito.mockStatic(Log::class.java)
        given(dbHelper.writableDatabase).willReturn(null)
        //exception
        ex.expect(RuntimeException::class.java)
        ex.expectMessage("Could not get a writable database")
        //when
        adapter.saveToSchedule(element)
    }

    @Test
    fun saveToSchedule() {
        //given
        val dbHelper = mock(DBHelper::class.java)
        val db = mock(SQLiteDatabase::class.java)
        val adapter = ScheduleAdapterImpl(dbHelper)
        val element = ScheduledElement(start, duration, title, notes)
        val values = ContentValues()
        values.put(COLUMN_NAME_START, start.toString())
        values.put(COLUMN_NAME_DURATION, duration.toString())
        values.put(COLUMN_NAME_TITLE, title)
        values.put(COLUMN_NAME_NOTES, notes)
        given(dbHelper.writableDatabase).willReturn(db)
        //when
        val result = adapter.saveToSchedule(element)
        //then
        assertEquals(result, element)
        then(db).should(only()).insert(eq(TABLE_NAME_SCHEDULE), isNull(String::class.java), eq(values))
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