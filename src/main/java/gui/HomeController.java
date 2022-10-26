package gui;

import db.Movie;
import db.MovieDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;
import javafx.stage.Stage;


public class HomeController {

    @FXML
    public TableView<Movie> query1Table;
    public TableView<Movie> query2Table;
    public TableView<Movie> query3Table;
    public TextField actorName;
    public GridPane gridPane;
    public Button query3btn;

    private MovieDAO dao = new MovieDAO();

    public void initialize() {
        initializeTable1();
        initializeTable2();
        initializeTable3();
    }

    public void initializeTable1() {
        TableColumn<Movie, String> studioNameCol = new TableColumn<>("Studió");
        TableColumn<Movie, Integer> countCol = new TableColumn<>("Gyártott film (db)");

        studioNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        countCol.setCellValueFactory(new PropertyValueFactory<>("count"));

        ObservableList<Movie> list = dao.getNumberOfMoviesByStudio();
        query1Table.setItems(list);
        query1Table.getColumns().addAll(studioNameCol, countCol);
    }

    public void initializeTable2() {
        TableColumn<Movie, String> movieNameCol = new TableColumn<>("Film");
        TableColumn<Movie, Integer> countCol = new TableColumn<>("Női szereplők (fő)");

        movieNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        countCol.setCellValueFactory(new PropertyValueFactory<>("count"));

        ObservableList<Movie> list = dao.getNumberOfFemaleActorsByMovie();
        query2Table.setItems(list);
        query2Table.getColumns().addAll(movieNameCol, countCol);
    }

    public void initializeTable3() {
        TableColumn<Movie, String> actorCol = new TableColumn<>("Színész");
        TableColumn<Movie, String> movieNameCol = new TableColumn<>("Film");

        actorCol.setCellValueFactory(new PropertyValueFactory<>("actorName"));
        movieNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        query3Table.getColumns().addAll(actorCol, movieNameCol);
    }

    public void handleQuery3() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        if (actorName.getText() != null && !actorName.getText().isEmpty()) {
            if (actorName.getText().length() <= 91) {
                ObservableList<Movie> list = dao.getMoviesByActorName(actorName.getText());
                query3Table.setItems(list);
            } else {
                error.setContentText("Túl hosszú a beírt név!");
                error.show();
            }

        } else {
            error.setContentText("A mező nincs kitöltve!");
            error.show();
        }
    }

    public void handleQuery1chart() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc = new BarChart<>(xAxis, yAxis);
        bc.setTitle("Gyártott filmek száma studiók szerint");
        xAxis.setLabel("Studió");
        yAxis.setLabel("Film (db)");

        ObservableList<Movie> list = dao.getNumberOfMoviesByStudio();

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (Movie movie : list) {
            series.getData().add(new XYChart.Data<>(movie.getName(), movie.getCount()));
        }
        bc.getData().add(series);
        setPopUpWindow(bc);
    }

    public void handleQuery2chart() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc = new BarChart<>(xAxis, yAxis);
        bc.setTitle("Női színészek száma filmek szerint");
        xAxis.setLabel("Film");
        yAxis.setLabel("Színésznő (fő)");

        ObservableList<Movie> list = dao.getNumberOfFemaleActorsByMovie();

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (Movie movie : list) {
            series.getData().add(new XYChart.Data<>(movie.getName(), movie.getCount()));
        }
        bc.getData().add(series);
        setPopUpWindow(bc);
    }

    private void setPopUpWindow(BarChart<String, Number> bc) {
        Popup popup = new Popup();
        popup.setX(500);
        popup.setY(200);

        bc.setStyle("-fx-background-color: \"#fcfcfc\";");

        Button closeBtn = new Button("X");
        closeBtn.setStyle("-fx-background-color: \"#fcfcfc\";");
        closeBtn.setCursor(Cursor.HAND);

        popup.getContent().addAll(bc, closeBtn);

        EventHandler<ActionEvent> event = actionEvent -> popup.hide();
        closeBtn.setOnAction(event);

        Stage stage = (Stage) gridPane.getScene().getWindow();
        popup.show(stage);
    }
}
