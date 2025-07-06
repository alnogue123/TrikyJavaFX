package com.example.demo.Model.interfaces;

public interface IboardService {
    boolean isWinner();
    boolean isDraw();
    boolean markBox(int rowIndex, int columnIndex);
    void resetGame();
    void changeTurn();
    String getCurrentPlayer();
}
