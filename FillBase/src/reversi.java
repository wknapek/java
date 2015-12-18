
import java.util.ArrayList;
import java.util.Collections;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author raven
 */
class reversi implements OtelloHelperInterface
{
    private int[] MOVE_ROW = {-1, -1, -1, 0, 0, 1, 1, 1};
    private int[] MOVE_COL = {-1, 0, 1, -1, 1, -1, 0, 1};
    class retrev
    {
        boolean validate = false;
        int hints = 0;
    }
    class myPoint
    {
        Position position;
        int count;
        public myPoint(int icount, Position pos )
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
    @Override
    public Position[] analyze(Disk[][] board, Disk playerDisk)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    retrev isValidMove(Disk[][] board, Disk check, int row, int col) 
    {
        retrev ret = new retrev();
        Disk oppDisk = (check == Disk.BLACK) ? Disk.WHITE : Disk.BLACK;
        // check 8 directions
        for (int i = 0; i < 8; ++i) 
        {
            int curRow = row + MOVE_ROW[i];
            int curCol = col + MOVE_COL[i];
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

                curRow += MOVE_ROW[i];
                curCol += MOVE_COL[i];
            }
            if (ret.validate)
                break;
        }

        return ret;
   }
    
    ArrayList<myPoint> findValidMove(Disk[][] board, Disk playerDisk) 
    {
        Disk suggestPiece = (playerDisk == Disk.BLACK) ? Disk.BLACK : Disk.WHITE;
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
                    myPoint tmp = new myPoint(isValid.hints, new Position(i, j));
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

    /// to test
    void test()
    {
        Disk[][] testTab = {{null      ,null      ,null      ,null      ,null      ,null      ,null       ,null}, //0
                            {null      ,null      ,null      ,null      ,null      ,null      ,null       ,null}, //1
                            {null      ,null      ,null      ,null      ,null      ,Disk.BLACK,null       ,null}, //2
                            {null      ,null      ,null      ,Disk.BLACK,Disk.BLACK,null      ,null       ,null}, //3
                            {null,      Disk.WHITE,Disk.WHITE,Disk.WHITE,Disk.WHITE,null      ,null       ,null}, //4
                            {null,      null,      null,      null      ,Disk.WHITE,null      ,null       ,null}, //5
                            {null,      null,      null,      null      ,null      ,null      ,null       ,null}, //6
                            {null,      null,      null,      null      ,null      ,null      ,null       ,null}, //7
                                                                                                               };
                          //  1          2          3          4          5          6          7           8
       ArrayList<myPoint> test = findValidMove(testTab, OtelloHelperInterface.Disk.BLACK);
       test = bubbleSort(test);
    }
}
