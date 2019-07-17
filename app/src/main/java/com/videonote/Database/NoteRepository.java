package com.videonote.Database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class NoteRepository extends SQLiteOpenHelper{

    private static final String TABLE_NAME = "notes_table";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FILE_NAME = "file_name";
    private static final String COLUMN_START_TIME = "start_time";
    private static final String COLUMN_REGISTRATION_NAME = "registration_name";
    private static final String COLUMN_REGISTRATION_TYPE = "registration_type";

    public NoteRepository(Context context) {
        super(context, Common.DATABASE_NAME, null, Common.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + TABLE_NAME
                    + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_FILE_NAME + " TEXT,"
                    + COLUMN_START_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                    + COLUMN_REGISTRATION_NAME + " TEXT,"
                    + COLUMN_REGISTRATION_TYPE + " TEXT"
                    + ")"
        );

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + TABLE_NAME
                        + "("
                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_FILE_NAME + " TEXT,"
                        + COLUMN_START_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                        + COLUMN_REGISTRATION_NAME + " TEXT,"
                        + COLUMN_REGISTRATION_TYPE + " TEXT"
                        + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long insert(NoteDTO note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FILE_NAME, note.getFileName());
        cv.put(COLUMN_START_TIME, note.getStartTime());
        cv.put(COLUMN_REGISTRATION_NAME, note.getRecordId());
        cv.put(COLUMN_REGISTRATION_TYPE, note.getType());
        long id = db.insert(TABLE_NAME,null, cv);
        db.close();
        return id;
    }

    public int updateData(NoteDTO note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FILE_NAME, note.getFileName());
        cv.put(COLUMN_START_TIME, note.getStartTime());
        /*
        cv.put(COLUMN_REGISTRATION_NAME, note.getRegistrationName());
        cv.put(COLUMN_REGISTRATION_TYPE, note.getRegistrationType());
*/
        // updating row
        return db.update(TABLE_NAME, cv, "id" + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    public NoteDTO getData(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_FILE_NAME, COLUMN_START_TIME, COLUMN_REGISTRATION_NAME, COLUMN_REGISTRATION_TYPE},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
/*
        // prepare note object
        NoteDTO data = new NoteDTO(
                cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_FILE_NAME)),
                cursor.getString(cursor.getColumnIndex(COLUMN_START_TIME)),
                cursor.getString(cursor.getColumnIndex(COLUMN_REGISTRATION_NAME)),
                cursor.getString(cursor.getColumnIndex(COLUMN_REGISTRATION_TYPE)));

        // close the db connection
        cursor.close();

        return data;
        */
return null;
    }

    public List<NoteDTO> getAllDatas() {
        List<NoteDTO> Datas = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " +
                COLUMN_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                NoteDTO data = new NoteDTO();
                data.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                data.setFileName(cursor.getString(cursor.getColumnIndex(COLUMN_FILE_NAME)));
                data.setStartTime(cursor.getLong(cursor.getColumnIndex(COLUMN_START_TIME)));
                /*
                data.setRegistrationName(cursor.getString(cursor.getColumnIndex(COLUMN_REGISTRATION_NAME)));
                data.setRegistrationType(cursor.getString(cursor.getColumnIndex(COLUMN_REGISTRATION_TYPE)));
*/
                Datas.add(data);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        // return notes list
        return Datas;
    }

    public int getDataCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }

    public void deleteData(NoteDTO data) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[]{String.valueOf(data.getId())});
        db.close();
    }
}