package com.example.tfg.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Race extends SavableItem {
    private String name;
    private String desc;
    private String asi_desc;
    private String age;
    private String size;
    private String speedJsonObject;
    private String languages;
    private String vision;
    private String traits;
    private String subracesJsonArray;
    private String document__license_url;
    private String document__url;

    public Race() {
    }

    public Race(JSONObject objetoJson, ArrayList<String> owner) {
        super(owner);
        try {
            if (objetoJson.has("name") && !objetoJson.isNull("name")) {
                this.name = objetoJson.getString("name");
            }
            if (objetoJson.has("desc") && !objetoJson.isNull("desc")) {
                this.desc = objetoJson.getString("desc");
            }
            if (objetoJson.has("asi_desc") && !objetoJson.isNull("asi_desc")) {
                this.asi_desc = objetoJson.getString("asi_desc");
            }
            if (objetoJson.has("age") && !objetoJson.isNull("age")) {
                this.age = objetoJson.getString("age");
            }
            if (objetoJson.has("size") && !objetoJson.isNull("size")) {
                this.size = objetoJson.getString("size");
            }
            if (objetoJson.has("speed") && !objetoJson.isNull("speed")) {
                this.speedJsonObject = objetoJson.getString("speed");
            } else {
                this.speedJsonObject = "\"speed\": {\n" +
                        "                \"wak\": 30,\n" +
                        "            }";
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
                this.subracesJsonArray = objetoJson.getString("subraces");
            }
            if (objetoJson.has("document__license_url") && !objetoJson.isNull("document__license_url")) {
                this.document__license_url = objetoJson.getString("document__license_url");
            }
            if (objetoJson.has("document__url") && !objetoJson.isNull("document__url")) {
                this.document__url = objetoJson.getString("document__url");
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

    public String getAsi_desc() {
        return asi_desc;
    }

    public void setAsi_desc(String asi_desc) {
        this.asi_desc = asi_desc;
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

    public String getSpeedJsonObject() {
        return speedJsonObject;
    }

    public void setSpeedJsonObject(String speedJsonObject) {
        this.speedJsonObject = speedJsonObject;
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

    public String getSubracesJsonArray() {
        return subracesJsonArray;
    }

    public void setSubracesJsonArray(String subracesJsonArray) {
        this.subracesJsonArray = subracesJsonArray;
    }

    public String getDocument__license_url() {
        return document__license_url;
    }

    public void setDocument__license_url(String document__license_url) {
        this.document__license_url = document__license_url;
    }

    public String getDocument__url() {
        return document__url;
    }

    public void setDocument__url(String document__url) {
        this.document__url = document__url;
    }
}
