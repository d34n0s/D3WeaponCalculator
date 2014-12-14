package com.d34n0s.www.d3weaponcalculator.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.d34n0s.www.d3weaponcalculator.R;
import com.d34n0s.www.d3weaponcalculator.helpers.CharacterImageSelector;
import com.d34n0s.www.d3weaponcalculator.helpers.DateFormatter;
import com.d34n0s.www.d3weaponcalculator.models.D3Heroes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dean on 13/12/2014.
 */
public class CharacterSelect extends BaseActivity {

    Switch sRegion;
    EditText etName;
    TextView etNumber;
    ListView lvCharResults;
    ProgressDialog progressDialog;

    String battleTag; //values from edit text fields
    String mainURL = ".api.battle.net/d3/profile/";
    String apiKey = "?locale=en_US&apikey=8rujpfqknnxsmvbm3yt6b8j8utg9dmyu";
    String urlComplete;

    //Switch region variable
    String region = "us";

    //setup shared prefs file
    public static String prefsFilename = "CharSelect_sharedPrefsDataFile";
    SharedPreferences prefsSP;

    //this is the array to hold our class data
    ArrayList<D3Heroes> arrayOfWebData = new ArrayList<D3Heroes>();

    //this is our adapter which is also a class variable
    FancyAdapter fa = null;

