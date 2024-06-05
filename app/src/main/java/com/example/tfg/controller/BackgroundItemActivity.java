package com.example.tfg.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.tfg.R;
import com.example.tfg.model.Background;

import org.json.JSONException;
import org.json.JSONObject;

public class BackgroundItemActivity extends AppCompatActivity {
    private TextView txName,txDescription, txProficienciesLanguagesEquipment, lbFeature, txFeature, txLicensing;
    private JSONObject objetoJSON;
    private Background background;
    private Toolbar toolbar;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background_item);
        txName = findViewById(R.id.txName);
        txDescription = findViewById(R.id.txMiscelaneous);
        txProficienciesLanguagesEquipment = findViewById(R.id.txSkillsLanguagesEquipment);
        lbFeature = findViewById(R.id.lbFeature);
        txFeature = findViewById(R.id.txFeature);
        txLicensing = findViewById(R.id.txLicense2);

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
            background = new Background(objetoJSON);

            txName.setText(background.getName().toUpperCase());
            //Description
            if(background.getDesc() != null && !background.getDesc().equals("")){
                txDescription.setText(background.getDesc());
            }
            txProficienciesLanguagesEquipment.setText("");
            if(background.getSkillProficiencies() != null && !background.getSkillProficiencies().equals("")){
                txProficienciesLanguagesEquipment.append("Skill Proficiencies: " + background.getSkillProficiencies() + "\n\n");
            }
            if(background.getToolProficiencies() != null && !background.getToolProficiencies().equals("")){
                txProficienciesLanguagesEquipment.append("Tool Proficiencies: " + background.getToolProficiencies() + "\n\n");
            }
            if(background.getLanguages() != null && !background.getLanguages().equals("")){
                txProficienciesLanguagesEquipment.append("Languages: " + background.getLanguages() + "\n\n");
            }
            if(background.getEquipment() != null && !background.getEquipment().equals("")){
                txProficienciesLanguagesEquipment.append("Equipment: " + background.getEquipment() + "\n\n");
            }
            //Feature
            if(background.getFeature() != null && !background.getFeature().equals("")){
                lbFeature.setText("Feature: " + background.getFeature() + "\n\n");
            }
            txFeature.setText("");
            if(background.getFeatureDesc() != null && !background.getFeatureDesc().equals("")){
                txFeature.append(background.getFeatureDesc() + "\n");
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
            Intent intent = new Intent(this,ApiListActivity.class);
            intent.putExtra("URL", "/classes/");
            intent.putExtra("TITLE", "Choose a class");
            intent.putExtra("ISCHARACTERCREATION", true);
            this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}