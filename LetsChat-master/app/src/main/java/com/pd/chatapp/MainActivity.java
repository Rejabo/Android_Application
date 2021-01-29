package com.example.triptogether;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText descriptionEdition;
    private Button submitEdition;
    public TextView profileDesc;
    public String profileDescContent;
    Firebase reference1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseDatabase db = FirebaseDatabase.getInstance();

        DatabaseReference myRef = db.getReference("users").child(UserDetails.username).child("description");

        profileDesc = findViewById(R.id.profileDescription);
        profileDescContent = profileDesc.getText().toString();
        profileDescContent =  db.getReference("users").child(UserDetails.username).child("description").toString();
        profileDesc.setText(profileDescContent);


        Firebase.setAndroidContext(this);
        reference1 = new Firebase(profileDescContent);

        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String descp = dataSnapshot.getValue(String.class);

                profileDesc.setText(descp);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) { }
        });
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.item1:
                Toast.makeText(this, "Item 1 selected", Toast.LENGTH_SHORT).show();

                return true;

            case R.id.item2:
                Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, Login.class);
                startActivity(i);
                finish();
                return true;

            case R.id.item3:
                Toast.makeText(this, "Item 3 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subitem1:
                Toast.makeText(this, "Sub Item 1 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subitem2:
                Toast.makeText(this, "Sub Item 2 selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

        if (v.getId() == R.id.editDescriptionButton) {
        editDescriptionDialog();
        }
    }



    public void editDescriptionDialog() { //for creating new dialog
        dialogBuilder = new AlertDialog.Builder(this);
        final View editDescriptionPopupView = getLayoutInflater().inflate(R.layout.editdescriptionpopup, null);
        descriptionEdition = editDescriptionPopupView.findViewById(R.id.editDescriptionText);

        submitEdition = (Button) editDescriptionPopupView.findViewById(R.id.submitEditButton);

        dialogBuilder.setView(editDescriptionPopupView);
        dialog = dialogBuilder.create();
        dialog.show();


        submitEdition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String edit_text_string = descriptionEdition.getText().toString();


                FirebaseDatabase db = FirebaseDatabase.getInstance();

                DatabaseReference myRef = db.getReference("users").child(UserDetails.username).child("description");


                //profileDescContent = edit_text_string;
                myRef.setValue((edit_text_string));
                Log.d("HURUHRUHRURHUR", edit_text_string);
                Log.d("heil heil", profileDescContent);

                profileDesc.setText(edit_text_string);

                dialog.dismiss();
            }
        });

    }





}