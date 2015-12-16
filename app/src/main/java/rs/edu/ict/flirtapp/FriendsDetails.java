package rs.edu.ict.flirtapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class FriendsDetails extends AppCompatActivity {

    private TextView username;
    private TextView age;
    private TextView numFriends;
    private TextView popularity;
    private Button flirt;
    private AlertDialog.Builder dialogBuilder;

    public void FlirtDialog() {
        //Varijable
        dialogBuilder = new AlertDialog.Builder(this);
        final String[] flirtAnswers = {"Big like","Like","Ok","Meh...","Here-there","Oh god no! :("};

        //Setovanje dijaloga kao sto je naslov, tekst i radio buttons
        dialogBuilder.setTitle("Flirt");
        dialogBuilder.setSingleChoiceItems(flirtAnswers, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Kada selektujes neki odgovor ovde ide logika
                Toast.makeText(getApplicationContext(),"Pokupi podatke u neku lokalnu promenjivu",Toast.LENGTH_SHORT).show();
            }
        });

        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Ovde kad izaberes odgovor treba da uradis logiku
                Toast.makeText(getApplicationContext(),"Posalji podatak u bazu ili gde vec",Toast.LENGTH_SHORT).show();
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Ovde je listener za cancel
                Toast.makeText(getApplicationContext(),"Nisi izabrao nista",Toast.LENGTH_SHORT).show();
            }
        });

        //Prijaz dijaloga
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_details);
        //inizialaze
        username = (TextView) findViewById(R.id.username);
        age = (TextView) findViewById(R.id.age);
        numFriends = (TextView) findViewById(R.id.numberFriends);
        popularity = (TextView) findViewById(R.id.popularity);
        SetFriendDetails();

        flirt = (Button)findViewById(R.id.flirtButton);


        flirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pozivas gore metodu FlirtDialog koja ima svu logiku dijaloga
                FlirtDialog();
            }
        });
    }

    //method for geting friend details
    public  void SetFriendDetails()
    {
        int FriendId = getIntent().getIntExtra("FriendId",0);
        User friend = User.GetUser(getApplicationContext(),FriendId);
        //populate the views
        username.setText(friend.getUser_name());
        age.setText(FriendAge(friend.getUser_birthday()));
        numFriends.setText(friend.getUser_email());
        String gender=friend.getUser_gender().equals("M")?"Male":"Female";
        popularity.setText(gender);

    }

    public  String FriendAge(int timestamp)
    {
        long age = System.currentTimeMillis()-(long)timestamp;
        String dateFormat = new SimpleDateFormat("yy").format(new Date(age));
        return  dateFormat;
    }
}
