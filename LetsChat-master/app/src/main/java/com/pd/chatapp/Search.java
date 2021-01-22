package com.pd.chatapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Search extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText fromCity, toCity, date, time;
    private Button addTrip, cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }


    public void onClick(View v) {
        if (v.getId() == R.id.chatButton) {
            Intent i = new Intent(this, Users.class);
            startActivity(i);
        }
        if (v.getId() == R.id.profileButton) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
        if (v.getId() == R.id.searchButton) {
            Intent i = new Intent(this, Search.class);
            startActivity(i);
        }
        if (v.getId() == R.id.addButton){
            createNewTripDialog();
        }

    }

    public void createNewTripDialog(){ //for creating new dialog
        dialogBuilder = new AlertDialog.Builder(this);
        final View tripPopupView = getLayoutInflater().inflate(R.layout.popup, null);
        toCity = (EditText) tripPopupView.findViewById(R.id.toCity);
        fromCity = (EditText) tripPopupView.findViewById(R.id.fromCity);
        date = (EditText) tripPopupView.findViewById(R.id.date);
        time = (EditText) tripPopupView.findViewById(R.id.time);

        addTrip = (Button) tripPopupView.findViewById(R.id.addTripButton);
        cancel = (Button) tripPopupView.findViewById(R.id.cancelButton);

    dialogBuilder.setView(tripPopupView);
    dialog= dialogBuilder.create();
    dialog.show();

    addTrip.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //define savebutton here

        }
    });

    cancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // define cancelbutton here
            dialog.dismiss();
        }
    });

    }


}
