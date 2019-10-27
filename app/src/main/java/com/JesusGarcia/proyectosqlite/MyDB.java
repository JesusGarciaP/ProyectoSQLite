package com.JesusGarcia.proyectosqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDB extends SQLiteOpenHelper {

    private final String SCRIPT_TABLE_CONTACTOS =
            "create table contactos (_id integer primary key autoincrement," +
                    "usuario text not null," +
                    "email text not null," +
                    "tel text not null," +
                    "fechaNac date not null);";
    public static final String[] COLUMNS_CONTACTOS =
            {"_id ", "usuario", "email", "tel","fechaNac"};
    public static final String TABLE_NAME_CONTACTOS =
            "contactos";

    public MyDB(@Nullable Context context) {

        super(context, "DBFile",
                null  , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL
                (SCRIPT_TABLE_CONTACTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
