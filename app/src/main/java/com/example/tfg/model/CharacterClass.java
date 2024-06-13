package com.example.tfg.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CharacterClass extends SavableItem {
    private String name;
    private String desc;
    private String hitDice;
    private String hpAtLv1;
    private String hpAtHigherLvs;
    private String profArmor;
    private String profWeapons;
    private String profTools;
    private String profSavingThrows;
    private String profSkills;
    private String equipment;
    private String table;
    private String spellcastingAbility;
    private String archetypesJsonArray;
    private String licenseURL;
    private String documentURL;

    public CharacterClass() {
    }

    public CharacterClass(JSONObject objetoJson, ArrayList<String> owner) {
        super(owner);
        try {
            if (objetoJson.has("name") && !objetoJson.isNull("name")) {
                this.name = objetoJson.getString("name");
            }
            if (objetoJson.has("desc") && !objetoJson.isNull("desc")) {
                this.desc = objetoJson.getString("desc");
            }
            if (objetoJson.has("hit_dice") && !objetoJson.isNull("hit_dice")) {
                this.hitDice = objetoJson.getString("hit_dice");
            }
            if (objetoJson.has("hp_at_1st_level") && !objetoJson.isNull("hp_at_1st_level")) {
                this.hpAtLv1 = objetoJson.getString("hp_at_1st_level");
            }
            if (objetoJson.has("hp_at_higher_levels") && !objetoJson.isNull("hp_at_higher_levels")) {
                this.hpAtHigherLvs = objetoJson.getString("hp_at_higher_levels");
            }
            if (objetoJson.has("prof_armor") && !objetoJson.isNull("prof_armor")) {
                this.profArmor = objetoJson.getString("prof_armor");
            }
            if (objetoJson.has("prof_weapons") && !objetoJson.isNull("prof_weapons")) {
                this.profWeapons = objetoJson.getString("prof_weapons");
            }
            if (objetoJson.has("prof_tools") && !objetoJson.isNull("prof_tools")) {
                this.profTools = objetoJson.getString("prof_tools");
            }
            if (objetoJson.has("prof_saving_throws") && !objetoJson.isNull("prof_saving_throws")) {
                this.profSavingThrows = objetoJson.getString("prof_saving_throws");
            }
            if (objetoJson.has("prof_skills") && !objetoJson.isNull("prof_skills")) {
                this.profSkills = objetoJson.getString("prof_skills");
            }
            if (objetoJson.has("equipment") && !objetoJson.isNull("equipment")) {
                this.equipment = objetoJson.getString("equipment");
            }
            if (objetoJson.has("table") && !objetoJson.isNull("table")) {
                this.table = objetoJson.getString("table");
            }
            if (objetoJson.has("spellcasting_ability") && !objetoJson.isNull("spellcasting_ability")) {
                this.spellcastingAbility = objetoJson.getString("spellcasting_ability");
            }
            if (objetoJson.has("archetypes") && !objetoJson.isNull("archetypes")) {
                this.archetypesJsonArray = objetoJson.getString("archetypes");
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getHitDice() {
        return hitDice;
    }

    public void setHitDice(String hitDice) {
        this.hitDice = hitDice;
    }

    public String getHpAtLv1() {
        return hpAtLv1;
    }

    public void setHpAtLv1(String hpAtLv1) {
        this.hpAtLv1 = hpAtLv1;
    }

    public String getHpAtHigherLvs() {
        return hpAtHigherLvs;
    }

    public void setHpAtHigherLvs(String hpAtHigherLvs) {
        this.hpAtHigherLvs = hpAtHigherLvs;
    }

    public String getProfArmor() {
        return profArmor;
    }

    public void setProfArmor(String profArmor) {
        this.profArmor = profArmor;
    }

    public String getProfWeapons() {
        return profWeapons;
    }

    public void setProfWeapons(String profWeapons) {
        this.profWeapons = profWeapons;
    }

    public String getProfTools() {
        return profTools;
    }

    public void setProfTools(String profTools) {
        this.profTools = profTools;
    }

    public String getProfSavingThrows() {
        return profSavingThrows;
    }

    public void setProfSavingThrows(String profSavingThrows) {
        this.profSavingThrows = profSavingThrows;
    }

    public String getProfSkills() {
        return profSkills;
    }

    public void setProfSkills(String profSkills) {
        this.profSkills = profSkills;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getSpellcastingAbility() {
        return spellcastingAbility;
    }

    public void setSpellcastingAbility(String spellcastingAbility) {
        this.spellcastingAbility = spellcastingAbility;
    }

    public String getArchetypesJsonArray() {
        return archetypesJsonArray;
    }

    public void setArchetypesJsonArray(String archetypesJsonArray) {
        this.archetypesJsonArray = archetypesJsonArray;
    }

    public String getLicenseURL() {
        return licenseURL;
    }

    public void setLicenseURL(String licenseURL) {
        this.licenseURL = licenseURL;
    }

    public String getDocumentURL() {
        return documentURL;
    }

    public void setDocumentURL(String documentURL) {
        this.documentURL = documentURL;
    }
}
