import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

/**
 * The GameLogic class implements the PlayableLogic interface and handles the core
 * logic for managing the game board, processing moves, counting flips, and managing
 * player turns in the game.
 */
public class GameLogic implements PlayableLogic {

    // The size of the board (8x8)
    final private int BOARD_SIZE = 8;

    // The game board, which holds the discs
    private Disc[][] board;

    // Players in the game
    private Player playerBlue;
    private Player playerRed;

    // The current player whose turn it is
    private Player currentPlayer;

    // Directions for disc flipping
    private final int[][] DIR = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};

    // Indicates the last player who made a move
    private boolean lastPlayer = true;

    // Indicates if the flipper logic has been applied
    private boolean flipper = false;

    // Stacks to manage undo functionality
    private Stack<Position> undoMoves = new Stack<>();
    private Stack<Integer> undoSteps = new Stack<>();

    // Stack to manage disc  flipping
    private Stack<Position> posCount = new Stack<>();

    /**
     * Constructor to initialize the game board.
     */
    public GameLogic() {
        board = new Disc[BOARD_SIZE][BOARD_SIZE];
    }

    /**
     * Places a disc at the specified position if it's valid and can flip opponent's discs.
     *
     * @param a    the position where the disc is to be placed
     * @param disc the disc being placed on the board
     * @return true if the disc was successfully placed, false otherwise
     */
    @Override
    public boolean locate_disc(Position a, Disc disc) {
        if (board[a.row()][a.col()] != null)
            return false;
        if (countFlips(a) > 0) {
            // Handling placement of different disc types
            switch (disc) {
                case UnflippableDisc unflippableDisc when disc.getOwner().getNumber_of_unflippedable() > 0 -> {
                    disc.getOwner().reduce_unflippedable();
                    board[a.row()][a.col()] = disc;
                }
                case BombDisc bombDisc when disc.getOwner().getNumber_of_bombs() > 0 -> {
                    disc.getOwner().reduce_bomb();
                    board[a.row()][a.col()] = disc;
                }
                case SimpleDisc simpleDisc -> board[a.row()][a.col()] = disc;
                case null, default -> {
                    return false;
                }
            }
            // Perform a move and save the moves for undo option
            flipper = !flipper;
            undoSteps.add(countFlips(a));
            undoMoves.add(a);

            // Printing the move information
            if (isFirstPlayerTurn())
                System.out.print("Player 1 ");
            else
                System.out.print("Player 2 ");
            System.out.println("placed a " + board[a.row()][a.col()].getType() + " in (" + a.row() + ", " + a.col() + ")");

            // Print flip information
            for (int i = 0; i < undoSteps.peek(); i++) {
                if (isFirstPlayerTurn())
                    System.out.print("Player 1 ");
                else
                    System.out.print("Player 2 ");
                System.out.println("flipped the " +
                        board[undoMoves.get(undoMoves.size() - i - 2).row()][undoMoves.get(undoMoves.size() - i - 2).col()].getType() +
                        " in (" + undoMoves.get(undoMoves.size() - i - 2).row() +
                        ", " + undoMoves.get(undoMoves.size() - i - 2).col() + ")");
            }
            System.out.print("\n");

            // End of turn
            flipper = !flipper;
            lastPlayer = !lastPlayer;
            return true;
        }
        return false;
    }

    /**
     * Retrieves the disc at a specific position on the board.
     *
     * @param position the position of the disc to retrieve
     * @return the disc at the specified position, or null if the position is empty
     */
    @Override
    public Disc getDiscAtPosition(Position position) {
        return board[position.row()][position.col()];
    }

    /**
     * Retrieves the size of the game board.
     *
     * @return the size of the board (8)
     */
    @Override
    public int getBoardSize() {
        return BOARD_SIZE;
    }

    /**
     * Returns a list of valid moves for the current player based on the game board.
     *
     * @return a list of valid positions where the player can place a disc
     */
    @Override
    public List<Position> ValidMoves() {
        List<Position> rightMoves = new ArrayList<>();
        for (int row = 0; row < BOARD_SIZE; row++)
            for (int col = 0; col < BOARD_SIZE; col++)
                if (board[row][col] == null)
                    if (countFlips(new Position(row, col)) > 0)
                        rightMoves.add(new Position(row, col));
        return rightMoves;
    }

    /**
     * Counts the number of flips that can occur when placing a disc at the given position.
     *
     * @param a the position where the disc is to be placed
     * @return the number of discs that would be flipped
     */
    @Override
    public int countFlips(Position a) {
        // Initialize the flip counter
        int count = 0;

        // Temporary stack to store discs that can be flipped
        Stack<Position> tempFlips = new Stack<>();

        // Attempt to count all possible flips in all directions for a given position
        for (int i = 0; i < BOARD_SIZE; i++) {
            count = count + flip(i, a.row(), a.col(), 0, tempFlips);
            posCount.addAll(tempFlips);

            // Clear the temporary stack for the next direction
            tempFlips.clear();
        }
        // If true the flips will apply
        if (flipper) {
            while (!posCount.empty()) {
                // Change the owner of the discs to the current player
                board[posCount.peek().row()][posCount.peek().col()].setOwner(currentPlayer);

                // Add flipped positions to the undo stack
                undoMoves.add(posCount.pop());
            }
        }
        // Clear the stack for the next given position
        posCount.clear();

        // Return the total number of possible flips
        return count;
    }


    /**
     * Helper function to attempt flipping discs in a specific direction.
     *
     * @param direction the direction in which to attempt flipping
     * @param row       the row to start from
     * @param col       the column to start from
     * @param count     the current flip count
     * @param temp      a stack to store flipped positions
     * @return the total number of flips in this direction
     */
    private int flip(int direction, int row, int col, int count, Stack<Position> temp) {
        int amount = 0;
        int rowAndDir = row + DIR[direction][0]; // Calculate the next row based on the direction
        int colAndDir = col + DIR[direction][1]; // Calculate the next column based on the direction

        // Check if the next position is within bounds
        if (0 <= rowAndDir && rowAndDir < BOARD_SIZE && 0 <= colAndDir && colAndDir < BOARD_SIZE) {
            Disc d = board[rowAndDir][colAndDir];
            Position p = new Position(rowAndDir, colAndDir);

            // If the disc belongs to the opponent, continue processing
            if (d != null && d.getOwner().isPlayerOne != lastPlayer) {
                if (d.getType().equals("⭕"))
                    count--; // Unflippable discs reduce the count since they cannot be flipped
                else if (d.getType().equals("💣")) {
                    // Handle bomb discs and their chain reaction
                    if (!temp.contains(p) && !posCount.contains(p)) {

                        temp.add(p); // Add bomb disc to the temporary stack
                        // Check surrounding discs for bomb effects
                        count = isBomb(rowAndDir, colAndDir, temp, count);
                    }
                } else {
                    // Normal disc handling
                    if (!temp.contains(p) && !posCount.contains(p))
                        temp.add(p); // Add position to the temporary stack
                    else count--; // Prevent duplicate entries from being processed
                }
                // Recursive call to continue checking in the same direction
                amount = flip(direction, rowAndDir, colAndDir, count + 1, temp);
            }
        } else {
            temp.clear(); // Clear the temporary stack if bounds are exceeded
            return 0; // No flips possible in this direction
        }

        // If the position is empty, verify if a valid flipping sequence was formed
        if (board[rowAndDir][colAndDir] == null) {
            if (!temp.empty()) {
                if (board[row][col].getOwner().isPlayerOne == lastPlayer)
                    temp.pop(); // Remove invalid entry
                else {
                    temp.clear(); // Clear invalid sequence
                    amount = 0;
                }
            }
            return 0;
        }
        // Return the count if a valid flip sequence is completed
        else if (board[rowAndDir][colAndDir].getOwner().isPlayerOne == lastPlayer) {
            if (lastPlayer)
                currentPlayer = playerBlue;
            else
                currentPlayer = playerRed;
            return count;
        } else
            // Continue accumulating flip counts
            return amount;
    }


    /**
     * Helper function to handle the behavior when a bomb disc is involved.
     *
     * @param row   the row of the bomb
     * @param col   the column of the bomb
     * @param temp  the temporary stack of flipped positions
     * @param count the current flip count
     * @return the total number of flips
     */
    private int isBomb(int row, int col, Stack<Position> temp, int count) {
        int rowAndDir;
        int colAndDir;

        // Find all the right discs to flip in all directions when a bomb disc is placed
        for (int i = 0; i < BOARD_SIZE; i++) {
            rowAndDir = row + DIR[i][0];
            colAndDir = col + DIR[i][1];

            // Ensure the position is within board bounds
            if (0 <= rowAndDir && rowAndDir < BOARD_SIZE && 0 <= colAndDir && colAndDir < BOARD_SIZE) {
                Disc d = board[rowAndDir][colAndDir];
                Position p = new Position(rowAndDir, colAndDir);

                // Check if the adjacent disc is existing and is not unflippable
                if (d != null && !(d instanceof UnflippableDisc)) {
                    if (d.getOwner().isPlayerOne != lastPlayer) {
                        if (!temp.contains(p) && !posCount.contains(p)) {
                            temp.add(p); // Add position to the temporary stack
                            count++; // Increment the flip count
                            if (d instanceof BombDisc)
                                // Handle recursive bomb chain reaction
                                count = isBomb(rowAndDir, colAndDir, temp, count);
                        }
                    }
                }
            }

        }
        return count; // Return the total flip count
    }

    /**
     * Retrieves the first player (blue).
     *
     * @return the first player (blue)
     */
    @Override
    public Player getFirstPlayer() {
        return playerBlue;
    }

    /**
     * Retrieves the second player (red).
     *
     * @return the second player (red)
     */
    @Override
    public Player getSecondPlayer() {
        return playerRed;
    }

    /**
     * Sets the players in the game.
     *
     * @param player1 the first player
     * @param player2 the second player
     */
    @Override
    public void setPlayers(Player player1, Player player2) {
        playerBlue = player1;
        playerRed = player2;

        // Initial disc placements on the board
        board[3][3] = new SimpleDisc(playerBlue);
        board[4][4] = new SimpleDisc(playerBlue);
        board[3][4] = new SimpleDisc(playerRed);
        board[4][3] = new SimpleDisc(playerRed);
    }


    /**
     * Checks if it's the first player's turn.
     *
     * @return true if it's the first player's turn, false otherwise
     */
    @Override
    public boolean isFirstPlayerTurn() {
        return lastPlayer;
    }


    /**
     * Determines if the game is finished based on available moves.
     *
     * @return true if the game is finished, false otherwise
     */
    @Override
    public boolean isGameFinished() {
        int discPlayerBlue = 0;
        int discPlayerRed = 0;
        if (ValidMoves().isEmpty()) {

            // Count the number of discs for each player
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (board[i][j] != null) {
                        if (board[i][j].getOwner().equals(playerBlue))
                            discPlayerBlue++;
                        else
                            discPlayerRed++;
                    }
                }
            }
            // Determine the winner
            if (discPlayerBlue > discPlayerRed) {
                playerBlue.addWin();
                System.out.println("Player 1 wins with " + discPlayerBlue + " discs! Player 2 had " +
                        discPlayerRed + " discs.");
            } else if (discPlayerRed > discPlayerBlue) {
                playerRed.addWin();
                System.out.println("Player 2 wins with " + discPlayerRed + " discs! Player 1 had " +
                        discPlayerBlue + " discs.");
            }
            return true;
        }
        return false;
    }

    /**
     * Resets the game board.
     */
    @Override
    public void reset() {
        board = new Disc[BOARD_SIZE][BOARD_SIZE];
        playerBlue.reset_bombs_and_unflippedable();
        playerRed.reset_bombs_and_unflippedable();
        undoMoves.clear();
        undoSteps.clear();

        // Reset initial board state
        board[3][3] = new SimpleDisc(playerBlue);
        board[4][4] = new SimpleDisc(playerBlue);
        board[3][4] = new SimpleDisc(playerRed);
        board[4][3] = new SimpleDisc(playerRed);
        lastPlayer = true;
    }


    /**
     * Undoes the last move made by a player.
     */
    @Override
    public void undoLastMove() {
        // Only allow undo for human players
        if (playerBlue.isHuman() && playerRed.isHuman()) {
            // Ensure there are moves to undo
            if (!undoMoves.empty() && !undoSteps.empty()) {

                System.out.println("Undoing last move:");
                for (int i = 0; i <= undoSteps.peek(); i++) {
                    Position undoPos = undoMoves.peek(); // Get the last position from the undo stack
                    Disc undoDisc = board[undoPos.row()][undoPos.col()]; // Get the last disk from the undo stack

                    // Increases back the amount of special discs
                    if (undoDisc instanceof UnflippableDisc)
                        undoDisc.getOwner().increase_unflippedable();
                    else if (undoDisc instanceof BombDisc)
                        undoDisc.getOwner().increase_bomb();

                    // Undo the placed disc or revert flipped discs and print the undo turn
                    if (i == 0) {
                        System.out.println("\tUndo: removing " + undoDisc.getType()
                                + " from (" + undoPos.row() + ", " + undoPos.col() + ") ");
                        // Removing the last placed disc
                        board[undoPos.row()][undoPos.col()] = null;
                    } else {
                        System.out.println("\tUndo: flipping back " +
                                board[undoPos.row()][undoPos.col()].getType()
                                + " in (" + undoPos.row() + ", " + undoPos.col() + ") ");

                        // Revent flipped discs
                        board[undoPos.row()][undoPos.col()].setOwner(currentPlayer);
                    }
                    undoMoves.pop(); // Remove the position from the undo stack
                }
                System.out.print("\n");

                lastPlayer = !lastPlayer; // Switch the turn back to the other player
                undoSteps.pop(); // Remove the last undo step count
            } else System.out.println("Undoing last move:\n" + "\tNo previous move available to undo.");
        }
    }
}