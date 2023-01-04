package com.example.salas;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.salas.Model.UserType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class NuevoUsuario extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;

    private FirebaseDatabase firebaseDB;
    private FirebaseFirestore firestore;

    private com.google.android.material.textfield.TextInputEditText usuario;
    private com.google.android.material.textfield.TextInputEditText name;
    private com.google.android.material.textfield.TextInputEditText contra;
    private com.google.android.material.textfield.TextInputEditText confirmarcontrasenia;
    private Button btnregistro;
    private Spinner tipoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_usuario);

        iniciarFireBase();

        usuario = findViewById(R.id.campousuarioNU);
        contra = findViewById(R.id.contraNU);
        confirmarcontrasenia = findViewById(R.id.confirmarcontraseniaNU);
        name = findViewById(R.id.usuarioNU);
        btnregistro = findViewById(R.id.btnregistroNU);
        tipoUsuario = findViewById(R.id.tipoUsuario_spinner);

        ArrayList<String> tiposUser = new ArrayList<>();
        tiposUser.add("Profesor");
        tiposUser.add("Alumno");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,tiposUser);
        tipoUsuario.setAdapter(adapter);

        btnregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validarEmail(usuario.getText().toString())){
                    if(contra.getText().toString().compareTo(confirmarcontrasenia.getText().toString())==0){
                        crearUsuario();
                    }else{
                        Toast.makeText(getBaseContext(),"Las contraseñas no son iguales", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getBaseContext(),"Dirección de correo inválida", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void crearUsuario(){
        mAuth.createUserWithEmailAndPassword(usuario.getText().toString(), contra.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            boolean tipo=false;

                            String tipS = (String) tipoUsuario.getSelectedItem();
                            if(tipS.compareTo("Alumno")!= 0)
                            {
                                tipo = true;
                            }

                            Toast.makeText(NuevoUsuario.this, "¡Gracias por unirte, " + name.getText() + "!" , Toast.LENGTH_SHORT).show();
                            UserType usuarioNuevo = new UserType(usuario.getText().toString(), tipo, user.getUid(), name.getText().toString());
                            nuevoUsuario(usuarioNuevo);
                            finish();
                        } else {
                            Toast.makeText(NuevoUsuario.this, "Error al registrarte",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void nuevoUsuario(UserType usuarioNuevo){
        firestore.collection("UsersType")
                .add(usuarioNuevo)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NuevoUsuario.this, "Error en el al guardar",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean validarEmail(String email){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private void iniciarFireBase(){
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        firebaseDB = FirebaseDatabase.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }
}