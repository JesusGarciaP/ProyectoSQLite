package com.JesusGarcia.proyectosqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DAOContacto {
    SQLiteDatabase _sqlSqLiteDatabase;

    public DAOContacto(Context ctx) {
        MyDB miDB = new MyDB(ctx);
        _sqlSqLiteDatabase =
                miDB.getWritableDatabase();
    }

    public long insert(Contacto contacto) {
        ContentValues cv =
                new ContentValues();
        cv.put(MyDB.COLUMNS_CONTACTOS[1],
                contacto.getUsuario());
        cv.put(MyDB.COLUMNS_CONTACTOS[2],
                contacto.getEmail());
        cv.put(MyDB.COLUMNS_CONTACTOS[3],
                contacto.getTel());
        cv.put(MyDB.COLUMNS_CONTACTOS[4],
                contacto.getFechaNac());
        return _sqlSqLiteDatabase.insert(
                MyDB.TABLE_NAME_CONTACTOS,
                null,
                cv);

    }
    public long update(Contacto contacto, String id){
        ContentValues cv =
                new ContentValues();
        cv.put(MyDB.COLUMNS_CONTACTOS[1],
                contacto.getUsuario());
        cv.put(MyDB.COLUMNS_CONTACTOS[2],
                contacto.getEmail());
        cv.put(MyDB.COLUMNS_CONTACTOS[3],
                contacto.getTel());
        cv.put(MyDB.COLUMNS_CONTACTOS[4],
                contacto.getFechaNac());
        return _sqlSqLiteDatabase.update(MyDB.TABLE_NAME_CONTACTOS,cv,"_id=?",new String[]{id});
    }

    public int delete(String id){
        return _sqlSqLiteDatabase.delete(MyDB.TABLE_NAME_CONTACTOS,"_id=?",new String[]{id});
    }

    public List<Contacto> getAll(){
        List<Contacto> lst = null;

        Cursor c = _sqlSqLiteDatabase.
                query(MyDB.TABLE_NAME_CONTACTOS,
                        MyDB.COLUMNS_CONTACTOS,
                        null,
                        null,
                        null,
                        null,null);

        if ( c.moveToFirst()){
            lst = new ArrayList<Contacto>();
            do {
                Contacto ctc = new
                        Contacto(c.getInt(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4)

                );
                lst.add(ctc);
            }while(c.moveToNext());
        }
        return lst;
    }

    public Cursor getAllCursor(){

        Cursor c = _sqlSqLiteDatabase.
                query(MyDB.TABLE_NAME_CONTACTOS,
                        MyDB.COLUMNS_CONTACTOS,
                        null,
                        null,
                        null,
                        null,null);

        return c;
    }
}
