package com.d34n0s.www.d3weaponcalculator.views;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.d34n0s.www.d3weaponcalculator.R;
import com.d34n0s.www.d3weaponcalculator.helpers.CharacterImageSelector;
import com.d34n0s.www.d3weaponcalculator.helpers.ItemPopUp;
import com.d34n0s.www.d3weaponcalculator.models.D3Items;
import com.d34n0s.www.d3weaponcalculator.models.D3Skills;
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
public class Character extends BaseActivity {

    TextView tvId;
    TextView tvName;
    TextView tvClass;
    ListView lvSkills;
    ListView lvItems;
    ImageView ivCharImage;
    ProgressDialog progressDialog;

    //Switch region variable
    String urlComplete;

    //this is the array to hold our skill class data
    ArrayList<D3Skills> arrayOfSkillData = new ArrayList<D3Skills>();

    //this is our adapter which is also a class variable
    FancySkillAdapter fSA = null;

    //this is the array to hold our skill class data
    ArrayList<D3Items> arrayOfItemData = new ArrayList<D3Items>();

    //this is our adapter which is also a class variable
    FancyItemAdapter fIA = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        tvId = (TextView) findViewById(R.id.tv_char_id);
        tvName = (TextView) findViewById(R.id.tv_char_name);
        tvClass = (TextView) findViewById(R.id.tv_char_class);
        lvSkills = (ListView) findViewById(R.id.lv_char_skills);
        lvItems = (ListView) findViewById(R.id.lv_char_items);
        ivCharImage = (ImageView) findViewById(R.id.iv_char_classGender);

        tvId.setText("Lv: " + getIntent().getExtras().getString("heroLevel"));
        tvName.setText(getIntent().getExtras().getString("heroName"));
        tvClass.setText(getIntent().getExtras().getString("heroClass"));

        //Set the Character Image
        Integer response = CharacterImageSelector.CharImage(getIntent().getExtras().getString("heroClass"), getIntent().getExtras().getString("heroGender"));
        ivCharImage.setImageResource(response);


        urlComplete = "https://" + getIntent().getExtras().getString("region")
                + getIntent().getExtras().getString("mainURL")
                + getIntent().getExtras().getString("battleTag")
                + "/hero/"
                + getIntent().getExtras().getString("heroId")
                + getIntent().getExtras().getString("apiKey");

