package com.example.salas;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.salas.Model.ListaUsers;
import com.example.salas.design.CustomAdapter;

import java.io.File;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link listarinactivos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class listarinactivos extends Fragment implements AdapterView.OnItemClickListener{


    ArrayList<String> registros;
    ArrayList<String> Nombres;
    ArrayList<String> imagenes;


    private ArrayList<ListaUsers> listaProds;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public listarinactivos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment listarinactivos.
     */
    // TODO: Rename and change types and number of parameters
    public static listarinactivos newInstance(String param1, String param2) {
        listarinactivos fragment = new listarinactivos();
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
        View root = inflater.inflate(R.layout.fragment_listarinactivos, container, false);

        listaProds = seticonandname();
        ListView listView = root.findViewById(R.id.custom_list_view2);
        CustomAdapter customAdapter = new CustomAdapter(getContext(), listaProds);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(this);
        return root;
    }

    private ArrayList<ListaUsers> seticonandname(){
        listaProds = new ArrayList<>();
        for (int i = 0; i < Nombres.size(); i++){
            //cargarImagen(imagenes.get(i));
            listaProds.add(new ListaUsers(R.drawable.ic_back, Nombres.get(i)));
        }
        return listaProds;
    }

    public void cargarImagen(String imagen, ImageView iv){
        try {
            File file = new File(imagen);
            Uri uriPhoto = FileProvider.getUriForFile(getContext(), "com.hardbug.productos.fileprovider", file);
            iv.setImageURI(uriPhoto);
        }catch(Exception ex){
            Toast.makeText(getContext(), "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
            Log.d("Carga de imagen", "Error al cargar imagen" + imagen + "\n Mensaje" + ex.getMessage() +
                    "\n Causa" + ex.getCause());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialogo_producto, null);
        ((TextView) dialogView.findViewById(R.id.tvdpInfoProducto)).setText(registros.get(position));

        ImageView ivImagen = dialogView.findViewById(R.id.ivDPFoto);
        cargarImagen(imagenes.get(position), ivImagen);
        AlertDialog.Builder dialogo = new AlertDialog.Builder(getContext());
        dialogo.setTitle("Producto");
        dialogo.setView(dialogView);
        dialogo.setPositiveButton("Aceptar", null);
        dialogo.show();
    }
}