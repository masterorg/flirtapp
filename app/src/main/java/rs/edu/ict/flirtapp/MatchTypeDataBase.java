package rs.edu.ict.flirtapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Ognjen on 28.6.2015..
 */
public class MatchTypeDataBase extends MainDataBase {
    public static final String TABLE_NAME = "s_Matches";
    public static final String MATCH_ID = "match_id";
    public static final String MATCH_NAME = "match_name";
    public static final String MATCH_PICTURE = "match_picture";
    public static final String MATCH_DESCRIPTION = "match_description";

    public MatchTypeDataBase(Context context)
    {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +
                " ( " + MATCH_ID + " integer not null, " +
                MATCH_NAME + " varchar(150) not null, " +
                MATCH_DESCRIPTION + " text " +
                MATCH_PICTURE + " text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long InsertRow(MatchType matchType)
    {
        ContentValues vrednost = new ContentValues();

        vrednost.put(MATCH_ID, matchType.getMatch_id());
        vrednost.put(MATCH_NAME, matchType.getMatch_name());
        vrednost.put(MATCH_PICTURE, matchType.getMatch_pucture());

        return super.getDatabase().insert(TABLE_NAME, null, vrednost);
    }

    public long UpdateRow(MatchType matchType)
    {
        ContentValues vrednost = new ContentValues();

        vrednost.put(MATCH_NAME, matchType.getMatch_name());
        vrednost.put(MATCH_PICTURE, matchType.getMatch_pucture());
        String where =  MATCH_ID+" = "+matchType.getMatch_id();

        return super.getDatabase().update(TABLE_NAME, vrednost, where, null);
    }

    public long DeleteRow(MatchType matchType)
    {
        String where =  MATCH_ID+" = "+matchType.getMatch_id();
        return super.getDatabase().delete(TABLE_NAME, where, null);
    }

    public ArrayList<MatchType> SelectAllMatchTypes()
    {
        ArrayList<MatchType> returns = new ArrayList<MatchType>();
        Cursor result = super.getDatabase().rawQuery("select * from "+TABLE_NAME,null);

        if(result.moveToFirst()) {
            do {

                MatchType tmp_match_type = new MatchType(result.getInt(0),result.getString(1),result.getString(2),result.getString(3));

                returns.add(tmp_match_type);

            } while (result.moveToNext());


            return returns;
        }
        else
        {
            return null;
        }

    }
}
