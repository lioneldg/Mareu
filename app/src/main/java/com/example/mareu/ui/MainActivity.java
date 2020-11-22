package com.example.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import com.example.mareu.databinding.ActivityMainBinding;
import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.InterfaceMeetingApiService;
import com.example.mareu.ui.FragmentRVList;

import java.sql.Timestamp;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        InterfaceMeetingApiService service = DI.getMeetingApiService();
        service.createMeeting(new Meeting(1684, new Timestamp(System.currentTimeMillis()), 2, "Sujet de la réunion", Arrays.asList("lioneldegans@gmail.com", "uneautreadresse@gmail.com")));

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        FragmentRVList fragmentRVList = new FragmentRVList();
        fragmentTransaction.add(activityMainBinding.getRoot().getId(), fragmentRVList, "tagFragmentRVList");
        fragmentTransaction.commit();
    }
}