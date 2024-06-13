package com.example.tfg.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Creature extends SavableItem{
    private String name;
    private String desc;
    private String size;
    private String type;
    private String alignment;
    private int armorClass;
    private int hitPoints;
    private String speedJsonObject;
    private int strength;
    private int dexterity;
    private int constitution;
    private int intelligence;
    private int wisdom;
    private int charisma;
    private String skillsJsonObject;
    private String vulnerabilities;
    private String resistances;
    private String inmunities;
    private String senses;
    private String challengeRating;
    private String languages;
    private String actionsJsonArray;
    private String bonusActionsJsonArray;
    private String reactionsJsonArray;
    private String legendaryDesc;
    private String legendaryActionsJsonArray;
    private String specialAbilitiesJsonArray;
    private String licenseURL;
    private String documentURL;

    public Creature() {
    }

    public Creature(JSONObject objetoJSON, ArrayList<String> owner) {
        super(owner);
        try {
            this.name = objetoJSON.getString("name");
            if (objetoJSON.has("desc") && !objetoJSON.isNull("desc")) {
                this.desc = objetoJSON.getString("desc");
            }
            if (objetoJSON.has("size") && !objetoJSON.isNull("size")) {
                this.size = objetoJSON.getString("size");
            }
            if (objetoJSON.has("type") && !objetoJSON.isNull("type")) {
                this.type = objetoJSON.getString("type");
            }
            if (objetoJSON.has("alignment") && !objetoJSON.isNull("alignment")) {
                this.alignment = objetoJSON.getString("alignment");
            }
            if (objetoJSON.has("armor_class") && !objetoJSON.isNull("armor_class")) {
                this.armorClass = objetoJSON.getInt("armor_class");
            } else this.armorClass = 0;
            if (objetoJSON.has("hit_points") && !objetoJSON.isNull("hit_points")) {
                this.hitPoints = objetoJSON.getInt("hit_points");
            } else this.hitPoints = 0;
            if (objetoJSON.has("speed") && !objetoJSON.isNull("speed")) {
                this.speedJsonObject = objetoJSON.getString("speed");
            } else {
                this.speedJsonObject = "\"speed\": {\n" +
                        "                \"walk\": 30,\n" +
                        "            }";
            }
            this.strength = objetoJSON.getInt("strength");
            this.dexterity = objetoJSON.getInt("dexterity");
            this.constitution = objetoJSON.getInt("constitution");
            this.intelligence = objetoJSON.getInt("intelligence");
            this.wisdom = objetoJSON.getInt("wisdom");
            this.charisma = objetoJSON.getInt("charisma");
            if (objetoJSON.has("skills") && !objetoJSON.isNull("skills")) {
                this.skillsJsonObject = objetoJSON.getString("skills");
            } else this.skillsJsonObject = "";
            if (objetoJSON.has("senses") && !objetoJSON.isNull("senses")) {
                this.senses = objetoJSON.getString("senses");
            }
            if (objetoJSON.has("challenge_rating") && !objetoJSON.isNull("challenge_rating")) {
                this.challengeRating = objetoJSON.getString("challenge_rating");
            }
            if (objetoJSON.has("languages") && !objetoJSON.isNull("languages")) {
                this.languages = objetoJSON.getString("languages");
            }
            if (objetoJSON.has("actions") && !objetoJSON.isNull("actions")) {
                this.actionsJsonArray = objetoJSON.getString("actions");
            }
            if (objetoJSON.has("bonus_actions") && !objetoJSON.isNull("bonus_actions")) {
                this.bonusActionsJsonArray = objetoJSON.getString("bonus_actions");
            }
            if (objetoJSON.has("reactions") && !objetoJSON.isNull("reactions")) {
                this.bonusActionsJsonArray = objetoJSON.getString("reactions");
            }
            if (objetoJSON.has("legendary_desc") && !objetoJSON.isNull("legendary_desc")) {
                this.legendaryDesc = objetoJSON.getString("legendary_desc");
            } else this.legendaryDesc = "";
            if (objetoJSON.has("legendary_actions") && !objetoJSON.isNull("legendary_actions")) {
                this.legendaryActionsJsonArray = objetoJSON.getString("legendary_actions");
            }
            if (objetoJSON.has("special_abilities") && !objetoJSON.isNull("special_abilities")) {
                this.specialAbilitiesJsonArray = objetoJSON.getString("special_abilities");
            }
            if (objetoJSON.has("document__license_url") && !objetoJSON.isNull("document__license_url")) {
                this.licenseURL = objetoJSON.getString("document__license_url");
            }
            if (objetoJSON.has("document__url") && !objetoJSON.isNull("document__url")) {
                this.documentURL = objetoJSON.getString("document__url");
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public int getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(int armorClass) {
        this.armorClass = armorClass;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getConstitution() {
        return constitution;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getCharisma() {
        return charisma;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public String getVulnerabilities() {
        return vulnerabilities;
    }

    public void setVulnerabilities(String vulnerabilities) {
        this.vulnerabilities = vulnerabilities;
    }

    public String getResistances() {
        return resistances;
    }

    public void setResistances(String resistances) {
        this.resistances = resistances;
    }

    public String getInmunities() {
        return inmunities;
    }

    public void setInmunities(String inmunities) {
        this.inmunities = inmunities;
    }

    public String getSenses() {
        return senses;
    }

    public void setSenses(String senses) {
        this.senses = senses;
    }

    public String getChallengeRating() {
        return challengeRating;
    }

    public void setChallengeRating(String challengeRating) {
        this.challengeRating = challengeRating;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getSpeedJsonObject() {
        return speedJsonObject;
    }

    public void setSpeedJsonObject(String speedJsonObject) {
        this.speedJsonObject = speedJsonObject;
    }

    public String getSkillsJsonObject() {
        return skillsJsonObject;
    }

    public void setSkillsJsonObject(String skillsJsonObject) {
        this.skillsJsonObject = skillsJsonObject;
    }

    public String getActionsJsonArray() {
        return actionsJsonArray;
    }

    public void setActionsJsonArray(String actionsJsonArray) {
        this.actionsJsonArray = actionsJsonArray;
    }

    public String getBonusActionsJsonArray() {
        return bonusActionsJsonArray;
    }

    public void setBonusActionsJsonArray(String bonusActionsJsonArray) {
        this.bonusActionsJsonArray = bonusActionsJsonArray;
    }

    public String getReactionsJsonArray() {
        return reactionsJsonArray;
    }

    public void setReactionsJsonArray(String reactionsJsonArray) {
        this.reactionsJsonArray = reactionsJsonArray;
    }

    public String getLegendaryDesc() {
        return legendaryDesc;
    }

    public void setLegendaryDesc(String legendaryDesc) {
        this.legendaryDesc = legendaryDesc;
    }

    public String getLegendaryActionsJsonArray() {
        return legendaryActionsJsonArray;
    }

    public void setLegendaryActionsJsonArray(String legendaryActionsJsonArray) {
        this.legendaryActionsJsonArray = legendaryActionsJsonArray;
    }

    public String getSpecialAbilitiesJsonArray() {
        return specialAbilitiesJsonArray;
    }

    public void setSpecialAbilitiesJsonArray(String specialAbilitiesJsonArray) {
        this.specialAbilitiesJsonArray = specialAbilitiesJsonArray;
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

    @Override
    public String toString() {
        return "Creature{" +
                "owner='" + getOwner() + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", size='" + size + '\'' +
                ", type='" + type + '\'' +
                ", alignment='" + alignment + '\'' +
                ", armorClass=" + armorClass +
                ", hitPoints=" + hitPoints +
                ", speedJsonObject='" + speedJsonObject + '\'' +
                ", strength=" + strength +
                ", dexterity=" + dexterity +
                ", constitution=" + constitution +
                ", intelligence=" + intelligence +
                ", wisdom=" + wisdom +
                ", charisma=" + charisma +
                ", skillsJsonObject='" + skillsJsonObject + '\'' +
                ", vulnerabilities='" + vulnerabilities + '\'' +
                ", resistances='" + resistances + '\'' +
                ", inmunities='" + inmunities + '\'' +
                ", senses='" + senses + '\'' +
                ", challengeRating='" + challengeRating + '\'' +
                ", languages='" + languages + '\'' +
                ", actionsJsonArray='" + actionsJsonArray + '\'' +
                ", bonusActionsJsonArray='" + bonusActionsJsonArray + '\'' +
                ", reactionsJsonArray='" + reactionsJsonArray + '\'' +
                ", legendaryDesc='" + legendaryDesc + '\'' +
                ", legendaryActionsJsonArray='" + legendaryActionsJsonArray + '\'' +
                ", specialAbilitiesJsonArray='" + specialAbilitiesJsonArray + '\'' +
                ", licenseURL='" + licenseURL + '\'' +
                ", documentURL='" + documentURL + '\'' +
                '}';
    }
}