//import java.sql.Array;

/**
 * Represents a move in a game involving a disc being placed at a specific position.
 */
public class Move {
    Disc disc;
    Position position;

    /**
     * Constructs a new Move object with a given position and disc.
     *
     * @param position the position where the disc is placed
     * @param disc     the disc being placed
     */
    public Move(Position position, Disc disc) {
        this.position = position;
        this.disc = disc;
    }

    /**
     * Returns the position of the move.
     *
     * @return the position where the disc is placed
     */
    public Position position() {
        return position;
    }

    /**
     * Returns the disc involved in the move.
     *
     * @return the disc being placed
     */
    public Disc disc() {
        return disc;
    }
}
