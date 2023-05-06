package com.example.smd_assignment2_v1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ConversationActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Conversation> conversationList;
    ConversationAdapter conversationAdapter;
    DataAccessLayer dal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        dal = new DataAccessLayer(this);

        //make three bot conversations on first launch
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        boolean firstLaunch = prefs.getBoolean("first_launch", true);
        if (firstLaunch) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("first_launch", false);
            editor.apply();

            Conversation bot1 = new Conversation(1,"bot1");
            Conversation bot2 = new Conversation(2,"bot2");
            Conversation bot3 = new Conversation(3,"bot3");
            dal.addConversation(bot1);
            dal.addConversation(bot2);
            dal.addConversation(bot3);
        }
        conversationList = dal.getAllConversations();

        recyclerView = findViewById(R.id.conversation_recycler_view);
        conversationAdapter = new ConversationAdapter(conversationList);
        recyclerView.setAdapter(conversationAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        conversationAdapter.setOnItemClickListener(new ConversationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(ConversationActivity.this, MainActivity.class);
                intent.putExtra("conversationId", conversationList.get(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        conversationList.clear();
        conversationList.addAll(dal.getAllConversations());
        conversationAdapter.notifyDataSetChanged();
    }
}
