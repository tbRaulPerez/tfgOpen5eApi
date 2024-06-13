package com.example.tfg.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MagicItem extends SavableItem{
    private String name;
    private String type;
    private String desc;
    private String rarity;
    private String requiresAttunement;
    private String licenseURL;
    private String documentURL;

    public MagicItem() {
    }

    public MagicItem(JSONObject objetoJson, ArrayList<String> owner) {
        super(owner);
            try {
                if(objetoJson.has("name") && !objetoJson.isNull("name")){
                    this.name = objetoJson.getString("name");
                }
                if(objetoJson.has("type") && !objetoJson.isNull("type")){
                    this.type = objetoJson.getString("type");
                }
                if(objetoJson.has("desc") && !objetoJson.isNull("desc")){
                    this.desc = objetoJson.getString("desc");
                }
                if(objetoJson.has("rarity") && !objetoJson.isNull("rarity")){
                    this.rarity = objetoJson.getString("rarity");
                }
                if(objetoJson.has("requires_attunement") && !objetoJson.isNull("requires_attunement")){
                    this.requiresAttunement = objetoJson.getString("requires_attunement");
                } else this.requiresAttunement = "";
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getRequiresAttunement() {
        return requiresAttunement;
    }

    public void setRequiresAttunement(String requiresAttunement) {
        this.requiresAttunement = requiresAttunement;
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
