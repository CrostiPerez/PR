package com.example.qwerty.qrcodeejemplo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qwerty.qrcodeejemplo.R;
import com.example.qwerty.qrcodeejemplo.model.Project;
import com.example.qwerty.qrcodeejemplo.model.ProjectsList;
import com.example.qwerty.qrcodeejemplo.view.ModelsMain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ProjectsViewHolder>{

    private ArrayList<ProjectsList> mProjectsList;
    private Context mContext;
    private DateFormat mDateFormat;


    public class ProjectsViewHolder extends RecyclerView.ViewHolder {
        public TextView projectID, projectName, projectStatus, projectDescription, projectStartDate, projectFinishDate;

        ProjectsViewHolder(View view){
            super(view);
            projectID = view.findViewById(R.id.projectID);
            projectName = view.findViewById(R.id.projectName);
            projectStatus = view.findViewById(R.id.projectStatus);
            projectDescription = view.findViewById(R.id.projectDescription);
            projectStartDate = view.findViewById(R.id.projectStartDate);
            projectFinishDate = view.findViewById(R.id.projectFinishDate);
        }
    }

    public ProjectsAdapter(ArrayList<ProjectsList> mProjectsLists, Context context){
        this.mProjectsList = mProjectsLists;
        this.mContext = context;
        mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    @Override
    public ProjectsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.projects_list_row, parent, false);

        return new ProjectsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProjectsViewHolder holder, int position) {
        ProjectsList projects = mProjectsList.get(position);
        holder.projectID.setText(projects.getProjectID());
        holder.projectName.setText(projects.getProjectName());
        holder.projectStatus.setText(projects.getProjectStatus());
        holder.projectDescription.setText(projects.getProjectDescription());
        holder.projectStartDate.setText(mDateFormat.format(projects.getProjectStartDate()));
        holder.projectFinishDate.setText(mDateFormat.format(projects.getProjectFinishDate()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Project.saveOnSharedPreferences(
                        Integer.parseInt(holder.projectID.getText().toString()),
                        holder.projectName.getText().toString(), mContext);
                Intent intent = new Intent(mContext, ModelsMain.class);
                mContext.startActivity(intent);
                ((Activity)mContext).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProjectsList.size();
    }

    public void setProjectsList(ArrayList<ProjectsList> projectsList) {
        mProjectsList = projectsList;
    }
}
