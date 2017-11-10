package hu.webandmore.shutter_mvp.api.model;

public class Group {

    private int id;
    private String name;
    private String color;
    private Channel[] channels;

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Channel[] getChannels() {
        return channels;
    }

    public void setChannels(Channel[] channels) {
        this.channels = channels;
    }
}
