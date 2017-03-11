package com.irozon.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.irozon.ratifier.Ratifier;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("RATIFIER");
    }

    public void signInClick(View view) {
        Ratifier.Valid ratify = Ratifier.getValidity(this);
        if (ratify.isValid()) { // Form is valid
            Toast.makeText(this, "Sign Up Success", Toast.LENGTH_SHORT).show();
        } else { // Form is not valid
            Toast.makeText(this, ratify.getErrorMsg(), Toast.LENGTH_SHORT).show();
        }
    }
}
