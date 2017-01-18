package de.x4fyr.timeline.adapter

import android.content.ContentValues
import android.util.Log
import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import de.x4fyr.timeline.domain.elements.ScheduledElement
import org.joda.time.DateTime
import org.joda.time.Duration
import org.joda.time.LocalDateTime
import java.util.concurrent.TimeUnit

/**
 * @author x4fyr
 * Created on 1/2/17.
 */
class ScheduleAdapterImpl(val dbHelper: DBHelper) : ScheduleAdapter {

    override fun getNext(element: ScheduledElement, count: Long): Set<ScheduledElement> {
        throw UnsupportedOperationException("not implemented") //TODO
    }

    override fun getPrev(element: ScheduledElement, count: Long): Set<ScheduledElement> {
        throw UnsupportedOperationException("not implemented") //TODO
    }

    object QUERIES {
        val DELETE = "id == "

        object TIMESPAN {
            val COLUMNS = arrayOf(COLUMN_NAME_ID, COLUMN_NAME_TITLE, COLUMN_NAME_NOTES, COLUMN_NAME_START,
                    COLUMN_NAME_DURATION, COLUMN_NAME_EXTERNAL_ID)
            val SELECTION = "$COLUMN_NAME_START >= AND $COLUMN_NAME_START >= "
            val GROUPBY = null
            val HAVING = null
            val ORDERBY = COLUMN_NAME_START
        }
    }

    val cache: LoadingCache<Pair<LocalDateTime, LocalDateTime>, Set<ScheduledElement>> = CacheBuilder.newBuilder()
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .build(CacheLoader.from { key -> queryTimeSpan(key!!) })

    /** Save the given ScheduleElement to the schedule persistence */
    override fun saveToSchedule(element: ScheduledElement): ScheduledElement {
        cache.invalidateAll()
        val db = dbHelper.writableDatabase
        if (db == null) {
            Log.e(this.toString(), "Could not get a writable database")
            throw RuntimeException("Could not get a writable database")
        }
        val values = ContentValues()
        values.put(COLUMN_NAME_START, element.start.toDateTime().millis)
        values.put(COLUMN_NAME_DURATION, element.duration.millis)
        values.put(COLUMN_NAME_TITLE, element.title)
        values.put(COLUMN_NAME_NOTES, element.notes)
        values.put(COLUMN_NAME_EXTERNAL_ID, element.externalId)
        val id = db.insert(TABLE_NAME_SCHEDULE, null, values)
        if (id < 0) {
            Log.e(this.toString(), "Could not write to database")
            throw RuntimeException("Could not write to database")
        } else {
            return element.copy(id = id)
        }
    }

    /** Delete given ScheduleElement from schedule persistence */
    override fun deleteFromSchedule(element: ScheduledElement) {
        cache.invalidateAll()
        val db = dbHelper.writableDatabase
        if (db == null) {
            Log.e(this.toString(), "Could not get a writable database")
            throw RuntimeException("Could not get a writable database")
        }
        val affectedRows = db.delete(
                TABLE_NAME_SCHEDULE,
                QUERIES.DELETE,
                arrayOf(element.id?.toString()
                        ?: throw UnsupportedOperationException("Cannot delete element without id")
                ))
        if (affectedRows < 0) {
            Log.e(this.toString(), "Could not write to database")
            throw RuntimeException("Could not write to database")
        }
    }

    /** get ScheduleElements of given time span */
    override fun getByTimeSpan(start: LocalDateTime, end: LocalDateTime): Set<ScheduledElement> = cache[Pair(start,
            end)]

    private fun queryTimeSpan(key: Pair<LocalDateTime, LocalDateTime>): Set<ScheduledElement> {
        val start = key.first
        val end = key.second
        val db = dbHelper.readableDatabase
        if (db == null) {
            Log.e(this.toString(), "Could not get a readable database")
            throw RuntimeException("Could not get a readable database")
        }
        val cursor = db.query(TABLE_NAME_SCHEDULE, QUERIES.TIMESPAN.COLUMNS, QUERIES.TIMESPAN.SELECTION,
                arrayOf(start.toDateTime().millis.toString(), end.toDateTime().millis.toString()),
                QUERIES.TIMESPAN.GROUPBY, QUERIES.TIMESPAN.HAVING, QUERIES.TIMESPAN.ORDERBY)
        val result = mutableSetOf<ScheduledElement>()
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndex(COLUMN_NAME_ID))
            val title = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TITLE))
            val notes = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NOTES))
            val startMillis = cursor.getLong(cursor.getColumnIndex(COLUMN_NAME_START))
            val durationMillis = cursor.getLong(cursor.getColumnIndex(COLUMN_NAME_DURATION))
            val ex_id = cursor.getLong(cursor.getColumnIndex(COLUMN_NAME_EXTERNAL_ID))
            result += ScheduledElement(id = id, title = title, notes = notes,
                    start = DateTime(startMillis).toLocalDateTime(), duration = Duration(durationMillis),
                    externalId = ex_id)
        }
        cursor.close()
        return result
    }

}