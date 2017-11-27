package hu.webandmore.shutter_mvp.api.model;

import java.util.ArrayList;

import hu.webandmore.shutter_mvp.app.Enums;

public class Automation {

    int id;
    String name;
    Enums.ShutterMovement shutterMovement;
    Group group;
    ArrayList<PickedDay> pickedDays;

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
        return shutterMovement;
    }

    public void setShutterMovement(Enums.ShutterMovement shutterMovement) {
        this.shutterMovement = shutterMovement;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public ArrayList<PickedDay> getPickedDays() {
        return pickedDays;
    }

    public void setPickedDays(ArrayList<PickedDay> pickedDays) {
        this.pickedDays = pickedDays;
    }
}
