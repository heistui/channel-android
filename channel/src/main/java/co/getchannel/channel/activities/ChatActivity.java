package co.getchannel.channel.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.github.bassaer.chatmessageview.models.Message;
import com.github.bassaer.chatmessageview.models.User;
import com.github.bassaer.chatmessageview.utils.ChatBot;
import com.github.bassaer.chatmessageview.views.ChatView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import co.getchannel.channel.FetchComplete;
import co.getchannel.channel.R;
import co.getchannel.channel.adapter.ChatsAdapter;
import co.getchannel.channel.models.CHClient;
import co.getchannel.channel.responses.CHThreadResponse;

public class ChatActivity extends AppCompatActivity implements FetchComplete {
    private RecyclerView recyclerView;
    private ChatView mChatView;


    public void execute(CHThreadResponse data){
//        recyclerView.setAdapter(new ChatsAdapter(data.getResult().getData().getMessages(), R.layout.list_item_movie, getApplicationContext()));

        for (CHThreadResponse.CHThreadResult.CHThreadData.CHThreadMessage msg : data.getResult().getData().getMessages()) {
            //User id
            int myId = 0;
            //User icon
            Bitmap myIcon  = BitmapFactory.decodeResource(getResources(), R.drawable.face_2);

//            try {
//                String img = msg.getSender().getProfilePictureURL();
//                URL url = new URL(msg.getSender().getProfilePictureURL());
//                myIcon = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            } catch(Exception e) {
//
//            }
            //User name
            String myName = msg.getSender().getName();
            //new message
            final User me = new User(myId, myName, myIcon);

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            try {
                cal.setTime(sdf.parse(msg.getCreatedAt()));// all done
            }catch (Exception e){

            }


            if (msg.getFromBusiness()){
                Message message = new Message.Builder()
                        .setUser(me)
                        .setRightMessage(false)
                        .setMessageText(msg.getData().getText())
                        .setCreatedAt(cal)
                        .hideIcon(false)
                        .build();

                //Set to chat view
                mChatView.send(message);
            }else{
                Message message = new Message.Builder()
                        .setUser(me)
                        .setRightMessage(true)
                        .setMessageText(msg.getData().getText())
                        .setCreatedAt(cal)
                        .hideIcon(true)
                        .build();
                //Set to chat view
                mChatView.send(message);
            }

        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

//        recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        Intent intent = getIntent();
//        HashMap<String, String> userData = (HashMap<String, String>)intent.getSerializableExtra("userData");
//        String userID = (String)intent.getSerializableExtra("userID");
//        CHClient.updateClientData(userID,userData);
       CHClient.activeThread(this);

        //User id
        int myId = 0;
        //User icon
        Bitmap myIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_2);
        //User name
        String myName = "Michael";

        int yourId = 1;
        Bitmap yourIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_1);
        String yourName = "Emily";

        final User me = new User(myId, myName, myIcon);
        final User you = new User(yourId, yourName, yourIcon);

        mChatView = (ChatView)findViewById(R.id.chat_view);

        //Set UI parameters if you need
        mChatView.setRightBubbleColor(ContextCompat.getColor(this, R.color.green500));
        mChatView.setLeftBubbleColor(Color.WHITE);
        mChatView.setBackgroundColor(ContextCompat.getColor(this, R.color.teal100));
        mChatView.setSendButtonColor(ContextCompat.getColor(this, R.color.cyan900));
        mChatView.setSendIcon(R.drawable.ic_action_send);
        mChatView.setRightMessageTextColor(Color.WHITE);
        mChatView.setLeftMessageTextColor(Color.BLACK);
        mChatView.setUsernameTextColor(Color.WHITE);
        mChatView.setSendTimeTextColor(Color.WHITE);
        mChatView.setDateSeparatorColor(Color.WHITE);
        mChatView.setInputTextHint("new message...");
        mChatView.setMessageMarginTop(5);
        mChatView.setMessageMarginBottom(5);

        //Click Send Button
        mChatView.setOnClickSendButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new message
                Message message = new Message.Builder()
                        .setUser(me)
                        .setRightMessage(true)
                        .setMessageText(mChatView.getInputText())
                        .hideIcon(true)
                        .build();
                //Set to chat view
                mChatView.send(message);
                //Reset edit text
                mChatView.setInputText("");

                //Receive message
                final Message receivedMessage = new Message.Builder()
                        .setUser(you)
                        .setRightMessage(false)
                        .setMessageText(ChatBot.talk(me.getName(), message.getMessageText()))
                        .build();

                // This is a demo bot
                // Return within 3 seconds
                int sendDelay = (new Random().nextInt(4) + 1) * 1000;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mChatView.receive(receivedMessage);
                    }
                }, sendDelay);
            }

        });
    }
}
