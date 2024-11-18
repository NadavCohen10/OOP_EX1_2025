import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

public class GameLogic implements PlayableLogic {
    private Disc [][] board = new Disc[8][8];
    private Player playerBlue;
    private Player playerRed;
    private Player currentPlayer;

    final int[][] DIR = {{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};
    final private int BOARD_SIZE = 8;
    // if lastPlayer = 1 then playerBlue else then playerRed
    private boolean lastPlayer = true;
    private boolean flipper = false ;

    private  Stack<Position> undoMoves = new Stack<>();
    private  Stack<Integer> undoSteps = new Stack<>();

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
        if(flipper)
            System.out.println("there whas " + count +" flips");
        return count;
    }



    private  int flip(int direction, int row, int col, int count, Stack<Position> temp)
    {
        int amount = 0;
        if(0 <= (row + DIR[direction][0]) && (row + DIR[direction][0]) < 8 && 0 <= (col + DIR[direction][1]) && (col + DIR[direction][1]) < 8) {
            if (board[row + DIR[direction][0]][col + DIR[direction][1]] != null && board[row + DIR[direction][0]][col + DIR[direction][1]].getOwner().isPlayerOne != lastPlayer && !board[row + DIR[direction][0]][col + DIR[direction][1]].getType().equals("⭕")) {
                temp.add(new Position(row + DIR[direction][0], col + DIR[direction][1]));// לתקן
                amount = flip(direction, row + DIR[direction][0], col + DIR[direction][1], count + 1,temp);
            }
        }
        else return 0;

        if (board[row + DIR[direction][0]][col + DIR[direction][1]] == null) {
            if(!temp.empty()) {
                if (board[row][col].getOwner().isPlayerOne == lastPlayer)
                    temp.pop();
                else
                    temp.clear();
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
        System.out.println("set players");


    }

    @Override
    public boolean isFirstPlayerTurn()
    {
        return lastPlayer;
    }

    @Override
    public boolean isGameFinished()
    {
        return false;
    }

    @Override
    public void reset() {
        board = new Disc[BOARD_SIZE][BOARD_SIZE];
        playerBlue.reset_bombs_and_unflippedable();
        playerRed.reset_bombs_and_unflippedable();
        board[3][3] = new SimpleDisc(playerBlue) ;
        board[4][4] = new SimpleDisc(playerBlue) ;
        board[3][4] = new SimpleDisc(playerRed) ;
        board[4][3] = new SimpleDisc(playerRed);
        lastPlayer = true;
        System.out.println("reset");

    }

    @Override
    public void undoLastMove() {
        if(playerBlue.isHuman()&&playerRed.isHuman()) {
            if (!undoMoves.empty() && !undoSteps.empty()) {
                for (int i = 0; i <= undoSteps.peek(); i++) {
                    if (i == 0) {
                        board[undoMoves.peek().row()][undoMoves.peek().col()] = null;
                        undoMoves.pop();
                    } else {
                        board[undoMoves.peek().row()][undoMoves.peek().col()].setOwner(currentPlayer);
                        undoMoves.pop();
                    }
                }
                lastPlayer = !lastPlayer;
                undoSteps.pop();
            }
            else System.out.println("Undoing last move:\n" + "\tNo previous move available to undo.");
        }


    }
}

