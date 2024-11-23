import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

public class GameLogic implements PlayableLogic {
    final private int BOARD_SIZE = 8;
    private Disc [][] board = new Disc[BOARD_SIZE][BOARD_SIZE];
    private Player playerBlue;
    private Player playerRed;
    private Player currentPlayer;
    private final int[][] DIR = {{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};
    private boolean lastPlayer = true;
    private boolean flipper = false ;
    private  Stack<Position> undoMoves = new Stack<>();
    private  Stack<Integer> undoSteps = new Stack<>();
    private boolean[][] bombMap = new boolean[BOARD_SIZE][BOARD_SIZE];
    private boolean[][] regDiscMap = new boolean[BOARD_SIZE][BOARD_SIZE];
    public GameLogic()
    {
        board = new Disc[BOARD_SIZE][BOARD_SIZE];
    }


    @Override
    public boolean locate_disc(Position a, Disc disc)
    {
        if (board[a.row()][a.col()]!=null)
            return false;
        if(countFlips(a)>0) {
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
            flipper = !flipper;
            undoSteps.add(countFlips(a));
            undoMoves.add(a);
            System.out.println("Player placed a " + board[a.row()][a.col()].getType() + " in (" + a.row() + ", " + a.col() + ")");
           for(int i = 0; i < undoSteps.peek();i++)
           {
               System.out.println("Player flipped the " +
                       board[undoMoves.get(undoMoves.size()-i-2).row()][undoMoves.get(undoMoves.size()-i-2).col()].getType() +
                       " in (" + undoMoves.get(undoMoves.size()-i-2).row() +
                       ", " + undoMoves.get(undoMoves.size()-i-2).col() + ")");
           }
            System.out.println();

            flipper = !flipper;
            lastPlayer = !lastPlayer;
            return true;
        }
        return false;
    }

    @Override
    public Disc getDiscAtPosition(Position position)
    {
        return board[position.row()][position.col()];
    }

    @Override
    public int getBoardSize()
    {
        return BOARD_SIZE;
    }

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

    @Override
    public int countFlips(Position a)
    {
        int count = 0;
        Stack<Position> tempFlips = new Stack<>();
        for(int i = 0; i<8;i++)
        {
            count = count + flip(i,a.row(),a.col(),0,tempFlips);
            if(flipper) {
                while (!tempFlips.empty()) {
                    board[tempFlips.peek().row()][tempFlips.peek().col()].setOwner(currentPlayer);
                    undoMoves.add(tempFlips.pop());
                }
            }
            tempFlips.clear();
        }
        return count;
    }



    private  int flip(int direction, int row, int col, int count, Stack<Position> temp)
    {

        if(row ==0 && col ==2 && direction == 0)
            System.out.println("hh");
        int amount = 0;
        if(0 <= (row + DIR[direction][0]) && (row + DIR[direction][0]) < 8 && 0 <= (col + DIR[direction][1]) && (col + DIR[direction][1]) < 8) {
            if (board[row + DIR[direction][0]][col + DIR[direction][1]] != null &&
                    board[row + DIR[direction][0]][col + DIR[direction][1]].getOwner().isPlayerOne != lastPlayer)
            {
                //if(board[row + DIR[direction][0]][col + DIR[direction][1]] instanceof BombDisc)
                //    System.out.println("he");
                //isBomb(row + DIR[direction][0],col + DIR[direction][1]);
                if(board[row + DIR[direction][0]][col + DIR[direction][1]].getType().equals("UD"))
                    count --;
                else if(board[row + DIR[direction][0]][col + DIR[direction][1]].getType().equals("DB"))
                {
                    temp.add(new Position(row + DIR[direction][0], col + DIR[direction][1]));
                   count = isBomb(row + DIR[direction][0],col + DIR[direction][1],temp, count);
//                    for( int i = 0; i<8;i++)
//                    {
//                        if(0 <= (row + DIR[direction][0] + DIR[i][0]) && (row + DIR[direction][0] + DIR[i][0]) < 8 &&
//                                0 <= (col + DIR[direction][1]+ DIR[i][1]) && (col +DIR[direction][1]+ DIR[i][1]) < 8)
//                            if(board[row + DIR[direction][0] + DIR[i][0]][col + DIR[direction][1] + DIR[i][1]]!=null && !(board[row + DIR[direction][0] + DIR[i][0]][col +DIR[direction][1] + DIR[i][1]] instanceof UnflippableDisc))
//                                if(board[row + DIR[direction][0] + DIR[i][0]][col +DIR[direction][1] + DIR[i][1]].getOwner().isPlayerOne!=lastPlayer) {
//                                    if(!temp.contains(new Position(row + DIR[direction][0] + DIR[i][0], col +DIR[direction][1] + DIR[i][1]))) {
//                                        temp.add(new Position(row + DIR[direction][0] + DIR[i][0], col + DIR[direction][1] + DIR[i][1]));
//                                        count++;
//                                    }
//                                }
//                    }
                }
                else
                    if(!temp.contains(new  Position(row + DIR[direction][0], col + DIR[direction][1])))
                        temp.add(new Position(row + DIR[direction][0], col + DIR[direction][1]));
                    else count--;


                amount = flip(direction, row + DIR[direction][0], col + DIR[direction][1], count + 1,temp) ;
            }
        }
        else
        {
            temp.clear();
            return 0;
        }
        if (board[row + DIR[direction][0]][col + DIR[direction][1]] == null) {
            if(!temp.empty()) {
                if (board[row][col].getOwner().isPlayerOne == lastPlayer)
                    temp.pop();
                else {
                    temp.clear();
                    amount = 0;
                }
            }
            return 0;
        }
        else if (board[row + DIR[direction][0]][col + DIR[direction][1]].getOwner().isPlayerOne == lastPlayer) {
            if(lastPlayer)
                currentPlayer = playerBlue;
            else
                currentPlayer = playerRed;
            return count;
        }
        else
            return amount;
    }


    private int isBomb(int row, int col, Stack<Position> temp, int count)
    {
      //  temp.add(new Position(row + DIR[direction][0], col + DIR[direction][1]));
        for( int i = 0; i<8;i++)
        {
            if(0 <= (row + DIR[i][0]) && (row + DIR[i][0]) < 8 &&
                    0 <= (col + DIR[i][1]) && (col + DIR[i][1]) < 8)
                if(board[row  + DIR[i][0]][col  + DIR[i][1]]!=null && !(board[row  + DIR[i][0]][col  + DIR[i][1]] instanceof UnflippableDisc))
                    if(board[row  + DIR[i][0]][col  + DIR[i][1]].getOwner().isPlayerOne!=lastPlayer) {
                        if(!temp.contains(new Position(row +  + DIR[i][0], col  + DIR[i][1]))) {
                            temp.add(new Position(row  + DIR[i][0], col  + DIR[i][1]));
                            count++;

                            if (board[row  + DIR[i][0]][col  + DIR[i][1]] instanceof BombDisc)
                                count =  isBomb(row  + DIR[i][0], col  + DIR[i][1],temp, count);
                        }
                    }
        }
        return count;
    }



//    private  int flip(int direction, int row, int col, int count, Stack<Position> temp)
//    {
//        if(row ==5 && col ==3)
//            System.out.println("hh");
//        int amount = 0;
//        if(0 <= (row + DIR[direction][0]) && (row + DIR[direction][0]) < 8 && 0 <= (col + DIR[direction][1]) && (col + DIR[direction][1]) < 8) {
//            if (board[row + DIR[direction][0]][col + DIR[direction][1]] != null &&
//                    board[row + DIR[direction][0]][col + DIR[direction][1]].getOwner().isPlayerOne != lastPlayer)
//            {
//                //if(board[row + DIR[direction][0]][col + DIR[direction][1]] instanceof BombDisc)
//                //    System.out.println("he");
//                   //isBomb(row + DIR[direction][0],col + DIR[direction][1]);
//                if(board[row + DIR[direction][0]][col + DIR[direction][1]] instanceof UnflippableDisc)
//                    count --;
//                else
//                    temp.add(new Position(row + DIR[direction][0], col + DIR[direction][1]));
//                amount = flip(direction, row + DIR[direction][0], col + DIR[direction][1], count + 1,temp) ;//+ isBomb(row + DIR[direction][0],col + DIR[direction][1],direction);
//            }
//        }
//        else
//        {
//            temp.clear();
//            amount = 0;
//            return 0;
//        }
//        if (board[row + DIR[direction][0]][col + DIR[direction][1]] == null) {
//            if(!temp.empty()) {
//                if (board[row][col].getOwner().isPlayerOne == lastPlayer)
//                    temp.pop();
//                else {
//                    temp.clear();
//                    amount = 0;
//                }
//            }
//            return 0;
//        }
//        else if (board[row + DIR[direction][0]][col + DIR[direction][1]].getOwner().isPlayerOne == lastPlayer) {
//               if(lastPlayer)
//                   currentPlayer = playerBlue;
//               else
//                   currentPlayer = playerRed;
//            return count;
//        }
//        else
//            return amount;
//    }


    @Override
    public Player getFirstPlayer()
    {
        return playerBlue;
    }

    @Override
    public Player getSecondPlayer()
    {
        return playerRed;
    }

    @Override
    public void setPlayers(Player player1, Player player2)
    {
        playerBlue = player1;
        playerRed = player2;
        board[3][3] = new SimpleDisc(playerBlue);
        board[4][4] = new SimpleDisc(playerBlue);
        board[3][4] = new SimpleDisc(playerRed);
        board[4][3] = new SimpleDisc(playerRed);
    }

    @Override
    public boolean isFirstPlayerTurn()
    {
        return lastPlayer;
    }

    @Override
    public boolean isGameFinished()
    {
        int discPlayerBlue = 0;
        int discPlayerRed = 0;
        if(ValidMoves().isEmpty())
        {
            for(int i = 0;i < BOARD_SIZE;i++)
            {
                for (int j = 0;j< BOARD_SIZE;j++)
                {
                    if(board[i][j]!=null)
                    {
                        if(board[i][j].getOwner().equals(playerBlue))
                            discPlayerBlue++;
                        else
                            discPlayerRed++;
                    }
                }
            }
            if (discPlayerBlue > discPlayerRed) {
                playerBlue.addWin();
                System.out.println("Player 1 wins with "+ discPlayerBlue+" discs! Player 2\n" +
                            "had "+ discPlayerRed + " discs.");
            }
            else if (discPlayerRed>discPlayerBlue) {
                playerRed.addWin();
                System.out.println("Player 2 wins with " + discPlayerRed + " discs! Player 1\n" +
                            "had " + discPlayerBlue + " discs.");
            }
            return true;
        }
        return false;
    }
    @Override
    public void reset() {
        board = new Disc[BOARD_SIZE][BOARD_SIZE];
        playerBlue.reset_bombs_and_unflippedable();
        playerRed.reset_bombs_and_unflippedable();
        undoMoves.clear();
        undoSteps.clear();
        board[3][3] = new SimpleDisc(playerBlue) ;
        board[4][4] = new SimpleDisc(playerBlue) ;
        board[3][4] = new SimpleDisc(playerRed) ;
        board[4][3] = new SimpleDisc(playerRed);
        lastPlayer = true;
    }

    @Override
    public void undoLastMove() {
        if(playerBlue.isHuman()&&playerRed.isHuman()) {
            if (!undoMoves.empty() && !undoSteps.empty()) {
                for (int i = 0; i <= undoSteps.peek(); i++) {
                    if(board[undoMoves.peek().row()][undoMoves.peek().col()] instanceof UnflippableDisc)
                        board[undoMoves.peek().row()][undoMoves.peek().col()].getOwner().increase_unflippedable();
                    else if (board[undoMoves.peek().row()][undoMoves.peek().col()] instanceof BombDisc)
                        board[undoMoves.peek().row()][undoMoves.peek().col()].getOwner().increase_bomb();
                    if (i == 0)
                        board[undoMoves.peek().row()][undoMoves.peek().col()] = null;
                    else
                        board[undoMoves.peek().row()][undoMoves.peek().col()].setOwner(currentPlayer);
                    undoMoves.pop();
                }
                lastPlayer = !lastPlayer;
                undoSteps.pop();
            }
            else System.out.println("Undoing last move:\n" + "\tNo previous move available to undo.");
        }
    }

}


