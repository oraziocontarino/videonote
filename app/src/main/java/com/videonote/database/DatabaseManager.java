package com.videonote.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.videonote.database.repositories.NoteRepository;
import com.videonote.database.repositories.RecordRepository;

public class DatabaseManager extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 13;
    public static final String DATABASE_NAME = "videonote_db";
    private RecordRepository recordRepository;
    private NoteRepository noteRepository;
    private static DatabaseManager instance;

    public static DatabaseManager getInstance(Context context){
        if(instance == null){
            instance = new DatabaseManager(context);
        }
        return instance;
    }

    private DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        recordRepository = new RecordRepository(this);
        noteRepository = new NoteRepository(this);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        recordRepository.onCreate(sqLiteDatabase);
        noteRepository.onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        recordRepository.onUpgrade(sqLiteDatabase, i, i1);
        noteRepository.onUpgrade(sqLiteDatabase, i, i1);
    }

    public RecordRepository getRecordRepository(){
        return recordRepository;
    }

    public NoteRepository getNoteRepository() {
        return noteRepository;
    }
}
