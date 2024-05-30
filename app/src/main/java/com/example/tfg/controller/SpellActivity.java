package com.example.tfg.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tfg.R;
import com.example.tfg.model.Spell;

import org.json.JSONException;
import org.json.JSONObject;

public class SpellActivity extends AppCompatActivity {
    private TextView txName, txLevelAndDuration, txCastingTimeAndSchool, txRangeAndComponents, txMaterial, txDescription, txClasses, txHigherLevels, lbLicense, txLicense;
    private JSONObject objetoJSON;
    private Spell spell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spell);
        txName = findViewById(R.id.txName);
        txLevelAndDuration = findViewById(R.id.txLevelAndDuration);
        txCastingTimeAndSchool = findViewById(R.id.txCastingTImeAndSchool);
        txRangeAndComponents = findViewById(R.id.txRangeAndComponents);
        txMaterial = findViewById(R.id.txSkillsLanguagesEquipment);
        txDescription = findViewById(R.id.txDescription);
        txClasses = findViewById(R.id.txClasses);
        txHigherLevels = findViewById(R.id.txFeature);
        lbLicense = findViewById(R.id.lbLicesing2);
        txLicense = findViewById(R.id.txLicense2);


        try {
            objetoJSON = new JSONObject(getIntent().getStringExtra("OBJETOJSON"));
            spell = new Spell(objetoJSON);

            txName.setText(spell.getName().toUpperCase());
            txLevelAndDuration.setText("");
            if(spell.getLevel() != null && !spell.getLevel().equals("")){
                txLevelAndDuration.append("LEVEL\n" + spell.getLevel());
            }
            if(spell.getDuration() != null && !spell.getDuration().equals("")){
                txLevelAndDuration.append("\n\nDURATION\n" + spell.getDuration());
            }
            if(spell.getConcentration() != null && !spell.getConcentration().equals("")){
                if(spell.getConcentration().equals("yes")){
                    txLevelAndDuration.append(", Concentration");
                }
            }
            txCastingTimeAndSchool.setText("");
            if(spell.getCastingTime() != null && !spell.getCastingTime().equals("")){
                txCastingTimeAndSchool.append("CASTING TIME\n" + spell.getCastingTime());
            }
            if(spell.getRitual() != null && !spell.getRitual().equals("")){
                if(spell.getRitual().equals("yes"));
                txCastingTimeAndSchool.append(", Ritual");
            }
            if(spell.getSchool() != null && !spell.getSchool().equals("")){
                txCastingTimeAndSchool.append("\n\nSCHOOL\n" + spell.getSchool());
            }
            txRangeAndComponents.setText("");
            if(spell.getRange() != null && !spell.getRange().equals("")){
                txRangeAndComponents.append("RANGE\n" + spell.getRange());
            }
            if(spell.getComponents() != null && !spell.getComponents().equals("")){
                txRangeAndComponents.append("\n\nCOMPONENTS\n" + spell.getComponents());
            }
            //material
            if(spell.getMaterial() != null && !spell.getMaterial().equals("")){
                txMaterial.setVisibility(View.VISIBLE);
                txMaterial.setText("Material components: " + spell.getMaterial());
            }
            //Description
            if(spell.getDesc() != null && !spell.getDesc().equals("")){
                txDescription.setText(spell.getDesc());
            }
            if(spell.getHigherLevel() != null && !spell.getHigherLevel().equals("")){
                txHigherLevels.setVisibility(View.VISIBLE);
                txHigherLevels.setText("At higher levels" + spell.getHigherLevel());
            }
            if(spell.getDndClass() != null && !spell.getDndClass().equals("")){
                txClasses.setVisibility(View.VISIBLE);
                txClasses.setText("Classes: " + spell.getDndClass());
            }
            txLicense.setText("");
            if(spell.getLicenseURL() != null && !spell.getLicenseURL().equals("")){
                lbLicense.setVisibility(View.VISIBLE);
                txLicense.setVisibility(View.VISIBLE);
                txLicense.append("License URL: " + spell.getLicenseURL());
            }
            if(spell.getDocumentURL() != null && !spell.getDocumentURL().equals("")){
                lbLicense.setVisibility(View.VISIBLE);
                txLicense.setVisibility(View.VISIBLE);
                txLicense.append("\nDocument URL: " + spell.getDocumentURL());
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
}