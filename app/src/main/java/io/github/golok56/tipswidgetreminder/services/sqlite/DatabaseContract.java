package io.github.golok56.tipswidgetreminder.services.sqlite;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public final class DatabaseContract {
    private DatabaseContract() {
    }

    public static final class PokemonCardsColumns implements BaseColumns {
        public static final String TABLE_NAME = "cards";
        public static final String NAME = "name";
        public static final String IMAGE = "image";
        public static final String RARITY = "rarity";
        public static final String SERIES = "series";
    }
}
