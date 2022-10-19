package db;

public class Actor {
    int actor_id;
    String firstName;
    String lastName;
    String dateOfBirth;
    int sex;

    public Actor(int actor_id, String firstName, String lastName, String dateOfBirth, int sex) {
        this.actor_id = actor_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        if (sex == 0 || sex == 1) {
            this.sex = sex;
        } else throw new IllegalArgumentException("The value for sex can only be 0 for females and 1 for males");

    }

    public Actor(String firstName, String lastName, String dateOfBirth, int sex) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        if (sex == 0 || sex == 1) {
            this.sex = sex;
        } else throw new IllegalArgumentException("The value for sex can only be 0 for females and 1 for males");
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

    public String getDateOfBirth() {
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

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setSex(int sex) {
        if (sex == 0 || sex == 1) {
            this.sex = sex;
        } else throw new IllegalArgumentException("The value for sex can only be 0 for females and 1 for males");
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
