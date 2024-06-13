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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import io.noties.markwon.Markwon;

public class CreatureActivity extends AppCompatActivity {
    private TextView txName, txSizeTypeAndAlignment, txAcHpSpeed, lbStats, txStatStr, txStatDex, txStatCon, txStatInt, txStatWis, txStatCha, txSkillsSensesLanguagesCr,
            lbSpecialAbilities, txSpecialAbilities, lbActions, txActions, lbBonusActions, txBonusActions, lbReactions, txReactions, lbLegendaryActions, txLegendaryActions, lbDescription, txDescription, lbLicensing, txLicense;
    JSONObject objetoJSON;
    Creature creature;
    Markwon markwon;
    private Toolbar toolbar;
    private String title;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creature);
        txName = findViewById(R.id.txName);
        txSizeTypeAndAlignment = findViewById(R.id.txSizeTypeAndAlignment);
        txAcHpSpeed = findViewById(R.id.txValues);
        lbStats = findViewById(R.id.lbStats);
        txStatStr = findViewById(R.id.txLevelAndDuration);
        txStatDex = findViewById(R.id.txCastingTImeAndSchool);
        txStatCon = findViewById(R.id.txRangeAndComponents);
        txStatInt = findViewById(R.id.txComponentsAndDamageAndEffects);
        txStatWis = findViewById(R.id.txStatWis);
        txStatCha = findViewById(R.id.txStatCha);
        txSkillsSensesLanguagesCr = findViewById(R.id.txSkillsSensesLanguagesCrVulnerabilitesResistancesAndInmunities);
        lbSpecialAbilities = findViewById(R.id.lbSpecialAbilities);
        txSpecialAbilities = findViewById(R.id.txSpecialAbilities);
        lbActions = findViewById(R.id.lbActions);
        lbBonusActions = findViewById(R.id.lbBonusActions);
        txActions = findViewById(R.id.txActions);
        txBonusActions = findViewById(R.id.txBonusActions);
        lbReactions = findViewById(R.id.lbReactions);
        txReactions = findViewById(R.id.txReactions);
        lbLegendaryActions = findViewById(R.id.lbLegendaryActions);
        txLegendaryActions = findViewById(R.id.txLegendaryActionsAndLegendaryDesc);
        lbDescription = findViewById(R.id.lbDescription);
        txDescription = findViewById(R.id.txMiscelaneous);
        lbLicensing = findViewById(R.id.lbLicesing);
        txLicense = findViewById(R.id.txLicense);
        markwon = Markwon.create(this);
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
        if(jsonObjectExtra != null && !jsonObjectExtra.equals("")){
            try {
                objetoJSON = new JSONObject(jsonObjectExtra);
                creature = new Creature(objetoJSON, new ArrayList<>());

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }else if(getIntent().getSerializableExtra("OBJECTFROMFIREBASE")!=null){
            creature = (Creature) getIntent().getSerializableExtra("OBJECTFROMFIREBASE");
        }
        try {

            //creature = (Creature) getIntent().getSerializableExtra("CREATURE");

            //key info
            txName.setText(creature.getName().toUpperCase());
            txSizeTypeAndAlignment.setText("");
            if (creature.getSize() != null && !creature.getSize().equals("")) {
                txSizeTypeAndAlignment.append(creature.getSize());
            }
            if (creature.getType() != null && !creature.getType().equals("")) {
                txSizeTypeAndAlignment.append(" " + creature.getType());
            }
            if (creature.getAlignment() != null && !creature.getAlignment().equals("")) {
                txSizeTypeAndAlignment.append(", " + creature.getAlignment());
            }
            txAcHpSpeed.setText("Armor Class: " + creature.getArmorClass() +
                    "\nHit Points: " + creature.getHitPoints());
            JSONObject speed = new JSONObject(creature.getSpeedJsonObject());
            Iterator<String> keys = speed.keys();
            txAcHpSpeed.append("\nSpeed: ");
            while (keys.hasNext()) {
                String key = keys.next();
                txAcHpSpeed.append(key + " " + speed.getInt(key));
                if (keys.hasNext()) {
                    txAcHpSpeed.append(", ");
                }
            }

            //Ability scores
            txStatStr.setText("STR\n" + creature.getStrength());
            txStatDex.setText("DEX\n" + creature.getDexterity());
            txStatCon.setText("STR\n" + creature.getConstitution());
            txStatInt.setText("STR\n" + creature.getIntelligence());
            txStatWis.setText("STR\n" + creature.getWisdom());
            txStatCha.setText("STR\n" + creature.getCharisma());

            //Special Abilities
            txSpecialAbilities.setText("");
            if (creature.getSpecialAbilitiesJsonArray() != null && creature.getSpecialAbilitiesJsonArray().length() != 0) {
                JSONArray specialAbilities = new JSONArray(creature.getSpecialAbilitiesJsonArray());
                lbSpecialAbilities.setVisibility(View.VISIBLE);
                txSpecialAbilities.setVisibility(View.VISIBLE);
                for (int i = 0; i < specialAbilities.length(); i++) {
                    txSpecialAbilities.append(specialAbilities.getJSONObject(i).getString("name"));
                    txSpecialAbilities.append("\n" + specialAbilities.getJSONObject(i).getString("desc") + "\n\n");
                }
            }
            //Skills
            JSONObject skills = new JSONObject(creature.getSkillsJsonObject());
            keys = skills.keys();
            txSkillsSensesLanguagesCr.setText("\nSkills: ");
            while (keys.hasNext()) {
                String key = keys.next();
                txSkillsSensesLanguagesCr.append(key + " " + skills.getString(key));
                if (keys.hasNext()) {
                    txSkillsSensesLanguagesCr.append(", ");
                }
            }
            //Senses
            if (creature.getSenses() != null && !creature.getSenses().equals("")) {
                txSkillsSensesLanguagesCr.append("\nSenses: " + creature.getSenses());
            }
            //Languages
            if (creature.getLanguages() != null && !creature.getLanguages().equals("")) {
                txSkillsSensesLanguagesCr.append("\nLanguages: " + creature.getLanguages());
            }

            //Challenge Rating
            if (creature.getChallengeRating() != null && !creature.getChallengeRating().equals("")) {
                txSkillsSensesLanguagesCr.append("\nChallenge Rating: " + creature.getChallengeRating());
            }
            //Actions
            txActions.setText("");
            if (creature.getActionsJsonArray() != null && creature.getActionsJsonArray().length() != 0) {
                JSONArray actions = new JSONArray(creature.getActionsJsonArray());
                lbActions.setVisibility(View.VISIBLE);
                txActions.setVisibility(View.VISIBLE);
                for (int i = 0; i < actions.length(); i++) {
                    txActions.append(actions.getJSONObject(i).getString("name"));
                    txActions.append("\n" + actions.getJSONObject(i).getString("desc") + "\n\n");
                }
            }
            //Bonus actions
            txBonusActions.setText("");
            if (creature.getBonusActionsJsonArray() != null && creature.getBonusActionsJsonArray().length() != 0) {
                JSONArray bonusActions = new JSONArray(creature.getBonusActionsJsonArray());
                lbBonusActions.setVisibility(View.VISIBLE);
                txBonusActions.setVisibility(View.VISIBLE);
                for (int i = 0; i < bonusActions.length(); i++) {
                    txBonusActions.append(bonusActions.getJSONObject(i).getString("name"));
                    txBonusActions.append("\n" + bonusActions.getJSONObject(i).getString("desc") + "\n\n");
                }
            }
            //Reactions
            txReactions.setText("");
            if (creature.getReactionsJsonArray() != null && creature.getReactionsJsonArray().length() != 0) {
                JSONArray reactions = new JSONArray(creature.getReactionsJsonArray());
                lbReactions.setVisibility(View.VISIBLE);
                txReactions.setVisibility(View.VISIBLE);
                for (int i = 0; i < reactions.length(); i++) {
                    txReactions.append(reactions.getJSONObject(i).getString("name"));
                    txReactions.append("\n" + reactions.getJSONObject(i).getString("desc") + "\n\n");
                }
            }


            //Legendary Actions
            txLegendaryActions.setText("");
            if (creature.getLegendaryDesc() != null && !creature.getLegendaryDesc().equals("")) {
                txLegendaryActions.append(creature.getLegendaryDesc());
            }

            if (creature.getLegendaryActionsJsonArray() != null && !creature.getLegendaryActionsJsonArray().isEmpty()) {
                JSONArray legendaryActions = new JSONArray(creature.getLegendaryActionsJsonArray());
                lbLegendaryActions.setVisibility(View.VISIBLE);
                txLegendaryActions.setVisibility(View.VISIBLE);
                for (int i = 0; i < legendaryActions.length(); i++) {
                    txLegendaryActions.append(legendaryActions.getJSONObject(i).getString("name"));
                    txLegendaryActions.append("\n" + legendaryActions.getJSONObject(i).getString("desc") + "\n\n");
                }
            }

            //Description
            if (creature.getDesc() != null && !creature.getDesc().equals("")) {
                lbDescription.setVisibility(View.VISIBLE);
                txDescription.setVisibility(View.VISIBLE);
                markwon.setMarkdown(txDescription, creature.getDesc());
            }


            //Licensing
            txLicense.setText("");
            if (creature.getLicenseURL() != null && !creature.getLicenseURL().equals("")) {
                lbLicensing.setVisibility(View.VISIBLE);
                txLicense.setVisibility(View.VISIBLE);
                txLicense.append("License URL: " + creature.getLicenseURL());
            }
            if (creature.getDocumentURL() != null && !creature.getDocumentURL().equals("")) {
                lbLicensing.setVisibility(View.VISIBLE);
                txLicense.setVisibility(View.VISIBLE);
                txLicense.append("\nLicense URL: " + creature.getDocumentURL());
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (title != null) {
            getMenuInflater().inflate(R.menu.creating_character_menu, menu);
        } else if(creature.getOwner().contains(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
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
            saveData(creature);
        }else if(item.getItemId() == R.id.btDeleteSavedItemMenu){
            new AlertDialog.Builder(this).setTitle("Are you sure that you want to delete this character. This is permanent").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(creature.getOwner().contains(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        creature.getOwner().remove(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        // below line is used to get the
                        // instance of our FIrebase database.
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        // below line is used to get reference for our database.
                        DatabaseReference databaseReference = firebaseDatabase.getReference().child("dndProject").child("creatures").child(creature.getName());
                        databaseReference.setValue(creature);
                        if(creature.getOwner().isEmpty()){
                            databaseReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(CreatureActivity.this, "Your creature was deleted", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CreatureActivity.this, "Data deletion failed" + e, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        CreatureActivity.this.finish();
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

    private void saveData(Creature creature) {
        // below line is used to get the
        // instance of our FIrebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();
        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference().child("dndProject").child("creatures").child(creature.getName());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Creature existentCreature = snapshot.getValue(Creature.class);
                if (existentCreature != null) {
                    //si el objeto existe
                    if (existentCreature.getOwner().contains(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        Toast.makeText(CreatureActivity.this, "You have already saved this item", Toast.LENGTH_SHORT).show();
                    }else {
                        //Si existe, pero este usuario no lo posee

                        existentCreature.getOwner().add(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        save(existentCreature);
                    }
                } else {
                    creature.getOwner().add(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    save(creature);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });

    }
    private void save(Creature creature){
        databaseReference.setValue(creature).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(CreatureActivity.this, "Added to your personal list!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreatureActivity.this, "Failed to save data " + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}


