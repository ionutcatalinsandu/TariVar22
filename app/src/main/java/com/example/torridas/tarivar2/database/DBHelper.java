package com.example.torridas.tarivar2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.torridas.tarivar2.city.model.City;
import com.example.torridas.tarivar2.country.model.Country;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Torridas on 06-Jul-17.
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CCManager";

    //Primul tabel
    private static final String TABLE_Countries = "Country";
    private static final String KEY_CID = "ID";
    private static final String KEY_CNAME = "Name";

    //Al doilea tabel
    private static final String TABLE_Cities = "Cities";
    private static final String KEY_OID = "ID";
    private static final String KEY_ONAME = "Name";
    private static final String KEY_COUNTRY_OF_PROVENIENCE_ID = "CID";

    private static final String TAG = "DBHelper";

    String creatingCountriesTableQuery(){
        return "CREATE TABLE " + TABLE_Countries + "(" + KEY_CID + " INTEGER PRIMARY KEY," +
                KEY_CNAME + " TEXT)";
    }

    String creatingCitiesTableQuery(){
        return "CREATE TABLE " + TABLE_Cities + "(" + KEY_OID + " INTEGER PRIMARY KEY," +
                KEY_ONAME + " TEXT," + KEY_COUNTRY_OF_PROVENIENCE_ID + " TEXT)";
    }

    private static DBHelper sInstance;
    public static synchronized DBHelper getInstante ( Context context ){
        if( sInstance == null ) {
            sInstance = new DBHelper(context.getApplicationContext());
        }
        return sInstance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(creatingCitiesTableQuery());
        db.execSQL(creatingCountriesTableQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Cities );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Countries);
        onCreate(db);
    }

    /// Countries OP


    public void addCountry( Country country ){
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();

        try{
            ContentValues val = new ContentValues();
            val.put(KEY_CNAME, country.getName());
            db.insert(TABLE_Countries, null, val);
            db.setTransactionSuccessful();
        }
        catch ( Exception e ) {
            Log.d(TAG, "Error adding country! ");
        }
        finally {
            db.endTransaction();
        }
    }

    public Country getCountryById( int id ){
        SQLiteDatabase db = this.getReadableDatabase();
        Country tara = null;
        Cursor sageata = db.query(TABLE_Countries, new String[]{KEY_CID, KEY_CNAME}, KEY_OID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        try{
            if( sageata != null){
                sageata.moveToFirst();
                tara = new Country( Integer.parseInt(sageata.getString(0)), sageata.getString(1) );
            }

        }
        catch ( Exception e ){
            Log.d(TAG, "Error while getting a country by id! ");
        }
        finally {
            if( sageata != null && !sageata.isClosed()){
                sageata.close();
            }
        }
        return tara;
    }

    public List<Country> getAllCountries(){
        List<Country> lista = new ArrayList<Country>();
        Country elem = null;

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_Countries;

        Cursor sageata = db.rawQuery(query, null);

        try {
            if( sageata.moveToFirst()){
                do{
                    elem = new Country(Integer.parseInt(sageata.getString(0)), sageata.getString(1));
                    lista.add(elem);
                }
                while ( sageata.moveToNext());
            }
        }
        catch  (Exception e ){
            Log.d(TAG, "Error while getting all countries! ");
        }
        finally {
            if( sageata != null && !sageata.isClosed() ){
                sageata.close();
            }
        }

        return lista;

    }

    public void updateCountry( Country country ){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            ContentValues val =  new ContentValues();
            val.put(KEY_CNAME, country.getName());
            db.update(TABLE_Countries, val, KEY_CID + "=?", new String[]{String.valueOf(country.getCountryID())});
            db.setTransactionSuccessful();
        }
        catch (Exception e ){
            Log.d(TAG, "Error while updating a country! ");
        }
        finally {
            db.endTransaction();
        }
    }

    public void deleteSingleCountry( Country country ){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try{
            db.delete(TABLE_Countries, KEY_CID + "=?", new String[]{String.valueOf(country.getCountryID())});
            db.setTransactionSuccessful();
        }
        catch (Exception e ){
            Log.d(TAG, "Error while deleting a country!");
        }
        finally{
            db.endTransaction();
        }
    }

    public int getCountriesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;
        String query = "SELECT * FROM " + TABLE_Countries;
        Cursor cursor = db.rawQuery(query, null);
        try {
            count = cursor.getCount();
        }catch (Exception e ){
            Log.d(TAG,"Error while couting countries!");
        }
        finally {
            if( cursor != null && !cursor.isClosed() ){
                cursor.close();
            }
        }
        return count;
    }

    public void deleteAllCountries() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_Countries;
        db.beginTransaction();
        try {
            db.execSQL(query);
            db.setTransactionSuccessful();
        }
        catch ( Exception e ){
            Log.d(TAG,"Error while deleting all countries! ");
        }
        finally {
            db.endTransaction();
            db.execSQL("vacuum");
        }
    }


    /// Cities OP
    public void addCity ( City city ) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues val = new ContentValues();
            val.put(KEY_ONAME, city.getName());
            val.put(KEY_COUNTRY_OF_PROVENIENCE_ID, city.getCountryOfProvienceID());
            db.insert(TABLE_Cities, null, val);
            db.setTransactionSuccessful();
        }
        catch (Exception e ){
            Log.d(TAG, "Error adding city!");
        }
        finally {
            db.endTransaction();
        }
    }

    public City getCityById( int id ) {
        City city = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor sageata = db.query(TABLE_Cities, new String[]{KEY_OID, KEY_ONAME, KEY_COUNTRY_OF_PROVENIENCE_ID},
                KEY_OID + "=?", new String[]{String.valueOf(id)}, null, null, null, null );

        try {
            if( sageata != null ){
                sageata.moveToFirst();
                city =  new City(sageata.getString(1),Integer.parseInt(sageata.getString(0)),Integer.parseInt(sageata.getString(2)));
            }
        }
        catch ( Exception e){
            Log.d(TAG, "Error while getting city by id!");
        }
        finally {
            if( sageata != null && !sageata.isClosed()){
                sageata.close();
            }
        }
        return city;
    }

    public List<City> getCittiesByCountry(int CountryID){
        List<City> lista = new ArrayList<City>();
        City elem = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_Cities + " WHERE "  + KEY_COUNTRY_OF_PROVENIENCE_ID
                + " = " + String.valueOf(CountryID);

        Cursor sageata = db.rawQuery(query, null);

        try{
            if( sageata.moveToFirst() ) {
                do {
                    elem = new City(sageata.getString(1),Integer.parseInt(sageata.getString(0)),Integer.parseInt(sageata.getString(2)));
                    lista.add(elem);

                } while(sageata.moveToNext());
            }
        } catch (Exception e ){
            Log.d(TAG, "Error while trying to get cities by " + CountryID + " ID!");
        }
        finally {
            if( sageata!= null && !sageata.isClosed() ){
                sageata.close();
            }
        }
        return lista;
    }

    public List<City> getAllCities(){
        List<City> lista = new ArrayList<City>();
        City elem = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM "+ TABLE_Cities;

        Cursor sageata = db.rawQuery(query, null );

        try {
            if(sageata.moveToFirst()){
                do{
                    elem = new City(sageata.getString(1),Integer.parseInt(sageata.getString(0)),
                            Integer.parseInt(sageata.getString(2)));
                    lista.add(elem);
                } while ( sageata.moveToNext());
            }
        }
        catch (Exception e){
            Log.d(TAG, "Error while getting all cities");
        }
        finally {
            if( sageata != null && !sageata.isClosed()){
                sageata.close();
            }
        }

        return lista;
    }

    public void updateSingleCity( City city ){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        try{

            ContentValues val = new ContentValues();
            val.put(KEY_ONAME, city.getName());
            val.put(KEY_COUNTRY_OF_PROVENIENCE_ID, city.getCountryOfProvienceID());
            db.update(TABLE_Cities, val, KEY_OID + "=?", new String[]{String.valueOf(city.getCityID())});
            db.setTransactionSuccessful();

        } catch ( Exception e){
            Log.d(TAG, "Error while updating city! ");
        }
        finally {
            db.endTransaction();
        }
    }

    public void deleteSingleCity( City city ){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            db.delete(TABLE_Cities, KEY_OID + "=?", new String[]{String.valueOf(city.getCityID())});
            db.setTransactionSuccessful();
        }
        catch ( Exception e ){
            Log.d(TAG, "Error while deleting city! ");
        }
        finally {
            db.endTransaction();
        }
    }

    public int getCitiesCount (){
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;
        String query = "SELECT * FROM " + TABLE_Cities;
        Cursor sageata = db.rawQuery(query, null );
        try {
            count = sageata.getCount();
        }
        catch (Exception e ) {
            Log.d(TAG, "Error while counting cities! ");
        }
        finally {
            if( sageata != null && !sageata.isClosed()){
                sageata.close();
            }
        }
        return count;
    }

    public void deleteAllCities(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_Cities;
        db.beginTransaction();

        try{
            db.execSQL(query);

            db.setTransactionSuccessful();
        }
        catch (Exception e){
            Log.d(TAG, "Error while deleting all cities!");
        }
        finally {
            db.endTransaction();
            db.execSQL("vacuum");
        }
    }
}
