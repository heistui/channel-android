package co.getchannel.channel.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.HashMap;

import co.getchannel.channel.FetchComplete;
import co.getchannel.channel.R;
import co.getchannel.channel.adapter.ChatsAdapter;
import co.getchannel.channel.models.CHClient;
import co.getchannel.channel.responses.CHThreadResponse;

public class ChatActivity extends AppCompatActivity implements FetchComplete {
    private RecyclerView recyclerView;
    public void execute(CHThreadResponse data){
        recyclerView.setAdapter(new ChatsAdapter(data.getResult().getData().getMessages(), R.layout.list_item_movie, getApplicationContext()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        HashMap<String, String> userData = (HashMap<String, String>)intent.getSerializableExtra("userData");
        String userID = (String)intent.getSerializableExtra("userID");
        CHClient.updateClientData(userID,userData);
        CHClient.activeThread(this);
    }
}
