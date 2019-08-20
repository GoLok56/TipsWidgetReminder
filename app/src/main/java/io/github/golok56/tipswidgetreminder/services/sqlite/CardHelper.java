package io.github.golok56.tipswidgetreminder.services.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import io.github.golok56.tipswidgetreminder.PokemonCard;

public class CardHelper {
    private static final String DATABASE_TABLE = DatabaseContract.PokemonCardsColumns.TABLE_NAME;
    private final DatabaseHelper databaseHelper;
    private static CardHelper INSTANCE;

    private SQLiteDatabase database;

    private CardHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static CardHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CardHelper(context);
                }
            }
        }
        return INSTANCE;
    }


    public void open() {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();

        if (database.isOpen()) database.close();
    }

    public ArrayList<PokemonCard> findAll() {
        ArrayList<PokemonCard> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null, DatabaseContract.PokemonCardsColumns._ID + " DESC"
                , null);
        cursor.moveToFirst();
        PokemonCard pokemonCard;
        if (cursor.getCount() > 0) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.PokemonCardsColumns.NAME));
                String image = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.PokemonCardsColumns.IMAGE));
                String rarity = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.PokemonCardsColumns.RARITY));
                String series = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.PokemonCardsColumns.SERIES));

                pokemonCard = new PokemonCard(name, image, rarity, series);
                arrayList.add(pokemonCard);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(PokemonCard pokemonCard) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(DatabaseContract.PokemonCardsColumns.NAME, pokemonCard.getName());
        initialValues.put(DatabaseContract.PokemonCardsColumns.IMAGE, pokemonCard.getImage());
        initialValues.put(DatabaseContract.PokemonCardsColumns.RARITY, pokemonCard.getRarity());
        initialValues.put(DatabaseContract.PokemonCardsColumns.SERIES, pokemonCard.getSeries());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }
}