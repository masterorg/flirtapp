package rs.edu.ict.flirtapp;

import android.content.Context;

/**
 * Created by Ognjen on 28.6.2015..
 * Using class for user object and manipulation of users
 */
public class User {
    private int user_id;
    private String user_name;
    private String user_email;
    private String user_gender;
    private int user_birthday;
    private String user_profile_picture_path;

    public User()
    {}

    public User(int user_id,String user_name, String user_email, String user_gender, int user_birthday, String user_profile_picture_path) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_gender = user_gender;
        this.user_birthday = user_birthday;
        this.user_profile_picture_path = user_profile_picture_path;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public int getUser_birthday() {
        return user_birthday;
    }

    public void setUser_birthday(int user_birthday) {
        this.user_birthday = user_birthday;
    }

    public String getUser_profile_picture_path() {
        return user_profile_picture_path;
    }

    public void setUser_profile_picture_path(String user_profile_picture_path) {
        this.user_profile_picture_path = user_profile_picture_path;
    }

    public void UpdateUser(Context context)
    {
        FriendsDataBase fd = new FriendsDataBase(context);
        fd.UpdateRow(this);
    }

    public void DeleteUser(Context context)
    {
        FriendsDataBase fd = new FriendsDataBase(context);
        fd.DeleteRow(this);
    }

    public static User GetUser(Context context, int user_id)
    {
        FriendsDataBase friendsDataBase = new FriendsDataBase(context);

        AllUserFriends list = friendsDataBase.SelectAllFriends();

        for(User user : list.GetListOfUser())
        {
            if(user.getUser_id() == user_id)
                return user;
        }

        return null;
    }

    public static AllUserFriends GetAllFriendsForUser(Context context)
    {
        FriendsDataBase friendsDataBase = new FriendsDataBase(context);

        return friendsDataBase.SelectAllFriends();
    }

    public static long DeleteAllFriends(Context context)
    {
        FriendsDataBase friendsDataBase = new FriendsDataBase(context);

        return friendsDataBase.DeleteAllFriends();
    }

    public static AllUserFriends SyncAllFriends(Context context,AllUserFriends allUserFriends)
    {
        FriendsDataBase fd = new FriendsDataBase(context);

        return fd.SyncAllFriends(allUserFriends);
    }
}
