package gui;

import db.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;

public class MovieController {

    @FXML
    public TableView<Movie> movieTable;
    public TableView<MadeBy> madeByTable;
    public TableView<StarsIn> starsInTable;
    public TableView<Genre> genreTable;
    public TextField title;
    public TextField year;
    public TextField length;
    public TextField genreName;
    public ComboBox<Director> movieDirector;
    public ComboBox<Movie> madeByMovies;
    public ComboBox<Movie> starsInMovies;
    public ComboBox<Movie> genreMovies;
    public ListView<Studio> studioListView;
    public ListView<Actor> actorListView;
    public Button madeBySubmitBtn;

    MadeByController madeByController = new MadeByController();
    StarsInController starsInController = new StarsInController();
    MovieDAO movieDAO = new MovieDAO();
    DirectorDAO directorDAO = new DirectorDAO();
    StudioDAO studioDAO = new StudioDAO();
    ActorDAO actorDAO = new ActorDAO();


    ListView<Studio> selectedStudios = new ListView<>();
    ListView<Actor> selectedActors = new ListView<>();


    TableColumn<Movie, String> movieTitle;
    TableColumn<Movie, Integer> movieYear;
    TableColumn<Movie, Integer> movieLength;
    TableColumn<Movie, String> directorName;

    public void initialize() {

        movieTitle = new TableColumn<>("Cím");
        movieYear = new TableColumn<>("Premier éve");
        movieLength = new TableColumn<>("Játékidő");
        directorName = new TableColumn<>("Rendezte");

        movieTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        movieYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        movieLength.setCellValueFactory(new PropertyValueFactory<>("length"));
        directorName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDirector().toString()));

        addToTable();

        movieTable.getColumns().addAll(movieTitle, movieYear, movieLength, directorName);

        movieDirector.setItems(directorDAO.listDirectors());

        madeByMovies.setItems(movieDAO.listMovies());
        madeByController.initTable(madeByTable);

        starsInMovies.setItems(movieDAO.listMovies());
        starsInController.initTable(starsInTable);

//        updateRecord();

        handleStudioListView();
        handleActorListView();


    }

    public void addToTable() {
        ObservableList<Movie> list = movieDAO.listMovies();

        movieTable.setItems(list);
    }

    private void handleStudioListView() {
        studioListView.setItems(studioDAO.listStudios());

        studioListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Studio item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? null : item.getStudio_name());
            }
        });
        studioListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        studioListView.getSelectionModel().selectedItemProperty().addListener((obs, ov, nv) -> selectedStudios.setItems(studioListView.getSelectionModel().getSelectedItems()));
    }

    private void handleActorListView() {
        actorListView.setItems(actorDAO.listActors());

        actorListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Actor item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? null : item.toString());
            }
        });
        actorListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        actorListView.getSelectionModel().selectedItemProperty().addListener((obs, ov, nv) -> selectedActors.setItems(actorListView.getSelectionModel().getSelectedItems()));

    }

    public void handleMovieSubmitBtn() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        if (title.getText() != null && !title.getText().isEmpty() && year.getText() != null && !length.getText().isEmpty() && length.getText() != null && !length.getText().isEmpty() && !movieDirector.getSelectionModel().isEmpty()) {
            if (title.getText().length() <= 45) {
                if (year.getText().matches("^\\d{4}$") && length.getText().matches("^\\d+$")) {
                    int movieYear = Integer.parseInt(year.getText());
                    int movieLength = Integer.parseInt(length.getText());
                    if (movieYear >= 1901 && movieYear <= 2025 && movieLength >= 5 && movieLength <= 600) {
                        String movieTitle = title.getText();
                        Movie movie = new Movie(movieTitle, movieYear, movieLength, movieDirector.getValue());
                        movieDAO.insertMovie(movie);
                        title.setText("");
                        year.setText("");
                        length.setText("");
                        addToTable();
                        madeByMovies.setItems(movieDAO.listMovies());
                        starsInMovies.setItems(movieDAO.listMovies());
                    } else {
                        error.setContentText("Az premier éve csak 1901 és 2025 közötti érték lehet, a hossz csak 5 és 600 közötti érték lehet!");
                        error.show();
                    }
                }
            } else {
                error.setContentText("A cím maximum 45 karakteres lehet!");
                error.show();
            }

        } else {
            error.setContentText("Nem lehet üres mező!");
            error.show();
        }
    }

    public void handleMadeBySubmitBtn() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        if (!madeByMovies.getSelectionModel().isEmpty()) {
            madeByController.handleMadeBySubmit(madeByTable, selectedStudios, madeByMovies.getValue());
            madeByMovies.valueProperty().set(null);
        } else {
            error.setContentText("Nincs film kiválasztva!");
        }
    }

    public void handleStarsInSubmitBtn() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        if (!starsInMovies.getSelectionModel().isEmpty()) {
            starsInController.handleStarsInSubmit(starsInTable, selectedActors, starsInMovies.getValue());
            starsInMovies.valueProperty().set(null);
        } else {
            error.setContentText("Nincs film kiválasztva!");
        }
    }

    public void handleGenreSubmitBtn() {

    }

    public void handleDeleteMovieBtn() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        Movie selectedItem = movieTable.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setHeaderText("Biztos törlöd a kiválasztott rekordot?");
            confirm.setContentText("Film: " + selectedItem.toString());
            ButtonType noButton = new ButtonType("Nem", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType yesButton = new ButtonType("Igen");
            confirm.getButtonTypes().setAll(noButton, yesButton);

            Optional<ButtonType> result = confirm.showAndWait();
            if (result.get() == yesButton) {
                movieDAO.deleteMovie(selectedItem);
                movieTable.getItems().remove(selectedItem);
            }
        } else {
            error.setContentText("Nincs rekord kiválasztva!");
            error.show();
        }
    }

    public void handleDeleteStudioBtn() {
        madeByController.handleDeleteStudioBtn(madeByTable);
    }

    public void handleDeleteActorBtn() {
        starsInController.handleDeleteStarsInBtn(starsInTable);
    }

    public void handleDeleteGenreBtn() {

    }
}
