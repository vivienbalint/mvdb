package db;

public class Director {

    int director_id;
    String lastName;
    String firstName;
    boolean sex;

    public Director(int director_id, String lastName, String firstName, boolean sex) {
        this.director_id = director_id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.sex = sex;
    }

    public int getDirector_id() {
        return director_id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public boolean isSex() {
        return sex;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }
}
