package wener.net.fraseado.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import wener.net.fraseado.model.Phrase;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "fraseado";

    private static final String PHRASE_TABLE_CREATE = "CREATE TABLE " + Phrase.tableName() + "(" +
            "ID PRIMARY KEY AUTOINCREMENT, " +
            "CONTENT TEXT, " +
            "INITIAL_DATE DATETIME DEFAULT CURRENT_TIMESTAMP, " +
            "LAST_EDIT_DATE DATETIME);";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * To create database
     *
     * @param db Database object
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PHRASE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
