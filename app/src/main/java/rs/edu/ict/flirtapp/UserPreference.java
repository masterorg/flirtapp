package rs.edu.ict.flirtapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Acer on 30.06.2015.
 */
public class UserPreference {

    public   static void setUser(Context context,User user,boolean active)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserName",user.getUser_name());
        editor.putString("Email",user.getUser_email());
        editor.putString("Gender",user.getUser_gender());
        editor.putInt("userID", user.getUser_id());
        editor.putString("AvatarPath",user.getUser_profile_picture_path());
        editor.putInt("DayOfBirth",user.getUser_birthday());
        editor.putBoolean("active",active);

        editor.commit();
    }

    public  static  boolean isLoggedIn(Context context)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int userId = sharedPreferences.getInt("userID",-1);
        return   (userId==(-1))?false:true;
    }
    //method for getting user data

    public  static  User getUserData(Context context)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        User profile = new User();
        profile.setUser_name(sharedPreferences.getString("UserName", ""));
        profile.setUser_email(sharedPreferences.getString("Email", ""));
        profile.setUser_id(sharedPreferences.getInt("userID", -1));
        profile.setUser_gender(sharedPreferences.getString("Gender", ""));
        profile.setUser_profile_picture_path(sharedPreferences.getString("AvatarPath",""));
        profile.setUser_birthday(sharedPreferences.getInt("DayOfBirth", -1));

        return  profile;
    }

    public  static  void DeleteUserData(Context context)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("UserName");
        editor.remove("Email");
        editor.remove("Gender");
        editor.remove("userID");
        editor.remove("AvatarPath");
        editor.remove("DayOfBirth");
        editor.remove("active");
    }

    public  static  boolean IsUserActive(Context context)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return  sharedPreferences.getBoolean("active",false);
    }

}
