package com.example.tfg.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.tfg.model.Creature;
import com.example.tfg.model.Race;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfg.R;
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

public class RaceActivity extends AppCompatActivity {


    private TextView txName, lbDescription, txDescription, lbTraits, txTraits, lbSubraces, txSubraces, lbLicense, txLicense;
    private JSONObject objetoJSON;
    private Race race;
    private Toolbar toolbar;
    private String title;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_races);

        txName = findViewById(R.id.txName);
        lbDescription = findViewById(R.id.lbDescription);
        txDescription = findViewById(R.id.txMiscelaneous);
        lbTraits = findViewById(R.id.lbRaceTraits);
        txTraits = findViewById(R.id.txRaceTraits);
        lbSubraces = findViewById(R.id.lbSubraces);
        txSubraces = findViewById(R.id.txSubraces);
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
        Markwon markwon = Markwon.create(this);
        String jsonObjectExtra = getIntent().getStringExtra("OBJETOJSON");
        if(jsonObjectExtra != null && !jsonObjectExtra.equals("")){
            try {
                objetoJSON = new JSONObject(getIntent().getStringExtra("OBJETOJSON"));
                race = new Race(objetoJSON,new ArrayList<>());

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }else if(getIntent().getSerializableExtra("OBJECTFROMFIREBASE")!=null){
            race = (Race) getIntent().getSerializableExtra("OBJECTFROMFIREBASE");
        }
        try {

            txName.setText(race.getName());
            //Description
            String str = "";
            txDescription.setText("");
            if (race.getDesc() != null && !race.getDesc().equals("")) {
                lbDescription.setVisibility(View.VISIBLE);
                txDescription.setVisibility(View.VISIBLE);
                str = str + race.getDesc() + "\n\n";
            }
            if (race.getAge() != null && !race.getAge().equals("")) {
                lbDescription.setVisibility(View.VISIBLE);
                txDescription.setVisibility(View.VISIBLE);
                str = str + race.getAge() + "\n\n";
            }
            if (race.getSize() != null && !race.getSize().equals("")) {
                lbDescription.setVisibility(View.VISIBLE);
                txDescription.setVisibility(View.VISIBLE);
                str = str + race.getSize() + "\n\n";
            }
            markwon.setMarkdown(txDescription, str);

            txTraits.setText("");
            str = "";
            JSONObject speed = new JSONObject(race.getSpeedJsonObject());
            if (speed != null && !speed.equals("")) {
                lbTraits.setVisibility(View.VISIBLE);
                lbTraits.setVisibility(View.VISIBLE);
                Iterator<String> keys = speed.keys();
                str = str + "Speed: ";
                while (keys.hasNext()) {
                    String key = keys.next();
                    str = str + key + " " + speed.getString(key);
                    if (keys.hasNext()) {
                        str = str + ", ";
                    } else str = str + "\n\n";
                }
            }
            if (race.getVision() != null && !race.getVision().equals("")) {
                lbTraits.setVisibility(View.VISIBLE);
                lbTraits.setVisibility(View.VISIBLE);
                str = str + race.getVision() + "\n\n";
            }
            if (race.getTraits() != null && !race.getTraits().equals("")) {
                lbTraits.setVisibility(View.VISIBLE);
                txTraits.setVisibility(View.VISIBLE);
                str = str + race.getTraits() + "\n\n";
            }
            if (race.getLanguages() != null && !race.getLanguages().equals("")) {
                lbTraits.setVisibility(View.VISIBLE);
                lbTraits.setVisibility(View.VISIBLE);
                str = str + race.getLanguages() + "\n\n";
            }
            if (race.getAsi_desc() != null && !race.getAsi_desc().equals("")) {
                lbTraits.setVisibility(View.VISIBLE);
                lbTraits.setVisibility(View.VISIBLE);
                str = str + race.getAsi_desc() + "\n\n";
            }
            markwon.setMarkdown(txTraits, str);

            txLicense.setText("");
            if (race.getDocument__license_url() != null && !race.getDocument__license_url().equals("")) {
                lbLicense.setVisibility(View.VISIBLE);
                txLicense.setVisibility(View.VISIBLE);
                txLicense.append("License URL: " + race.getDocument__license_url());
            }
            if (race.getDocument__url() != null && !race.getDocument__url().equals("")) {
                lbLicense.setVisibility(View.VISIBLE);
                txLicense.setVisibility(View.VISIBLE);
                txLicense.append("\nDocument URL: " + race.getDocument__url());
            }


            txSubraces.setText("");
            str = "";
            JSONArray subraces = new JSONArray(race.getSubracesJsonArray());
            if (subraces != null && subraces.length() != 0) {
                lbSubraces.setVisibility(View.VISIBLE);
                txSubraces.setVisibility(View.VISIBLE);
                for (int i = 0; i < subraces.length(); i++) {
                    str = str + subraces.getJSONObject(i).getString("name").toUpperCase() + "\n\n";
                    str = str + subraces.getJSONObject(i).getString("desc") + "\n\n";
                    str = str + subraces.getJSONObject(i).getString("traits") + "\n\n";
                    str = str + subraces.getJSONObject(i).getString("asi_desc") + "\n\n";
                }
                markwon.setMarkdown(txSubraces, str);
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (title != null) {
            getMenuInflater().inflate(R.menu.creating_character_menu, menu);
        } else if(race.getOwner().contains(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
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
            saveData(race);
        } else if (item.getItemId() == R.id.choose_this_item) {
            Intent intent = new Intent(this, ApiListActivity.class);
            intent.putExtra("URL", "/backgrounds/");
            intent.putExtra("TITLE", "Choose a background");
            intent.putExtra("CHOSENRACE", objetoJSON.toString());
            intent.putExtra("ISCHARACTERCREATION", true);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            this.startActivity(intent);
            return true;
        }else if(item.getItemId() == R.id.btDeleteSavedItemMenu){
            new AlertDialog.Builder(this).setTitle("Are you sure that you want to delete this character. This is permanent").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(race.getOwner().contains(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        race.getOwner().remove(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        // below line is used to get the
                        // instance of our FIrebase database.
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        // below line is used to get reference for our database.
                        DatabaseReference databaseReference = firebaseDatabase.getReference().child("dndProject").child("races").child(race.getName());
                        databaseReference.setValue(race);
                        if(race.getOwner().isEmpty()){
                            databaseReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(RaceActivity.this, "Your race was deleted", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RaceActivity.this, "Data deletion failed" + e, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        RaceActivity.this.finish();
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

    private void saveData(Race race) {
        // below line is used to get the
        // instance of our FIrebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference().child("dndProject").child("races").child(race.getName());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Race existentRace = snapshot.getValue(Race.class);
                if (existentRace != null) {
                    //si el objeto existe
                    if (existentRace.getOwner().contains(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        Toast.makeText(RaceActivity.this, "You have already saved this item", Toast.LENGTH_SHORT).show();
                    }else {
                        //Si existe, pero este usuario no lo posee

                        existentRace.getOwner().add(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        save(existentRace);
                    }
                } else {
                    //Si no existe, crea el objeto
                    race.getOwner().add(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    save(race);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });
    }
    private void save(Race race){
        databaseReference.setValue(race).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(RaceActivity.this, "Added to your personal list!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RaceActivity.this, "Failed to save data " + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}


