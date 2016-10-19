package de.x4fyr.timeline.model.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import de.x4fyr.timeline.Timeline;

import java.util.Optional;


/**
 * SQLiteOpenHelper implementation creating and updating the database.
 */
class DBOpenHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "main.db";
    private static int DB_VERSION = 1; // Update onUpgrade when changing this
    private static String DB_CREATE;

    static { //Create db layout
        StringBuilder sb  = new StringBuilder();
        sb
                .append("CREATE TABLE `elements` (")
                .append("  `_id` INTEGER NOT NULL, `duration` INTEGER NULL, `title` TEXT NOT NULL, `notes` TEXT NULL,")
                .append("  PRIMARY KEY (`_id`));");
        DB_CREATE = sb.toString();
    }

    DBOpenHelper() {
        super(Timeline.getContext().get(), DB_NAME, null, DB_VERSION);
        if (!Timeline.getContext().isPresent()) {
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
