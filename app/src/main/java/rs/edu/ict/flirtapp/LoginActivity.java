package rs.edu.ict.flirtapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class LoginActivity extends AppCompatActivity {

    //btnSignIn
    private  Button btnSignIn;
    EditText username ;
    EditText password ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText)findViewById(R.id.Username);
         password = (EditText)findViewById(R.id.Password);

        //Dugme Cancel prazni polja za password i username
        Button cancel = (Button)findViewById(R.id.btnCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                username.setText("");
                password.setText("");
            }
        });

        btnSignIn =(Button)findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginUser();

            }
        });
        //Dugmence "Tap here" koji otvara SignUpActivity za pravljanje novog profila
        Button signup = (Button)findViewById(R.id.btnSignUp);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //method for asyncTask
    public void LoginUser()
    {
        LoginTask lt = new LoginTask(username.getText().toString(),password.getText().toString());
        lt.execute();
        try {
            JSONObject restulObject = lt.get();
            ParseLoginData(restulObject);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    //parss
    public void ParseLoginData(JSONObject jsonObject)
    {
        try {
            String success = jsonObject.getString("success");
            //
            //check the success
           if(success.equals("true"))
            {
                String userName = jsonObject.optString("UserName", "");
                String Email = jsonObject.optString("Email", "");
                String Gender = jsonObject.optString("Gender", "");
                User profile = new User();
                //set userdata
                profile.setUser_name(userName);
                profile.setUser_email(Email);
                profile.setUser_gender(Gender);
                profile.setUser_id(jsonObject.optInt("userID", 0));
                profile.setUser_birthday(jsonObject.optInt("DayOfBirth", 0));
                profile.setUser_profile_picture_path(jsonObject.optString("AvatarPath", ""));


                UserPreference.setUser(this, profile, jsonObject.getBoolean("active"));
                //test

                //go to swipe activity
                finish();
                Intent intent = new Intent(getApplicationContext(), SwipeActivity.class);
                startActivity(intent);

            }
            //if it is not good
            else
            {
                Toast.makeText(this,"Please try again",Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
