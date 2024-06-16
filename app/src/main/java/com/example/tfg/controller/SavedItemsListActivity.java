package com.example.tfg.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg.R;
import com.example.tfg.adapter.RecyclerAdapterSavedItems;
import com.example.tfg.model.Armor;
import com.example.tfg.model.Background;
import com.example.tfg.model.Character;
import com.example.tfg.model.CharacterClass;
import com.example.tfg.model.Creature;
import com.example.tfg.model.MagicItem;
import com.example.tfg.model.Race;
import com.example.tfg.model.Spell;
import com.example.tfg.model.Weapon;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SavedItemsListActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rvCharacterList;
    RecyclerAdapterSavedItems recyclerAdapterSavedItems;
    private String type;

    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_characters_list);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar = findViewById(R.id.toolbar2);
        rvCharacterList = findViewById(R.id.rvFirebaseItemList);
        rvCharacterList.setLayoutManager(new LinearLayoutManager(this));
        setSupportActionBar(toolbar);
        //Se dejará de mostrar el nombre de la app en el actionBar además de mostrar un boton de atrás.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        populateRecycler();


    }
    public void populateRecycler(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        type = getIntent().getStringExtra("TYPE");
        if (type != null) {
            switch (type) {
                case "characters":
                    databaseReference = firebaseDatabase.getReference().child("dndProject").child("characters");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<Character> characters = new ArrayList<>();
                            for (DataSnapshot data : snapshot.getChildren()) {
                                Character current = data.getValue(Character.class);
                                current.setFirebaseKey(data.getKey());
                                if (current.getOwner().contains(currentUser.getEmail()))
                                    characters.add(current);
                            }
                            recyclerAdapterSavedItems = new RecyclerAdapterSavedItems(characters);
                            rvCharacterList.setAdapter(recyclerAdapterSavedItems);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                    break;
                case "creatures":
                    databaseReference = firebaseDatabase.getReference().child("dndProject").child("creatures");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<Creature> creatures = new ArrayList<>();
                            for (DataSnapshot data : snapshot.getChildren()) {
                                Creature currentCreature = data.getValue(Creature.class);
                                if (currentCreature.getOwner().contains(currentUser.getEmail()))
                                    creatures.add(currentCreature);
                            }
                            recyclerAdapterSavedItems = new RecyclerAdapterSavedItems(creatures);
                            rvCharacterList.setAdapter(recyclerAdapterSavedItems);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                    break;
                case "spells":
                    databaseReference = firebaseDatabase.getReference().child("dndProject").child("spells");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<Spell> spells = new ArrayList<>();
                            for (DataSnapshot data : snapshot.getChildren()) {
                                Spell currentSpell = data.getValue(Spell.class);
                                if (currentSpell.getOwner().contains(currentUser.getEmail()))
                                    spells.add(currentSpell);
                            }
                            recyclerAdapterSavedItems = new RecyclerAdapterSavedItems(spells);
                            rvCharacterList.setAdapter(recyclerAdapterSavedItems);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                    break;
                case "backgrounds":
                    databaseReference = firebaseDatabase.getReference().child("dndProject").child("backgrounds");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<Background> backgrounds = new ArrayList<>();
                            for (DataSnapshot data : snapshot.getChildren()) {
                                Background currentBackground = data.getValue(Background.class);
                                if (currentBackground.getOwner().contains(currentUser.getEmail()))
                                    backgrounds.add(currentBackground);
                            }
                            recyclerAdapterSavedItems = new RecyclerAdapterSavedItems(backgrounds);
                            rvCharacterList.setAdapter(recyclerAdapterSavedItems);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                    break;
                case "races":
                    databaseReference = firebaseDatabase.getReference().child("dndProject").child("races");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<Race> races = new ArrayList<>();
                            for (DataSnapshot data : snapshot.getChildren()) {
                                Race currentRace = data.getValue(Race.class);
                                if (currentRace.getOwner().contains(currentUser.getEmail()))
                                    races.add(currentRace);
                            }
                            recyclerAdapterSavedItems = new RecyclerAdapterSavedItems(races);
                            rvCharacterList.setAdapter(recyclerAdapterSavedItems);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                    break;
                case "classes":
                    databaseReference = firebaseDatabase.getReference().child("dndProject").child("classes");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<CharacterClass> cClasses = new ArrayList<>();
                            for (DataSnapshot data : snapshot.getChildren()) {
                                CharacterClass currentcClass = data.getValue(CharacterClass.class);
                                if (currentcClass.getOwner().contains(currentUser.getEmail()))
                                    cClasses.add(currentcClass);
                            }
                            recyclerAdapterSavedItems = new RecyclerAdapterSavedItems(cClasses);
                            rvCharacterList.setAdapter(recyclerAdapterSavedItems);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                    break;
                case "magic items":
                    databaseReference = firebaseDatabase.getReference().child("dndProject").child("magicItems");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<MagicItem> magicItems = new ArrayList<>();
                            for (DataSnapshot data : snapshot.getChildren()) {
                                MagicItem currentMagicItem = data.getValue(MagicItem.class);
                                if (currentMagicItem.getOwner().contains(currentUser.getEmail()))
                                    magicItems.add(currentMagicItem);
                            }
                            recyclerAdapterSavedItems = new RecyclerAdapterSavedItems(magicItems);
                            rvCharacterList.setAdapter(recyclerAdapterSavedItems);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                    break;
                case "weapons":
                    databaseReference = firebaseDatabase.getReference().child("dndProject").child("weapons");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<Weapon> weapons = new ArrayList<>();
                            for (DataSnapshot data : snapshot.getChildren()) {
                                Weapon currentweapon = data.getValue(Weapon.class);
                                if (currentweapon.getOwner().contains(currentUser.getEmail()))
                                    weapons.add(currentweapon);
                            }
                            recyclerAdapterSavedItems = new RecyclerAdapterSavedItems(weapons);
                            rvCharacterList.setAdapter(recyclerAdapterSavedItems);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                    break;
                case "armors":
                    databaseReference = firebaseDatabase.getReference().child("dndProject").child("armors");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<Armor> armors = new ArrayList<>();
                            for (DataSnapshot data : snapshot.getChildren()) {
                                Armor currentArmor = data.getValue(Armor.class);
                                if (currentArmor.getOwner().contains(currentUser.getEmail()))
                                    armors.add(currentArmor);
                            }
                            recyclerAdapterSavedItems = new RecyclerAdapterSavedItems(armors);
                            rvCharacterList.setAdapter(recyclerAdapterSavedItems);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                    break;
                default:

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (type.equals("characters"))
            getMenuInflater().inflate(R.menu.character_list_menu, menu);
        return true;
    }

    //Se gestiona lo que sucede al pulsar los elementos del menu:
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.choose_this_item) {
            Intent intent = new Intent(this, ApiListActivity.class);
            intent.putExtra("URL", "/races/");
            intent.putExtra("TITLE", "Choose a race");
            intent.putExtra("ISCHARACTERCREATION", true);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            this.startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (recyclerAdapterSavedItems != null)
            populateRecycler();
    }
}