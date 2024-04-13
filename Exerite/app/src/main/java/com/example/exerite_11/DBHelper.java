package com.example.exerite_11;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {
    // Database version
    private static final int DATABASE_VERSION = 1;
    // Database name
    private static final String DATABASE_NAME = "User_data.db";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the users table
        String CREATE_USERS_TABLE = "CREATE TABLE users (" +
                "userid INTEGER PRIMARY KEY AUTOINCREMENT," +
                "email TEXT," +
                "password TEXT)";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_WORKOUTS_TABLE = "CREATE TABLE workouts (" +
                "workoutid INTEGER PRIMARY KEY AUTOINCREMENT," +
                "userid INTEGER," + "WorkoutName TEXT, " +
                "date TEXT," +
                "description TEXT)";
        db.execSQL(CREATE_WORKOUTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exists and create fresh table
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS workouts");
        onCreate(db);
    }

    public boolean insertData(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("password", password);
        long result = db.insert("users", null, values);
        db.close(); // Closing database connection
        return result != -1;
    }

    public boolean checkUserEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close(); // Closing cursor
        db.close(); // Closing database connection
        return exists;
    }

    public boolean checkPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ? AND password = ?", new String[]{email, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close(); // Closing cursor
        db.close(); // Closing database connection
        return exists;
    }

    public boolean hasRecentWorkouts(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM workouts WHERE userid = ? ORDER BY date DESC LIMIT 1", new String[]{String.valueOf(userId)});
        boolean hasRecentWorkouts = cursor.getCount() > 0;
        cursor.close(); // Closing cursor
        db.close(); // Closing database connection
        return hasRecentWorkouts;
    }

    public boolean insertWorkout(int userId, String workoutName, String workoutDescription) {
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());

        ContentValues values = new ContentValues();
        values.put("userid", userId);
        values.put("WorkoutName", workoutName);
        values.put("date", currentDateTime);
        values.put("description", workoutDescription);

        long result = db.insert("workouts", null, values);
        db.close(); // Closing database connection
        return result != -1;
    }
}
