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
import com.example.tfg.model.Weapon;

import org.json.JSONException;
import org.json.JSONObject;

public class WeaponActivity extends AppCompatActivity {
    private TextView txName, txDescription, lbLicense, txLicense;
    private JSONObject objetoJSON;
    private Weapon weapon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_weapon);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txName = findViewById(R.id.txName);
        txDescription = findViewById(R.id.txMiscelaneous);
        txLicense = findViewById(R.id.txLicense2);
        lbLicense = findViewById(R.id.lbLicesing2);
        try {
            objetoJSON = new JSONObject(getIntent().getStringExtra("OBJETOJSON"));
            weapon = new Weapon(objetoJSON);

            txName.setText(weapon.getName());
            txDescription.setText("");
            if(weapon.getCategory() != null && !weapon.getCategory().equals("")){
                txDescription.append("Category: " + weapon.getCategory() + ".\n\n");
            }
            if(weapon.getCost() != null && !weapon.getCost().equals("")){
                txDescription.append("Cost: " + weapon.getCost() + ".\n\n");
            }
            if(weapon.getDmgDice() != null && !weapon.getDmgDice().equals("")){
                txDescription.append("Damage: " + weapon.getDmgDice());
                if(weapon.getDmgType() != null && !weapon.getDmgType().equals("")){
                    txDescription.append(" of " + weapon.getDmgType() + " damage");
                }
                txDescription.append(".\n\n");
            }
            if(weapon.getProperties() != null && weapon.getProperties().length != 0){
                txDescription.append("Properties:");
                for(int i = 0; i < weapon.getProperties().length; i++){
                    txDescription.append(" " + weapon.getProperties()[i]);
                    if(i+1 < weapon.getProperties().length)
                        txDescription.append(",");
                }
            }

            if(weapon.getLicenseURL() != null && !weapon.getLicenseURL().equals("")){
                lbLicense.setVisibility(View.VISIBLE);
                txLicense.setVisibility(View.VISIBLE);
                txLicense.append("License URL: " + weapon.getLicenseURL());
            }
            if(weapon.getDocumentURL() != null && !weapon.getDocumentURL().equals("")){
                lbLicense.setVisibility(View.VISIBLE);
                txLicense.setVisibility(View.VISIBLE);
                txLicense.append("\nDocument URL: " + weapon.getDocumentURL());
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}