package com.example.firebasebanco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import io.opencensus.tags.Tag;

public class Registro extends AppCompatActivity {
    EditText ident, nombre,correo,contrasena;
    Button registrar, iniciarsesion,buscar;
    FirebaseFirestore db;
    String idauto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        ident = findViewById(R.id.etident);
        nombre = findViewById(R.id.etnombre);
        correo = findViewById(R.id.etcorreo);
        contrasena = findViewById(R.id.etcontrasena);
        registrar = findViewById(R.id.btnregistrarr);
        iniciarsesion = findViewById(R.id.btniniciarsesionr);
        buscar = findViewById(R.id.btnbuscarr);
        //Instanciar Firebase firestore
        db = FirebaseFirestore.getInstance();
        //Evento click de resgistrar
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Registrar Firestore
                Map<String, Object> cliente = new HashMap<>();
                cliente.put("ident", ident.getText().toString());
                cliente.put("nombre", nombre.getText().toString());
                cliente.put("correo", correo.getText().toString());
                cliente.put("contrasena", contrasena.getText().toString());

                // Add a new document with a generated ID
                db.collection("cliente")
                    .add(cliente)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            Toast.makeText(getApplicationContext(),"Cliente agregado correctamente",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Log.w(TAG, "Error adding document", e);
                            Toast.makeText(getApplicationContext(),"Error de coneccion",Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        });
        //Evento click de buscar
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idauto=""; //variable para verificar si esta vacio no existe
                db.collection("cliente")
                    .whereEqualTo("ident", ident.getText().toString())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    idauto = document.getId(); //capturar el id del documento
                                    //Log.d("prods", document.getId() + " => " + document.getData());
                                    ident.setText(document.getData().get("ident").toString());
                                    nombre.setText(document.getData().get("nombre").toString());
                                    correo.setText(document.getData().get("correo").toString());
                                    contrasena.setText(document.getData().get("contrasena").toString());
                                    //onCreateDescription().setText(document.getData().get("descrip").toString());
                                }
                                if (idauto.isEmpty()){
                                    Toast.makeText(getApplicationContext(),"Identificación no existente",Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Log.d("cliente", "Error getting documents: ", task.getException());
                            }
                        }
                    });
            }
        });
    }
}
