package com.example.salas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_configuracion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_configuracion extends Fragment implements LocationListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MaterialToolbar toolbar;
    public static double longi, lati;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    static int LOCATION_PERMISSION_REQUEST = 15;
    //Variables propias
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    FirebaseUser user;
    TextView coordenadas, ubi;

    Button btnconfiguracionAceptar, editar;


    com.google.android.material.textfield.TextInputEditText nuevo_correo;
    com.google.android.material.textfield.TextInputEditText nueva_contrasenia;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_configuracion() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_configuracion.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_configuracion newInstance(String param1, String param2) {
        fragment_configuracion fragment = new fragment_configuracion();
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
        View root = inflater.inflate(R.layout.fragment_configuracion, container, false);

        iniciarFireBase();

        /*toolbar = root.findViewById(R.id.toolbarconf);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.inflateMenu(R.menu.menu_vacio);
        toolbar.setNavigationOnClickListener(view -> {
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        });


        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.barubi:
                    if(longi == 0 && lati == 0){
                        ubi.setVisibility(View.VISIBLE);
                        ubi.setText("Obteniendo ubicación......");
                    }else{
                        fragment_ubicacion ubi = new fragment_ubicacion();
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content, ubi, "");
                        fragmentTransaction.setReorderingAllowed(true);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        });*/

        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (checkLocationPermission(getActivity())){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }

        coordenadas = root.findViewById(R.id.coordenadas);
        ubi = root.findViewById(R.id.headerubi);
        nuevo_correo = root.findViewById(R.id.nombre_cliente);/////
        nueva_contrasenia = root.findViewById(R.id.confirmarpassword_cliente);/////
        nuevo_correo.setText(Login.UserSys.getEmail());
        nueva_contrasenia.setText(Login.UserSys.getID());
        nuevo_correo.setEnabled(false);
        nueva_contrasenia.setEnabled(false);
        btnconfiguracionAceptar = root.findViewById(R.id.btnconfiguracionAceptar);
        editar = root.findViewById(R.id.btnconfiguracioneditar);


        btnconfiguracionAceptar.setVisibility(View.GONE);
        ubi.setVisibility(View.GONE);

        editar.setOnClickListener(View -> {
            nuevo_correo.setText("");
            nueva_contrasenia.setText("");
            nuevo_correo.setEnabled(true);
            nueva_contrasenia.setEnabled(true);
            btnconfiguracionAceptar.setVisibility(android.view.View.VISIBLE);
        });

        btnconfiguracionAceptar.setOnClickListener(View ->{
            updatePasword();
            updateEmail();
        });

        return root;
    }

    public void updateEmail() {
        String email = nuevo_correo.getText().toString().trim();
        UserProfileChangeRequest perfil = new UserProfileChangeRequest.Builder().build();
        if(email.length() > 0){
            user.updateEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful ()) {
                                Toast.makeText (getContext(), "Correo actualizado", Toast.LENGTH_SHORT).show ();
                                Login.UserSys.setEmail(email);
                                firestore.collection("UsersType").document(Login.UserSys.getID())
                                        .set(Login.UserSys)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                            }
                                        });
                            } else {
                                Exception e = task.getException();
                                Toast.makeText (getContext(), "Error al actualizar: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.w("updateEmail", "Unable to update email", e);
                            }
                        }
                    });
        }
    }

    public void updatePasword(){
        String contra = nueva_contrasenia.getText().toString().trim();
        if(contra.length() > 0){
            user.updatePassword(contra)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getContext(), "Contraseña actualizada",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }



    @Override
    public void onLocationChanged(Location location) {
        coordenadas.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        lati = location.getLatitude();
        longi = location.getLongitude();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }



    private void iniciarFireBase(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseApp.initializeApp(getContext());
        firestore = FirebaseFirestore.getInstance();
    }

    public static boolean checkLocationPermission(Activity activity){
        if(ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST);
            return false;
        }
        return true;
    }
}