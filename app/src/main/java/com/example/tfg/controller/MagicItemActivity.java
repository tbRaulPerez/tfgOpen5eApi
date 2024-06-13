package com.example.tfg.controller;

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
import com.example.tfg.model.Creature;
import com.example.tfg.model.MagicItem;
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

import io.noties.markwon.Markwon;

public class MagicItemActivity extends AppCompatActivity {
    private TextView txName, txTypeRarity, txDescription, lbLicense, txLicense;
    private JSONObject objetoJSON;
    private MagicItem magicItem;
    private Toolbar toolbar;
    private String title;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

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
        if (jsonObjectExtra != null && !jsonObjectExtra.equals("")) {
            try {
                objetoJSON = new JSONObject(getIntent().getStringExtra("OBJETOJSON"));
                magicItem = new MagicItem(objetoJSON, new ArrayList<>());

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        } else if (getIntent().getSerializableExtra("OBJECTFROMFIREBASE") != null) {
            magicItem = (MagicItem) getIntent().getSerializableExtra("OBJECTFROMFIREBASE");
        }
        txName.setText(magicItem.getName());
        txTypeRarity.setText("");
        if (magicItem.getRarity() != null && !magicItem.getRarity().equals("")) {
            txTypeRarity.append("Type: " + magicItem.getRarity());
        }
        if (magicItem.getType() != null && !magicItem.getType().equals("")) {
            txTypeRarity.append(" " + magicItem.getType() + ".");
        }
        if (magicItem.getRequiresAttunement() != null && !magicItem.getRequiresAttunement().equals("")) {
            txTypeRarity.append("\n\n" + magicItem.getRequiresAttunement());
        }
        txDescription.setText("");
        if (magicItem.getDesc() != null && !magicItem.getDesc().equals("")) {
            markwon.setMarkdown(txDescription, magicItem.getDesc());
        }
        if (magicItem.getLicenseURL() != null && !magicItem.getLicenseURL().equals("")) {
            lbLicense.setVisibility(View.VISIBLE);
            txLicense.setVisibility(View.VISIBLE);
            txLicense.append("License URL: " + magicItem.getLicenseURL());
        }
        if (magicItem.getDocumentURL() != null && !magicItem.getDocumentURL().equals("")) {
            lbLicense.setVisibility(View.VISIBLE);
            txLicense.setVisibility(View.VISIBLE);
            txLicense.append("\nDocument URL: " + magicItem.getDocumentURL());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (title != null) {
            getMenuInflater().inflate(R.menu.creating_character_menu, menu);
        } else if(magicItem.getOwner().contains(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
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
            saveData(magicItem);
        }else if(item.getItemId() == R.id.btDeleteSavedItemMenu){
            new AlertDialog.Builder(this).setTitle("Are you sure that you want to delete this character. This is permanent").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(magicItem.getOwner().contains(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        magicItem.getOwner().remove(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        // below line is used to get the
                        // instance of our FIrebase database.
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        // below line is used to get reference for our database.
                        DatabaseReference databaseReference = firebaseDatabase.getReference().child("dndProject").child("magicItems").child(magicItem.getName());
                        databaseReference.setValue(magicItem);
                        if(magicItem.getOwner().isEmpty()){
                            databaseReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(MagicItemActivity.this, "Your magic item was deleted", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MagicItemActivity.this, "Data deletion failed" + e, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        MagicItemActivity.this.finish();
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

    private void saveData(MagicItem magicItem) {
        // below line is used to get the
        // instance of our FIrebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference().child("dndProject").child("magicItems").child(magicItem.getName());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                MagicItem existentMagicItem = snapshot.getValue(MagicItem.class);
                if (existentMagicItem != null) {
                    //si el objeto existe
                    if (existentMagicItem.getOwner().contains(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                        Toast.makeText(MagicItemActivity.this, "You have already saved this item", Toast.LENGTH_SHORT).show();
                    } else {
                        //Si existe, pero este usuario no lo posee

                        existentMagicItem.getOwner().add(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        save(existentMagicItem);
                    }
                } else {
                    //Si no existe, crea el objeto
                    magicItem.getOwner().add(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    save(magicItem);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });
    }

    private void save(MagicItem magicItem) {
        databaseReference.setValue(magicItem).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(MagicItemActivity.this, "Added to your personal list!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MagicItemActivity.this, "Failed to save data " + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}


