package com.example.tfg.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tfg.R;
import com.example.tfg.model.Creature;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class CreatureActivity extends AppCompatActivity {
    private TextView txName, txSizeTypeAndAlignment, txAcHpSpeed, lbStats, txStatStr,txStatDex, txStatCon, txStatInt, txStatWis, txStatCha, txSkillsSensesLanguagesCr,
            lbSpecialAbilities, txSpecialAbilities,lbActions,txActions, lbBonusActions,txBonusActions,lbReactions, txReactions,lbLegendaryActions,txLegendaryActions,lbDescription,txDescription,lbLicensing,txLicense;
    JSONObject objetoJSON;
    Creature creature;

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


        try {
            objetoJSON = new JSONObject(getIntent().getStringExtra("OBJETOJSON"));
            creature = new Creature(objetoJSON);
            //creature = (Creature) getIntent().getSerializableExtra("CREATURE");

            //key info
            txName.setText(creature.getName().toUpperCase());
            txSizeTypeAndAlignment.setText("");
            if(creature.getSize() != null && !creature.getSize().equals("")){
                txSizeTypeAndAlignment.append(creature.getSize());
            }
            if(creature.getType() != null && !creature.getType().equals("")){
                txSizeTypeAndAlignment.append(" " + creature.getType());
            }
            if(creature.getAlignment() != null && !creature.getAlignment().equals("")){
                txSizeTypeAndAlignment.append(", " + creature.getAlignment());
            }
            txAcHpSpeed.setText("Armor Class: " + creature.getArmorClass() +
                    "\nHit Points: " + creature.getHitPoints());
            Iterator<String> keys = creature.getSpeed().keys();
            txAcHpSpeed.append("\nSpeed: ");
            while(keys.hasNext()){
                String key = keys.next();
                txAcHpSpeed.append(key +" "+ creature.getSpeed().getInt(key));
                if(keys.hasNext()){
                    txAcHpSpeed.append(", ");
                }
            }

            //Ability scores
            txStatStr.setText("STR\n"+creature.getStrength());
            txStatDex.setText("DEX\n"+creature.getDexterity());
            txStatCon.setText("STR\n"+creature.getConstitution());
            txStatInt.setText("STR\n"+creature.getIntelligence());
            txStatWis.setText("STR\n"+creature.getWisdom());
            txStatCha.setText("STR\n"+creature.getCharisma());

            //Special Abilities
            txSpecialAbilities.setText("");
            if(creature.getSpecialAbilities() != null && creature.getSpecialAbilities().length() != 0){
                lbSpecialAbilities.setVisibility(View.VISIBLE);
                txSpecialAbilities.setVisibility(View.VISIBLE);
                for(int i = 0; i < creature.getSpecialAbilities().length(); i++){
                    txSpecialAbilities.append(creature.getSpecialAbilities().getJSONObject(i).getString("name"));
                    txSpecialAbilities.append("\n" + creature.getSpecialAbilities().getJSONObject(i).getString("desc") + "\n\n");
                }
            }
            //Skills
            keys = creature.getSkills().keys();
            txSkillsSensesLanguagesCr.setText("\nSkills: ");
            while (keys.hasNext()){
                String key = keys.next();
                txSkillsSensesLanguagesCr.append(key + " " + creature.getSkills().getString(key));
                if(keys.hasNext()){
                    txSkillsSensesLanguagesCr.append(", ");
                }
            }
            //Senses
            if(creature.getSenses() != null && !creature.getSenses().equals("")){
                txSkillsSensesLanguagesCr.append("\nSenses: " + creature.getSenses());
            }
            //Languages
            if(creature.getLanguages() != null && !creature.getLanguages().equals("")){
                txSkillsSensesLanguagesCr.append("\nLanguages: " + creature.getLanguages());
            }

            //Challenge Rating
            if(creature.getChallengeRating() != null && !creature.getChallengeRating().equals("")){
                txSkillsSensesLanguagesCr.append("\nChallenge Rating: " + creature.getChallengeRating());
            }
            //Actions
            txActions.setText("");
            if(creature.getActions() != null && creature.getActions().length() != 0){
                lbActions.setVisibility(View.VISIBLE);
                txActions.setVisibility(View.VISIBLE);
                for(int i = 0; i < creature.getActions().length(); i++){
                    txActions.append(creature.getActions().getJSONObject(i).getString("name"));
                    txActions.append("\n" + creature.getActions().getJSONObject(i).getString("desc") + "\n\n");
                }
            }
            //Bonus actions
            txBonusActions.setText("");
            if(creature.getBonusActions() != null && creature.getBonusActions().length() != 0){
                lbBonusActions.setVisibility(View.VISIBLE);
                txBonusActions.setVisibility(View.VISIBLE);
                for(int i = 0; i < creature.getBonusActions().length(); i++){
                    txBonusActions.append(creature.getBonusActions().getJSONObject(i).getString("name"));
                    txBonusActions.append("\n" + creature.getBonusActions().getJSONObject(i).getString("desc") + "\n\n");
                }
            }
            //Reactions
            txReactions.setText("");
            if(creature.getReactions() != null && creature.getReactions().length() != 0){
                lbReactions.setVisibility(View.VISIBLE);
                txReactions.setVisibility(View.VISIBLE);
                for(int i = 0; i < creature.getReactions().length(); i++){
                    txReactions.append(creature.getReactions().getJSONObject(i).getString("name"));
                    txReactions.append("\n" + creature.getReactions().getJSONObject(i).getString("desc") + "\n\n");
                }
            }


            //Legendary Actions
            txLegendaryActions.setText("");
            if(creature.getLegendaryDesc() != null && !creature.getLegendaryDesc().equals("")){
                txLegendaryActions.append(creature.getLegendaryDesc());
            }
            if(creature.getLegendaryActions() != null && creature.getLegendaryActions().length() != 0){
                lbLegendaryActions.setVisibility(View.VISIBLE);
                txLegendaryActions.setVisibility(View.VISIBLE);
                for(int i = 0; i < creature.getLegendaryActions().length(); i++){
                    txLegendaryActions.append(creature.getLegendaryActions().getJSONObject(i).getString("name"));
                    txLegendaryActions.append("\n" + creature.getLegendaryActions().getJSONObject(i).getString("desc") + "\n\n");
                }
            }

            //Description
            if(creature.getDesc() != null && !creature.getDesc().equals("")){
                lbDescription.setVisibility(View.VISIBLE);
                txDescription.setVisibility(View.VISIBLE);
                txDescription.setText(creature.getDesc());
            }


            //Licensing
            txLicense.setText("");
            if(creature.getLicenseURL() != null && !creature.getLicenseURL().equals("")){
                lbLicensing.setVisibility(View.VISIBLE);
                txLicense.setVisibility(View.VISIBLE);
                txLicense.append("License URL: " + creature.getLicenseURL());
            }
            if(creature.getDocumentURL() != null && !creature.getDocumentURL().equals("")){
                lbLicensing.setVisibility(View.VISIBLE);
                txLicense.setVisibility(View.VISIBLE);
                txLicense.append("\nLicense URL: " + creature.getDocumentURL());
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}