package com.example.demo.Controller;
import com.example.demo.Model.domain.Board;
import com.example.demo.service.BoardService;
import com.example.demo.utils.AlertUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController  implements Initializable {

    @FXML private StackPane Box00, Box01, Box02, Box10, Box11, Box12, Box20, Box21, Box22;

    @FXML private GridPane grid;

    @FXML private Label PlayerLB;
    @FXML private BorderPane borderPane;

    private  HashMap <StackPane,Boolean> MapPane;
    private  Board board ;
    private BoardService boardService;
    private AlertUtils alert;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MapPane = new HashMap<>();
        board = new Board();
        boardService = new BoardService(board);
        alert = new AlertUtils();
        setOnclickPane();
        fillMap();
        PlayerLB.setText(board.getCurrentPlayer());
    }

    private void fillMap() {
        List.of(Box00, Box01, Box02, Box10, Box11, Box12, Box20, Box21, Box22)
                .forEach(p -> MapPane.put(p, false));
    }

    private void setOnclickPane() {
        for (Node node : grid.getChildren()) {
            if (node instanceof StackPane panel) {
                panel.setOnMouseClicked(event -> manageClickPanel(panel));
                if (isFull()){
                    cleanPane();
                }
            }
        }
    }

    private void manageClickPanel(StackPane panel) {
        if (!MapPane.containsKey(panel) || MapPane.get(panel)) return;

        dialPanel(panel);
        placePlayer(panel);
        checkbox(panel);

        if (isFull()) {
            handleEndGame(alert.showConfirmation("Alert", "Juego terminado", "¿Quieres empezar otra vez?"));
            return;
        }

        changeShift();
    }

    private void handleEndGame(Optional<ButtonType> result) {
        if (result.isEmpty() || result.get() != ButtonType.OK) {
            Platform.exit();
            return;
        }
        cleanPane();
    }

    private void dialPanel(StackPane panel) {
        MapPane.compute(panel, (k, value) -> true);
    }

    private void placePlayer(StackPane panel) {
        Label label = new Label(boardService.getCurrentPlayer());
        panel.getChildren().add(label);
    }

    private void changeShift() {
        boardService.changeTurn();
        PlayerLB.setText(boardService.getCurrentPlayer());
        PlayerLB.setStyle("-fx-text-fill: " +
                (board.getCurrentPlayer().equals("X") ? "#e74c3c;" : "#3498db;"));
    }

    private void checkbox(StackPane panel) {
        Integer col = GridPane.getColumnIndex(panel);
        Integer row = GridPane.getRowIndex(panel);

        if (col == null || row == null) return;

        if ( !boardService.markBox(row, col)) {
            return;
        }

        if (boardService.isWinner()){
            handleEndGame(alert.showConfirmation("Fin","Juego terminado!","un jugador ha ganado"));
        }
    }

    private void cleanPane(){
        for (Node node : grid.getChildren()){
            if (node instanceof StackPane pane){
                pane.getChildren().removeIf(child -> child instanceof Label);
            }
        }
        MapPane.replaceAll((k, v) -> false);
        boardService.resetGame();
    }

    private boolean isFull(){
        return boardService.isDraw();
    }
}
