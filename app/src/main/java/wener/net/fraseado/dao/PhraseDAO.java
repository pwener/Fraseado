package wener.net.fraseado.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import wener.net.fraseado.helper.DatabaseHelper;
import wener.net.fraseado.model.Phrase;

public class PhraseDAO {
    private final Context context;

    private static final long UNSUCCESSFUL_INSERT = -1;

    public PhraseDAO(Context context){
        this.context = context;
    }

    public boolean insert(Phrase phrase) {
        SQLiteDatabase sqlite = new DatabaseHelper(context).getWritableDatabase();

        ContentValues content = new ContentValues();
        content.put("CONTENT", phrase.getContent());

        long operationStatus = sqlite.insert(Phrase.tableName(), null, content);

        boolean isSuccessful = validateStatus(operationStatus);

        return isSuccessful;
    }

    public List<Phrase> selectAll() {
        List<Phrase> phrases = new ArrayList<Phrase>();

        String selectQuery = "SELECT * FROM " + Phrase.tableName();

        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();

        Cursor cursor = null;

        try {
            cursor = db.rawQuery(selectQuery, null);

            phrases = mountPhrases(cursor);
        } catch (Exception exception) {
            Log.e(getClass().getSimpleName(), "An exception occurs", exception);
        } finally {
            cursor.close();
            db.close();
        }

        Log.d(getClass().getSimpleName(), "Size of return: " + phrases.size());

        return  phrases;
    }

    /**
     * This method is invoked into selectAll, after running the SQL.
     *
     * @param cursor run query and get result
     * @return
     */
    private List<Phrase> mountPhrases(Cursor cursor) {
        List<Phrase> phrases = new ArrayList<Phrase>();

        if (cursor.moveToFirst()) {
            do {
                Phrase phrase = new Phrase();

                int idIndex = cursor.getColumnIndex("ID");
                phrase.setContent(cursor.getString(idIndex));

                int contentIndex = cursor.getColumnIndex("CONTENT");
                phrase.setContent(cursor.getString(contentIndex));

                phrases.add(phrase);
            } while (cursor.moveToNext());
        }

        return phrases;
    }

    private boolean validateStatus(long operationStatus) {
        boolean valid = false;

        // if operation not equals to -1, so is valid!
        if(operationStatus != UNSUCCESSFUL_INSERT){
            valid = true;
        }

        return  valid;
    }
}