package com.example.odeh.benue;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/com.example.odeh.benue/databases/";
    private SQLiteDatabase myDataBase;
    private final Context myContext;


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "word.db" ;
    private  static  final  String TABLE = "wordsTable";
    private  static  final  String ID = "id";
    private  static  final  String WORD = "word";
    private  static  final  String CATEGORY = "category";
    private  static  final  String TIV_TRANSLATION = "tiv_translation";
    private  static  final  String IDOMA_TRANSLATION = "idoma_translation";
    private  static  final  String TIV_AUDIO_FILENAME = "tiv_audio_file_name";
    private  static  final  String IDOMA_AUDIO_FILENAME = "idoma_audio_file_name";
    private String createStatement = " CREATE TABLE IF NOT EXISTS "+TABLE+" ("+
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + WORD + " TEXT, " + CATEGORY+
            " TEXT, "+ TIV_TRANSLATION+" TEXT, "+ IDOMA_TRANSLATION+" TEXT, "+
            TIV_AUDIO_FILENAME+" TEXT, "+ IDOMA_AUDIO_FILENAME+" TEXT);";

    public DBHelper ( Context context ) {
        super ( context , DATABASE_NAME , null , DATABASE_VERSION );
        this.myContext = context;
    }

    @Override
    public void onCreate ( SQLiteDatabase arg0 ) {
        //arg0.execSQL( this.createStatement ) ;
    }

    @Override
    public void onUpgrade ( SQLiteDatabase arg0 , int arg1 , int arg2 ) {
        //arg0.execSQL( " DROP TABLE IF EXISTS " + this.TABLE) ;
        //onCreate ( arg0 ) ;
    }

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if(dbExist){
            Log.e("Young man", "Database exists");

        }else{

            //By calling this method an empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.

            myContext.openOrCreateDatabase("word.db", Context.MODE_PRIVATE,null);
            Log.e("LOOK HERE", "I SHOULD BE WRITING NOW");

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database "+e);
            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){

            //database does't exist yet.
            Log.i("DBHelper","Database does not exist.");
        }

        if(checkDB != null){

            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open("words.sqlite");

        Log.e("LOOK HERE", "I PASSED PHASE 1");
        // Path to the just created empty db
        String outFileName = DB_PATH + DATABASE_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException{
        //Open the database
        String myPath = DB_PATH + DATABASE_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }
}