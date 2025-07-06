package com.example.demo.service;

import com.example.demo.Model.domain.Board;
import com.example.demo.Model.interfaces.IboardService;

import java.util.function.BiFunction;

public class BoardService implements IboardService {

    private final Board board;

    public BoardService(Board board) {
        this.board = board;
    }

    @Override
    public boolean isWinner() {
        String[][] board = this.board.getBoard();
        int size = board.length;


        for (int i = 0; i < size; i++) {
            if (traverseRows(board, i, size)) return true;
            if (traverseColumns(board, i)) return true;
        }

        if (traverseDiagonal(board, size, (i, s) -> new Integer[]{i, i})) return true;
        return traverseDiagonal(board, size, (i, s) -> new Integer[]{i, s - 1 - i});
    }

    private boolean traverseDiagonal(String[][] board, int size , BiFunction<Integer,Integer, Integer[]> generator){
        Integer[] pos0 = generator.apply(0,size);
        String first = board[pos0[0]][pos0[1]];
        if (first == null) return false;

        for (int i = 0; i < size; i++) {
            Integer[] pos = generator.apply(i,size);
            if (!first.equals(board[pos[0]][pos[1]])) {
                return false;
            }
        }
        return true;
    }

    private static boolean traverseColumns(String[][] board, int i) {
        if (board[0][i] != null) {
            boolean allEqual = true;
            for (String[] strings : board) {
                if (!board[0][i].equals(strings[i])) {
                    allEqual = false;
                    break;
                }
            }
            if (allEqual) return true;
        }
        return false;
    }

    private static boolean traverseRows(String[][] board, int i, int size) {
        if (board[i][0] != null) {
            boolean allEqual = true;
            for (int j = 1; j < size; j++) {
                if (!board[i][0].equals(board[i][j])) {
                    allEqual = false;
                    break;
                }
            }
            if (allEqual) return true;
        }
        return false;
    }

    @Override
    public boolean isDraw() {
        for (String[] row : board.getBoard()) {
            for (String cell : row) {
                if (cell == null) return false;
            }
        }
        return true;
    }


    @Override
    public void resetGame() {
        board.resetBoard();
    }

    @Override
    public void changeTurn() {
        board.changeShift();
    }

    @Override
    public boolean markBox(int rowIndex, int columnIndex) {
       return board.checkBox(rowIndex, columnIndex);
    }

    @Override
    public String getCurrentPlayer() {
        return board.getCurrentPlayer();
    }
}
