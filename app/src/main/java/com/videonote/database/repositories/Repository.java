package com.videonote.database.repositories;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.videonote.database.TableColumn;

public abstract class Repository <T>{
    protected String LOG_PREFIX = "REPOSITORY";
    protected String TABLE_NAME;
    protected SQLiteOpenHelper _this;
    public abstract void onCreate(SQLiteDatabase sqLiteDatabase);
    public abstract void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1);
    public abstract void insert(T data);
    public abstract void delete(T data);
    public Repository(SQLiteOpenHelper _this, String table){
        this.TABLE_NAME = table;
        this._this = _this;
    }

    protected String generateCreateTableSql(TableColumn... columns){
        StringBuilder queryBuilder = new StringBuilder();
        String query;
        queryBuilder.append("CREATE TABLE " + TABLE_NAME + "(");
        for(TableColumn column : columns){
            queryBuilder.append(column.getName() + " " + column.getAttrs()+", ");
        }
        query = queryBuilder.toString();
        query = query.substring(0, query.length() - 2) + ")";
        Log.d(LOG_PREFIX, query);
        return query;
    }

    protected String generateDropTableSql(){
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
