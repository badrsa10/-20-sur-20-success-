package edmt.dev.androidcollapsingtoolbar;

import java.io.Serializable;

/**
 * Created by badr on 27/01/18.
 */

public class Event implements Serializable {
    private String location;
    private String details;
    private String duration;
    private String time;


    public Event(){
        this.location="";
        this.details="";
        this.duration="";
        this.time="";
    }

    public String getDetails() {
        return details;
    }

    public String getDuration() {
        return duration;
    }

    public String getLocation() {
        return location;
    }

    public String getTime() {
        return time;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
