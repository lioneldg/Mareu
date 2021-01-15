package com.example.mareu.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.databinding.ActivityAddMeetingBinding;
import com.example.mareu.databinding.FragmentRvListBinding;
import com.example.mareu.databinding.ListCellBinding;
import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.InterfaceMeetingApiService;
import com.example.mareu.service.MeetingApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class RVListFragment extends Fragment {
    private FragmentRvListBinding fragmentRvListBinding;
    private RecyclerView recyclerView;
    private RecyclerViewListAdapter recyclerViewListAdapter;
    private InterfaceMeetingApiService service = DI.getMeetingApiService();             //récupère l'API services
    private int requestCode = 658;
    private MeetingApiService.EnumFilterType filterType = MeetingApiService.EnumFilterType.NONE ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {        //un RV qui affiche la liste des réunions
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentRvListBinding = FragmentRvListBinding.inflate(inflater, container, false);
        recyclerView = fragmentRvListBinding.list;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSetAdapter();
        FloatingActionButton floatingActionButton = fragmentRvListBinding.floatingActionButton;
        floatingActionButton.setOnClickListener(view -> {
            Intent addMeetingIntent = new Intent(getActivity(), AddMeetingActivity.class);
            startActivityForResult(addMeetingIntent, requestCode);
        });
        return fragmentRvListBinding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == this.requestCode && resultCode == Activity.RESULT_OK){
            service.sortByDate();
            if(service.getFilterType() != MeetingApiService.EnumFilterType.NONE){
                rvSetAdapter();                                         //redonner l'adapter à la RV car il a changé à cause de filteredMeetings = new ArrayList<>(); dans MeetingApiService
            } else {
                recyclerViewListAdapter.notifyDataSetChanged();
            }
        }
    }

    public void rvSetAdapter(){
        recyclerViewListAdapter = new RecyclerViewListAdapter(service.getFilterType() == MeetingApiService.EnumFilterType.NONE ? service.getMeetings() : service.getFilteredMeetings());
        recyclerView.setAdapter(recyclerViewListAdapter);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        service.clearMeetings();
        rvSetAdapter();
    }
}
