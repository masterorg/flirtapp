package rs.edu.ict.flirtapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class SignUpActivity extends AppCompatActivity {
    private Button signUp;
    private Button cancel;

    private EditText username, password, confpass, email;
    private RadioGroup radioGroup;
    private RadioButton radioMale, radioFemale;
    private String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = (EditText)findViewById(R.id.editTextUsername);
        password = (EditText)findViewById(R.id.editTextPassword);
        confpass = (EditText)findViewById(R.id.editTextConfPassword);
        email = (EditText)findViewById(R.id.editTextEmail);

        radioFemale = (RadioButton)findViewById(R.id.radioSexFemale);
        radioMale = (RadioButton)findViewById(R.id.radioSexMale);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroupSex);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == radioMale.getId()){
                    sex = "M";
                }
                else if(checkedId == radioFemale.getId()){
                    sex = "Z";
                }
            }
        });

        //Pozivanje SignUp metode kad se stisne dugme SignUp
        signUp = (Button)findViewById(R.id.btnSignUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.length() > 3 && password.length() > 5 && confpass.equals(password) && email.length() > 5 && radioFemale.isChecked() || radioMale.isChecked()) {
                    SignUp();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Popuni sve!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Praznjenje svih polja kad se stisne dugme cancel
        cancel = (Button)findViewById(R.id.btnCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioMale.setChecked(false);
                radioFemale.setChecked(false);
                username.setText("");
                password.setText("");
                confpass.setText("");
                email.setText("");
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

    //SignUp metoda
    public void SignUp() {
        SignUpTask signUpTask = new SignUpTask(username.getText().toString(),password.getText().toString(),email.getText().toString(),sex);
        signUpTask.execute();

        try {
            JSONObject restulObject = signUpTask.get();
            ParseLoginData(restulObject);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void ParseLoginData(JSONObject jsonObject)
    {
        try {
            String success = jsonObject.getString("succes");
            //
            //check the success
            if(success.equals("true"))
            {
                String message = jsonObject.optString("message");
                Toast.makeText(this,message,Toast.LENGTH_LONG).show();
                finish();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

            }
            //if it is not good
            else
            {
                String message = jsonObject.optString("message");
                Toast.makeText(this,message,Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
