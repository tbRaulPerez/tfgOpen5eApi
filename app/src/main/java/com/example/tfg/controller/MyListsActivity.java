package com.example.tfg.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg.R;
import com.example.tfg.adapter.RecyclerAdapter;
import com.example.tfg.model.ListItem;

import java.util.ArrayList;
import java.util.List;

public class MyListsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    //se usara el adapter que no usa conexiones pues se le pasará una lista personalizada (metodo getList).
    RecyclerAdapter recyclerAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_lists);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        //configuracion del recyclerView,
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerAdapter = new RecyclerAdapter(getList());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(layoutManager);

        //Se dejará de mostrar el nombre de la app en el actionBar además de mostrar un boton de atrás.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public List<ListItem> getList() {
        List<ListItem> list = new ArrayList<>();

        list.add(new ListItem("My Characters", R.drawable.baseline_arrow_right_24));
        list.add(new ListItem("My Creatures", R.drawable.baseline_arrow_right_24));
        list.add(new ListItem("My Spells", R.drawable.baseline_arrow_right_24));
        list.add(new ListItem("My Backgrounds", R.drawable.baseline_arrow_right_24));
        list.add(new ListItem("My Races", R.drawable.baseline_arrow_right_24));
        list.add(new ListItem("My Classes", R.drawable.baseline_arrow_right_24));
        list.add(new ListItem("My Magic items", R.drawable.baseline_arrow_right_24));
        list.add(new ListItem("My Weapons", R.drawable.baseline_arrow_right_24));
        list.add(new ListItem("My Armors", R.drawable.baseline_arrow_right_24));

        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    //Se gestiona lo que sucede al pulsar los elementos del menu:
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}