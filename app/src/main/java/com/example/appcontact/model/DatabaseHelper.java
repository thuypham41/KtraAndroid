package com.example.appcontact.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "contact_management.db";
    public static final int DATABASE_VERSION = 1;

    // Table and column names for units
    public static final String TABLE_UNITS = "units";
    public static final String COLUMN_UNIT_ID = "id";
    public static final String COLUMN_UNIT_NAME = "name";
    public static final String COLUMN_UNIT_EMAIL = "email";
    public static final String COLUMN_UNIT_WEBSITE = "website";
    public static final String COLUMN_UNIT_LOGO = "logo";
    public static final String COLUMN_UNIT_ADDRESS = "address";
    public static final String COLUMN_UNIT_PHONE = "phone";
    public static final String COLUMN_UNIT_PARENT_ID = "parent_id";

    // Table and column names for employees
    public static final String TABLE_EMPLOYEES = "employees";
    public static final String COLUMN_EMPLOYEE_ID = "id";
    public static final String COLUMN_EMPLOYEE_NAME = "name";
    public static final String COLUMN_EMPLOYEE_POSITION = "position";
    public static final String COLUMN_EMPLOYEE_EMAIL = "email";
    public static final String COLUMN_EMPLOYEE_PHONE = "phone";
    public static final String COLUMN_EMPLOYEE_AVATAR = "avatar";
    public static final String COLUMN_EMPLOYEE_UNIT_ID = "unit_id";

    // Create table statements
    public static final String TABLE_CREATE_UNITS =
            "CREATE TABLE " + TABLE_UNITS + " (" +
                    COLUMN_UNIT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_UNIT_NAME + " TEXT, " +
                    COLUMN_UNIT_EMAIL + " TEXT, " +
                    COLUMN_UNIT_WEBSITE + " TEXT, " +
                    COLUMN_UNIT_LOGO + " BLOB, " +
                    COLUMN_UNIT_ADDRESS + " TEXT, " +
                    COLUMN_UNIT_PHONE + " TEXT, " +
                    COLUMN_UNIT_PARENT_ID + " INTEGER" + ");";

    public static final String TABLE_CREATE_EMPLOYEES =
            "CREATE TABLE " + TABLE_EMPLOYEES + " (" +
                    COLUMN_EMPLOYEE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_EMPLOYEE_NAME + " TEXT, " +
                    COLUMN_EMPLOYEE_POSITION + " TEXT, " +
                    COLUMN_EMPLOYEE_EMAIL + " TEXT, " +
                    COLUMN_EMPLOYEE_PHONE + " TEXT, " +
                    COLUMN_EMPLOYEE_AVATAR + " BLOB) " ;
//                    +
//                    COLUMN_EMPLOYEE_UNIT_ID + " INTEGER, " +
//                    "FOREIGN KEY(" + COLUMN_EMPLOYEE_UNIT_ID + ") REFERENCES " +
//                    TABLE_UNITS + "(" + COLUMN_UNIT_ID + ")" + ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_UNITS);
        db.execSQL(TABLE_CREATE_EMPLOYEES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UNITS);
        onCreate(db);
    }
}