import java.sql.Array;

public class Move {
    Disc disc;
    Position position;

    public Move(Position position, Disc disc)
    {
        this.position = position;
        this.disc = disc;
    }
    public Position position() {
        return  position;
    }

    public Disc disc() {
        return disc;
    }
}
