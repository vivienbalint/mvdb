package db;

public class Movie {
    private int movie_id;
    private String title;
    private int year;
    private int length;
    private Director director;

    private String name;
    private int count;
    private String actorName;

    public Movie(int movie_id, String title, int year, int length, Director director) {
        this.movie_id = movie_id;
        this.title = title;
        if (year >= 1901 && year <= 2025) {
            this.year = year;
        } else throw new IllegalArgumentException("Year must be between 1901 and 2025");
        if (length >= 5 && length <= 600) {
            this.length = length;
        } else throw new IllegalArgumentException("Length must be between 5 and 600 minutes");
        this.director = director;
    }

    public Movie(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public Movie(String actorName, String name) {
        this.actorName = actorName;
        this.name = name;
    }

    public Movie(String title, int year, int length, Director director) {
        this.title = title;
        if (year >= 1901 && year <= 2025) {
            this.year = year;
        } else throw new IllegalArgumentException("Year must be between 1901 and 2025");
        if (length >= 5 && length <= 600) {
            this.length = length;
        } else throw new IllegalArgumentException("Length must be between 5 and 600 minutes");
        this.director = director;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public int getLength() {
        return length;
    }

    public Director getDirector() {
        return director;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public String getActorName() {
        return actorName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        if (year >= 1901 && year <= 2025) {
            this.year = year;
        } else throw new IllegalArgumentException("Year must be between 1901 and 2025");
    }

    public void setLength(int length) {
        if (length >= 5 && length <= 600) {
            this.length = length;
        } else throw new IllegalArgumentException("Length must be between 5 and 600 minutes");

    }

    public void setDirector(Director director) {
        this.director = director;
    }

    @Override
    public String toString() {
        return title + " (" + year + ")";
    }
}
