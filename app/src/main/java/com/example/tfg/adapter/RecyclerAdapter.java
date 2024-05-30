package com.example.tfg.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg.R;
import com.example.tfg.controller.ApiListActivity;
import com.example.tfg.model.ListItem;

import java.util.List;

//Este adaptador es usado para los recyclerViews que no usan peticiones
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {
    List<ListItem> lista;

    //recibe por parametro un objeto de tipo lista que contendra objetos tipo ListItem
    public RecyclerAdapter(List lista){
        this.lista = lista;
    }

    //Se crea la estructura de la lista a partir del layout custom_item_list
    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_list, parent, false);
        RecyclerHolder recyclerHolder = new RecyclerHolder(view);
        return recyclerHolder;
    }

    //Se asigna la informacion correspondiente a cada vista de cada celda
    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        ListItem li = lista.get(position);
        holder.title.setText(li.getTitle());
        holder.img.setImageResource(li.getImgId());
        //Un OnClickListener comprobará que objeto de la lista fue tocado,
        //y lanzará su actividad correspondiente
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (holder.title.getText().toString()){
                    case "My Characters":
                        startListItemActivity(holder, ApiListActivity.class);
                        break;
                    case "Creatures":
                        startListItemActivity(holder, ApiListActivity.class, "/monsters/");
                        break;
                    case "Spells":
                        startListItemActivity(holder, ApiListActivity.class,"/spells/");
                        break;
                    case "Background":
                        startListItemActivity(holder, ApiListActivity.class,"/backgrounds/");
                        break;
                    case "Races":
                        startListItemActivity(holder, ApiListActivity.class, "/races/");
                        break;
                    case "Classes":
                        startListItemActivity(holder, ApiListActivity.class, "/classes/");
                        break;
                    case "Magic Items":
                        startListItemActivity(holder, ApiListActivity.class, "/magicitems/");
                        break;
                    case "Weapons":
                        startListItemActivity(holder, ApiListActivity.class, "/weapons/");
                        break;
                    case "Armor":
                        startListItemActivity(holder, ApiListActivity.class, "/armor/");
                        break;
                }
            }
        });
    }
    //devuelve el tamaño de la lista
    @Override
    public int getItemCount() {
        return lista.size();
    }

    //Metodo que lanza una actividad de un item del RecyclerView recibiendo el holder y la clase de la nueva actividad como parametro
    public void startListItemActivity(RecyclerHolder holder, Class activityClass){
        Intent intent = new Intent(holder.itemView.getContext(), activityClass);
        holder.itemView.getContext().startActivity(intent);
    }

    //Metodo que lanza una actividad de un item de RecyclerView que requerirá una conexion. Recibe
    //por parametro el RecyclerHolder y la clase de la actividad ademas de la url.
    public void startListItemActivity(RecyclerHolder holder, Class activityClass, String url){
        Intent intent = new Intent(holder.itemView.getContext(), activityClass);
        intent.putExtra("URL", url);
        holder.itemView.getContext().startActivity(intent);
    }

    //RecyclerHolder
    public class RecyclerHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView title;


        //Recibe la vista y asigna las vistas a sus variables
        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.imgItem);
            title = (TextView) itemView.findViewById(R.id.txItemTitle);


        }
    }
}
