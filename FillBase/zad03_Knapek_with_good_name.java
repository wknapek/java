
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dom
 */
class ReversiBoard implements ReversiBoardInterface
{
    private int[] move_hor = {-1, -1, -1, 0, 0, 1, 1, 1};
    private int[] move_ver = {-1, 0, 1, -1, 1, -1, 0, 1};
    private Disk myBoard[][] = new Disk[8][8];
    private Disk myNextPlayer;
    class retrev
    {
        boolean validate = false;
        int hints = 0;
    }
    class myPoint
    {
        ReversiBoardInterface.Position position;
        int count;
        public myPoint(int icount, ReversiBoardInterface.Position pos )
        {
            count = icount;
            position = pos;
        }
        public int getCount()
        {
            return count;
        }
        public myPoint()
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
        retrev isValidMove(ReversiBoardInterface.Disk[][] board, ReversiBoardInterface.Disk check, int row, int col) 
    {
        retrev ret = new retrev();
        ReversiBoardInterface.Disk oppDisk = (check == ReversiBoardInterface.Disk.BLACK) ? ReversiBoardInterface.Disk.WHITE : ReversiBoardInterface.Disk.BLACK;
        // check 8 directions
        for (int i = 0; i < 8; ++i) 
        {
            int curRow = row + move_hor[i];
            int curCol = col + move_ver[i];
            boolean hasOppPieceBetween = false;
            int tocheck = 0;
            while (curRow >=0 && curRow < 8 && curCol >= 0 && curCol < 8) 
            {

                if (board[curRow][curCol] == oppDisk)
                {
                    hasOppPieceBetween = true;
                    tocheck++;
                }
                else if ((board[curRow][curCol] == check) && hasOppPieceBetween)
                {
                    if(board[row][col] == null)
                    {
                        ret.validate = true;
                        ret.hints = tocheck;
                        break;
                    }
                }
                else
                    break;

                curRow += move_hor[i];
                curCol += move_ver[i];
            }
            if (ret.validate)
                break;
        }

        return ret;
   }

    ArrayList<myPoint> findValidMove(ReversiBoardInterface.Disk[][] board, ReversiBoardInterface.Disk playerDisk) 
    {
        boolean test =false;
        int count = 0;

        ArrayList<myPoint> moveMap = new ArrayList<>();
        for (int i = 0; i < 8; ++i)
        {
            for (int j = 0; j < 8; ++j) 
            {
                retrev isValid = isValidMove(board,playerDisk, i, j);
                if (isValid.validate)
                {
                    myPoint tmp = new myPoint(isValid.hints, new ReversiBoardInterface.Position(i, j));
                    moveMap.add(tmp);
                }
            }
        }
       return moveMap;
    }
    
    public ArrayList<myPoint> bubbleSort(ArrayList<myPoint> a) 
    {
        for (int i = 0; i < a.size()-1; i++) {
            for (int j = 1; j < a.size() - i; j++) 
            {
                int tmp1 = a.get(j-1).count;
                int tmp2 = a.get(j).count;
                if ( tmp1 < tmp2) {
                    myPoint temp = new myPoint(a.get(j - 1).count,a.get(j - 1).position);
                    //temp = a.get(j - 1);
                    a.get(j -1 ).count = a.get(j).count;
                    a.get(j -1 ).position = a.get(j).position;
                    int tmpint = temp.count;
                    a.get(j).count = tmpint;
                    a.get(j).position = temp.position;
                }
            }
        }
        return a;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void setGameState(Disk[][] board, Disk nextPlayer) 
    {
        for (int i = 0; i < 8; ++i)
        {
            for (int j = 0; j < 8; ++j) 
            {
                myBoard[i][j] = board[i][j];
            }
        }
        myNextPlayer = nextPlayer;
    }

    @Override
    public boolean canWeContinueTheGame() 
    {
        ArrayList<myPoint> black = new ArrayList<>();
        ArrayList<myPoint> white = new ArrayList<>();
        black = findValidMove(myBoard, Disk.BLACK);
        white = findValidMove(myBoard, Disk.WHITE);
        if(black.isEmpty() && white.isEmpty())
        {
            try 
            {
                throw new CanNotContinue();
            } 
            catch (CanNotContinue ex) 
            {
                return false;
            }
        }
        else
        {
            return true;
        }
    }

    @Override
    public Disk nextPlayer() throws CanNotContinue 
    {
        Disk ret = null;
        if(canWeContinueTheGame())
        {
            if(myNextPlayer == Disk.BLACK)
            {
                ret = Disk.WHITE;
            }
            else
            {
                ret = Disk.BLACK;
            }
        }
        if(ret == null)
        {
            throw new CanNotContinue();
        }
        else
        {
            return ret;
        }
    }

    @Override
    public int move(Position pos) throws IllegalMove 
    {
        int ret =0;
        retrev validMove;
        try {
            validMove = isValidMove(myBoard, nextPlayer(), pos.getIndex1(), pos.getIndex2());
            if(!validMove.validate)
                {
                    throw new IllegalMove();
                }
        } 
        catch (CanNotContinue ex) 
        {
        }
        try {
            ArrayList<myPoint> array = findValidMove(myBoard, nextPlayer());
            for(int i =0; i < array.size(); i++)
            {
                if(array.get(i).position.getIndex1() == pos.getIndex1() && array.get(i).position.getIndex2() == pos.getIndex2())
                {
                    ret = array.get(i).count;
                }
            }
        } catch (CanNotContinue ex) 
        {
            throw new IllegalMove();
        }
        if(ret == 0)
        {
            throw new IllegalMove();
        }
        return ret;
        
    }

    @Override
    public Disk[][] getBoard() 
    {
        return myBoard;
    }

    @Override
    public int getResult(Disk player) 
    {
       int ret = 0;
       for (int i = 0; i < 8; ++i)
        {
            for (int j = 0; j < 8; ++j) 
            {
                if(myBoard[i][j] == player)
                {
                    ret++;
                }
            }
        }
       return ret;
    }

    ////////////////////////////////////////////////////
}
