package com.example.tfg.model;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Creature{
    private String name;
    private String desc;
    private String size;
    private String type;
    private String alignment;
    private int armorClass;
    private int hitPoints;
    private JSONObject speed;
    private int strength;
    private int dexterity;
    private int constitution;
    private int intelligence;
    private int wisdom;
    private int charisma;
    private JSONObject skills;
    private String vulnerabilities;
    private String resistances;
    private String inmunities;
    private String senses;
    private String challengeRating;
    private String languages;
    private JSONArray actions;
    private JSONArray bonusActions;
    private JSONArray reactions;
    private String legendaryDesc;
    private JSONArray legendaryActions;
    private JSONArray specialAbilities;
    private String licenseURL;
    private String documentURL;

    public Creature(JSONObject objetoJSON) {
        try {
            this.name = objetoJSON.getString("name");
            if(objetoJSON.has("desc") && !objetoJSON.isNull("desc")){
                this.desc = objetoJSON.getString("desc");
            }
                if(objetoJSON.has("size") && !objetoJSON.isNull("size")){
                this.size = objetoJSON.getString("size");
            }
            if(objetoJSON.has("type") && !objetoJSON.isNull("type")){
                this.type = objetoJSON.getString("type");
            }
            if(objetoJSON.has("alignment") && !objetoJSON.isNull("alignment")){
                this.alignment = objetoJSON.getString("alignment");
            }
            if(objetoJSON.has("armor_class") && !objetoJSON.isNull("armor_class")){
                this.armorClass = objetoJSON.getInt("armor_class");
            }else this.armorClass = 0;
            if(objetoJSON.has("hit_points") && !objetoJSON.isNull("hit_points")){
                this.hitPoints = objetoJSON.getInt("hit_points");
            }else this.hitPoints = 0;
            if(objetoJSON.has("speed") && !objetoJSON.isNull("speed")){
                this.speed = objetoJSON.getJSONObject("speed");
            } else{
                this.speed = new JSONObject("\"speed\": {\n" +
                        "                \"walk\": 30,\n" +
                        "            }");
            }
            this.strength = objetoJSON.getInt("strength");
            this.dexterity = objetoJSON.getInt("dexterity");
            this.constitution = objetoJSON.getInt("constitution");
            this.intelligence = objetoJSON.getInt("intelligence");
            this.wisdom = objetoJSON.getInt("wisdom");
            this.charisma = objetoJSON.getInt("charisma");
            if(objetoJSON.has("skills") && !objetoJSON.isNull("skills")){
                this.skills = objetoJSON.getJSONObject("skills");
            } else this.skills = new JSONObject("");
            if(objetoJSON.has("senses") && !objetoJSON.isNull("senses")){
                this.senses = objetoJSON.getString("senses");
            }
            if(objetoJSON.has("challenge_rating") && !objetoJSON.isNull("challenge_rating")){
                this.challengeRating = objetoJSON.getString("challenge_rating");
            }
            if(objetoJSON.has("languages") && !objetoJSON.isNull("languages")){
                this.languages = objetoJSON.getString("languages");
            }
            if(objetoJSON.has("actions") && !objetoJSON.isNull("actions")){
                this.actions = objetoJSON.getJSONArray("actions");
            }
            if(objetoJSON.has("bonus_actions") && !objetoJSON.isNull("bonus_actions")){
                this.bonusActions = objetoJSON.getJSONArray("bonus_actions");
            }
            if(objetoJSON.has("reactions") && !objetoJSON.isNull("reactions")){
                this.reactions = objetoJSON.getJSONArray("reactions");
            }
            if(objetoJSON.has("legendary_desc") && !objetoJSON.isNull("legendary_desc")){
                this.legendaryDesc = objetoJSON.getString("legendary_desc");
            } else this.legendaryDesc = "";
            if(objetoJSON.has("legendary_actions") && !objetoJSON.isNull("legendary_actions")){
                this.legendaryActions = objetoJSON.getJSONArray("legendary_actions");
            }
            if(objetoJSON.has("special_abilities") && !objetoJSON.isNull("special_abilities")){
                this.specialAbilities = objetoJSON.getJSONArray("special_abilities");
            }
            if(objetoJSON.has("document__license_url") && !objetoJSON.isNull("document__license_url")){
                this.licenseURL = objetoJSON.getString("document__license_url");
            }
            if(objetoJSON.has("document__url") && !objetoJSON.isNull("document__url")){
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

    public JSONObject getSpeed() {
        return speed;
    }

    public void setSpeed(JSONObject speed) {
        this.speed = speed;
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

    public JSONObject getSkills() {
        return skills;
    }

    public void setSkills(JSONObject skills) {
        this.skills = skills;
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

    public JSONArray getActions() {
        return actions;
    }

    public void setActions(JSONArray actions) {
        this.actions = actions;
    }

    public JSONArray getBonusActions() {
        return bonusActions;
    }

    public void setBonusActions(JSONArray bonusActions) {
        this.bonusActions = bonusActions;
    }

    public JSONArray getReactions() {
        return reactions;
    }

    public void setReactions(JSONArray reactions) {
        this.reactions = reactions;
    }

    public String getLegendaryDesc() {
        return legendaryDesc;
    }

    public void setLegendaryDesc(String legendaryDesc) {
        this.legendaryDesc = legendaryDesc;
    }

    public JSONArray getLegendaryActions() {
        return legendaryActions;
    }

    public void setLegendaryActions(JSONArray legendaryActions) {
        this.legendaryActions = legendaryActions;
    }

    public JSONArray getSpecialAbilities() {
        return specialAbilities;
    }

    public void setSpecialAbilities(JSONArray specialAbilities) {
        this.specialAbilities = specialAbilities;
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