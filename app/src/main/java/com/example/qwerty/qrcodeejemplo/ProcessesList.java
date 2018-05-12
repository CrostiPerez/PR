package com.example.qwerty.qrcodeejemplo;

public class ProcessesList {

    private String processName;
    private int processID;


    public ProcessesList() {
    }

    public ProcessesList(int processID, String processName) {
        this.processName = processName;
        this.processID = processID;
    }


    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public int getProcessID() {
        return processID;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

}

