package rs.edu.ict.flirtapp;

import android.content.Context;

/**
 * Created by Ognjen on 28.6.2015..
 */
public class UserMatch {
    private String user_name;
    private String user_email;
    private String user_gender;
    private String match_name;
    private String match_description;

    public UserMatch(String user_name, String user_email, String user_gender, String match_name, String match_description) {
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_gender = user_gender;
        this.match_name = match_name;
        this.match_description = match_description;
    }

    public UserMatch() {
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

    public String getMatch_name() {
        return match_name;
    }

    public void setMatch_name(String match_name) {
        this.match_name = match_name;
    }

    public String getMatch_description() {
        return match_description;
    }

    public void setMatch_description(String match_description) {
        this.match_description = match_description;
    }

    @Override
    public String toString() {
        String prefix = "him";
        if(user_gender == "Z")
        {
            prefix = "her";
        }

        return "Match with user "+user_name+" with match type: "+match_name+"( "+match_description+" )"+"\n You can send mail to "+prefix+" on: "+user_email;
    }
}
