package com.videonote.database.repositories;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.videonote.Common;
import com.videonote.database.dto.RecordDTO;
import com.videonote.database.TableColumn;

import java.util.ArrayList;
import java.util.List;

public class RecordRepository extends Repository<RecordDTO>{
    private static final TableColumn ID = new TableColumn("id", "INTEGER PRIMARY KEY AUTOINCREMENT");

    private static final TableColumn FILE_NAME = new TableColumn("fileName", "TEXT");

    private static final TableColumn TYPE = new TableColumn("recordId", "TEXT");


    public RecordRepository(SQLiteOpenHelper _this) {
        super(_this, "records_table");
        //super(context, Common.DATABASE_NAME, null, Common.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                generateCreateTableSql(ID, FILE_NAME, TYPE)
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(generateDropTableSql());
        onCreate(sqLiteDatabase);
    }

    public void insert(RecordDTO record){
        SQLiteDatabase db = _this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FILE_NAME.getName(), record.getFileName());
        cv.put(TYPE.getName(), record.getType());
        long id = db.insert(TABLE_NAME,null, cv);
        record.setId(id);
        db.close();
    }

    public int update(RecordDTO record) {
        SQLiteDatabase db = _this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(FILE_NAME.getName(), record.getFileName());
        cv.put(TYPE.getName(), record.getType());

        // updating row
        return db.update(TABLE_NAME, cv, ID.getName() + " = ?",
                new String[]{String.valueOf(record.getId())});
    }

    public RecordDTO getById(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = _this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{ID.getName(), FILE_NAME.getName(), TYPE.getName()},
                ID.getName() + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        RecordDTO data = new RecordDTO(
                cursor.getInt(cursor.getColumnIndex(ID.getName())),
                cursor.getString(cursor.getColumnIndex(FILE_NAME.getName())),
                cursor.getString(cursor.getColumnIndex(TYPE.getName())));

        // close the db connection
        cursor.close();

        return data;
    }

    public List<RecordDTO> getByType(SQLiteOpenHelper _this, Common.RECORD_TYPE type) {
        SQLiteDatabase db = _this.getWritableDatabase();
        List<RecordDTO> records = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{ID.getName(), FILE_NAME.getName(), TYPE.getName()},
                TYPE.getName() + "=?",
                new String[]{type.name()}, null, null, ID.getName()+" DESC ", null);

        if (cursor == null || cursor.moveToFirst() == false)
            return records;

        do {
            RecordDTO record = new RecordDTO();
            record.setId(cursor.getInt(cursor.getColumnIndex(ID.getName())));
            record.setFileName(cursor.getString(cursor.getColumnIndex(FILE_NAME.getName())));
            record.setType(cursor.getString(cursor.getColumnIndex(TYPE.getName())));
            records.add(record);
        } while (cursor.moveToNext());

        return records;
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
    public void delete(RecordDTO record) {
        SQLiteDatabase db = _this.getWritableDatabase();
        db.delete(TABLE_NAME, ID.getName() + " = ?",
                new String[]{String.valueOf(record.getId())});
        db.close();
    }
}