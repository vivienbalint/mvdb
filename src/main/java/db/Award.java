package db;

public class Award {
    private final String awardName;
    private final int awardYear;
    private final String awardCategory;
    private Movie awardedMovie;

    public Award(String awardName, int awardYear, String awardCategory, Movie awardedMovie) {
        this.awardName = awardName;
        if (awardYear >= 1901 && awardYear <= 2022) {
            this.awardYear = awardYear;
        } else throw new IllegalArgumentException("Year must be between 1901 and 2022");
        this.awardCategory = awardCategory;
        this.awardedMovie = awardedMovie;
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

    public void setAwardedMovie(Movie awardedMovie) {
        this.awardedMovie = awardedMovie;
    }

    @Override
    public String toString() {
        return awardName + " (" + awardYear + ", " + awardCategory + ")\n Elnyerte: " + awardedMovie.toString();
    }
}
