package com.example.mareu.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.databinding.ListCellBinding;
import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.InterfaceMeetingApiService;
import com.example.mareu.tools.Tools;
import java.util.Calendar;
import java.util.List;

public class RecyclerViewListAdapter extends RecyclerView.Adapter<RecyclerViewListAdapter.RecyclerViewListViewHolder> {
    private final InterfaceMeetingApiService service = DI.getMeetingApiService();             //récupère l'API services
    private final List<Meeting> meetings;

    public RecyclerViewListAdapter(List<Meeting> meetings) {
        this.meetings = meetings;
    }

    @NonNull
    @Override
    public RecyclerViewListAdapter.RecyclerViewListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        com.example.mareu.databinding.ListCellBinding listCellBinding = ListCellBinding.inflate(inflater, parent, false);
        return new RecyclerViewListViewHolder(listCellBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewListAdapter.RecyclerViewListViewHolder holder, int position) {
        Meeting meeting = meetings.get(position);
        Context context = holder.itemView.getContext();
        holder.display(meeting, service, this, context);
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }

    public static class RecyclerViewListViewHolder extends RecyclerView.ViewHolder{

        private final TextView subject;
        private final TextView location;
        private final TextView participants;
        private final ImageButton delete;

        public RecyclerViewListViewHolder(final ListCellBinding itemView) {
            super(itemView.getRoot());
            subject = itemView.subject;
            location = itemView.location;
            participants = itemView.participants;
            delete = itemView.delete;
        }

        public void display(Meeting meeting, InterfaceMeetingApiService service, RecyclerViewListAdapter recyclerViewListAdapter, Context context) {
            Calendar calendar = meeting.getCalendar();
            String hour = Tools.formatStringTime(calendar.get(Calendar.HOUR_OF_DAY));
            String minute = Tools.formatStringTime(calendar.get(Calendar.MINUTE));
            String day = Tools.formatStringTime(calendar.get(Calendar.DAY_OF_MONTH));
            String month = Tools.formatStringTime(calendar.get(Calendar.MONTH) + 1);
            String locationStr = context.getString(R.string.Salle) + meeting.getLocation() + ", " + context.getString(R.string.le) + " " + day + "/" + month + " " + context.getString(R.string.à)+ " " + hour + "H" + minute ;
            String participantsStr = meeting.getParticipants().toString();
            participantsStr = participantsStr.substring(1, participantsStr.length()-1); //retrait du premier et dernier caractère
            subject.setText(meeting.getSubject());
            location.setText(locationStr);
            participants.setText(participantsStr);
            delete.setOnClickListener(view -> {
                service.deleteMeeting(meeting);
                recyclerViewListAdapter.notifyDataSetChanged();
            });
        }
    }
}
