package com.example.beerapp.Activities.Surveys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beerapp.R;
import com.google.android.material.button.MaterialButton;

public class TierThreeActivity extends AppCompatActivity {

    MaterialButton upload_snapshot,pay_now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tier_three);
        init();
    }

    private void init() {
        upload_snapshot = findViewById(R.id.upload_snapshot);
        pay_now = findViewById(R.id.pay_now);

        upload_snapshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TierThreeActivity.this,TokenActivity.class));
                Toast.makeText(TierThreeActivity.this, "Upload Snapshot", Toast.LENGTH_SHORT).show();
            }
        });

        pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TierThreeActivity.this, "Pay Now", Toast.LENGTH_SHORT).show();
            }
        });
    }


//    profile_img.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            ImagePicker.Companion.with(SignUpActivity.this)
//                    .crop()                    //Crop image(Optional), Check Customization for more option
//                    .compress(1024)            //Final image size will be less than 1 MB(Optional)
//                    .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
//                    .start();
//        }
//    });
//}

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == Activity.RESULT_OK) {
//            //Image Uri will not be null for RESULT_OK
//            Uri imageUri = data.getData();
//            profile_img.setImageURI(imageUri);
//
//        } else if (resultCode == ImagePicker.RESULT_ERROR) {
//            Toast.makeText(this, ImagePicker.RESULT_ERROR, Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
//        }
//    }
}
