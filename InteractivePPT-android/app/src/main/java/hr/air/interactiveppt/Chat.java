package hr.air.interactiveppt;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;
import hr.air.interactiveppt.entities.ChatMessage;

/**
 * Created by Smrad on 31.1.2017..
 */

public class Chat extends AppCompatActivity {

        private DrawerLayout mDrawer;
        private Toolbar toolbar;
        private NavigationView nvDrawer;

        String surveyAuthorId;
        private ActionBarDrawerToggle drawerToggle;

        private static int SIGN_IN_REQUEST_CODE = 1;
        private FirebaseListAdapter<ChatMessage> adapter;
        RelativeLayout activity_chat;

        //Add Emojicon
        EmojiconEditText emojiconEditText;
        ImageView emojiButton,submitButton;
        EmojIconActions emojIconActions;


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if(item.getItemId() == R.id.menu_sign_out)
            {
                AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Snackbar.make(activity_chat,"You have been signed out.", Snackbar.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
            if (drawerToggle.onOptionsItemSelected(item)) {
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.main_menu,menu);
            return true;
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == SIGN_IN_REQUEST_CODE)
            {
                if(resultCode == RESULT_OK)
                {
                    Snackbar.make(activity_chat,"Successfully signed in.Welcome!", Snackbar.LENGTH_SHORT).show();
                    displayChatMessage();
                }
                else{
                    Snackbar.make(activity_chat,"We couldn't sign you in.Please try again later", Snackbar.LENGTH_SHORT).show();
                    finish();
                }
            }
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chat);

            activity_chat = (RelativeLayout)findViewById(R.id.activity_chat);

            Intent intent = getIntent();
            surveyAuthorId= intent.getStringExtra("id");

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

            nvDrawer = (NavigationView) findViewById(R.id.nvView);
            setupDrawerContent(nvDrawer);

            if (getSupportActionBar() != null) {
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ham_ic);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
            }

            drawerToggle = new ActionBarDrawerToggle(
                    this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close) {

                public void onDrawerClosed(View view) {
                    super.onDrawerClosed(view);
                    invalidateOptionsMenu();
                }

                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                    invalidateOptionsMenu();
                }

                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                    super.onDrawerSlide(drawerView, slideOffset);
                }
            };
            drawerToggle = setupDrawerToggle();

            mDrawer.addDrawerListener(drawerToggle);

            //Add Emoji
            emojiButton = (ImageView)findViewById(R.id.emoji_button);
            submitButton = (ImageView)findViewById(R.id.submit_button);
            emojiconEditText = (EmojiconEditText)findViewById(R.id.emojicon_edit_text);
            emojIconActions = new EmojIconActions(this,activity_chat,emojiButton,emojiconEditText);
            emojIconActions.ShowEmojicon();

            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseDatabase.getInstance().getReference().push().setValue(new ChatMessage(emojiconEditText.getText().toString(),
                            FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                    emojiconEditText.setText("");
                    emojiconEditText.requestFocus();
                }
            });

            //Check if not sign-in then navigate Signin page
            if(FirebaseAuth.getInstance().getCurrentUser() == null)
            {
                startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),SIGN_IN_REQUEST_CODE);
            }
            else
            {
                Snackbar.make(activity_chat,"Welcome "+FirebaseAuth.getInstance().getCurrentUser().getEmail(),Snackbar.LENGTH_SHORT).show();
                //Load content
                displayChatMessage();
            }


        }

        private void displayChatMessage() {

            ListView listOfMessage = (ListView)findViewById(R.id.list_of_message);
            adapter = new FirebaseListAdapter<ChatMessage>(this,ChatMessage.class,R.layout.list_item,FirebaseDatabase.getInstance().getReference())
            {
                @Override
                protected void populateView(View v, ChatMessage model, int position) {

                    //Get references to the views of list_item.xml
                    TextView messageText, messageUser, messageTime;
                    messageText = (EmojiconTextView) v.findViewById(R.id.message_text);
                    messageUser = (TextView) v.findViewById(R.id.message_user);
                    messageTime = (TextView) v.findViewById(R.id.message_time);

                    messageText.setText(model.getMessageText());
                    messageUser.setText(model.getMessageUser());
                    messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));

                }
            };
            listOfMessage.setAdapter(adapter);
        }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Intent intent;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                intent = new Intent(this, PresentationList.class);
                intent.putExtra("id",surveyAuthorId);
                finish();
                startActivity(intent);
                break;
            case R.id.nav_second_fragment:
                intent = new Intent(this, CreateSurvey.class);
                intent.putExtra("id",surveyAuthorId);
                finish();
                startActivity(intent);
                break;
            case R.id.nav_third_fragment:
                intent = new Intent(this, GetCode.class);
                intent.putExtra("id",surveyAuthorId);
                finish();
                startActivity(intent);
                break;
            case R.id.nav_logout:
                finish();
                System.exit(0);
                break;
        }

    }
    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

}
