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


}