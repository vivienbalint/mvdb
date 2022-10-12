package db;

import java.sql.Date;

public class Actor {
    int actor_id;
    String firstName;
    String lastName;
    Date dateOfBirth;
    boolean sex;

    public Actor(int actor_id, String firstName, String lastName, Date dateOfBirth, boolean sex) {
        this.actor_id = actor_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
    }

    public int getActor_id() {
        return actor_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public boolean isSex() {
        return sex;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }
}
