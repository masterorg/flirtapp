package rs.edu.ict.flirtapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Ognjen on 28.6.2015..
 */
public class FriendsDataBase extends MainDataBase {
    public static final String TABLE_NAME = "t_UserFriend";
    public static final String USER_ID = "user_ID";
    public static final String USER_NAME = "user_name";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_GENDER = "user_gender";
    public static final String USER_BIRTHDAY = "user_birthday";
    public static final String USER_PROFILE_PICTURE_PATH = "user_profile_picture_path";


    public FriendsDataBase(Context context)
    {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +
                " ( " + USER_ID + " integer not null, " +
                USER_NAME + " varchar(150) not null, " +
                USER_EMAIL + " varchar(150) not null, " +
                USER_GENDER + " char(1) not null, " +
                USER_BIRTHDAY + " integer not null," +
                USER_PROFILE_PICTURE_PATH + " text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private long InsertRow(User friend)
    {
        ContentValues vrednost = new ContentValues();

        vrednost.put(USER_ID, friend.getUser_id());
        vrednost.put(USER_NAME, friend.getUser_name());
        vrednost.put(USER_EMAIL, friend.getUser_email());
        vrednost.put(USER_GENDER, friend.getUser_gender());
        vrednost.put(USER_BIRTHDAY, friend.getUser_birthday());
        vrednost.put(USER_PROFILE_PICTURE_PATH, friend.getUser_profile_picture_path());

        return super.getDatabase().insert(TABLE_NAME, null, vrednost);
    }

    public long UpdateRow(User friend)
    {
        this.open();
        ContentValues vrednost = new ContentValues();

        vrednost.put(USER_NAME, friend.getUser_name());
        vrednost.put(USER_EMAIL, friend.getUser_email());
        vrednost.put(USER_GENDER, friend.getUser_gender());
        vrednost.put(USER_BIRTHDAY, friend.getUser_birthday());
        vrednost.put(USER_PROFILE_PICTURE_PATH, friend.getUser_profile_picture_path());
        String where =  USER_ID+" = "+friend.getUser_id();

        long numrows = super.getDatabase().update(TABLE_NAME, vrednost, where, null);

        this.close();
        return numrows;
    }

    public long DeleteAllFriends()
    {
        this.open();
        long numrows = super.getDatabase().delete(TABLE_NAME, null, null);
        this.close();

        return numrows;
    }

    public long DeleteRow(User friend)
    {
        this.open();
        String where =  USER_ID+" = "+friend.getUser_id();
        long numrows =  super.getDatabase().delete(TABLE_NAME, where, null);

        this.close();

        return numrows;
    }

    public AllUserFriends SelectAllFriends()
    {
        this.open();
        ArrayList<User> returns = new ArrayList<User>();
        Cursor result = super.getDatabase().rawQuery("select * from "+TABLE_NAME,null);

        while (result.moveToNext()) {

                User tmp_usr_match = new User(result.getInt(0),result.getString(1),result.getString(2),result.getString(3),result.getInt(4), result.getString(5));

                returns.add(tmp_usr_match);

            }

            AllUserFriends list = new AllUserFriends(returns);

        this.close();

        return list;

    }

    public AllUserFriends SyncAllFriends(AllUserFriends allUserFriends)
    {
        this.open();

        for(User user : allUserFriends.GetListOfUser())
        {
            if(!UserExist(user.getUser_id()))
            {
                InsertRow(user);
            }
        }


        ArrayList<User> returns = new ArrayList<User>();
        Cursor result = super.getDatabase().rawQuery("select * from "+TABLE_NAME,null);

        while (result.moveToNext()) {

            User tmp_usr_match = new User(result.getInt(0),result.getString(1),result.getString(2),result.getString(3),result.getInt(4), result.getString(5));

            returns.add(tmp_usr_match);

        }

        AllUserFriends list = new AllUserFriends(returns);

        this.close();

        return list;

    }

    private boolean UserExist(int UserID)
    {
        Cursor result = super.getDatabase().rawQuery("select count(1) from "+TABLE_NAME+" where "+USER_ID+" = "+UserID ,null);

        result.moveToFirst();

        if(result.getInt(0) == 0)
            return false;

        return true;
    }

}
