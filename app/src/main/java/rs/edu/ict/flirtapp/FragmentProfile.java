package rs.edu.ict.flirtapp;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfile extends android.support.v4.app.Fragment {

    private TextView username,numberFriends;

    public FragmentProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


         View view= inflater.inflate(R.layout.fragment_profile, container, false);
        //inicialize fields
        this.username= (TextView) view.findViewById(R.id.username);
        this.numberFriends= (TextView) view.findViewById(R.id.numberFriends);
        SetUserProfile();


        return  view;


    }

    public  void SetUserProfile()
    {
        User profile = UserPreference.getUserData(getActivity());
        username.setText(profile.getUser_name());
        //select all friend and count them
        FriendsDataBase friendsDataBase = new FriendsDataBase(getActivity());
        AllUserFriends friends = friendsDataBase.SelectAllFriends();
        numberFriends.setText(""+friends.GetListOfUser().size());



    }

}
