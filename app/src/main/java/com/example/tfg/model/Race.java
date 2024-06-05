package com.example.tfg.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Race implements Serializable {
    private String name;
    private String desc;
    private String abilityScoreImprovement;
    private String age;
    private String size;
    private JSONObject speed;
    private String languages;
    private String vision;
    private String traits;
    private JSONArray subraces;
    private String licenseURL;
    private String documentURL;

    public Race(JSONObject objetoJson) {
        try {
            if (objetoJson.has("name") && !objetoJson.isNull("name")) {
                this.name = objetoJson.getString("name");
            }
            if (objetoJson.has("desc") && !objetoJson.isNull("desc")) {
                this.desc = objetoJson.getString("desc");
            }
            if (objetoJson.has("asi_desc") && !objetoJson.isNull("asi_desc")) {
                this.abilityScoreImprovement = objetoJson.getString("asi_desc");
            }
            if (objetoJson.has("age") && !objetoJson.isNull("age")) {
                this.age = objetoJson.getString("age");
            }
            if (objetoJson.has("size") && !objetoJson.isNull("size")) {
                this.size = objetoJson.getString("size");
            }
            if (objetoJson.has("speed") && !objetoJson.isNull("speed")) {
                this.speed = objetoJson.getJSONObject("speed");
            } else {
                this.speed = new JSONObject("\"speed\": {\n" +
                        "                \"walk\": 30,\n" +
                        "            }");
            }
            if (objetoJson.has("languages") && !objetoJson.isNull("languages")) {
                this.languages = objetoJson.getString("languages");
            }
            if (objetoJson.has("vision") && !objetoJson.isNull("vision")) {
                this.vision = objetoJson.getString("vision");
            }
            if (objetoJson.has("traits") && !objetoJson.isNull("traits")) {
                this.traits = objetoJson.getString("traits");
            }
            if (objetoJson.has("subraces") && !objetoJson.isNull("subraces")) {
                this.subraces = objetoJson.getJSONArray("subraces");
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAbilityScoreImprovement() {
        return abilityScoreImprovement;
    }

    public void setAbilityScoreImprovement(String abilityScoreImprovement) {
        this.abilityScoreImprovement = abilityScoreImprovement;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public JSONObject getSpeed() {
        return speed;
    }

    public void setSpeed(JSONObject speed) {
        this.speed = speed;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getTraits() {
        return traits;
    }

    public void setTraits(String traits) {
        this.traits = traits;
    }

    public JSONArray getSubraces() {
        return subraces;
    }

    public void setSubraces(JSONArray subraces) {
        this.subraces = subraces;
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