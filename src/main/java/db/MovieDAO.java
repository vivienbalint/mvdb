package db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MovieDAO {

    public void insertMovie(Movie movie) {
        String query = "INSERT INTO film(cim, premier_eve, jatekido, rendezo_id_film) VALUES ('" + movie.getTitle() + "', '" + movie.getYear() + "', '" + movie.getLength() + "', '" + movie.getDirector().getDirector_id() + "')";
        DbDAO.executeUpdate(query);
//        for (Studio studio : movie.getMadeBy()) {
//            String query2 = "INSERT INTO gyartotta(studio_id_gyartotta, film_id_gyartotta) VALUES ('" + studio.getStudio_id() + "', '" + movie.getMovie_id() + "')";
//            DbDAO.executeUpdate(query2);
//        }
//        for (Actor actor : movie.getStars()) {
//            String query3 = "INSERT INTO szerepel(film_id_szerepel, szinesz_id_szerepel) VALUES ('" + movie.getMovie_id() + "', '" + actor.getActor_id() + "')";
//            DbDAO.executeUpdate(query3);
//        }
    }

    public List<Studio> getStudiosByMovieID(int movie_id) {
        List<Studio> studios = new ArrayList<>();
        String query = "SELECT * FROM studio INNER JOIN gyartotta ON studio.studio_id=gyartotta.studio_id_gyartotta WHERE gyartotta.film_id_gyartotta=" + movie_id;
        List<Map<String, Object>> data = DbDAO.executeQuery(query);
        if (data != null) {
            for (Map<String, Object> row : data) {
                java.sql.Date date = java.sql.Date.valueOf(row.get("alapitasi_ev").toString());
                LocalDate localDate = date.toLocalDate();
                int year = localDate.getYear();
                Studio studio = new Studio(Integer.parseInt(row.get("studio_id").toString()), row.get("studio_nev").toString(), row.get("szekhely").toString(), year);
                studios.add(studio);
            }
            return studios;
        } else return null;
    }

    public List<Actor> getActorsByMovieId(int movie_id) {
        List<Actor> actors = new ArrayList<>();
        String query = "SELECT * FROM szinesz INNER JOIN szerepel ON szinesz.szinesz_id=szerepel.szinesz_id_szerepel WHERE szerepel.film_id_szerepel=" + movie_id;
        List<Map<String, Object>> data = DbDAO.executeQuery(query);
        if (data != null) {
            for (Map<String, Object> row : data) {
                Actor actor = new Actor(Integer.parseInt(row.get("szinesz_id").toString()), row.get("keresztnev").toString(), row.get("vezeteknev").toString(), row.get("szuletesi_ido").toString(), Integer.parseInt(row.get("nem").toString()));
                actors.add(actor);
            }
            return actors;
        } else return null;
    }

    public Movie getMovieById(int movie_id) {
        DirectorDAO directorDAO = new DirectorDAO();
        String query = "SELECT * FROM film WHERE film_id=" + movie_id;
        List<Map<String, Object>> data = DbDAO.executeQuery(query);
        if (data != null) {
            Map<String, Object> movieById = data.get(0);
            java.sql.Date date = java.sql.Date.valueOf(movieById.get("premier_eve").toString());
            LocalDate localDate = date.toLocalDate();
            int year = localDate.getYear();
            return new Movie(Integer.parseInt(movieById.get("film_id").toString()), movieById.get("cim").toString(), year, Integer.parseInt(movieById.get("jatekido").toString()), directorDAO.getDirectorById(Integer.parseInt(movieById.get("rendezo_id_film").toString())));
        } else return null;
    }

    public ObservableList<Movie> listMovies() {
        DirectorDAO directorDAO = new DirectorDAO();
        String query = "SELECT * FROM film";
        ObservableList<Movie> result = FXCollections.observableArrayList();
        List<Map<String, Object>> data = DbDAO.executeQuery(query);
        if (data != null) {
            for (Map<String, Object> row : data) {
                java.sql.Date date = java.sql.Date.valueOf(row.get("premier_eve").toString());
                LocalDate localDate = date.toLocalDate();
                int year = localDate.getYear();
                Movie movie = new Movie(Integer.parseInt(row.get("film_id").toString()), row.get("cim").toString(), year, Integer.parseInt(row.get("jatekido").toString()), directorDAO.getDirectorById(Integer.parseInt(row.get("rendezo_id_film").toString())));
                result.add(movie);
            }
            return result;
        } else return null;
    }

    public void deleteMovie(Movie movie) {
        String query1 = "DELETE FROM film WHERE film_id=" + movie.getMovie_id();
        DbDAO.executeUpdate(query1);
//        for (Studio studio : movie.getMadeBy()) {
//            String query2 = "DELETE FROM gyartotta WHERE film_id_gyartotta=" + movie.getMovie_id();
//            DbDAO.executeUpdate(query2);
//        }
//        for (Actor actor : movie.getStars()) {
//            String query3 = "DELETE FROM szerepel WHERE film_id_szerepel=" + movie.getMovie_id();
//            DbDAO.executeUpdate(query3);
//        }
    }

    public void updateMovie(Movie movie) {
        String query1 = "UPDATE film SET cim='" + movie.getTitle() + "', premier_eve='" + movie.getYear() + "', jatekido='" + movie.getLength() + "', rendezo_id_film='" + movie.getDirector().getDirector_id() + "' WHERE film_id=" + movie.getMovie_id();
        DbDAO.executeUpdate(query1);
//        for (Studio studio : movie.getMadeBy()) {
//            String query2 = "UPDATE gyartotta SET studio_id_gyartotta='" + studio.getStudio_id() + "' WHERE film_id_gyartotta=" + movie.getMovie_id();
//            DbDAO.executeUpdate(query2);
//        }
//        for (Actor actor : movie.getStars()) {
//            String query3 = "UPDATE szerepel SET szinesz_id_szerepel='" + actor.getActor_id() + "' WHERE film_id_szerepel=" + movie.getMovie_id();
//            DbDAO.executeUpdate(query3);
//        }
    }
}
