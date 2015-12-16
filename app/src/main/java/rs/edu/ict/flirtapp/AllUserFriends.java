package rs.edu.ict.flirtapp;

import java.util.ArrayList;

/**
 * Created by Ognjen on 28.6.2015..
 */
public class AllUserFriends {
    private ArrayList<User> UserList;

    public AllUserFriends()
    {
        UserList = new ArrayList<User>();
    }

    public AllUserFriends(ArrayList<User> UserList)
    {
        this.UserList = UserList;
    }

    public void AddUser(User friend)
    {
        UserList.add(friend);
    }

    public User SelectUser(int position)
    {
        return UserList.get(position);
    }

    public void RemoveUser(User friend)
    {
        UserList.remove(friend);
    }

    public ArrayList<User> GetListOfUser()
    {
        return UserList;
    }
}
