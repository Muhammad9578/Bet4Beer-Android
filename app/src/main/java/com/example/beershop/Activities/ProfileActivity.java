package com.example.beershop.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.Navigation;
import de.hdodenhof.circleimageview.CircleImageView;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.beershop.Activities.LoginDetails.LoginActivity;
import com.example.beershop.Activities.LoginDetails.SignUpActivity;
import com.example.beershop.Models.UserData;
import com.example.beershop.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;

public class ProfileActivity extends AppCompatActivity{

    private CircleImageView profile_img;
    private TextView change_image;
    private EditText user_name,user_email,user_pass;
    private MaterialButton btn_save;
    private Uri imageUri;
    private String name, email;
    private ProgressDialog pd;
    private int check=0;
    private DatabaseReference databaseReference;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private ImageView edit_name,edit_email,edit_pass, back_ic;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;
    private String downloadURL;
    private String url;
    private String dob;
    private Toolbar toolbar;
    private Drawable unwrappedDrawable, wrappedDrawable;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private boolean isMembersVisible = false;
    private NavigationView navigationView;
    private String check1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();
        setUpDrawer();
        SharedPreferences prefs1 = getSharedPreferences("Check Data", MODE_PRIVATE);
        check1 = prefs1.getString("check", "");
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
                if(check1.matches("Customer")) {
                    startActivity(new Intent(this, CustomerLandingActivity.class));
                    mDrawerLayout.closeDrawers();
                    finish();
                }else if(check1.matches("No")) {
                    startActivity(new Intent(this, No_Activity.class));
                    mDrawerLayout.closeDrawers();
                    finish();
                }else if(check1.matches("Screen")){
                    startActivity(new Intent(this, ScreeningActivity.class));
                    mDrawerLayout.closeDrawers();
                    finish();
                }
                break;

            case R.id.userProfileFragment:
                startActivity(new Intent(this, ProfileActivity.class));
                mDrawerLayout.closeDrawers();
                break;

            case R.id.map:
                startActivity(new Intent(this, MapActivity.class));
                mDrawerLayout.closeDrawers();
                finish();
                break;

            case R.id.tokens:
                MenuItem t_received = navigationView.getMenu().findItem(R.id.creditsReceivedFragment);
                MenuItem t_remaining = navigationView.getMenu().findItem(R.id.creditsRemainingFragment);
              //  MenuItem t_used = navigationView.getMenu().findItem(R.id.orderHistoryFragment);

                MenuItem menuItem = navigationView.getMenu().findItem(R.id.tokens);
                final ImageView drawerSwitch = (ImageView) menuItem.getActionView().findViewById(R.id.drawer_switch);

                if (!isMembersVisible) {
                    t_received.setVisible(true);
                    t_remaining.setVisible(true);
                 //   t_used.setVisible(true);
                    isMembersVisible = true;
                    drawerSwitch.setImageResource(R.drawable.ic_up);
                } else {
                    t_received.setVisible(false);
                    t_remaining.setVisible(false);
                   // t_used.setVisible(false);
                    isMembersVisible = false;
                    drawerSwitch.setImageResource(R.drawable.ic_down);
                }
                break;

            case R.id.creditsReceivedFragment:
                startActivity(new Intent(this, CreditsReceivedActivity.class));
                mDrawerLayout.closeDrawers();
                finish();
                break;

            case R.id.creditsRemainingFragment:
                startActivity(new Intent(this, CreditsRemainingActivity.class));
                mDrawerLayout.closeDrawers();
                finish();
                break;

//            case R.id.orderHistoryFragment:
//                startActivity(new Intent(this, OrderHistoryActivity.class));
//                mDrawerLayout.closeDrawers();
//                finish();
//                break;

            case R.id.refferals:
                startActivity(new Intent(this, ReferralsActivity.class));
                mDrawerLayout.closeDrawers();
                finish();
                break;