    //this array is used to temporarily hold our json rows, for each row we returned
    //until we use the array to create our person object
    static ArrayList<String> resultRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_select);

        etName = (EditText) findViewById(R.id.et_charSelect_name);
        etNumber = (EditText) findViewById(R.id.et_charSelect_number);

        lvCharResults = (ListView) findViewById(R.id.lv_charSelect);

        sRegion = (Switch) findViewById(R.id.sw_charSelect_Region);
        sRegion.setChecked(false);
        sRegion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    region = "eu";
                }else{
                    region= "us";
                }

            }
        });

        //change region picker based on loading of shared prefs
        if(region.matches("us")){
            sRegion.setChecked(false);
        }
        else if(region.matches("eu")){
            sRegion.setChecked(true);
        }else{
            sRegion.setChecked(false);
        }

        //check the current state before we display the screen
        if(sRegion.isChecked()){
            region = "eu";
        }
        else {
            region = "us";
        }

        loadSharedPrefs();

    }

    private void loadSharedPrefs(){

        prefsSP = getSharedPreferences(prefsFilename, 0);


            region = prefsSP.getString("region", "us");
            String userName = prefsSP.getString("userName", "");
            etName.setText(userName);
            String number = prefsSP.getString("number", "");
            etNumber.setText(number);

    }


    private void saveSharedPrefs(){

        SharedPreferences.Editor editor = prefsSP.edit();

        editor.putString("region", region);
        String userName = etName.getText().toString();
        editor.putString("userName", userName);
        String number = etNumber.getText().toString();
        editor.putString("number", number);

        editor.apply();

    }

    private void doLogin(){

        progressDialog = new ProgressDialog(this);
        progressDialog = ProgressDialog.show(this, "", "Loading...", true, true);

        battleTag = etName.getText().toString() + "-" +etNumber.getText().toString();
        urlComplete = "https://" + region + mainURL + battleTag + "/" + apiKey;

        Log.d(this.toString(), urlComplete);

        Ion.with(this)
                .load(urlComplete)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e != null) {
                            Toast.makeText(CharacterSelect.this, "An error occurred please try again later", Toast.LENGTH_LONG).show();
                            Log.d(this.toString(), e.toString());
                            progressDialog.dismiss();
                            return;
                        }

                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        String jsonResult = gson.toJson(result);

                        Log.d(this.toString(), result.toString());

                        JSONObject json;

                        try {
                             json = new JSONObject(jsonResult);

                            if (json.has("reason")){
                                String reason = json.getString("reason");

                                Toast t = Toast.makeText(CharacterSelect.this, reason, Toast.LENGTH_SHORT);
                                t.show();

                            }else {
                                JSONArray heroes = json.getJSONArray("heroes");

                                int i = 0;
                                while (i < heroes.length()) {

                                    JSONObject json_data = heroes.getJSONObject(i);
                                    D3Heroes resultRow = new D3Heroes();

                                    resultRow.id = json_data.getString("id");
                                    resultRow.name = json_data.getString("name");
                                    resultRow.paragonLevel = json_data.getString("paragonLevel");
                                    resultRow.seasonal = json_data.getString("seasonal");
                                    resultRow.level = json_data.getString("level");
                                    resultRow.hardcore = json_data.getString("hardcore");
                                    resultRow.gender = json_data.getString("gender");
                                    resultRow.dead = json_data.getString("dead");
                                    resultRow.playerClass = json_data.getString("class");
                                    resultRow.last_updated = json_data.getString("last-updated");

                                    arrayOfWebData.add(resultRow);

                                    i++;
                                }
                            }

                            //tvJSON.setText(result);

                        } catch (JSONException e1) {
                            e.printStackTrace();
                        }


                        progressDialog.dismiss();
                        fa = new FancyAdapter();

                        //set the adapter to turn it on
                        lvCharResults.setAdapter(fa);

                        lvCharResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position,
                                                    long id) {

                                Intent showCharacterIntent = new Intent(view.getContext(), Character.class);

                                String heroId = arrayOfWebData.get(position).getId();
                                String heroName = arrayOfWebData.get(position).getHeroName();
                                String heroClass = arrayOfWebData.get(position).getPlayerClass();
                                String heroGender = arrayOfWebData.get(position).getPlayerGender();
                                String heroLevel = arrayOfWebData.get(position).getLevel();

                                showCharacterIntent.putExtra("heroName", heroName);
                                showCharacterIntent.putExtra("heroClass", heroClass);
                                showCharacterIntent.putExtra("heroGender", heroGender);
                                showCharacterIntent.putExtra("heroLevel", heroLevel);
                                showCharacterIntent.putExtra("heroId", heroId);
                                showCharacterIntent.putExtra("region", region);
                                showCharacterIntent.putExtra("mainURL", mainURL);
                                showCharacterIntent.putExtra("battleTag", battleTag);
                                showCharacterIntent.putExtra("apiKey", apiKey);

                                startActivity(showCharacterIntent);

                            }
                        });
                    }
                });

    }

    class FancyAdapter extends ArrayAdapter<D3Heroes> {

        FancyAdapter(){
            super(CharacterSelect.this, android.R.layout.simple_list_item_1, arrayOfWebData);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            //we use an if statement on our view that is passed in, to see if ti has been recycled or not.
            // if it's been recycled, then it already exists and we don't need to call the inflater function.
            // this saves heaps of time and resources.
            if (convertView == null) {
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.activity_character_select_list, null);

                //we're using a view holder class to cache the result of the findViewbyID function which we then store in a tag on the view
                holder = new ViewHolder(convertView);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.populateFrom(arrayOfWebData.get(position));

            return(convertView);
        }
    }

    class ViewHolder {
        public ImageView list_Img = null;
        public TextView list_name = null;
        public TextView list_level = null;
        public TextView list_class = null;
        public TextView list_lastUpdate = null;
        public TextView list_heroId = null;

        ViewHolder(View resultRow){
            list_Img = (ImageView) resultRow.findViewById(R.id.tv_charList_pic);
            list_name = (TextView) resultRow.findViewById(R.id.tv_charList_name);
            list_level = (TextView) resultRow.findViewById(R.id.tv_charList_level);
            list_class = (TextView) resultRow.findViewById(R.id.tv_charList_playerClass);
            list_lastUpdate = (TextView) resultRow.findViewById(R.id.tv_charList_lastUpdated);
            list_heroId = (TextView) resultRow.findViewById((R.id.tv_charList_id));

        }

        String populateFrom(D3Heroes d3) {
            //set the basic data on each element
            list_name.setText(d3.name);
            list_level.setText("Lv: " + d3.level);
            list_class.setText(d3.playerClass);
            //list_heroId.setText(d3.id);

            //convert the date from milliseconds to data
            DateFormatter df = new DateFormatter();
            Long l = Long.parseLong(d3.last_updated);
            String formattedDate = df.getDate(l*1000, "dd/MM/yyyy");
            list_lastUpdate.setText("Updated:\n" + formattedDate);

            //set the character image via the CharacterImageSelector class
            CharacterImageSelector cis = new CharacterImageSelector();
            Integer response = cis.CharImage(d3.playerClass, d3.gender);
            list_Img.setImageResource(response);
            return null;


        }
    }

    public void b_characterSelect_Get(View view) {

        doLogin();


        saveSharedPrefs();
    }
}
