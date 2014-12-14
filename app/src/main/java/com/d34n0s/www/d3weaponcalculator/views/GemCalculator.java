package com.d34n0s.www.d3weaponcalculator.views;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.d34n0s.www.d3weaponcalculator.R;

/**
 * Created by dean on 13/12/2014.
 */
public class GemCalculator extends BaseActivity {
    EditText et_gemCalc_input;
    TableLayout tl_gemCalcHeader;
    TableLayout tl_gemCalc;
    TextView tv_gemCalc_100;
    TextView tv_gemCalc_90;
    TextView tv_gemCalc_80;
    TextView tv_gemCalc_70;
    TextView tv_gemCalc_60;
    TextView tv_gemCalc_30;
    TextView tv_gemCalc_15;
    TextView tv_gemCalc_8;
    TextView tv_gemCalc_4;
    TextView tv_gemCalc_2;
    TextView tv_gemCalc_1;
    TextView tv_gemCalc_0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gem_calc);

        initialise();
    }

    private void initialise(){
        et_gemCalc_input = (EditText) findViewById(R.id.et_gemCalc_input);
        tl_gemCalcHeader = (TableLayout) findViewById(R.id.tl_gemCalcHeader);
        tl_gemCalc = (TableLayout) findViewById(R.id.tl_gemCalc);
        tv_gemCalc_100 = (TextView) findViewById(R.id.tv_gemCalc_100);
        tv_gemCalc_90 = (TextView) findViewById(R.id.tv_gemCalc_90);
        tv_gemCalc_80 = (TextView) findViewById(R.id.tv_gemCalc_80);
        tv_gemCalc_70 = (TextView) findViewById(R.id.tv_gemCalc_70);
        tv_gemCalc_60 = (TextView) findViewById(R.id.tv_gemCalc_60);
        tv_gemCalc_30 = (TextView) findViewById(R.id.tv_gemCalc_30);
        tv_gemCalc_15 = (TextView) findViewById(R.id.tv_gemCalc_15);
        tv_gemCalc_8 = (TextView) findViewById(R.id.tv_gemCalc_8);
        tv_gemCalc_4 = (TextView) findViewById(R.id.tv_gemCalc_4);
        tv_gemCalc_2 = (TextView) findViewById(R.id.tv_gemCalc_2);
        tv_gemCalc_1 = (TextView) findViewById(R.id.tv_gemCalc_1);
        tv_gemCalc_0 = (TextView) findViewById(R.id.tv_gemCalc_0);

        tl_gemCalcHeader.setVisibility(View.GONE);
        tl_gemCalc.setVisibility(View.GONE);
    }


    public void getGemChance(){

        int gem = Integer.parseInt(et_gemCalc_input.getText().toString());


        tv_gemCalc_100.setText(String.valueOf(gem + 10));

        tv_gemCalc_90.setText(String.valueOf(gem + 9));

        tv_gemCalc_80.setText(String.valueOf(gem + 8));

        tv_gemCalc_70.setText(String.valueOf(gem + 7));

        tv_gemCalc_60.setText(String.valueOf(gem + 6) + " to " + String.valueOf(gem));

        if(gem -1 < 0){
            tv_gemCalc_30.setText("N/A");
        }else{
            tv_gemCalc_30.setText(String.valueOf(gem - 1));
        }

        if(gem -2 < 0){
            tv_gemCalc_15.setText("N/A");
        }else{
            tv_gemCalc_15.setText(String.valueOf(gem + -2));
        }

        if(gem -3 < 0){
            tv_gemCalc_8.setText("N/A");
        }else{
            tv_gemCalc_8.setText(String.valueOf(gem + -3));
        }

        if(gem -4 < 0){
            tv_gemCalc_4.setText("N/A");
        }else{
            tv_gemCalc_4.setText(String.valueOf(gem + -4));
        }

        if(gem -5 < 0){
            tv_gemCalc_2.setText("N/A");
        }else{
            tv_gemCalc_2.setText(String.valueOf(gem + -5));
        }

        if(gem -6 < 0){
            tv_gemCalc_1.setText("N/A");
        }else{
            tv_gemCalc_1.setText(String.valueOf(gem + -6));
        }


        if(gem -7 < 0){
            tv_gemCalc_0.setText("N/A");
        }else{
            tv_gemCalc_0.setText(String.valueOf(gem + -7));
        }

        tl_gemCalcHeader.setVisibility(View.VISIBLE);
        tl_gemCalc.setVisibility(View.VISIBLE);

    }

    public void b_gemCalc_go(View view) {
        //close the keyboard
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        if(et_gemCalc_input.getText().toString().matches("")){
            Toast t = Toast.makeText(GemCalculator.this, "Please enter a Gem level", Toast.LENGTH_SHORT);
            t.show();
        }else{
            getGemChance();
        }

    }
}
