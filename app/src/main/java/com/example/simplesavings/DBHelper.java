package com.example.simplesavings;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "savings.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE entries (id INTEGER PRIMARY KEY, amount REAL, note TEXT, type TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public void insertEntry(double amount, String note, String type) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("amount", amount);
        values.put("note", note);
        values.put("type", type);
        db.insert("entries", null, values);
    }

    public List<Entry> getIncomeEntries() {
        return getEntries("income");
    }

    public List<Entry> getExpenseEntries() {
        return getEntries("expense");
    }

    private List<Entry> getEntries(String type) {
        List<Entry> entries = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("entries", null, "type=?", new String[]{type}, null, null, "id DESC");
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            double amount = cursor.getDouble(cursor.getColumnIndexOrThrow("amount"));
            String note = cursor.getString(cursor.getColumnIndexOrThrow("note"));
            String entryType = cursor.getString(cursor.getColumnIndexOrThrow("type"));
            entries.add(new Entry(id, amount, note, entryType));
        }
        cursor.close();
        return entries;
    }

    public double getTotalIncome() {
        return getTotal("income");
    }

    public double getTotalExpense() {
        return getTotal("expense");
    }

    private double getTotal(String type) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(amount) FROM entries WHERE type=?", new String[]{type});
        double total = cursor.moveToFirst() ? cursor.getDouble(0) : 0.0;
        cursor.close();
        return total;
    }

    public void deleteEntry(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("entries", "id=?", new String[]{String.valueOf(id)});
    }
}