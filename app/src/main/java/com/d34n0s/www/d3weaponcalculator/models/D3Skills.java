package com.d34n0s.www.d3weaponcalculator.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dlawrence on 6/11/2014.
 */
public class D3Skills {

    @SerializedName("name")
    public String skillName;
    @SerializedName("description")
    public String skillDesc;
    @SerializedName("tooltipUrl")
    public String skillTT;
    public String skillRune;
    public String runeDesc;
    public String runeTT;
    public String skillType;

    public String getRuneTT(){
        return runeTT;
    }
    public String getSkillTT(){
        return skillTT;
    }
}

