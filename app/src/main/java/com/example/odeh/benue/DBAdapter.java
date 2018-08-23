package com.example.odeh.benue;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.util.ArrayList;

public class DBAdapter {

    String TABLE = "wordsTable";
    private  static  final  String ID = "id";
    private  static  final  String WORD = "word";
    private  static  final  String CATEGORY = "category";
    private  static  final  String TIV_TRANSLATION = "tiv_translation";
    private  static  final  String IDOMA_TRANSLATION = "idoma_translation";
    private  static  final  String TIV_AUDIO_FILENAME = "tiv_audio_filename";
    private  static  final  String IDOMA_AUDIO_FILENAME = "idoma_audio_filename";
    private DBHelper dbHelper;
    private  String query;

    public DBAdapter ( Context context ) {
         dbHelper = new DBHelper ( context );

        try {
            dbHelper.createDataBase();

        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        try {
            dbHelper.openDataBase();

        }catch(SQLException sqle){
            throw sqle;
        }
    }

    public ArrayList<Word> getWords (Context context, String category, String language, String order) {
        ArrayList <Word> word = new ArrayList <>() ;
        Resources res = context.getResources();
        SQLiteDatabase sqliteDB = dbHelper.getReadableDatabase() ;
        if (language.equalsIgnoreCase("tiv")){
            query = "SELECT "+WORD+", "+TIV_TRANSLATION+", "+TIV_AUDIO_FILENAME+
                    " FROM "+TABLE+" WHERE "+CATEGORY+" = '"+category+"' order by "+
            order+" asc";
        }
        else{
            query = "SELECT "+WORD+", "+IDOMA_TRANSLATION+", "+IDOMA_AUDIO_FILENAME+
                    " FROM "+TABLE+" WHERE "+CATEGORY+" = '"+category+"' order by "+
            order+" asc;";
        }
        Cursor cursor = sqliteDB.rawQuery ( query , null ) ;
        cursor.moveToFirst () ;
        for ( int i = 0; i < cursor . getCount () ; i ++) {
            int soundId = res.getIdentifier(cursor.getString (2), "raw", context.getPackageName());
            word.add( new Word ( cursor . getString (0) ,
                    cursor . getString (1) , soundId ) ) ;
            cursor . moveToNext () ;
        }
        cursor . close () ;
        sqliteDB . close () ;
        return word ;
    }
}
