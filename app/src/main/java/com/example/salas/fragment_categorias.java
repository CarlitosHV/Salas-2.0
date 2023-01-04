package com.example.salas;

import static android.content.ContentValues.TAG;

import static com.example.salas.fragment_usuarios.UserSys;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.salas.Model.Salones;
import com.example.salas.design.CustomAdapterSalonesOcupados;
import com.example.salas.design.CustomAdapterVentas;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
 * Use the {@link fragment_categorias#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_categorias extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    FloatingActionButton add;

    MaterialToolbar toolbar;
    private static int Bandera = 0;
    private static String ItemSeleccionado;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_categorias() {
        // Required empty public constructor
    }

    private ListView custom_list_view_categorias;

    private FirebaseFirestore firestore;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    ArrayList<Salones> salones;
    private Spinner activos_salones;

    private void iniciarFireBase(){
        FirebaseApp.initializeApp(getContext());
        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_categorias.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_categorias newInstance(String param1, String param2) {
        fragment_categorias fragment = new fragment_categorias();
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

    @SuppressLint({"RestrictedApi", "ResourceAsColor"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_categorias, container, false);
        custom_list_view_categorias=root.findViewById(R.id.custom_list_view_categorias);
        AlertDialog.Builder b = new AlertDialog.Builder(getContext());
        iniciarFireBase();
        LLamarAnimales();

        activos_salones = root.findViewById(R.id.activos_ventas);
        ArrayList<String> tipos = new ArrayList<>();
        tipos.add("Activos");
        tipos.add("Ocupados");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,tipos);
        activos_salones.setAdapter(adapter);
        activos_salones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LLamarAnimales();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        custom_list_view_categorias.setOnItemLongClickListener((adapterView, view, i, l) -> {
            if(Bandera == 0){
                String av = (String) activos_salones.getSelectedItem();
                if(av.compareTo("Activos") == 0 && Login.UserSys.isTipo()) {
                    toolbar.inflateMenu(R.menu.menu_eliminar);
                    view.setBackgroundColor(R.color.secondaryColor);
                    ItemSeleccionado = salones.get(i).getId();
                    Bandera = 1;
                }
            }else{
                toolbar.getMenu().clear();
                view.setBackgroundColor(Color.TRANSPARENT);
                ItemSeleccionado = "";
                Bandera = 0;
            }
            return true;
        });

        /*custom_list_view_categorias.setOnItemClickListener((adapterView, view, i, l) -> {
            if(Bandera == 1){
                toolbar.getMenu().clear();
                view.setBackgroundColor(Color.TRANSPARENT);
                Bandera = 0;
            }
        });*/

        toolbar = root.findViewById(R.id.toolbarcateprods);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(view -> {
            Intent intent = new Intent(getContext(), Login.class);
            startActivity(intent);
        });

        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.barubi:
                    fragment_ubicacion ubi = new fragment_ubicacion();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, ubi, "");
                    fragmentTransaction.setReorderingAllowed(true);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    return true;
                case R.id.bar_editaritem:
                    fragment_modificar modifica = new fragment_modificar();
                    Bundle bundle = new Bundle();
                    bundle.putString("id_salon", ItemSeleccionado);
                    modifica.setArguments(bundle);
                    FragmentTransaction fragmentTransaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.content, modifica, "");
                    fragmentTransaction1.setReorderingAllowed(true);
                    fragmentTransaction1.addToBackStack(null);
                    fragmentTransaction1.commit();
                    return true;
                case R.id.bar_eliminaritem:
                    b.setMessage("¿Desea eliminar el Item?").setCancelable(false)
                            .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    eliminarItem(ItemSeleccionado);
                                }
                            }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert = b.create();
                    //Setting the title manually
                    alert.setTitle("Atención");
                    alert.show();
                    LLamarAnimales();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        });

        add = root.findViewById(R.id.fabcategorias);
        add.setOnClickListener(view -> {
            fragment_herramientas herramientas = new fragment_herramientas();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content, herramientas, "");
            fragmentTransaction.setReorderingAllowed(true);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        if (!Login.UserSys.isTipo()){
            add.setVisibility(View.GONE);
            activos_salones.setVisibility(View.GONE);
        }

        return root;
    }

    private void LLamarAnimales() {
        if (Login.UserSys.isTipo()){

            firestore.collection("Salones")
                    .whereEqualTo("idProfe", Login.UserSys.getID())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                salones = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Map<String, Object> obj = document.getData();
                                    boolean prestado = (Boolean) obj.get("prestado");
                                    Salones salon = new Salones();
                                    if (prestado){
                                        salon.setPrestado(prestado);
                                        salon.setDescripcion(obj.get("descripcion").toString());
                                        salon.setAula((double)obj.get("aula"));
                                        salon.setPropietario(obj.get("propietario").toString());
                                        salon.setAlumno(obj.get("alumno").toString());
                                        salon.setEdificio(obj.get("edificio").toString());
                                        salon.setId(document.getId());
                                        salon.setIdPrestamo(obj.get("idPrestamo").toString());
                                        salon.setIdProfe(obj.get("idProfesor").toString());
                                    }else{
                                        salon.setPrestado(prestado);
                                        salon.setDescripcion(obj.get("descripcion").toString());
                                        salon.setAula((double)obj.get("aula"));
                                        salon.setPropietario(obj.get("propietario").toString());
                                        salon.setEdificio(obj.get("edificio").toString());
                                        salon.setId(document.getId());
                                    }
                                    String av = (String) activos_salones.getSelectedItem();
                                    if(av.compareTo("Activos")== 0){
                                        if(!salon.isPrestado()){
                                            salones.add(salon);
                                        }
                                    }else{
                                        if(salon.isPrestado()){
                                            salones.add(salon);
                                        }
                                    }
                                }
                                CustomAdapterVentas adapter = new CustomAdapterVentas(getContext(), salones);
                                custom_list_view_categorias.setAdapter(adapter);
                            }
                        }
                    });
        }else{
            ArrayList<Salones> salones = new ArrayList<>();
            firestore.collection("Salones")
                    .whereEqualTo("idPrestamo", Login.UserSys.getID())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Map<String, Object> obj = document.getData();
                                    boolean prestado = (Boolean) obj.get("prestado");
                                    if (prestado){
                                        Salones salon = new Salones();
                                        salon.setPrestado(prestado);
                                        salon.setDescripcion(obj.get("descripcion").toString());
                                        salon.setAula((double)obj.get("aula"));
                                        salon.setPropietario(obj.get("propietario").toString());
                                        salon.setEdificio(obj.get("edificio").toString());
                                        salon.setId(document.getId());
                                        salones.add(salon);
                                    }
                                }
                                CustomAdapterSalonesOcupados adapter = new CustomAdapterSalonesOcupados(getContext(),salones);
                                custom_list_view_categorias.setAdapter(adapter);
                            }
                        }
                    });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bar_editaritem:
                Toast.makeText(getContext(), "Se ha presionado editar", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void eliminarItem(String id) {
        firestore.collection("Salones").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(), "Se ha eliminado con éxito", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error tratando de eliminar", e);
            }
        });
    }
}