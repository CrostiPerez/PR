package com.example.qwerty.qrcodeejemplo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

public class ProjectsMain extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ProjectsAdapter adapter;
    //Pieza pieza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects_main);

        //pieza = Pieza.fromIntent(getIntent());

        recyclerView = (RecyclerView) findViewById(R.id.projectsRecyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        adapter = new ProjectsAdapter(prepareDataProjects()/*, pieza, getIntent().getStringExtra("login_id"), this*/);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new com.example.qwerty.qrcodeejemplo.DividerItemDecoration(getApplicationContext()));
    }

    public ArrayList<ProjectsList> prepareDataProjects() {

        ArrayList<ProjectsList> projectsList = new ArrayList<>();

        ProjectsList project = new ProjectsList("1" , "Hola", "Aun", "Descripcion", new Date(12/07/2012), new Date(12/07/2012));
        projectsList.add(project);

        project = new ProjectsList("1" , "Hola", "sdasd", "Descripcion", new Date(12/07/2012), new Date(12/07/2012));
        projectsList.add(project);

        project = new ProjectsList("1" , "Hola", "1345y", "Descripcion", new Date(12/07/2012), new Date(12/07/2012));
        projectsList.add(project);

        project = new ProjectsList("1" , "Hola", "h56uj5u", "Descripcion", new Date(12/07/2012), new Date(12/07/2012));
        projectsList.add(project);

        project = new ProjectsList("1" , "Hola", "duefb", "Descripcion", new Date(12/07/2012), new Date(12/07/2012));
        projectsList.add(project);

        project = new ProjectsList("1" , "Hola", "34827yfr92", "Descripcion", new Date(12/07/2012), new Date(12/07/2012));
        projectsList.add(project);

        project = new ProjectsList("1" , "Hola", "fpi2m-", "Descripcion", new Date(12/07/2012), new Date(12/07/2012));
        projectsList.add(project);

        project = new ProjectsList("1" , "Hola", "cu3rfn02h", "Descripcion", new Date(12/07/2012), new Date(12/07/2012));
        projectsList.add(project);

        project = new ProjectsList("1" , "Hola", "fjg2nvoj", "Descripcion", new Date(12/07/2012), new Date(12/07/2012));
        projectsList.add(project);

        project = new ProjectsList("1" , "Hola", "vjn0t2pij0nv", "Descripcion", new Date(12/07/2012), new Date(12/07/2012));
        projectsList.add(project);

        project = new ProjectsList("1" , "Hola", "coi2jn0", "Descripcion", new Date(12/07/2012), new Date(12/07/2012));
        projectsList.add(project);

        project = new ProjectsList("1" , "Hola", "fvgybhinjo", "Descripcion", new Date(12/07/2012), new Date(12/07/2012));
        projectsList.add(project);

        project = new ProjectsList("1" , "Hola", "w4dfv6", "Descripcion", new Date(12/07/2012), new Date(12/07/2012));
        projectsList.add(project);

        project = new ProjectsList("1" , "Hola", "cyfvguhbinj", "Descripcion", new Date(12/07/2012), new Date(12/07/2012));
        projectsList.add(project);

        return projectsList;
    }
}
