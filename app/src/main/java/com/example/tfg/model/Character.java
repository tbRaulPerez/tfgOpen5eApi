package com.example.tfg.model;

import java.util.ArrayList;

public class Character extends SavableItem{
    private String name;
    private int level;
    private int hitpoints;
    private int hitpointsAtLevel1;
    private int armorClass;
    private int str,dex,con,cha,wis,inte;
    private boolean profAcrobatics;
    private boolean profAnimalHandling;
    private boolean profArcana;
    private boolean profAthletics;
    private boolean profDeception;
    private boolean profHistory;
    private boolean profInsight;
    private boolean profIntimidation;
    private boolean profInvestigation;
    private boolean profMedicine;
    private boolean profNature;
    private boolean profPerception;
    private boolean profPersuasion;
    private boolean profPerformance;
    private boolean profReligion;
    private boolean profSleightOfHand;
    private boolean profStealth;
    private boolean profSurvival;
    private Background background;
    private CharacterClass cClass;
    private  Race race;
    private ArrayList<Spell> spells;
    private ArrayList<Armor> armors;
    private  ArrayList<Weapon> weapons;
    private  ArrayList<MagicItem> magicItems;
    private String firebaseKey;

    public Character() {
    }

    public Character(ArrayList<String> owner,String name, Background background, CharacterClass cClass, Race race, ArrayList<Spell> spells, ArrayList<Armor> armors, ArrayList<Weapon> weapons, ArrayList<MagicItem> magicItems) {
        super(owner);
        this.name = name;
        this.level = 1;
        this.con = 10;
        this.cClass = cClass;
        if (this.getcClass().getName().equals("Barbarian")) {
            this.hitpoints = (int)Math.round(12 + (this.con-10)*0.5);
        } else if (this.getcClass().getName().equals("Fighter") ||
                this.getcClass().getName().equals("Paladin") ||
                this.getcClass().getName().equals("Ranger")) {
            this.hitpoints = (int)Math.round(10 + (this.con-10)*0.5);
        }else if (this.getcClass().getName().equals("Bard") ||
                this.getcClass().getName().equals("Cleric") ||
                this.getcClass().getName().equals("Druid") ||
                this.getcClass().getName().equals("Monk") ||
                this.getcClass().getName().equals("Rogue") ||
                this.getcClass().getName().equals("Warlock")) {
            this.hitpoints = (int)Math.round(8 + (this.con-10)*0.5);
        } else if (this.getcClass().getName().equals("Sorcerer") ||
                this.getcClass().getName().equals("Wizard")){
            this.hitpoints = (int)Math.round(6 + (this.con-10)*0.5);
        }
        this.hitpointsAtLevel1 = this.hitpoints;
        this.background = background;
        this.str = 10;
        this.dex= 10;
        this.inte = 10;
        this.wis = 10;
        this.cha = 10;
        this.armorClass = 10;
        this.race = race;
        this.spells = spells;
        this.armors = armors;
        this.weapons = weapons;
        this.magicItems = magicItems;
        this.profAcrobatics = false;
        this.profAnimalHandling = false;
        this.profArcana = false;
        this.profAthletics = false;
        this.profDeception = false;
        this.profHistory = false;
        this.profInsight = false;
        this.profIntimidation = false;
        this.profInvestigation = false;
        this.profMedicine = false;
        this.profNature = false;
        this.profPerception = false;
        this.profPersuasion = false;
        this.profPerformance = false;
        this.profReligion = false;
        this.profSleightOfHand = false;
        this.profStealth = false;
        this.profSurvival = false;

    }

    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }

    public int getHitpointsAtLevel1() {
        return hitpointsAtLevel1;
    }

    public void setHitpointsAtLevel1(int hitpointsAtLevel1) {
        this.hitpointsAtLevel1 = hitpointsAtLevel1;
    }

    public int getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(int armorClass) {
        this.armorClass = armorClass;
    }

    public int getStr() {
        return str;
    }

    public void setStr(int str) {
        this.str = str;
    }

    public int getDex() {
        return dex;
    }

    public void setDex(int dex) {
        this.dex = dex;
    }

    public int getCon() {
        return con;
    }

    public void setCon(int con) {
        this.con = con;
    }

    public int getCha() {
        return cha;
    }

    public void setCha(int cha) {
        this.cha = cha;
    }

    public int getWis() {
        return wis;
    }

    public void setWis(int wis) {
        this.wis = wis;
    }

    public int getInte() {
        return inte;
    }

    public void setInte(int inte) {
        this.inte = inte;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(int hitpoints) {
        this.hitpoints = hitpoints;
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

    public boolean isProfAcrobatics() {
        return profAcrobatics;
    }

    public void setProfAcrobatics(boolean profAcrobatics) {
        this.profAcrobatics = profAcrobatics;
    }

    public boolean isProfAnimalHandling() {
        return profAnimalHandling;
    }

    public void setProfAnimalHandling(boolean profAnimalHandling) {
        this.profAnimalHandling = profAnimalHandling;
    }

    public boolean isProfArcana() {
        return profArcana;
    }

    public void setProfArcana(boolean profArcana) {
        this.profArcana = profArcana;
    }

    public boolean isProfAthletics() {
        return profAthletics;
    }

    public void setProfAthletics(boolean profAthletics) {
        this.profAthletics = profAthletics;
    }

    public boolean isProfDeception() {
        return profDeception;
    }

    public void setProfDeception(boolean profDeception) {
        this.profDeception = profDeception;
    }

    public boolean isProfHistory() {
        return profHistory;
    }

    public void setProfHistory(boolean profHistory) {
        this.profHistory = profHistory;
    }

    public boolean isProfInsight() {
        return profInsight;
    }

    public void setProfInsight(boolean profInsight) {
        this.profInsight = profInsight;
    }

    public boolean isProfIntimidation() {
        return profIntimidation;
    }

    public void setProfIntimidation(boolean profIntimidation) {
        this.profIntimidation = profIntimidation;
    }

    public boolean isProfInvestigation() {
        return profInvestigation;
    }

    public void setProfInvestigation(boolean profInvestigation) {
        this.profInvestigation = profInvestigation;
    }

    public boolean isProfMedicine() {
        return profMedicine;
    }

    public void setProfMedicine(boolean profMedicine) {
        this.profMedicine = profMedicine;
    }

    public boolean isProfNature() {
        return profNature;
    }

    public void setProfNature(boolean profNature) {
        this.profNature = profNature;
    }

    public boolean isProfPerception() {
        return profPerception;
    }

    public void setProfPerception(boolean profPerception) {
        this.profPerception = profPerception;
    }

    public boolean isProfPersuasion() {
        return profPersuasion;
    }

    public void setProfPersuasion(boolean profPersuasion) {
        this.profPersuasion = profPersuasion;
    }

    public boolean isProfPerformance() {
        return profPerformance;
    }

    public void setProfPerformance(boolean profPerformance) {
        this.profPerformance = profPerformance;
    }

    public boolean isProfReligion() {
        return profReligion;
    }

    public void setProfReligion(boolean profReligion) {
        this.profReligion = profReligion;
    }

    public boolean isProfSleightOfHand() {
        return profSleightOfHand;
    }

    public void setProfSleightOfHand(boolean profSleightOfHand) {
        this.profSleightOfHand = profSleightOfHand;
    }

    public boolean isProfStealth() {
        return profStealth;
    }

    public void setProfStealth(boolean profStealth) {
        this.profStealth = profStealth;
    }

    public boolean isProfSurvival() {
        return profSurvival;
    }

    public void setProfSurvival(boolean profSurvival) {
        this.profSurvival = profSurvival;
    }

    @Override
    public String toString() {
        return "Character{" +
                "name='" + name + '\'' +
                ", level=" + level +
                ", hitpoints=" + hitpoints +
                ", armorClass=" + armorClass +
                ", str=" + str +
                ", dex=" + dex +
                ", con=" + con +
                ", cha=" + cha +
                ", wis=" + wis +
                ", inte=" + inte +
                ", background=" + background +
                ", cClass=" + cClass +
                ", race=" + race +
                ", spells=" + spells +
                ", armors=" + armors +
                ", weapons=" + weapons +
                ", magicItems=" + magicItems +
                '}';
    }
}
