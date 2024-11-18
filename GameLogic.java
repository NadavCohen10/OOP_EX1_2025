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
    //private  List<Position> tempFlips = new ArrayList<>();
    private  List<Position> positionToFlip = new ArrayList<>();

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
            countFlips(a);
          //  if(lastPlayer)
           //     currentPlayer = playerBlue;
          //  else
          //      currentPlayer = playerRed;

          //  for (Position flipIt :positionToFlip)
          //      board[flipIt.row()][flipIt.col()].setOwner(currentPlayer);

            flipper = !flipper;
            lastPlayer = !lastPlayer;

            return true;
        }
        return false;
    }

    @Override
    public Disc getDiscAtPosition(Position position)
    {
     //   System.out.println(position.row() +" "+ position.col());

        return board[position.row()][position.col()];

    }

    @Override
    public int getBoardSize()
    {
        return BOARD_SIZE;
    }

   // @Override
    public List<Position> ValidMoves(int a)
    {

        return List.of();
    }

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
       // tempFlips.clear();
        for(int i = 0; i<8;i++)
        {
            count = count + flip(i,a.row(),a.col(),0,tempFlips);//, flipper);
            if(flipper) {
                while (!tempFlips.empty()) {
                    //Position P = temp.pop();
                    board[tempFlips.peek().row()][tempFlips.peek().col()].setOwner(currentPlayer);
                    tempFlips.pop();
                }
            }
            tempFlips.clear();
           // positionToFlip.addAll(tempFlips);


        }
        // System.out.println(count);
        return count;
    }



    private  int flip(int direction, int row, int col, int count, Stack<Position> temp)//, boolean flipper)
    {
        //  List<Position> tempFlips = new ArrayList<>();
        // tempFlips.clear();
        int amount = 0;
        if(0 <= (row + DIR[direction][0]) && (row + DIR[direction][0]) < 8 && 0 <= (col + DIR[direction][1]) && (col + DIR[direction][1]) < 8) {
            if (board[row + DIR[direction][0]][col + DIR[direction][1]] != null && board[row + DIR[direction][0]][col + DIR[direction][1]].getOwner().isPlayerOne != lastPlayer) {
                temp.add(new Position(row + DIR[direction][0], col + DIR[direction][1]));// לתקן
                amount = flip(direction, row + DIR[direction][0], col + DIR[direction][1], count + 1,temp);
            }
        }
        else return 0;

        if(row == 2&& col ==4)
            System.out.println("hhh");
        if (board[row + DIR[direction][0]][col + DIR[direction][1]] == null) {
            if(!temp.empty()) {
                if (board[row][col].getOwner().isPlayerOne == lastPlayer)
                    //temp.clear();
                    temp.pop();
                else temp.clear();
            }
            return 0;
        }
        else if (board[row + DIR[direction][0]][col + DIR[direction][1]].getOwner().isPlayerOne == lastPlayer) {
               if(lastPlayer) //לתקן
               {
                   currentPlayer = playerBlue;//לתקן
               }
             else {
                   currentPlayer = playerRed;//ךתקן
               }

           //    for (Position num : temp)//לתקן
          //  while(!temp.empty())
           // {
               // Position P = temp.pop();
              //  board[temp.peek().row()][temp.peek().col()].setOwner(currentPlayer);
             //   temp.pop();
                        //num.row()][num.col()].setOwner(currentPlayer);//לתקן
          //  }

            //       board[num.row()][num.col()].setOwner(currentPlayer);//לתקן

            return count;
        }
        else
            return amount;
    }

    private  int flip1(int direction, int row, int col,int count)//, boolean flipper)
    {
      //  List<Position> tempFlips = new ArrayList<>();
       // tempFlips.clear();
        int amount = 0;
        if(0 <= (row + DIR[direction][0]) && (row + DIR[direction][0]) < 8 && 0 <= (col + DIR[direction][1]) && (col + DIR[direction][1]) < 8) {
           if (board[row + DIR[direction][0]][col + DIR[direction][1]] != null) {
              // tempFlips.add(new Position(row + DIR[direction][0], col + DIR[direction][1]));// לתקן
              // amount = flip(direction, row + DIR[direction][0], col + DIR[direction][1], count + 1);
           }
       }
       else return 0;

       if(row == 2&& col ==4)
           System.out.println("hhh");
        if (board[row + DIR[direction][0]][col + DIR[direction][1]] == null)
            return 0 ;
        else if (board[row + DIR[direction][0]][col + DIR[direction][1]].getOwner().isPlayerOne == lastPlayer) {
         //   if(lastPlayer) //לתקן
        //    currentPlayer = playerBlue;//לתקן
        //    else currentPlayer = playerRed;//ךתקן
         //   for (Position num : tempFlips)//לתקן
         //   {//לתקן
        //       board[num.row()][num.col()].setOwner(currentPlayer);//לתקן
        //    }//לתקן
            return count;
        }
        else
            return amount;
    }





    //לבדוק לגבי מהלכים שלא סוגרים מעגל
