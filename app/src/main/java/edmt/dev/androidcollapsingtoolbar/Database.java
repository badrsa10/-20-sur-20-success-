package edmt.dev.androidcollapsingtoolbar;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hamza on 27/11/17.
 */

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Users";

    private static final int DATABASE_VERSION = 2;
    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table Etudients( _mail text primary key,name text not null,prenom text not null,date integer not null,password text not null,numero integer not null,niveau text not null,gender text not null );";
    private static final String DATABASE_CREATE2 = "create table Masters( _mail text primary key,name text not null,prenom text not null,date integer not null,password text not null,numero integer not null,niveau text not null,gender text not null );";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
     SQLiteDatabase db;
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        db.execSQL(DATABASE_CREATE2);

    }
    public void insert1(String nom,String prenom,String mail,String date,String password,String adress,String num,String gender){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Nom",nom);
        values.put("Prenom",prenom);
        values.put("mail",mail);
        values.put("date",date);
        values.put("password",password);
        values.put("adress",adress);
        values.put("numero",num);
        values.put("gender",gender);
        db.insert("Masters",null,values);


    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
