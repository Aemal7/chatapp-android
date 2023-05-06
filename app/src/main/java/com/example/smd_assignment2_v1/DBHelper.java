package com.example.smd_assignment2_v1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "chatApp.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONVERSATION_TABLE = "CREATE TABLE Conversation (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL);";
        String CREATE_MESSAGE_TABLE = "CREATE TABLE Message (id INTEGER PRIMARY KEY AUTOINCREMENT, conversationId INTEGER NOT NULL, body TEXT NOT NULL, status TEXT NOT NULL, FOREIGN KEY(conversationId) REFERENCES Conversation(id));";
        db.execSQL(CREATE_CONVERSATION_TABLE);
        db.execSQL(CREATE_MESSAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Conversation");
        db.execSQL("DROP TABLE IF EXISTS Message");
        onCreate(db);
    }
}
