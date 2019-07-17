package app.gobusiness.com.remindernotificationdivyanshu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Database_Helper extends SQLiteOpenHelper {



private static final String DB_NAME = "reminder_notification";
private static final String  TABLE_NAME = "reminder";
private static final String ID = "id";
private static final String TITLE = "title";
private static final String DESCRIPTION = "description";
private static final String TIME = "time";
private static final String DATE ="date";
private static final String REMINDER_ID = "reminder_id" ;


public Database_Helper(Context context)
    {
        super(context,DB_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {

        String CREATE_TABLE_REMINDER = " CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + TITLE + " TEXT,"
                + DESCRIPTION + " TEXT,"
                + TIME + " TEXT,"
                + DATE + " TEXT,"
                + REMINDER_ID + " TEXT"

                + ")";

        sqLiteDatabase.execSQL(CREATE_TABLE_REMINDER);
        Log.d("Create Table:", " "+ CREATE_TABLE_REMINDER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
}

    public boolean addReminder(ReminderModel reminderModel)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("title",reminderModel.getTitle());
        contentValues.put("description",reminderModel.getDescription());
        contentValues.put("time",reminderModel.getTime());
        contentValues.put("date",reminderModel.getDate());
        contentValues.put("reminder_id",reminderModel.getReminder_id());


        sqLiteDatabase.insert(TABLE_NAME, null,contentValues);
        sqLiteDatabase.close();
        retu