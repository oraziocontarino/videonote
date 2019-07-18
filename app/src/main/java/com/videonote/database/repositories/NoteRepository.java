package com.videonote.database.repositories;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.videonote.database.dto.NoteDTO;
import com.videonote.database.TableColumn;

import java.util.ArrayList;
import java.util.List;

public class NoteRepository extends Repository<NoteDTO> {
    private static final TableColumn ID = new TableColumn("id", "INTEGER PRIMARY KEY AUTOINCREMENT");

    private static final TableColumn RECORD_ID = new TableColumn("recordId", "TEXT");

    private static final TableColumn FILE_NAME = new TableColumn("fileName", "TEXT");

    private static final TableColumn START_TIME_MS = new TableColumn("startTimeMs", "TEXT");

    public NoteRepository(SQLiteOpenHelper _this) {
        super(_this, "notes_table");
        //super(context, Common.DATABASE_NAME, null, Common.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
            generateCreateTableSql(ID, RECORD_ID, FILE_NAME, START_TIME_MS)
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(generateDropTableSql());
        onCreate(sqLiteDatabase);
    }

    public void insert(NoteDTO note){
        SQLiteDatabase db = _this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RECORD_ID.getName(), note.getRecordId());
        cv.put(FILE_NAME.getName(), note.getFileName());
        cv.put(START_TIME_MS.getName(), note.getStartTime());
        long id = db.insert(TABLE_NAME,null, cv);
        db.close();
        note.setId(id);
    }

    public List<NoteDTO> getByRecordId(String recordId) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = _this.getReadableDatabase();
        String selectQuery = "SELECT  * "   +
                             "FROM "        + TABLE_NAME        + " " +
                             "WHERE "       + RECORD_ID.getName()  + " = " + recordId +
                             "ORDER BY "    + RECORD_ID.getName()  + " DESC ";
        Log.d(LOG_PREFIX, selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);
        List<NoteDTO> results = new ArrayList<NoteDTO>();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                NoteDTO data = new NoteDTO();
                data.setId(cursor.getInt(cursor.getColumnIndex(ID.getName())));
                data.setFileName(cursor.getString(cursor.getColumnIndex(FILE_NAME.getName())));
                data.setStartTime(cursor.getLong(cursor.getColumnIndex(START_TIME_MS.getName())));
                results.add(data);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        // return notes list
        return results;
    }
/*
    public int getDataCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }
*/
    public void delete(NoteDTO data) {
        SQLiteDatabase db = _this.getWritableDatabase();
        db.delete(TABLE_NAME, ID.getName() + " = ?",
                new String[]{String.valueOf(data.getId())});
        db.close();
    }
}