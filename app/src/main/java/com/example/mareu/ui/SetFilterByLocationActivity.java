package com.example.mareu.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.annotation.Nullable;
import com.example.mareu.databinding.ActivitySetFilterByLocationBinding;
import java.util.ArrayList;
import java.util.List;

public class SetFilterByLocationActivity extends Activity {
    ActivitySetFilterByLocationBinding activitySetFilterByLocationBinding;
    Button ok;
    Button cancel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySetFilterByLocationBinding = ActivitySetFilterByLocationBinding.inflate(getLayoutInflater());
        setContentView(activitySetFilterByLocationBinding.getRoot());

        Spinner spinner = activitySetFilterByLocationBinding.spinner;
        List<String> items = new ArrayList<String>();
        items.add("1");
        items.add("2");
        items.add("3");
        items.add("4");
        items.add("5");
        items.add("6");
        items.add("7");
        items.add("8");
        items.add("9");
        items.add("10");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ok = activitySetFilterByLocationBinding.ok;
        cancel = activitySetFilterByLocationBinding.cancel;

        ok.setOnClickListener(view -> {
            String stringSelectedItem = spinner.getSelectedItem().toString();
            int selectedItem = Integer.parseInt(stringSelectedItem);
            Intent data = new Intent();
            data.putExtra("selectedItem", selectedItem);
            setResult(RESULT_OK, data);
            finish();
        });

        cancel.setOnClickListener(view -> {
            finish();
        });
    }
}
