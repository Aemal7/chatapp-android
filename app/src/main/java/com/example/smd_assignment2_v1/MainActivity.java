package com.example.smd_assignment2_v1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView welcomeTextView;
    EditText messageEditText;
    ImageButton sendButton;
    List<Message> messageList;
    MessageAdapter messageAdapter;
    int conversationId;
    DataAccessLayer dal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conversationId = getIntent().getIntExtra("conversationId", 0);
        dal = new DataAccessLayer(this);
        messageList = dal.getMessagesByConversationId(conversationId);

        recyclerView = findViewById(R.id.recycler_view);
        welcomeTextView = findViewById(R.id.welcome_text);
        messageEditText = findViewById(R.id.message_edit_text);
        sendButton = findViewById(R.id.send_btn);
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);

        sendButton.setOnClickListener((v)-> {
            //our message
            String question = messageEditText.getText().toString().trim();
            addToChat(question, Message.SENT);
            messageEditText.setText("");
            welcomeTextView.setVisibility(View.GONE);

            //bot message
            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
            String generatedString = new String(array, Charset.forName("UTF-8"));
            addToChat(generatedString, Message.RECEIVED);
        });
    }

    void addToChat(String message, String status) {
        Message newMessage = new Message(conversationId, message, status);
        dal.addMessage(newMessage);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(newMessage);
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }
}
