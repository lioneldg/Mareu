package com.example.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import com.example.mareu.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();                               //ajout de FragmentRVList au fragmentTransaction relié à l'activité principale
        RVListFragment rvListFragment = new RVListFragment();
        fragmentTransaction.add(activityMainBinding.getRoot().getId(), rvListFragment, "tagFragmentRVList");
        fragmentTransaction.commit();
    }
}