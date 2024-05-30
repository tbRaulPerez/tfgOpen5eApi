package com.example.tfg.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Background {
    private String name;
    private String desc;
    private String skillProficiencies;
    private String toolProficiencies;
    private String languages;
    private String equipment;
    private String feature;
    private String featureDesc;
    private String licenseURL;
    private String documentURL;

    public Background(JSONObject objetoJSON) {
        try {
            this.name = objetoJSON.getString("name");
            if(objetoJSON.has("desc") && !objetoJSON.isNull("desc")){
                this.desc = objetoJSON.getString("desc");
            }
            if(objetoJSON.has("skill_proficiencies") && !objetoJSON.isNull("skill_proficiencies")){
                this.skillProficiencies = objetoJSON.getString("skill_proficiencies");
            }
            if(objetoJSON.has("tool_proficiencies") && !objetoJSON.isNull("tool_proficiencies")){
                this.toolProficiencies = objetoJSON.getString("tool_proficiencies");
            }
            if(objetoJSON.has("languages") && !objetoJSON.isNull("languages")){
                this.languages = objetoJSON.getString("languages");
            }
            if(objetoJSON.has("equipment") && !objetoJSON.isNull("equipment")){
                this.equipment = objetoJSON.getString("equipment");
            }
            if(objetoJSON.has("feature") && !objetoJSON.isNull("feature")){
                this.feature = objetoJSON.getString("feature");
            }
            if(objetoJSON.has("feature_desc") && !objetoJSON.isNull("feature_desc")){
                this.featureDesc = objetoJSON.getString("feature_desc");
            }
            if(objetoJSON.has("document__license_url") && !objetoJSON.isNull("document__license_url")){
                this.licenseURL = objetoJSON.getString("document__license_url");
            }
            if(objetoJSON.has("document__url") && !objetoJSON.isNull("document__url")){
                this.documentURL =objetoJSON.getString("document__url");
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

    public String getSkillProficiencies() {
        return skillProficiencies;
    }

    public void setSkillProficiencies(String skillProficiencies) {
        this.skillProficiencies = skillProficiencies;
    }

    public String getToolProficiencies() {
        return toolProficiencies;
    }

    public void setToolProficiencies(String toolProficiencies) {
        this.toolProficiencies = toolProficiencies;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getFeatureDesc() {
        return featureDesc;
    }

    public void setFeatureDesc(String featureDesc) {
        this.featureDesc = featureDesc;
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
