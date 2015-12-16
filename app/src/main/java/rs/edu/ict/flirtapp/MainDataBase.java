package rs.edu.ict.flirtapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ognjen on 28.6.2015..
 */
public abstract class MainDataBase extends SQLiteOpenHelper {
    public static final String DBNAME = "FilrtApp.sqlite";
    public static final int VERSION = 1;
    private SQLiteDatabase database;

    public MainDataBase(Context context)
    {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db)
    {
        super.onOpen(db);
    }

    public boolean open() throws SQLiteException
    {
        try {
            this.database = this.getWritableDatabase();
            return this.database.isOpen();
        }
        catch (SQLiteException e)
        {
            throw e;
        }
    }

    public void close() throws SQLiteException
    {
        try
        {
            this.database.close();
        }
        catch (SQLiteException e)
        {
            throw e;
        }
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }
}
