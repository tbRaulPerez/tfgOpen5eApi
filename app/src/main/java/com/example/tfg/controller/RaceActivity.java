package com.example.tfg.controller;

import android.content.Intent;
import android.os.Bundle;

import com.example.tfg.model.Race;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.tfg.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class RaceActivity extends AppCompatActivity {


    private TextView txName, lbDescription, txDescription, lbTraits, txTraits, lbSubraces, txSubraces, lbLicense, txLicense;
    private JSONObject objetoJSON;
    private Race race;
    private Toolbar toolbar;
    private String title;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_races);

        txName = findViewById(R.id.txName);
        lbDescription = findViewById(R.id.lbDescription);
        txDescription = findViewById(R.id.txMiscelaneous);
        lbTraits = findViewById(R.id.lbRaceTraits);
        txTraits = findViewById(R.id.txRaceTraits);
        lbSubraces = findViewById(R.id.lbSubraces);
        txSubraces = findViewById(R.id.txSubraces);
        lbLicense = findViewById(R.id.lbLicesing2);
        txLicense = findViewById(R.id.txLicense2);

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
        }

        try {
            objetoJSON = new JSONObject(getIntent().getStringExtra("OBJETOJSON"));
            race = new Race(objetoJSON);

            txName.setText(race.getName());
            //Description
            txDescription.setText("");
            if(race.getDesc() != null && !race.getDesc().equals("")){
                lbDescription.setVisibility(View.VISIBLE);
                txDescription.setVisibility(View.VISIBLE);
                txDescription.append(race.getDesc() + "\n\n");
            }
            if(race.getAge() != null && !race.getAge().equals("")){
                lbDescription.setVisibility(View.VISIBLE);
                txDescription.setVisibility(View.VISIBLE);
                txDescription.append(race.getAge() + "\n\n");
            }
            if(race.getSize() != null && !race.getSize().equals("")){
                lbDescription.setVisibility(View.VISIBLE);
                txDescription.setVisibility(View.VISIBLE);
                txDescription.append(race.getSize() + "\n\n");
            }
            txTraits.setText("");
            if(race.getSpeed() != null && !race.getSpeed().equals("")){
                lbTraits.setVisibility(View.VISIBLE);
                lbTraits.setVisibility(View.VISIBLE);
                Iterator<String> keys = race.getSpeed().keys();
                txTraits.append("Speed: ");
                while (keys.hasNext()){
                    String key = keys.next();
                    txTraits.append(key + " " + race.getSpeed().getString(key));
                    if(keys.hasNext()){
                        txTraits.append(", ");
                    }else txTraits.append("\n\n");
                }
            }
            if(race.getVision() != null && !race.getVision().equals("")){
                lbTraits.setVisibility(View.VISIBLE);
                lbTraits.setVisibility(View.VISIBLE);
                txTraits.append("Vision: " + race.getVision() + "\n\n");
            }
            if(race.getTraits() != null && !race.getTraits().equals("")){
                lbTraits.setVisibility(View.VISIBLE);
                lbTraits.setVisibility(View.VISIBLE);
                txTraits.append("Traits: " + race.getTraits() + "\n\n");
            }
            if(race.getLanguages() != null && !race.getLanguages().equals("")){
                lbTraits.setVisibility(View.VISIBLE);
                lbTraits.setVisibility(View.VISIBLE);
                txTraits.append("Languages: " + race.getLanguages() + "\n\n");
            }
            if(race.getAbilityScoreImprovement() != null && !race.getAbilityScoreImprovement().equals("")){
                lbTraits.setVisibility(View.VISIBLE);
                lbTraits.setVisibility(View.VISIBLE);
                txTraits.append("Ability Score improvement: " + race.getAbilityScoreImprovement() + "\n\n");
            }

            txLicense.setText("");
            if(race.getLicenseURL() != null && !race.getLicenseURL().equals("")){
                lbLicense.setVisibility(View.VISIBLE);
                txLicense.setVisibility(View.VISIBLE);
                txLicense.append("License URL: " + race.getLicenseURL());
            }
            if(race.getDocumentURL() != null && !race.getDocumentURL().equals("")){
                lbLicense.setVisibility(View.VISIBLE);
                txLicense.setVisibility(View.VISIBLE);
                txLicense.append("\nDocument URL: " + race.getDocumentURL());
            }


                txSubraces.setText("");
                if(race.getSubraces() != null && race.getSubraces().length() != 0){
                    lbSubraces.setVisibility(View.VISIBLE);
                    txSubraces.setVisibility(View.VISIBLE);
                    for(int i = 0; i < race.getSubraces().length(); i++){
                        txSubraces.append(race.getSubraces().getJSONObject(i).getString("name").toUpperCase() + "\n\n");
                        txSubraces.append(race.getSubraces().getJSONObject(i).getString("desc") + "\n\n");
                        txSubraces.append(race.getSubraces().getJSONObject(i).getString("traits") + "\n\n");
                        txSubraces.append(race.getSubraces().getJSONObject(i).getString("asi_desc") + "\n\n");
                    }
                }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(title != null){
            getMenuInflater().inflate(R.menu.creating_character_menu, menu);
        }
        return true;
    }

    //Se gestiona lo que sucede al pulsar los elementos del menu:
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if(item.getItemId() == R.id.item_choose){
            Intent intent = new Intent(this, ApiListActivity.class);
            intent.putExtra("URL", "/backgrounds/");
            intent.putExtra("TITLE", "Choose a background");
            intent.putExtra("ISCHARACTERCREATION", true);
            this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


