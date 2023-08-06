package com.example.myapplication;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProgramC {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BoardMove boardMove = new BoardMove();

        char[][] board = new char[8][8];
        for (int i = 0; i < 8; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < 8; j++) {
                board[i][j] = line.charAt(j);
            }
        }

        String result = boardMove.findMateMove(board);
        System.out.println(result);
    }


    static class BoardMove {

        private String findMateMove(char[][] board) {
            Map<Character, String> matches = new HashMap<>();
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    char piece = board[row][col];
                    if (isWhiteCoin(piece)) {
                        String move = findMateMoveForPiece(board, row, col, piece);
                        if (!move.isEmpty()) {
                            matches.put(piece, move);
                        }
                    }

                }
            }

            return matches.isEmpty() ? "No found" : matches.values().toArray()[0].toString();


        }

        private boolean isWhiteCoin(char piece) {
            return piece >= 'A' && piece <= 'Z';

        }

        private String findMateMoveForPiece(char[][] board, int row, int col, char piece) {
            StringBuilder move = new StringBuilder();

            switch (piece) {
                case 'P':
                    int i1 = row - 1;
                    int[] colOffsets = {-1, 0, 1};

                    for (int colOffset : colOffsets) {
                        int newCol = col + colOffset;

                        if (newCol >= 0 && newCol < 8) {
                            if (colOffset == 0 && board[i1][newCol] == '.') {

                                if (row == 1) {

                                    char[] promotionPieces = {'Q', 'R', 'B', 'N'};
                                    for (char promotedPiece : promotionPieces) {
                                        char[][] tempBoard = copyBoard(board);
                                        tempBoard[row][col] = promotedPiece;
                                        tempBoard[i1][newCol] = '.';
                                        if (isCheck(tempBoard, false)) {
                                            return "" + (char) (col + 'a') + (8 - row) + (char) (newCol + 'a') + (8 - i1);
                                        }
                                    }
                                } else {

                                    if (isCheckMateAfterMove(board, row, col, i1, newCol)) {
                                        return createMoveString(row, col, i1, newCol);
                                    }
                                }
                            } else if (Math.abs(colOffset) == 1 && Character.isLowerCase(board[i1][newCol])) {

                                if (row == 1) {

                                    char[] promotionPieces = {'N', 'R', 'B', 'Q'};
                                    for (char promotedPiece : promotionPieces) {
                                        char[][] tempBoard = copyBoard(board);
                                        tempBoard[row][col] = '.';
                                        tempBoard[i1][newCol] = promotedPiece;
                                        if (isCheck(tempBoard, false)) {

                                            return "" + (char) (col + 'a') + (8 - row) + (char) (newCol + 'a') + (8 - i1);

                                        }
                                    }
                                } else {

                                    if (isCheckMateAfterMove(board, row, col, i1, newCol)) {
                                        return createMoveString(row, col, i1, newCol);
                                    }
                                }
                            }
                        }
                    }
                    break;
                case 'N':
                    int[] knightMovesRow = {1, -1, 2, -2, 2, -2, 1, -1};
                    int[] knightMovesCol = {-2, -2, -1, -1, 1, 1, 2, 2};

                    for (int i = 0; i < 8; i++) {
                        int newRow = row + knightMovesRow[i];
                        int newCol = col + knightMovesCol[i];

                        if (isLegalMove(board, row, col, newRow, newCol)) {
                            char[][] tempBoard = copyBoard(board);
                            movePiece(tempBoard, row, col, newRow, newCol);
                            if (isCheck(tempBoard, false)) {
                                return "" + (char) (col + 'a') + (8 - row) + (char) (newCol + 'a') + (8 - newRow);
                            }
                        }
                    }
                    break;
                case 'B':
                    int[] diagonalRow = {-1, -1, 1, 1};
                    int[] diagonalCol = {-1, 1, 1, -1};

                    for (int direction = 0; direction < 4; direction++) {
                        int newRow = row;
                        int newCol = col;

                        while (true) {
                            newRow += diagonalRow[direction];
                            newCol += diagonalCol[direction];

                            if (!isInsideBoard(newRow, newCol)) {
                                break;
                            }

                            if (isLegalMove(board, row, col, newRow, newCol)) {
                                char[][] tempBoard = copyBoard(board);
                                movePiece(tempBoard, row, col, newRow, newCol);
                                if (isCheck(tempBoard, false)) {
                                    return "" + (char) (col + 'a') + (8 - row) + (char) (newCol + 'a') + (8 - newRow);
                                }
                            }

                            if (board[newRow][newCol] != '.') {
                                break;
                            }
                        }
                    }
                    break;


                case 'R':
                    for (int newRow = 0; newRow < 8; newRow++) {
                        for (int newCol = 0; newCol < 8; newCol++) {
                            if (isLegalRookMove(board, row, col, newRow, newCol)) {
                                char[][] tempBoard = makeMove(board, row, col, newRow, newCol);
                                if (isCheck(tempBoard, false)) {
                                    return "" + (char) (col + 'a') + (8 - row) + (char) (newCol + 'a') + (8 - newRow);
                                }
                            }
                        }

                    }
                    break;
                case 'Q':

                    int[] diagonalRow1 = {-1, -1, 1, 1};
                    int[] diagonalCol1 = {-1, 1, 1, -1};

                    for (int direction = 0; direction < 4; direction++) {
                        int newRow = row;
                        int newCol = col;

                        while (true) {
                            newRow += diagonalRow1[direction];
                            newCol += diagonalCol1[direction];

                            if (!isInsideBoard(newRow, newCol)) {
                                break;
                            }

                            if (isLegalMove(board, row, col, newRow, newCol)) {
                                char[][] tempBoard = copyBoard(board);
                                movePiece(tempBoard, row, col, newRow, newCol);
                                if (isCheck(tempBoard, false)) {
                                    return "" + (char) (col + 'a') + (8 - row) + (char) (newCol + 'a') + (8 - newRow);
                                }
                            }

                            if (board[newRow][newCol] != '.') {
                                break;
                            }
                        }
                    }

                    int[] straightRow1 = {-1, 1, 0, 0};
                    int[] straightCol1 = {0, 0, 1, -1};

                    for (int direction = 0; direction < 4; direction++) {
                        int newRow = row;
                        int newCol = col;

                        while (true) {
                            newRow += straightRow1[direction];
                            newCol += straightCol1[direction];

                            if (!isInsideBoard(newRow, newCol)) {
                                break;
                            }

                            if (isLegalMove(board, row, col, newRow, newCol)) {
                                char[][] tempBoard = copyBoard(board);
                                movePiece(tempBoard, row, col, newRow, newCol);
                                if (isCheck(tempBoard, false)) {
                                    return "" + (char) (col + 'a') + (8 - row) + (char) (newCol + 'a') + (8 - newRow);
                                }
                            }

                            if (board[newRow][newCol] != '.') {
                                break;
                            }
                        }
                    }
                    break;


                case 'K':
                    move.append(checkKingMoves(board, row, col));
                    break;
                default:
                    break;
            }
            return move.toString();
        }


        private boolean isInsideBoard(int row, int col) {
            return row >= 0 && row < 8 && col >= 0 && col < 8;
        }

        private char[][] makeMove(char[][] board, int fromRow, int fromCol, int toRow, int toCol) {
            char[][] newBoard = new char[8][8];


            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    newBoard[row][col] = board[row][col];
                }
            }


            newBoard[toRow][toCol] = newBoard[fromRow][fromCol];
            newBoard[fromRow][fromCol] = '.';

            return newBoard;
        }

        private String checkKingMoves(char[][] board, int row, int col) {
            int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

            for (int[] direction : directions) {
                int newRow = row + direction[0];
                int newCol = col + direction[1];

                if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                    if (board[newRow][newCol] == '.' || Character.isLowerCase(board[newRow][newCol])) {
                        if (isCheckMateAfterMove(board, row, col, newRow, newCol)) {
                            return createMoveString(row, col, newRow, newCol);
                        }
                    }
                }
            }
            return "";
        }

        private boolean isCheckMateAfterMove(char[][] board, int fromRow, int fromCol, int toRow, int toCol) {
            // Make a deep copy of the board
            char[][] newBoard = new char[8][8];
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    newBoard[i][j] = board[i][j];
                }
            }

            // Apply the move
            newBoard[toRow][toCol] = newBoard[fromRow][fromCol];
            newBoard[fromRow][fromCol] = '.';

            return isCheckMate(newBoard);
        }

        private String createMoveString(int fromRow, int fromCol, int toRow, int toCol) {
            return (char) ('a' + fromCol) + Integer.toString(8 - fromRow) + (char) ('a' + toCol) + Integer.toString(8 - toRow);
        }

        private boolean isCheckMate(char[][] board) {
            int blackKingRow = -1;
            int blackKingCol = -1;

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (board[i][j] == 'k') {
                        blackKingRow = i;
                        blackKingCol = j;
                        break;
                    }
                }
                if (blackKingRow != -1) {
                    break;
                }
            }

            boolean kingInCheck = isSquareAttacked(board, blackKingRow, blackKingCol);

            if (!kingInCheck) {
                return false;
            }


            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (Character.isLowerCase(board[i][j])) {
                        for (int newRow = 0; newRow < 8; newRow++) {
                            for (int newCol = 0; newCol < 8; newCol++) {
                                if (isLegalMove(board, i, j, newRow, newCol)) {
                                    char[][] newBoard = applyMove(board, i, j, newRow, newCol);
                                    if (!isSquareAttacked(newBoard, blackKingRow, blackKingCol)) {
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }


            return true;
        }

        private boolean isSquareAttacked(char[][] board, int targetRow, int targetCol) {
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if (Character.isUpperCase(board[row][col])) {
                        if (isLegalMove(board, row, col, targetRow, targetCol)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        private boolean isLegalMove(char[][] board, int fromRow, int fromCol, int toRow, int toCol) {
            char piece = board[fromRow][fromCol];

            switch (Character.toUpperCase(piece)) {
                case 'P':
                    return isLegalPawnMove(board, fromRow, fromCol, toRow, toCol);
                case 'N':
                    return isLegalKnightMove(board, fromRow, fromCol, toRow, toCol);
                case 'B':
                    return isLegalBishopMove(board, fromRow, fromCol, toRow, toCol);
                case 'R':
                    return isLegalRookMove(board, fromRow, fromCol, toRow, toCol);
                case 'Q':
                    return isLegalQueenMove(board, fromRow, fromCol, toRow, toCol);
                case 'K':
                    return isLegalKingMove(board, fromRow, fromCol, toRow, toCol);
                default:
                    return false;
            }
        }

        private boolean isLegalKnightMove(char[][] board, int fromRow, int fromCol, int toRow, int toCol) {
            int rowDiff = Math.abs(fromRow - toRow);
            int colDiff = Math.abs(fromCol - toCol);


            if (!((rowDiff == 1 && colDiff == 2) || (rowDiff == 2 && colDiff == 1))) {
                return false;
            }


            if (Character.isUpperCase(board[fromRow][fromCol]) && Character.isUpperCase(board[toRow][toCol])) {
                return false;
            }

            if (Character.isLowerCase(board[fromRow][fromCol]) && Character.isLowerCase(board[toRow][toCol])) {
                return false;
            }

            return true;
        }

        private boolean isLegalKingMove(char[][] board, int row, int col, int newRow, int newCol) {
            int rowDiff = Math.abs(row - newRow);
            int colDiff = Math.abs(col - newCol);

            if (rowDiff <= 1 && colDiff <= 1) {
                char[][] tempBoard = copyBoard(board);
                movePiece(tempBoard, row, col, newRow, newCol);
                return !isCheck(tempBoard, Character.isUpperCase(board[row][col]));
            }

            return false;
        }

        private char[][] copyBoard(char[][] board) {
            char[][] newBoard = new char[8][8];
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    newBoard[row][col] = board[row][col];
                }
            }
            return newBoard;
        }

        private void movePiece(char[][] board, int row, int col, int newRow, int newCol) {
            board[newRow][newCol] = board[row][col];
            board[row][col] = '.';
        }

        private boolean isCheck(char[][] board, boolean isWhiteKing) {
            char king = isWhiteKing ? 'K' : 'k';
            int kingRow = -1, kingCol = -1;

            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if (board[row][col] == king) {
                        kingRow = row;
                        kingCol = col;
                        break;
                    }
                }
                if (kingRow != -1) {
                    break;
                }
            }

            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    char piece = board[row][col];
                    if (Character.isUpperCase(piece) != isWhiteKing && isLegalMoveIgnoringKingSafety(board, row, col, kingRow, kingCol)) {
                        return true;
                    }
                }
            }

            return false;
        }

        private boolean isLegalMoveIgnoringKingSafety(char[][] board, int row, int col, int newRow, int newCol) {
            if (row < 0 || row >= 8 || col < 0 || col >= 8 || newRow < 0 || newRow >= 8 || newCol < 0 || newCol >= 8) {
                return false;
            }

            char piece = board[row][col];

            if (Character.isUpperCase(piece)) {
                if (Character.isUpperCase(board[newRow][newCol])) {
                    return false;
                }
            } else {
                if (Character.isLowerCase(board[newRow][newCol])) {
                    return false;
                }
            }

            switch (Character.toUpperCase(piece)) {
                case 'P':
                    return isLegalPawnMove(board, row, col, newRow, newCol);
                case 'N':
                    return isLegalKnightMove(board, row, col, newRow, newCol);
                case 'B':
                    return isLegalBishopMove(board, row, col, newRow, newCol);
                case 'R':
                    return isLegalRookMove(board, row, col, newRow, newCol);
                case 'Q':
                    return isLegalQueenMove(board, row, col, newRow, newCol);
                case 'K':
                    return isLegalKingMove(board, row, col, newRow, newCol);
                default:
                    return false;
            }
        }

        private boolean isLegalRookMove(char[][] board, int fromRow, int fromCol, int toRow, int toCol) {
            int rowDiff = Math.abs(fromRow - toRow);
            int colDiff = Math.abs(fromCol - toCol);


            if (rowDiff != 0 && colDiff != 0) {
                return false;
            }


            int rowStep = (toRow - fromRow) == 0 ? 0 : (toRow - fromRow) / rowDiff;
            int colStep = (toCol - fromCol) == 0 ? 0 : (toCol - fromCol) / colDiff;

            int newRow = fromRow + rowStep;
            int newCol = fromCol + colStep;
            while (newRow != toRow || newCol != toCol) {
                if (board[newRow][newCol] != '.') {
                    return false;
                }
                newRow += rowStep;
                newCol += colStep;
            }


            if (Character.isUpperCase(board[fromRow][fromCol]) && Character.isUpperCase(board[toRow][toCol])) {
                return false;
            }

            if (Character.isLowerCase(board[fromRow][fromCol]) && Character.isLowerCase(board[toRow][toCol])) {
                return false;
            }

            return true;
        }

        private boolean isLegalBishopMove(char[][] board, int fromRow, int fromCol, int toRow, int toCol) {
            int rowDiff = Math.abs(fromRow - toRow);
            int colDiff = Math.abs(fromCol - toCol);


            if (rowDiff != colDiff) {
                return false;
            }


            int rowStep = rowDiff == 0 ? 0 : (toRow - fromRow) / rowDiff;
            int colStep = colDiff == 0 ? 0 : (toCol - fromCol) / colDiff;

            int newRow = fromRow + rowStep;
            int newCol = fromCol + colStep;
            while (newRow != toRow || newCol != toCol) {
                if (board[newRow][newCol] != '.') {
                    return false;
                }
                newRow += rowStep;
                newCol += colStep;
            }


            if (Character.isUpperCase(board[fromRow][fromCol]) && Character.isUpperCase(board[toRow][toCol])) {
                return false;
            }

            if (Character.isLowerCase(board[fromRow][fromCol]) && Character.isLowerCase(board[toRow][toCol])) {
                return false;
            }

            return true;
        }

        private boolean isLegalPawnMove(char[][] board, int fromRow, int fromCol, int toRow, int toCol) {
            int rowDiff = Math.abs(fromRow - toRow);
            int colDiff = Math.abs(fromCol - toCol);

            int direction = Character.isUpperCase(board[fromRow][fromCol]) ? -1 : 1;

            if (fromRow + direction == toRow && fromCol == toCol && board[toRow][toCol] == '.') {
                return true;
            }

            if (fromRow + direction == toRow && colDiff == 1 && isOppositeColor(board[fromRow][fromCol], board[toRow][toCol])) {
                return true;
            }

            return false;
        }

        private boolean isOppositeColor(char piece1, char piece2) {
            if (Character.isUpperCase(piece1) && Character.isLowerCase(piece2)) {
                return true;
            }
            if (Character.isLowerCase(piece1) && Character.isUpperCase(piece2)) {
                return true;
            }
            return false;
        }


        private boolean isLegalQueenMove(char[][] board, int fromRow, int fromCol, int toRow, int toCol) {
            int rowDiff = Math.abs(fromRow - toRow);
            int colDiff = Math.abs(fromCol - toCol);

            if (rowDiff != 0 && colDiff != 0 && rowDiff != colDiff) {
                return false;
            }

            int rowStep = (toRow - fromRow) == 0 ? 0 : (toRow - fromRow) / rowDiff;
            int colStep = (toCol - fromCol) == 0 ? 0 : (toCol - fromCol) / colDiff;

            int newRow = fromRow + rowStep;
            int newCol = fromCol + colStep;
            while (newRow != toRow || newCol != toCol) {
                if (board[newRow][newCol] != '.') {
                    return false;
                }
                newRow += rowStep;
                newCol += colStep;
            }

            if (Character.isUpperCase(board[fromRow][fromCol]) && Character.isUpperCase(board[toRow][toCol])) {
                return false;
            }

            if (Character.isLowerCase(board[fromRow][fromCol]) && Character.isLowerCase(board[toRow][toCol])) {
                return false;
            }

            return true;
        }

        private char[][] applyMove(char[][] board, int fromRow, int fromCol, int toRow, int toCol) {
            char[][] newBoard = new char[8][8];
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    newBoard[i][j] = board[i][j];
                }
            }
            newBoard[toRow][toCol] = newBoard[fromRow][fromCol];
            newBoard[fromRow][fromCol] = '.';
            return newBoard;
        }
    }
}