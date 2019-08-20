package io.github.golok56.tipswidgetreminder.services.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbfilm.sqlite";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_MOVIE = String.format(
            "CREATE TABLE %s ( " +
                    "%s INTEGER PRIMARY KEY, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT);",
            DatabaseContract.PokemonCardsColumns.TABLE_NAME,
            DatabaseContract.PokemonCardsColumns._ID,
            DatabaseContract.PokemonCardsColumns.NAME,
            DatabaseContract.PokemonCardsColumns.IMAGE,
            DatabaseContract.PokemonCardsColumns.RARITY,
            DatabaseContract.PokemonCardsColumns.SERIES
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
