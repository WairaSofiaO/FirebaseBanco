package com.example.firebasebanco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText correo, contrasena;
    Button iniciar;
    TextView registrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        correo = findViewById(R.id.etcorreo);
        contrasena = findViewById(R.id.etcontrasena);
        iniciar = findViewById(R.id.btniniciarsesion);
        registrar = findViewById(R.id.tvregistrarse);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Registro.class));
            }
        });
    }
}
