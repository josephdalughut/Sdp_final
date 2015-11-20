package com.example.mycompany.sdp_final.gui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.mycompany.sdp_final.Database;
import com.example.mycompany.sdp_final.R;
import com.example.mycompany.sdp_final.entities.EntityFactory;
import com.example.mycompany.sdp_final.gui.fragments.MapFragment;

public class MainActivity extends AppCompatActivity {

    private static int LOADER_ID = 0;

    public static int getLoaderId(){
        LOADER_ID += 1;
        return LOADER_ID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Database.cacheFacilities(EntityFactory.generateFacilities(), getApplicationContext());
        Database.cacheOffices(EntityFactory.generateOffices(), getApplicationContext());
        Database.cacheStaff(EntityFactory.generateStaff(), getApplicationContext());
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new MapFragment()).commit();
        }
    }

}
