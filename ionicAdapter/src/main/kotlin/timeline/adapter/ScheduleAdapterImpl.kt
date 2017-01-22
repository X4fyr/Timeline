package timeline.adapter

import timeline.domain.elements.ScheduledElement
import util.jsToKotlin.ionic.ionicNative.SQLite
import util.jsToKotlin.ionic.ionicNative
import utils.date.DateTime

class ScheduleAdapterImpl : ScheduleAdapter {
    val db = SQLite()

    object DB_CONFIG {
        val name = "data.db"
        val location = "default"
    }

    override fun saveToSchedule(element: ScheduledElement): ScheduledElement {
        db.openDatabase(DB_CONFIG).then { db ->
            db.executeSql(SQL_CREATE_SCHEDULE, {}).then {  } .catch {  }
        }
                .catch { error -> console.error("Error opening database", error) }
        return element //TODO
    }

    override fun deleteFromSchedule(element: ScheduledElement) {
        throw UnsupportedOperationException("not implemented") //TODO
    }

    override fun getByTimeSpan(start: DateTime, end: DateTime): Set<ScheduledElement> {
        throw UnsupportedOperationException("not implemented") //TODO
    }

    override fun getNext(element: ScheduledElement, count: Long): Set<ScheduledElement> {
        throw UnsupportedOperationException("not implemented") //TODO
    }

    override fun getPrev(element: ScheduledElement, count: Long): Set<ScheduledElement> {
        throw UnsupportedOperationException("not implemented") //TODO
    }
}