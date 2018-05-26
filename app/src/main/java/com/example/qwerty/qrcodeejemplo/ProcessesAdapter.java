package com.example.qwerty.qrcodeejemplo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ProcessesAdapter extends RecyclerView.Adapter<ProcessesAdapter.ProcessViewHolder>{

    private ArrayList<ProcessesList> processesList;
    private Pieza pieza;
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

    public ProcessesAdapter(ArrayList<ProcessesList> processesList, Pieza pieza, String id, Context context){
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
        holder.processID.setText(process.getProcessID() + "");
        holder.processName.setText(process.getProcessName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
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
                ((Activity)mContext).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return processesList.size();
    }

}
