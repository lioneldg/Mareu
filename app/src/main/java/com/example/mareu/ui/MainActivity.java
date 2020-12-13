package com.example.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import com.example.mareu.databinding.ActivityMainBinding;
import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.InterfaceMeetingApiService;

import java.sql.Timestamp;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        InterfaceMeetingApiService service = DI.getMeetingApiService();                                                     //récupère l'API services
        service.createMeeting(new Meeting(1684, new Timestamp(System.currentTimeMillis()), 2, "Sujet de la réunion", Arrays.asList("lioneldegans@gmail.com", "uneautreadresse@gmail.com")));    //création d'une fausse réunion pour les besoins des tests

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();                               //ajout de FragmentRVList au fragmentTransaction relié à l'activité principale
        RVListFragment rvListFragment = new RVListFragment();
        fragmentTransaction.add(activityMainBinding.getRoot().getId(), rvListFragment, "tagFragmentRVList");
        fragmentTransaction.commit();
    }
}