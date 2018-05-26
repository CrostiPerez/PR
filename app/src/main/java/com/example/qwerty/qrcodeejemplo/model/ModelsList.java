package com.example.qwerty.qrcodeejemplo.model;

public class ModelsList {

    private String modelID, modelName, modelDescription;

    public ModelsList(){}

    public ModelsList(String modelID, String modelName, String modelDescription){
        this.modelID = modelID;
        this.modelName = modelName;
        this.modelDescription = modelDescription;
    }

    public String getModelID() {
        return modelID;
    }

    public void setModelID(String modelID) {
        this.modelID = modelID;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelDescription() {
        return modelDescription;
    }

    public void setModelDescription(String modelDescription) {
        this.modelDescription = modelDescription;
    }
}
