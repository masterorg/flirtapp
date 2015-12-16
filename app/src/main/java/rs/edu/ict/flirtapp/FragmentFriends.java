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

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFriends extends android.support.v4.app.Fragment {

    ListView friendsList;
    ArrayList<User> users;





    public FragmentFriends() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);

        friendsList = (ListView)rootView.findViewById(R.id.friendsList);



        PopulateFriends();


        //Event kada se klikne na stavku liste da se udje u activity koji prikazuje detalje o tom korisniku
        friendsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = FragmentFriends.this.getActivity();
                Intent intent = new Intent(context, FriendsDetails.class); //Vazno je da ja znam Zivac ;)
                User userFriend= users.get(position);
                intent.putExtra("FriendId",userFriend.getUser_id());
                startActivity(intent);
            }
        });

        return rootView;
    }


    //method for populating listView with friends
    public  void PopulateFriends()
    {

        GetAllUserFriends getAllUserFriends = new GetAllUserFriends(UserPreference.getUserData(getActivity()).getUser_id(),getActivity());
        getAllUserFriends.execute();
        FriendsDataBase friendsDataBase = new FriendsDataBase(getActivity());
        AllUserFriends friends = friendsDataBase.SelectAllFriends();
        users = friends.GetListOfUser();
        FriendsAdapter friendsAdapter = new FriendsAdapter(getActivity(),users);
        friendsList.setAdapter(friendsAdapter);

    }
}
