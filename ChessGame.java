/**
 * ChessGame.java
 * A console-based chess game implementation
 */

import java.util.*;

public class ChessGame {
    public static void main(String[] args) {
        ChessBoard board = new ChessBoard();
        GameController game = new GameController(board);
        game.startGame();
    }
}

/**
 * Represents a chess piece with its color, type, and movement logic
 */
class ChessPiece {
    private char type;  // K=King, Q=Queen, R=Rook, B=Bishop, N=Knight, P=Pawn
    private boolean isWhite;
    private boolean hasMoved;

    public ChessPiece(char type, boolean isWhite) {
        this.type = type;
        this.isWhite = isWhite;
        this.hasMoved = false;
    }

    public char getType() {
        return type;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setMoved() {
        this.hasMoved = true;
    }

    @Override
    public String toString() {
        // Display white pieces as uppercase and black pieces as lowercase
        return isWhite ? String.valueOf(type) : String.valueOf(Character.toLowerCase(type));
    }

    /**
     * Validates if a move from source to destination is legal for this piece
     */
    public boolean isValidMove(int srcRow, int srcCol, int destRow, int destCol, ChessPiece[][] board) {
        if (srcRow == destRow && srcCol == destCol) {
            return false; // Cannot move to the same position
        }

        // Check if destination has a piece of the same color
        if (board[destRow][destCol] != null && board[destRow][destCol].isWhite() == this.isWhite) {
            return false;
        }

        switch (Character.toUpperCase(type)) {
            case 'P': // Pawn
                return isValidPawnMove(srcRow, srcCol, destRow, destCol, board);
            case 'R': // Rook
                return isValidRookMove(srcRow, srcCol, destRow, destCol, board);
            case 'N': // Knight
                return isValidKnightMove(srcRow, srcCol, destRow, destCol);
            case 'B': // Bishop
                return isValidBishopMove(srcRow, srcCol, destRow, destCol, board);
            case 'Q': // Queen
                return isValidQueenMove(srcRow, srcCol, destRow, destCol, board);
            case 'K': // King
                return isValidKingMove(srcRow, srcCol, destRow, destCol);
            default:
                return false;
        }
    }

    private boolean isValidPawnMove(int srcRow, int srcCol, int destRow, int destCol, ChessPiece[][] board) {
        int direction = isWhite ? -1 : 1; // White pawns move up (-1), black pawns move down (+1)

        // Forward movement
        if (srcCol == destCol) {
            // Single square move
            if (destRow == srcRow + direction && board[destRow][destCol] == null) {
                return true;
            }

            // Double square move from starting position
            if (!hasMoved && destRow == srcRow + 2 * direction && board[destRow][destCol] == null
                    && board[srcRow + direction][srcCol] == null) {
                return true;
            }
        }

        // Capture diagonally
        else if ((destCol == srcCol + 1 || destCol == srcCol - 1) && destRow == srcRow + direction) {
            return board[destRow][destCol] != null && board[destRow][destCol].isWhite() != this.isWhite;
        }

        return false;
    }

    private boolean isValidRookMove(int srcRow, int srcCol, int destRow, int destCol, ChessPiece[][] board) {
        // Rook moves horizontally or vertically
        if (srcRow != destRow && srcCol != destCol) {
            return false;
        }

        // Check path is clear
        int rowStep = Integer.compare(destRow, srcRow);
        int colStep = Integer.compare(destCol, srcCol);

        int currentRow = srcRow + rowStep;
        int currentCol = srcCol + colStep;

        while (currentRow != destRow || currentCol != destCol) {
            if (board[currentRow][currentCol] != null) {
                return false; // Path is blocked
            }
            currentRow += rowStep;
            currentCol += colStep;
        }

        return true;
    }

    private boolean isValidKnightMove(int srcRow, int srcCol, int destRow, int destCol) {
        // Knight moves in L-shape: 2 squares in one direction and 1 square perpendicular
        int rowDiff = Math.abs(destRow - srcRow);
        int colDiff = Math.abs(destCol - srcCol);

        return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
    }

    private boolean isValidBishopMove(int srcRow, int srcCol, int destRow, int destCol, ChessPiece[][] board) {
        // Bishop moves diagonally
        if (Math.abs(destRow - srcRow) != Math.abs(destCol - srcCol)) {
            return false;
        }

        // Check path is clear
        int rowStep = Integer.compare(destRow, srcRow);
        int colStep = Integer.compare(destCol, srcCol);

        int currentRow = srcRow + rowStep;
        int currentCol = srcCol + colStep;

        while (currentRow != destRow && currentCol != destCol) {
            if (board[currentRow][currentCol] != null) {
                return false; // Path is blocked
            }
            currentRow += rowStep;
            currentCol += colStep;
        }

        return true;
    }

    private boolean isValidQueenMove(int srcRow, int srcCol, int destRow, int destCol, ChessPiece[][] board) {
        // Queen can move like a rook or bishop
        return isValidRookMove(srcRow, srcCol, destRow, destCol, board) ||
                isValidBishopMove(srcRow, srcCol, destRow, destCol, board);
    }

    private boolean isValidKingMove(int srcRow, int srcCol, int destRow, int destCol) {
        // King moves one square in any direction
        int rowDiff = Math.abs(destRow - srcRow);
        int colDiff = Math.abs(destCol - srcCol);

        return rowDiff <= 1 && colDiff <= 1;
    }
}

/**
 * Represents the chess board and manages the pieces
 */
class ChessBoard {
    private ChessPiece[][] board;
    private static final int BOARD_SIZE = 8;

