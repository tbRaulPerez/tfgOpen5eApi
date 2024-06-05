package com.example.tfg.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class CharacterActivity extends AppCompatActivity {
    TabLayout tabLayout;
    TabItem tabItemSheet;
    TabItem tabItemClass;
    TabItem tabItemRace;
    LinearLayout linearLayoutSheet;
    ScrollView scrollView, scrollViewClass, scrollViewRace;
    TabItem tabItemInventory;
    TabItem tabItemSpells;
    TextView txName, txValues, lbLevel, txLevel, lbStats, txStatStr,txStatDex, txStatCon, txStatInt, txStatWis, txStatCha,
            lbTraits, txTraits, txClassName, txValuesClassChar, lbClassTraitsChar, txDescriptionClassChar, lbSubclassesChar,
            txSubclassesChar, lbLicenseClass,txlicenseClass, txNameRaceChar, lbDescriptionRaceChar, txValuesRaceChar,
            lbRaceTraitsChar, txRaceTraitsChar, lbSubracesChar, txSubracesChar, lbLicenseRaceChar, txLicenseRaceChar;
    CheckBox cbAcrobatics, cbAnimalHandling, cbArcana, cbAthletics,cbDeception, cbHistory, cbInsight, cbIntimidation,
            cbInvestigation, cbMedicine, cbNature, cbPerception, cbPerformance, cbPersuasion, cbReligion, cbSleightOfHand,
            cbStealth, cbSurvival;
    Spinner spLevel;

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

        tabLayout = findViewById(R.id.CharacterTabs);
        tabItemSheet = findViewById(R.id.TabItemSheet);
        tabItemRace = findViewById(R.id.TabItemRace);
        tabItemClass = findViewById(R.id.TabItemClass);
        tabItemInventory = findViewById(R.id.TabItemInventory);
        tabItemSpells = findViewById(R.id.TabItemSpells);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (Objects.requireNonNull(tab.getContentDescription()).toString()){
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
            public void onTabUnselected(TabLayout.Tab tab) {           }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {            }

        });
        //SHEET
        linearLayoutSheet = findViewById(R.id.linearLayoutSheet);
        scrollView = findViewById(R.id.scrollVeiw);
        txName = findViewById(R.id.txName);
        txValues = findViewById(R.id.txValues);
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
        lbStats = findViewById(R.id.lbStats);
        txStatStr = findViewById(R.id.txStatStr);
        txStatDex = findViewById(R.id.txStatDex);
        txStatCon = findViewById(R.id.txStatCon);
        txStatInt = findViewById(R.id.txStatInt);
        txStatWis = findViewById(R.id.txStatWis);
        txStatCha = findViewById(R.id.txStatCha);
        cbAcrobatics= findViewById(R.id.cbAcrobatics);
        cbAnimalHandling= findViewById(R.id.cbAnimalHandling);
        cbArcana= findViewById(R.id.cbArcana);
        cbAthletics= findViewById(R.id.cbAthletics);
        cbDeception= findViewById(R.id.cbDeception);
        cbHistory= findViewById(R.id.cbHistory);
        cbInsight= findViewById(R.id.cbInsight);
        cbIntimidation= findViewById(R.id.cbIntimidation);
        cbInvestigation= findViewById(R.id.cbInvestigation);
        cbMedicine= findViewById(R.id.cbMedicine);
        cbNature= findViewById(R.id.cbNature);
        cbPerception= findViewById(R.id.cbPerception);
        cbPerformance= findViewById(R.id.cbPerformance);
        cbPersuasion= findViewById(R.id.cbPersuasion);
        cbReligion= findViewById(R.id.cbReligion);
        cbSleightOfHand= findViewById(R.id.cbSleightOfHand);
        cbStealth= findViewById(R.id.cbStealth);
        cbSurvival= findViewById(R.id.cbSurvival);
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

    }
}