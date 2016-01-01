
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dom
 */
class BetterOtelloHelper implements BetterOtelloHelperInterface
{
    private int[] move_hor = {-1, -1, -1, 0, 0, 1, 1, 1};
    private int[] move_ver = {-1, 0, 1, -1, 1, -1, 0, 1};
    class retrev
    {
        boolean validate = false;
        int hints = 0;
    }
    class myPoint
    {
        OtelloHelperInterface.Position position;
        int count;
        public myPoint(int icount, OtelloHelperInterface.Position pos )
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

    ////////////////////////////////////////////////////
    
    retrev isValidMove(OtelloHelperInterface.Disk[][] board, OtelloHelperInterface.Disk check, int row, int col) 
    {
        retrev ret = new retrev();
        OtelloHelperInterface.Disk oppDisk = (check == OtelloHelperInterface.Disk.BLACK) ? OtelloHelperInterface.Disk.WHITE : OtelloHelperInterface.Disk.BLACK;
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
    
    ArrayList<myPoint> findValidMove(OtelloHelperInterface.Disk[][] board, OtelloHelperInterface.Disk playerDisk) 
    {
        OtelloHelperInterface.Disk suggestPiece = (playerDisk == OtelloHelperInterface.Disk.BLACK) ? OtelloHelperInterface.Disk.BLACK : OtelloHelperInterface.Disk.WHITE;
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
                    myPoint tmp = new myPoint(isValid.hints, new OtelloHelperInterface.Position(i, j));
                    moveMap.add(tmp);
                    System.out.println(isValid.hints);
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
    
    public Disk[][] effectMove(Disk[][] board, Disk piece, int row, int col) 
    {
    board[row][col] = piece;


    // check 8 directions
    for (int i = 0; i < 8; ++i) {
            int curRow = row + move_hor[i];
            int curCol = col + move_ver[i];
            boolean hasOppPieceBetween = false;
            while (curRow >=0 && curRow < 8 && curCol >= 0 && curCol < 8) {
                    // if empty square, break
                    if (board[curRow][curCol] == null)
                            break;

                    if (board[curRow][curCol] != piece)
                            hasOppPieceBetween = true;

                    if ((board[curRow][curCol] == piece) && hasOppPieceBetween)
                    {
                            int effectPieceRow = row + move_hor[i];
                            int effectPieceCol = col + move_ver[i];
                            while (effectPieceRow != curRow || effectPieceCol != curCol)
                            {
                                    board[effectPieceRow][effectPieceCol] = piece;
                                    effectPieceRow += move_hor[i];
                                    effectPieceCol += move_ver[i];
                            }

                            break;
                    }

                    curRow += move_hor[i];
                    curCol += move_ver[i];
            }
    }

    return board;
    }

    /// to test
    void test()
    {

    }
    ////////////////////////////////////////////////////////////////////////
    @Override
    public Map<Integer, Set<List<Position>>> analyze(Disk[][] board, Disk playerDisk) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
