package de.x4fyr.timeline.adapter

import android.content.ContentValues
import android.util.Log
import de.x4fyr.timeline.domain.elements.TodoElement
import org.joda.time.LocalDate

class TodoListAdapterImpl(val dbHelper : DBHelper) : TodoListAdapter {

    override fun getByDate(date: LocalDate): Set<TodoElement> {
        throw UnsupportedOperationException("not implemented") //TODO
    }

    override fun getAll(): Set<TodoElement> {
        throw UnsupportedOperationException("not implemented") //TODO
    }

    object QUERIES {
        val DELETE = "id == "
    }

    override fun saveToToDoList(element: TodoElement): TodoElement {
        val db = dbHelper.writableDatabase
        if (db == null) {
            Log.e(this.toString(), "Could not get a writable database")
            throw RuntimeException("Could not get a writable database")
        }
        val values = ContentValues()
        values.put(COLUMN_NAME_ID, element.id)
        values.put(COLUMN_NAME_TITLE, element.title)
        values.put(COLUMN_NAME_NOTES, element.notes)
        values.put(COLUMN_NAME_PLANNED_DATE, element.plannedDate?.toDateTimeAtStartOfDay()?.millis)
        values.put(COLUMN_NAME_PLANNED_DURATION, element.plannedDuration?.millis)
        val id = db.insert(TABLE_NAME_TODO, null, values)
        if (id < 0) {
            Log.e(this.toString(), "Could not write to database")
            throw RuntimeException("Could not write to database")
        }
        return element.copy(id = id)
    }

    override fun deleteFromTodoList(element: TodoElement) {
        val db = dbHelper.writableDatabase
        if (db == null) {
            Log.e(this.toString(), "Could not get a writable database")
            throw RuntimeException("Could not get a writable database")
        }
        val affectedRows = db.delete(TABLE_NAME_TODO, QUERIES.DELETE, arrayOf(element.id?.toString()))
        if (affectedRows < 0) {
            Log.e(this.toString(), "Could not write to database")
            throw RuntimeException("Could not write to database")
        }
    }
}