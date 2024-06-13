package com.example.tfg.controller;

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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg.R;
import com.example.tfg.model.Armor;
import com.example.tfg.model.Creature;
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

public class ArmorActivity extends AppCompatActivity {
    private TextView txName, txDescription, lbLicense, txLicense;
    private JSONObject objetoJSON;
    private Armor armor;
    private Toolbar toolbar;
    private String title;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

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
                armor = new Armor(objetoJSON, new ArrayList<>());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        } else if (getIntent().getSerializableExtra("OBJECTFROMFIREBASE") != null) {
            armor = (Armor) getIntent().getSerializableExtra("OBJECTFROMFIREBASE");
        }


        txName.setText(armor.getName());
        txDescription.setText("");
        if (armor.getCategory() != null && !armor.getCategory().equals("")) {
            txDescription.append("Category: " + armor.getCategory() + "\n\n");
        }
        if (armor.getAcString() != null && !armor.getAcString().equals("")) {
            txDescription.append("Armor class: " + armor.getAcString() + "\n\n");
        }
        if (armor.getStrengthRequirement() != 0) {
            txDescription.append("Strength requirement: " + armor.getStrengthRequirement() + "\n\n");
        }
        if (armor.getCost() != null && !armor.getCost().equals("")) {
            txDescription.append("Cost: " + armor.getCost() + "\n\n");
        }
        if (armor.getStealthDisadvange() != null && !armor.getStealthDisadvange()) {
            txDescription.append("Stealth checks made with this armor are made with disadvange");
        }
        if (armor.getLicenseURL() != null && !armor.getLicenseURL().equals("")) {
            lbLicense.setVisibility(View.VISIBLE);
            txLicense.setVisibility(View.VISIBLE);
            txLicense.append("License URL: " + armor.getLicenseURL());
        }
        if (armor.getDocumentURL() != null && !armor.getDocumentURL().equals("")) {
            lbLicense.setVisibility(View.VISIBLE);
            txLicense.setVisibility(View.VISIBLE);
            txLicense.append("\nDocument URL: " + armor.getDocumentURL());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (title != null) {
            getMenuInflater().inflate(R.menu.creating_character_menu, menu);
        } else if(armor.getOwner().contains(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
            getMenuInflater().inflate(R.menu.saved_item_menu, menu);
        } else {
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
            saveData(armor);
        }else if(item.getItemId() == R.id.btDeleteSavedItemMenu){
            new AlertDialog.Builder(this).setTitle("Are you sure that you want to delete this character. This is permanent").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(armor.getOwner().contains(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        armor.getOwner().remove(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        // below line is used to get the
                        // instance of our FIrebase database.
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        // below line is used to get reference for our database.
                        DatabaseReference databaseReference = firebaseDatabase.getReference().child("dndProject").child("armors").child(armor.getName());
                        databaseReference.setValue(armor);
                        if(armor.getOwner().isEmpty()){
                            databaseReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(ArmorActivity.this, "Your armor was deleted", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ArmorActivity.this, "Data deletion failed" + e, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        ArmorActivity.this.finish();
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

    private void saveData(Armor armor) {
        // below line is used to get the
        // instance of our FIrebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference().child("dndProject").child("armors").child(armor.getName());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Armor existentArmor = snapshot.getValue(Armor.class);
                if (existentArmor != null) {
                    if (existentArmor.getOwner().contains(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                        Toast.makeText(ArmorActivity.this, "You have already saved this item", Toast.LENGTH_SHORT).show();
                    } else {
                        existentArmor.getOwner().add(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        save(existentArmor);
                    }
                } else {
                    armor.getOwner().add(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    save(armor);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });
    }

    private void save(Armor armor) {
        databaseReference.setValue(armor).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ArmorActivity.this, "Added to your personal list!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ArmorActivity.this, "Failed to save data " + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}


