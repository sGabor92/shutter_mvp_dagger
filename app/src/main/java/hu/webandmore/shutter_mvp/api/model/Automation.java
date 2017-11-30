package hu.webandmore.shutter_mvp.api.model;

import java.util.ArrayList;

import hu.webandmore.shutter_mvp.app.Enums;

public class Automation {

    private int id;
    private String name;
    // TODO - kideríteni, hogy küldi az enum-ot
    private Enums.ShutterMovement movement;
    private ArrayList<Group> groups;
    private String time; // rendes UTC szerint
    private ArrayList<PickedDay> days;
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
        return movement;
    }

    public void setShutter_movement(Enums.ShutterMovement movement) {
        this.movement = movement;
    }

    public ArrayList<Group> getGroup() {
        return groups;
    }

    public void setGroup(ArrayList<Group> groups) {
        this.groups = groups;
    }

    public String getAutomation_time() {
        return time;
    }

    public void setAutomation_time(String automation_time) {
        this.time = automation_time;
    }

    public ArrayList<PickedDay> getPicked_days() {
        return days;
    }

    public void setPicked_days(ArrayList<PickedDay> picked_days) {
        this.days = picked_days;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPickedDayString() {
        StringBuilder ret = new StringBuilder();
        for(PickedDay day: days) {
            ret.append(day.getName()).append(", ");
        }
        return ret.toString();
    }
}
