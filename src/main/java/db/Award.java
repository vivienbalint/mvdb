package db;

public class Award {
    String awardName;
    int awardYear;
    String awardCategory;
    Movie awardedMovie;

    public Award(String awardName, int awardYear, String awardCategory, Movie awardedMovie) {
        this.awardName = awardName;
        if(awardYear >= 1900 && awardYear <= 2022) {
            this.awardYear = awardYear;
        } else throw new IllegalArgumentException("Year must be between 1900 and 2022");
        this.awardCategory = awardCategory;
        this.awardedMovie = awardedMovie;
    }

    public Award(String awardName, int awardYear, String awardCategory) {
        this.awardName = awardName;
        if(awardYear >= 1900 && awardYear <= 2022) {
            this.awardYear = awardYear;
        } else throw new IllegalArgumentException("Year must be between 1900 and 2022");
        this.awardCategory = awardCategory;
    }

    public String getAwardName() {
        return awardName;
    }

    public int getAwardYear() {
        return awardYear;
    }

    public String getAwardCategory() {
        return awardCategory;
    }

    public Movie getAwardedMovie() {
        return awardedMovie;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public void setAwardYear(int awardYear) {
        if(awardYear >= 1900 && awardYear <= 2022) {
            this.awardYear = awardYear;
        } else throw new IllegalArgumentException("Year must be between 1900 and 2022");
    }

    public void setAwardCategory(String awardCategory) {
        this.awardCategory = awardCategory;
    }

    public void setAwardedMovie(Movie awardedMovie) {
        this.awardedMovie = awardedMovie;
    }
}
