package db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class AwardDAO {

    public void insertAward(Award award) {
        String query = "INSERT INTO dij(nev, ev, kategoria, film_id_dij) VALUES ('" + award.getAwardName() + "', '" + award.getAwardYear() + "', '" + award.getAwardCategory() + "', '" + award.getAwardedMovie().getMovie_id() + "')";
        DbDAO.executeUpdate(query);
    }

    public ObservableList<Award> listAwards() {
        MovieDAO dao = new MovieDAO();
        String query = "SELECT * FROM dij";
        ObservableList<Award> result = FXCollections.observableArrayList();
        List<Map<String, Object>> data = DbDAO.executeQuery(query);
        if (data != null) {
            for (Map<String, Object> row : data) {
                java.sql.Date date = java.sql.Date.valueOf(row.get("ev").toString());
                LocalDate localDate = date.toLocalDate();
                int year = localDate.getYear();
                Award award = new Award(row.get("nev").toString(), year, row.get("kategoria").toString(), dao.getMovieById(Integer.parseInt(row.get("film_id_dij").toString())));
                result.add(award);
            }
            return result;
        } else return null;
    }

    public void deleteAward(Award award) {
        String query = "DELETE FROM dij WHERE nev= '" + award.getAwardName() + "' AND ev= '" + award.getAwardYear() + "' AND kategoria= '" + award.getAwardCategory() + "'";
        DbDAO.executeUpdate(query);
    }

    public void updateAward(Award award) {
        String query = "UPDATE dij SET nev='" + award.getAwardName() + "', ev='" + award.getAwardYear() + "', kategoria='" + award.getAwardCategory() + "', film_id_dij= '" + award.getAwardedMovie().getMovie_id() + "' WHERE nev= '" + award.getAwardName() + "' AND ev= '" + award.getAwardYear() + "' AND kategoria= '" + award.getAwardCategory() + "'";
        DbDAO.executeUpdate(query);
    }
}
