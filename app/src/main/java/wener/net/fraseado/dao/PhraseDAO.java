package wener.net.fraseado.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

        Log.d(getClass().getSimpleName(), "Insert return: " + operationStatus);

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
                // set id
                int idIndex = cursor.getColumnIndex("ID");
                phrase.setContent(cursor.getString(idIndex));

                // set content
                int contentIndex = cursor.getColumnIndex("CONTENT");
                phrase.setContent(cursor.getString(contentIndex));

                // set creational date
                int initialDate = cursor.getColumnIndex("INITIAL_DATE");
                // get date in string
                String initialDateStr = cursor.getString(initialDate);
                // convert to util.Date
                phrase.setInitialDate(formatDate(initialDateStr));

                // add to list
                phrases.add(phrase);
            } while (cursor.moveToNext());
        }

        return phrases;
    }

    /**
     * Format date getted from db with string
     *
     * @param initialDateStr full date information
     * @return Date object
     */
    private Date formatDate(String initialDateStr) {
        Log.d(toString(), "Started in " + initialDateStr);

        Date date = null;

        DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            date = formatDate.parse(initialDateStr);
        } catch (ParseException exception) {
            exception.printStackTrace();
        }

        return date;
    }

    /**
     * Used into insert
     * @param operationStatus is returned value when db insert something
     */
    private boolean validateStatus(long operationStatus) {
        boolean valid = false;

        // if operation not equals to -1, so is valid!
        if(operationStatus != UNSUCCESSFUL_INSERT){
            valid = true;
        }

        return  valid;
    }
}
