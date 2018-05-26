package com.example.qwerty.qrcodeejemplo.view;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import android.os.Bundle;

import com.example.qwerty.qrcodeejemplo.R;
import com.example.qwerty.qrcodeejemplo.adapter.ModelsAdapter;
import com.example.qwerty.qrcodeejemplo.model.ModelsList;

public class ModelsMain extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ModelsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_models_main);

        recyclerView = (RecyclerView) findViewById(R.id.modelsRecyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        adapter = new ModelsAdapter(prepareDataModels()/*, pieza, getIntent().getStringExtra("login_id"), this*/);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext()));

    }

    public ArrayList<ModelsList> prepareDataModels() {

        ArrayList<ModelsList> modelsList = new ArrayList<>();

        ModelsList model = new ModelsList("1" , "Hola", "fla");
        modelsList.add(model);

        model = new ModelsList("2" , "Hola", "sfghjk");
        modelsList.add(model);

        model = new ModelsList("3" , "Hola", "euihbvfiv");
        modelsList.add(model);

        model = new ModelsList("4" , "Hola", "viejnv");
        modelsList.add(model);

        model = new ModelsList("5" , "Hola", "wertyu");
        modelsList.add(model);

        model = new ModelsList("6" , "Hola", "mkvmo");
        modelsList.add(model);

        return modelsList;
    }

}
