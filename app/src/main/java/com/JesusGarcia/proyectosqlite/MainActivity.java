package com.JesusGarcia.proyectosqlite;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends AppCompatActivity {
    ListView lv;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd=findViewById(R.id.btnAgregarC);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registro_Contactos.class);
                startActivityForResult(intent,1000);
            }
        });
        onActivityResult(1000,-1,getIntent());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000 && resultCode==RESULT_OK){
            DAOContacto dao = new DAOContacto(this);
            Cursor c = dao.getAllCursor();

            lv = findViewById(R.id.lv);

            SimpleCursorAdapter adp = new SimpleCursorAdapter
                    (this, android.R.layout.simple_list_item_2,
                            c,
                            new String[]{"usuario", "email"},
                            new int[]{android.R.id.text1,
                                    android.R.id.text2},
                            SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE);

            lv.setAdapter(adp);
        }
    }
}
