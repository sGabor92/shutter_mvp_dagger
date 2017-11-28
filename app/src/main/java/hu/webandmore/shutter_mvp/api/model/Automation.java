package hu.webandmore.shutter_mvp.api.model;

import java.util.ArrayList;

import hu.webandmore.shutter_mvp.app.Enums;

public class Automation {

    private int id;
    private String name;
    // TODO - kideríteni, hogy küldi az enum-ot
    private Enums.ShutterMovement shutter_movement;
    private ArrayList<Group> group;
    private String automation_time; // rendes UTC szerint
    private ArrayList<PickedDay> picked_days;
    private boolean active;

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

    public Enums.ShutterMovement getShutterMovement() {
        return shutter_movement;
    }

    public void setShutterMovement(Enums.ShutterMovement shutterMovement) {
        this.shutter_movement = shutterMovement;
    }

    public ArrayList<Group> getGroup() {
        return group;
    }

    public void setGroup(ArrayList<Group> group) {
        this.group = group;
    }

    public ArrayList<PickedDay> getPickedDays() {
        return picked_days;
    }

    public void setPickedDays(ArrayList<PickedDay> pickedDays) {
        this.picked_days = pickedDays;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
