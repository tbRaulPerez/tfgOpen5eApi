package com.example.tfg.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfg.R;
import com.example.tfg.model.Creature;
import com.example.tfg.model.Race;
import com.example.tfg.model.Spell;
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

public class SpellActivity extends AppCompatActivity {
    private TextView txName, txLevelAndDuration, txCastingTimeAndSchool, txRangeAndComponents, txMaterial, txDescription, txClasses, txHigherLevels, lbLicense, txLicense;
    private JSONObject objetoJSON;
    private Spell spell;
    private String title;
    private Toolbar toolbar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spell);
        txName = findViewById(R.id.txName);
        txLevelAndDuration = findViewById(R.id.txLevelAndDuration);
        txCastingTimeAndSchool = findViewById(R.id.txCastingTImeAndSchool);
        txRangeAndComponents = findViewById(R.id.txRangeAndComponents);
        txMaterial = findViewById(R.id.txSkillsLanguagesEquipment);
        txDescription = findViewById(R.id.txMiscelaneous);
        txClasses = findViewById(R.id.txClasses);
        txHigherLevels = findViewById(R.id.txFeature);
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
            } else {
                actionBar.setDisplayShowTitleEnabled(false);
            }
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        String jsonObjectExtra = getIntent().getStringExtra("OBJETOJSON");
        if (jsonObjectExtra != null && !jsonObjectExtra.equals("")) {
            try {
                objetoJSON = new JSONObject(getIntent().getStringExtra("OBJETOJSON"));
                spell = new Spell(objetoJSON, new ArrayList<>());

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        } else if (getIntent().getSerializableExtra("OBJECTFROMFIREBASE") != null) {
            spell = (Spell) getIntent().getSerializableExtra("OBJECTFROMFIREBASE");
        }

        txName.setText(spell.getName().toUpperCase());
        txLevelAndDuration.setText("");
        if (spell.getLevel() != null && !spell.getLevel().equals("")) {
            txLevelAndDuration.append("LEVEL\n" + spell.getLevel());
        }
        if (spell.getDuration() != null && !spell.getDuration().equals("")) {
            txLevelAndDuration.append("\n\nDURATION\n" + spell.getDuration());
        }
        if (spell.getConcentration() != null && !spell.getConcentration().equals("")) {
            if (spell.getConcentration().equals("yes")) {
                txLevelAndDuration.append(", Concentration");
            }
        }
        txCastingTimeAndSchool.setText("");
        if (spell.getCastingTime() != null && !spell.getCastingTime().equals("")) {
            txCastingTimeAndSchool.append("CASTING TIME\n" + spell.getCastingTime());
        }
        if (spell.getRitual() != null && !spell.getRitual().equals("")) {
            if (spell.getRitual().equals("yes")) ;
            txCastingTimeAndSchool.append(", Ritual");
        }
        if (spell.getSchool() != null && !spell.getSchool().equals("")) {
            txCastingTimeAndSchool.append("\n\nSCHOOL\n" + spell.getSchool());
        }
        txRangeAndComponents.setText("");
        if (spell.getRange() != null && !spell.getRange().equals("")) {
            txRangeAndComponents.append("RANGE\n" + spell.getRange());
        }
        if (spell.getComponents() != null && !spell.getComponents().equals("")) {
            txRangeAndComponents.append("\n\nCOMPONENTS\n" + spell.getComponents());
        }
        //material
        if (spell.getMaterial() != null && !spell.getMaterial().equals("")) {
            txMaterial.setVisibility(View.VISIBLE);
            txMaterial.setText("Material components: " + spell.getMaterial());
        }
        //Description
        if (spell.getDesc() != null && !spell.getDesc().equals("")) {
            txDescription.setText(spell.getDesc());
        }
        if (spell.getHigherLevel() != null && !spell.getHigherLevel().equals("")) {
            txHigherLevels.setVisibility(View.VISIBLE);
            txHigherLevels.setText("At higher levels" + spell.getHigherLevel());
        }
        if (spell.getDndClass() != null && !spell.getDndClass().equals("")) {
            txClasses.setVisibility(View.VISIBLE);
            txClasses.setText("Classes: " + spell.getDndClass());
        }
        txLicense.setText("");
        if (spell.getLicenseURL() != null && !spell.getLicenseURL().equals("")) {
            lbLicense.setVisibility(View.VISIBLE);
            txLicense.setVisibility(View.VISIBLE);
            txLicense.append("License URL: " + spell.getLicenseURL());
        }
        if (spell.getDocumentURL() != null && !spell.getDocumentURL().equals("")) {
            lbLicense.setVisibility(View.VISIBLE);
            txLicense.setVisibility(View.VISIBLE);
            txLicense.append("\nDocument URL: " + spell.getDocumentURL());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (title != null) {
            getMenuInflater().inflate(R.menu.creating_character_menu, menu);
        } else if(spell.getOwner().contains(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
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
            saveData(spell);
        }else if(item.getItemId() == R.id.btDeleteSavedItemMenu){
            new AlertDialog.Builder(this).setTitle("Are you sure that you want to delete this character. This is permanent").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(spell.getOwner().contains(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        spell.getOwner().remove(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        // below line is used to get the
                        // instance of our FIrebase database.
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        // below line is used to get reference for our database.
                        DatabaseReference databaseReference = firebaseDatabase.getReference().child("dndProject").child("spells").child(spell.getName());
                        databaseReference.setValue(spell);
                        if(spell.getOwner().isEmpty()){
                            databaseReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(SpellActivity.this, "Your spell was deleted", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SpellActivity.this, "Data deletion failed" + e, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        SpellActivity.this.finish();
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

    private void saveData(Spell spell) {
        // below line is used to get the
        // instance of our FIrebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference().child("dndProject").child("spells").child(spell.getName());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Spell existentSpell = snapshot.getValue(Spell.class);
                if (existentSpell != null) {
                    //si el objeto existe
                    if (existentSpell.getOwner().contains(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                        Toast.makeText(SpellActivity.this, "You have already saved this item", Toast.LENGTH_SHORT).show();
                    } else {
                        //Si existe, pero este usuario no lo posee

                        existentSpell.getOwner().add(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        save(existentSpell);
                    }
                } else {
                    //Si no existe, crea el objeto
                    spell.getOwner().add(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    save(spell);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });
    }

    private void save(Spell spell) {
        databaseReference.setValue(spell).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(SpellActivity.this, "Added to your personal list!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SpellActivity.this, "Failed to save data " + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}


