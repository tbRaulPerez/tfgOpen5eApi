package com.example.tfg.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.tfg.R;
import com.example.tfg.adapter.RecyclerAdapterConnection;
import com.example.tfg.connection.ApiConnect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerAdapterConnection recyclerAdapter;
    JSONObject objetoJson;
    String chosenRaceString;
    private String chosenBackgroundString;
    JSONArray arrayJson;
    Toolbar toolbar;
    String url;
    int contadorPaginacion;
    boolean isSearching;
    boolean isCharacterCreation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_list);
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        contadorPaginacion = 1;
        isSearching = false;

        recyclerView = findViewById(R.id.rvFirebaseItemList);

        Intent i = getIntent();



        //Se recoge el extra que contiene la url de la petición y se crea
        // un objeto Connections (hereda de AsyncTask) ejecutandolo, pues es una operacion con un tiempo de respuesta largo.
        url = i.getStringExtra("URL");

        //Se dejará de mostrar el nombre de la app en el actionBar además de mostrar un boton de atrás.
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            String title = i.getStringExtra("TITLE");
            if(title != null) {
                actionBar.setTitle(title);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                if(url.equals("/races/")){
                    alertDialogBuilder.setMessage("Choose a race for your character").
                            setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).create().show();
                }else if(url.equals("/backgrounds/")){
                    alertDialogBuilder.setMessage("Now hoose a background for your character").
                            setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).create().show();
                }else if(url.equals("/classes/")){
                    alertDialogBuilder.setMessage("Finally choose a class for your character").
                            setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).create().show();
                }
            } else actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        isCharacterCreation = i.getBooleanExtra("ISCHARACTERCREATION", false);
        if(isCharacterCreation){
            chosenRaceString = getIntent().getStringExtra("CHOSENRACE");
            chosenBackgroundString = getIntent().getStringExtra("CHOSENBACKGROUND");
        }

        new Connections().execute(url);


        //Listener que comprueba cuando el scroll alcanza el fondo de la lista, en cuyo caso añade al final la siguiente
        //pagina de resultados de la api
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(!recyclerView.canScrollVertically(1)){
                    if(!isSearching){
                        contadorPaginacion++;
                        new ConnectionsAppend().execute(url+"?page="+contadorPaginacion);
                    }
                }
            }
        });
    }

    //Se crea un menu que usara el menu_with_searchbar xml
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_with_searchbar, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        //se aplica un queryTextListener a la barra de busqueda
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //Se sobrescribe el metodo que gestiona cuando se busca y se
            //llama al metodo search
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(!s.equals("")){
                    isSearching = true;
                    search(s);
                }
                return false;
            }

            //Se sobrescribe el metodo que gestiona cuando el texto en el searchbar cambia y se
            //llama al metodo search
            @Override
            public boolean onQueryTextChange(String s) {
                if(s.equals("")){
                    search(s);
                    isSearching = false;
                    contadorPaginacion = 1;
                }
                return false;
            }
        });

        return true;
    }

    //metodo que gestiona lo que ocurre al pulsar el boton de atrás
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //metodo que realiza la busqueda de la barra de busqueda, recibe por parametro el texto
    //que contiene la barra.
    public void search(String text){
        new Connections().execute(url+"?search="+text);
    }



    //Esta clase realiza las conexiones de forma asincrona y posteriormente interpreta el
    // resultado JSON asignando la informacion a su vista correspondiente
    private class Connections extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return ApiConnect.getRequest(strings[0]);
        }
        @Override
        protected void onPostExecute(String s){
            if(s != null){
                try {
                    objetoJson = new JSONObject(s);
                    arrayJson = objetoJson.getJSONArray("results");
                    recyclerAdapter = new RecyclerAdapterConnection(arrayJson, url, isCharacterCreation, chosenRaceString, chosenBackgroundString, getApplicationContext());

                    LinearLayoutManager layoutManager = new LinearLayoutManager(ApiListActivity.this);

                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerView.setLayoutManager(layoutManager);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else{
                Toast.makeText(ApiListActivity.this, "there was a problem while loading data", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class ConnectionsAppend extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            return ApiConnect.getRequest(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            if(s != null){
                try {
                    objetoJson = new JSONObject(s);
                    JSONArray arrayJsonToAppend = objetoJson.getJSONArray("results");
                    for (int i = 0; i < arrayJsonToAppend.length(); i++){
                        arrayJson.put(arrayJsonToAppend.getJSONObject(i));
                    }
                    recyclerAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }else{
                //Toast.makeText(ApiListActivity.this, "there was a problem while loading data", Toast.LENGTH_LONG).show();
            }
        }
    }
}