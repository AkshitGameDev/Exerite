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
    private static final int DATABASE_VERSION = 5;
    // Database name
    private static final String DATABASE_NAME = "Exerite5V.db";

    // Table and columns
    private static final String TABLE_JOURNALS = "Journals";
    private static final String COLUMN_ID = "journal_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_EMAIL = "email"; // New column for email

    private static final String TABLE_DIET = "DietTable";
    private static final String DCOLUMN_ID = "diet_id";
    private static final String DCOLUMN_EMAIL = "email_id";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_NAME = "diet_name";
    private static final String COLUMN_CALORIE = "diet_calorie";
    private static final String TABLE_EXERCISES = "exercises";
    private static final String COLUMN_WORKOUT_ID = "workoutid";
    private static final String WCOLUMN_EMAIL = "email";
    private static final String COLUMN_WORKOUT_NAME = "workout_name";
    private static final String COLUMN_WORKOUT_DESCRIPTION = "workout_description";
    private static final String WCOLUMN_CATEGORY = "category"; // New col


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
                "username TEXT NOT NULL," +
                "profile_image BLOB)";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_EXERCISES_TABLE = "CREATE TABLE " + TABLE_EXERCISES + " (" +
                COLUMN_WORKOUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_WORKOUT_NAME + " TEXT, " +
                COLUMN_WORKOUT_DESCRIPTION + " TEXT, " +
                COLUMN_CATEGORY + " TEXT)"; // New column added
        db.execSQL(CREATE_EXERCISES_TABLE);

        String CREATE_TABLE_JOURNALS =
                "CREATE TABLE " + TABLE_JOURNALS + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT NOT NULL, " +
                        COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                        COLUMN_EMAIL + " TEXT" + // Add email column
                        ");";
        db.execSQL(CREATE_TABLE_JOURNALS);

        String createTable = "CREATE TABLE " + TABLE_DIET + " (" +
                DCOLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DCOLUMN_EMAIL + " TEXT, " +
                COLUMN_CATEGORY + " TEXT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_CALORIE + " TEXT)";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exists and create fresh table
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS exercises");
        db.execSQL("DROP TABLE IF EXISTS Journals");
        db.execSQL("DROP TABLE IF EXISTS DietTable");

        onCreate(db);
    }

    public boolean insertData(String email, String password,String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("password", password);
        values.put("username", username);
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
        SQLiteDatabase db = null;
        Cursor cursor = null;
        boolean exists = false;

        try {
            db = this.getReadableDatabase();

            // Select only the required field, usually the primary key or any column
            String query = "SELECT 1 FROM users WHERE email = ? AND password = ?";

            // Using try-with-resources to ensure the cursor is closed automatically
            cursor = db.rawQuery(query, new String[]{email, password});

            // Check if the cursor has any records
            exists = cursor.moveToFirst(); // Returns true if there is at least one record
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close(); // Ensure the cursor is closed to avoid memory leaks
            }
            if (db != null) {
                db.close(); // Ensure the database is closed to free resources
            }
        }

        return exists;
    }





    public boolean addDiet(String email, String category, String name, String calorie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DCOLUMN_EMAIL, email);
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_CALORIE, calorie);

        long result = db.insert(TABLE_DIET, null, values);
        db.close();

        return result != -1;
    }

    public ArrayList<DietModel> getDiets(String email, String category) {
        ArrayList<DietModel> dietList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_DIET + " WHERE " + DCOLUMN_EMAIL + "=? AND " + COLUMN_CATEGORY + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{email, category});

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String calorie = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CALORIE));
                String demail = cursor.getString(cursor.getColumnIndexOrThrow(DCOLUMN_EMAIL));
                String dcategory = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY));

                dietList.add(new DietModel(demail,dcategory,name,calorie));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return dietList;
    }


    public boolean addExercise(String email, String workoutName, String workoutDescription, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WCOLUMN_EMAIL, email);
        values.put(COLUMN_WORKOUT_NAME, workoutName);
        values.put(COLUMN_WORKOUT_DESCRIPTION, workoutDescription);
        values.put(WCOLUMN_CATEGORY, category); // Include category

        long result = db.insert(TABLE_EXERCISES, null, values);
        db.close();

        return result != -1;
    }

    public ArrayList<ExersiseModel> getExercises(String email, String category) {
        ArrayList<ExersiseModel> exerciseList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // SQL query to filter by both email and category
        String selection = WCOLUMN_EMAIL + "=? AND " + WCOLUMN_CATEGORY + "=?";
        String[] selectionArgs = new String[]{email, category};

        Cursor cursor = db.query(
                TABLE_EXERCISES,
                new String[]{COLUMN_WORKOUT_ID, WCOLUMN_EMAIL, COLUMN_WORKOUT_NAME, COLUMN_WORKOUT_DESCRIPTION, WCOLUMN_CATEGORY},
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WORKOUT_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WORKOUT_DESCRIPTION));
                String cat = cursor.getString(cursor.getColumnIndexOrThrow(WCOLUMN_CATEGORY)); // Retrieve category

                // Create ExersiseModel with category
                exerciseList.add(new ExersiseModel(email,name, description, cat));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return exerciseList;
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

    public void deleteDiet(String dishName) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // Specify the WHERE clause and arguments
            String whereClause = COLUMN_NAME + "=?";
            String[] whereArgs = new String[]{dishName};

            // Perform the delete operation
            int rowsDeleted = db.delete(TABLE_DIET, whereClause, whereArgs);
            if (rowsDeleted > 0) {
                Log.d("DBHelper", "Diet entry deleted successfully: " + dishName);
            } else {
                Log.d("DBHelper", "No diet entry found with name: " + dishName);
            }
        } catch (Exception e) {
            Log.e("DBHelper", "Error while trying to delete diet entry", e);
        } finally {
            db.close();
        }
    }



}
