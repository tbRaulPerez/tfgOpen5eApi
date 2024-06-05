package com.example.tfg.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg.R;
import com.example.tfg.model.Armor;

import org.json.JSONException;
import org.json.JSONObject;

public class ArmorActivity extends AppCompatActivity {
    private TextView txName, txDescription, lbLicense, txLicense;
    private JSONObject objetoJSON;
    private Armor armor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_armor);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txName = findViewById(R.id.txName);
        txDescription = findViewById(R.id.txMiscelaneous);
        lbLicense = findViewById(R.id.lbLicesing2);
        txLicense = findViewById(R.id.txLicense2);

        try {
            objetoJSON = new JSONObject(getIntent().getStringExtra("OBJETOJSON"));
            armor = new Armor(objetoJSON);

            txName.setText(armor.getName());
            txDescription.setText("");
            if(armor.getCategory() != null && !armor.getCategory().equals("")){
                txDescription.append("Category: " + armor.getCategory() + "\n\n");
            }
            if(armor.getAcString() != null && !armor.getAcString().equals("")){
                txDescription.append("Armor class: " + armor.getAcString() + "\n\n");
            }
            if(armor.getStrengthRequirement() != 0){
                txDescription.append("Strength requirement: " + armor.getStrengthRequirement() + "\n\n");
            }
            if(armor.getCost() != null && !armor.getCost().equals("")){
                txDescription.append("Cost: " + armor.getCost() + "\n\n");
            }
            if(armor.getStealthDisadvange() != null && !armor.getStealthDisadvange()){
                txDescription.append("Stealth checks made with this armor are made with disadvange");
            }
            if(armor.getLicenseURL() != null && !armor.getLicenseURL().equals("")){
                lbLicense.setVisibility(View.VISIBLE);
                txLicense.setVisibility(View.VISIBLE);
                txLicense.append("License URL: " + armor.getLicenseURL());
            }
            if(armor.getDocumentURL() != null && !armor.getDocumentURL().equals("")){
                lbLicense.setVisibility(View.VISIBLE);
                txLicense.setVisibility(View.VISIBLE);
                txLicense.append("\nDocument URL: " + armor.getDocumentURL());
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}