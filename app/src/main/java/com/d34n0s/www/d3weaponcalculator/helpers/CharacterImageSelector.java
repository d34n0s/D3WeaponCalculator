package com.d34n0s.www.d3weaponcalculator.helpers;


import com.d34n0s.www.d3weaponcalculator.R;

/**
 * Created by dean on 10/11/2014.
 */
public class CharacterImageSelector {


    public static Integer CharImage(String character, String gender){
        
        Integer response = null;
        
        //set the image based on class and sex
        if (character.contentEquals("barbarian")) {
            if (gender.contentEquals("0")) {
                response = (R.drawable.barbarian_m);
            } else {
                response = (R.drawable.barbarian_f);
            }

        } else if (character.contentEquals("wizard")) {
            if (gender.contentEquals("0")) {
                response = (R.drawable.wizard_m);
            } else {
                response = (R.drawable.wizard_f);
            }
        } else if (character.contentEquals("demon-hunter")) {
            if (gender.contentEquals("0")) {
                response = (R.drawable.demonhunter_m);
            } else {
                response = (R.drawable.demonhunter_f);
            }
        } else if (character.contentEquals("witch-doctor")) {
            if (gender.contentEquals("0")) {
                response = (R.drawable.witchdoctor_m);
            } else {
                response = (R.drawable.witchdoctor_f);
            }
        } else if (character.contentEquals("monk")) {
            if (gender.contentEquals("0")) {
                response = (R.drawable.monk_m);
            } else {
                response = (R.drawable.monk_f);
            }
        } else if (character.contentEquals("crusader")) {
            if (gender.contentEquals("0")) {
                response = (R.drawable.crusader_m);
            } else {
                response = (R.drawable.crusader_f);
            }
        }
     return response;
    }
   
}
