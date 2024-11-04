package com.example.beershop.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.beershop.Adapters.CreditsAdapter;
import com.example.beershop.Models.CreditsData;
import com.example.beershop.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderHistoryActivity extends AppCompatActivity {

    private String quantity1, u1;
    private FirebaseAuth mAuth;
    private RecyclerView credits_receivedrv;
    private ProgressDialog pd;
    private int remaining;
    private DatabaseReference mDatabase;
    private ArrayList<CreditsData> creditsModel = new ArrayList<>();
    private ArrayList<Integer> codes = new ArrayList<>();
    private CreditsAdapter pAdapter;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        back = findViewById(R.id.back);

        pd = new ProgressDialog(this);
        pd.setMessage("Please wait...");
        pd.setCancelable(false);

        credits_receivedrv = findViewById(R.id.credits_receivedrv);

        this.deleteDatabase("beersdb");
        mAuth = FirebaseAuth.getInstance();

        initAdapter();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderHistoryActivity.super.onBackPressed();
            }
        });
    }

    private void initAdapter() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderHistoryActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        credits_receivedrv.setLayoutManager(linearLayoutManager);

        pAdapter = new CreditsAdapter(OrderHistoryActivity.this, creditsModel, codes);
        credits_receivedrv.setAdapter(pAdapter);

        getData();
    }

    public void getData() {

        pd.show();

        mDatabase = FirebaseDatabase.getInstance().getReference("Surveys").child(mAuth.getCurrentUser().getUid());

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pd.dismiss();
                creditsModel.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    CreditsData data = ds.getValue(CreditsData.class);
                    creditsModel.add(data);
                    Log.i("ddx", "");
                }

                pAdapter.notifyDataSetChanged();

                int size = creditsModel.size();

                for (int i = 0; i < size; i++) {

                    CreditsData eventsModel = creditsModel.get(i);
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    String URL = "https://us1.api.voucherify.io/v1/vouchers/" + eventsModel.getCode();

                    final int finalI = i;
                    final int finalI1 = i;
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("CREDITS", response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONObject ob = jsonObject.getJSONObject("redemption");
                                quantity1 = ob.getString("quantity");

                                remaining = Integer.parseInt(quantity1);
                                creditsModel.get(finalI1).setRemaining(remaining);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                creditsModel.get(finalI1).setRemaining(0);
                            }
                            pAdapter.notifyDataSetChanged();
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("CREDITS", error.toString());
                        }
                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {

                            final Map<String, String> params = new HashMap<>();
                            params.put("X-App-Id", "2986fb82-6dae-4e5b-a070-ec57e8849b84");
                            params.put("X-App-Token", "90bf01af-4ac2-4d0e-970d-04ff3a639bfb");
                            params.put("Content-Type", "application/json");
                            return params;
                        }
                    };

                    requestQueue.add(stringRequest);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.i("dd", "" + databaseError);
                Toast.makeText(getApplicationContext(), "Ooops...Something went wrong", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        });

    }
}
