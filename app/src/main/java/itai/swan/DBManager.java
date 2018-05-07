package itai.swan;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import static itai.swan.DBManager.NotificationRulesTable.SQL_CREATE_NOTIFICATION_RULES_TABLE;

/**
 * Created by itai on 03/08/17.
 */

final public class DBManager {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DBManager() {}

    /* Inner class that defines the table contents */
    public static class NotificationRulesTable implements BaseColumns {
        public static final String TABLE_NAME = "notification_rules";
        public static final String COLUMN_RULE_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_MIN_HEIGHT = "min_height";
        public static final String COLUMN_SPOT = "spot";
        public static final String COLUMN_MIN_STARS = "min_stars";

        public static final String SQL_CREATE_NOTIFICATION_RULES_TABLE =
                "CREATE TABLE " + NotificationRulesTable.TABLE_NAME + " (" +
                        NotificationRulesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        NotificationRulesTable.COLUMN_RULE_NAME + " TEXT," +
                        NotificationRulesTable.COLUMN_DESCRIPTION + " TEXT," +
                        NotificationRulesTable.COLUMN_MIN_HEIGHT + " REAL," +
                        NotificationRulesTable.COLUMN_SPOT + " INTEGER," +
                        NotificationRulesTable.COLUMN_MIN_STARS + " INTEGER)";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + NotificationRulesTable.TABLE_NAME;
    }

    static public class DBHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "Swan.db";

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_NOTIFICATION_RULES_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            /*// This database is only a cache for online data, so its upgrade policy is
            // to simply discard the data and start over
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);*/
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            /*onUpgrade(db, oldVersion, newVersion);*/
        }
    }

    static public boolean tableExists(SQLiteDatabase db, String tableName)
    {
        if (tableName == null || db == null || !db.isOpen())
        {
            return false;
        }
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", tableName});
        if (!cursor.moveToFirst())
        {
            cursor.close();
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }
}

