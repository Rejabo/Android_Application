package com.example.triptogether;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.google.firebase.database.FirebaseDatabase;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;


public class Search extends AppCompatActivity {


    RecyclerView recview;
    myadapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle("");

        recview=(RecyclerView)findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users"), model.class)
                        .build();

        adapter=new myadapter(options);
        recview.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.searchmenu,menu);

        MenuItem item=menu.findItem(R.id.search);

        SearchView searchView=(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    private void processsearch(String s)
    {
        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users").orderByChild("course").startAt(s).endAt(s+"\uf8ff"), model.class)
                        .build();

        adapter=new myadapter(options);
        adapter.startListening();
        recview.setAdapter(adapter);

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
    }
}
