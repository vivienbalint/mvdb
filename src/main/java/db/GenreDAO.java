package db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Map;

public class GenreDAO {

    public void insertGenre(Genre genre) {
        String query = "INSERT INTO mufaj(mufaj, film_id_mufaj) VALUES ('" + genre.getGenreName() + "', '" + genre.getMovie().getMovie_id() + "')";
        DbDAO.executeUpdate(query);
    }

    public ObservableList<Genre> listGenre() {
        MovieDAO dao = new MovieDAO();
        String query = "SELECT * FROM mufaj";
        ObservableList<Genre> result = FXCollections.observableArrayList();
        List<Map<String, Object>> data = DbDAO.executeQuery(query);
        if (data != null) {
            for (Map<String, Object> row : data) {
                Genre genre = new Genre(row.get("mufaj").toString(), dao.getMovieById(Integer.parseInt(row.get("film_id_mufaj").toString())));
                result.add(genre);
            }
            return result;
        } else return null;
    }

    public void deleteGenre(Genre genre) {
        String query = "DELETE FROM mufaj WHERE mufaj= '" + genre.getGenreName() + "' AND film_id_mufaj= '" + genre.getMovie().getMovie_id() + "'";
        DbDAO.executeUpdate(query);
    }
}
