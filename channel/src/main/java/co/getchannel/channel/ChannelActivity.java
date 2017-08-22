package co.getchannel.channel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.HashMap;

import co.getchannel.channel.models.CHClient;

public class ChannelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        HashMap<String, String> userData = (HashMap<String, String>)intent.getSerializableExtra("userData");
        String userID = (String)intent.getSerializableExtra("userID");
        CHClient.updateClientData(userID,userData);

        CHClient.activeThread();
        setContentView(R.layout.activity_channel);
    }
}
