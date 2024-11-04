package com.example.beershop.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.beershop.Activities.LoginDetails.LoginActivity;
import com.example.beershop.Models.Compaign.C_Discount;
import com.example.beershop.Models.Compaign.C_Redemption;
import com.example.beershop.Models.Compaign.Com_metadata;
import com.example.beershop.Models.Compaign.CompaignData;
import com.example.beershop.Models.Compaign.Voucher;
import com.example.beershop.Models.Customer.Address;
import com.example.beershop.Models.Customer.C_Metadata;
import com.example.beershop.Models.Customer.CustomerData;
import com.example.beershop.Models.Publication.Campaign;
import com.example.beershop.Models.Publication.Customer;
import com.example.beershop.Models.Publication.P_Metadata;
import com.example.beershop.Models.Publication.PublicationData;
import com.example.beershop.Models.Voucher.Code_Config;
import com.example.beershop.Models.Voucher.Discount;
import com.example.beershop.Models.Voucher.Metadata;
import com.example.beershop.Models.Voucher.Redemption;
import com.example.beershop.Models.Voucher.VoucherData;
import com.example.beershop.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ScreeningActivity extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private String id = "";
    private String name, email;
    private String Voucher_Code = "";
    private String Customer_id = "";
    private Toolbar toolbar;
    private Drawable unwrappedDrawable, wrappedDrawable;
    private String  voucher_url="";
    private ProgressDialog pd;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private boolean isMembersVisible = false;
    private NavigationView navigationView;
    private int check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening);

        TextView yes = findViewById(R.id.yes);
        TextView no = findViewById(R.id.no);
        TextView potentially = findViewById(R.id.potentially);

        mAuth=FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("Please wait...");
        SharedPreferences prefs = getSharedPreferences("User Data", MODE_PRIVATE);
        name = prefs.getString("name","");
        email = prefs.getString("email","");

        Compaing(name);


        SharedPreferences sharedpreferences = getSharedPreferences("Check Data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("check","Screen");
        editor.apply();
        editor.commit();

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Voucher(1);

            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedpreferences = getSharedPreferences("Check Data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("check","Yes");
                editor.putString("udid",mAuth.getCurrentUser().getUid());
                editor.apply();
                editor.commit();

                Intent intent = new Intent(ScreeningActivity.this, CustomerLandingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        potentially.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedpreferences = getSharedPreferences("Check Data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("check","Yes");
                editor.putString("udid",mAuth.getCurrentUser().getUid());
                editor.apply();
                editor.commit();

                Intent intent = new Intent(ScreeningActivity.this, CustomerLandingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        setUpDrawer();

    }

    public void setUpDrawer(){
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        unwrappedDrawable = AppCompatResources.getDrawable(this, R.drawable.ic_menu_black);
        wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setClickable(true);
        mToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        setupNavigationView();
        mToggle.setHomeAsUpIndicator(wrappedDrawable);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.setDrawerIndicatorEnabled(false);
        mToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        navigationView.setCheckedItem(R.id.switchToggleButton);
//        navigationView.getMenu().performIdentifierAction(R.id.switchToggleButton, 0);
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void setupNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switchScreen(item.getItemId());
                return true;
            }

        });
    }

    private void switchScreen(int id) {

        switch (id){

            case R.id.mainCustomerFragment:
                startActivity(new Intent(this, ScreeningActivity.class));
                mDrawerLayout.closeDrawers();
                finish();
                break;

            case R.id.userProfileFragment:
                startActivity(new Intent(this, ProfileActivity.class));
                mDrawerLayout.closeDrawers();
                break;

            case R.id.map:
                startActivity(new Intent(this, MapActivity.class));
                mDrawerLayout.closeDrawers();
                break;

            case R.id.tokens:
                MenuItem t_received = navigationView.getMenu().findItem(R.id.creditsReceivedFragment);
                MenuItem t_remaining = navigationView.getMenu().findItem(R.id.creditsRemainingFragment);
             //   MenuItem t_used = navigationView.getMenu().findItem(R.id.orderHistoryFragment);

                MenuItem menuItem = navigationView.getMenu().findItem(R.id.tokens);
                final ImageView drawerSwitch = (ImageView) menuItem.getActionView().findViewById(R.id.drawer_switch);

                if (!isMembersVisible) {
                    t_received.setVisible(true);
                    t_remaining.setVisible(true);
                  //  t_used.setVisible(true);
                    isMembersVisible = true;
                    drawerSwitch.setImageResource(R.drawable.ic_up);
                } else {
                    t_received.setVisible(false);
                    t_remaining.setVisible(false);
                 //   t_used.setVisible(false);
                    isMembersVisible = false;
                    drawerSwitch.setImageResource(R.drawable.ic_down);
                }
                break;

            case R.id.creditsReceivedFragment:
                startActivity(new Intent(this, CreditsReceivedActivity.class));
                mDrawerLayout.closeDrawers();
                break;

            case R.id.creditsRemainingFragment:
                startActivity(new Intent(this, CreditsRemainingActivity.class));
                mDrawerLayout.closeDrawers();
                break;

//            case R.id.orderHistoryFragment:
//                startActivity(new Intent(this, OrderHistoryActivity.class));
//                mDrawerLayout.closeDrawers();
//                finish();
//                break;

            case R.id.refferals:
                startActivity(new Intent(this, ReferralsActivity.class));
                mDrawerLayout.closeDrawers();
                break;

            case R.id.logout:
                Logout();
                mDrawerLayout.close();
                break;
        }
    }


    @Override
    public void onBackPressed() {

        showCustomDialog1();

    }
    public void Voucher(int quantity){

        pd.show();
        Gson gson = new Gson();

        Code_Config code_config = new Code_Config("PROMO-#####");

        Discount discount = new Discount(100, "PERCENT");

        Metadata metadata = new Metadata(true, "de-en");

        final Redemption redemption = new Redemption(quantity);

        VoucherData voucherData = new VoucherData("Customers Vouchers","DISCOUNT_VOUCHER","2019-01-01T00:00:00Z","2020-12-31T23:59:59Z",code_config,discount, redemption,metadata);

        final String json = gson.toJson(voucherData);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = "https://us1.api.voucherify.io/v1/vouchers";

        final String requestBody = json.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", response);

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    Object ob = jsonObject.getJSONObject("assets");
                    Object os = ((JSONObject) ob).getJSONObject("qr");
                    voucher_url = ((JSONObject) os).getString("url");
                    Voucher_Code = jsonObject.getString("code");

                    id = jsonObject.getString("id");
//
                    Log.i("iii",""+Voucher_Code);
                    Log.i("uuu",""+voucher_url);

                    if(voucher_url.equals("")){

                        pd.dismiss();
                        Dialogue();
                    }else {
                        SharedPreferences sharedpreferences = getSharedPreferences("Voucher no", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("url", voucher_url);
                        editor.putString("code", Voucher_Code);
                        editor.putString("uid", mAuth.getCurrentUser().getUid());
                        editor.apply();
                        editor.commit();

                        Customer(Voucher_Code);
                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                final Map<String, String> params = new HashMap<>();
                params.put("X-App-Id" , "2986fb82-6dae-4e5b-a070-ec57e8849b84");
                params.put("X-App-Token" , "90bf01af-4ac2-4d0e-970d-04ff3a639bfb");
                params.put("Content-Type" , "application/json");
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    public void Customer(final String v_id){

        Gson gson = new Gson();

        Address address = new Address("Melbourne", "FL", "226 E Fee Ave", "null", "Australia", "32901");

        C_Metadata c_metadata =  new C_Metadata("en");

        CustomerData customerData =  new CustomerData(id, name, email, "Premium user, ACME Inc.", address, c_metadata);

        final String json1 = gson.toJson(customerData);

        Log.i("ccc",""+json1);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://us1.api.voucherify.io/v1/customers";

        final String requestBody = json1.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Customer", response);
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    Customer_id = jsonObject.getString("id");
                    Log.i("cic",""+Customer_id);

                    Publication(v_id,Customer_id);

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Customer", error.toString());

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                final Map<String, String> params = new HashMap<>();
                params.put("X-App-Id" , "2986fb82-6dae-4e5b-a070-ec57e8849b84");
                params.put("X-App-Token" , "90bf01af-4ac2-4d0e-970d-04ff3a639bfb");
                params.put("Content-Type" , "application/json");
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    public void Compaing(String name){

        Gson gson = new Gson();

        C_Discount c_discount = new C_Discount("100.0","PERCENT");

        C_Redemption c_redemption = new C_Redemption(10);

        Code_Config code_config = new Code_Config("TC6-PROMO-#######");

        Voucher voucher = new Voucher("DISCOUNT_VOUCHER",c_discount,c_redemption,code_config);

        Com_metadata com_metadata = new Com_metadata(true);

        CompaignData compaignData = new CompaignData(name, "2016-10-26T00:00:00Z", "2025-12-26T00:00:00Z", 10, voucher, com_metadata);

        final String json1 = gson.toJson(compaignData);

        Log.i("com",""+json1);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://us1.api.voucherify.io/v1/campaigns";

        final String requestBody = json1.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Compaign", response);
                try {

                    JSONObject jsonObject = new JSONObject(response);

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Compaign", error.toString());

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                final Map<String, String> params = new HashMap<>();
                params.put("X-App-Id" , "2986fb82-6dae-4e5b-a070-ec57e8849b84");
                params.put("X-App-Token" , "90bf01af-4ac2-4d0e-970d-04ff3a639bfb");
                params.put("Content-Type" , "application/json");
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    public void Publication(String v_code, String c_id){

        Gson gson = new Gson();

        Campaign campaign = new Campaign(name, 1);

        Customer customer = new Customer(c_id,email,name);

        P_Metadata p_metadata = new P_Metadata(true, "Shop Admin");

        PublicationData publicationData = new PublicationData(campaign, customer, p_metadata, v_code);

        final String json1 = gson.toJson(publicationData);

        Log.i("ppp",""+json1);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://us1.api.voucherify.io/v1/publications";

        final String requestBody = json1.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("publication", response);
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    SharedPreferences sharedpreferences = getSharedPreferences("Check Data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("check","No");
                    editor.putString("udid",mAuth.getCurrentUser().getUid());
                    editor.apply();
                    editor.commit();

                    pd.dismiss();
                    Intent intent = new Intent(ScreeningActivity.this, No_Activity.class);
                    startActivity(intent);
                    finish();

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("publication", error.toString());

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                final Map<String, String> params = new HashMap<>();
                params.put("X-App-Id" , "2986fb82-6dae-4e5b-a070-ec57e8849b84");
                params.put("X-App-Token" , "90bf01af-4ac2-4d0e-970d-04ff3a639bfb");
                params.put("Content-Type" , "application/json");
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void showCustomDialog1() {
        final PrettyDialog pDialog = new PrettyDialog(ScreeningActivity.this);
        pDialog
                .setTitle(getString(R.string.app_name))
                .setMessage("Are you sure you want to Exit?")
                .setIcon(R.drawable.pdlg_icon_info)
                .setIconTint(R.color.colorPrimary)
                .addButton(
                        "Yes",
                        R.color.colorPrimary,
                        R.color.pdlg_color_white,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                finishAffinity();
                                pDialog.dismiss();
                            }
                        }
                )
                .addButton("No",
                        R.color.pdlg_color_red,
                        R.color.pdlg_color_white,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                pDialog.dismiss();
                            }
                        })
                .show();
    }


    public void Logout() {

        final PrettyDialog pDialog = new PrettyDialog(ScreeningActivity.this);
        pDialog
                .setTitle(getString(R.string.app_name))
                .setMessage("Are you sure you want to logout?")
                .setIcon(R.drawable.pdlg_icon_info)
                .setIconTint(R.color.colorPrimary)
                .addButton(
                        "Yes",
                        R.color.colorPrimary,
                        R.color.pdlg_color_white,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {

                                mAuth.getInstance().signOut();

                                SharedPreferences sharedpreferences = getSharedPreferences("User Data", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("name", "");
                                editor.putString("email", "");
                                editor.putString("image", "");
                                editor.putString("udid", "");
                                editor.apply();
                                editor.commit();

                                Toast.makeText(getApplicationContext(), "Sign out successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ScreeningActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finishAffinity();
                                pDialog.dismiss();
                            }
                        }
                )
                .addButton("No",
                        R.color.pdlg_color_red,
                        R.color.pdlg_color_white,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                pDialog.dismiss();
                            }
                        })
                .show();

    }

    public void Dialogue() {

        new AlertDialog.Builder(ScreeningActivity.this)
                .setTitle("\n\nBet4Beers")
                .setMessage("\nOoopss...Something went wrong in Assigning Tier Two Token.\nPlease Retry.\n\n\n")
                .setCancelable(false)
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Voucher(1);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }
}
