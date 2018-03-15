package com.example.mirko.todo_liste;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreasmaier on 15.03.18.
 */

public class ListEntryDBHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "todoliste";
    public static final String TABLE_TOOO = "todo";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TEXT = "textdata";
    public static final String SQL_CREATE = "CREATE TABLE " + TABLE_TOOO + " (" +
            COLUMN_ID + " TEXT PRIMARY KEY NOT NULL, " +
            COLUMN_TEXT + " TEXT NOT NULL);";

    private static final String[] COLUMNS = {
            COLUMN_ID, COLUMN_TEXT
    };


    private SQLiteDatabase database;

    public ListEntryDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void open() {
        this.database = getWritableDatabase();

    }

    @Override
    public void close() {
        super.close();
    }

    public void saveEntry(ListEntry listEntry) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, listEntry.getId());
        contentValues.put(COLUMN_TEXT, listEntry.getText());
        database.insert(TABLE_TOOO, null, contentValues);
    }

    public List<ListEntry> getAllEntries() {
        List<ListEntry> entryList = new ArrayList<>();
        Cursor cursor = this.database.query(TABLE_TOOO, COLUMNS, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            while (cursor.moveToNext()) {
                ListEntry listEntry = cursorToEntryList(cursor);
                entryList.add(listEntry);
            }

            cursor.close();
        }

        return entryList;
    }

    private ListEntry cursorToEntryList(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(COLUMN_ID);
        int idText = cursor.getColumnIndex(COLUMN_TEXT);

        String id = cursor.getString(idIndex);
        String text = cursor.getString(idText);
        ListEntry listEntry = new ListEntry(text);
        listEntry.setId(id);
        return listEntry;

    }

    public boolean removeListEntry(ListEntry listEntry) {
        if (listEntry != null) {
            String[] args = new String[]{listEntry.getId()};
            return database.delete(TABLE_TOOO,
                    COLUMN_ID + " = ? ", args) > 0;
        }
        return false;
    }
}
