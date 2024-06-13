package com.example.tfg.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.tfg.model.Creature;
import com.example.tfg.model.Weapon;
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

public class WeaponActivity extends AppCompatActivity {
    private TextView txName, txDescription, lbLicense, txLicense;
    private JSONObject objetoJSON;
    private Weapon weapon;
    private Toolbar toolbar;
    private String title;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

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
                objetoJSON = new JSONObject(jsonObjectExtra);
                weapon = new Weapon(objetoJSON, new ArrayList<>());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        } else if (getIntent().getSerializableExtra("OBJECTFROMFIREBASE") != null) {
            weapon = (Weapon) getIntent().getSerializableExtra("OBJECTFROMFIREBASE");
        }


        txName.setText(weapon.getName());
        txDescription.setText("");
        if (weapon.getCategory() != null && !weapon.getCategory().equals("")) {
            txDescription.append("Category: " + weapon.getCategory() + ".\n\n");
        }
        if (weapon.getCost() != null && !weapon.getCost().equals("")) {
            txDescription.append("Cost: " + weapon.getCost() + ".\n\n");
        }
        if (weapon.getDmgDice() != null && !weapon.getDmgDice().equals("")) {
            txDescription.append("Damage: " + weapon.getDmgDice());
            if (weapon.getDmgType() != null && !weapon.getDmgType().equals("")) {
                txDescription.append(" of " + weapon.getDmgType() + " damage");
            }
            txDescription.append(".\n\n");
        }
        if (weapon.getProperties() != null && weapon.getProperties().size() != 0) {
            txDescription.append("Properties:");
            for (String property : weapon.getProperties()) {
                txDescription.append(" " + property + " ");
            }
        }

        if (weapon.getLicenseURL() != null && !weapon.getLicenseURL().equals("")) {
            lbLicense.setVisibility(View.VISIBLE);
            txLicense.setVisibility(View.VISIBLE);
            txLicense.append("License URL: " + weapon.getLicenseURL());
        }
        if (weapon.getDocumentURL() != null && !weapon.getDocumentURL().equals("")) {
            lbLicense.setVisibility(View.VISIBLE);
            txLicense.setVisibility(View.VISIBLE);
            txLicense.append("\nDocument URL: " + weapon.getDocumentURL());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (title != null) {
            getMenuInflater().inflate(R.menu.creating_character_menu, menu);
        } else if(weapon.getOwner().contains(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
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
            saveData(weapon);
        }else if(item.getItemId() == R.id.btDeleteSavedItemMenu){
            new AlertDialog.Builder(this).setTitle("Are you sure that you want to delete this character. This is permanent").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(weapon.getOwner().contains(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        weapon.getOwner().remove(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        // below line is used to get the
                        // instance of our FIrebase database.
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        // below line is used to get reference for our database.
                        DatabaseReference databaseReference = firebaseDatabase.getReference().child("dndProject").child("weapons").child(weapon.getName());
                        databaseReference.setValue(weapon);
                        if(weapon.getOwner().isEmpty()){
                            databaseReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(WeaponActivity.this, "Your weapon was deleted", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(WeaponActivity.this, "Data deletion failed" + e, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        WeaponActivity.this.finish();
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

    private void saveData(Weapon weapon) {
        // below line is used to get the
        // instance of our FIrebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();
        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference().child("dndProject").child("weapons").child(weapon.getName());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Weapon existentWeapon = snapshot.getValue(Weapon.class);
                if (existentWeapon != null) {
                    //si el objeto existe
                    if (existentWeapon.getOwner().contains(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                        Toast.makeText(WeaponActivity.this, "You have already saved this item", Toast.LENGTH_SHORT).show();
                    } else {
                        //Si existe, pero este usuario no lo posee
                        existentWeapon.getOwner().add(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        save(existentWeapon);
                    }
                } else {
                    //Si no existe, crea el objeto
                    weapon.getOwner().add(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    save(weapon);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });

    }

    private void save(Weapon weapon) {
        databaseReference.setValue(weapon).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(WeaponActivity.this, "Added to your personal list!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(WeaponActivity.this, "Failed to save data " + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}