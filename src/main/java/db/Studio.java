package db;

public class Studio {
    private int studio_id;
    private String studio_name;
    private String headquarter;
    private int year;

    public Studio(int studio_id, String studio_name, String headquarter, int year) {
        this.studio_id = studio_id;
        this.studio_name = studio_name;
        this.headquarter = headquarter;
        if (year >= 1901 && year <= 2022) {
            this.year = year;
        } else throw new IllegalArgumentException("Year must be between 1901 and 2022");
    }

    public Studio(String studio_name, String headquarter, int year) {
        this.studio_name = studio_name;
        this.headquarter = headquarter;
        if (year >= 1901 && year <= 2022) {
            this.year = year;
        } else throw new IllegalArgumentException("Year must be between 1901 and 2022");
    }

    public int getStudio_id() {
        return studio_id;
    }

    public String getStudio_name() {
        return studio_name;
    }

    public String getHeadquarter() {
        return headquarter;
    }

    public int getYear() {
        return year;
    }

    public void setStudio_name(String studio_name) {
        this.studio_name = studio_name;
    }

    public void setHeadquarter(String headquarter) {
        this.headquarter = headquarter;
    }

    public void setYear(int year) {
        if (year >= 1901 && year <= 2022) {
            this.year = year;
        } else throw new IllegalArgumentException("Year must be between 1901 and 2022");
    }
}
