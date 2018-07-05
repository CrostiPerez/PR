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
import com.example.qwerty.qrcodeejemplo.model.Model;
import com.example.qwerty.qrcodeejemplo.model.ModelsList;
import com.example.qwerty.qrcodeejemplo.view.CronometroActivity;

import java.util.ArrayList;

public class ModelsAdapter extends RecyclerView.Adapter<ModelsAdapter.ModelsViewHolder>{

    private ArrayList<ModelsList> mModelsLists;
    private Context mContext;
    private  String id;


    public class ModelsViewHolder extends RecyclerView.ViewHolder {
        public TextView modelID, modelName, modelDescription;

        ModelsViewHolder(View view){
            super(view);
            modelID =  view.findViewById(R.id.modelID);
            modelName = view.findViewById(R.id.modelName);
            modelDescription = view.findViewById(R.id.modelDescription);
        }
    }

    public ModelsAdapter(ArrayList<ModelsList> mModelsLists, Context context){
        this.mModelsLists = mModelsLists;
        this.mContext = context;
    }

    @Override
    public ModelsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.models_list_row, parent, false);

        return new ModelsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ModelsViewHolder holder, int position) {
        ModelsList models = mModelsLists.get(position);
        holder.modelID.setText(models.getModelID());
        holder.modelName.setText(models.getModelName());
        holder.modelDescription.setText(models.getModelDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.saveOnSharedPreferences(
                        Integer.parseInt(holder.modelID.getText().toString()),
                        holder.modelName.getText().toString(),
                        holder.modelDescription.getText().toString(),
                        mContext);
                Intent intent = new Intent(mContext, CronometroActivity.class);
                mContext.startActivity(intent);
                ((Activity)mContext).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mModelsLists.size();
    }

    public void setModelsList(ArrayList<ModelsList> modelsLists) {
        mModelsLists = modelsLists;
    }

}