        getCharDetails();

    }

    private void getCharDetails() {

        Log.d(this.toString(), "getCharDetails");

        progressDialog = new ProgressDialog(Character.this);
        progressDialog = ProgressDialog.show(Character.this, "", "Please Wait", true, false);

        Ion.with(this)
                .load(urlComplete)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        progressDialog.dismiss();

                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        String jsonResult = gson.toJson(result);

                        Log.d(this.toString(), result.toString());

                        try {

                            //SKILLS from json

                            JSONObject jsonSkills = new JSONObject(jsonResult);

                            JSONObject skills = jsonSkills.getJSONObject("skills");

                            JSONArray activeSkills = skills.getJSONArray("active");

                            JSONArray passiveSkills = skills.getJSONArray("passive");

                            //TEST
                            GsonBuilder gsonb = new GsonBuilder();
                            Gson gson1 = gsonb.create();

                            D3Skills d3s;

                            Log.d(this.toString(), "d3s Object Created");


                            for (int i = 0; i < activeSkills.length(); i++) {

                                JSONObject json_data = activeSkills.getJSONObject(i);
                                if(json_data.has("skill")) {
                                    JSONObject skill = json_data.getJSONObject("skill");

                                    d3s = gson1.fromJson(skill.toString(), D3Skills.class);

                                    if (skill.has("rune")) {
                                        JSONObject rune = json_data.getJSONObject("rune");
                                        d3s.skillRune = rune.getString("name");
                                        d3s.runeDesc = rune.getString("description");
                                        d3s.runeTT = rune.getString("tooltipParams");
                                    }

                                    d3s.skillType = "active";

                                    arrayOfSkillData.add(d3s);
                                }
                            }

                            for (int i = 0; i < passiveSkills.length(); i++) {

                                JSONObject json_data = passiveSkills.getJSONObject(i);
                                if(json_data.has("skill")){
                                JSONObject skill = json_data.getJSONObject("skill");

                                if(skill != null) {
                                    d3s = gson1.fromJson(skill.toString(), D3Skills.class);
                                    d3s.skillRune = "-Passive-";
                                    d3s.skillTT = skill.getString("tooltipUrl");
                                    d3s.skillType = "passive";

                                    arrayOfSkillData.add(d3s);
                                }
                                }
                            }

                        } catch (JSONException e1) {
                            e.printStackTrace();

                            Log.d(this.toString(), e1.toString());
                        }

                        //initialise our fancy adapter object
                        fSA = new FancySkillAdapter();

                        //set the adapter to turn it on
                        lvSkills.setAdapter(fSA);

                        lvSkills.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position,
                                                    long id) {

                                String runeURL = arrayOfSkillData.get(position).getRuneTT();
                                String skillURL = arrayOfSkillData.get(position).getSkillTT();
                                ItemPopUp ip = new ItemPopUp();
                                ip.showSkills(view.getContext(), "http://us.battle.net/d3/en/tooltip/" + skillURL, "http://us.battle.net/d3/en/tooltip/" + runeURL);

                                //ip.showWeapon(view.getContext(), "http://us.battle.net/d3/en/tooltip/item/CoMBCJ75xbgBEgcIBBVWt3pQHRWh2rodmwYAyx1mIwZQHY9Y1h4dyfq8oDCLAjjaAkAAUBJYBGDaAmorCgwIABD0s-GugoCAgBASGwig6relCxIHCAQVI5aumDCPAjgAQAFYBJABAIABRqUBj1jWHq0B42_MhLUBm9nZ77gBkPXbkQjAAQ0YrN3VhwpQCFgA");

                            }
                        });

                        //ITEMS from JSON
                        try {
                            JSONObject jsonItems = new JSONObject(jsonResult);

                            JSONObject items = jsonItems.getJSONObject("items");

                            //HEAD-------------------------------------------------
                            D3Items resultItemHeadRow = new D3Items();

                            if (items.has("head")) {
                                JSONObject head = items.getJSONObject("head");

                                resultItemHeadRow.itemSlot = "Head";
                                resultItemHeadRow.itemName = head.getString("name");
                                resultItemHeadRow.itemDisplayColor = head.getString("displayColor");
                                resultItemHeadRow.itemTT = head.getString("tooltipParams");

                                arrayOfItemData.add(resultItemHeadRow);
                            } else {
                                resultItemHeadRow.itemSlot = "Head";
                                resultItemHeadRow.itemName = "--This slot is empty--";
                                resultItemHeadRow.itemDisplayColor = "white";

                                arrayOfItemData.add(resultItemHeadRow);
                            }
                            //NECK-------------------------------------------------
                            D3Items resultItemneckRow = new D3Items();

                            if (items.has("neck")) {
                                JSONObject neck = items.getJSONObject("neck");

                                resultItemneckRow.itemSlot = "Neck";
                                resultItemneckRow.itemName = neck.getString("name");
                                resultItemneckRow.itemDisplayColor = neck.getString("displayColor");
                                resultItemneckRow.itemTT = neck.getString("tooltipParams");

                                arrayOfItemData.add(resultItemneckRow);
                            } else {
                                resultItemneckRow.itemSlot = "Neck";
                                resultItemneckRow.itemName = "--This slot is empty--";
                                resultItemneckRow.itemDisplayColor = "white";

                                arrayOfItemData.add(resultItemneckRow);
                            }
                            //SHOULDERS-------------------------------------------------
                            D3Items resultItemShouldersRow = new D3Items();

                            if (items.has("shoulders")) {
                                JSONObject shoulders = items.getJSONObject("shoulders");

                                resultItemShouldersRow.itemSlot = "Shoulders";
                                resultItemShouldersRow.itemName = shoulders.getString("name");
                                resultItemShouldersRow.itemDisplayColor = shoulders.getString("displayColor");
                                resultItemShouldersRow.itemTT = shoulders.getString("tooltipParams");

                                arrayOfItemData.add(resultItemShouldersRow);
                            } else {
                                resultItemShouldersRow.itemSlot = "Shoulders";
                                resultItemShouldersRow.itemName = "--This slot is empty--";
                                resultItemShouldersRow.itemDisplayColor = "white";

                                arrayOfItemData.add(resultItemShouldersRow);
                            }

                            //TORSO-------------------------------------------------
                            D3Items resultItemTorsoRow = new D3Items();

                            if (items.has("torso")) {
                                JSONObject torso = items.getJSONObject("torso");

                                resultItemTorsoRow.itemSlot = "Torso";
                                resultItemTorsoRow.itemName = torso.getString("name");
                                resultItemTorsoRow.itemDisplayColor = torso.getString("displayColor");
                                resultItemTorsoRow.itemTT = torso.getString("tooltipParams");

                                arrayOfItemData.add(resultItemTorsoRow);
                            } else {
                                resultItemTorsoRow.itemSlot = "Torso";
                                resultItemTorsoRow.itemName = "--This slot is empty--";
                                resultItemTorsoRow.itemDisplayColor = "white";

                                arrayOfItemData.add(resultItemTorsoRow);
                            }
                            //BRACERS-------------------------------------------------
                            D3Items resultItembracersRow = new D3Items();

                            if (items.has("bracers")) {
                                JSONObject bracers = items.getJSONObject("bracers");

                                resultItembracersRow.itemSlot = "bracers";
                                resultItembracersRow.itemName = bracers.getString("name");
                                resultItembracersRow.itemDisplayColor = bracers.getString("displayColor");
                                resultItembracersRow.itemTT = bracers.getString("tooltipParams");

                                arrayOfItemData.add(resultItembracersRow);
                            } else {
                                resultItembracersRow.itemSlot = "bracers";
                                resultItembracersRow.itemName = "--This slot is empty--";
                                resultItembracersRow.itemDisplayColor = "white";

                                arrayOfItemData.add(resultItembracersRow);
                            }
                            //HANDS-------------------------------------------------
                            D3Items resultItemHandsRow = new D3Items();

                            if (items.has("hands")) {
                                JSONObject hands = items.getJSONObject("hands");

                                resultItemHandsRow.itemSlot = "Hands";
                                resultItemHandsRow.itemName = hands.getString("name");
                                resultItemHandsRow.itemDisplayColor = hands.getString("displayColor");
                                resultItemHandsRow.itemTT = hands.getString("tooltipParams");

                                arrayOfItemData.add(resultItemHandsRow);
                            } else {
                                resultItemHandsRow.itemSlot = "Hands";
                                resultItemHandsRow.itemName = "--This slot is empty--";
                                resultItemHandsRow.itemDisplayColor = "white";

                                arrayOfItemData.add(resultItemHandsRow);
                            }
                            //LEFT FINGER-------------------------------------------------
                            D3Items resultItemleftFingerRow = new D3Items();

                            if (items.has("leftFinger")) {
                                JSONObject leftFinger = items.getJSONObject("leftFinger");

                                resultItemleftFingerRow.itemSlot = "Left Finger";
                                resultItemleftFingerRow.itemName = leftFinger.getString("name");
                                resultItemleftFingerRow.itemDisplayColor = leftFinger.getString("displayColor");
                                resultItemleftFingerRow.itemTT = leftFinger.getString("tooltipParams");

                                arrayOfItemData.add(resultItemleftFingerRow);
                            } else {
                                resultItemleftFingerRow.itemSlot = "Left Finger";
                                resultItemleftFingerRow.itemName = "--This slot is empty--";
                                resultItemleftFingerRow.itemDisplayColor = "white";

                                arrayOfItemData.add(resultItemleftFingerRow);
                            }
                            //RIGHT FINGER-------------------------------------------------
                            D3Items resultItemrightFingerRow = new D3Items();

                            if (items.has("rightFinger")) {
                                JSONObject rightFinger = items.getJSONObject("rightFinger");

                                resultItemrightFingerRow.itemSlot = "Right Finger";
                                resultItemrightFingerRow.itemName = rightFinger.getString("name");
                                resultItemrightFingerRow.itemDisplayColor = rightFinger.getString("displayColor");
                                resultItemrightFingerRow.itemTT = rightFinger.getString("tooltipParams");

                                arrayOfItemData.add(resultItemrightFingerRow);
                            } else {
                                resultItemrightFingerRow.itemSlot = "Right Finger";
                                resultItemrightFingerRow.itemName = "--This slot is empty--";
                                resultItemrightFingerRow.itemDisplayColor = "white";

                                arrayOfItemData.add(resultItemrightFingerRow);
                            }
                            //WAIST-------------------------------------------------
                            D3Items resultItemwaistRow = new D3Items();

                            if (items.has("waist")) {
                                JSONObject waist = items.getJSONObject("waist");

                                resultItemwaistRow.itemSlot = "Waist";
                                resultItemwaistRow.itemName = waist.getString("name");
                                resultItemwaistRow.itemDisplayColor = waist.getString("displayColor");
                                resultItemwaistRow.itemTT = waist.getString("tooltipParams");

                                arrayOfItemData.add(resultItemwaistRow);
                            } else {
                                resultItemwaistRow.itemSlot = "Waist";
                                resultItemwaistRow.itemName = "--This slot is empty--";
                                resultItemwaistRow.itemDisplayColor = "white";

                                arrayOfItemData.add(resultItemwaistRow);
                            }
                            //LEGS-------------------------------------------------
                            D3Items resultItemlegsRow = new D3Items();

                            if (items.has("legs")) {
                                JSONObject legs = items.getJSONObject("legs");

                                resultItemlegsRow.itemSlot = "Legs";
                                resultItemlegsRow.itemName = legs.getString("name");
                                resultItemlegsRow.itemDisplayColor = legs.getString("displayColor");
                                resultItemlegsRow.itemTT = legs.getString("tooltipParams");

                                arrayOfItemData.add(resultItemlegsRow);
                            } else {
                                resultItemlegsRow.itemSlot = "Legs";
                                resultItemlegsRow.itemName = "--This slot is empty--";
                                resultItemlegsRow.itemDisplayColor = "white";

                                arrayOfItemData.add(resultItemlegsRow);
                            }
                            //FEET-------------------------------------------------
                            D3Items resultItemFeetRow = new D3Items();

                            if (items.has("feet")) {
                                JSONObject feet = items.getJSONObject("feet");

                                resultItemFeetRow.itemSlot = "Feet";
                                resultItemFeetRow.itemName = feet.getString("name");
                                resultItemFeetRow.itemDisplayColor = feet.getString("displayColor");
                                resultItemFeetRow.itemTT = feet.getString("tooltipParams");

                                arrayOfItemData.add(resultItemFeetRow);
                            } else {
                                resultItemFeetRow.itemSlot = "Feet";
                                resultItemFeetRow.itemName = "--This slot is empty--";
                                resultItemFeetRow.itemDisplayColor = "white";

                                arrayOfItemData.add(resultItemFeetRow);
                            }
                            //MAIN HAND-------------------------------------------------
                            D3Items resultItemmainHandRow = new D3Items();

                            if (items.has("mainHand")) {
                                JSONObject mainHand = items.getJSONObject("mainHand");

                                resultItemmainHandRow.itemSlot = "Main Hand";
                                resultItemmainHandRow.itemName = mainHand.getString("name");
                                resultItemmainHandRow.itemDisplayColor = mainHand.getString("displayColor");
                                resultItemmainHandRow.itemTT = mainHand.getString("tooltipParams");

                                arrayOfItemData.add(resultItemmainHandRow);
                            } else {
                                resultItemmainHandRow.itemSlot = "Main Hand";
                                resultItemmainHandRow.itemName = "--This slot is empty--";
                                resultItemmainHandRow.itemDisplayColor = "white";

                                arrayOfItemData.add(resultItemmainHandRow);
                            }
                            //OFFHAND-------------------------------------------------
                            D3Items resultItemoffHandRow = new D3Items();

                            if (items.has("offHand")) {
                                JSONObject offHand = items.getJSONObject("offHand");

                                resultItemoffHandRow.itemSlot = "Off Hand";
                                resultItemoffHandRow.itemName = offHand.getString("name");
                                resultItemoffHandRow.itemDisplayColor = offHand.getString("displayColor");
                                resultItemoffHandRow.itemTT = offHand.getString("tooltipParams");

                                arrayOfItemData.add(resultItemoffHandRow);
                            } else {
                                resultItemoffHandRow.itemSlot = "Off Hand";
                                resultItemoffHandRow.itemName = "--This slot is empty--";
                                resultItemoffHandRow.itemDisplayColor = "white";

                                arrayOfItemData.add(resultItemoffHandRow);
                            }

                        } catch (JSONException e1) {
                            e.printStackTrace();
                        }

                        //initialise our fancy adapter object
                        fIA = new FancyItemAdapter();

                        //set the adapter to turn it on
                        lvItems.setAdapter(fIA);

                        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position,
                                                    long id) {

                                String itemURL = arrayOfItemData.get(position).getItemTT();
                                ItemPopUp ip = new ItemPopUp();
                                ip.showWeapon(view.getContext(), "http://us.battle.net/d3/en/tooltip/" + itemURL);
                                //ip.showWeapon(view.getContext(), "http://us.battle.net/d3/en/tooltip/item/CoMBCJ75xbgBEgcIBBVWt3pQHRWh2rodmwYAyx1mIwZQHY9Y1h4dyfq8oDCLAjjaAkAAUBJYBGDaAmorCgwIABD0s-GugoCAgBASGwig6relCxIHCAQVI5aumDCPAjgAQAFYBJABAIABRqUBj1jWHq0B42_MhLUBm9nZ77gBkPXbkQjAAQ0YrN3VhwpQCFgA");

                            }
                        });
                    }

                });

    }

                    class FancySkillAdapter extends ArrayAdapter<D3Skills> {

                        FancySkillAdapter(){
                            super(Character.this, android.R.layout.simple_list_item_1, arrayOfSkillData);
                        }

                        public View getView(int position, View convertView, ViewGroup parent) {
                            SkillsViewHolder holder;

                            //we use an if statement on our view that is passed in, to see if ti has been recycled or not.
                            // if it's been recycled, then it already exists and we don't need to call the inflater function.
                            // this saves heaps of time and resources.
                            if (convertView == null) {
                                LayoutInflater inflater = getLayoutInflater();
                                convertView = inflater.inflate(R.layout.activity_character_skills_list, null);

                                //we're using a view holder class to cache the result of the findViewbyID function which we then store in a tag on the view
                                holder = new SkillsViewHolder(convertView);

                                convertView.setTag(holder);
                            } else {
                                holder = (SkillsViewHolder) convertView.getTag();
                            }
                            holder.populateFrom(arrayOfSkillData.get(position));

                            return(convertView);
                        }
                    }

                    class SkillsViewHolder {
                        public TextView list_skillName = null;
                        public TextView list_skillRune = null;
                        public LinearLayout ll_row = null;


                        SkillsViewHolder(View resultRow){
                            list_skillName = (TextView) resultRow.findViewById(R.id.tv_char_skills_name);
                            list_skillRune = (TextView) resultRow.findViewById(R.id.tv_char_skills_rune);
                            ll_row = (LinearLayout) resultRow.findViewById(R.id.ll_char_skills_row);
                        }

                        void populateFrom(D3Skills d3s) {
                            //set the basic data on each element
                            list_skillName.setText(d3s.skillName);
                            list_skillRune.setText(d3s.skillRune);
/*                            if(d3s.skillType.matches("active")){
                                ll_row.setBackgroundColor(Color.parseColor("#9CEE8A"));
                            }else if(d3s.skillType.matches("passive")){
                                ll_row.setBackgroundColor(Color.parseColor("#EEBB7E"));
                            }*/

                        }
                    }



                    class FancyItemAdapter extends ArrayAdapter<D3Items> {

                        FancyItemAdapter(){
                            super(Character.this, android.R.layout.simple_list_item_1, arrayOfItemData);
                        }

                        public View getView(int position, View convertView, ViewGroup parent) {
                            ItemsViewHolder holder;

                            //we use an if statement on our view that is passed in, to see if ti has been recycled or not.
                            // if it's been recycled, then it already exists and we don't need to call the inflater function.
                            // this saves heaps of time and resources.
                            if (convertView == null) {
                                LayoutInflater inflater = getLayoutInflater();
                                convertView = inflater.inflate(R.layout.activity_character_item_list, null);

                                //we're using a view holder class to cache the result of the findViewbyID function which we then store in a tag on the view
                                holder = new ItemsViewHolder(convertView);

                                convertView.setTag(holder);
                            } else {
                                holder = (ItemsViewHolder) convertView.getTag();
                            }
                            holder.populateFrom(arrayOfItemData.get(position));

                            return(convertView);
                        }
                    }

                    class ItemsViewHolder {
                        public TextView list_itemSlot = null;
                        public TextView list_itemName = null;
                        public LinearLayout list_textColor = null;


                        ItemsViewHolder(View resultRow) {
                            list_itemSlot = (TextView) resultRow.findViewById(R.id.tv_char_item_slot);
                            list_itemName = (TextView) resultRow.findViewById(R.id.tv_char_item_name);
                            list_textColor = (LinearLayout) resultRow.findViewById(R.id.ll_char_item_list);
                        }

                        void populateFrom(D3Items d3s) {
                            //set the basic data on each element
                            list_itemSlot.setText(d3s.itemSlot);
                            list_itemName.setText(d3s.itemName);

                            //set colour of bg
                            if (d3s.itemDisplayColor.contentEquals("orange")) {

                                list_itemName.setTextColor(Color.parseColor("#EEBB7E"));

                            } else if (d3s.itemDisplayColor.contentEquals("green")) {

                                list_itemName.setTextColor(Color.parseColor("#9CEE8A"));

                            } else if (d3s.itemDisplayColor.contentEquals("yellow")) {

                                list_itemName.setTextColor(Color.parseColor("#FFFFA5"));

                            } else {

                                list_itemName.setTextColor(Color.WHITE);

                            }
                        }
                    }
                }

