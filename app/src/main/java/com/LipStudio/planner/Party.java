package com.LipStudio.planner;

import java.util.ArrayList;

public class Party {

    private String partyName;
    private String description;
    private ArrayList<Product> products;
    private ArrayList<Guest> guests;
    public Party(String name){
        this.partyName = name;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
}
