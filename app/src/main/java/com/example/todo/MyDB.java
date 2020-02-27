package com.example.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

public class MyDB {

    public final String TABLE = "Todo"; // name of table
    public final String ID = "_id";
    public final String NAME = "name";
    public final String CREATION ="creation";
    public final String FINISH ="finish";
    public final String COMPLETED="completed";
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase database;
    public MyDB(Context context) {
        dbHelper = new MyDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }


    public long createRecords(String id, String name, String creation, String finish, String completed) {
        ContentValues values = new ContentValues();
        values.put(ID, id);
        values.put(NAME, name);
        values.put(CREATION, String.valueOf(creation));
        values.put(FINISH, String.valueOf(finish));
        values.put(COMPLETED, completed);
        return database.insert(TABLE, null, values);
    }

    public Cursor selectContacts() {
        String[] cols = new String[]{ID, NAME, CREATION, FINISH, COMPLETED};
        Cursor mCursor = database.query(true, TABLE, cols, null
                , null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor; // iterate to get each value.
    }

    public Cursor selectContact(String id){
        String[] cols = new String[]{ID, NAME, CREATION, FINISH, COMPLETED};
        Cursor mCursor = database.query(true, TABLE, cols, ID+"=?", new String[] {id}, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public int getCount() {
        int count = (int) DatabaseUtils.queryNumEntries(database, TABLE);
        return count;
    }

    public boolean deleteContact(String id) {
        return database.delete(TABLE, ID + "=" + id, null) > 0;
    }

    public void updateContact(String id, String number){
        ContentValues values = new ContentValues();
        //new value
        database.update(TABLE,values,ID+"=?",new String[]{id});
    }

    public String getId(int index){
        Cursor c= selectContacts();
        c.move(index);
        return String.valueOf(c.getString(0));
    }
}

