package com.example.tfg.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.tfg.R;
import com.example.tfg.model.Background;
import com.example.tfg.model.Spell;

import org.json.JSONException;
import org.json.JSONObject;

public class BackgroundItemActivity extends AppCompatActivity {
    private TextView txName,txDescription, txProficienciesLanguagesEquipment, lbFeature, txFeature, txLicensing;
    private JSONObject objetoJSON;
    private Background background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background_item);
        txName = findViewById(R.id.txName);
        txDescription = findViewById(R.id.txDescription);
        txProficienciesLanguagesEquipment = findViewById(R.id.txSkillsLanguagesEquipment);
        lbFeature = findViewById(R.id.lbFeature);
        txFeature = findViewById(R.id.txFeature);
        txLicensing = findViewById(R.id.txLicense2);

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
                txProficienciesLanguagesEquipment.append("Skill Proficiencies: " + background.getSkillProficiencies() + "\n");
            }
            if(background.getToolProficiencies() != null && !background.getToolProficiencies().equals("")){
                txProficienciesLanguagesEquipment.append("Tool Proficiencies: " + background.getToolProficiencies() + "\n");
            }
            if(background.getLanguages() != null && !background.getLanguages().equals("")){
                txProficienciesLanguagesEquipment.append("Languages: " + background.getLanguages() + "\n");
            }
            if(background.getEquipment() != null && !background.getEquipment().equals("")){
                txProficienciesLanguagesEquipment.append("Equipment: " + background.getEquipment() + "\n");
            }
            //Feature
            if(background.getFeature() != null && !background.getFeature().equals("")){
                lbFeature.append("Feature: " + background.getFeature() + "\n");
            }
            if(background.getFeatureDesc() != null && !background.getFeatureDesc().equals("")){
                txFeature.append(background.getFeatureDesc() + "\n");
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
}