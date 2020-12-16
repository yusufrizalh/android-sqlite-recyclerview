package id.yusufrizalh.sqliteandroid;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SqliteDatabase extends SQLiteOpenHelper {
    // menghimpun kebutuhan struktur database
    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "Contacts";
    private static final String TABLE_CONTACTS = "Contacts";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "contactName";
    private static final String COLUMN_NO = "contactNumber";

    public SqliteDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // membuat query untuk create table
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " +
                TABLE_CONTACTS + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY, " +
                COLUMN_NAME + " TEXT," +
                COLUMN_NO + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // membuat query untuk re-create table jika sudah ada
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    /*
     operasional dasar tabel pada database: Create Read Update Delete (CRUD)
    */

    // 1: Read / SELECT
    ArrayList<Contacts> listContacts() {
        String sql = "SELECT * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Contacts> storeContacts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                storeContacts.add(new Contacts(id, name, phone));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeContacts;
    }

    // 2: Create / INSERT
    void addContacts(Contacts contacts) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, contacts.getName());
        values.put(COLUMN_NO, contacts.getPhoneNumber());
        SQLiteDatabase db = this.getReadableDatabase(); // mendeteksi database yg digunakan
        db.insert(TABLE_CONTACTS, null, values);
    }

    // 3: Update / UPDATE
    void updateContacts(Contacts contacts) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, contacts.getName());
        values.put(COLUMN_NO, contacts.getPhoneNumber());
        SQLiteDatabase db = this.getReadableDatabase();
        db.update(TABLE_CONTACTS, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(contacts.getId())});
    }

    // 4: Delete / DELETE
    void deleteContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_CONTACTS, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
    }
}
