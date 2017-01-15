package de.x4fyr.timeline.adapter

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import de.x4fyr.timeline.domain.elements.ScheduledElement
import de.x4fyr.util.*
import org.assertj.core.api.Assertions.assertThat
import org.joda.time.Duration
import org.joda.time.LocalDateTime
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

    @Rule
    @JvmField
    val ex = ExpectedException.none()!!

    companion object {
        val RANDOM = Random()
        val START = RANDOM.nextLocalDateTime()
        val DURATION = RANDOM.nextDuration()
        val TITLE = RANDOM.nextString(20)
        val NOTES = RANDOM.nextString(100)
        val ID = RANDOM.nextLongPositive()
        val EX_ID = RANDOM.nextLongPositive()
    }

    @Test
    fun saveToScheduleNullDB() {
        //given
        val dbHelper = mock(DBHelper::class.java)
        val element = ScheduledElement(title = "", notes = "", start = LocalDateTime.now(), duration = Duration.ZERO)
        val adapter = ScheduleAdapterImpl(dbHelper)
        given(dbHelper.writableDatabase).willReturn(null)
        //exception
        ex.expect(RuntimeException::class.java)
        ex.expectMessage("Could not get a writable database")
        //when
        adapter.saveToSchedule(element)
        //then
        then(dbHelper).should(only()).writableDatabase
        then(dbHelper).shouldHaveNoMoreInteractions()
    }

    @Test
    fun saveToSchedule() {
        //given
        val dbHelper = mock(DBHelper::class.java)
        val db = mock(SQLiteDatabase::class.java)
        val adapter = ScheduleAdapterImpl(dbHelper)
        val element = ScheduledElement(start = START, duration = DURATION, title = TITLE, notes = NOTES, externalId =
        EX_ID)
        given(dbHelper.writableDatabase).willReturn(db)
        given(db.insert(eq(TABLE_NAME_SCHEDULE), isNull(), any())).willReturn(ID)
        //when
        val result = adapter.saveToSchedule(element)
        //then
        assertThat(result).isEqualTo(element.copy(id = ID))
        val valuesCaptor = ArgumentCaptor.forClass(ContentValues::class.java)
        then(db).should(only()).insert(eq(TABLE_NAME_SCHEDULE), isNull(), valuesCaptor.capture())
        then(db).shouldHaveNoMoreInteractions()
        val values = valuesCaptor.value
        assertThat(values).isNotNull()
        assertThat(values.size()).isEqualTo(5)
        assertThat(values.get(COLUMN_NAME_START)).isEqualTo(START.toDateTime().millis)
        assertThat(values.get(COLUMN_NAME_DURATION)).isEqualTo(DURATION.millis)
        assertThat(values.get(COLUMN_NAME_TITLE)).isEqualTo(TITLE)
        assertThat(values.get(COLUMN_NAME_NOTES)).isEqualTo(NOTES)
        assertThat(values.get(COLUMN_NAME_EXTERNAL_ID)).isEqualTo(EX_ID)
    }

    @Test
    fun saveToScheduleNoDBWrite() {
        //given
        val dbHelper = mock(DBHelper::class.java)
        val db = mock(SQLiteDatabase::class.java)
        val adapter = ScheduleAdapterImpl(dbHelper)
        val element = ScheduledElement(start = START, duration = DURATION, title = TITLE, notes = NOTES, externalId =
        EX_ID)
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
        assertThat(values).isNotNull()
        assertThat(values.size()).isEqualTo(5)
        assertThat(values.get(COLUMN_NAME_START)).isEqualTo(START.toDateTime().millis)
        assertThat(values.get(COLUMN_NAME_DURATION)).isEqualTo(DURATION.millis)
        assertThat(values.get(COLUMN_NAME_TITLE)).isEqualTo(TITLE)
        assertThat(values.get(COLUMN_NAME_NOTES)).isEqualTo(NOTES)
        assertThat(values.get(COLUMN_NAME_EXTERNAL_ID)).isEqualTo(EX_ID)
    }

    @Test
    fun deleteFromScheduleNullDB() {
        //given
        //given
        val dbHelper = mock(DBHelper::class.java)
        val adapter = ScheduleAdapterImpl(dbHelper)
        val element = ScheduledElement(ID, TITLE, NOTES, START, DURATION)
        given(dbHelper.writableDatabase).willReturn(null)
        //exceptions
        ex.expect(RuntimeException::class.java)
        ex.expectMessage("Could not get a writable database")
        //when
        adapter.deleteFromSchedule(element)
        //then
        then(dbHelper).should(only()).writableDatabase
        then(dbHelper).shouldHaveNoMoreInteractions()
    }

    @Test
    fun deleteFromScheduleNoID() {
        //given
        val dbHelper = mock(DBHelper::class.java)
        val db = mock(SQLiteDatabase::class.java)
        val adapter = ScheduleAdapterImpl(dbHelper)
        val element = ScheduledElement(null, TITLE, NOTES, START, DURATION)
        given(dbHelper.writableDatabase).willReturn(db)
        given(db.delete(eq(TABLE_NAME_SCHEDULE), eq(ScheduleAdapterImpl.QUERIES.DELETE), eq(arrayOf(ID.toString()))))
                .willReturn(RANDOM.nextIntPositive())
        //exceptions
        ex.expect(UnsupportedOperationException::class.java)
        ex.expectMessage("Cannot delete element without id")
        //when
        adapter.deleteFromSchedule(element)
        //then
        then(dbHelper).should(only()).writableDatabase
        then(dbHelper).shouldHaveNoMoreInteractions()
        then(db).should(only())
                .delete(eq(TABLE_NAME_SCHEDULE), eq(ScheduleAdapterImpl.QUERIES.DELETE), eq(arrayOf(ID.toString())))
        then(db).shouldHaveNoMoreInteractions()
    }

    @Test
    fun deleteFromScheduleNoDBWrite() {
        //given
        val dbHelper = mock(DBHelper::class.java)
        val db = mock(SQLiteDatabase::class.java)
        val adapter = ScheduleAdapterImpl(dbHelper)
        val element = ScheduledElement(ID, TITLE, NOTES, START, DURATION)
        given(dbHelper.writableDatabase).willReturn(db)
        given(db.delete(eq(TABLE_NAME_SCHEDULE), eq(ScheduleAdapterImpl.QUERIES.DELETE), eq(arrayOf(ID.toString()))))
                .willReturn(-1)
        //exceptions
        ex.expect(RuntimeException::class.java)
        ex.expectMessage("Could not write to database")
        //when
        adapter.deleteFromSchedule(element)
        //then
        then(dbHelper).should(only()).writableDatabase
        then(dbHelper).shouldHaveNoMoreInteractions()
        then(db).should(only())
                .delete(eq(TABLE_NAME_SCHEDULE), eq(ScheduleAdapterImpl.QUERIES.DELETE), eq(arrayOf(ID.toString())))
        then(db).shouldHaveNoMoreInteractions()
    }

    @Test
    fun deleteFromSchedule() {
        //given
        val dbHelper = mock(DBHelper::class.java)
        val db = mock(SQLiteDatabase::class.java)
        val adapter = ScheduleAdapterImpl(dbHelper)
        val element = ScheduledElement(ID, TITLE, NOTES, START, DURATION)
        given(dbHelper.writableDatabase).willReturn(db)
        given(db.delete(eq(TABLE_NAME_SCHEDULE), eq(ScheduleAdapterImpl.QUERIES.DELETE), eq(arrayOf(ID.toString()))))
                .willReturn(RANDOM.nextIntPositive())
        //when
        adapter.deleteFromSchedule(element)
        //then
        then(dbHelper).should(only()).writableDatabase
        then(dbHelper).shouldHaveNoMoreInteractions()
        then(db).should(only())
                .delete(eq(TABLE_NAME_SCHEDULE), eq(ScheduleAdapterImpl.QUERIES.DELETE), eq(arrayOf(ID.toString())))
        then(db).shouldHaveNoMoreInteractions()
    }


    @Test
    fun getByTimeSpanNullDB() {
        //given
        val dbHelper = mock(DBHelper::class.java)
        val adapter = ScheduleAdapterImpl(dbHelper)
        val (start, duration, end) = RANDOM.nextStartDurationEnd()
        given(dbHelper.writableDatabase).willReturn(null)
        //exceptions
        ex.expect(RuntimeException::class.java)
        ex.expectMessage("Could not get a readable database")
        //when
        adapter.getByTimeSpan(start, end)
        //then
        then(dbHelper).should(only()).readableDatabase
        then(dbHelper).shouldHaveNoMoreInteractions()
    }

    @Test
    fun getByTimeSpan() {
        //given
        val dbHelper = mock(DBHelper::class.java)
        val db = mock(SQLiteDatabase::class.java)
        val cursor = mock(Cursor::class.java)
        val templates = ScheduleAdapterImpl.QUERIES.TIMESPAN
        val (start, duration, end) = RANDOM.nextStartDurationEnd()
        val adapter = ScheduleAdapterImpl(dbHelper)
        given(dbHelper.readableDatabase).willReturn(db)
        given(db.query(TABLE_NAME_SCHEDULE, templates.COLUMNS, templates.SELECTION,
                arrayOf(start.toDateTime().millis.toString(), end.toDateTime().millis.toString()),
                templates.GROUPBY, templates.HAVING, templates.ORDERBY))
                .willReturn(cursor)
        val data = getRandomData()
        val iterator = data.listIterator()
        var current: ScheduledElement? = null
        given(cursor.moveToNext()).will {
            val bool: Boolean
            if (iterator.hasNext()) {
                current = iterator.next()
                bool = true
            } else {
                bool = false
            }
            given(cursor.getLong(0)).willReturn(current!!.id)
            given(cursor.getString(1)).willReturn(current!!.title)
            given(cursor.getString(2)).willReturn(current!!.notes)
            given(cursor.getLong(3)).willReturn(current!!.start.toDateTime().millis)
            given(cursor.getLong(4)).willReturn(current!!.duration.millis)
            given(cursor.getLong(5)).willReturn(current!!.externalId)
            bool
        }
        given(cursor.getColumnIndex(COLUMN_NAME_ID)).willReturn(0)
        given(cursor.getColumnIndex(COLUMN_NAME_TITLE)).willReturn(1)
        given(cursor.getColumnIndex(COLUMN_NAME_NOTES)).willReturn(2)
        given(cursor.getColumnIndex(COLUMN_NAME_START)).willReturn(3)
        given(cursor.getColumnIndex(COLUMN_NAME_DURATION)).willReturn(4)
        given(cursor.getColumnIndex(COLUMN_NAME_EXTERNAL_ID)).willReturn(5)
        //when
        val result = adapter.getByTimeSpan(start, end)
        //then
        then(dbHelper).should(only()).readableDatabase
        then(dbHelper).shouldHaveNoMoreInteractions()
        then(db).should(only()).query(TABLE_NAME_SCHEDULE, templates.COLUMNS, templates.SELECTION,
                arrayOf(start.toDateTime().millis.toString(), end.toDateTime().millis.toString()),
                templates.GROUPBY, templates.HAVING, templates.ORDERBY)
        then(db).shouldHaveNoMoreInteractions()
        then(cursor).should().close()
        assertThat(result).containsOnlyElementsOf(data)
    }

    @Test
    fun caching() {
        //given
        val dbHelper = mock(DBHelper::class.java)
        val db = mock(SQLiteDatabase::class.java)
        val adapter = ScheduleAdapterImpl(dbHelper)
        val cursor = mock(Cursor::class.java)
        val (start, duration, end) = RANDOM.nextStartDurationEnd()
        given(dbHelper.writableDatabase).willReturn(db)
        given(dbHelper.readableDatabase).willReturn(db)
        val sampleElement = ScheduledElement(title = TITLE, notes = NOTES, start = START, duration = DURATION)
        val data = getRandomData()
        var iterator = data.listIterator()
        var current: ScheduledElement? = null
        given(cursor.moveToNext()).will {
            val bool: Boolean
            if (iterator.hasNext()) {
                current = iterator.next()
                bool = true
            } else {
                bool = false
            }
            given(cursor.getLong(0)).willReturn(current!!.id)
            given(cursor.getString(1)).willReturn(current!!.title)
            given(cursor.getString(2)).willReturn(current!!.notes)
            given(cursor.getLong(3)).willReturn(current!!.start.toDateTime().millis)
            given(cursor.getLong(4)).willReturn(current!!.duration.millis)
            given(cursor.getLong(5)).willReturn(current!!.externalId)
            bool
        }
        given(cursor.getColumnIndex(COLUMN_NAME_ID)).willReturn(0)
        given(cursor.getColumnIndex(COLUMN_NAME_TITLE)).willReturn(1)
        given(cursor.getColumnIndex(COLUMN_NAME_NOTES)).willReturn(2)
        given(cursor.getColumnIndex(COLUMN_NAME_START)).willReturn(3)
        given(cursor.getColumnIndex(COLUMN_NAME_DURATION)).willReturn(4)
        given(cursor.getColumnIndex(COLUMN_NAME_EXTERNAL_ID)).willReturn(5)
        given(db.query(any(), any(), any(), any(), any(), any(), any())).will {
            iterator = data.listIterator()
            cursor
        }
        //when
        val result1 = adapter.getByTimeSpan(start, end)
        val result2 = adapter.getByTimeSpan(start, end)
        adapter.saveToSchedule(sampleElement)
        val result3 = adapter.getByTimeSpan(start, end)
        adapter.deleteFromSchedule(sampleElement.copy(id = ID))
        val result4 = adapter.getByTimeSpan(start, end)
        //then
        then(db).should(times(3)).query(any(), any(), any(), any(), any(), any(), any())
        then(dbHelper).should(times(3)).readableDatabase
        assertThat(result1).isEqualTo(result2).isEqualTo(result3).isEqualTo(result4)
    }

    fun getRandomData(): List<ScheduledElement> {
        val result: MutableList<ScheduledElement> = mutableListOf()
        val ids: MutableList<Long> = mutableListOf()
        RANDOM.longs().boxed().limit(100).forEach { ids.add(it) }
        for (id in ids) {
            val (start, duration, end) = RANDOM.nextStartDurationEnd()
            val title = RANDOM.nextString(20)
            val notes = RANDOM.nextString(100)
            val element = ScheduledElement(id, title, notes, start, duration, RANDOM
            .nextLong())
            result.add(element)
        }
        return result
    }

}