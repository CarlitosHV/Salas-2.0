package com.example.salas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link buscar#newInstance} factory method to
 * create an instance of this fragment.
 *
 *
 */
public class buscar extends Fragment {

    EditText ID, nombre, categoria, descripcion;
    ImageView foto;
    Button buscar;
    String img = "";
    String rutaImagen;

    MaterialToolbar materialToolbar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public buscar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment buscar.
     */
    // TODO: Rename and change types and number of parameters
    public static buscar newInstance(String param1, String param2) {
        buscar fragment = new buscar();
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
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_buscar, container, false);
        Botones(root);
        materialToolbar = root.findViewById(R.id.toolbarconsultar);
        materialToolbar.setNavigationIcon(R.drawable.ic_back);
        materialToolbar.setNavigationOnClickListener(view -> {
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        });

        buscar.setOnClickListener(View -> {
            if (ID.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Ingrese el ID del producto", Toast.LENGTH_SHORT).show();
            } else {

            }
        });
        return root;
    }

    public void Botones(View root) {
        ID = root.findViewById(R.id.idprodconsultar);
        nombre = root.findViewById(R.id.nombreprodconsultar);
        categoria = root.findViewById(R.id.categoriaconsultar);
        descripcion = root.findViewById(R.id.descriptionprodconsultar);
        foto = root.findViewById(R.id.ivFotoprodconsultar);
        buscar = root.findViewById(R.id.btnconsultarbuscar);
        nombre.setEnabled(false);
        categoria.setEnabled(false);
        descripcion.setEnabled(false);
    }
}