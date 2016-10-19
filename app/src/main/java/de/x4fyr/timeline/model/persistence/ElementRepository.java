package de.x4fyr.timeline.model.persistence;

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

    private final SQLiteOpenHelper helper;
    private static String TABLE_NAME = "elements";

    ElementRepository(SQLiteOpenHelper helper) {
        this.helper = helper;
    }

    public void delete(LocalDateTime localDateTime) {
        @Cleanup SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_NAME, "_id == " + String.valueOf(localDateTime.toEpochSecond(ZoneOffset.UTC)), null);
    }

    @Override
    public boolean exists(LocalDateTime localDateTime) {
        @Cleanup SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "_id == " + localDateTime.toEpochSecond(ZoneOffset.UTC), null, null, null, null);
        return cursor.getCount() > 0;
    }

    @Override
    public Element findOne(LocalDateTime localDateTime) {
        return null;
    }

    @Override
    public <S extends Element> S save(S entity) {
        return null;
    }

    private Element fromCursor(Cursor cursor) {
        return Element.builder()
                .start(LocalDateTime.ofEpochSecond(cursor.getLong(0), 0, ZoneOffset.UTC))
                .duration(Duration.ofSeconds(cursor.getLong(1)))
                .title(cursor.getString(2))
                .notes(cursor.getString(3)).build();
    }
}
