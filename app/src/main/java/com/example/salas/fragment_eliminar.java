package com.example.salas;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_eliminar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_eliminar extends Fragment {

    EditText ID, nombre, categoria, descripcion;
    ImageView foto;
    Button eliminar, buscar, inactivar;
    String img = "";
    String rutaImagen;
    TextView nom;
    MaterialToolbar materialToolbar;
    public static int band=0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_eliminar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_eliminar.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_eliminar newInstance(String param1, String param2) {
        fragment_eliminar fragment = new fragment_eliminar();
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
        View root = inflater.inflate(R.layout.fragment_eliminar, container, false);
        Botones(root);

        materialToolbar = root.findViewById(R.id.toolbareliminar);
        materialToolbar.setNavigationIcon(R.drawable.ic_back);
        materialToolbar.setNavigationOnClickListener(view -> {
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        });

        buscar.setOnClickListener(View -> {

        });

        eliminar.setOnClickListener(View -> {
            if (band == 1){
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialogo_producto, null);
                ((TextView) dialogView.findViewById(R.id.tvdpInfoProducto)).setText("¿Desea eliminar el producto?");
                ImageView ivImagen = dialogView.findViewById(R.id.ivDPFoto);
                cargarImagen(img, ivImagen);
                AlertDialog.Builder dialogo = new AlertDialog.Builder(getContext());
                dialogo.setTitle("Importante");
                dialogo.setCancelable(false);
                dialogo.setView(dialogView);
                dialogo.setPositiveButton("Confirmar", (dialogInterface, i) -> {

                });
                dialogo.setNegativeButton("Cancelar", (dialogInterface, i) -> Toast.makeText(getContext(), "Producto aún activo", Toast.LENGTH_SHORT).show());
                dialogo.show();
            }else{
                Toast.makeText(getContext(), "Por favor, busca un ID válido", Toast.LENGTH_SHORT).show();
            }
        });

        inactivar.setOnClickListener(View -> {
            if (band == 1){
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialogo_producto, null);
                ((TextView) dialogView.findViewById(R.id.tvdpInfoProducto)).setText("¿Desea inactivar el producto?");
                ImageView ivImagen = dialogView.findViewById(R.id.ivDPFoto);
                cargarImagen(img, ivImagen);
                AlertDialog.Builder dialogo = new AlertDialog.Builder(getContext());
                dialogo.setTitle("Importante");
                dialogo.setCancelable(false);
                dialogo.setView(dialogView);
                dialogo.setPositiveButton("Confirmar", (dialogInterface, i) -> {

                    band=0;
                });
                dialogo.setNegativeButton("Cancelar", (dialogInterface, i) -> Toast.makeText(getContext(), "Producto aún activo", Toast.LENGTH_SHORT).show());
                dialogo.show();
            }else{
                Toast.makeText(getContext(), "Por favor, busca un ID válido", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
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

    public void Botones(View root) {
        ID = root.findViewById(R.id.idprodeliminar);
        nombre = root.findViewById(R.id.nombreprodeliminar);
        categoria = root.findViewById(R.id.categoriaeliminar);
        descripcion = root.findViewById(R.id.descriptionprodeliminar);
        foto = root.findViewById(R.id.ivFotoprodeliminar);
        buscar = root.findViewById(R.id.btnconsultareliminar);
        eliminar = root.findViewById(R.id.btneliminar);
        nom = root.findViewById(R.id.tvdpInfoProducto);
        inactivar = root.findViewById(R.id.btninactivar);
        nom.setText("");
        nombre.setEnabled(false);
        categoria.setEnabled(false);
        descripcion.setEnabled(false);
    }

    public void limpiar(){
        ID.setText("");
        nombre.setText("");
        categoria.setText("");
        descripcion.setText("");
        img = "";
        Bitmap imgBitMap = BitmapFactory.decodeFile(img);
        foto.setImageBitmap(imgBitMap);
        nombre.setEnabled(false);
        categoria.setEnabled(false);
        descripcion.setEnabled(false);
    }
}