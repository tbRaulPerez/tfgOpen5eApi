package com.example.tfg.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg.R;
import com.example.tfg.controller.CreatureActivity;
import com.example.tfg.controller.SpellActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//Este adaptador es usado para los recyclerViews que usan peticiones externas
public class RecyclerAdapterConnection extends RecyclerView.Adapter<RecyclerAdapterConnection.RecyclerHolder> {
    JSONArray list;
    String objectType;
    //Recibe un JSONArray con el que creará los objetos del recyclerView
    public RecyclerAdapterConnection(JSONArray list, String objectType){
        this.list = list;
        this.objectType = objectType;
    }
    //Metodo que recibe una lista filtrada por el searchbar y la asigna al recyclerView
    public void setFilteredList(JSONArray filteredList){
        list = filteredList;

        notifyDataSetChanged();
    }

    //Se crea la estructura de la lista a partir del layout custom_item_list
    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_api_item_list, parent, false);
        RecyclerHolder recyclerHolder = new RecyclerHolder(view);
        return recyclerHolder;
    }

    //Se asigna la informacion correspondiente a cada vista de cada celda

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        try {
            JSONObject jsonObject = (JSONObject) list.get(position);
            holder.title.setText(jsonObject.getString("name"));
            //Listener que comprueba la url del objeto tocado para lanzar
            //la actividad correspondiente
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(objectType.equals("/monsters/")){
                        startListItemActivity(holder, CreatureActivity.class, jsonObject);
                    } else if(objectType.equals("/spells/")){
                        startListItemActivity(holder, SpellActivity.class, jsonObject);
                    } else if(objectType.equals("/backgrounds/")){
//                        startListItemActivity(holder, MechanicsItemActivity.class, url);
                    } else if(objectType.equals("/races/")){
//                        startListItemActivity(holder, ClassActivity.class, url);
                    } else if(objectType.equals("/classes/")){
//                        startListItemActivity(holder, SubracesActivity.class, url);
                    } else if(objectType.equals("/magicitems/")){
//                        startListItemActivity(holder, SubracesActivity.class, url);
                    } else if(objectType.equals("/weapons/")){
//                        startListItemActivity(holder, SubracesActivity.class, url);
                    } else if(objectType.equals("/armor/")){
//                        startListItemActivity(holder, SubracesActivity.class, url);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.length();
    }

    //Lanza una actividad recibiendo por parametro el RecyclerHolder, la clase de la actividad, y la url
    public void startListItemActivity(RecyclerHolder holder, Class activityClass, JSONObject objetoJSON){
        Intent intent = new Intent(holder.itemView.getContext(), activityClass);
        System.out.println(objetoJSON.toString());
            if(objetoJSON != null){
                intent.putExtra("OBJETOJSON", objetoJSON.toString());
            }
            holder.itemView.getContext().startActivity(intent);

    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView title;

        //enlaza la variable con la vista correspondiente recibiendo la vista del item como parametro
        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            title= itemView.findViewById(R.id.txTItleConnection);
        }
    }
}
