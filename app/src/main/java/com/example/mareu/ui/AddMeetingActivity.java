package com.example.mareu.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.mareu.R;
import com.example.mareu.databinding.ActivityAddMeetingBinding;
import com.example.mareu.di.DI;
import com.example.mareu.service.InterfaceMeetingApiService;
import com.example.mareu.tools.Tools;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddMeetingActivity extends AppCompatActivity {
    private int currentYear, currentMonth, currentDay, currentHour, currentMinute;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private AutoCompleteTextView autoCompleteTextView;
    private ChipGroup chipGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = this;
        ActivityAddMeetingBinding activityAddMeetingBinding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        setContentView(activityAddMeetingBinding.getRoot());

        FragmentManager fm = getSupportFragmentManager();
        InterfaceMeetingApiService service = DI.getMeetingApiService();
        EditText editTextMeetingSubject = activityAddMeetingBinding.editTextMeetingSubject;
        EditText editTextMeetingMaster = activityAddMeetingBinding.editTextMeetingMaster;
        Button buttonDatePicker = activityAddMeetingBinding.buttonDatePicker;
        Button buttonTimePicker = activityAddMeetingBinding.buttonTimePicker;
        Spinner spinnerRooms = activityAddMeetingBinding.spinnerRooms;
        autoCompleteTextView = activityAddMeetingBinding.complete;
        chipGroup = activityAddMeetingBinding.chipGroup;
        ImageButton imageButtonCancel = activityAddMeetingBinding.imageButtonCancel;
        ImageButton imageButtonValidate = activityAddMeetingBinding.imageButtonValidate;

        String[] emails = getResources().getStringArray(R.array.emails);

        List<Integer> roomNumbers = new ArrayList<Integer>();
        for(int i = 1; i<=10; i++) roomNumbers.add(i);                                                              //remplissage de l'ArrayList
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, roomNumbers);
        spinnerRooms.setAdapter(arrayAdapter);

        editTextMeetingSubject.setOnKeyListener((view, keyCode, keyEvent) -> {                                      //retour à la ligne automatique arrivé à 20 caractères
            if(editTextMeetingSubject.getText().length() > 20 && keyCode == ' ' && editTextMeetingSubject.isSingleLine()){
                editTextMeetingSubject.append("\n");
            }
            return false;
        });

        buttonDatePicker.setOnClickListener(view -> {               //onClickListener sur le bouton Date
            final Calendar c = Calendar.getInstance();
            currentYear = c.get(Calendar.YEAR);
            currentMonth = c.get(Calendar.MONTH);
            currentDay = c.get(Calendar.DAY_OF_MONTH);              //récupération de la date courante
            datePickerDialog = new DatePickerDialog(context, (view12, year, monthOfYear, dayOfMonth) -> {   //création de la DatePickerDialog
                String monthOfYearStr = (monthOfYear+1) > 9 ? String.valueOf(monthOfYear+1) : '0' + String.valueOf(monthOfYear+1);
                String dayOfMonthStr = dayOfMonth > 9 ? String.valueOf(dayOfMonth) : '0' + String.valueOf(dayOfMonth);
                buttonDatePicker.setText(dayOfMonthStr + "/" + monthOfYearStr + "/" + year);
                service.setDate(dayOfMonth, monthOfYear + 1, year);
            }, currentYear, currentMonth, currentDay);
            datePickerDialog.show();
        });

        buttonTimePicker.setOnClickListener(view -> {                //onClickListener sur le bouton Heure
            final Calendar c = Calendar.getInstance();
            currentHour = c.get(Calendar.HOUR_OF_DAY);
            currentMinute = c.get(Calendar.MINUTE);                 //récupération de l'heure courante
            timePickerDialog = new TimePickerDialog(context, (view1, hour, minute) -> {     //création de la TimePickerDialog
                String hourStr = hour > 9 ? String.valueOf(hour) : '0' + String.valueOf(hour);
                String minuteStr = minute > 9 ? String.valueOf(minute) : '0' + String.valueOf(minute);
                buttonTimePicker.setText(hourStr + "H" + minuteStr);
                service.setTime(hour, minute);
            }, currentHour, currentMinute, true);
            timePickerDialog.show();
        });

        autoCompleteTextView.setThreshold(1);                                                                                                   //paramétrage de l'autocompletion
        ArrayAdapter<String> autocompleteAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, emails);
        autoCompleteTextView.setAdapter(autocompleteAdapter);
        autoCompleteTextView.setOnKeyListener((view, keyCode, keyEvent) -> {
            if(keyEvent.getAction() == keyEvent.ACTION_UP && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                String email = autoCompleteTextView.getText().toString();                                                                       //récupération de la saisie touche entré incluse
                email = email.substring(0, email.length()-1);                                                                                   //retrait de la touche entré dans la saisie
                email = email.toLowerCase();
                if(Tools.validateEmail(email)) {
                    createChip(email);
                    autoCompleteTextView.setText("");
                } else{
                    autoCompleteTextView.setText("");
                    autoCompleteTextView.append(email);                                                          //réécrit l'email et positionne le curseur à la fin
                    Toast.makeText(this, "Veuillez saisir un format d'adresse mail valide.", Toast.LENGTH_LONG).show();
                }

            }
            return false;
        });

        imageButtonCancel.setOnClickListener(view -> finish());

        imageButtonValidate.setOnClickListener(view -> {
            String subject = editTextMeetingSubject.getText().toString();
            String master = editTextMeetingMaster.getText().toString();
            //String date = textViewDatePicker.getText().toString();
            //String time = textViewTimePicker.getText().toString();
            String room = spinnerRooms.getSelectedItem().toString();
            //service.createMeeting(new Meeting(1684, new Timestamp(System.currentTimeMillis()), room, subject, Arrays.asList("lioneldegans@gmail.com", "uneautreadresse@gmail.com")));
        });

    }

    private void createChip(String email) {
        Chip chip = new Chip(this);
        chip.setText(email);
        chip.setBackgroundColor(R.color.purple_200);
        chip.setCloseIconVisible(true);
        chip.setOnCloseIconClickListener(view -> chipGroup.removeView(chip));
        chip.setTextColor(getResources().getColor(R.color.black));
        chipGroup.addView(chip);
    }
}
