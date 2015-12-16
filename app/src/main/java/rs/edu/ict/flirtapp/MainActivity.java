package rs.edu.ict.flirtapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    protected boolean _active ;

    protected int loadingTime = 1000; //Vreme prikazivanja logo-a na pocetku (u milisekundama)

    //Recimo neka sesija
    private boolean sesija ;//










    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this._active=UserPreference.IsUserActive(getApplicationContext());
        this.sesija=UserPreference.isLoggedIn(getApplicationContext());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                //Ako postoji sesija ide se odma na SwipeActivity
                if(sesija){


                    finish();
                    Intent intent = new Intent(getApplicationContext(), SwipeActivity.class);
                    startActivity(intent);
                }
                //Ako nema sesije ide se na LoginActivity
                else{
                    finish();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }

            }
        }, loadingTime);

    }



}
