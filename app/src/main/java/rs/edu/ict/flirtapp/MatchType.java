package rs.edu.ict.flirtapp;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Ognjen on 28.6.2015..
 */
public class MatchType {
    private int match_id;
    private String match_name;
    private String match_pucture;
    private String match_description;

    public MatchType(int match_id, String match_name, String match_pucture,String match_description) {
        this.match_id = match_id;
        this.match_name = match_name;
        this.match_pucture = match_pucture;
        this.match_description = match_description;
    }

    public int getMatch_id() {
        return match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public String getMatch_name() {
        return match_name;
    }

    public void setMatch_name(String match_name) {
        this.match_name = match_name;
    }

    public String getMatch_pucture() {
        return match_pucture;
    }

    public void setMatch_pucture(String match_pucture) {
        this.match_pucture = match_pucture;
    }

    public String getMatch_description() {
        return match_description;
    }

    public void setMatch_description(String match_description) {
        this.match_description = match_description;
    }

    public static MatchType getMechType(Context context,int match_id)
    {
        MatchTypeDataBase matchTypeDataBase = new MatchTypeDataBase(context);
        ArrayList<MatchType> list = matchTypeDataBase.SelectAllMatchTypes();

        for (MatchType item : list)
        {
            if (item.getMatch_id() == match_id)
            {
                return item;
            }
        }

        return null;
    }

    public static ArrayList<MatchType> getAllMatchTypes(Context context)
    {
        MatchTypeDataBase matchTypeDataBase = new MatchTypeDataBase(context);
        matchTypeDataBase.open();
        return matchTypeDataBase.SelectAllMatchTypes();
    }


}
