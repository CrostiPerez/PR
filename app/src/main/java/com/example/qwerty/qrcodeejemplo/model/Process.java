package model;

/**
 * Created by qwerty on 19/05/18.
 */

public class Process {
    int process_id;
    String process_name;

    public Process() {
    }

    public Process(int process_id, String process_name) {
        this.process_id = process_id;
        this.process_name = process_name;
    }

    public int getProcessID() {
        return process_id;
    }

    public void setProcessID(int process_id) {
        this.process_id = process_id;
    }

    public String getProcessName() {
        return process_name;
    }

    public void setProcessName(String process_name) {
        this.process_name = process_name;
    }
}
