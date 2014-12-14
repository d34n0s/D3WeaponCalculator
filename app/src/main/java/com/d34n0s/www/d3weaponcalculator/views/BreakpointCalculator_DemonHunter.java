package com.d34n0s.www.d3weaponcalculator.views;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.d34n0s.www.d3weaponcalculator.R;
import com.d34n0s.www.d3weaponcalculator.helpers.ImagePopUp;

/**
 * Created by dean on 14/11/2014.
 */
public class BreakpointCalculator_DemonHunter extends BaseActivity{

    EditText weaponSpeed;
    ImageButton winfo;

    EditText aps;
    ImageButton ainfo;

    EditText tasker;
    ImageButton tinfo;

    TextView result;

    TableLayout tl;
    TableRow t1;
    TableRow t2;
    TableRow t3;
    TableRow t4;
    TableRow t5;
    TableRow t6;
    TableRow t7;
    TableRow t8;

    double bp = 0;

    ImagePopUp ip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakpoint_calculator_demon_hunter);

        initialise();

        ip = new ImagePopUp();
    }

    private void initialise(){
        weaponSpeed = (EditText) findViewById(R.id.et_bc_dh_weaponSpeed);
        aps = (EditText) findViewById(R.id.et_bc_dh_aps);
        tasker = (EditText) findViewById(R.id.et_bc_dh_tasker);

        result = (TextView) findViewById(R.id.tv_bpc_dh_result);

        tl= (TableLayout) findViewById(R.id.tl_bpc_dh);
        t1= (TableRow) findViewById(R.id.tr_bpc_dh_1);
        t2= (TableRow) findViewById(R.id.tr_bpc_dh_2);
        t3= (TableRow) findViewById(R.id.tr_bpc_dh_3);
        t4= (TableRow) findViewById(R.id.tr_bpc_dh_4);
        t5= (TableRow) findViewById(R.id.tr_bpc_dh_5);
        t6= (TableRow) findViewById(R.id.tr_bpc_dh_6);
        t7= (TableRow) findViewById(R.id.tr_bpc_dh_7);
        t8= (TableRow) findViewById(R.id.tr_bpc_dh_8);
    }

    private void calculate() {

        double ws;
        double as;
        double t;


        if (weaponSpeed.getText().toString().matches("")) {
            ws = 0;
        } else {
            ws = Double.parseDouble(weaponSpeed.getText().toString());
        }

        if (aps.getText().toString().matches("")) {
            as = 0;
        } else {
            as = Double.parseDouble(aps.getText().toString());
        }

        if (tasker.getText().toString().matches("")) {
            t = 0;
        } else {
            t = Double.parseDouble(tasker.getText().toString());
        }

        if (ws == 0 || as == 0) {
            result.setText("Enter Weapon and Attack Speed");
        } else if (t == 0) {
            bp = ws * (1 + as / 100);
            result.setText(String.format("%.5f", bp));
            setTableRow();
        } else {
            bp = ws * (1 + as / 100) * (1 + t / 100);
            result.setText(String.format("%.5f", bp));
            setTableRow();
        }



    }

        private void setTableRow(){

            t1.setBackgroundColor(Color.TRANSPARENT);
            t2.setBackgroundColor(Color.TRANSPARENT);
            t3.setBackgroundColor(Color.TRANSPARENT);
            t4.setBackgroundColor(Color.TRANSPARENT);
            t5.setBackgroundColor(Color.TRANSPARENT);
            t6.setBackgroundColor(Color.TRANSPARENT);
            t7.setBackgroundColor(Color.TRANSPARENT);
            t8.setBackgroundColor(Color.TRANSPARENT);

        double l1min = 0.98182; double l1max = 1.10204;
        double l2min = 1.10205; double l2max = 1.25581;
        double l3min = 1.25582; double l3max = 1.45945;
        double l4min =  1.45946; double l4max = 1.74193;
        double l5min = 1.74194; double l5max = 2.16;
        double l6min = 2.16001; double l6max = 2.84210;
        double l7min = 2.84211; double l7max = 4.15385;
        double l8min = 4.15386; double l8max = 999;

        if(bp > l1min && bp < l1max){
            t1.setBackgroundColor(Color.parseColor("#E4ED91"));
        }else if(bp > l2min && bp < l2max){
            t2.setBackgroundColor(Color.parseColor("#E4ED91"));
        }else if(bp > l3min && bp < l3max){
            t3.setBackgroundColor(Color.parseColor("#E4ED91"));
        }else if(bp > l4min && bp < l4max){
            t4.setBackgroundColor(Color.parseColor("#E4ED91"));
        }else if(bp > l5min && bp < l5max){
            t5.setBackgroundColor(Color.parseColor("#E4ED91"));
        }else if(bp > l6min && bp < l6max){
            t6.setBackgroundColor(Color.parseColor("#E4ED91"));
        }else if(bp > l7min && bp < l7max){
            t7.setBackgroundColor(Color.parseColor("#E4ED91"));
        }else if(bp > l8min && bp < l8max){
            t8.setBackgroundColor(Color.parseColor("#E4ED91"));
        }
            tl.setVisibility(View.VISIBLE);
    }


    public void ib_wc_dh_winfo(View view) {

        ip.showImage(this, R.drawable.weapon_attackspeed_example);
    }

    public void ib_wc_dh_ainfo(View view) {
        ip = new ImagePopUp();
        ip.showImage(this, R.drawable.player_attack_speed);
    }

    public void ib_wc_dh_tinfo(View view) {
        ip = new ImagePopUp();
        ip.showImage(this, R.drawable.tasker_example);
    }

    public void b_bc_dh_calc(View view) {
        //close the keyboard
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        calculate();
    }
}
