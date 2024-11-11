public class Position {
    private int columns;
    private int rows;


    public Position(int rows, int columns) {
        this.columns = columns;
        this.rows = rows;
    }

    public int getRow() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }


    public void setColumns(int columns) {
        this.columns = columns;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }


}