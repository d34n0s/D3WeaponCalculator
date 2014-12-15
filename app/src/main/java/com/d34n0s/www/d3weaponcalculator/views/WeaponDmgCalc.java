package com.d34n0s.www.d3weaponcalculator.views;

import android.os.Bundle;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.d34n0s.www.d3weaponcalculator.R;

/**
 * Created by dean on 13/12/2014.
 */
public class WeaponDmgCalc extends BaseActivity {

    ViewFlipper vf_wc_flipper;
    TextView tv_wc_instructions;
    TextView tv_wc_DefDmg;
    EditText et_wc_def_dmg_min;
    EditText et_wc_def_dmg_max;
    EditText et_wc_def_weaponAPS;
    EditText et_wc_def_DmgPercentage;
    EditText et_wc_def_DmgIAS;
    EditText et_wc_def_subdmg_min;
    EditText et_wc_def_subdmg_max;
    ImageView iv_wc_examples;
    TextView tv_wc_NewDmg;
    TextView tv_wc_NewDmgPercent;
    TextView tv_wc_NewDmgIAS;
    EditText et_wc_new_dmg_min;
    EditText et_wc_new_dmg_max;

    float initialX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapon_dmg_calc);

        vf_wc_flipper = (ViewFlipper) findViewById(R.id.vf_wc_flipper);
        tv_wc_instructions = (TextView) findViewById(R.id.tv_wc_instructions);
        tv_wc_DefDmg = (TextView) findViewById(R.id.tv_wc_DefDmg);
        et_wc_def_dmg_min = (EditText) findViewById(R.id.et_wc_def_dmg_min);
        et_wc_def_dmg_max = (EditText) findViewById(R.id.et_wc_def_dmg_max);
        et_wc_def_weaponAPS = (EditText) findViewById(R.id.et_wc_def_weaponAPS);
        et_wc_def_DmgPercentage = (EditText) findViewById(R.id.et_wc_def_DmgPercentage);
        et_wc_def_DmgIAS = (EditText) findViewById(R.id.et_wc_def_DmgIAS);
        et_wc_def_subdmg_min = (EditText) findViewById(R.id.et_wc_def_subdmg_min);
        et_wc_def_subdmg_max = (EditText) findViewById(R.id.et_wc_def_subdmg_max);
        iv_wc_examples = (ImageView) findViewById(R.id.iv_wc_examples);
        tv_wc_NewDmg = (TextView) findViewById(R.id.tv_wc_NewDmg);
        tv_wc_NewDmgPercent = (TextView) findViewById(R.id.tv_wc_NewDmgPercent);
        tv_wc_NewDmgIAS = (TextView) findViewById(R.id.tv_wc_NewDmgIAS);
        et_wc_new_dmg_min = (EditText) findViewById(R.id.et_wc_new_dmg_min);
        et_wc_new_dmg_max = (EditText) findViewById(R.id.et_wc_new_dmg_max);

        instructionText();

        defaultData1();

    }

    private void defaultData1(){
        et_wc_def_dmg_min.setHint("1213");
        et_wc_def_dmg_max.setHint("1646");
        et_wc_def_weaponAPS.setHint("1.40");
        et_wc_def_DmgPercentage.setHint("0");
        et_wc_def_DmgIAS.setHint("0");
        et_wc_def_subdmg_min.setHint("1045");
        et_wc_def_subdmg_max.setHint("1254");

        et_wc_new_dmg_min.setHint("1199");
        et_wc_new_dmg_max.setHint("1490");

        iv_wc_examples.setImageResource(R.drawable.weapon_example);


    }

    private void defaultData2(){
        et_wc_def_dmg_min.setHint("1335");
        et_wc_def_dmg_max.setHint("1878");
        et_wc_def_weaponAPS.setHint("1.40");
        et_wc_def_DmgPercentage.setHint("8");
        et_wc_def_DmgIAS.setHint("0");
        et_wc_def_subdmg_min.setHint("1068");
        et_wc_def_subdmg_max.setHint("1347");

        et_wc_new_dmg_min.setHint("1199");
        et_wc_new_dmg_max.setHint("1490");

        iv_wc_examples.setImageResource(R.drawable.weapon_example_2);
    }

    private void defaultData3(){
        et_wc_def_dmg_min.setHint("1306");
        et_wc_def_dmg_max.setHint("1851");
        et_wc_def_weaponAPS.setHint("1.48");
        et_wc_def_DmgPercentage.setHint("7");
        et_wc_def_DmgIAS.setHint("6");
        et_wc_def_subdmg_min.setHint("1053");
        et_wc_def_subdmg_max.setHint("1338");

        et_wc_new_dmg_min.setHint("1199");
        et_wc_new_dmg_max.setHint("1490");

        iv_wc_examples.setImageResource(R.drawable.weapon_example_3);
    }

    private void clearData(){
        et_wc_def_dmg_min.setText("");
        et_wc_def_dmg_max.setText("");
        et_wc_def_weaponAPS.setText("");
        et_wc_def_DmgPercentage.setText("");
        et_wc_def_DmgIAS.setText("");
        et_wc_def_subdmg_min.setText("");
        et_wc_def_subdmg_max.setText("");

        et_wc_new_dmg_min.setText("");
        et_wc_new_dmg_max.setText("");
        et_wc_def_dmg_min.setHint("");
        et_wc_def_dmg_max.setHint("");
        et_wc_def_weaponAPS.setHint("");
        et_wc_def_DmgPercentage.setHint("");
        et_wc_def_DmgIAS.setHint("");
        et_wc_def_subdmg_min.setHint("");
        et_wc_def_subdmg_max.setHint("");

        et_wc_new_dmg_min.setHint("");
        et_wc_new_dmg_max.setHint("");
    }

    private void instructionText(){
        tv_wc_instructions.setText(Html.fromHtml(
                "Use this weapon calculator to determine if you should roll the Weapon " +
                        "Damage, the % Damage or Attack Speed. <br/><br/>" +
                        "In Step 1, enter the the values of the weapon as it is now" +
                        " (See examples for where to read values).<br/><br/>" +
                        "Then take your weapon to the Mystic and see what the best potential Min / Max " +
                        "Damage rolls are. <br/><br/>" +
                        "In Step 2, enter those potential Min / Max rolls, " +
                        "and press the Calculate button.<br/><br/>" +
                        "<strong>Swipe to continue --></strong>"

        ));
    }

    @Override
    public boolean onTouchEvent(MotionEvent touchevent) {



        switch (touchevent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = touchevent.getX();
                break;
            case MotionEvent.ACTION_UP:
                float finalX = touchevent.getX();
                if (initialX > finalX + 100) {
                    if (vf_wc_flipper.getDisplayedChild() == 2)
                        break;

                    vf_wc_flipper.showNext();
                } else if (initialX < finalX - 100){
                    if (vf_wc_flipper.getDisplayedChild() == 0)
                        break;

                    vf_wc_flipper.showPrevious();
                }
                break;
        }
        return false;
    }

    private void calculate(){

        //Default weapon variables
        //set them to a default value if left blank so the app doesn't blow up
        Double def_dmg_min;
        if(et_wc_def_dmg_min.getText().toString().matches("") || et_wc_def_dmg_min.getText().toString().matches("0")){
            def_dmg_min = 1.00;
            et_wc_def_dmg_min.setText("1");
        } else {
            def_dmg_min = Double.parseDouble(et_wc_def_dmg_min.getText().toString());
        }

        Double def_dmg_max;
        if(et_wc_def_dmg_max.getText().toString().matches("") || et_wc_def_dmg_max.getText().toString().matches("0")){
            def_dmg_max = 1.00;
            et_wc_def_dmg_max.setText("1");
        }else{
            def_dmg_max = Double.parseDouble(et_wc_def_dmg_max.getText().toString());
        }

        Double def_weaponAPS;
        if(et_wc_def_weaponAPS.getText().toString().matches("") || et_wc_def_weaponAPS.getText().toString().matches("0")){
            def_weaponAPS = 1.00;
            et_wc_def_weaponAPS.setText("1");
        }else{
            def_weaponAPS = Double.parseDouble(et_wc_def_weaponAPS.getText().toString());
        }

        Double def_DmgPercentage;
        if(et_wc_def_DmgPercentage.getText().toString().matches("") || et_wc_def_DmgPercentage.getText().toString().matches("0")){
            def_DmgPercentage = 0.00;
            et_wc_def_DmgPercentage.setText("0");
        }else{
            def_DmgPercentage = Double.parseDouble(et_wc_def_DmgPercentage.getText().toString());
        }

        Double def_DmgIAS;
        if(et_wc_def_DmgIAS.getText().toString().matches("") || et_wc_def_DmgIAS.getText().toString().matches("0")){
            def_DmgIAS = 1.00;
            et_wc_def_DmgIAS.setText("0");
        }else{
            def_DmgIAS = Double.parseDouble(et_wc_def_DmgIAS.getText().toString());
            def_DmgIAS = 1 + def_DmgIAS / 100; //turn it into a percentage
        }

        Double def_subdmg_min;
        if(et_wc_def_subdmg_min.getText().toString().matches("") || et_wc_def_subdmg_min.getText().toString().matches("0")){
            def_subdmg_min = 1.00;
            et_wc_def_subdmg_min.setText("1");
        }else{
            def_subdmg_min = Double.parseDouble(et_wc_def_subdmg_min.getText().toString());
        }

        Double def_subdmg_max;
        if(et_wc_def_subdmg_max.getText().toString().matches("") || et_wc_def_subdmg_max.getText().toString().matches("0")){
            def_subdmg_max = 1.00;
            et_wc_def_subdmg_max.setText("1");
        }else{
            def_subdmg_max = Double.parseDouble(et_wc_def_subdmg_max.getText().toString());
        }

        //calculate base weapon attack speed so that we can better calculate actual
        //attack speed without the rounding that the game imposes in the GUI
        //round to 2 decimal places to match GUI
        double defWeaponBaseAPS;
        defWeaponBaseAPS = Double.parseDouble(String.format("%.2f", def_weaponAPS / def_DmgIAS));

        //calculate the new more accurate attach speed without the rounding
        def_weaponAPS = defWeaponBaseAPS * def_DmgIAS;

        //Calculate the weapons default Damage Value
        Double defCalc = ((def_dmg_max + def_dmg_min) / 2) * def_weaponAPS;
        tv_wc_DefDmg.setText("Current DPS: " + (String.format("%.2f", defCalc)));

        //Start the calculations for the newly rolled weapon damage

        //calculate the adjusted default sub weapon damage based on %dmg increase
        Double defSubCalcMin = (def_subdmg_min * (1+def_DmgPercentage/100));
        Double defSubCalcMax = (def_subdmg_max * (1+def_DmgPercentage/100));

        //weapon best damage roll values
        Double new_dmg_min;
        if(et_wc_new_dmg_min.getText().toString().matches("") || et_wc_new_dmg_min.getText().toString().matches("0")){
            new_dmg_min = defSubCalcMin;
            et_wc_new_dmg_min.setText(defSubCalcMin.toString());
        }else{
            new_dmg_min = Double.parseDouble(et_wc_new_dmg_min.getText().toString());
        }

        Double new_dmg_max;
        if(et_wc_new_dmg_max.getText().toString().matches("") || et_wc_new_dmg_max.getText().toString().matches("0")){
            new_dmg_max = defSubCalcMax;
            et_wc_new_dmg_max.setText(defSubCalcMax.toString());
        }else{
            new_dmg_max = Double.parseDouble(et_wc_new_dmg_max.getText().toString());
        }

        //calculate the new DPS based on Sub Damage rolled
        Double newRangeMin;
        Double newRangeMax;
        Double newCalc;

        //increase the newly rolled in max based any existing % damage increase
        if(def_DmgPercentage == 0){
            newRangeMin = (new_dmg_min - defSubCalcMin) + def_dmg_min;
            newRangeMax = (new_dmg_max - defSubCalcMax) + def_dmg_max;
        }else{
            newRangeMin = ((new_dmg_min*(1+def_DmgPercentage/100)) - defSubCalcMin) + def_dmg_min;
            newRangeMax = ((new_dmg_max*(1+def_DmgPercentage/100)) - defSubCalcMax) + def_dmg_max;
        }
        newCalc = (newRangeMin + newRangeMax) /2 * def_weaponAPS;
        tv_wc_NewDmg.setText("DPS if Sub Damage Range Max Rolled: " + String.format("%.2f", newCalc));



        //calculate the DPS max based on +10% Damage rolled
        if(def_DmgPercentage == 0){
            new_dmg_min = def_subdmg_min * 1.1;
            new_dmg_max = def_subdmg_max * 1.1;
            newRangeMin = (new_dmg_min - def_subdmg_min) + def_dmg_min;
            newRangeMax = (new_dmg_max - def_subdmg_max) + def_dmg_max;
            newCalc = (newRangeMin + newRangeMax) /2 * def_weaponAPS;
        }else{
            newCalc = defCalc * (1.1/(1+def_DmgPercentage/100));
        }
        tv_wc_NewDmgPercent.setText("DPS if +10% Damage Rolled: " + String.format("%.2f", newCalc));


        //calculate the new DPS based on IAS rolled

        newCalc = (def_dmg_min + def_dmg_max)/2 * (defWeaponBaseAPS * 1.07);


        tv_wc_NewDmgIAS.setText("DPS if 7% IAS Rolled: " + String.format("%.2f", newCalc));

    }


    public void b_wc_example1(View view) {defaultData1();}

    public void b_wc_example2(View view) {defaultData2();}

    public void b_wc_example3(View view) {defaultData3();}

    public void b_wc_clear(View view) {clearData();}

    public void b_wc_calculate(View view) {calculate();}
}
