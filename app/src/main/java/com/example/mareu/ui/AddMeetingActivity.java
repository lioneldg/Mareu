package com.example.mareu.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mareu.R;
import com.example.mareu.databinding.ActivityAddMeetingBinding;
import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.InterfaceMeetingApiService;
import com.example.mareu.tools.Tools;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddMeetingActivity extends AppCompatActivity {
    private int currentYear, currentMonth, currentDay, currentHour, currentMinute;
    private String yearStr, monthOfYearStr, dayOfMonthStr, hourStr, minuteStr;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private AutoCompleteTextView autoCompleteTextView;
    private ChipGroup chipGroup;
    private ArrayList<String> participants;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = this;
        ActivityAddMeetingBinding activityAddMeetingBinding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        setContentView(activityAddMeetingBinding.getRoot());

        InterfaceMeetingApiService service = DI.getMeetingApiService();
        EditText editTextMeetingSubject = activityAddMeetingBinding.editTextMeetingSubject;
        EditText editTextMeetingMaster = activityAddMeetingBinding.editTextMeetingMaster;
        TextView buttonDatePicker = activityAddMeetingBinding.buttonDatePicker;
        TextView buttonTimePicker = activityAddMeetingBinding.buttonTimePicker;
        Spinner spinnerRooms = activityAddMeetingBinding.spinnerRooms;
        autoCompleteTextView = activityAddMeetingBinding.complete;
        chipGroup = activityAddMeetingBinding.chipGroup;
        ImageButton imageButtonCancel = activityAddMeetingBinding.imageButtonCancel;
        ImageButton imageButtonValidate = activityAddMeetingBinding.imageButtonValidate;
        participants = new ArrayList<>();

        String[] emails = getResources().getStringArray(R.array.emails);

        List<Integer> roomNumbers = new ArrayList<>();
        //remplissage de l'ArrayList
        for(int i = 1; i<=10; i++) roomNumbers.add(i);
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roomNumbers);
        spinnerRooms.setAdapter(arrayAdapter);

        //retour à la ligne automatique arrivé à 20 caractères
        editTextMeetingSubject.setOnKeyListener((view, keyCode, keyEvent) -> {
            if(editTextMeetingSubject.getText().length() > 20 && keyCode == ' ' && editTextMeetingSubject.isSingleLine()){
                editTextMeetingSubject.append("\n");
            }
            return false;
        });

        //onClickListener sur le bouton Date
        buttonDatePicker.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();
            currentYear = c.get(Calendar.YEAR);
            currentMonth = c.get(Calendar.MONTH);
            currentDay = c.get(Calendar.DAY_OF_MONTH);              //récupération de la date courante
            datePickerDialog = new DatePickerDialog(context, (view12, year, monthOfYear, dayOfMonth) -> {   //création de la DatePickerDialog
                yearStr = String.valueOf(year);
                monthOfYearStr = Tools.formatStringTime(monthOfYear+1);
                dayOfMonthStr = Tools.formatStringTime(dayOfMonth);
                buttonDatePicker.setText(dayOfMonthStr + "/" + monthOfYearStr + "/" + year);
            }, currentYear, currentMonth, currentDay);
            datePickerDialog.show();
        });

        buttonTimePicker.setOnClickListener(view -> {                //onClickListener sur le bouton Heure
            final Calendar c = Calendar.getInstance();
            currentHour = c.get(Calendar.HOUR_OF_DAY);
            currentMinute = c.get(Calendar.MINUTE);                 //récupération de l'heure courante
            timePickerDialog = new TimePickerDialog(context, (view1, hour, minute) -> {     //création de la TimePickerDialog
                hourStr = Tools.formatStringTime(hour);
                minuteStr = Tools.formatStringTime(minute);
                buttonTimePicker.setText(hourStr + "H" + minuteStr);
            }, currentHour, currentMinute, true);
            timePickerDialog.show();
        });

        autoCompleteTextView.setThreshold(1);                                                                                                   //paramétrage de l'autocompletion
        ArrayAdapter<String> autocompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, emails);
        autoCompleteTextView.setAdapter(autocompleteAdapter);
        autoCompleteTextView.setOnKeyListener((view, keyCode, keyEvent) -> {
            if(keyEvent.getAction() == KeyEvent.ACTION_UP && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                String email = autoCompleteTextView.getText().toString();                                                                       //récupération de la saisie touche entré incluse
                email = email.substring(0, email.length()-1);                                                                                   //retrait de la touche entré dans la saisie
                email = email.toLowerCase();
                if(Tools.validateEmail(email)) {
                    createChip(email);
                    participants.add(email);
                    autoCompleteTextView.setText("");
                } else{
                    autoCompleteTextView.setText("");
                    autoCompleteTextView.append(email);                                                          //réécrit l'email et positionne le curseur à la fin
                    Toast.makeText(this, "Veuillez saisir une adresse mail valide.", Toast.LENGTH_LONG).show();
                }

            }
            return false;
        });

        imageButtonCancel.setOnClickListener(view -> finish());

        imageButtonValidate.setOnClickListener(view -> {
            String subject = editTextMeetingSubject.getText().toString();
            String master = editTextMeetingMaster.getText().toString();
            String room = spinnerRooms.getSelectedItem().toString();

            if(testsBeforeValid(subject, master)){
                Timestamp timestamp = Timestamp.valueOf(yearStr + '-' + monthOfYearStr + '-' + dayOfMonthStr + ' ' + hourStr + ':' + minuteStr + ':' + '0');
                Toast.makeText(this, "timestamps: " + timestamp.getTime(), Toast.LENGTH_LONG).show();
                service.createMeeting(new Meeting(timestamp, Integer.parseInt(room), subject, participants));
                finish();
            }
        });

    }

    private boolean testsBeforeValid(String subject, String master){
        boolean valid = true;
        String infoErrors = "";

        if(Tools.textIsEmpty(subject)){
            infoErrors += "Veuillez saisir un sujet.";
            valid = false;
        }
        if(Tools.textIsEmpty(master)){
            if(infoErrors.length() > 0 ) infoErrors += "\n";
            infoErrors += "Veuillez saisir votre nom dans le champ organisateur.";
            valid = false;
        }
        if(!Tools.validateDate(dayOfMonthStr, monthOfYearStr, yearStr)){
            if(infoErrors.length() > 0 ) infoErrors += "\n";
            infoErrors += "Veuillez choisir une date valide.";
            valid = false;
        }
        if(!Tools.validateTime(hourStr, minuteStr)){
            if(infoErrors.length() > 0 ) infoErrors += "\n";
            infoErrors += "Veuillez choisir une heure valide.";
            valid = false;
        }
        if(!Tools.participantsIsNotEmpty(participants)){
            if(infoErrors.length() > 0 ) infoErrors += "\n";
            infoErrors += "Veuillez renseigner le(s) participant(s).";
            valid = false;
        }
        if(infoErrors.length() > 0){
            Toast.makeText(this, infoErrors, Toast.LENGTH_LONG).show();
        }
        return valid;
    }

    private void createChip(String email) {
        Chip chip = new Chip(this);
        chip.setText(email);
        chip.setBackgroundColor(getResources().getColor(R.color.purple_200));
        chip.setCloseIconVisible(true);
        chip.setOnCloseIconClickListener(view -> {
            participants.remove(email);
            chipGroup.removeView(chip);
        });
        chip.setTextColor(getResources().getColor(R.color.black));
        chipGroup.addView(chip);
    }
}
