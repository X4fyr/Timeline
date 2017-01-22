package timeline.adapter

const val TABLE_NAME_SCHEDULE = "schedule"
const val TABLE_NAME_EXTERNAL = "external"
const val TABLE_NAME_TODO = "todo"
const val COLUMN_NAME_ID = "id"
const val COLUMN_NAME_TITLE = "title"
const val COLUMN_NAME_NOTES = "notes"
const val COLUMN_NAME_START = "start"
const val COLUMN_NAME_EXTERNAL_UUID = "externalUUID"
const val COLUMN_NAME_EXTERNAL_ID = "externalId"
const val COLUMN_NAME_DURATION = "duration"
const val COLUMN_NAME_SCHEDULE_ID = "scheduleId"
const val COLUMN_NAME_PLANNED_DATE = "plannedDate"
const val COLUMN_NAME_PLANNED_DURATION = "plannedDuration"

const val SQL_CREATE_SCHEDULE = "CREATE TABLE " + TABLE_NAME_SCHEDULE + " (" +
        COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
        COLUMN_NAME_TITLE + " TEXT NOT NULL," +
        COLUMN_NAME_NOTES + " TEXT NOT NULL," +
        COLUMN_NAME_START + " INTEGER NOT NULL," +
        COLUMN_NAME_DURATION + " INTEGER NOT NULL," +
        "FOREIGN KEY(" + COLUMN_NAME_EXTERNAL_ID + ") REFERENCES " + TABLE_NAME_EXTERNAL + "(" + COLUMN_NAME_ID + ")" +
        ")"

const val SQL_CREATE_EXTERNAL = "CREATE TABLE " + TABLE_NAME_EXTERNAL + " (" +
        COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
        COLUMN_NAME_EXTERNAL_UUID + " TEXT NOT NULL" +
        "FOREIGN KEY(" + COLUMN_NAME_SCHEDULE_ID + ") REFERENCES " + TABLE_NAME_SCHEDULE + "(" + COLUMN_NAME_ID + ")" +
        ")"

const val SQL_CREATE_TODO = "CREATE TABLE " + TABLE_NAME_TODO + " (" +
        COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
        COLUMN_NAME_TITLE + " TEXT NOT NULL," +
        COLUMN_NAME_NOTES + " TEXT NOT NULL," +
        COLUMN_NAME_PLANNED_DATE + " TEXT," +
        COLUMN_NAME_PLANNED_DURATION + " TEXT" +
        ")"
