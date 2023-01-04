package com.example.salas;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.salas.Model.ListaUsers;
import com.example.salas.Model.UserType;
import com.example.salas.design.CustomAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_usuarios#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_usuarios extends Fragment implements AdapterView.OnItemClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MaterialToolbar toolbar;
    FloatingActionButton add;

    private FirebaseAuth mAuth;

    private List<UserType> ListarProductos = new ArrayList<>();
    public static UserType UserSys;
    ArrayList<String> listaUsers = new ArrayList<String>();
    ArrayList<ListaUsers> listau = new ArrayList<ListaUsers>();
    ArrayAdapter<UserType> userArrayAdapter;
    ArrayList<UserType> usuarios = new ArrayList<>();

    private FirebaseDatabase firebaseDB;
    private FirebaseFirestore firestore;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_usuarios() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_usuarios.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_usuarios newInstance(String param1, String param2) {
        fragment_usuarios fragment = new fragment_usuarios();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private ArrayList<ListaUsers> seticonandname(){
        usuarios = new ArrayList<>();
        for (int i = 0; i < usuarios.size(); i++){
            //cargarImagen(imagenes.get(i));
            listau.add(new ListaUsers(R.drawable.icon, usuarios.get(i).getName()));
        }
        return listau;
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
        View root = inflater.inflate(R.layout.fragment_usuarios, container, false);

        iniciarFireBase();
        LLamarUsuario();
        listau = seticonandname();
        ListView listView = root.findViewById(R.id.custom_list_view_usuarios);
        CustomAdapter customAdapter = new CustomAdapter(getContext(), listau);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(this);

        toolbar = root.findViewById(R.id.toolbarusuarios);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(view -> {
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        });

        add = root.findViewById(R.id.fabuser);
        add.setOnClickListener(view -> {
            fragment_editar_usuario editar = new fragment_editar_usuario();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content, editar, "");
            fragmentTransaction.setReorderingAllowed(true);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        return root;
    }

    public void LLamarUsuario() {

        firestore.collection("UsersType")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> obj = document.getData();
                                UserType usuario = new UserType();
                                usuario.setEmail(obj.get("email").toString());
                                usuario.setID(obj.get("id").toString());
                                usuario.setTipo((Boolean) obj.get("tipo"));
                                usuario.setName(obj.get("name").toString());
                                usuarios.add(usuario);
                            }
                        }
                    }
                });
    }


    private void iniciarFireBase() {
        FirebaseApp.initializeApp(getContext());
        mAuth = FirebaseAuth.getInstance();
        firebaseDB = FirebaseDatabase.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}