package de.x4fyr.timeline.adapter

import android.provider.BaseColumns

/**
 * Contract defining the schema of the main db
 *
 * @author x4fyr
 * Created on 1/2/17.
 */

const val TABLE_NAME_SCHEDULE = "schedule"
const val TABLE_NAME_EXTERNAL = "external"
const val TABLE_NAME_TODO = "todo"
const val COLUMN_NAME_ID = BaseColumns._ID
const val COLUMN_NAME_TITLE = "title"
const val COLUMN_NAME_NOTES = "notes"
const val COLUMN_NAME_START = "start"
const val COLUMN_NAME_EXTERNAL_UUID = "externalUUID"
const val COLUMN_NAME_DURATION = "duration"
const val COLUMN_NAME_IS_SCHEDULED = "isScheduled"
const val COLUMN_NAME_PLANNED_DATE = "plannedDate"
const val COLUMN_NAME_PLANNED_DURATION = "plannedDuration"

const val SQL_CREATE_SCHEDULE = "CREATE TABLE " + TABLE_NAME_SCHEDULE + " (" +
        COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
        COLUMN_NAME_TITLE + " TEXT NOT NULL," +
        COLUMN_NAME_NOTES + " TEXT NOT NULL," +
        COLUMN_NAME_START + " TEXT NOT NULL," +
        COLUMN_NAME_DURATION + " TEXT NOT NULL," +
        COLUMN_NAME_EXTERNAL_UUID + " TEXT" +
        ")"
const val SQL_DELETE_SCHEDULE = "DROP TABLE IF EXISTS " + TABLE_NAME_SCHEDULE

const val SQL_CREATE_EXTERNAL = "CREATE TABLE " + TABLE_NAME_EXTERNAL + " (" +
        COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
        COLUMN_NAME_EXTERNAL_UUID + " TEXT NOT NULL" +
        COLUMN_NAME_IS_SCHEDULED + " INTEGER NOT NULL" +
        ")"
const val SQL_DELETE_EXTERNAL = "DROP TABLE IF EXISTS " + TABLE_NAME_EXTERNAL

const val SQL_CREATE_TODO = "CREATE TABLE " + TABLE_NAME_TODO + " (" +
        COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
        COLUMN_NAME_TITLE + " TEXT NOT NULL," +
        COLUMN_NAME_NOTES + " TEXT NOT NULL," +
        COLUMN_NAME_PLANNED_DATE + " TEXT," +
        COLUMN_NAME_PLANNED_DURATION + " TEXT" +
        ")"
const val SQL_DELETE_TODO = "DROP TABLE IF EXISTS " + TABLE_NAME_TODO
