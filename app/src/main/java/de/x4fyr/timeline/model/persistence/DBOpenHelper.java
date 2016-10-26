package de.x4fyr.timeline.model.persistence;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import de.x4fyr.timeline.Timeline;

/**
 * SQLiteOpenHelper implementation creating and updating the database.
 */
class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "main.db";
    private static final int DB_VERSION = 1; // Update onUpgrade when changing this
    private static final String DB_CREATE = "CREATE TABLE `elements` (" +
            "  `_id` INTEGER NOT NULL, `duration` INTEGER NULL, `title` TEXT NOT NULL, `notes` TEXT NULL," +
            "  PRIMARY KEY (`_id`));";

    /**
     * Constructor.
     */
    DBOpenHelper() {
        super(Timeline.getContext(), DB_NAME, null, DB_VERSION);
        if (Timeline.getContext() == null) {
            throw new NullPointerException("Context initialised before accessing the db.");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Fill on db increase
    }
}
