package com.example.qwerty.qrcodeejemplo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qwerty.qrcodeejemplo.R;
import com.example.qwerty.qrcodeejemplo.model.Piece;
import com.example.qwerty.qrcodeejemplo.model.ProjectsList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ProjectsViewHolder>{

    private ArrayList<ProjectsList> projectsList;
    private Piece pieza;
    private Context mContext;
    private  String id;
    private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");


    public class ProjectsViewHolder extends RecyclerView.ViewHolder {
        public TextView projectID, projectName, projectStatus, projectDescription, projectStartDate, projectFinishDate;

        public  ProjectsViewHolder(View view){
            super(view);
            projectID = (TextView)view.findViewById(R.id.projectID);
            projectName = (TextView)view.findViewById(R.id.projectName);
            projectStatus = (TextView)view.findViewById(R.id.projectStatus);
            projectDescription = (TextView)view.findViewById(R.id.projectDescription);
            projectStartDate = (TextView)view.findViewById(R.id.projectStartDate);
            projectFinishDate = (TextView)view.findViewById(R.id.projectFinishDate);
        }
    }

    public ProjectsAdapter(ArrayList<ProjectsList> projectsList /*Pieza pieza, String id, Context context*/){
        this.projectsList = projectsList;
        /*this.pieza = pieza;
        this.id = id;
        this.mContext = context;*/
    }

    @Override
    public ProjectsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.projects_list_row, parent, false);

        return new ProjectsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProjectsViewHolder holder, int position) {
        ProjectsList projects = projectsList.get(position);
        holder.projectID.setText("ID: " + projects.getProjectID());
        holder.projectName.setText("Nombre: " + projects.getProjectName());
        holder.projectStatus.setText("Estado: " + projects.getProjectStatus());
        holder.projectDescription.setText("Descripcion: " + projects.getProjectDescription());
        holder.projectStartDate.setText("Fecha de incio: " + df.format(projects.getProjectStartDate()));
        holder.projectFinishDate.setText("Fecha de entrega: " + df.format(projects.getProjectFinishDate()));

        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Cronometro.class);
                intent.putExtra("piece_id", pieza.getId());
                intent.putExtra("model_id", pieza.getModel_id());
                intent.putExtra("piece_name", pieza.getName());
                intent.putExtra("piece_processes", pieza.getProcesses().toString());
                intent.putExtra("muertes", pieza.getMuertes());
                intent.putExtra("login_id", id);
                mContext.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return projectsList.size();
    }

}
