package hr.foi.air.interactiveppt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;
import hr.foi.air.interactiveppt.entities.ChatMessage;
import hr.foi.air.interactiveppt.webservice.SendDataAndProcessResponseTask;
import hr.foi.air.interactiveppt.webservice.ServiceGenerator;
import hr.foi.air.interactiveppt.webservice.WebService;

/**
 * Created by Smrad on 31.1.2017..
 */

public class Chat extends AppCompatActivity {

        private String idUser;
        private int pptId;
        RelativeLayout activity_chat;

        //Add Emojicon
        EmojiconEditText emojiconEditText;
        ImageView emojiButton,submitButton;
        EmojIconActions emojIconActions;


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if(item.getItemId() == R.id.menu_sign_out)
            {
                //send http rest data with your uid and action identifier to mute chat (server then puts that in message queue and following process in notifier.php saves state that message doesn't need to be sent to that user
            }
            return true;
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.main_menu,menu);
            return true;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chat);

            idUser = getIntent().getStringExtra("userId");
            pptId = getIntent().getIntExtra("pptId", -1);

            activity_chat = (RelativeLayout)findViewById(R.id.activity_chat);

            //Add Emoji
            emojiButton = (ImageView)findViewById(R.id.emoji_button);
            submitButton = (ImageView)findViewById(R.id.submit_button);
            emojiconEditText = (EmojiconEditText)findViewById(R.id.emojicon_edit_text);
            emojIconActions = new EmojIconActions(this,activity_chat,emojiButton,emojiconEditText);
            emojIconActions.ShowEmojicon();

            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new SendDataAndProcessResponseTask(ServiceGenerator.createService(WebService.class)
                            .sendChatMessage("send_chat_message", pptId, idUser, emojiconEditText.getText().toString().replace("\f", "").replace("\t", "").replace('\n', '\f') + '\n'),
                            new SendDataAndProcessResponseTask.PostActions() {
                                @Override
                                public void onSuccess(Object genericResponse) {
                                    boolean response = (boolean) genericResponse;
                                    if (response) {

                                    }
                                    else {
                                        Toast.makeText(Chat.this, "Poslužitelj nije uspio obraditi i proslijediti navedenu poruku!", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure() {
                                    Toast.makeText(Chat.this, "Neuspjeh kod slanja poruke poslužitelju! Provjerite vezu s Internetom", Toast.LENGTH_LONG).show();
                                }
                            }
                    );

                    emojiconEditText.setText("");
                    emojiconEditText.requestFocus();
                }
            });

            displayChatMessage();


        }

        private void displayChatMessage() {

            ListView listOfMessage = (ListView)findViewById(R.id.list_of_message);
        }

}
