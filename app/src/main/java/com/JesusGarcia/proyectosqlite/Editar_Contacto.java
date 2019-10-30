package com.JesusGarcia.proyectosqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Editar_Contacto extends AppCompatActivity {
    Button btnGuardar;
    EditText txtUsuario, txtFechaNac, txtEmail, txtTelefono;
    Intent i;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_contactos);

        btnGuardar = findViewById(R.id.btnGuardarC);
        txtUsuario = findViewById(R.id.txtUsuario);
        txtFechaNac = findViewById(R.id.txtFechaNac);
        txtEmail = findViewById(R.id.txtEmail);
        txtTelefono = findViewById(R.id.txtTelefono);

        i = getIntent();

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
            return;
        }

        final int idContacto = extras.getInt("_id");
        txtUsuario.setText(extras.getString("usuario"));
        txtEmail.setText(extras.getString("email"));
        txtTelefono.setText(extras.getString("tel"));
        txtFechaNac.setText(extras.getString("fechaNac"));

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DAOContacto dao = new DAOContacto(getApplicationContext());
                dao.update(new Contacto(0,txtUsuario.getText().toString(),txtEmail.getText().toString(),
                                txtTelefono.getText().toString(),txtFechaNac.getText().toString()),idContacto+"");

                Toast.makeText(Editar_Contacto.this, "Se ha actualizado el contacto",
                        Toast.LENGTH_SHORT).show();
                setResult(MainActivity.RESULT_OK, getIntent());
                finish();
            }
        });

    }
    public void btnCancel_click(View v){
        setResult(RESULT_CANCELED  , i );
        Toast.makeText(this, "Contacto no actualizado", Toast.LENGTH_SHORT).show();
        finish();
    }
}
