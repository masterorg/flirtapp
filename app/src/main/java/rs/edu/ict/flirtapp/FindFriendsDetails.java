package rs.edu.ict.flirtapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;


public class FindFriendsDetails extends AppCompatActivity {

    private TextView username;
    private TextView age;
    private TextView email;
    private TextView genderText;
    private Button addFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends_details);
        username = (TextView) findViewById(R.id.usersUsername);
        age = (TextView) findViewById(R.id.usersAge);
        email = (TextView) findViewById(R.id.userEmail);
        genderText = (TextView) findViewById(R.id.usersGender);
        SetFriendDetails();

        addFriend = (Button)findViewById(R.id.addFriend);


        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pozivas gore metodu FlirtDialog koja ima svu logiku dijaloga
            }
        });
    }

    public  void SetFriendDetails()
    {
        String usernameIntent = getIntent().getStringExtra("usernName");
        int ageIntent = getIntent().getIntExtra("userAge",0);
        String emailIntent = getIntent().getStringExtra("userEmail");
        String genderIntent = getIntent().getStringExtra("userGender");
        //populate the views
        username.setText(usernameIntent);
        age.setText(FriendAge(ageIntent));
        email.setText(emailIntent);
        String gender=genderIntent.equals("M")?"Male":"Female";
        genderText.setText(gender);

    }

    public  String FriendAge(int timestamp)
    {
        long age = System.currentTimeMillis()-(long)timestamp;
        String dateFormat = new SimpleDateFormat("yy").format(new Date(age));
        return  dateFormat;
    }
}
