package com.example.android.popularmoviesstage2.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;

import java.util.Map;
import java.util.Set;

public class TestUtilities extends AndroidTestCase {
    static final int TEST_MOVIE_ID = 286217; // The Martian

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }

    static ContentValues createMovieValues() {
        ContentValues movieValues = new ContentValues();
        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, TEST_MOVIE_ID);
        movieValues.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE, "The Martian");
        movieValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, "/AjbENYG3b8lhYSkdrWwlhVLRPKR.jpg");
        movieValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, "2015-10-02");
        movieValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, 7.8);
        movieValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, "During a manned mission to " +
                "Mars, Astronaut Mark Watney is presumed dead after a fierce storm and left " +
                "behind +by his crew. But Watney has survived and finds himself stranded and " +
                "alone +on the hostile planet. With only meager supplies, he must draw upon his " +
                "+ingenuity, wit and spirit to subsist and find a way to signal to Earth that he " +
                "is alive.");

        return movieValues;
    }
}
