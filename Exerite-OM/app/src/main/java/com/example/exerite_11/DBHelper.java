package com.example.exerite_11;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {
    // Database version
    private static final int DATABASE_VERSION = 1;
    // Database name
    private static final String DATABASE_NAME = "Exerite2V.db";

    // Table and columns
    private static final String TABLE_JOURNALS = "Journals";
    private static final String COLUMN_ID = "journal_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_EMAIL = "email"; // New column for email
    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the users table
        String CREATE_USERS_TABLE = "CREATE TABLE users (" +
                "userid INTEGER PRIMARY KEY AUTOINCREMENT," +
                "email TEXT NOT NULL," +
                "password TEXT NOT NULL," +
                "username TEXT NOT NULL,"+
                "profile_image BLOB)";
        db.execSQL(CREATE_USERS_TABLE);


        String CREATE_WORKOUTS_TABLE = "CREATE TABLE workouts (" +
                "workoutid INTEGER PRIMARY KEY AUTOINCREMENT," +
                "userid INTEGER," +
                "WorkoutName TEXT, " +
                "date TEXT," +
                "description TEXT)";
        db.execSQL(CREATE_WORKOUTS_TABLE);

        String CREATE_TABLE_JOURNALS =
                "CREATE TABLE " + TABLE_JOURNALS + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT NOT NULL, " +
                        COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                        COLUMN_EMAIL + " TEXT" + // Add email column
                        ");";
        db.execSQL(CREATE_TABLE_JOURNALS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exists and create fresh table
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS workouts");
        db.execSQL("DROP TABLE IF EXISTS Journals");
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

    public void insertJournal(String title, String description, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_EMAIL, email);

        long result = db.insert(TABLE_JOURNALS, null, values);
        if (result == -1) {
            Log.e("DatabaseHelper", "Error inserting journal");
        } else {
            Log.d("DatabaseHelper", "Journal inserted successfully");
        }

        db.close();
    }

    // Update an existing journal
    public void updateJournal(int id, String newTitle, String newDescription, String newEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, newTitle);
        values.put(COLUMN_DESCRIPTION, newDescription);
        values.put(COLUMN_EMAIL, newEmail);

        int result = db.update(TABLE_JOURNALS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        if (result == 0) {
            Log.e("DatabaseHelper", "Error updating journal");
        } else {
            Log.d("DatabaseHelper", "Journal updated successfully");
        }

        db.close();
    }

    public JournalModel getJournalById(int journal_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("Journals", new String[]{"journal_id", "title", "description","email"}, "journal_id=?", new String[]{String.valueOf(journal_id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            String useremail = cursor.getString(cursor.getColumnIndexOrThrow("email"));

            cursor.close();
            return new JournalModel(journal_id, title, description,useremail);
        }
        return null;
    }

    // Retrieve all journals
    public ArrayList<JournalModel> getAllJournals() {
        ArrayList<JournalModel> journals = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_JOURNALS;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int journalId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL));

                JournalModel journal = new JournalModel(journalId, title, description, email);
                journals.add(journal);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return journals;
    }
    public void deleteJournal(int journal_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Journals", "journal_id = ?", new String[]{String.valueOf(journal_id)});
        db.close();
    }

}
