package de.x4fyr.timeline.model.persistence

import android.content.ContentValues
import android.database.Cursor
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.test.mock.MockContext
import com.andrewreitz.spock.android.AndroidSpecification
import de.x4fyr.timeline.model.Element
import org.threeten.bp.Duration
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset


/**
 * Created by x4fyr on 10/19/16.
 */
class ElementRepositorySpec extends AndroidSpecification {

    static String DB_NAME = "main"
    static int DB_VERSION = 1
    static String TABLE_NAME = ElementRepository.TABLE_NAME;

    def SQLiteDatabaseAdapter db
    def ElementRepository repository

    void setup() {
        db = Mock(SQLiteDatabaseAdapter.class)
        repository = new ElementRepository(db)
    }

    def "Delete"() {
        given:
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(a, 0, ZoneOffset.UTC)
        when:
        repository.delete(localDateTime)
        then:
        1 * db.delete(TABLE_NAME, b, null) >> 1
        0 * _
        where:
        a << [1, 2, 3]
        b = "_id == " + a.toString()

    }

    def "Exists"() {
        given:
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(a, 0, ZoneOffset.UTC)
        Cursor cursor = Stub(Cursor.class) {
            getCount() >> c
        }
        when:
        def result = repository.exists(localDateTime)
        then:
        assert result == d
        1 * db.query(TABLE_NAME, null, b, null, null, null, null) >> cursor
        where:
        a | c
        1 | 1
        2 | 0
        3 | 1

        b = "_id == " + a.toString()
        d = c > 0
    }

    def "FindOne"() {
        given:
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(start, 0, ZoneOffset.UTC)
        Cursor cursor = Stub(Cursor.class) {
            getLong(0) >> start;
            getLong(1) >> duration
            getString(2) >> title
            getString(3) >> notes
        }
        when:
        def Element result = repository.findOne(localDateTime)
        then:
        assert result.start == localDateTime
        assert result.duration == Duration.ofSeconds(duration)
        assert result.title == title
        assert result.notes == notes
        1 * db.query(TABLE_NAME, null, b, null, null, null, null) >> cursor;
        where:
        start | duration | title    | notes
        1     | 1        | "title1" | "note1"
        2     | 2        | "title2" | "note2"
        3     | 3        | "title3" | "note3"

        b = "_id == " + start.toString()
    }

    def "Save"() {
        given:
        def Element entity = new Element(LocalDateTime.ofEpochSecond(start, 0, ZoneOffset.UTC), Duration.ofSeconds(duration), title, notes)
        def ContentValues values = new ContentValues(4)
        values.put("_id", start)
        values.put("duration", duration)
        values.put("title", title)
        values.put("notes", notes)
        when:
        def Element result = repository.save(entity)
        then:
        assert result == entity
        1 * db.insert(TABLE_NAME, null, values)
        where:
        start | duration | title    | notes
        1     | 1        | "title1" | "note1"
        2     | 2        | "title2" | "note2"
        3     | 3        | "title3" | "note3"
    }

}
