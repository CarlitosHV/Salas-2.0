package com.example.salas;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.salas.Model.UserType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.Executor;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_editar_usuario#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_editar_usuario extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MaterialToolbar toolbar;

    private FirebaseFirestore firestore;
    private FirebaseDatabase firebaseDB;
    private FirebaseAuth mAuth;

    Button btnguardaruser;
    ProgressBar loadingProgressBar;
    CheckBox admin;
    Boolean Admin;
    private com.google.android.material.textfield.TextInputEditText usuario, contrasenia, contrasenia2;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_editar_usuario() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_editar_usuario.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_editar_usuario newInstance(String param1, String param2) {
        fragment_editar_usuario fragment = new fragment_editar_usuario();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_editar_usuario, container, false);

        iniciarFireBase();

        toolbar = root.findViewById(R.id.toolbareditar_user);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(view -> {
            fragment_usuarios usuarios = new fragment_usuarios();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content, usuarios, "");
            fragmentTransaction.setReorderingAllowed(true);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        loadingProgressBar = root.findViewById(R.id.loadingUser);
        usuario =  root.findViewById(R.id.campousuarioeditar);
        contrasenia = root.findViewById(R.id.contraeditar);
        contrasenia2 = root.findViewById(R.id.confirmarcontraseniaeditar);
        admin = root.findViewById(R.id.checkadmin);
        btnguardaruser = root.findViewById(R.id.btnmodificaruser);

        Admin = admin.isChecked();

        btnguardaruser.setOnClickListener(View -> {
            if(validarEmail(usuario.getText().toString())){
                if(contrasenia.getText().toString().compareTo(contrasenia2.getText().toString())==0){
                    crearUsuario();
                }else{
                    Toast.makeText(getContext(),"Las contraseñas no son iguales", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getContext(),"Email no valido", Toast.LENGTH_SHORT).show();
            }
        });


        return root;
    }

    private void crearUsuario(){
        mAuth.createUserWithEmailAndPassword(usuario.getText().toString(), contrasenia.getText().toString())
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            UserType usuarioNuevo = new UserType(usuario.getText().toString(), Admin, user.getUid(), usuario.getText().toString());
                            nuevoUsuario(usuarioNuevo);
                            Toast.makeText(getContext(), "Registro Exitoso",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Error en el registro",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void nuevoUsuario( UserType usuarioNuevo ){
        loadingProgressBar.setVisibility(View.VISIBLE);
        firestore.collection("UsersType")
                .add(usuarioNuevo)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        usuario.setText("");
                        contrasenia.setText("");
                        contrasenia2.setText("");
                        loadingProgressBar.setVisibility(View.GONE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error en el registro",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean validarEmail(String email){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private void iniciarFireBase(){
        FirebaseApp.initializeApp(getContext());
        mAuth = FirebaseAuth.getInstance();
        firebaseDB = FirebaseDatabase.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }
}