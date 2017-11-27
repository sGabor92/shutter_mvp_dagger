package hu.webandmore.shutter_mvp.api.model;

import hu.webandmore.shutter_mvp.app.Enums;

public class Automation {

    int id;
    Enums.ShutterMovement shutterMovement;
    Group group;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
