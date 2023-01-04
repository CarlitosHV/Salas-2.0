package com.example.salas.design;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.salas.Model.ListaUsers;
import com.example.salas.R;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<ListaUsers> listaProds;

    public CustomAdapter(Context context, ArrayList<ListaUsers> listaProds) {
        this.context = context;
        this.listaProds = listaProds;
    }

    @Override
    public int getCount() {
        return listaProds.size();
    }

    @Override
    public Object getItem(int position) {
        return listaProds.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderView holderView;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_layout_with_cardview,
                    parent, false);
            holderView = new HolderView(convertView);
            convertView.setTag(holderView);
        }else{
            holderView = (HolderView) convertView.getTag();
        }

        ListaUsers lista = listaProds.get(position);
        holderView.listaiconos.setImageResource(lista.getIconoprod());
        holderView.productname.setText(lista.getNomprod());
        return convertView;
    }

    private static class HolderView{
        private final ImageView listaiconos;
        private final TextView productname;

        public HolderView(View view){
            listaiconos = view.findViewById(R.id.icono_prod);
            productname = view.findViewById(R.id.nombre_producto);
        }
    }
}
