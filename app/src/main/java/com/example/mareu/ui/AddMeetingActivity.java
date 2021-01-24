package com.example.mareu.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddMeetingActivity extends AppCompatActivity {
    private int currentYear;
    private int currentMonth;
    private int currentDay;
    private int currentHour;
    private int currentMinute;
    private int pickedYear = -1;
    private int pickedMonthOfYear = -1;
    private int pickedDayOfMonth = -1;
    private int pickedHour = -1;
    private int pickedMinute = -1;
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
                String monthOfYearStr = Tools.formatStringTime(monthOfYear + 1);
                String dayOfMonthStr = Tools.formatStringTime(dayOfMonth);
                pickedYear = year;
                pickedMonthOfYear = monthOfYear;
                pickedDayOfMonth = dayOfMonth;
                buttonDatePicker.setText(dayOfMonthStr + "/" + monthOfYearStr + "/" + year);
            }, currentYear, currentMonth, currentDay);
            datePickerDialog.show();
        });

        buttonTimePicker.setOnClickListener(view -> {                //onClickListener sur le bouton Heure
            final Calendar c = Calendar.getInstance();
            currentHour = c.get(Calendar.HOUR_OF_DAY);
            currentMinute = c.get(Calendar.MINUTE);                 //récupération de l'heure courante
            timePickerDialog = new TimePickerDialog(context, (view1, hour, minute) -> {     //création de la TimePickerDialog
                String hourStr = Tools.formatStringTime(hour);
                String minuteStr = Tools.formatStringTime(minute);
                pickedHour = hour;
                pickedMinute = minute;
                buttonTimePicker.setText(hourStr + "H" + minuteStr);
            }, currentHour, currentMinute, true);
            timePickerDialog.show();
        });

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
                Calendar calendar = Calendar.getInstance();
                calendar.set(pickedYear, pickedMonthOfYear, pickedDayOfMonth, pickedHour, pickedMinute);
                service.createMeeting(new Meeting(calendar, Integer.parseInt(room), subject, participants));
                setResult(Activity.RESULT_OK);
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
        if(!Tools.validateDate(pickedDayOfMonth, pickedMonthOfYear + 1, pickedYear)){
            if(infoErrors.length() > 0 ) infoErrors += "\n";
            infoErrors += "Veuillez choisir une date valide.";
            valid = false;
        }
        if(!Tools.validateTime(pickedHour, pickedMinute)){
            if(infoErrors.length() > 0 ) infoErrors += "\n";
            infoErrors += "Veuillez choisir une heure valide.";
            valid = false;
        }
        if(Tools.participantsIsEmpty(participants)){
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

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        finish();
    }
}