            case R.id.logout:
                Logout();
                mDrawerLayout.close();
                break;
        }
    }

    private void init() {
        change_image = findViewById(R.id.upload);
        profile_img = findViewById(R.id.profile_img);
        edit_name = findViewById(R.id.edit_name);
        edit_email = findViewById(R.id.edit_email);
        edit_pass = findViewById(R.id.edit_pass);
        back_ic = findViewById(R.id.back);
        user_name = findViewById(R.id.user_name);
        user_email = findViewById(R.id.user_email);
        user_pass = findViewById(R.id.user_pass);

        btn_save = findViewById(R.id.btn_save);

        SharedPreferences prefs = getSharedPreferences("User Data", MODE_PRIVATE);
        String email = prefs.getString("email","");
        String name = prefs.getString("name","");
        String image = prefs.getString("image","");
        url = prefs.getString("image" ,"");
        dob = prefs.getString("dob","");

        user_name.setText(name);
        user_email.setText(email);
        Glide.with(this).load(image).into(profile_img);

        pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("Updating profile...");

        mAuth = FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("Users");

        edit_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_name.setEnabled(true);
            }
        });

        edit_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_email.setEnabled(true);
            }
        });

        edit_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_pass.setEnabled(true);
            }
        });


        change_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(ProfileActivity.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UpdateProfile();
            }
        });
    }

    public void Logout() {

        final PrettyDialog pDialog = new PrettyDialog(ProfileActivity.this);
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
                                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
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


    public void UpdateProfile(){

        name = user_name.getText().toString().trim();
        email = user_email.getText().toString().trim();

        if(name.isEmpty()){

            edit_name.setVisibility(View.GONE);
            user_name.setError("Enter name");

        }else if(email.isEmpty()){

            edit_email.setVisibility(View.GONE);
            user_email.setError("Enter email");
        }else if(!email.matches(emailPattern)){

            edit_email.setVisibility(View.GONE);
            user_email.setError("Invalid email");

        }else {

            edit_email.setVisibility(View.VISIBLE);
            edit_name.setVisibility(View.VISIBLE);
            user_name.setError(null);
            user_email.setError(null);

            pd.show();


            if(check == 1){

                storageReference = FirebaseStorage.getInstance().getReference().child("Profile Images").child(imageUri.getLastPathSegment());
                storageReference.putFile(imageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @SuppressWarnings("VisibleForTests")
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Log.i("Success", "Uploaded successfully!");
                                downloadURL = taskSnapshot.getDownloadUrl().toString();

                                UserData userData = new UserData(name,email,downloadURL,dob);


                                databaseReference.child(mAuth.getCurrentUser().getUid()).setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        pd.dismiss();
                                        Toast.makeText(getApplicationContext(),"Profile updated successfully",Toast.LENGTH_LONG).show();

                                        SharedPreferences sharedpreferences = getSharedPreferences("User Data", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putString("name", name);
                                        editor.putString("email", email);

                                        Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        pd.dismiss();

                                        Dialogue("Failed to update profile");
                                    }
                                });

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

                Dialogue("Failed to update profile");
            }
        });
            }else {

                UpdateEmail(email);

                UserData userData = new UserData(name,email,url,dob);

                databaseReference.child(mAuth.getCurrentUser().getUid()).setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Profile updated successfully",Toast.LENGTH_LONG).show();

                        SharedPreferences sharedpreferences = getSharedPreferences("User Data", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("name", name);
                        editor.putString("email", email);

                        Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        pd.dismiss();
                        Dialogue("Failed to update profile.");
                    }
                });

            }

        }
    }

    public void Dialogue(String message) {

        new AlertDialog.Builder(ProfileActivity.this)
                .setTitle("Bet4Beers")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            imageUri = data.getData();
            profile_img.setImageURI(imageUri);
            check=1;
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.RESULT_ERROR, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    public void UpdateEmail(final String email) {

        SharedPreferences prefs = getSharedPreferences("User Data", MODE_PRIVATE);
        String password = prefs.getString("password", "");
        String mail = prefs.getString("email","");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider
                .getCredential(mail, password);

        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("Update", "User re-authenticated.");

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.updateEmail(email)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d("Update", "User email address updated.");

                                        }
                                    }
                                });
                    }
                });
    }
}
