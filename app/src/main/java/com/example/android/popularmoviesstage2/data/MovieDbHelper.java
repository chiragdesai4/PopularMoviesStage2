package com.example.android.popularmoviesstage2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.android.popularmoviesstage2.data.MovieContract.MovieEntry;
import static com.example.android.popularmoviesstage2.data.MovieContract.ReviewEntry;
import static com.example.android.popularmoviesstage2.data.MovieContract.TrailerEntry;

public class MovieDbHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "movies.db";
    // Increment if change in DB schema
    private static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIES_TABLE =
                "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
                        MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        MovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                        MovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, " +
                        MovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                        MovieEntry.COLUMN_RELEASE_DATE + " INTEGER NOT NULL, " +
                        MovieEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, " +
                        MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL" +
                        " );";

        final String SQL_CREATE_TRAILERS_TABLE =
                "CREATE TABLE " + TrailerEntry.TABLE_NAME + " (" +
                        TrailerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        TrailerEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                        TrailerEntry.COLUMN_TRAILER_KEY + " TEXT NOT NULL," +

                        // Foreign key referencing movie table
                        " FOREIGN KEY (" + TrailerEntry.COLUMN_MOVIE_ID + ") REFERENCES " +
                        MovieEntry.TABLE_NAME + " (" + MovieEntry._ID + ") );";

        final String SQL_CREATE_REVIEWS_TABLE =
                "CREATE TABLE " + ReviewEntry.TABLE_NAME + " (" +
                        ReviewEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        ReviewEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                        ReviewEntry.COLUMN_AUTHOR + " TEXT NOT NULL, " +
                        ReviewEntry.COLUMN_CONTENT + " TEXT NOT NULL," +

                        // Foreign key referencing movie table
                        " FOREIGN KEY (" + ReviewEntry.COLUMN_MOVIE_ID + ") REFERENCES " +
                        MovieEntry.TABLE_NAME + " (" + MovieEntry._ID + ") );";

        db.execSQL(SQL_CREATE_MOVIES_TABLE);
        db.execSQL(SQL_CREATE_TRAILERS_TABLE);
        db.execSQL(SQL_CREATE_REVIEWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TrailerEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ReviewEntry.TABLE_NAME);
        onCreate(db);
    }
}
