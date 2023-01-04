package com.example.salas;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.salas.Model.Salones;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_modificar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_modificar extends Fragment {

    EditText ID, aula, categoria, descripcion, cantidad;
    ImageView foto;
    Spinner mod;
    Button actualizar, buscar, tomarfoto;
    String img = "";
    String rutaImagen;
    String id_salon;
    ProgressBar loadingbar;
    MaterialToolbar materialToolbar;
    private FirebaseFirestore firestore;
    private FirebaseStorage storage;
    private StorageReference storageRef;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_modificar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_modificar.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_modificar newInstance(String param1, String param2) {
        fragment_modificar fragment = new fragment_modificar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_modificar, container, false);
        Botones(root);

        if (getArguments() != null){
            id_salon = getArguments().getString("id_salon");
        }

        //consultaritem(id_animal);

        materialToolbar = root.findViewById(R.id.toolbarmodificarherra);
        materialToolbar.setNavigationIcon(R.drawable.ic_back);
        materialToolbar.setNavigationOnClickListener(view -> {
            fragment_categorias cat = new fragment_categorias();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content, cat, "");
            fragmentTransaction.setReorderingAllowed(true);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        ArrayList<String> tipos = new ArrayList<>();
        tipos.add("Facultad de Ingeniería");
        tipos.add("Facultad de Filosofía");
        tipos.add("Facultad de Lenguas");
        tipos.add("Facultad de administración");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,tipos);
        mod.setAdapter(adapter);

        actualizar.setOnClickListener(view -> {
            Map <String, Object> map = new HashMap<>();
            String code = (String) mod.getSelectedItem();
            map.put("edificio", code);
            String description = descripcion.getText().toString().trim();
            map.put("descripcion", description);
            double count = Double.parseDouble(aula.getText().toString());
            map.put("aula", count);

            Salones sal = new Salones(code,description,count,Login.UserSys.getName(),Login.UserSys.getID());
            updateHerramienta(sal,"Salones", map);
        });



        tomarfoto.setOnClickListener(View -> {
            abrirCamara();
        });
        return root;
    }

    private void updateHerramienta(Salones salon, String collection, Map map){
        iniciarFireBase();
        loadingbar.setVisibility(View.VISIBLE);
        firestore.collection(collection)
                .document(id_salon).update(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        limpiar();
                        //SubirImagen(id_salon);
                        loadingbar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Salón editado con éxito",
                                Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error en el registro",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void consultaritem(String id){
        firestore.collection("Salones")
                .document(id)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                       descripcion.setText(documentSnapshot.get("descripcion").toString());
                       aula.setText(documentSnapshot.get("aula").toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error al consultar",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void SubirImagen(String id){
        StorageReference imagensr = storageRef.child(id+".jpg");
        StorageReference ImagesRefsr = storageRef.child("images/"+id+".jpg");

        foto.setDrawingCacheEnabled(true);
        foto.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) foto.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imagensr.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });


    }


    public void Botones(View root) {
        aula = root.findViewById(R.id.Preciomodificar);
        descripcion = root.findViewById(R.id.Descripcionmodificar);
        foto = root.findViewById(R.id.ivFotoherramodificar);
        mod = root.findViewById(R.id.animals_spinnermodificar);
        tomarfoto = root.findViewById(R.id.btntomarfotoherramientamodificar);
        loadingbar = root.findViewById(R.id.loadingMH);
        actualizar = root.findViewById(R.id.btnmodificarherramienta);
    }



    public void limpiar(){
        aula.setText("");
        descripcion.setText("");
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
                Uri fotoUri = FileProvider.getUriForFile(getContext(), "com.example.salas.fileprovider", imagenArchivo);
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
            foto.setImageBitmap(imgBitMap);

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



    public void abrirfragments(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment, "");
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void iniciarFireBase(){
        FirebaseApp.initializeApp(getContext());
        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
    }

}

