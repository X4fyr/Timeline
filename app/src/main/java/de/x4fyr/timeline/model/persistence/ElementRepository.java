package de.x4fyr.timeline.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import de.x4fyr.timeline.model.Element;
import lombok.Cleanup;
import org.threeten.bp.Duration;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by x4fyr on 10/19/16.
 */
public class ElementRepository implements CRUDRepository<Element, LocalDateTime> {

    private final SQLiteDatabaseAdapter db;
    private static String TABLE_NAME = "elements";

    ElementRepository(SQLiteDatabaseAdapter db) {
        this.db = db;
    }

    public void delete(LocalDateTime localDateTime) {
        db.delete(TABLE_NAME, "_id == " + String.valueOf(localDateTime.toEpochSecond(ZoneOffset.UTC)), null);
    }

    @Override
    public boolean exists(LocalDateTime localDateTime) {
        Cursor cursor = db.query(TABLE_NAME, null, "_id == " + localDateTime.toEpochSecond(ZoneOffset.UTC), null, null, null, null);
        return cursor.getCount() > 0;
    }

    @Override
    public Element findOne(LocalDateTime localDateTime) {
        Cursor cursor = db.query(TABLE_NAME, null, "_id == " + localDateTime.toEpochSecond(ZoneOffset.UTC), null, null, null, null);
        return fromCursor(cursor);
    }

    @Override
    public <S extends Element> S save(S entity) {
        ContentValues values = new ContentValues(4);
        values.put("_id", entity.getStart().toEpochSecond(ZoneOffset.UTC));
        values.put("duration", entity.getDuration().getSeconds());
        values.put("title", entity.getTitle());
        values.put("notes", entity.getNotes());
        db.insert(TABLE_NAME, null, values);
        return entity;
    }

    private Element fromCursor(Cursor cursor) {
        return Element.builder()
                .start(LocalDateTime.ofEpochSecond(cursor.getLong(0), 0, ZoneOffset.UTC))
                .duration(Duration.ofSeconds(cursor.getLong(1)))
                .title(cursor.getString(2))
                .notes(cursor.getString(3)).build();
    }

}
