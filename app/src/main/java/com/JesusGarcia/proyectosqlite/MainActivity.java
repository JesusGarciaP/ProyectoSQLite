package com.JesusGarcia.proyectosqlite;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    ListView lv;
    Button btnAdd, btnSearch;
    EditText txtFilter;
    SimpleCursorAdapter adp;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearch=findViewById(R.id.btnBuscar);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DAOContacto dao = new DAOContacto(getApplicationContext());

                txtFilter=findViewById(R.id.txtBuscar);
                String user = txtFilter.getText().toString();

                Cursor filtro = dao.filter(user, "usuario");

                adp = new SimpleCursorAdapter
                        (getApplicationContext(), android.R.layout.simple_list_item_2,
                                filtro,
                                new String[]{"usuario", "email"},
                                new int[]{android.R.id.text1,
                                        android.R.id.text2},
                                SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE);
                lv.setAdapter(adp);
            }
        });



        btnAdd=findViewById(R.id.btnAgregarC);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registro_Contactos.class);
                startActivityForResult(intent,1000);
            }
        });
        onActivityResult(1000,-1,getIntent());


        final DAOContacto dao = new DAOContacto(this);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {

                List<Contacto> importar = dao.getAll();
                final Contacto contactoSeleccionado = importar.get(position);

                AlertDialog.Builder menu = new AlertDialog.Builder(MainActivity.this);
                CharSequence[] opciones = {"Editar","Eliminar"};
                menu.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int opcion) {
                        switch (opcion){
                            case 0://Editar
                                Intent intent = new Intent(MainActivity.this, Editar_Contacto.class);
                                intent.putExtra("_id", contactoSeleccionado.getId());
                                intent.putExtra("usuario", contactoSeleccionado.getUsuario());
                                intent.putExtra("email", contactoSeleccionado.getEmail());
                                intent.putExtra("tel", contactoSeleccionado.getTel());
                                intent.putExtra("fechaNac", contactoSeleccionado.getFechaNac());
                                startActivityForResult(intent,1000);
                                break;
                            case 1://Eliminar
                                Snackbar.make(view,"¿Estás seguro",Snackbar.LENGTH_LONG).setAction("SI",
                                        new View.OnClickListener(){
                                            @Override
                                            public void onClick(View view) {
                                                String contact = contactoSeleccionado.id+"";
                                                dao.delete(contact);
                                                onActivityResult(1000,-1,getIntent());
                                                Toast.makeText(MainActivity.this, "elemento seleccionado: "+contact, Toast.LENGTH_SHORT).show();
                                            }
                                        }).show();
                                break;
                        }
                    }
                });
                menu.create().show();
                return true;
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000 && resultCode==RESULT_OK){
            DAOContacto dao = new DAOContacto(this);
            c = dao.getAllCursor();

            lv = findViewById(R.id.lv);

            adp = new SimpleCursorAdapter
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
