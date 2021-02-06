package com.example.mareu.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mareu.databinding.FragmentRvListBinding;
import com.example.mareu.di.DI;
import com.example.mareu.service.InterfaceMeetingApiService;
import com.example.mareu.service.MeetingApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RVListFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerViewListAdapter recyclerViewListAdapter;
    private final InterfaceMeetingApiService service = DI.getMeetingApiService();             //récupère l'API service
    private final int requestCode = 658;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {        //un RV qui affiche la liste des réunions
        super.onCreateView(inflater, container, savedInstanceState);
        com.example.mareu.databinding.FragmentRvListBinding fragmentRvListBinding = FragmentRvListBinding.inflate(inflater, container, false);
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
    //la liste des meeting dépend du type de filtre
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
