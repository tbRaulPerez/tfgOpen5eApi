package com.example.tfg.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Spell extends SavableItem{
    private String name;
    private String desc;
    private String higherLevel;
    private String range;
    private String components;
    private String material;
    private String ritual;
    private String duration;
    private String concentration;
    private String castingTime;
    private String level;
    private String school;
    private String dndClass;
    private String licenseURL;
    private String documentURL;

    public Spell() {
    }

    public Spell(JSONObject objetoJSON, ArrayList<String> owner) {
        super(owner);
        try {
            this.name = objetoJSON.getString("name");
            if(objetoJSON.has("desc") && !objetoJSON.isNull("desc")){
                this.desc = objetoJSON.getString("desc");
            }
            if(objetoJSON.has("higher_level") && !objetoJSON.isNull("higher_level")){
                this.higherLevel = objetoJSON.getString("higher_level");
            }
            if(objetoJSON.has("range") && !objetoJSON.isNull("range")){
                this.range = objetoJSON.getString("range");
            }
            if(objetoJSON.has("components") && !objetoJSON.isNull("components")){
                this.components = objetoJSON.getString("components");
            }
            if(objetoJSON.has("material") && !objetoJSON.isNull("material")){
                this.material = objetoJSON.getString("material");
            }
            if(objetoJSON.has("ritual") && !objetoJSON.isNull("ritual")){
                this.ritual = objetoJSON.getString("ritual");
            }
            if(objetoJSON.has("duration") && !objetoJSON.isNull("duration")){
                this.duration = objetoJSON.getString("duration");
            }
            if(objetoJSON.has("concentration") && !objetoJSON.isNull("concentration")){
                this.concentration = objetoJSON.getString("concentration");
            }
            if(objetoJSON.has("casting_time") && !objetoJSON.isNull("casting_time")){
                this.castingTime = objetoJSON.getString("casting_time");
            }
            if(objetoJSON.has("level") && !objetoJSON.isNull("level")){
                this.level = objetoJSON.getString("level");
            }if(objetoJSON.has("school") && !objetoJSON.isNull("school")){
                this.school = objetoJSON.getString("school");
            }
            if(objetoJSON.has("dnd_class") && !objetoJSON.isNull("dnd_class")){
                this.dndClass = objetoJSON.getString("dnd_class");
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

    public String getHigherLevel() {
        return higherLevel;
    }

    public void setHigherLevel(String higherLevel) {
        this.higherLevel = higherLevel;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getComponents() {
        return components;
    }

    public void setComponents(String components) {
        this.components = components;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getRitual() {
        return ritual;
    }

    public void setRitual(String ritual) {
        this.ritual = ritual;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getConcentration() {
        return concentration;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    public String getCastingTime() {
        return castingTime;
    }

    public void setCastingTime(String castingTime) {
        this.castingTime = castingTime;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getDndClass() {
        return dndClass;
    }

    public void setDndClass(String dndClass) {
        this.dndClass = dndClass;
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
