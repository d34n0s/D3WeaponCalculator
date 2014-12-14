package com.d34n0s.www.d3weaponcalculator.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.d34n0s.www.d3weaponcalculator.R;


public class Launcher extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void b_ls_weaponCalc(View view) {
        Intent iwc = new Intent(view.getContext(), WeaponDmgCalc.class);
        startActivity(iwc);
    }

    public void b_ls_armory(View view) {
        Intent ia = new Intent(view.getContext(), CharacterSelect.class);
        startActivity(ia);
    }

    public void b_ls_legendary_gem_calculator(View view) {
        Intent ialgc = new Intent(view.getContext(), GemCalculator.class);
        startActivity(ialgc);
    }

    public void b_ls_breakpointCalculator(View view) {
        Intent ibc = new Intent(view.getContext(), BreakpointCalculator.class);
        startActivity(ibc);
    }
}
