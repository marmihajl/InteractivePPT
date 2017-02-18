package hr.foi.air.interactiveppt.entities;

/**
 * Created by marin on 5.11.2016..
 */

public class User {
    public String fullName;
    public String id;

    public User(String fullName, String id) {
        this.fullName = fullName;
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
