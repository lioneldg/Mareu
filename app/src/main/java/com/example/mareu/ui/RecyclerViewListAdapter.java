package com.example.mareu.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mareu.databinding.ListCellBinding;
import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.InterfaceMeetingApiService;
import java.util.List;

public class RecyclerViewListAdapter extends RecyclerView.Adapter<RecyclerViewListAdapter.RecyclerViewListViewHolder> {
    private ListCellBinding listCellBinding;

    private InterfaceMeetingApiService service = DI.getMeetingApiService();
    private final List<Meeting> meetings = service.getMeetings();

    @NonNull
    @Override
    public RecyclerViewListAdapter.RecyclerViewListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        listCellBinding = ListCellBinding.inflate(inflater, parent, false);
        return new RecyclerViewListViewHolder(listCellBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewListAdapter.RecyclerViewListViewHolder holder, int position) {
        Meeting meeting = meetings.get(position);
        holder.display(meeting);
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }

    public static class RecyclerViewListViewHolder extends RecyclerView.ViewHolder{

        private final TextView name;
        private final TextView description;
        private Meeting currentMeeting;

        public RecyclerViewListViewHolder(final ListCellBinding itemView) {
            super(itemView.getRoot());
            name = itemView.name;
            description = itemView.description;
            itemView.getRoot().setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    new AlertDialog.Builder(itemView.getRoot().getContext())
                            .setTitle(currentMeeting.getSubject())
                            .setMessage(currentMeeting.getParticipants().get(0))
                            .show();
                }
            });
        }

        public void display(Meeting meeting) {
            currentMeeting = meeting;
            name.setText(String.valueOf(meeting.getLocation()));
            description.setText(meeting.getSubject());
        }
    }
}
