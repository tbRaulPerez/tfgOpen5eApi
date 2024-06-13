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
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tfg.R;
import com.example.tfg.adapter.RecyclerAdapter;
import com.example.tfg.model.ListItem;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    //se usara el adapter que no usa conexiones pues se le pasará una lista personalizada (metodo getList).
    RecyclerAdapter recyclerAdapter;
    FirebaseAuth fAuth;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //configuracion del recyclerView,
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerAdapter = new RecyclerAdapter(getList());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(layoutManager);

        //Se elimina el titulo de la actionBar
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);
        }

    }
    //Se crea un menu con con el diseño primary_menu.xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.primary_menu,menu);

        return true;
    }
    //Se gestiona lo que sucede al pulsar los elementos del menu:
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int itemId = item.getItemId();
        if(itemId == R.id.choose_this_item){
            fAuth.signOut();
            finish();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        return true;
    }

    //Se sobrescribe el metodo que gestiona lo que sucede al pulsar el boton de atras
    //para añadir una alerta de que se cerrará la sesión
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Do you want to log out?").setTitle("Log out");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                fAuth.signOut();
                finish();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }
    //devuelve la lista de items para el recyclerView
    public List<ListItem> getList(){
        List<ListItem> list = new ArrayList<>();

        list.add(new ListItem("My Lists", R.drawable.baseline_format_list_bulleted_24));
        list.add(new ListItem("Creatures", R.drawable.monster_icon));
        list.add(new ListItem("Spells", R.drawable.spells_icon));
        list.add(new ListItem("Background", R.drawable.backgrounds_icon));
        list.add(new ListItem("Races", R.drawable.characters_icon));
        list.add(new ListItem("Classes", R.drawable.classes_icon));
        list.add(new ListItem("Magic items", R.drawable.magic_item));
        list.add(new ListItem("Weapons", R.drawable.weapons_icon));
        list.add(new ListItem("Armor", R.drawable.armor_icon));
        return list;
    }
}