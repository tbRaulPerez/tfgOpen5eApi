package com.example.tfg.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg.R;
import com.example.tfg.model.CharacterClass;

import org.json.JSONException;
import org.json.JSONObject;

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
    public static Activity thisActivity;

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


        try {
            objetoJSON = new JSONObject(getIntent().getStringExtra("OBJETOJSON"));
            cClass = new CharacterClass(objetoJSON);

            txName.setText(cClass.getName());

            txMiscelanous.setText("");
            if(cClass.getHitDice() != null && !cClass.getHitDice().equals("")){
                txMiscelanous.append("Hit dice: " + cClass.getHitDice() + ".\n\n");
            }
            if(cClass.getHpAtLv1() != null && !cClass.getHpAtLv1().equals("")){
                txMiscelanous.append("Hit points at level 1: " + cClass.getHpAtLv1() + ".\n\n");
            }
            if(cClass.getHpAtHigherLvs() != null && !cClass.getHpAtHigherLvs().equals("")){
                txMiscelanous.append("Hit points at higher levels: " + cClass.getHpAtHigherLvs() + ".\n\n");
            }
            if(cClass.getEquipment() != null && !cClass.getEquipment().equals("")){
                txMiscelanous.append(cClass.getEquipment() + ".\n\n");
            }
            if(cClass.getProfSavingThrows() != null && !cClass.getProfSavingThrows().equals("")){
                txMiscelanous.append("You are proficient in the following Saving Throws: " + cClass.getProfSavingThrows() + ".\n\n");
            }
            if(cClass.getProfSkills() != null && !cClass.getProfSkills().equals("")){
                txMiscelanous.append("You have proficiency in the following Skills: " + cClass.getProfSkills() + ".\n\n");
            }
            if(cClass.getProfArmor() != null && !cClass.getProfArmor().equals("")){
                txMiscelanous.append("Proficiency with Armors: " + cClass.getProfArmor() + ".\n\n");
            }
            if(cClass.getProfWeapons() != null && !cClass.getProfWeapons().equals("")){
                txMiscelanous.append("Proficiency with Weapons: " + cClass.getProfWeapons() + ".\n\n");
            }
            if(cClass.getProfTools() != null && !cClass.getProfTools().equals("")){
                txMiscelanous.append("Proficiency with tools: " + cClass.getProfTools() + ".\n\n");
            }
            if(cClass.getSpellcastingAbility() != null && !cClass.getSpellcastingAbility().equals("")){
                txMiscelanous.append("Spellcasting ability: " + cClass.getSpellcastingAbility() + ".\n\n");
            }
            txDescription.setText("");
            if(cClass.getDesc() != null && !cClass.getDesc().equals("")){
                markwon.setMarkdown(txDescription, cClass.getDesc() + ".\n\n");
                //txDescription.append(cClass.getDesc() + ".\n\n");
            }

            txSubclasses.setText("");
            if(cClass.getArchetypes() != null && cClass.getArchetypes().length() != 0){
                String subclasses = "";
                for(int i = 0; i < cClass.getArchetypes().length(); i++){
                    subclasses = subclasses + cClass.getArchetypes().getJSONObject(i).getString("name").toUpperCase() + "\n\n";
                    subclasses = subclasses + cClass.getArchetypes().getJSONObject(i).getString("desc") + "\n\n";
                    subclasses = subclasses +"License Url: " + cClass.getArchetypes().getJSONObject(i).getString("document__url") + "\n\n";
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
        }
        return true;
    }
    //Se gestiona lo que sucede al pulsar los elementos del menu:
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if(item.getItemId() == R.id.item_choose){
            Intent intent = new Intent(this,ChooseNameActivity.class);
            intent.putExtra("TITLE", "Choose a name");
            intent.putExtra("CHOSENRACE", chosenRaceString);
            intent.putExtra("CHOSENBACKGROUND", choosenBackgroundString);
            intent.putExtra("CHOSENCLASS", objetoJSON.toString());
            intent.putExtra("ISCHARACTERCREATION", true);
            this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}