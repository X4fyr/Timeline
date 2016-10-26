package de.x4fyr.timeline.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import de.x4fyr.timeline.model.Element;
import org.threeten.bp.Duration;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;

/**
 * Repository managing elements in the app SQLite db.
 */
public class ElementRepository implements CRUDRepository<Element, LocalDateTime> {

    private static final String TABLE_NAME = "elements";
    private final SQLiteDatabaseAdapter db;

    ElementRepository(SQLiteDatabaseAdapter db) {
        this.db = db;
    }

    public void delete(LocalDateTime localDateTime) {
        db.delete(TABLE_NAME, getIdWhereStatement(localDateTime), null);
    }

    @Override
    public boolean exists(LocalDateTime localDateTime) {
        Cursor cursor = db.query(TABLE_NAME, null,
                getIdWhereStatement(localDateTime), null, null, null, null);
        return cursor.getCount() > 0;
    }

    @Override
    public Element findOne(LocalDateTime localDateTime) {
        Cursor cursor = db.query(TABLE_NAME, null,
                getIdWhereStatement(localDateTime), null, null, null, null);
        return fromCursor(cursor);
    }

    @Override
    public <S extends Element> S save(S entity) {
        ContentValues values = new ContentValues();
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

    private String getIdWhereStatement(LocalDateTime localDateTime) {
        return "_id == " + localDateTime.toEpochSecond(ZoneOffset.UTC);
    }

}