    public ChessBoard() {
        board = new ChessPiece[BOARD_SIZE][BOARD_SIZE];
        initializeBoard();
    }

    private void initializeBoard() {
        // Place pawns
        for (int col = 0; col < BOARD_SIZE; col++) {
            board[1][col] = new ChessPiece('P', false); // Black pawns
            board[6][col] = new ChessPiece('P', true);  // White pawns
        }

        // Place rooks
        board[0][0] = new ChessPiece('R', false);
        board[0][7] = new ChessPiece('R', false);
        board[7][0] = new ChessPiece('R', true);
        board[7][7] = new ChessPiece('R', true);

        // Place knights
        board[0][1] = new ChessPiece('N', false);
        board[0][6] = new ChessPiece('N', false);
        board[7][1] = new ChessPiece('N', true);
        board[7][6] = new ChessPiece('N', true);

        // Place bishops
        board[0][2] = new ChessPiece('B', false);
        board[0][5] = new ChessPiece('B', false);
        board[7][2] = new ChessPiece('B', true);
        board[7][5] = new ChessPiece('B', true);

        // Place queens
        board[0][3] = new ChessPiece('Q', false);
        board[7][3] = new ChessPiece('Q', true);

        // Place kings
        board[0][4] = new ChessPiece('K', false);
        board[7][4] = new ChessPiece('K', true);
    }

    public void displayBoard() {
        System.out.println("\n  a b c d e f g h");
        System.out.println(" +-----------------+");

        for (int row = 0; row < BOARD_SIZE; row++) {
            System.out.print((8 - row) + "|");

            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] == null) {
                    System.out.print(" .");
                } else {
                    System.out.print(" " + board[row][col]);
                }
            }

            System.out.println(" |" + (8 - row));
        }

        System.out.println(" +-----------------+");
        System.out.println("  a b c d e f g h\n");
    }

    public boolean movePiece(int srcRow, int srcCol, int destRow, int destCol) {
        ChessPiece piece = board[srcRow][srcCol];

        if (piece == null) {
            return false;
        }

        if (piece.isValidMove(srcRow, srcCol, destRow, destCol, board)) {
            board[destRow][destCol] = piece;
            board[srcRow][srcCol] = null;
            piece.setMoved();
            return true;
        }

        return false;
    }

    public ChessPiece getPiece(int row, int col) {
        if (row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE) {
            return board[row][col];
        }
        return null;
    }

    public boolean isKingCaptured(boolean isWhiteKing) {
        // Check if king of specified color is still on the board
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                ChessPiece piece = board[row][col];
                if (piece != null && piece.getType() == 'K' && piece.isWhite() == isWhiteKing) {
                    return false; // King is found, not captured
                }
            }
        }
        return true; // King not found, considered captured
    }
}

/**
 * Controls the game flow and user interactions
 */
class GameController {
    private ChessBoard board;
    private Scanner scanner;
    private boolean isWhiteTurn;
    private boolean gameOver;

    public GameController(ChessBoard board) {
        this.board = board;
        this.scanner = new Scanner(System.in);
        this.isWhiteTurn = true; // White moves first
        this.gameOver = false;
    }

    public void startGame() {
        System.out.println("Welcome to Console Chess!");
        System.out.println("Enter moves in the format 'e2 e4' (source destination)");
        System.out.println("Type 'quit' to exit the game");

        while (!gameOver) {
            board.displayBoard();

            String currentPlayer = isWhiteTurn ? "White" : "Black";
            System.out.println(currentPlayer + "'s turn.");

            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("quit")) {
                System.out.println("Game ended by player.");
                break;
            }

            if (processMove(input)) {
                // Check if a king is captured (simplified win condition)
                if (board.isKingCaptured(!isWhiteTurn)) {
                    board.displayBoard();
                    System.out.println(currentPlayer + " wins! Game over.");
                    gameOver = true;
                } else {
                    // Switch turns
                    isWhiteTurn = !isWhiteTurn;
                }
            } else {
                System.out.println("Invalid move. Please try again.");
            }
        }

        scanner.close();
    }

    private boolean processMove(String moveInput) {
        String[] parts = moveInput.split("\\s+");

        if (parts.length != 2) {
            return false;
        }

        String src = parts[0];
        String dest = parts[1];

        if (src.length() != 2 || dest.length() != 2) {
            return false;
        }

        // Convert chess notation (e.g., "e2") to array indices
        int srcCol = src.charAt(0) - 'a';
        int srcRow = '8' - src.charAt(1);
        int destCol = dest.charAt(0) - 'a';
        int destRow = '8' - dest.charAt(1);

        // Validate indices
        if (srcCol < 0 || srcCol >= 8 || srcRow < 0 || srcRow >= 8 ||
                destCol < 0 || destCol >= 8 || destRow < 0 || destRow >= 8) {
            return false;
        }

        // Check if there is a piece at source position
        ChessPiece piece = board.getPiece(srcRow, srcCol);
        if (piece == null) {
            System.out.println("No piece at the source position.");
            return false;
        }

        // Check if the piece belongs to the current player
        if (piece.isWhite() != isWhiteTurn) {
            System.out.println("You can only move your own pieces.");
            return false;
        }

        // Attempt to move the piece
        return board.movePiece(srcRow, srcCol, destRow, destCol);
    }
}