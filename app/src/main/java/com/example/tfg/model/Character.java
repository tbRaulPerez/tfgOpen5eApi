package com.example.tfg.model;

import java.util.ArrayList;

public class Character {
    private String name;
    private Background background;
    private CharacterClass cClass;
    private  Race race;
    private ArrayList<Spell> spells;
    private ArrayList<Armor> armors;
    private  ArrayList<Weapon> weapons;
    private  ArrayList<MagicItem> magicItems;

    public Character(String name, Background background, CharacterClass cClass, Race race, ArrayList<Spell> spells,
                     ArrayList<Armor> armors, ArrayList<Weapon> weapons, ArrayList<MagicItem> magicItems) {
        this.name = name;
        this.background = background;
        this.cClass = cClass;
        this.race = race;
        this.spells = spells;
        this.armors = armors;
        this.weapons = weapons;
        this.magicItems = magicItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    public Background getBackground() {
        return background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public CharacterClass getcClass() {
        return cClass;
    }

    public void setcClass(CharacterClass cClass) {
        this.cClass = cClass;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public ArrayList<Spell> getSpells() {
        return spells;
    }

    public void setSpells(ArrayList<Spell> spells) {
        this.spells = spells;
    }

    public ArrayList<Armor> getArmors() {
        return armors;
    }

    public void setArmors(ArrayList<Armor> armors) {
        this.armors = armors;
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(ArrayList<Weapon> weapons) {
        this.weapons = weapons;
    }

    public ArrayList<MagicItem> getMagicItems() {
        return magicItems;
    }

    public void setMagicItems(ArrayList<MagicItem> magicItems) {
        this.magicItems = magicItems;
    }
}
