package com.example.salas;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.MaterialToolbar;

import java.io.File;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Agregar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Agregar extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PERMISO_CAMARA = 5;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public String img;
    public String rutaImagen;
    EditText ID, nombre, categoria, descripcion;
    ImageView imagen;
    Button camara, guardar;
    MaterialToolbar toolbar;

    public Agregar() {
        // Required empty public constructor
    }

    public static Agregar newInstance(String param1, String param2) {
        Agregar fragment = new Agregar();
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
        View root = inflater.inflate(R.layout.fragment_agregar, container, false);
        cuadrostexto(root);
        botonesimg(root);


        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA);

        toolbar = root.findViewById(R.id.toolbaragregarprod);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(view -> {
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.barmostrarcreditos:
                        new AlertDialog.Builder(getContext()).setTitle("Acerca de").setMessage("" + "Carlos Alberto Hernández Velázquez \n"
                                + "Profesora: Rocío Elizabeth Pulido Alba\n" + "Programación Android :D \n" + "Aplicación Material Design + SQLite\n" + "Versión 2.1").setPositiveButton("Aceptar", null).show();
                        return true;

                    case R.id.bartopfind:
                        buscar Buscar = new buscar();
                        FragmentTransaction fragmentmodificar = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentmodificar.replace(R.id.content, Buscar, "");
                        fragmentmodificar.addToBackStack(null);
                        fragmentmodificar.setReorderingAllowed(true);
                        fragmentmodificar.commit();
                        return true;

                    default:
                        return false;
                }
            }
        });


        return root;
    }

    public void cuadrostexto(View root) {
        ID = root.findViewById(R.id.idprod);
        nombre = root.findViewById(R.id.nombreprod);
        categoria = root.findViewById(R.id.categoriaprod);
        descripcion = root.findViewById(R.id.descriptionprod);
    }

    public void botonesimg(View root) {
        imagen = root.findViewById(R.id.ivFotoprod);
        guardar = root.findViewById(R.id.btnguardar);
        camara = root.findViewById(R.id.btntomarfoto);

        guardar.setOnClickListener(this);
        camara.setOnClickListener(this);
    }

    private void abrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            File imagenArchivo = null;
            try {
                imagenArchivo = crearImagen();
            } catch (IOException ex) {
                Log.e("Error ", ex.toString());
            }

            if (imagenArchivo != null) {
                Uri fotoUri = FileProvider.getUriForFile(getContext(), "com.hardbug.productos.fileprovider", imagenArchivo);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
                startActivityForResult(intent, 1);
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //Bundle extras = data.getExtras();
            Bitmap imgBitMap = BitmapFactory.decodeFile(rutaImagen);
            imagen.setImageBitmap(imgBitMap);

        }
    }

    private File crearImagen() throws IOException {
        String nombreImagen = "producto_";
        File directorio = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(nombreImagen, ".jpg", directorio);
        rutaImagen = imagen.getAbsolutePath();
        img = rutaImagen;
        return imagen;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btntomarfoto:
                PedirPermisoCamara();
                abrirCamara();
                break;
            case R.id.btnguardar:
                if (nombre.getText().equals("") || ID.getText().equals("") || categoria.getText().equals("")
                        || descripcion.getText().equals("") || img.equals("")) {
                    Toast.makeText(getContext(), "Campos vacíos o imagen del producto no cargada", Toast.LENGTH_SHORT).show();
                } else {
                    int id = Integer.parseInt(ID.getText().toString());
                    String Nombre = nombre.getText().toString();
                    String Categoria = categoria.getText().toString();
                    String Descripcion = descripcion.getText().toString();
                    boolean estatus = true;
                    String imagen = img;

                }
                break;
        }
    }

    public void limpiar() {
        ID.setText("");
        nombre.setText("");
        categoria.setText("");
        descripcion.setText("");
        img = "";
        Bitmap imgBitMap = BitmapFactory.decodeFile(img);
        imagen.setImageBitmap(imgBitMap);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.barmostrarcreditos:
                new AlertDialog.Builder(getContext()).setTitle("Acerca de").setMessage("" + "Carlos Alberto Hernández Velázquez \n"
                        + "Profesora: Rocío Elizabeth Pulido Alba\n" + "Programación Android :D \n" + "Aplicación Material Design + SQLite\n" + "Versión 2.1").setPositiveButton("Aceptar", null).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void PedirPermisoCamara() {
        //Comprobación 'Racional'
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CAMERA)) {
            AlertDialog AD;
            AlertDialog.Builder ADBuilder = new AlertDialog.Builder(getContext());
            ADBuilder.setMessage("Para capturar imagen del producto es necesario utilizar la cámara de tu dispositivo. Permite que HardBug pueda acceder a la cámara.");
            ADBuilder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Solicitamos permisos
                    ActivityCompat.requestPermissions(
                            getActivity(),
                            new String[]{Manifest.permission.CAMERA},
                            PERMISO_CAMARA);
                }
            });
            AD = ADBuilder.create();
            AD.show();
        } else {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    PERMISO_CAMARA);
        }
    }
}