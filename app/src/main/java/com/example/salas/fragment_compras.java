package com.example.salas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.salas.Model.Salones;
import com.example.salas.design.CustomAdapterSalonesOcupados;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_compras#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_compras extends Fragment {

    MaterialToolbar toolbar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Variables propias
    Spinner compras_spinner;
    ListView custom_list_view_compras;

    private FirebaseFirestore firestore;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private ArrayList<Salones> SalonesList;

    private void iniciarFireBase(){
        FirebaseApp.initializeApp(getContext());
        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
    }

    public fragment_compras() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_compras.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_compras newInstance(String param1, String param2) {
        fragment_compras fragment = new fragment_compras();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_compras, container, false);
        custom_list_view_compras = root.findViewById(R.id.custom_list_view_compras);


        iniciarFireBase();
        compras_spinner = root.findViewById(R.id.compras_spinner);
        ArrayList<String> tipos = new ArrayList<>();
        tipos.add("Facultad de Ingeniería");
        tipos.add("Facultad de Filosofía");
        tipos.add("Facultad de Lenguas");
        tipos.add("Facultad de administración");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,tipos);
        compras_spinner.setAdapter(adapter);

        compras_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),compras_spinner.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
                LLamarSalones(compras_spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        custom_list_view_compras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Salones salo = SalonesList.get(position);

                AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
                alerta.setMessage("¿Desea ocupar "+salo.getEdificio()+"?")
                                .setCancelable(false)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                salo.setPrestado(true);
                                salo.setAlumno(Login.UserSys.getName());
                                salo.setIdPrestamo(Login.UserSys.getID());
                                firestore.collection("Salones").document(salo.getId())
                                        .set(salo)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                            }
                                        });
                                Toast.makeText(getContext(),"Ya puedes ocupar el salón:  "+salo.getEdificio(),Toast.LENGTH_SHORT);
                                LLamarSalones(compras_spinner.getSelectedItem().toString());
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog titulo = alerta.create();
                titulo.setTitle("Ocupar");
                titulo.show();
            }
        });



        toolbar = root.findViewById(R.id.toolbarcompras);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(view -> {
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        });

        return root;
    }

    private void LLamarSalones(String Tipo) {
        SalonesList = new ArrayList<>();
        firestore.collection("Salones")
                .whereEqualTo("edificio", Tipo)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> obj = document.getData();
                                boolean prestado = (Boolean) obj.get("prestado");
                                if (!prestado){
                                    Salones salones = new Salones();
                                    salones.setPrestado(prestado);
                                    salones.setDescripcion(obj.get("descripcion").toString());
                                    salones.setAula((double)obj.get("aula"));
                                    salones.setPropietario(obj.get("propietario").toString());
                                    salones.setEdificio(obj.get("edificio").toString());
                                    salones.setId(document.getId());
                                    SalonesList.add(salones);
                                }
                            }
                            CustomAdapterSalonesOcupados adapter = new CustomAdapterSalonesOcupados(getContext(), SalonesList);
                            custom_list_view_compras.setAdapter(adapter);
                        }
                    }
                });
    }


}