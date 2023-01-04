package com.example.salas;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class fragment_principal extends AppCompatActivity {

    Context context = this;
    BottomNavigationView bottomNavigationView;
    Boolean prueba = true;

    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setItemHorizontalTranslationEnabled(true);

        Toast.makeText(this, "¡Bienvenido " + Login.UserSys.getName() + "!", Toast.LENGTH_SHORT).show();


        if (Login.UserSys.isTipo()){
            bottomNavigationView.inflateMenu(R.menu.menu);
            //Fragment que siempre va a cargar en pantalla principal
            fragment_categorias cate = new fragment_categorias();
            abrirfragments(cate);
        }else{
            bottomNavigationView.inflateMenu(R.menu.menu2);
            fragment_categorias catego = new fragment_categorias();
            abrirfragments(catego);
        }




        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                //Casos para los fragmentos del admin
                case R.id.barhome:
                    fragment_categorias cats = new fragment_categorias();
                    abrirfragments(cats);
                    return true;
                case R.id.barintereses:
                    fragment_compras compras = new fragment_compras();
                    abrirfragments(compras);
                    return true;
                case R.id.barcuenta:
                    fragment_configuracion config = new fragment_configuracion();
                    abrirfragments(config);
                    return true;
            }
            return false;
        });

    }

    public void abrirfragments(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment, "");
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.bartopfind:
                Toast.makeText(context, "Se ha presionado", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }
}
