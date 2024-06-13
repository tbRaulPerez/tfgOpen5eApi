package com.example.tfg.model;

import java.io.Serializable;
import java.util.ArrayList;

public class SavableItem implements Serializable {
    private ArrayList<String> owner;

    public SavableItem(ArrayList<String> owner) {
        this.owner = owner;
    }

    public SavableItem() {
    }

    public ArrayList<String> getOwner() {
        return owner;
    }

    public void setOwner(ArrayList<String> owner) {
        this.owner = owner;
    }
}
