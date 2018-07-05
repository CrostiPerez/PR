package com.example.qwerty.qrcodeejemplo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.qwerty.qrcodeejemplo.R;
import com.example.qwerty.qrcodeejemplo.model.Piece;
import com.example.qwerty.qrcodeejemplo.model.ProcessesList;
import com.example.qwerty.qrcodeejemplo.view.CronometroActivity;

public class ProcessesAdapter extends RecyclerView.Adapter<ProcessesAdapter.ProcessViewHolder>{

    private ArrayList<ProcessesList> processesList;
    private Piece pieza;
    private Context mContext;
    private  String id;


    public class ProcessViewHolder extends RecyclerView.ViewHolder {
        public TextView processID, processName;

        public  ProcessViewHolder(View view){
            super(view);
            processID = (TextView)view.findViewById(R.id.processID);
            processName = (TextView)view.findViewById(R.id.processName);
        }
    }

    public ProcessesAdapter(ArrayList<ProcessesList> processesList, Piece pieza, String id, Context context){
        this.processesList = processesList;
        this.pieza = pieza;
        this.id = id;
        this.mContext = context;
    }

    @Override
    public ProcessViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.processes_list_row, parent, false);

        return new ProcessViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProcessViewHolder holder, int position) {
        ProcessesList process = processesList.get(position);
        holder.processID.setText("ID: " + process.getProcessID() + "");
        holder.processName.setText("Nombre: " + process.getProcessName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CronometroActivity.class);
                mContext.startActivity(intent);
                ((Activity)mContext).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return processesList.size();
    }

    public void setProcessesList(ArrayList<ProcessesList> processesList) {
        this.processesList = processesList;
    }
}
