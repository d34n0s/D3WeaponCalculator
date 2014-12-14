package com.d34n0s.www.d3weaponcalculator.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.d34n0s.www.d3weaponcalculator.R;

/**
 * Created by dean on 13/12/2014.
 */
public class BreakpointCalculator extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakpoint_calculator_menu);
    }


    public void b_bpcm_demonHunter(View view) {
        Intent idh = new Intent(view.getContext(), BreakpointCalculator_DemonHunter.class);
        startActivity(idh);
    }
}
