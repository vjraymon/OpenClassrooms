package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.app.ActionBar;
import android.content.ClipData;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.List;

import butterknife.OnClick;

public class DisplayNeighbourActivity extends AppCompatActivity {

    private NeighbourApiService mApiService;
    private long id;
    private static boolean singleton = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_neighbour);

        id = getIntent().getLongExtra("keyId", -1);
        TextView displayName = findViewById(R.id.displayName);
        String name = getIntent().getExtras().getString("keyName", "defaultKey");
        displayName.setText(name);
        TextView displayNameIncrusted = findViewById(R.id.displayNameIncrusted);
        displayNameIncrusted.setText(name);
        String avatarUrl = getIntent().getExtras().getString("keyAvatarUrl", "defaultKey");
        TextView displayAddress = findViewById(R.id.displayAddress);
        displayAddress.setText(getIntent().getExtras().getString("keyAddress", "defaultKey"));
        TextView displayPhoneNumber = findViewById(R.id.displayPhoneNumber);
        displayPhoneNumber.setText(getIntent().getExtras().getString("keyPhoneNumber", "defaultKey"));
        TextView displayEmail = findViewById(R.id.displayEmail);
        displayEmail.setText("www.facebook.fr/" + name.toLowerCase());
        TextView displayAboutMe = findViewById(R.id.displayAboutMe);
        displayAboutMe.setText(getIntent().getExtras().getString("keyAboutMe", "defaultKey"));

        ImageView mNeighbourAvatar = findViewById(R.id.displayAvatar);
        Glide.with(mNeighbourAvatar.getContext())
                .load(avatarUrl)
                .apply(RequestOptions.centerCropTransform())
                .into(mNeighbourAvatar);

    }

    public void AddFavoriteNeighbour(View view) {
        Log.i("neighbour", "button add");
        mApiService = DI.getNeighbourApiService();
        mApiService.addFavoriteNeighbour(id);
    }

    public void ReturnFavoriteNeighbour(View view) {
        finish();
        singleton = false;
    }

    /**
     * Used to navigate to this activity
     * @param context
     */
    public static void navigate(android.content.Context context, Neighbour neighbour) {
        if (!singleton) {
            Intent intent = new Intent(context, DisplayNeighbourActivity.class);
            intent.putExtra("keyId", neighbour.getId());
            intent.putExtra("keyName", neighbour.getName());
            intent.putExtra("keyAvatarUrl", neighbour.getAvatarUrl());
            intent.putExtra("keyAddress", neighbour.getAddress());
            intent.putExtra("keyPhoneNumber", neighbour.getPhoneNumber());
            intent.putExtra("keyAboutMe", neighbour.getAboutMe());
            ActivityCompat.startActivity(context, intent, null);
            singleton = true;
        }
    }

}