//private  int flip(int direction, int row, int col,int count)//, boolean flipper)
//{
//    switch (direction) {
//        case 1:
//        if (row - 1 > -0) {
//            if (board[row - 1][col] == null){
//                if (!tempFlips.isEmpty())
//                    tempFlips.remove(tempFlips.size() - 1);
//                return 0;
//            }
//            else if (board[row - 1][col].getOwner().isPlayerOne != lastPlayer) {
//                //Position temp(row-1,col);
//                tempFlips.add(new Position (row - 1,col));
//                return flip(direction, row - 1, col, count + 1);//, flipper);
//            }
//            else return count;
//        }
//        break;
//
//        case 2:
//            if (row - 1 >= 0 && col + 1 < BOARD_SIZE) {
//                if (board[row - 1][col + 1] == null) {
//                    if (!tempFlips.isEmpty())
//                        tempFlips.remove(tempFlips.size() - 1);
//                    return 0;
//                }
//                else if (board[row - 1][col + 1].getOwner().isPlayerOne != lastPlayer) {
//                    tempFlips.add(new Position (row - 1,col + 1));
//                    return flip(direction, row - 1, col + 1, count + 1);
//                }
//                else return count;
//            }
//            break;
//        case 3:
//            if (col + 1 < BOARD_SIZE) {
//                if (board[row][col + 1] == null) {
//                    if (!tempFlips.isEmpty())
//                        tempFlips.remove(tempFlips.size() - 1);
//                    return 0;
//                }
//                else if (board[row][col + 1].getOwner().isPlayerOne != lastPlayer) {
//                    tempFlips.add(new Position (row,col + 1));
//                    return flip(direction, row, col + 1, count + 1);
//                }
//                else return count;
//            }
//            break;
//        case 4:
//            if (row + 1 < BOARD_SIZE && col + 1 < BOARD_SIZE) {
//                if (board[row + 1][col + 1] == null) {
//                    if (!tempFlips.isEmpty())
//                        tempFlips.remove(tempFlips.size() - 1);
//                    return 0;
//                }
//                else if (board[row + 1][col + 1].getOwner().isPlayerOne != lastPlayer) {
//                //    positionsToFlip.add(new Position (row + 1,col + 1));
//                    return flip(direction, row + 1, col + 1, count + 1);
//                }
//                else return count;
//            }
//            break;
//        case 5:
//            if (row + 1 < BOARD_SIZE) {
//                if (board[row + 1][col] == null) {
//                    if (!tempFlips.isEmpty())
//                        tempFlips.remove(tempFlips.size() - 1);
//                    return 0;
//                }
//                else if (board[row + 1][col].getOwner().isPlayerOne != lastPlayer) {
//                 //   positionsToFlip.add(new Position (row + 1,col));
//                    return flip(direction, row + 1, col, count + 1);
//                }
//                else return count;
//            }
//            break;
//        case 6:
//            if (row + 1 < BOARD_SIZE && col - 1 >= 0) {
//                if (board[row + 1][col - 1] == null) {
//                    if (!tempFlips.isEmpty())
//                        tempFlips.remove(tempFlips.size() - 1);
//                    return 0;
//                }
//                else if (board[row + 1][col - 1].getOwner().isPlayerOne != lastPlayer) {
//                    tempFlips.add(new Position (row + 1,col - 1));
//                    return flip(direction, row + 1, col - 1, count + 1);
//                }
//                else return count;
//            }
//            break;
//        case 7:
//            if (col - 1 >= 0) {
//                if (board[row][col - 1] == null) {
//                    if (!tempFlips.isEmpty())
//                        tempFlips.remove(tempFlips.size() - 1);
//                    return 0;
//                }
//                else if (board[row][col - 1].getOwner().isPlayerOne != lastPlayer) {
//                    tempFlips.add(new Position (row,col - 1));
//                    return flip(direction, row, col - 1, count + 1);
//                }
//                else return count;
//            }
//            break;
//        case 8:
//            if (row - 1 >= 0 && col - 1 > 0) {
//                if (board[row - 1][col - 1] == null) {
//                    if (!tempFlips.isEmpty())
//                        tempFlips.remove(tempFlips.size() - 1);
//                    return 0;
//                }
//                else if (board[row - 1][col - 1].getOwner().isPlayerOne != lastPlayer) {
//                    tempFlips.add(new Position (row - 1,col - 1));
//                    return flip(direction, row - 1, col - 1, count + 1);
//                }
//                else return count;
//            }
//            break;
//    }
// return 0;
//}











