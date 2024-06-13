package com.example.tfg.controller;

import static com.firebase.ui.auth.data.model.User.getUser;
import static com.google.firebase.auth.AuthKt.auth;
import static com.google.firebase.auth.AuthKt.getAuth;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
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
import com.example.tfg.model.Background;
import com.example.tfg.model.Character;
import com.example.tfg.model.CharacterClass;
import com.example.tfg.model.Race;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Objects;

import io.noties.markwon.Markwon;

public class CharacterActivity extends AppCompatActivity {
    TabLayout tabLayout;
    TabItem tabItemSheet;
    TabItem tabItemClass;
    TabItem tabItemRace;
    LinearLayout linearLayoutSheet;
    ScrollView scrollView, scrollViewClass, scrollViewRace;
    TabItem tabItemInventory;
    TabItem tabItemSpells;
    EditText edtHitpoints, edtArmorClass, edtStr, edtDex, edtCon, edtInt, edtWis, edtCha;
    TextView txName, lbHitPoints, txMaxHitPoints, lbArmorClass, txValues, lbLevel, txLevel, lbStats, txStatStr, txStatDex, txStatCon, txStatInt, txStatWis, txStatCha,
            lbTraits, txTraits, txClassName, txValuesClassChar, lbClassTraitsChar, txDescriptionClassChar, lbSubclassesChar,
            txSubclassesChar, lbLicenseClass, txlicenseClass, txNameRaceChar, lbDescriptionRaceChar, txValuesRaceChar,
            lbRaceTraitsChar, txRaceTraitsChar, lbSubracesChar, txSubracesChar, lbLicenseRaceChar, txLicenseRaceChar;
    CheckBox cbAcrobatics, cbAnimalHandling, cbArcana, cbAthletics, cbDeception, cbHistory, cbInsight, cbIntimidation,
            cbInvestigation, cbMedicine, cbNature, cbPerception, cbPerformance, cbPersuasion, cbReligion, cbSleightOfHand,
            cbStealth, cbSurvival;
    Spinner spLevel;
    private Toolbar toolbar;

