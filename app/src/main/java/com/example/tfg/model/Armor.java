package com.example.tfg.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Armor {
    private String name;
    private String category;
    private String acString;
    private int strengthRequirement;
    private String cost;
    private Boolean stealthDisadvange;
    private String licenseURL;
    private String documentURL;

    public Armor(JSONObject objetoJson) {
        try {
            if (objetoJson.has("name") && !objetoJson.isNull("name")) {
                this.name = objetoJson.getString("name");
            }
            if (objetoJson.has("category") && !objetoJson.isNull("category")) {
                this.category = objetoJson.getString("category");
            }
            if (objetoJson.has("ac_string") && !objetoJson.isNull("ac_string")) {
                this.acString = objetoJson.getString("ac_string");
            }
            if (objetoJson.has("strength_requirement") && !objetoJson.isNull("strength_requirement")) {
                this.strengthRequirement = objetoJson.getInt("strength_requirement");
            } else this.strengthRequirement = 0;
            if (objetoJson.has("cost") && !objetoJson.isNull("cost")) {
                this.cost = objetoJson.getString("cost");
            }
            if (objetoJson.has("stealth_disadvantage") && !objetoJson.isNull("stealth_disadvantage")) {
                this.stealthDisadvange = objetoJson.getBoolean("stealth_disadvantage");
            }
            if(objetoJson.has("document__license_url") && !objetoJson.isNull("document__license_url")){
                this.licenseURL = objetoJson.getString("document__license_url");
            }
            if(objetoJson.has("document__url") && !objetoJson.isNull("document__url")){
                this.documentURL = objetoJson.getString("document__url");
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAcString() {
        return acString;
    }

    public void setAcString(String acString) {
        this.acString = acString;
    }

    public int getStrengthRequirement() {
        return strengthRequirement;
    }

    public void setStrengthRequirement(int strengthRequirement) {
        this.strengthRequirement = strengthRequirement;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public Boolean getStealthDisadvange() {
        return stealthDisadvange;
    }

    public void setStealthDisadvange(Boolean stealthDisadvange) {
        this.stealthDisadvange = stealthDisadvange;
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
