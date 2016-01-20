

/**
 *
 * zad 4 ver 1
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class BetterOtelloHelper implements BetterOtelloHelperInterface
{
    private int[] move_hor = {-1, -1, -1, 0, 0, 1, 1, 1};
    private int[] move_ver = {-1, 0, 1, -1, 1, -1, 0, 1};
    private Disk originalBoard[][] = new Disk[8][8];
    private Set<List<myPoint>> treepath;
    private int score = 0;
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
    void setpaths(Set<List<myPoint>> paths)
    {
        treepath = null;
        treepath = new HashSet<>(paths);
    }
    void setScore(int i)
    {
        score = i;
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
    public Map<Integer, Set<List<Position>>> mapSort(Map<Integer, Set<List<Position>>> myMap) 
    {
        List<Integer> keys = new LinkedList<Integer>(myMap.keySet());
        Collections.sort(keys);
        Collections.reverse(keys);
        Map<Integer,Set<List<Position>>> sortedMap = new LinkedHashMap<Integer,Set<List<Position>>>();
        keys.stream().forEach((key) ->
        {
            sortedMap.put(key, myMap.get(key));
        });
        return sortedMap;
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
    setScore(points);
    return board;
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
    void setOriginalBoard(Disk[][] board)
    {
        for(int i = 0; i < 8 ; i++)
        {
           for(int j = 0; j<8;j++)
           {
               originalBoard[i][j] = board[i][j];
           }
        }
    }
    /// to test
    void test()
    {

    }
    
    List<myPoint> checkContinue(Disk[][] board, Disk playerDisk, myPoint poz)
    {
        Set<List<myPoint>> mySet = new HashSet<>();
        setOriginalBoard(board);
        board = effectMove(board, playerDisk,poz.position.getIndex1(), poz.position.getIndex2());
        if(poz.count != score);
        {
            poz.count = score;
        }
        Disk[][] tmpboard = prepareTmpBoard(board);
        List<myPoint> tmplist= new ArrayList<>();
        List<myPoint> Final= new LinkedList<>();
        Final.add(poz);
        Disk suggestPiece = (playerDisk == Disk.BLACK) ?  Disk.WHITE : Disk.BLACK;
        tmplist = findValidMove(board, suggestPiece);
        if(tmplist.isEmpty())
        {
           tmplist = findValidMove(tmpboard, playerDisk);
           if(!tmplist.isEmpty())
           {
               for(int i =0; i< tmplist.size(); i++)
               {
                   List<myPoint> tmplist2  = checkContinue(tmpboard, playerDisk, tmplist.get(i));
                   Final = new LinkedList<>();
                   Final.add(poz);
                   for(int j =0; j <tmplist2.size();j++)
                   {
                       Final.add(tmplist2.get(j));
                   }
                   mySet.add(new LinkedList<>(Final));
                   tmpboard = prepareTmpBoard(originalBoard);
               }
           }
        }
        if(mySet.isEmpty())
        {
            mySet.add(Final);
        }
        setpaths(mySet);
        return Final;
    }
    
    int calculatepoints(List<myPoint> tmplist)
    {
        int wynik =0;
        for(int i = 0; i < tmplist.size(); i++)
        {
            wynik+=tmplist.get(i).count;
        }
        return wynik;
    }
    ////////////////////////////////////////////////////////////////////////
    @Override
    public Map<Integer, Set<List<Position>>> analyze(Disk[][] board, Disk playerDisk) 
    {
        setOriginalBoard(board);
        ArrayList<myPoint> array = findValidMove(board, playerDisk);
        Disk oppPiece ;
        if(playerDisk == Disk.BLACK)
        {
            oppPiece = Disk.WHITE;
        }
        else
        {
            oppPiece = Disk.BLACK;
        }
            //setOriginalBoard(board);
        Map<Integer, Set<List<Position>>> myMap = new TreeMap<Integer, Set<List<Position>>>();
        for (int i=0; i < array.size(); ++i) 
        {
            array.get(i).count = findPoints(board, array.get(i).position.getIndex1(), array.get(i).position.getIndex2(), playerDisk);
        }
        for(int j =0; j< array.size();j++)
        {
            Disk myboard[][] = prepareTmpBoard(board);
            List<myPoint> list = new LinkedList<>();
            checkContinue(myboard, playerDisk, array.get(j));
            for(List<myPoint> tmp : treepath)
            {
                int tmppoints = calculatepoints(tmp);
                if(myMap.containsKey(tmppoints))
                {
                    List<Position> tmplist = new ArrayList<>();
                    for(int k =0; k < tmp.size();k++)
                    {
                        tmplist.add(tmp.get(k).position);
                    }
                    Set<List<Position>> tmpSet = myMap.get(tmppoints);
                    tmpSet.add(new ArrayList<Position>(tmplist));
                    myMap.replace(tmppoints, tmpSet);
                }
                else
                {
                    List<Position> tmplist = new ArrayList<>();
                    for(int k =0; k < tmp.size();k++)
                    {
                        tmplist.add(tmp.get(k).position);
                    }
                    Set<List<Position>> tmpset = new HashSet<>();
                    tmpset.add(tmplist);
                    myMap.put(tmppoints, tmpset);
                }
            }
            //myMap.put(j, v);
            list.isEmpty();
        }
        myMap = mapSort(myMap);
        return myMap;
    }
    
}
