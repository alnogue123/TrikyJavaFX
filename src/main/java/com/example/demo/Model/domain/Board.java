package com.example.demo.Model.domain;

import java.util.Arrays;

public class Board {
    private final String[][] board = new String[3][3];
    private String currentPlayer = "O";

    public boolean checkBox(int rowIndex, int columnIndex){
        if (board[rowIndex][columnIndex] == null){
            board[rowIndex][columnIndex] = currentPlayer;
            return true;
        }
        return false;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void changeShift(){
        currentPlayer = currentPlayer.equals("X") ? "O" : "X";
    }

    public String[][] getBoard() {
        return board;
    }

    public void resetBoard() {
        for (String[] strings : board) {
            Arrays.fill(strings, null);
        }
    }
}
