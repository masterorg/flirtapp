package rs.edu.ict.flirtapp;

import java.util.ArrayList;

/**
 * Created by Ognjen on 28.6.2015..
 */
public class AllUserMatches {
    private ArrayList<UserMatch> MatchList;

    public AllUserMatches()
    {
        MatchList = new ArrayList<UserMatch>();
    }

    public AllUserMatches(ArrayList<UserMatch> MatchList)
    {
        this.MatchList = MatchList;
    }

    public void AddMach(UserMatch match)
    {
        MatchList.add(match);
    }

    public UserMatch SelectMatch(int position)
    {
        return MatchList.get(position);
    }

    public void RemoveMatch(UserMatch match)
    {
        MatchList.remove(match);
    }

    public ArrayList<UserMatch> GetListOfMatches()
    {
        return MatchList;
    }
}
