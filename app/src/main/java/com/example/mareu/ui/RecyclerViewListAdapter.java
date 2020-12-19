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
import com.example.mareu.tools.Tools;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class RecyclerViewListAdapter extends RecyclerView.Adapter<RecyclerViewListAdapter.RecyclerViewListViewHolder> {
    private ListCellBinding listCellBinding;

    private InterfaceMeetingApiService service = DI.getMeetingApiService();             //récupère l'API services
    private final List<Meeting> meetings = service.getMeetings();                       //récupère la liste des réunions

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

        private final TextView boldLine;
        private final TextView participants;

        public RecyclerViewListViewHolder(final ListCellBinding itemView) {
            super(itemView.getRoot());
            boldLine = itemView.boldLine;
            participants = itemView.participants;
        }

        public void display(Meeting meeting) {
            Date date = new Date(meeting.getTime().getTime());//timestamp to date
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);                           //date to calendar
            String hour = Tools.formatStringTime(calendar.get(Calendar.HOUR_OF_DAY));
            String minute = Tools.formatStringTime(calendar.get(Calendar.MINUTE));
            String day = Tools.formatStringTime(calendar.get(Calendar.DAY_OF_MONTH));
            String month = Tools.formatStringTime(calendar.get(Calendar.MONTH));
            String bold = meeting.getSubject() + " - salle " + meeting.getLocation() + " \nLe " + day + "/" + month + " à " + hour + "H" + minute ;
            String participantsStr = meeting.getParticipants().toString();
            participantsStr = participantsStr.substring(1, participantsStr.length()-1); //retrait du premier et dernier caractère
            boldLine.setText(bold);
            participants.setText(participantsStr);
        }
    }
}
