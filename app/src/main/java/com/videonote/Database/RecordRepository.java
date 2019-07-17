package com.videonote.Database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.videonote.Database.Common;
import com.videonote.Database.RecordDTO;

import java.util.ArrayList;
import java.util.List;

public class RecordRepository extends SQLiteOpenHelper{

    private static final String TABLE_NAME = "records_table";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FILE_NAME = "file_name";
    private static final String COLUMN_TYPE = "type";

    public RecordRepository(Context context) {
        super(context, Common.DATABASE_NAME, null, Common.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + TABLE_NAME
                    + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_FILE_NAME + " TEXT,"
                    + COLUMN_TYPE + " TEXT"
                    + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long insert(RecordDTO record){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FILE_NAME, record.getFileName());
        cv.put(COLUMN_TYPE, record.getType());
        long id = db.insert(TABLE_NAME,null, cv);
        db.close();
        return id;
    }

    public int updateData(RecordDTO record) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FILE_NAME, record.getFileName());
        cv.put(COLUMN_TYPE, record.getType());

        // updating row
        return db.update(TABLE_NAME, cv, "id" + " = ?",
                new String[]{String.valueOf(record.getId())});
    }

    public RecordDTO getData(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_FILE_NAME, COLUMN_TYPE},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        RecordDTO data = new RecordDTO(
                cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_FILE_NAME)),
                cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)));

        // close the db connection
        cursor.close();

        return data;
    }

    public List<RecordDTO> getAllRecordsByType(Common.RECORD_TYPE type) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<RecordDTO> records = new ArrayList<>();

        Cursor cursor = db.query(COLUMN_FILE_NAME,
                new String[]{COLUMN_ID, COLUMN_FILE_NAME, COLUMN_TYPE},
                COLUMN_TYPE + "=?",
                new String[]{type.name()}, null, null, COLUMN_ID+" DESC ", null);

        if (cursor == null || cursor.moveToFirst() == false)
            return records;

        do {
            RecordDTO record = new RecordDTO();
            record.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            record.setFileName(cursor.getString(cursor.getColumnIndex(COLUMN_FILE_NAME)));
            record.setType(cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)));
            records.add(record);
        } while (cursor.moveToNext());

        return records;
        /*
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query.toString(), null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                RecordDTO data = new RecordDTO();
                data.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                data.setFileName(cursor.getString(cursor.getColumnIndex(COLUMN_FILE_NAME)));
                data.setType(cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)));

                Datas.add(data);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        // return notes list
        return Datas;
        */
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

    public void deleteData(RecordDTO record) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[]{String.valueOf(record.getId())});
        db.close();
    }
}