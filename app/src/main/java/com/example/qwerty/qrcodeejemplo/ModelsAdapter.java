package com.example.qwerty.qrcodeejemplo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ModelsAdapter extends RecyclerView.Adapter<ModelsAdapter.ModelsViewHolder>{

    private ArrayList<ModelsList> modelsList;
    //private Pieza pieza;
    private Context mContext;
    private  String id;
    //private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");


    public class ModelsViewHolder extends RecyclerView.ViewHolder {
        public TextView modelID, modelName, modelDescription;

        public  ModelsViewHolder(View view){
            super(view);
            modelID =  (TextView)view.findViewById(R.id.modelID);
            modelName = (TextView)view.findViewById(R.id.modelName);
            modelDescription = (TextView)view.findViewById(R.id.modelDescription);
        }
    }

    public ModelsAdapter(ArrayList<ModelsList> modelsList /*Pieza pieza, String id, Context context*/){
        this.modelsList = modelsList;
        /*this.pieza = pieza;
        this.id = id;
        this.mContext = context;*/
    }

    @Override
    public ModelsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.models_list_row, parent, false);

        return new ModelsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ModelsViewHolder holder, int position) {
        ModelsList models = modelsList.get(position);
        holder.modelID.setText("ID: " + models.getModelID());
        holder.modelName.setText("Nombre: " + models.getModelName());
        holder.modelDescription.setText("Descripcion: " + models.getModelDescription());

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
        return modelsList.size();
    }

}