    int prof;
    Character character;
    Race race;
    Background background;
    CharacterClass cClass;
    Markwon markwon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_character);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        toolbar = findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
        //Se dejará de mostrar el nombre de la app en el actionBar además de mostrar un boton de atrás.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        character = (Character) getIntent().getSerializableExtra("OBJECTFROMFIREBASE");
        markwon = Markwon.create(this);
        tabLayout = findViewById(R.id.CharacterTabs);
        tabItemSheet = findViewById(R.id.TabItemSheet);
        tabItemRace = findViewById(R.id.TabItemRace);
        tabItemClass = findViewById(R.id.TabItemClass);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (Objects.requireNonNull(tab.getContentDescription()).toString()) {
                    case "Sheet":
                        scrollView.setVisibility(View.VISIBLE);
                        scrollViewClass.setVisibility(View.GONE);
                        scrollViewRace.setVisibility(View.GONE);
                        break;
                    case "Class":
                        scrollView.setVisibility(View.GONE);
                        scrollViewClass.setVisibility(View.VISIBLE);
                        scrollViewRace.setVisibility(View.GONE);
                        break;
                    case "Race":
                        scrollView.setVisibility(View.GONE);
                        scrollViewClass.setVisibility(View.GONE);
                        scrollViewRace.setVisibility(View.VISIBLE);
                        break;
                    case "Inventory":
                        scrollView.setVisibility(View.GONE);
                        scrollViewClass.setVisibility(View.GONE);
                        scrollViewRace.setVisibility(View.GONE);
                        break;
                    case "Spells":
                        scrollView.setVisibility(View.GONE);
                        scrollViewClass.setVisibility(View.GONE);
                        scrollViewRace.setVisibility(View.GONE);
                        break;
                    default:
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

        });
        //SHEET
        linearLayoutSheet = findViewById(R.id.linearLayoutSheet);
        scrollView = findViewById(R.id.scrollVeiw);
        txName = findViewById(R.id.txName);
        txValues = findViewById(R.id.txValues);
        lbHitPoints = findViewById(R.id.lbHitPoints);
        edtHitpoints = findViewById(R.id.edtHitPoints);
        txMaxHitPoints = findViewById(R.id.txMaxHitPoints);
        lbArmorClass = findViewById(R.id.lbArmorClass);
        edtArmorClass = findViewById(R.id.edtArmorClass);
        lbLevel = findViewById(R.id.lbLevel);
        txLevel = findViewById(R.id.txLevel);
        spLevel = findViewById(R.id.spLevel);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.levels,
                android.R.layout.simple_spinner_item
        );
        // Specify the layout to use when the list of choices appears.
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        spLevel.setAdapter(spinnerAdapter);
        spLevel.setSelection(character.getLevel() - 1, false);
        lbStats = findViewById(R.id.lbStats);
        txStatStr = findViewById(R.id.txStatStr);
        txStatDex = findViewById(R.id.txStatDex);
        txStatCon = findViewById(R.id.txStatCon);
        txStatInt = findViewById(R.id.txStatInt);
        txStatWis = findViewById(R.id.txStatWis);
        txStatCha = findViewById(R.id.txStatCha);
        edtStr = findViewById(R.id.edtStr);
        edtDex = findViewById(R.id.edtDex);
        edtCon = findViewById(R.id.edtCon);
        edtInt = findViewById(R.id.edtInt);
        edtWis = findViewById(R.id.edtWis);
        edtCha = findViewById(R.id.edtCha);
        cbAcrobatics = findViewById(R.id.cbAcrobatics);
        cbAnimalHandling = findViewById(R.id.cbAnimalHandling);
        cbArcana = findViewById(R.id.cbArcana);
        cbAthletics = findViewById(R.id.cbAthletics);
        cbDeception = findViewById(R.id.cbDeception);
        cbHistory = findViewById(R.id.cbHistory);
        cbInsight = findViewById(R.id.cbInsight);
        cbIntimidation = findViewById(R.id.cbIntimidation);
        cbInvestigation = findViewById(R.id.cbInvestigation);
        cbMedicine = findViewById(R.id.cbMedicine);
        cbNature = findViewById(R.id.cbNature);
        cbPerception = findViewById(R.id.cbPerception);
        cbPerformance = findViewById(R.id.cbPerformance);
        cbPersuasion = findViewById(R.id.cbPersuasion);
        cbReligion = findViewById(R.id.cbReligion);
        cbSleightOfHand = findViewById(R.id.cbSleightOfHand);
        cbStealth = findViewById(R.id.cbStealth);
        cbSurvival = findViewById(R.id.cbSurvival);
        lbTraits = findViewById(R.id.lbTraits);
        txTraits = findViewById(R.id.txTraits);
        scrollView.setVisibility(View.VISIBLE);
        //CLASS
        scrollViewClass = findViewById(R.id.scrollViewClass);
        scrollViewClass.setVisibility(View.GONE);
        txClassName = findViewById(R.id.txClassName);
        txValuesClassChar = findViewById(R.id.txValuesClassChar);
        lbClassTraitsChar = findViewById(R.id.lbClassTraitsChar);
        txDescriptionClassChar = findViewById(R.id.txDescriptionClassChar);
        lbSubclassesChar = findViewById(R.id.lbSubclassesChar);
        txSubclassesChar = findViewById(R.id.txSubclassesChar);
        lbLicenseClass = findViewById(R.id.lbLicenseClassChar);
        txlicenseClass = findViewById(R.id.txLicenseClassChar);
        //RACE
        scrollViewRace = findViewById(R.id.scrollViewRace);
        scrollViewRace.setVisibility(View.GONE);
        txNameRaceChar = findViewById(R.id.txNameRaceChar);
        lbDescriptionRaceChar = findViewById(R.id.lbDescriptionRaceChar);
        txValuesRaceChar = findViewById(R.id.txValuesRaceChar);
        lbRaceTraitsChar = findViewById(R.id.lbRaceTraitsChar);
        txRaceTraitsChar = findViewById(R.id.txRaceTraitsChar);
        lbSubracesChar = findViewById(R.id.lbSubracesChar);
        txSubracesChar = findViewById(R.id.txSubracesChar);
        lbLicenseRaceChar = findViewById(R.id.lbLicenseClassChar);
        txLicenseRaceChar = findViewById(R.id.txLicenseRaceChar);
        populateCharacterSheet();
        populateClassSheet();
        populateRaceSheet();

        View.OnKeyListener onKeyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    editTextChaged(v);
                    return true;
                }
                return false;
            }
        };
        edtHitpoints.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    editTextChaged(v);
            }
        });
        edtArmorClass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    editTextChaged(v);
            }
        });

        edtStr.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    editTextChaged(v);
            }
        });
        edtStr.setOnKeyListener(onKeyListener);
        edtDex.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    editTextChaged(v);
            }
        });
        edtDex.setOnKeyListener(onKeyListener);
        edtCon.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    editTextChaged(v);
            }
        });
        edtCon.setOnKeyListener(onKeyListener);
        edtInt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    editTextChaged(v);
            }
        });
        edtInt.setOnKeyListener(onKeyListener);
        edtWis.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    editTextChaged(v);
            }
        });
        edtWis.setOnKeyListener(onKeyListener);
        edtCha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    editTextChaged(v);
            }
        });
        edtCha.setOnKeyListener(onKeyListener);
        spLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                character.setLevel(Integer.valueOf(spLevel.getSelectedItem().toString()));
                characterDataChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    CheckBox checkbox = (CheckBox) view;
                    new AlertDialog.Builder(CharacterActivity.this).setMessage("Do you want to change the proficiency with this skill?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    checkbox.setChecked(!checkbox.isChecked());
                                    profWithSkill();
                                    if (checkbox.equals(cbAcrobatics)) {
                                        character.setProfAcrobatics(checkbox.isChecked());
                                    } else if (checkbox.equals(cbAnimalHandling)) {
                                        character.setProfAnimalHandling(checkbox.isChecked());
                                    } else if (checkbox.equals(cbArcana)) {
                                        character.setProfArcana(checkbox.isChecked());
                                    } else if (checkbox.equals(cbAthletics)) {
                                        character.setProfAthletics(checkbox.isChecked());
                                    } else if (checkbox.equals(cbDeception)) {
                                        character.setProfDeception(checkbox.isChecked());
                                    } else if (checkbox.equals(cbHistory)) {
                                        character.setProfHistory(checkbox.isChecked());
                                    } else if (checkbox.equals(cbInsight)) {
                                        character.setProfInsight(checkbox.isChecked());
                                    } else if (checkbox.equals(cbIntimidation)) {
                                        character.setProfIntimidation(checkbox.isChecked());
                                    } else if (checkbox.equals(cbInvestigation)) {
                                        character.setProfInvestigation(checkbox.isChecked());
                                    } else if (checkbox.equals(cbMedicine)) {
                                        character.setProfMedicine(checkbox.isChecked());
                                    } else if (checkbox.equals(cbNature)) {
                                        character.setProfNature(checkbox.isChecked());
                                    } else if (checkbox.equals(cbPerception)) {
                                        character.setProfPerception(checkbox.isChecked());
                                    } else if (checkbox.equals(cbPerformance)) {
                                        character.setProfPerformance(checkbox.isChecked());
                                    } else if (checkbox.equals(cbPersuasion)) {
                                        character.setProfPersuasion(checkbox.isChecked());
                                    } else if (checkbox.equals(cbReligion)) {
                                        character.setProfReligion(checkbox.isChecked());
                                    } else if (checkbox.equals(cbSleightOfHand)) {
                                        character.setProfSleightOfHand(checkbox.isChecked());
                                    } else if (checkbox.equals(cbStealth)) {
                                        character.setProfStealth(checkbox.isChecked());
                                    } else if (checkbox.equals(cbSurvival)) {
                                        character.setProfSurvival(checkbox.isChecked());
                                    }
                                    characterDataChanged();
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                    return true;
                }
                return false;
            }
        };
        cbAcrobatics.setOnTouchListener(onTouchListener);
        cbAnimalHandling.setOnTouchListener(onTouchListener);
        cbArcana.setOnTouchListener(onTouchListener);
        cbAthletics.setOnTouchListener(onTouchListener);
        cbDeception.setOnTouchListener(onTouchListener);
        cbHistory.setOnTouchListener(onTouchListener);
        cbInsight.setOnTouchListener(onTouchListener);
        cbIntimidation.setOnTouchListener(onTouchListener);
        cbInvestigation.setOnTouchListener(onTouchListener);
        cbMedicine.setOnTouchListener(onTouchListener);
        cbNature.setOnTouchListener(onTouchListener);
        cbPerception.setOnTouchListener(onTouchListener);
        cbPerformance.setOnTouchListener(onTouchListener);
        cbPersuasion.setOnTouchListener(onTouchListener);
        cbReligion.setOnTouchListener(onTouchListener);
        cbSleightOfHand.setOnTouchListener(onTouchListener);
        cbStealth.setOnTouchListener(onTouchListener);
        cbSurvival.setOnTouchListener(onTouchListener);

    }


    private void editTextChaged(View v) {
        EditText edt = (EditText) v;
        new AlertDialog.Builder(CharacterActivity.this).setMessage("Are you sure that you want to save the last change?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (edt.equals(edtWis)) {
                            character.setWis(Integer.valueOf(edt.getText().toString()));
                        } else if (edt.equals(edtStr)) {
                            character.setStr(Integer.valueOf(edt.getText().toString()));
                        } else if (edt.equals(edtDex)) {
                            character.setDex(Integer.valueOf(edt.getText().toString()));
                        } else if (edt.equals(edtCon)) {
                            character.setCon(Integer.valueOf(edt.getText().toString()));
                        } else if (edt.equals(edtInt)) {
                            character.setInte(Integer.valueOf(edt.getText().toString()));
                        } else if (edt.equals(edtCha)) {
                            character.setCha(Integer.valueOf(edt.getText().toString()));
                        } else if (edt.equals(edtHitpoints)) {
                            character.setHitpoints(Integer.valueOf(edt.getText().toString()));
                        } else if (edt.equals(edtArmorClass)) {
                            character.setArmorClass(Integer.valueOf(edt.getText().toString()));
                        }
                        characterDataChanged();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        populateCharacterSheet();
                    }
                }).show();
    }

    private int profIfChecked(CheckBox cbox) {
        if (cbox.isChecked()) {
            return prof;
        } else return 0;
    }


    private void profWithSkill() {
        int dexMod = (int) Math.round((character.getDex() - 10) * 0.5);
        int wisMod = (int) Math.round((character.getWis() - 10) * 0.5);
        int intMod = (int) Math.round((character.getInte() - 10) * 0.5);
        int strMod = (int) Math.round((character.getStr() - 10) * 0.5);
        int chaMod = (int) Math.round((character.getCha() - 10) * 0.5);
        cbAcrobatics.setText("Acrobatics (dex) +" + (dexMod + profIfChecked(cbAcrobatics)));
        cbAnimalHandling.setText("Animal Handling (wis) +" + (wisMod + profIfChecked(cbAnimalHandling)));
        cbArcana.setText("Arcana (int) +" + (intMod + profIfChecked(cbArcana)));
        cbAthletics.setText("Athletics (str) +" + (strMod + profIfChecked(cbAthletics)));
        cbDeception.setText("Deception (cha) +" + (chaMod + profIfChecked(cbDeception)));
        cbHistory.setText("History (int) +" + (intMod + profIfChecked(cbHistory)));
        cbInsight.setText("Insight (wis) +" + (wisMod + profIfChecked(cbInsight)));
        cbIntimidation.setText("Intimidation (cha) +" + (chaMod + profIfChecked(cbIntimidation)));
        cbInvestigation.setText("Investigation (int) +" + (intMod + profIfChecked(cbInvestigation)));
        cbMedicine.setText("Medicine (wis) +" + (wisMod + profIfChecked(cbMedicine)));
        cbNature.setText("Nature (int) +" + (intMod + profIfChecked(cbNature)));
        cbPerception.setText("Perception (wis) +" + (wisMod + profIfChecked(cbPerception)));
        cbPerformance.setText("Performance (cha) +" + (chaMod + profIfChecked(cbPerformance)));
        cbPersuasion.setText("Persuasion (cha) +" + (chaMod + profIfChecked(cbPerformance)));
        cbReligion.setText("Religion (int) +" + (intMod + profIfChecked(cbReligion)));
        cbSleightOfHand.setText("Sleight Of Hand (dex) +" + (dexMod + profIfChecked(cbSleightOfHand)));
        cbStealth.setText("Stealth (dex) +" + (dexMod + profIfChecked(cbStealth)));
        cbSurvival.setText("Survival (wis) +" + (wisMod + profIfChecked(cbSurvival)));
    }

    private void characterDataChanged() {
        populateCharacterSheet();
        saveData();
    }

    private void populateCharacterSheet() {
        race = character.getRace();
        background = character.getBackground();
        cClass = character.getcClass();
        prof = (int) Math.ceil(Double.valueOf(character.getLevel()) / 4) + 1;

        txName.setText(character.getName());
        JSONObject speed = null;
        try {
            speed = new JSONObject(race.getSpeedJsonObject());
            String str = "";
            if (speed != null && !speed.equals("")) {
                Iterator<String> keys = speed.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    str = str + key + " " + speed.getString(key);
                    if (keys.hasNext()) {
                        str = str + ", ";
                    }
                }
            }
            txValues.setText("Class: " + cClass.getName() + "\nSpeed: " + str + "\nProficiency Bonus: +" + prof);
            edtHitpoints.setText("" + character.getHitpoints());

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        int hpAtHigherLevels = 0;
        if (character.getcClass().getName().equals("Barbarian")) {
            hpAtHigherLevels = 7;
        } else if (character.getcClass().getName().equals("Fighter") ||
                character.getcClass().getName().equals("Paladin") ||
                character.getcClass().getName().equals("Ranger")) {
            hpAtHigherLevels = 6;
        } else if (character.getcClass().getName().equals("Bard") ||
                character.getcClass().getName().equals("Cleric") ||
                character.getcClass().getName().equals("Druid") ||
                character.getcClass().getName().equals("Monk") ||
                character.getcClass().getName().equals("Rogue") ||
                character.getcClass().getName().equals("Warlock")) {
            hpAtHigherLevels = 5;
        } else if (character.getcClass().getName().equals("Sorcerer") ||
                character.getcClass().getName().equals("Wizard")) {
            hpAtHigherLevels = 4;
        }

        txMaxHitPoints.setText("/" + (character.getHitpointsAtLevel1() + (hpAtHigherLevels * (character.getLevel() - 1)) + (int) Math.round((character.getCon() - 10) * 0.5)));
        edtArmorClass.setText("" + character.getArmorClass());
        txLevel.setText(character.getcClass().getName());
        edtStr.setText("" + character.getStr());
        edtDex.setText("" + character.getDex());
        edtCon.setText("" + character.getCon());
        edtInt.setText("" + character.getInte());
        edtWis.setText("" + character.getWis());
        edtCha.setText("" + character.getCha());
        cbAcrobatics.setChecked(character.isProfAcrobatics());
        cbAnimalHandling.setChecked(character.isProfAnimalHandling());
        cbArcana.setChecked(character.isProfArcana());
        cbAthletics.setChecked(character.isProfAthletics());
        cbDeception.setChecked(character.isProfDeception());
        cbHistory.setChecked(character.isProfHistory());
        cbInsight.setChecked(character.isProfInsight());
        cbIntimidation.setChecked(character.isProfIntimidation());
        cbInvestigation.setChecked(character.isProfInvestigation());
        cbMedicine.setChecked(character.isProfMedicine());
        cbNature.setChecked(character.isProfNature());
        cbPerception.setChecked(character.isProfPerception());
        cbPerformance.setChecked(character.isProfPerformance());
        cbPersuasion.setChecked(character.isProfPersuasion());
        cbReligion.setChecked(character.isProfReligion());
        cbSleightOfHand.setChecked(character.isProfSleightOfHand());
        cbStealth.setChecked(character.isProfStealth());
        cbSurvival.setChecked(character.isProfSurvival());
        profWithSkill();

        String str = "";
        if (race.getVision() != null && !race.getVision().equals("")) {
            str = str + race.getVision() + "\n\n";
        }
        if (background.getLanguages() != null && !background.getLanguages().equals("")) {
            str = str + "Languages: " + background.getLanguages() + "\n\n";
        }
        if (cClass.getProfWeapons() != null && !cClass.getProfWeapons().equals("")) {
            str = str + "Proficiency with weapons: " + cClass.getProfWeapons() + "\n\n";
        }
        if (cClass.getProfArmor() != null && !cClass.getProfArmor().equals("")) {
            str = str + "Proficiency with armor: " + cClass.getProfArmor() + "\n\n";
        }
        if (background.getToolProficiencies() != null && !background.getToolProficiencies().equals("")) {
            str = str + "Proficiency with tools: " + background.getToolProficiencies() + "\n\n";
        }
        markwon.setMarkdown(txTraits, str);
    }

    private void populateRaceSheet() {
        try {

            txNameRaceChar.setText(race.getName());
            //Description
            String str = "";
            txValuesRaceChar.setText("");
            if (race.getDesc() != null && !race.getDesc().equals("")) {
                lbDescriptionRaceChar.setVisibility(View.VISIBLE);
                txValuesRaceChar.setVisibility(View.VISIBLE);
                str = str + race.getDesc() + "\n\n";
            }
            if (race.getAge() != null && !race.getAge().equals("")) {
                lbDescriptionRaceChar.setVisibility(View.VISIBLE);
                txValuesRaceChar.setVisibility(View.VISIBLE);
                str = str + race.getAge() + "\n\n";
            }
            if (race.getSize() != null && !race.getSize().equals("")) {
                lbDescriptionRaceChar.setVisibility(View.VISIBLE);
                txValuesRaceChar.setVisibility(View.VISIBLE);
                str = str + race.getSize() + "\n\n";
            }
            markwon.setMarkdown(txValuesRaceChar, str);

            txRaceTraitsChar.setText("");
            str = "";
            JSONObject speed = new JSONObject(race.getSpeedJsonObject());
            if (speed != null && !speed.equals("")) {
                lbRaceTraitsChar.setVisibility(View.VISIBLE);
                txRaceTraitsChar.setVisibility(View.VISIBLE);
                Iterator<String> keys = speed.keys();
                str = str + "Speed: ";
                while (keys.hasNext()) {
                    String key = keys.next();
                    str = str + key + " " + speed.getString(key);
                    if (keys.hasNext()) {
                        str = str + ", ";
                    } else str = str + "\n\n";
                }
                txRaceTraitsChar.append(str);
            }
            if (race.getVision() != null && !race.getVision().equals("")) {
                lbRaceTraitsChar.setVisibility(View.VISIBLE);
                txRaceTraitsChar.setVisibility(View.VISIBLE);
                str = str + race.getVision() + "\n\n";
            }
            if (race.getTraits() != null && !race.getTraits().equals("")) {
                lbRaceTraitsChar.setVisibility(View.VISIBLE);
                txRaceTraitsChar.setVisibility(View.VISIBLE);
                str = str + race.getTraits() + "\n\n";
            }
            if (race.getLanguages() != null && !race.getLanguages().equals("")) {
                lbRaceTraitsChar.setVisibility(View.VISIBLE);
                txRaceTraitsChar.setVisibility(View.VISIBLE);
                str = str + race.getLanguages() + "\n\n";
            }
            if (race.getAsi_desc() != null && !race.getAsi_desc().equals("")) {
                lbRaceTraitsChar.setVisibility(View.VISIBLE);
                txRaceTraitsChar.setVisibility(View.VISIBLE);
                str = str + race.getAsi_desc() + "\n\n";
            }
            markwon.setMarkdown(txRaceTraitsChar, str);

            txLicenseRaceChar.setText("");
            if (race.getDocument__license_url() != null && !race.getDocument__license_url().equals("")) {
                lbLicenseRaceChar.setVisibility(View.VISIBLE);
                txLicenseRaceChar.setVisibility(View.VISIBLE);
                txLicenseRaceChar.append("License URL: " + race.getDocument__license_url());
            }
            if (race.getDocument__url() != null && !race.getDocument__url().equals("")) {
                lbLicenseRaceChar.setVisibility(View.VISIBLE);
                txLicenseRaceChar.setVisibility(View.VISIBLE);
                txLicenseRaceChar.append("\nDocument URL: " + race.getDocument__url());
            }


            txSubracesChar.setText("");
            str = "";
            JSONArray subraces = new JSONArray(race.getSubracesJsonArray());
            if (subraces != null && subraces.length() != 0) {
                lbSubracesChar.setVisibility(View.VISIBLE);
                txSubracesChar.setVisibility(View.VISIBLE);
                for (int i = 0; i < subraces.length(); i++) {
                    str = str + subraces.getJSONObject(i).getString("name").toUpperCase() + "\n\n";
                    str = str + subraces.getJSONObject(i).getString("desc") + "\n\n";
                    str = str + subraces.getJSONObject(i).getString("traits") + "\n\n";
                    str = str + subraces.getJSONObject(i).getString("asi_desc") + "\n\n";
                }
                markwon.setMarkdown(txSubracesChar, str);
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void populateClassSheet() {
        try {

            txClassName.setText(cClass.getName());

            txValuesClassChar.setText("");
            String str = "";
            if (cClass.getHitDice() != null && !cClass.getHitDice().equals("")) {
                str = str + "Hit dice: " + cClass.getHitDice() + ".\n\n";
            }
            if (cClass.getHpAtLv1() != null && !cClass.getHpAtLv1().equals("")) {
                str = str + "Hit points at level 1: " + cClass.getHpAtLv1() + ".\n\n";
            }
            if (cClass.getHpAtHigherLvs() != null && !cClass.getHpAtHigherLvs().equals("")) {
                str = str + "Hit points at higher levels: " + cClass.getHpAtHigherLvs() + ".\n\n";
            }
            if (cClass.getEquipment() != null && !cClass.getEquipment().equals("")) {
                str = str + cClass.getEquipment() + "\n\n";
            }
            if (cClass.getProfSavingThrows() != null && !cClass.getProfSavingThrows().equals("")) {
                str = str + "You are proficient in the following Saving Throws: " + cClass.getProfSavingThrows() + ".\n\n";
            }
            if (cClass.getProfSkills() != null && !cClass.getProfSkills().equals("")) {
                str = str + "You have proficiency in the following Skills: " + cClass.getProfSkills() + ".\n\n";
            }
            if (cClass.getProfArmor() != null && !cClass.getProfArmor().equals("")) {
                str = str + "Proficiency with Armors: " + cClass.getProfArmor() + ".\n\n";
            }
            if (cClass.getProfWeapons() != null && !cClass.getProfWeapons().equals("")) {
                str = str + "Proficiency with Weapons: " + cClass.getProfWeapons() + ".\n\n";
            }
            if (cClass.getProfTools() != null && !cClass.getProfTools().equals("")) {
                str = str + "Proficiency with tools: " + cClass.getProfTools() + ".\n\n";
            }
            if (cClass.getSpellcastingAbility() != null && !cClass.getSpellcastingAbility().equals("")) {
                str = str + "Spellcasting ability: " + cClass.getSpellcastingAbility() + ".\n\n";
            }
            markwon.setMarkdown(txValuesClassChar, str);
            txDescriptionClassChar.setText("");
            if (cClass.getDesc() != null && !cClass.getDesc().equals("")) {
                markwon.setMarkdown(txDescriptionClassChar, cClass.getDesc() + ".\n\n");
                //txDescription.append(cClass.getDesc() + ".\n\n");
            }


            txSubclassesChar.setText("");
            if (cClass.getArchetypesJsonArray() != null && cClass.getArchetypesJsonArray().length() != 0) {
                JSONArray archetypes = new JSONArray(cClass.getArchetypesJsonArray());
                String subclasses = "";
                for (int i = 0; i < archetypes.length(); i++) {
                    subclasses = subclasses + archetypes.getJSONObject(i).getString("name").toUpperCase() + "\n\n";
                    subclasses = subclasses + archetypes.getJSONObject(i).getString("desc") + "\n\n";
                    subclasses = subclasses + "License Url: " + archetypes.getJSONObject(i).getString("document__url") + "\n\n";
                    subclasses = subclasses + "________________________________________\n\n";
                }
                markwon.setMarkdown(txSubclassesChar, subclasses);
            }
            if (cClass.getLicenseURL() != null && !cClass.getLicenseURL().equals("")) {
                lbLicenseClass.setVisibility(View.VISIBLE);
                txlicenseClass.setVisibility(View.VISIBLE);
                txlicenseClass.append("License URL: " + cClass.getLicenseURL());
            }
            if (cClass.getDocumentURL() != null && !cClass.getDocumentURL().equals("")) {
                lbLicenseClass.setVisibility(View.VISIBLE);
                txlicenseClass.setVisibility(View.VISIBLE);
                txlicenseClass.append("\nDocument URL: " + cClass.getDocumentURL());
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveData() {
        // below line is used to get the
        // instance of our FIrebase database.
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("dndProject").child("characters").child(character.getFirebaseKey());

        databaseReference.setValue(character).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(CharacterActivity.this, "Saved new data", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CharacterActivity.this, "Data saving failed" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.character_detail_screen_menu, menu);
        return true;
    }

    //Se gestiona lo que sucede al pulsar los elementos del menu:
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if(item.getItemId() == R.id.btMenuShare){
            EditText edt = new EditText(this);
            edt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            edt.setHint("Email");
            edt.setPaddingRelative(15,15,15,15);
            edt.isFocused();
            // below line is used to get the
            // instance of our FIrebase database.
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            // below line is used to get reference for our database.
            DatabaseReference databaseReference = firebaseDatabase.getReference().child("dndProject").child("characters").child(character.getFirebaseKey());
            new AlertDialog.Builder(this).setTitle("Type the email of the user to share this character").setView(edt).setPositiveButton("Share", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(!edt.getText().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        character.getOwner().add(edt.getText().toString());
                        databaseReference.setValue(character);
                        Toast.makeText(CharacterActivity.this, "Character shared with user " + edt.getText().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
            return true;
        }else if(item.getItemId() == R.id.btMenuDelete){
            new AlertDialog.Builder(this).setTitle("Are you sure that you want to delete this character. This is permanent").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(character.getOwner().contains(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        character.getOwner().remove(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        // below line is used to get the
                        // instance of our FIrebase database.
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        // below line is used to get reference for our database.
                        DatabaseReference databaseReference = firebaseDatabase.getReference().child("dndProject").child("characters").child(character.getFirebaseKey());
                        databaseReference.setValue(character);
                        if(character.getOwner().isEmpty()){
                            databaseReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(CharacterActivity.this, "Your character was deleted", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CharacterActivity.this, "Data deletion failed" + e, Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(CharacterActivity.this, "Your character was deleted, but other people still have access", Toast.LENGTH_LONG).show();
                        }
                        CharacterActivity.this.finish();
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
}