package com.example.tfg.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Weapon extends SavableItem{
    private String name;
    private String category;
    private String cost;
    private String dmgDice;
    private String dmgType;
    private ArrayList<String> properties;
    private String licenseURL;
    private String documentURL;

    public Weapon() {
    }

    public Weapon(JSONObject objetoJson, ArrayList<String> owner) {
        super(owner);
        try {
            if (objetoJson.has("name") && !objetoJson.isNull("name")) {
                this.name = objetoJson.getString("name");
            }
            if (objetoJson.has("category") && !objetoJson.isNull("category")) {
                this.category = objetoJson.getString("category");
            }
            if (objetoJson.has("cost") && !objetoJson.isNull("cost")) {
                this.cost = objetoJson.getString("cost");
            }
            if (objetoJson.has("damage_dice") && !objetoJson.isNull("damage_dice")) {
                this.dmgDice = objetoJson.getString("damage_dice");
            }
            if (objetoJson.has("damage_type") && !objetoJson.isNull("damage_type")) {
                this.dmgType = objetoJson.getString("damage_type");
            }
            if (objetoJson.has("properties") && !objetoJson.isNull("properties")) {
                JSONArray arrJson = objetoJson.getJSONArray("properties");
                this.properties = new ArrayList<String>();
                for (int i = 0; i < arrJson.length(); i++)
                    this.properties.add(arrJson.getString(i));
            }
            if (objetoJson.has("document__license_url") && !objetoJson.isNull("document__license_url")) {
                this.licenseURL = objetoJson.getString("document__license_url");
            }
            if (objetoJson.has("document__url") && !objetoJson.isNull("document__url")) {
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

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDmgDice() {
        return dmgDice;
    }

    public void setDmgDice(String dmgDice) {
        this.dmgDice = dmgDice;
    }

    public String getDmgType() {
        return dmgType;
    }

    public void setDmgType(String dmgType) {
        this.dmgType = dmgType;
    }

    public ArrayList<String> getProperties() {
        return properties;
    }

    public void setProperties(ArrayList<String> properties) {
        this.properties = properties;
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
