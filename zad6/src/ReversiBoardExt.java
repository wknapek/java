import java.util.ArrayList;
import java.util.List;

/*
 * zag 6 ver1
 */
class ReversiBoardExt implements ReversiBoardInterfaceExt
{
    private int[] move_hor = {-1, -1, -1, 0, 0, 1, 1, 1};
    private int[] move_ver = {-1, 0, 1, -1, 1, -1, 0, 1};
    private Disk myBoard[][] = new Disk[8][8];
    private Disk myTmpBoard[][] = new Disk[8][8];
    private Disk myNextPlayer;
    private int pointUndo = 0;
    private int pointReDo = 0;
    boolean movewasexexute;
    private List<Disk[][]> history;
    public ReversiBoardExt()
    {
        history = new ArrayList<Disk[][]>();
    }


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

    public Disk[][] effectMove(Disk[][] board, Disk piece, int row, int col) 
    {
    board[row][col] = piece;
    int points =0 ;
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
                                    points++;
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
        movewasexexute = false;
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
            //if(movewasexexute)
            //{
                /*if(myNextPlayer == Disk.BLACK)
                {
                    ret = Disk.WHITE;
                }
                else
                {
                    ret = Disk.BLACK;
                }
            }
            else
            {*/
                ret = myNextPlayer;
            //}
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

    Disk[][] prepareTmpBoard(Disk[][] board)
    {
        Disk boardtmp[][] = new Disk[8][8]; 
        for(int i = 0; i < 8 ; i++)
        {
           for(int j = 0; j<8;j++)
           {
               boardtmp[i][j] = board[i][j];
           }
        }
        return boardtmp;
    }
    
    @Override
    public int move(Position pos) throws IllegalMove 
    {
        int ret =0;
        int point_before = 0;
        int point_after =0;
        int allin =0;
        myTmpBoard = prepareTmpBoard(myBoard);
        Disk player = null;
        try
        {
            player = nextPlayer();
        }
        catch (CanNotContinue ex)
        {
            
        }       
        retrev validMove = null;
        point_before = getResult(player);
        Disk oppDisk = (player == Disk.BLACK) ? Disk.WHITE : Disk.BLACK;
        validMove = isValidMove(myBoard, player, pos.getIndex1(), pos.getIndex2());
        if(!validMove.validate)
        {
            throw new IllegalMove();
        }
        myTmpBoard = effectMove(myTmpBoard, player, pos.getIndex1(), pos.getIndex2());
        point_after = getTmpResult(player);
        allin = getTmpResult(oppDisk);
        movewasexexute = true;
        List<myPoint> mylist = findValidMove(myTmpBoard, oppDisk);
        if(mylist.isEmpty())
        {
            setGameState(myTmpBoard, player);
        }
        else
        {
            setGameState(myTmpBoard, oppDisk);
        }
        if(allin == 0)
        {
             ret = point_after - point_before -1;   
        }
        else
        {
        ret = point_after - point_before - 1;
        }
        history.add(myTmpBoard);
        pointUndo = history.size() - 1;
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

    public int getTmpResult(Disk player) 
    {
       int ret = 0;
       for (int i = 0; i < 8; ++i)
        {
            for (int j = 0; j < 8; ++j) 
            {
                if(myTmpBoard[i][j] == player)
                {
                    ret++;
                }
            }
        }
       return ret;
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
    ////////////////////////////////////////////////////
    @Override
    public void undo() throws IllegalOperationException
    {
        if(pointUndo > history.size() -1)
        {
            throw new IllegalOperationException();
        }
        pointUndo--;
        myTmpBoard = history.get(pointUndo);
        myBoard = myTmpBoard;
    }

    @Override
    public void redo() throws IllegalOperationException
    {
        if(pointReDo < 0)
        {
            throw new IllegalOperationException();
        }
        pointReDo = pointUndo + 1;
        myTmpBoard = history.get(pointReDo);
        myBoard = myTmpBoard;
    }
}
