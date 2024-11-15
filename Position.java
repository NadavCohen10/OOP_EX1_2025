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


//    public void setColumns(int columns) {
//        this.columns = columns;
//    }
//    public void setRows(int rows) {
//        this.rows = rows;
//    }



}