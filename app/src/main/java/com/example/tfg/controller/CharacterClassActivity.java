package com.example.tfg.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg.R;

import com.example.tfg.model.CharacterClass;
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

import io.noties.markwon.Markwon;

public class CharacterClassActivity extends AppCompatActivity {
    private TextView txName, txMiscelanous,txDescription,txTable, txSubclasses, lbLicense, txLicense;
    private JSONObject objetoJSON;
    private CharacterClass cClass;
    private Toolbar toolbar;
    private String title;
    private boolean isCharacterCreation;
    private String chosenRaceString;
    private String choosenBackgroundString;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_character_class);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Markwon markwon = Markwon.create(this);
        txName = findViewById(R.id.txName);
        txMiscelanous = findViewById(R.id.txMiscelaneous);
        txDescription = findViewById(R.id.txDescription);
        txTable = findViewById(R.id.txClassTable);
        txSubclasses= findViewById(R.id.txSubclasses);
        txLicense = findViewById(R.id.txLicense2);
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
                cClass = new CharacterClass(objetoJSON, new ArrayList<>());

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }else if(getIntent().getSerializableExtra("OBJECTFROMFIREBASE")!=null){
            cClass = (CharacterClass) getIntent().getSerializableExtra("OBJECTFROMFIREBASE");
        }
        try {

            txName.setText(cClass.getName());

            txMiscelanous.setText("");
            String str = "";
            if(cClass.getHitDice() != null && !cClass.getHitDice().equals("")){
                str = str + "Hit dice: " + cClass.getHitDice() + ".\n\n";
            }
            if(cClass.getHpAtLv1() != null && !cClass.getHpAtLv1().equals("")){
                str = str + "Hit points at level 1: " + cClass.getHpAtLv1() + ".\n\n";
            }
            if(cClass.getHpAtHigherLvs() != null && !cClass.getHpAtHigherLvs().equals("")){
                str = str + "Hit points at higher levels: " + cClass.getHpAtHigherLvs() + ".\n\n";
            }
            if(cClass.getEquipment() != null && !cClass.getEquipment().equals("")){
                str = str + cClass.getEquipment() + "\n\n";
            }
            if(cClass.getProfSavingThrows() != null && !cClass.getProfSavingThrows().equals("")){
                str = str + "You are proficient in the following Saving Throws: " + cClass.getProfSavingThrows() + ".\n\n";
            }
            if(cClass.getProfSkills() != null && !cClass.getProfSkills().equals("")){
                str = str + "You have proficiency in the following Skills: " + cClass.getProfSkills() + ".\n\n";
            }
            if(cClass.getProfArmor() != null && !cClass.getProfArmor().equals("")){
                str = str + "Proficiency with Armors: " + cClass.getProfArmor() + ".\n\n";
            }
            if(cClass.getProfWeapons() != null && !cClass.getProfWeapons().equals("")){
                str = str + "Proficiency with Weapons: " + cClass.getProfWeapons() + ".\n\n";
            }
            if(cClass.getProfTools() != null && !cClass.getProfTools().equals("")){
                str = str + "Proficiency with tools: " + cClass.getProfTools() + ".\n\n";
            }
            if(cClass.getSpellcastingAbility() != null && !cClass.getSpellcastingAbility().equals("")){
                str = str + "Spellcasting ability: " + cClass.getSpellcastingAbility() + ".\n\n";
            }
            markwon.setMarkdown(txMiscelanous, str);
            txDescription.setText("");
            if(cClass.getDesc() != null && !cClass.getDesc().equals("")){
                markwon.setMarkdown(txDescription, cClass.getDesc() + ".\n\n");
                //txDescription.append(cClass.getDesc() + ".\n\n");
            }


            txSubclasses.setText("");
            if(cClass.getArchetypesJsonArray() != null && cClass.getArchetypesJsonArray().length() != 0){
                JSONArray archetypes = new JSONArray(cClass.getArchetypesJsonArray());
                String subclasses = "";
                for(int i = 0; i < archetypes.length(); i++){
                    subclasses = subclasses + archetypes.getJSONObject(i).getString("name").toUpperCase() + "\n\n";
                    subclasses = subclasses + archetypes.getJSONObject(i).getString("desc") + "\n\n";
                    subclasses = subclasses +"License Url: " + archetypes.getJSONObject(i).getString("document__url") + "\n\n";
                    subclasses = subclasses +"________________________________________\n\n";
                }
                markwon.setMarkdown(txSubclasses, subclasses);
            }
            if(cClass.getLicenseURL() != null && !cClass.getLicenseURL().equals("")){
                lbLicense.setVisibility(View.VISIBLE);
                txLicense.setVisibility(View.VISIBLE);
                txLicense.append("License URL: " + cClass.getLicenseURL());
            }
            if(cClass.getDocumentURL() != null && !cClass.getDocumentURL().equals("")){
                lbLicense.setVisibility(View.VISIBLE);
                txLicense.setVisibility(View.VISIBLE);
                txLicense.append("\nDocument URL: " + cClass.getDocumentURL());
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        isCharacterCreation = getIntent().getBooleanExtra("ISCHARACTERCREATION", false);
        if(isCharacterCreation){
            chosenRaceString = getIntent().getStringExtra("CHOSENRACE");
            choosenBackgroundString = getIntent().getStringExtra("CHOSENBACKGROUND");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(title != null){
            getMenuInflater().inflate(R.menu.creating_character_menu, menu);
        }else if(cClass.getOwner().contains(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
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
            saveData(cClass);
        }else if(item.getItemId() == R.id.choose_this_item){
            Intent intent = new Intent(this,ChooseNameActivity.class);
            intent.putExtra("TITLE", "Choose a name");
            intent.putExtra("CHOSENRACE", chosenRaceString);
            intent.putExtra("CHOSENBACKGROUND", choosenBackgroundString);
            intent.putExtra("CHOSENCLASS", objetoJSON.toString());
            intent.putExtra("ISCHARACTERCREATION", true);
            this.startActivity(intent);
            return true;
        }else if(item.getItemId() == R.id.btDeleteSavedItemMenu){
            new AlertDialog.Builder(this).setTitle("Are you sure that you want to delete this character. This is permanent").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(cClass.getOwner().contains(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        cClass.getOwner().remove(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        // below line is used to get the
                        // instance of our FIrebase database.
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        // below line is used to get reference for our database.
                        DatabaseReference databaseReference = firebaseDatabase.getReference().child("dndProject").child("classes").child(cClass.getName());
                        databaseReference.setValue(cClass);
                        if(cClass.getOwner().isEmpty()){
                            databaseReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(CharacterClassActivity.this, "Your class was deleted", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CharacterClassActivity.this, "Data deletion failed" + e, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        CharacterClassActivity.this.finish();
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
    private void saveData(CharacterClass cClass) {
        // below line is used to get the
        // instance of our FIrebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference().child("dndProject").child("classes").child(cClass.getName());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CharacterClass existentCClass = snapshot.getValue(CharacterClass.class);
                if (existentCClass != null) {
                    //si el objeto existe
                    if (existentCClass.getOwner().contains(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        Toast.makeText(CharacterClassActivity.this, "You have already saved this item", Toast.LENGTH_SHORT).show();
                    }else {
                        //Si existe, pero este usuario no lo posee

                        existentCClass.getOwner().add(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        save(existentCClass);
                    }
                } else {
                    //Si no existe
                    cClass.getOwner().add(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    save(cClass);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());

            }
        });
    }
    private void save(CharacterClass cClass){
        databaseReference.setValue(cClass).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(CharacterClassActivity.this, "Added to your personal list!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CharacterClassActivity.this, "Failed to save data " + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}