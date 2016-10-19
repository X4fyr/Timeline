package de.x4fyr.timeline.model.persistence

import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.test.mock.MockContext
import com.andrewreitz.spock.android.AndroidSpecification
import org.threeten.bp.LocalDateTime


/**
 * Created by x4fyr on 10/19/16.
 */
class ElementRepositorySpec extends AndroidSpecification {

    SQLiteOpenHelper helper
    SQLiteDatabase db;
    ElementRepository repository


    static String DB_NAME = "main"
    static int DB_VERSION = 1
    static String TABLE_NAME = ElementRepository.TABLE_NAME;

    void setup() {
        helper = Mock(SQLiteOpenHelper.class)
        db = GroovyMock(constructorArgs: ["", 0, (SQLiteDatabase.CursorFactory)null, (DatabaseErrorHandler)null], SQLiteDatabase.class)
        repository = new ElementRepository(helper)
    }

    void cleanup() {

    }

    def "Delete"() {
        given:
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(a)
        when:
        repository.delete(localDateTime)
        then:
        1 * helper.getWritableDatabase() >> db
        1 * db.delete(TABLE_NAME, b, null)
        0 * _
        where:
        a << [1, 2, 3]
        b = "_id == " + a.toString()

    }

    def "Exists"() {

    }

    def "FindOne"() {

    }

    def "Save"() {

    }
}
