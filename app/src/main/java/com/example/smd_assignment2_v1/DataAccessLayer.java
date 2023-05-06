package com.example.smd_assignment2_v1;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DataAccessLayer {
    private DBHelper dbHelper;

    public DataAccessLayer(Context context) {
        dbHelper = new DBHelper(context);
    }

    public List<Conversation> getAllConversations() {
        List<Conversation> conversationList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Conversation", null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                conversationList.add(new Conversation(id, name));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return conversationList;
    }

    public List<Message> getMessagesByConversationId(int conversationId) {
        List<Message> messageList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Message WHERE conversationId = ?", new String[]{String.valueOf(conversationId)});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String body = cursor.getString(cursor.getColumnIndex("body"));
                @SuppressLint("Range") String status = cursor.getString(cursor.getColumnIndex("status"));
                messageList.add(new Message(conversationId, body, status));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return messageList;
    }

    public long addMessage(Message message) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("conversationId", message.getConversationId());
        contentValues.put("body", message.getMessage());
        contentValues.put("status", message.getStatus());

        long messageId = db.insert("Message", null, contentValues);
        db.close();
        return messageId;
    }

    public void addConversation(Conversation conversation) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", conversation.getName());

        long newRowId = db.insert("Conversation", null, contentValues);
        conversation.setId((int) newRowId);
        db.close();
    }
}
