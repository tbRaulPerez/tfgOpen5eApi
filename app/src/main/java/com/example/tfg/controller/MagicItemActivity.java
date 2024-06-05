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
import com.example.tfg.model.MagicItem;

import org.json.JSONException;
import org.json.JSONObject;

public class MagicItemActivity extends AppCompatActivity {
    private TextView txName, txTypeRarity, txDescription, lbLicense, txLicense;
    private JSONObject objetoJSON;
    private MagicItem magicItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_magic_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txName = findViewById(R.id.txName);
        txTypeRarity = findViewById(R.id.txClassTable);
        txDescription = findViewById(R.id.txMiscelaneous);
        lbLicense = findViewById(R.id.lbLicesing2);
        txLicense = findViewById(R.id.txLicense2);

        try {
            objetoJSON = new JSONObject(getIntent().getStringExtra("OBJETOJSON"));
            magicItem = new MagicItem(objetoJSON);

            txName.setText(magicItem.getName());
            txTypeRarity.setText("");
            if(magicItem.getRarity() != null && !magicItem.getRarity().equals("")){
                txTypeRarity.append("Type: " + magicItem.getRarity());
            }
            if(magicItem.getType() != null && !magicItem.getType().equals("")){
                txTypeRarity.append(" " + magicItem.getType() + ".");
            }
            if(magicItem.getRequiresAttunement() != null && !magicItem.getRequiresAttunement().equals("")){
                txTypeRarity.append("\n\n" + magicItem.getRequiresAttunement());
            }
            txDescription.setText("");
            if(magicItem.getDesc() != null && !magicItem.getDesc().equals("")){
                txDescription.append("\n\n" + magicItem.getDesc());
            }
            if(magicItem.getLicenseURL() != null && !magicItem.getLicenseURL().equals("")){
                lbLicense.setVisibility(View.VISIBLE);
                txLicense.setVisibility(View.VISIBLE);
                txLicense.append("License URL: " + magicItem.getLicenseURL());
            }
            if(magicItem.getDocumentURL() != null && !magicItem.getDocumentURL().equals("")){
                lbLicense.setVisibility(View.VISIBLE);
                txLicense.setVisibility(View.VISIBLE);
                txLicense.append("\nDocument URL: " + magicItem.getDocumentURL());
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}