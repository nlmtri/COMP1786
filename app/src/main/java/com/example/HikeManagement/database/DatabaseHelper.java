package com.example.HikeManagement.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "MyHikerApp.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_hiker";
    private static final String COLUMN_ID = "person_id";
    private static final String COLUMN_NAME = "hike_name";
    private static final String COLUMN_LOCATION = "hike_location";
    private static final String COLUMN_DATE = "hike_date";
    private static final String COLUMN_PARKING_AVAILABLE = "hike_parking_available";
    private static final String COLUMN_LENGTH = "hike_length";
    private static final String COLUMN_WEATHER_FORECAST = "hike_weather_forecast";
    private static final String COLUMN_TIME_ESTIMATED = "hike_time_estimated";
    private static final String COLUMN_DIFFICULTY_LEVEL = "hike_difficulty_level";
    private static final String COLUMN_DESCRIPTION = "hike_description";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_LOCATION + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_PARKING_AVAILABLE + " TEXT, " +
                COLUMN_LENGTH + " TEXT, " +
                COLUMN_WEATHER_FORECAST + " TEXT, " +
                COLUMN_TIME_ESTIMATED + " TEXT, " +
                COLUMN_DIFFICULTY_LEVEL + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addNewHike(
            String name,
            String location,
            String date,
            String parkingAvailable,
            String length,
            String weatherForecast,
            String estimatedTime,
            String difficultyLevel,
            String description
    ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, name);
        values.put(COLUMN_LOCATION, location);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_PARKING_AVAILABLE, parkingAvailable);
        values.put(COLUMN_LENGTH, length);
        values.put(COLUMN_WEATHER_FORECAST, weatherForecast);
        values.put(COLUMN_TIME_ESTIMATED, estimatedTime);
        values.put(COLUMN_DIFFICULTY_LEVEL, difficultyLevel);
        values.put(COLUMN_DESCRIPTION, description);

        long result = db.insert(TABLE_NAME, null, values);

        if (result == -1) {
            Toast.makeText(context, "Failed to Add.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Add Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllHikeInformation() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public void updateHikeInformation(
            String rowId,
            String name,
            String location,
            String date,
            String parkingAvailable,
            String length,
            String weatherForecast,
            String estimatedTime,
            String difficultyLevel,
            String description
    ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, name);
        values.put(COLUMN_LOCATION, location);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_PARKING_AVAILABLE, parkingAvailable);
        values.put(COLUMN_LENGTH, length);
        values.put(COLUMN_WEATHER_FORECAST, weatherForecast);
        values.put(COLUMN_TIME_ESTIMATED, estimatedTime);
        values.put(COLUMN_DIFFICULTY_LEVEL, difficultyLevel);
        values.put(COLUMN_DESCRIPTION, description);

        long result = db.update(TABLE_NAME, values, "person_id=?", new String[]{rowId});

        if (result == -1) {
            Toast.makeText(context, "Failed to Update.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteOneHikeInformation(String rowId) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "person_id=?", new String[]{rowId});

        if (result == -1) {
            Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllHikeInformation() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    public Cursor searchHikesByName(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {
                COLUMN_ID, COLUMN_NAME, COLUMN_LOCATION, COLUMN_DATE, COLUMN_PARKING_AVAILABLE,
                COLUMN_LENGTH, COLUMN_WEATHER_FORECAST, COLUMN_TIME_ESTIMATED, COLUMN_DIFFICULTY_LEVEL, COLUMN_DESCRIPTION
        };
        String selection = COLUMN_NAME + " LIKE ?";
        String[] selectionArgs = new String[]{"%" + query + "%"};

        return db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
    }
}
