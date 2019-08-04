package com.videonote.database.repositories;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.videonote.view.Common;
import com.videonote.database.dto.RecordDTO;
import com.videonote.database.TableColumn;

import java.util.ArrayList;
import java.util.List;

public class RecordRepository extends Repository<RecordDTO>{
    private static final TableColumn ID = new TableColumn("id", "INTEGER PRIMARY KEY AUTOINCREMENT");

    private static final TableColumn FILE_NAME = new TableColumn("fileName", "TEXT");

    private static final TableColumn TYPE = new TableColumn("recordId", "TEXT");

    private static final TableColumn LATITUDE = new TableColumn("latitude", "REAL");
    private static final TableColumn LONGITUDE = new TableColumn("longitude", "REAL");
    private static final TableColumn LOCALITY = new TableColumn("locality", "TEXT");


    public RecordRepository(SQLiteOpenHelper _this) {
        super(_this, "records_table");
        //super(context, Common.DATABASE_NAME, null, Common.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                generateCreateTableSql(ID, FILE_NAME, TYPE, LATITUDE, LONGITUDE, LOCALITY)
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
        cv.put(LATITUDE.getName(), record.getLatitude());
        cv.put(LONGITUDE.getName(), record.getLongitude());
        cv.put(LOCALITY.getName(), record.getLocality());
        long id = db.insert(TABLE_NAME,null, cv);
        record.setId(id);
        db.close();
    }

    public int update(RecordDTO record) {
        SQLiteDatabase db = _this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(FILE_NAME.getName(), record.getFileName());
        cv.put(TYPE.getName(), record.getType());
        cv.put(LATITUDE.getName(), record.getLatitude());
        cv.put(LONGITUDE.getName(), record.getLongitude());
        cv.put(LOCALITY.getName(), record.getLocality());

        // updating row
        return db.update(TABLE_NAME, cv, ID.getName() + " = ?",
                new String[]{String.valueOf(record.getId())});
    }
/*
    public RecordDTO getById(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = _this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{ID.getName(), FILE_NAME.getName(), TYPE.getName()},
                ID.getName() + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare record object
        RecordDTO data = DatabaseUtils.recordCursorToDTO(cursor);

        // close the db connection
        cursor.close();

        return data;
    }
    */

    public List<RecordDTO> getByType(Common.RECORD_TYPE type) {
        SQLiteDatabase db = _this.getWritableDatabase();
        List<RecordDTO> records = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{
                        ID.getName(),
                        FILE_NAME.getName(),
                        TYPE.getName(),
                        LATITUDE.getName(),
                        LONGITUDE.getName(),
                        LOCALITY.getName()
                },
                TYPE.getName() + "=?",
                new String[]{type.name()}, null, null, ID.getName()+" DESC ", null);

        if (cursor == null || cursor.moveToFirst() == false)
            return records;

        do {
            records.add(
                    new RecordDTO(
                            cursor.getInt(cursor.getColumnIndex(ID.getName())),
                            cursor.getString(cursor.getColumnIndex(FILE_NAME.getName())),
                            cursor.getString(cursor.getColumnIndex(TYPE.getName())),
                            cursor.getDouble(cursor.getColumnIndex(LATITUDE.getName())),
                            cursor.getDouble(cursor.getColumnIndex(LONGITUDE.getName())),
                            cursor.getString(cursor.getColumnIndex(LOCALITY.getName()))
                    )
            );
        } while (cursor.moveToNext());
        if(!cursor.isClosed()){
            cursor.close();
        }
        return records;
    }

    public List<RecordDTO> getAll() {
        SQLiteDatabase db = _this.getWritableDatabase();
        List<RecordDTO> records = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{
                        ID.getName(),
                        FILE_NAME.getName(),
                        TYPE.getName(),
                        LATITUDE.getName(),
                        LONGITUDE.getName(),
                        LOCALITY.getName()
                },
                null,
                null,
                null,
                null,
                ID.getName()+" DESC ",
                null);

        if (cursor == null || cursor.moveToFirst() == false)
            return records;

        do {
            records.add(
                    new RecordDTO(
                            cursor.getInt(cursor.getColumnIndex(ID.getName())),
                            cursor.getString(cursor.getColumnIndex(FILE_NAME.getName())),
                            cursor.getString(cursor.getColumnIndex(TYPE.getName())),
                            cursor.getDouble(cursor.getColumnIndex(LATITUDE.getName())),
                            cursor.getDouble(cursor.getColumnIndex(LONGITUDE.getName())),
                            cursor.getString(cursor.getColumnIndex(LOCALITY.getName()))
                    )
            );
        } while (cursor.moveToNext());
        if(!cursor.isClosed()){
            cursor.close();
        }
        return records;
    }

    public void delete(RecordDTO record) {
        SQLiteDatabase db = _this.getWritableDatabase();
        db.delete(TABLE_NAME, ID.getName() + " = ?",
                new String[]{String.valueOf(record.getId())});
        db.close();
    }
}