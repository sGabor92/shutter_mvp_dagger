package hu.webandmore.shutter_mvp.api.model;

import java.util.ArrayList;

import hu.webandmore.shutter_mvp.app.Enums;

public class Automation {

    private int id;
    private String name;
    // TODO - kideríteni, hogy küldi az enum-ot
    private Enums.ShutterMovement shutter_movement;
    private ArrayList<Group> groups;
    private String automation_time; // rendes UTC szerint
    private ArrayList<PickedDay> picked_days;
    private boolean active = true;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Enums.ShutterMovement getShutter_movement() {
        return shutter_movement;
    }

    public void setShutter_movement(Enums.ShutterMovement shutter_movement) {
        this.shutter_movement = shutter_movement;
    }

    public ArrayList<Group> getGroup() {
        return groups;
    }

    public void setGroup(ArrayList<Group> groups) {
        this.groups = groups;
    }

    public String getAutomation_time() {
        return automation_time;
    }

    public void setAutomation_time(String automation_time) {
        this.automation_time = automation_time;
    }

    public ArrayList<PickedDay> getPicked_days() {
        return picked_days;
    }

    public void setPicked_days(ArrayList<PickedDay> picked_days) {
        this.picked_days = picked_days;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
