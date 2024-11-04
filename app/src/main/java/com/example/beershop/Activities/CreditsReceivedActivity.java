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

import com.example.beershop.Adapters.CreditsReceivedAdapter;
import com.example.beershop.Models.CreditReceived;
import com.example.beershop.Models.CreditsModel;
import com.example.beershop.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreditsReceivedActivity extends AppCompatActivity {

    private ImageView back;
    private RecyclerView credits_receivedrv;
    private ArrayList<CreditReceived> creditsModel = new ArrayList<>();
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits_received);

        pd = new ProgressDialog(this);
        pd.setMessage("Please wait...");
        pd.setCancelable(false);

        mAuth = FirebaseAuth.getInstance();
        credits_receivedrv = findViewById(R.id.credits_receivedrv);
        back = findViewById(R.id.back);

        initAdapter();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
    }


    private void initAdapter() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CreditsReceivedActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        credits_receivedrv.setLayoutManager(linearLayoutManager);

        getData();
    }

    public void getData(){

        pd.show();

        mDatabase = FirebaseDatabase.getInstance().getReference("Surveys").child(mAuth.getCurrentUser().getUid());

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                creditsModel.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren()){

                    CreditReceived data = ds.getValue(CreditReceived.class);
                    creditsModel.add(data);
                    Log.i("ddx","");
                   // progressDialog.dismiss();
                }

                CreditsReceivedAdapter pAdapter = new CreditsReceivedAdapter(CreditsReceivedActivity.this, creditsModel);

                credits_receivedrv.setAdapter(pAdapter);
                pAdapter.notifyDataSetChanged();
                pd.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

               // progressDialog.dismiss();
                Log.i("dd",""+databaseError);
                Toast.makeText(getApplicationContext(),"Ooops...Something went wrong",Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        });
    }
}
