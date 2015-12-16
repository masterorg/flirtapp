package rs.edu.ict.flirtapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Acer on 03.07.2015.
 * class for managing list of users into Adapter to listvView
 */
public class FriendsAdapter extends ArrayAdapter<User> {

    public FriendsAdapter(Context context, ArrayList<User> users)
    {
        super(context,0,users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = getItem(position);
        if(convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout,parent,false);
        }

        //find TextViews
        TextView friendUsername = (TextView) convertView.findViewById(R.id.friendUsername);
        ImageView user_pic_thumbnail  = (ImageView) convertView.findViewById(R.id.user_pic_thumbnail);

        //set friend username
        friendUsername.setText(user.getUser_name());
        user_pic_thumbnail.setImageResource(R.drawable.default_user_thumbnail);
        return convertView;
    }
}
