package com.myandroidmovielib;

/**
 * Created by jklaz on 12-Apr-17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class Sqlfunc extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "movies.db";
    public static final String TABLE_NAME = "movies";
    public static final String ID = "ID";
    public static final String TITLE = "TITLE";
    public static final String RELEASEDATE = "RELEASEDATE";
    public static final String RATING = "RATING";

    public Sqlfunc(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT , %s TEXT , %s REAL ,unique (%s, %s))", TABLE_NAME, ID, TITLE, RELEASEDATE, RATING, TITLE, RELEASEDATE);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insert(Movie movie){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(TITLE, movie.getTitle());
        cv.put(RELEASEDATE, movie.getReleaseDate());
        cv.put(RATING, movie.getRating());

        long result = db.insert(TABLE_NAME, null, cv);

        db.close();

        return result;
    }

    public long update(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TITLE, movie.getTitle());
        values.put(RELEASEDATE, movie.getReleaseDate());
        values.put(RATING, movie.getRating());
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE    TITLE = ? AND RELEASEDATE = ? AND ID <> ?", new String[]{movie.getTitle(),String.valueOf(movie.getReleaseDate()),String.valueOf(movie.getId())});

        if (mCursor.getCount() > 0)
        {
            /* record exist */
            db.close();
            return -1;
        }
        else
        {
            /* record not exist */
            long result = db.update(TABLE_NAME, values, ID + " = ?", new String[] { String.valueOf(movie.getId()) });
            db.close();
            return result;
        }

    }


    public ArrayList<Movie> fetch(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Movie> movies = new ArrayList<Movie>();

        Cursor cursor = db.query(TABLE_NAME,
                new String[] {ID, TITLE, RELEASEDATE, RATING}, null, null, null, null, null);

        while (cursor.moveToNext()) {
            Movie movie = new Movie();
            movie.setId(cursor.getInt(0));
            movie.setTitle(cursor.getString(1));
            movie.setReleaseDate(cursor.getInt(2));
            movie.setRating(cursor.getFloat(3));
            movies.add(movie);
        }
        return movies;
    }

    public Movie fetchOne(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Movie movie = null;

        Cursor cursor = db.query(TABLE_NAME, new String[] {ID, TITLE, RELEASEDATE, RATING}, ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            movie = new Movie();
            movie.setId(cursor.getInt(0));
            movie.setTitle(cursor.getString(1));
            movie.setReleaseDate(cursor.getInt(2));
            movie.setRating(cursor.getFloat(3));
        }

        return movie;
    }

    public ArrayList<Movie> fetchTop3() {//fetch top 3 with rating >=2.5 (Ratings are 0-5)
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Movie> moviesTop = new ArrayList<Movie>();

        Cursor cursor = db.query(TABLE_NAME,new String[] {ID, TITLE, RELEASEDATE, RATING},RATING + ">= 2.5",null,null,null,RATING +" DESC","3");
        while (cursor.moveToNext()) {
            Movie movie = new Movie();
            movie.setId(cursor.getInt(0));
            movie.setTitle(cursor.getString(1));
            movie.setReleaseDate(cursor.getInt(2));
            movie.setRating(cursor.getFloat(3));
            moviesTop.add(movie);
        }
        return moviesTop;
    }

    public ArrayList<Movie> fetchBottom3() {// fetch bottom 3 with rating <2.5 (Ratings are 0-5)
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Movie> moviesBottom = new ArrayList<Movie>();

        Cursor cursor = db.query(TABLE_NAME,new String[] {ID, TITLE, RELEASEDATE, RATING},RATING + "< 2.5",null,null,null,RATING +" ASC","3");
        while (cursor.moveToNext()) {
            Movie movie = new Movie();
            movie.setId(cursor.getInt(0));
            movie.setTitle(cursor.getString(1));
            movie.setReleaseDate(cursor.getInt(2));
            movie.setRating(cursor.getFloat(3));
            moviesBottom.add(movie);
        }
        return moviesBottom;
    }



    public void delete(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " = ?", new String[] { String.valueOf(movie.getId()) });
        db.close();
    }

    public int getCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }
}
