public class Position {
    public int columns;
    public int rows;

    public Position()
    {

    }
    public Position(int rows, int columns) {
        this.columns = columns;
        this.rows = rows;
    }

    public int row()
    {
        return rows;
    }

    public int col()
    {
        return columns;
    }


    @Override
    public boolean equals(Object obj)
    {
        Position a = (Position) obj;
        return columns == a.col() && rows == a.rows;

    }

}