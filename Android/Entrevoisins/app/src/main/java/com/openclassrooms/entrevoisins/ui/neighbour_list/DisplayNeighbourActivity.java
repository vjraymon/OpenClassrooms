package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.openclassrooms.entrevoisins.R;

public class DisplayNeighbourActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_neighbour);

        TextView displayName = findViewById(R.id.displayName);
        String data = getIntent().getExtras().getString("keyName", "defaultKey");
        displayName.setText(data);
    }
}