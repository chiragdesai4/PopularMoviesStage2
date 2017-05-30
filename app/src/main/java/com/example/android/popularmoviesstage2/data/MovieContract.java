package com.example.android.popularmoviesstage2.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

public class MovieContract {

    /**
     * Inner class that defines the table contents of the movie table.
     */

    public static final String CONTENT_AUTHORITY = "com.example.android.popularmoviesstage2";
    public static final String PATH_MOVIE = "movie";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_TRAILER = "trailer";

    public static final String PATH_REVIEW = "review";

    public static long normalizeDate(long startDate) {

        Time time = new Time();
        time.set(startDate);

        int julianDay = Time.getJulianDay(startDate, time.gmtoff);

        return time.setJulianDay(julianDay);

    }
    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI =

                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =

                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String CONTENT_ITEM_TYPE =

                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        /**
         * Table name
         */
        public static final String TABLE_NAME = "movies";

        /**
         * TMDb id of movie
         */
        public static final String COLUMN_MOVIE_ID = "movie_id";

        /**
         * Original title of movie
         */
        public static final String COLUMN_ORIGINAL_TITLE = "original_title";

        /**
         * Release date of movie
         */
        public static final String COLUMN_RELEASE_DATE = "release_date";

        /**
         * TMDb path to poster
         */
        public static final String COLUMN_POSTER_PATH = "poster_path";

        /**
         * TMDb user vote average
         */
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";

        /**
         * TMDb user vote average
         */
        public static final String COLUMN_OVERVIEW = "overview";

        /**
         * TMDb user vote average
         */
        public static final String COLUMN_USER_FAVORITE = "user_favorite";

        public static Uri buildMovieUri() {

            return CONTENT_URI;
        }

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    /**
     * Inner class that defines the table contents of the trailers table.
     */
    public static final class TrailerEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILER).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILER;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILER;

        /**
         * Table name
         */
        public static final String TABLE_NAME = "trailers";

        /**
         * Foreign key: TMDb id of movie
         */
        public static final String COLUMN_MOVIE_ID = "movie_id";

        /**
         * Key of the link
         */
        public static final String COLUMN_TRAILER_KEY = "trailer_key";

        public static Uri buildMovieTrailerUri(long movieId) {

            return CONTENT_URI.buildUpon().appendPath(String.valueOf(movieId)).build();

        }
        public static String getMovieIdFromUri(Uri uri) {

            return uri.getPathSegments().get(1);

        }
    }

    /**
     * Inner class that defines the table contents of the reviews table.
     */
    public static final class ReviewEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEW).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEW;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEW;
        /**
         * Table name
         */
        public static final String TABLE_NAME = "reviews";

        /**
         * Foreign key: TMDb id of movie
         */
        public static final String COLUMN_MOVIE_ID = "movie_id";

        /**
         * Author of review
         */
        public static final String COLUMN_AUTHOR = "author";

        /**
         * Content of review
         */
        public static final String COLUMN_CONTENT = "content";

        public static Uri buildMovieReviewsUri(long movieId) {

            return CONTENT_URI.buildUpon().appendPath(String.valueOf(movieId)).build();
        }

        public static String getMovieIdFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }
}
