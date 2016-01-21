import java.util.ArrayList;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author raven
 */
class OtelloHelper implements OtelloHelperInterface
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
    @Override
    public Position[] analyze(Disk[][] board, Disk playerDisk)
    {
        //Position[] list;
        //list = new Position[];
        Disk oppDisk = (playerDisk == Disk.BLACK) ? Disk.WHITE : Disk.BLACK;
        ArrayList<myPoint> array = findValidMove(board, playerDisk);
        int listsize = array.size();
        Position[] list = new Position[listsize];
        for (int i=0; i < array.size(); ++i) 
        {
            array.get(i).count = findPoints(board, array.get(i).position.getIndex1(), array.get(i).position.getIndex2(), playerDisk);
        }
        bubbleSort(array);
        for(int i =0; i < listsize; i++)
        {
            list[i] = array.get(i).position;
        }
        
        return list;
    }
    
    retrev isValidMove(Disk[][] board, Disk check, int row, int col) 
    {
        retrev ret = new retrev();
        Disk oppDisk = (check == Disk.BLACK) ? Disk.WHITE : Disk.BLACK;
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
                }
                else if ((board[curRow][curCol] == check) && hasOppPieceBetween)
                {
                    if(board[row][col] == null)
                    {
                        ret.validate = true;
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
                if (isValid.validate )
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
    
    int findPoints(Disk[][] board ,int row, int col, Disk player)
    {
        Disk oppDisk = (player == Disk.BLACK) ? Disk.WHITE : Disk.BLACK;
        int points =0;
        board[row][col] = player;
        points =  findHori(board, row, col, player);
        points += findVert(board, row, col, player);
        points += findDiag(board, row, col, player);
        board[row][col] = null;
        return points;
    }
    
    int findVert(Disk[][] board, int row, int col, Disk player)
    {
        Disk oppDisk = (player == Disk.BLACK) ? Disk.WHITE : Disk.BLACK;
        int points = 0;
        int nullcount = 0; 
        boolean border = false;
        for(int i = row; i<8; i++)
        {
            if(board[i][col] == oppDisk)
            {
                points++;
            }
            if(board[i][col] == null)
            {
                nullcount++;
            }
            if(board[i][col] == player)
            {
                if(i!=row)
                {
                    border = true;
                    break;
                }
            }
        }
        for(int i = row; i>-1; i--)
        {
            if(board[i][col] == oppDisk)
            {
                points++;
            }
            if(board[i][col] == null)
            {
                nullcount++;
            }
            if(board[i][col] == player)
            {
                if(i!=row)
                {
                    border = true;
                    break;
                }
            }
        }
        if(border)
        {
            return points;
        }
        else
        {
            return 0;
        }
    }
    
    int findHori(Disk[][] board, int row, int col, Disk player)
    {
        Disk oppDisk = (player == Disk.BLACK) ? Disk.WHITE : Disk.BLACK;
        int points =0;
        int nullcount = 0;
        boolean border =false;
        for(int i = col; i<8; i++)
        {
            if(board[row][i] == oppDisk)
            {
                points++;
            }
            if(board[row][i] == null)
            {
                nullcount++;
            }
            if(board[row][i]  == player)
            {
                if(i != col)
                {
                    border = true;
                    break;
                }
            }
        }
        for(int i = col; i>-1; i--)
        {
            if(board[row][i]  == oppDisk)
            {
                points++;
            }
            if(board[row][i]  == null)
            {
                nullcount++;
            }
            if(board[row][i] == player)
            {
                if(i != col)
                {
                    border =  true;
                    break;
                }
            }
        }
        if(border)
        {
            return points;
        }
        else
        {
            return 0;
        }
    }
    
    int findDiag(Disk[][] board, int row, int col, Disk player)
    {
        Disk oppDisk = (player == Disk.BLACK) ? Disk.WHITE : Disk.BLACK;
        int points =0;
        int tmpPoints = 0;
        int nullcount =0;
        boolean border = false;
        for(int i = 0; i < 8; i++)
        {
            if(row + i < 8 && col + i < 8)
            {
                if(board[row + i][col + i] == oppDisk)
                {
                    points++;
                }
                if(board[row + i][col + i] == player)
                {
                    
                    if(i!=0)
                    {
                        border = true;
                        break;
                    }
                }
                if(board[row + i][col + i] == null)
                {
                    nullcount++;
                }
            }
            else
            {
                break;
            }
        }
        if(!border && nullcount > 0)
        {
            border = false;
            points = 0;
            nullcount =0;
        }
        else
        {
            border = false;
            if(nullcount==0)
            {
                tmpPoints += points; 
            }
            points = 0;
        }
        for(int i = 0; i < 8; i++)
        {
            if(row - i > -1 && col - i > -1)
            {
                if(board[row - i][col - i] == oppDisk)
                {
                    points++;
                }
                if(board[row - i][col - i] == player)
                {
                    if(i!=0)
                    {
                        border = true;
                        break;
                    }
                }
                if(board[row - i][col - i] == null)
                {
                    nullcount++;
                }
            }
            else
            {
                break;
            }
        }
        if(!border && nullcount > 0)
        {
            border = false;
            points = 0;
            nullcount =0;
        }
        else
        {
            border = false;
            if(nullcount==0)
            {
                tmpPoints += points; 
            }
            points = 0;
        }
        for(int i = 0; i < 8; i++)
        {
            if(row - i > -1 && col + i < 8)
            {
                if(board[row - i][col + i] == oppDisk)
                {
                    points++;
                }
                if(board[row - i][col + i] == player)
                {
                    if(i!=0)
                    {
                        border = true;
                        break;
                    }
                }
                if(board[row - i][col + i] == null)
                {
                    nullcount++;
                }
            }
            else
            {
                break;
            }
        }
        if(!border && nullcount > 0)
        {
            border = false;
            points = 0;
            nullcount =0;
        }
        else
        {
            border = false;
            if(nullcount==0)
            {
                tmpPoints += points; 
            }
            points = 0;
        }
        for(int i = 0; i < 8; i++)
        {
            if(row + i < 8 && col - i > -1)
            {
                if(board[row + i][col - i] == oppDisk)
                {
                    points++;
                }
                if(board[row + i][col - i] == player)
                {
                    if(i!=0)
                    {
                        border = true;
                        break;
                    }
                }
                if(board[row + i][col - i] == null)
                {
                    nullcount++;
                }
            }
            else
            {
                break;
            }
        }
        if(!border)
        {
            border = false;
            points = 0;
            nullcount =0;
        }
        else
        {
            border = false;
            if(nullcount==0)
            {
                tmpPoints += points; 
            }
            points = 0;
        }
        if(tmpPoints != 0)
        {
            tmpPoints += points; 
            return tmpPoints;
        }
        else
        {
            return 0;
        }
    }
    /// to test
    void test()
    {

    }
}
