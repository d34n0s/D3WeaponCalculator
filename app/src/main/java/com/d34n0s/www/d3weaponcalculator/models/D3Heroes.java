package com.d34n0s.www.d3weaponcalculator.models;

/**
 * Created by dlawrence on 5/11/2014.
 */
public class D3Heroes {
    public String paragonLevel;
    public String seasonal;
    public String name;
    public String id;
    public String level;
    public String hardcore;
    public String gender;
    public String dead;
    public String playerClass;
    public String last_updated;

    public String getId(){
        return id;
    }
    public String getHeroName(){
        return name;
    }
    public String getPlayerClass(){
        return playerClass;
    }
    public String getPlayerGender(){
        return gender;
    }
    public String getLevel(){
        return level;
    }
}
