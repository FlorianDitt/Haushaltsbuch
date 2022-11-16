package com.example.haushaltsbuch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Haushaltsbuch.db";

    private static final int Database_VERSION = 2;

    public static final String TABLE1_NAME = "Transaktionen";
    public static final String TABLE2_NAME = "Leihgaben";
    public static final String TABLE3_NAME = "Kontostand";
    public static final String TABLE4_NAME = "Dauerauftraege";

    // Database creation sql statement
    private static final String Table1_CREATE = "CREATE TABLE " + TABLE1_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Datum DATE, Betrag REAL, Einnahmen INTEGER, Grund TEXT)";
    private static final String Table2_CREATE = "CREATE TABLE " + TABLE2_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Betrag REAL, Verliehen INTEGER, Partner TEXT, Refund Date)";
    private static final String Table3_CREATE = "CREATE TABLE " + TABLE3_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Datum DATE, Betrag REAL)";
    private static final String Table4_CREATE = "CREATE TABLE " + TABLE4_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Betrag REAL, Einnahmen INTEGER, Wiederholung TEXT, Datum Date)";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, Database_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Table1_CREATE);
        db.execSQL(Table2_CREATE);
        db.execSQL(Table3_CREATE);
        db.execSQL(Table4_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int ii) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE3_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE4_NAME);
        onCreate(db);
    }

    public boolean insertTransaktion(String Datum, double Betrag, int Einnahmen, String Grund){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Datum", Datum);
        contentValues.put("Betrag", Betrag);
        contentValues.put("Einnahmen", Einnahmen);
        contentValues.put("Grund", Grund);
        long result = db.insert(TABLE1_NAME, null, contentValues);
                if(result == -1){
                    return false;
                }else{
                    return true;
                }
    }
    public boolean insertBorrow(double Betrag, int Verliehen, String Partner, String Refund){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Betrag", Betrag);
        contentValues.put("Verliehen", Verliehen);
        contentValues.put("Partner", Partner);
        contentValues.put("Refund", Refund);
        long result = db.insert(TABLE2_NAME, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean insertBankBelance(String Datum, double Betrag){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Datum", Datum);
        contentValues.put("Betrag", Betrag);
        long result = db.insert(TABLE3_NAME, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean insertBankerOrder(double Betrag, int Einnahmen, String Wiederholung, String Datum){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Betrag", Betrag);
        contentValues.put("Einnahmen", Einnahmen);
        contentValues.put("Wiederholung", Wiederholung);
        contentValues.put("Datum", Datum);
        long result = db.insert(TABLE4_NAME, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    public Cursor getdata(String Table) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT * FROM " + Table, null);
        return res;
    }
    public Boolean deleteTransaktion(int ID, String Table){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM " + Table +" WHERE ID = ?", new String[] {String.valueOf(ID)});
        if (cursor.getCount() > 0) {
            long result = DB.delete(Table, "ID = ?", new String[] {String.valueOf(ID)});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }
    }
    /*public Boolean updateBankBalance(String ID, String Betrag, String Datum, String Einnahmen, String Grund){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Datum", Datum);
        contentValues.put("Kontostand", Betrag);
        contentValues.put("Einnahmen", Einnahmen);
        contentValues.put("Grund", Grund);
        Cursor cursor = DB.rawQuery("SELECT * FROM kontostand WHERE ID = ?", new String[] {String.valueOf(ID)});
        if (cursor.getCount() > 0) {
            long result = DB.update("transaktionen", contentValues, "ID = ?", new String[]{String.valueOf(ID)});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }
    }

   */

}
