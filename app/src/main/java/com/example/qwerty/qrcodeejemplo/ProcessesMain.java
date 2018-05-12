package com.example.qwerty.qrcodeejemplo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class ProcessesMain extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ProcessesAdapter adapter;
    Pieza pieza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processes_main);

        pieza = Pieza.fromIntent(getIntent());

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        adapter = new ProcessesAdapter(prepareData(), pieza, this);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public ArrayList<ProcessesList> prepareData() {

        ArrayList<ProcessesList> processesList = new ArrayList<>();

        ProcessesList process = new ProcessesList(1 , "Hola");
        processesList.add(process);

        process = new ProcessesList(2, "Hola");
        processesList.add(process);

        process = new ProcessesList(3, "Adios");
        processesList.add(process);

        process = new ProcessesList(4, "Omar");
        processesList.add(process);

        process = new ProcessesList(5, "Corona");
        processesList.add(process);

        process = new ProcessesList(6, "Chova");
        processesList.add(process);

        process = new ProcessesList(7, "Armando");
        processesList.add(process);

        process = new ProcessesList(8, "Checo");
        processesList.add(process);

        return processesList;
    }
}