package com.d34n0s.www.d3weaponcalculator.views;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.d34n0s.www.d3weaponcalculator.R;

/**
 * Created by dean on 13/12/2014.
 */
public class BaseActivity extends ActionBarActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menuItem_weaponCalc) {
            Intent iwc = new Intent(this, WeaponDmgCalc.class);
            startActivity(iwc);
        }

        if (id == R.id.menuItem_armory) {
            Intent ia = new Intent(this, CharacterSelect.class);
            startActivity(ia);
        }

        if (id == R.id.menuItem_LegGemCalc) {
            Intent ialgc = new Intent(this, GemCalculator.class);
            startActivity(ialgc);
        }

        if (id == R.id.menuItem_breakPointCalc) {
            Intent ibc = new Intent(this, BreakpointCalculator.class);
            startActivity(ibc);
        }
        return super.onOptionsItemSelected(item);
    }

}
