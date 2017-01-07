package de.x4fyr.timeline.adapter

import android.content.ContentValues
import android.util.Log
import de.x4fyr.timeline.domain.elements.ScheduledElement
import org.joda.time.LocalDateTime

/**
 * @author x4fyr
 * Created on 1/2/17.
 */
class ScheduleAdapterImpl(val dbHelper: DBHelper) : ScheduleAdapter {

    /** Save the given ScheduleElement to the schedule persistence */
    override fun saveToSchedule(element: ScheduledElement): ScheduledElement {
        val db = dbHelper.writableDatabase
        if (dbHelper.writableDatabase == null) {
            Log.e(this.toString(), "Could not get a writable database")
            throw RuntimeException("Could not get a writable database")
        }
        val values = ContentValues()
        values.put(COLUMN_NAME_START, element.start.toString())
        values.put(COLUMN_NAME_DURATION, element.duration.toString())
        values.put(COLUMN_NAME_TITLE, element.title)
        values.put(COLUMN_NAME_NOTES, element.notes)
        val line = db.insert(TABLE_NAME_SCHEDULE, null, values)
        if (line < 0) {
            throw RuntimeException("Could not write to database")
        } else {
            return element
        }
    }

    /** Delete given ScheduleElement from schedule persistence */
    override fun deleteFromSchedule(element: ScheduledElement) {
        throw UnsupportedOperationException("not implemented") //TODO
    }

    /** get ScheduleElements of given time span */
    override fun getByTimeSpan(start: LocalDateTime, end: LocalDateTime): Collection<ScheduledElement> {
        throw UnsupportedOperationException("not implemented") //TODO
    }

}