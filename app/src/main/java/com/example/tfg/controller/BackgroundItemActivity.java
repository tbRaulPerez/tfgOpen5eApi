package com.example.tfg.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfg.R;
import com.example.tfg.model.Background;
import com.example.tfg.model.Creature;
import com.example.tfg.model.Race;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class BackgroundItemActivity extends AppCompatActivity {
    private TextView txName,txDescription, txProficienciesLanguagesEquipment, lbFeature, txFeature, txLicensing,lbLicense;
    private JSONObject objetoJSON;
    private Background background;
    private Toolbar toolbar;
    private String title;
    private boolean isCharacterCreation;
    private String chosenRaceString;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;


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
        lbLicense = findViewById(R.id.lbLicesing2);

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
        String jsonObjectExtra = getIntent().getStringExtra("OBJETOJSON");
        if(jsonObjectExtra != null && !jsonObjectExtra.equals("")){
            try {
                objetoJSON = new JSONObject(getIntent().getStringExtra("OBJETOJSON"));
                background = new Background(objetoJSON, new ArrayList<>());

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }else if(getIntent().getSerializableExtra("OBJECTFROMFIREBASE")!=null){
            background = (Background) getIntent().getSerializableExtra("OBJECTFROMFIREBASE");
        }


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

            txLicensing.setText("");
            if (background.getLicenseURL() != null && !background.getLicenseURL().equals("")) {
                lbLicense.setVisibility(View.VISIBLE);
                txLicensing.setVisibility(View.VISIBLE);
                txLicensing.append("License URL: " + background.getLicenseURL());
            }
            if (background.getDocumentURL() != null && !background.getDocumentURL().equals("")) {
                lbLicense.setVisibility(View.VISIBLE);
                txLicensing.setVisibility(View.VISIBLE);
                txLicensing.append("\nDocument URL: " + background.getDocumentURL());
            }

        
        isCharacterCreation = getIntent().getBooleanExtra("ISCHARACTERCREATION", false);
        if(isCharacterCreation){
            chosenRaceString = getIntent().getStringExtra("CHOSENRACE");
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(title != null){
            getMenuInflater().inflate(R.menu.creating_character_menu, menu);
        }else if(background.getOwner().contains(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
            getMenuInflater().inflate(R.menu.saved_item_menu, menu);
        }else {
            getMenuInflater().inflate(R.menu.detail_item_menu, menu);
        }
        return true;
    }
    //Se gestiona lo que sucede al pulsar los elementos del menu:
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.save_item) {
            saveData(background);
        } else if(item.getItemId() == R.id.choose_this_item){
            Intent intent = new Intent(this,ApiListActivity.class);
            intent.putExtra("URL", "/classes/");
            intent.putExtra("TITLE", "Choose a class");
            intent.putExtra("CHOSENRACE", chosenRaceString);
            intent.putExtra("CHOSENBACKGROUND", objetoJSON.toString());
            intent.putExtra("ISCHARACTERCREATION", true);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            this.startActivity(intent);
            return true;
        }else if(item.getItemId() == R.id.btDeleteSavedItemMenu){
            new AlertDialog.Builder(this).setTitle("Are you sure that you want to delete this character. This is permanent").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(background.getOwner().contains(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        background.getOwner().remove(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        // below line is used to get the
                        // instance of our FIrebase database.
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        // below line is used to get reference for our database.
                        DatabaseReference databaseReference = firebaseDatabase.getReference().child("dndProject").child("backgrounds").child(background.getName());
                        databaseReference.setValue(background);
                        if(background.getOwner().isEmpty()){
                            databaseReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(BackgroundItemActivity.this, "Your background was deleted", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(BackgroundItemActivity.this, "Data deletion failed" + e, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        BackgroundItemActivity.this.finish();
                    }
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void saveData(Background background) {
        // below line is used to get the
        // instance of our FIrebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference().child("dndProject").child("backgrounds").child(background.getName());

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Background existentBackground = snapshot.getValue(Background.class);
                if(existentBackground != null){
                    if(existentBackground.getOwner().contains(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        Toast.makeText(BackgroundItemActivity.this, "You have already saved this item", Toast.LENGTH_SHORT).show();
                    }else {
                        existentBackground.getOwner().add(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        save(existentBackground);
                    }
                }else{
                    background.getOwner().add(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    save(background);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });
    }
    private void save(Background background){
        databaseReference.setValue(background).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(BackgroundItemActivity.this, "Added to your personal list!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(BackgroundItemActivity.this, "Failed to save data " + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}