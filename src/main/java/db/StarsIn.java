package db;

public class StarsIn {
    Actor actor;
    Movie movie;

    public StarsIn(Actor actor, Movie movie) {
        this.actor = actor;
        this.movie = movie;
    }

    public Actor getActor() {
        return actor;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
