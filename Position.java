/**
 * Represents a position in the board with row and column coordinates.
 */
public class Position {
    public int columns;
    public int rows;

    /**
     * Default constructor for Position.
     *
     * Initializes a position with default values for rows and columns.
     */
    public Position()
    {

    }

    /**
     * Constructs a Position object with specified row and column values.
     *
     * @param rows the row index
     * @param columns the column index
     */
    public Position(int rows, int columns) {
        this.columns = columns;
        this.rows = rows;
    }

    /**
     * Retrieves the row index of this position.
     *
     * @return the eow index
     */
    public int row()
    {
        return rows;
    }

    /**
     * Retrieves the column index of this position.
     *
     * @return the column index
     */
    public int col()
    {
        return columns;
    }


    /**
     * Compares this position to another position for equality.
     *
     * @param obj the object to compare
     * @return true if the object is a Position with the same row and column values; false otherwise
     */
    @Override
    public boolean equals(Object obj)
    {
        Position a = (Position) obj;
        return columns == a.col() && rows == a.rows;

    }

}