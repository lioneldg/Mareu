package com.example.mareu.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.mareu.R;
import com.example.mareu.databinding.ActivityMainBinding;
import com.example.mareu.di.DI;
import com.example.mareu.service.InterfaceMeetingApiService;
import com.example.mareu.service.MeetingApiService;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private MenuItem dateFilter;
    private MenuItem locationFilter;
    private boolean isDateFiltered = false;
    private boolean isLocationFiltered = false;
    private InterfaceMeetingApiService service = DI.getMeetingApiService();
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private int requestCode = 145;
    RVListFragment rvListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();                               //ajout de FragmentRVList au fragmentTransaction relié à l'activité principale
        rvListFragment = new RVListFragment();
        fragmentTransaction.add(activityMainBinding.getRoot().getId(), rvListFragment, "tagFragmentRVList");
        fragmentTransaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        dateFilter = menu.findItem(R.id.dateFilter);
        locationFilter = menu.findItem(R.id.locationFilter);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dateFilter:
                if(!isDateFiltered){
                    final Calendar c = Calendar.getInstance();
                    int currentYear = c.get(Calendar.YEAR);
                    int currentMonth = c.get(Calendar.MONTH);
                    int currentDay = c.get(Calendar.DAY_OF_MONTH);              //récupération de la date courante
                    DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
                        isDateFiltered = true;
                        if(isLocationFiltered){
                            service.setFilterType(MeetingApiService.EnumFilterType.BOTH);
                        } else{
                            service.setFilterType(MeetingApiService.EnumFilterType.DATE);
                        }
                        dateFilter.getIcon().setTint(getResources().getColor(R.color.red));
                        service.initFilterByDate(year, monthOfYear, dayOfMonth);
                        rvListFragment.rvSetAdapter();
                    }, currentYear, currentMonth, currentDay);
                    datePickerDialog.show();
                }else{
                    dateFilter.getIcon().setTint(getResources().getColor(R.color.design_default_color_on_primary));
                    isDateFiltered = false;
                    if(isLocationFiltered){
                        service.setFilterType(MeetingApiService.EnumFilterType.LOCATION);
                        service.filterByLocation();
                    } else{
                        service.setFilterType(MeetingApiService.EnumFilterType.NONE);
                    }
                    rvListFragment.rvSetAdapter();
                }
                return true;
            case R.id.locationFilter:
                if(!isLocationFiltered){
                    Intent intent = new Intent(this, SetFilterByLocationActivity.class);
                    startActivityForResult(intent, requestCode);
                }else{
                    locationFilter.getIcon().setTint(getResources().getColor(R.color.design_default_color_on_primary));
                    isLocationFiltered = false;
                    if(isDateFiltered){
                        service.setFilterType(MeetingApiService.EnumFilterType.DATE);
                        service.filterByDate();
                    } else{
                        service.setFilterType(MeetingApiService.EnumFilterType.NONE);
                    }
                    rvListFragment.rvSetAdapter();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == this.requestCode && resultCode == Activity.RESULT_OK){
            isLocationFiltered = true;
            if(isDateFiltered){
                service.setFilterType(MeetingApiService.EnumFilterType.BOTH);
            } else{
                service.setFilterType(MeetingApiService.EnumFilterType.LOCATION);
                service.filterByLocation();
            }
            locationFilter.getIcon().setTint(getResources().getColor(R.color.red));
            service.initFilterByLocation(data.getIntExtra("selectedItem", 1));
            rvListFragment.rvSetAdapter();
        }
    }
}