package com.d34n0s.www.d3weaponcalculator.helpers;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


/**
 * Created by dean on 14/11/2014.
 */
public class ItemPopUp {

    String preXML;
    String postXML;
    Context newContext;

    Integer isSkill = 0;
    StringBuilder webResults = new StringBuilder();

    ProgressDialog progressDialog;

    public void showWeapon(Context context, String url) {


        newContext = context;
        isSkill = 0;

        progressDialog = new ProgressDialog(context);
        progressDialog = ProgressDialog.show(context, "", "Please Wait", true, false);

        getData(url);

    }

    public void showSkills(Context context, String... url) {


        newContext = context;
        isSkill = 1;

        progressDialog = new ProgressDialog(context);
        progressDialog = ProgressDialog.show(context, "", "Please Wait", true, false);

        getData(url[0]);
        getData(url[1]);


    }

    private void getData(String url){

        Ion.with(newContext)
                .load(url)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e != null) {
                            Toast.makeText(newContext, "Error loading data", Toast.LENGTH_LONG).show();
                           Log.d(newContext.toString(), e.toString());

                            return;
                        }

                        progressDialog.dismiss();

                        webResults.append(result);

                        if(isSkill == 0){
                            Dialog builder = new Dialog(newContext);
                            builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            builder.getWindow().setBackgroundDrawable(
                                    new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialogInterface) {
                                    //nothing;
                                }
                            });

                            preXML = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                                    "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                                    "<head>\n" +
                                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                                    "<title>Untitled Document</title>\n" +
                                    "\n" +
                                    "\n" +
                                    "<link href=\"http://us.battle.net/d3/static/css/tooltips.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
                                    "</head>\n" +
                                    "\n" +
                                    "<body bgcolor=\"#000000\">\n" +
                                    "<table width=\"100%\" border=\"0\" bgcolor=\"#000000\">\n" +
                                    "  <tr>\n" +
                                    "    <td>";

                            postXML = "    </td>\n" +
                                    "  </tr>\n" +
                                    "</table>\n" +
                                    "\n" +
                                    "\n" +
                                    "</body>\n" +
                                    "</html>";

                            StringBuilder sb = new StringBuilder();
                            sb.append(preXML);
                            sb.append(webResults);
                            sb.append(postXML);

                            String html = sb.toString();

                            WebView webView = new WebView(newContext);
                            webView.loadData(html, "text/html", "utf-8");

                            builder.addContentView(webView, new RelativeLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT));
                            builder.show();
                        }else{
                            isSkill = 0;
                        }

                    }
                });
    }

}




