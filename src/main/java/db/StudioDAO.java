package db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class StudioDAO {

    public void insertStudio(Studio studio) {
        String query = "INSERT INTO studio(studio_nev, szekhely, alapitasi_ev) VALUES ('" + studio.getStudio_name() + "', '" + studio.getHeadquarter() + "', '" + studio.getYear() + "')";
        DbDAO.executeUpdate(query);
    }

    public Studio getStudioById(int id) {
        String query = "SELECT * FROM studio WHERE studio_id=" + id;
        List<Map<String, Object>> data = DbDAO.executeQuery(query);
        if (data != null) {
            Map<String, Object> studioById = data.get(0);
            java.sql.Date date = java.sql.Date.valueOf(studioById.get("alapitasi_ev").toString());
            LocalDate localDate = date.toLocalDate();
            int year = localDate.getYear();
            return new Studio(Integer.parseInt(studioById.get("studio_id").toString()), studioById.get("studio_nev").toString(), studioById.get("szekhely").toString(), year);
        } else return null;
    }

    public ObservableList<Studio> listStudios() {
        String query = "SELECT * FROM studio";

        ObservableList<Studio> result = FXCollections.observableArrayList();
        List<Map<String, Object>> data = DbDAO.executeQuery(query);

        if (data != null) {
            for (Map<String, Object> row : data) {
                java.sql.Date date = java.sql.Date.valueOf(row.get("alapitasi_ev").toString());
                LocalDate localDate = date.toLocalDate();
                int year = localDate.getYear();
                Studio studio = new Studio(Integer.parseInt(row.get("studio_id").toString()), row.get("studio_nev").toString(), row.get("szekhely").toString(), year);
                result.add(studio);
            }
            return result;
        } else return null;
    }

    public void deleteStudio(Studio studio) {
        String query = "DELETE FROM studio WHERE studio_id=" + studio.getStudio_id();
        DbDAO.executeUpdate(query);
    }

    public void updateStudio(Studio studio) {
        String query = "UPDATE studio SET studio_nev='" + studio.getStudio_name() + "', szekhely='" + studio.getHeadquarter() + "', alapitasi_ev='" + studio.getYear() + "' WHERE studio_id=" + studio.getStudio_id();
        DbDAO.executeUpdate(query);
    }
}
