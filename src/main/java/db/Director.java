package db;

public class Director {
    int director_id;
    String lastName;
    String firstName;
    int sex;

    public Director(int director_id, String lastName, String firstName, int sex) {
        this.director_id = director_id;
        this.lastName = lastName;
        this.firstName = firstName;
        if (sex == 0 || sex == 1) {
            this.sex = sex;
        } else throw new IllegalArgumentException("The value for sex can only be 0 for females and 1 for males");
    }

    public Director(String lastName, String firstName, int sex) {
        this.lastName = lastName;
        this.firstName = firstName;
        if (sex == 0 || sex == 1) {
            this.sex = sex;
        } else throw new IllegalArgumentException("The value for sex can only be 0 for females and 1 for males");
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

    public int getSex() {
        return sex;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