//
//
//    private int flip1(int direction, int row, int col,int count) {
//    switch (direction) {
//        //up
//        case 1: {
//            if (row - 1 >= 0) {
//                if (board[row - 1][col] != null) {
//                    if (board[row - 1][col].getOwner().isPlayerOne != lastPlayer)
//                        return  flip(direction, row - 1, col, count+1);
//                }
//                else return -count;// Integer.MIN_VALUE;
//            }
//            break;
//        }
//        // up-right
//        case 2: {
//            if (row - 1 >= 0 && col + 1 < BOARD_SIZE) {
//                if (board[row - 1][col + 1] != null) {
//                    if (board[row - 1][col + 1].getOwner().isPlayerOne != lastPlayer)
//                        return  flip(direction, row - 1, col + 1, count+1);
//                } else return -count;// Integer.MIN_VALUE;
//            }
//            break;
//        }
//        //right
//        case 3: {
//            if (col + 1 < BOARD_SIZE) {
//                if (board[row][col + 1] != null) {
//                    if (board[row][col + 1].getOwner().isPlayerOne != lastPlayer)
//                        return  flip(direction, row, col + 1,count+1);
//                } else return -count;// Integer.MIN_VALUE;
//            }
//            break;
//        }
//        //down-right
//        case 4: {
//            if (row + 1 < BOARD_SIZE && col + 1 < BOARD_SIZE) {
//                if (board[row + 1][col + 1] != null) {
//                    if (board[row + 1][col + 1].getOwner().isPlayerOne != lastPlayer)
//                        return  flip(direction, row + 1, col + 1,count+1);
//                } else return -count;// Integer.MIN_VALUE;
//            }
//            break;
//        }
//        //down
//        case 5: {
//            if (row + 1 < BOARD_SIZE) {
//                if (board[row + 1][col] != null) {
//                    if (board[row + 1][col].getOwner().isPlayerOne != lastPlayer)
//                        return  flip(direction, row + 1, col,count+1);
//                } else return -count;// Integer.MIN_VALUE;
//            }
//            break;
//        }
//        //down-left
//        case 6: {
//            if (row + 1 < BOARD_SIZE && col - 1 >= 0) {
//                if (board[row + 1][col - 1] != null) {
//                    if (board[row + 1][col - 1].getOwner().isPlayerOne != lastPlayer)
//                        return  flip(direction, row + 1, col - 1,count+1);
//                } else return -count;// Integer.MIN_VALUE;
//            }
//            break;
//        }
//        //left
//        case 7: {
//            if (col - 1 >= 0) {
//                if(board[row][col - 1] != null) {
//                    if (board[row][col - 1].getOwner().isPlayerOne != lastPlayer)
//                        return  flip(direction, row, col - 1,count+1);
//                } else return -count;// Integer.MIN_VALUE;
//            }
//            break;
//        }
//        //up-left
//        case 8: {
//            if (row - 1 >= 0 && col - 1 > 0) {
//                if (board[row - 1][col - 1] != null) {
//                    if (board[row - 1][col - 1].getOwner().isPlayerOne != lastPlayer)
//                        return  flip(direction, row - 1, col - 1,count+1);
//                } else return -count;// Integer.MIN_VALUE;
//            }
//            break;
//        }
//
//    }
//
//
//
//
//
////
////    // up-right
////    case 2: {
////        if (row - 1 > 0 && col + 1 < BOARD_SIZE && board[row - 1][col + 1] != null && board[row - 1][col + 1].getOwner().isPlayerOne != lastPlayer)
////            return 1 + flip(direction, row - 1, col + 1);
////        break;
////    }
////    //right
////    case 3: {
////        if (col + 1 < BOARD_SIZE && board[row][col + 1] != null && board[row][col + 1].getOwner().isPlayerOne != lastPlayer)
////            return 1 + flip(direction, row, col + 1);
////        break;
////    }
////    //down-right
////    case 4: {
////        if (row + 1 < BOARD_SIZE && col + 1 < BOARD_SIZE && board[row + 1][col + 1] != null && board[row + 1][col + 1].getOwner().isPlayerOne != lastPlayer)
////            return 1 + flip(direction, row + 1, col + 1);
////        break;
////    }
////    //down
////    case 5: {
////        if (row + 1 < BOARD_SIZE && board[row + 1][col] != null && board[row + 1][col].getOwner().isPlayerOne != lastPlayer)
////            return 1 + flip(direction, row + 1, col);
////        break;
////    }
////    //down-left
////    case 6: {
////        if (row + 1 < BOARD_SIZE && col - 1 > 0 && board[row + 1][col - 1] != null && board[row + 1][col - 1].getOwner().isPlayerOne != lastPlayer)
////            return 1 + flip(direction, row + 1, col - 1);
////        break;
////    }
////    //left
////    case 7: {
////        if (col - 1 > 0 && board[row][col - 1] != null && board[row][col - 1].getOwner().isPlayerOne != lastPlayer)
////            return 1 + flip(direction, row, col - 1);
////        break;
////    }
////    //up-left
////    case 8: {
////        if (row - 1 > 0 && col - 1 > 0 && board[row - 1][col - 1] != null && board[row - 1][col - 1].getOwner().isPlayerOne != lastPlayer)
////            return 1 + flip(direction, row - 1, col - 1);
////        break;
////    }
////
////}
//
//    return count;
//}

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

        board[3][3] = new SimpleDisc(playerBlue) ;
        board[4][4] = new SimpleDisc(playerBlue) ;
        board[3][4] = new SimpleDisc(playerRed) ;
        board[4][3] = new SimpleDisc(playerRed);
        lastPlayer = true;
        System.out.println("reset");

    }

    @Override
    public void undoLastMove() {

    }
}


