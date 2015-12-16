package rs.edu.ict.flirtapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;

import tabs.SlidingTabLayout;


public class SwipeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager pager;
    private SlidingTabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);


        GetAllUserMatches getAllUserMatches = new GetAllUserMatches(UserPreference.getUserData(this).getUser_id(),this);
        getAllUserMatches.execute();

        toolbar = (Toolbar)findViewById(R.id.app_bar);
        toolbar.setTitle("FlirtApp");
        setSupportActionBar(toolbar);

        pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        tabs = (SlidingTabLayout)findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabView(R.layout.custom_tab_view, R.id.tabText);
        //Postavljanje boje toolbar tray-a
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.accentColor);
            }
        });
        tabs.setViewPager(pager);

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

        //Biranje sa ToolBara akciju, ovo je za settings
        if (id == R.id.action_settings) {
            return true;
        }
        //Ovo je za refresh
        if(id == R.id.logout){
            UserPreference.DeleteUserData(getApplicationContext());
            finish();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    class PagerAdapter extends FragmentPagerAdapter {

        int[] icons = {R.drawable.home,R.drawable.friends,R.drawable.find_friends}; //Vuces ikonice iz drawable u niz
        String[] tabs = getResources().getStringArray(R.array.tabs);

        public PagerAdapter(FragmentManager fm) {

            super(fm);
            tabs = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new FragmentProfile();
                case 1:
                    return new FragmentFriends();
                case 2:
                    return new FragmentFindFriends();
                //case 3:
                    //return new FragmentNeupotrbljivZaSada();
                default:break;

            }
            return null;
        }

        //Postavljanje ikonica umesto texta na tittle tab
        @Override
        public CharSequence getPageTitle(int position){
            Drawable drawable = getResources().getDrawable(icons[position]);
            drawable.setBounds(0,0,72,72);
            ImageSpan imageSpan = new ImageSpan(drawable);
            SpannableString spannableString = new SpannableString(" ");
            spannableString.setSpan(imageSpan,0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }

        //3 taba, u slucaju povecavanja broj taba staviti broj ovde (max 6)
        @Override
        public int getCount() {
            return 3;
        }
    }
}
