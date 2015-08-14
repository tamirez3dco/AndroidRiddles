package controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import adapters.GridAdapter;

public class DataBaseAdapter
{
    static final String DATABASE_word = GridAdapter.folderPath + "/Manager.db";//"Manager.db";
    static final int DATABASE_VERSION = 1;
    //writing the create table ddl command
    static final String CREATE_DICTIONARY_TABLE = "CREATE TABLE dictionary (" +
            "id	INTEGER PRIMARY KEY AUTOINCREMENT," +
            "word	TEXT NOT NULL," +
            "isEnable  TEXT NOT NULL);";

    public SQLiteDatabase db; // Variable to hold the database instance
    private final Context context; // Context of the application using the database
    private DataBaseHelper dbHelper; // Database open/upgrade helper
    public DataBaseAdapter(Context _context){
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_word, null, DATABASE_VERSION);
    }
    public DataBaseAdapter open() throws SQLException{
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public long addWord(String word,boolean isEnable) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("word",word);
        initialValues.put("isEnable", isEnable);
        long id  = db.insert("dictionary", null, initialValues);
        return id;
    }
    public Cursor getAllWords(){
        return db.rawQuery("SELECT * FROM dictionary ORDER BY word ASC;",null);
    }

    public Cursor getId(String word){
        return db.rawQuery("SELECT id FROM dictionary WHERE word='"+word+"';",null);
    }
    public void updateCheckBox(String id,boolean isEnable){
        db.execSQL("UPDATE dictionary SET isEnable='"+isEnable+"' WHERE id='"+id+"';");
    }
    public void updateWord(String id,String word){
        db.execSQL("UPDATE dictionary SET word='"+word+"' WHERE id='"+id+"';");
    }
    public void close()
    {
        db.close();
    }
    public SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }
}