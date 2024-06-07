package com.example.tfg.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg.R;
import com.example.tfg.model.Background;
import com.example.tfg.model.Character;
import com.example.tfg.model.CharacterClass;
import com.example.tfg.model.Race;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLOutput;

public class ChooseNameActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView etName;
    private Button btDone;
    private String title;
    private String chosenRaceString;
    private String chosenBackgroundString;
    private String chosenClassString;
    private JSONObject chosenRace, chosenBackground, chosenClass;
    private Character character;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_name);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etName = findViewById(R.id.etChooseName);
        btDone = findViewById(R.id.btChooseNameDone);

        toolbar = findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
        //Se dejará de mostrar el nombre de la app en el actionBar además de mostrar un boton de atrás.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            title = getIntent().getStringExtra("TITLE");
            if (title != null) {
                actionBar.setTitle(title);
            }else{
                actionBar.setDisplayShowTitleEnabled(false);
            }
            actionBar.setDisplayHomeAsUpEnabled(true);
            chosenRaceString = getIntent().getStringExtra("CHOSENRACE");
            System.out.println(chosenRaceString);
            chosenBackgroundString = getIntent().getStringExtra("CHOSENBACKGROUND");
            System.out.println(chosenBackgroundString);
            chosenClassString = getIntent().getStringExtra("CHOSENCLASS");
            System.out.println(chosenClassString);

        }
        btDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (etName.getText().toString() != null && !etName.getText().toString().equals("")){
                        chosenRace = new JSONObject(chosenRaceString);
                        chosenBackground = new JSONObject(chosenBackgroundString);
                        chosenClass = new JSONObject(chosenClassString);
                        character = new Character(etName.getText().toString(),new Background(chosenBackground),new CharacterClass(chosenClass),new Race(chosenRace),null,null,null, null);
                        System.out.println(character.toString());
                        Toast.makeText(ChooseNameActivity.this, "Your character was created successfully!", Toast.LENGTH_LONG).show();

                        finish();
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
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
