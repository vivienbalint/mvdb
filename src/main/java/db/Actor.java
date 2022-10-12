package db;

import java.sql.Date;

public class Actor {
    int actor_id;
    String firstName;
    String lastName;
    Date dateOfBirth;
    int sex;

    public Actor(int actor_id, String firstName, String lastName, Date dateOfBirth, int sex) {
        this.actor_id = actor_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
    }

    public Actor(String firstName, String lastName, Date dateOfBirth, int sex) {
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

    public int getSex() {
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

    public void setSex(int sex) {
        this.sex = sex;
    }
}
