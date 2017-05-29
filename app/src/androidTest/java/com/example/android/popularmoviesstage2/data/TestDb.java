package com.example.android.popularmoviesstage2.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.example.android.popularmoviesstage2.data.MovieDbHelper;

import java.util.HashSet;

public class TestDb extends AndroidTestCase {

    public static final String LOG_TAG = TestDb.class.getSimpleName();

    // Since we want each test to start with a clean slate
    private void deleteTheDatabase() {
        mContext.deleteDatabase(MovieDbHelper.DATABASE_NAME);
    }

    // Called before each test
    public void setUp() {
        deleteTheDatabase();
    }

    public void testCreateDb() throws Throwable {
        // Hashset containing all the table names to look for
        final HashSet<String> tableNameHashSet = new HashSet<>();
        tableNameHashSet.add(MovieContract.MovieEntry.TABLE_NAME);
        tableNameHashSet.add(MovieContract.TrailerEntry.TABLE_NAME);
        tableNameHashSet.add(MovieContract.ReviewEntry.TABLE_NAME);

        // Delete db
        mContext.deleteDatabase(MovieDbHelper.DATABASE_NAME);

        // Open db
        SQLiteDatabase db = new MovieDbHelper(mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        // Are the tables created correctly?
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        assertTrue("DB is not created correctly", c.moveToFirst());

        // Verify that all tables has been created by removing the same strings in the hashtable
        do {
            tableNameHashSet.remove(c.getString(0));
        } while (c.moveToNext());

        // Fails if one or more tables are missing
        assertTrue("DB is missing a table.", tableNameHashSet.isEmpty());

        // Does the tables contain the correct columns
        c = db.rawQuery("PRAGMA table_info(" + MovieContract.MovieEntry.TABLE_NAME + ")",
                null);

        assertTrue("Couldn't get db table information.", c.moveToFirst());

        // Hashset containing columns we're looking for
        final HashSet<String> movieColumnHashSet = new HashSet<>();
        movieColumnHashSet.add(MovieContract.MovieEntry._ID);
        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_MOVIE_ID);
        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE);
        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_POSTER_PATH);
        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_RELEASE_DATE);
        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE);
        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_OVERVIEW);

        int columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            movieColumnHashSet.remove(columnName);
        } while (c.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("DB doesn't contain required columns.",
                movieColumnHashSet.isEmpty());

        db.close();
    }

    public void testMovieTable() {
        long movieRowId = insertMovie();
    }

    public long insertMovie() {

        // Open db
        SQLiteDatabase db = new MovieDbHelper(mContext).getWritableDatabase();

        // Get test values defined
        ContentValues testValues = TestUtilities.createMovieValues();

        // Insert a row of data and verify it was inserted
        long movieRowId = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, testValues);
        assertTrue("Error inserting movie in table", movieRowId != -1);

        Cursor cursor = db.query(
                MovieContract.MovieEntry.TABLE_NAME,// Table to Query
                null, // all columns
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );
        assertTrue("No data returned when querying movie table", cursor.moveToFirst());

        TestUtilities.validateCurrentRecord("Movie query validation failed", cursor, testValues);

        assertFalse("More than one recorned returned from the table", cursor.moveToNext());

        cursor.close();
        db.close();
        return movieRowId;
    }
}
