package com.example.mareu.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.databinding.ActivityAddMeetingBinding;
import com.example.mareu.databinding.FragmentRvListBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RVListFragment extends Fragment {
    private FragmentRvListBinding fragmentRvListBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {        //un RV qui affiche la liste des réunions
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentRvListBinding = FragmentRvListBinding.inflate(inflater, container, false);
        RecyclerView recyclerView = fragmentRvListBinding.list;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new RecyclerViewListAdapter());
        FloatingActionButton floatingActionButton = fragmentRvListBinding.floatingActionButton;
        //floatingActionButton.setBackgroundColor(Color.parseColor("red"));
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addMeetingIntent = new Intent(getActivity(), AddMeetingActivity.class);
                startActivity(addMeetingIntent);
            }
        });
        return fragmentRvListBinding.getRoot();
    }
}
