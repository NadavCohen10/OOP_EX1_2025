/**
 * Represents a Random AI player in the game.
 * <p>
 * The Random AI selects its moves and discs randomly.
 * This class extends the AIPlayer class and overrides relevant methods
 */
public class RandomAI extends AIPlayer {


    /**
     * Constructs a RandomAI player.
     *
     * @param isPlayerOne a boolean indicating whether this player is Player One
     *                    (true for Player One, false for Player Two)
     */
    public RandomAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }


    /**
     * Determines and returns a random move for the RandomAI based on the current game status.
     *
     * @param gameStatus the current state of the game, providing information about
     *                   valid moves and amount of flips
     * @return a randomly selected move, or null if no valid moves are available
     */
    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        Player randomAI;
        Position randomPosition;
        Disc randomDisc;

        // Determine whether the RandomAI is Player One or Player Two
        if (isPlayerOne)
            randomAI = gameStatus.getFirstPlayer();
        else
            randomAI = gameStatus.getSecondPlayer();

        // Check if there are valid moves available
        if (!gameStatus.ValidMoves().isEmpty()) {
            // Select a random position from the list of valid moves
            randomPosition = gameStatus.ValidMoves().get((int) (Math.random() * gameStatus.ValidMoves().size()));

            // Randomly decide which type of disc to play
            int rndmDisc = (int) (Math.random() * 3); // Random integer between 0 and 2

            // Choose the disc based on the random value and availability
            if (rndmDisc == 0 && randomAI.getNumber_of_bombs() > 0)
                randomDisc = new BombDisc(randomAI);
            else if (rndmDisc == 1 && randomAI.getNumber_of_unflippedable() > 0)
                randomDisc = new UnflippableDisc(randomAI);
            else randomDisc = new SimpleDisc(randomAI);

            // Return the move with the chosen position and disc
            return new Move(randomPosition, randomDisc);
        }
        // Return null if no valid moves are available
        return null;
    }
}

