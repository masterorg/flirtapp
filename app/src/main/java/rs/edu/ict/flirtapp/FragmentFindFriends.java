package rs.edu.ict.flirtapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFindFriends extends android.support.v4.app.Fragment {


    ListView findFriendsList;
    ArrayList<User> users;





    public FragmentFindFriends() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_find_friends, container, false);

        findFriendsList = (ListView)rootView.findViewById(R.id.findFriendsList);

        PopulateFriends();





        //Event kada se klikne na stavku liste da se udje u activity koji prikazuje detalje o tom korisniku
        findFriendsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = FragmentFindFriends.this.getActivity();
                Intent intent = new Intent(context, FindFriendsDetails.class);
                User nonFriendUser= users.get(position);
                intent.putExtra("usernName",nonFriendUser.getUser_name());
                intent.putExtra("userEmail",nonFriendUser.getUser_email());
                intent.putExtra("userAge",nonFriendUser.getUser_birthday());
                intent.putExtra("userGender",nonFriendUser.getUser_gender());
                startActivity(intent);
            }
        });

        return rootView;
    }


    public  void PopulateFriends()
    {
            GetAllNoneUsers getAllNoneUsers  = new GetAllNoneUsers(UserPreference.getUserData(getActivity()).getUser_id(),getActivity());
        try {
            JSONObject jsonObject = getAllNoneUsers.execute().get();
            users = ParseFriendData(jsonObject);
            //adapter
            FriendsAdapter friendsAdapter = new FriendsAdapter(getActivity(),users);
            findFriendsList.setAdapter(friendsAdapter);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    //json
    public  ArrayList<User> ParseFriendData(JSONObject jsonObject)
    {
        ArrayList<User> noneFriends = new ArrayList<User>();
        try {
            boolean status=jsonObject.getBoolean("success");
            if(status)
            {
                //if status is true
                JSONArray friends = jsonObject.getJSONArray("nonFriends");



                for (int i=0;i<friends.length();i++)
                {
                    JSONObject friend = friends.getJSONObject(i);
                    //taking the values

                    User profile = new User();
                    profile.setUser_name(friend.getString("UserName"));
                    profile.setUser_email(friend.getString("Email"));
                    profile.setUser_gender(friend.getString("Gender"));
                    profile.setUser_id(friend.optInt("ID", 0));
                    profile.setUser_birthday(friend.optInt("DayOfBirth", 0));
                    profile.setUser_profile_picture_path(friend.optString("AvatarPath", ""));
                    //insert user in all friends list
                    noneFriends.add(profile);
                }




            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        finally {
            return  noneFriends;
        }
    }
}
