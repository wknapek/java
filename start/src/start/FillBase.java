package start;

import java.util.ArrayList;
import java.util.List;


public class FillBase {
	/**
	 * Metoda wypelnia zadanym kolorem (color) obrazek (image). Obrazek reprezentowany jest
	 * przez dwowymiarowa tablice. Wypelnienie zaczyna sie 
	 * w pozycji image[ firstIndex ][ secondIndex ] i obejmuje caly obszar obrazka, ktory
	 * jest osiagalny przemieszczajac sie z pozycji startowej do sasiednich pikseli
	 * wg. ponizszych zasad:
	 * <ul>
	 * <li>Kazdy piksel wewnatrz obrazka sasiaduje z 8-mioma sasiednimi pikselami. Liczba sasiadow
	 * dla pikseli brzegowych jest odpowienio mniejsza.</li>
	 * <li>Z aktualnie analizowanej pozycji wolno przemieszczac sie wylacznie do tych sasiednich lokalizacji, 
	 * ktore oznaczone sa w tablicy sasiadow jako <code>true</code>. <code>false</code> oznacza brak
	 * mozliwosci ruchu w kierunku danego sasiada.</li>
	 * 
	 * <li>Tablica sasiadow (neighbours) jest zawsze rozmiaru 3x3. Informacja o tym czy
	 * z danej pozycji mozna przesunac sie o w danym kierunku znajduje sie na stosownej pozycji
	 * tej tablicy. I tak jesli analizujemy mozliwosc ruchu z polozenia [x][y], to obowiazuje nastepujace
	 * powiazenie polozen w tablicy sasiadow i mozliwosci przesuniec.
	 * <table style="border:1px solid blue" border="1">
	 * <tr><td></td><td>neighbour[0][]</td><td>neighbour[1][]</td><td>neighbour[2][]</td></tr>
	 * <tr><td>neighbour[][0]</td><td>[ x - 1 ][ y + 1 ]</td><td>[ x ][ y + 1 ]</td><td>[ x + 1 ][ y + 1 ]</td></tr> 
	 * <tr><td>neighbour[][1]</td><td>[ x - 1 ][ y ]</td><td>[ x ][ y  ]</td><td>[ x + 1 ][ y ]</td></tr> 
	 * <tr><td>neighbour[][2]</td><td>[ x - 1 ][ y - 1 ]</td><td>[ x ][ y - 1 ]</td><td>[ x + 1 ][ y - 1 ]</td></tr> 
	 * </table>
	 * </li>
	 * 
	 * <li>Nie wolno nadpisywac kolorem (color) i tym samym przekraczac tych elementow obrazka, ktore
	 * maja kolor wymieniony w tablicy <code>colors</code>. Uwaga: color moze znajdowac sie w tablicy colors.</li>   
	 * </ul>
	 * 
	 * @param image obrazek do wypelnienia kolorem
	 * @param neighbours informacja, ktore piksele sa traktowane jako sasiednie
	 * @param colors tablica chronionych kolorow
	 * @param color kolor, ktorym wypelniamy obrazek
	 * @param firstIndex pierwszy indeks pozycji startowej
	 * @param secondIndex drugi indeks pozycji startwej
	 */
	public void fill( int[][] image, boolean [][] neighbours, int[] colors, int color, int firstIndex, int secondIndex ) {
		// tak, to nic nie robi...
	}
}



class FillBaseImplementation extends FillBase
{
    public class point
    {
        int x;
        int y;
        public point()
        {
            x=1;
            y=1;
        }
        public point(int ix, int iy)
        {
            x=ix;
            y=iy;
        }
    }
    
    public class host
    {
        int[][] image;
        int mycolor = -1;
        List<point> points = new ArrayList<>();
    }
            
    public void fill( int[][] image, boolean [][] neighbours, int[] colors, int color, int firstIndex, int secondIndex ) 
    {
        int maxCol = image.length;
        int maxRow = image[maxCol - 1].length;
        int[] mycolor = new int[colors.length + 1];
                host tmp = new host();
        if(colors.length > 0 && !checkColor(colors, color))
        {
            for(int i =0 ;i<colors.length;i++)
            {
                mycolor[i]=colors[i];
            }
            mycolor[colors.length]=color;
        }
        else
        {
            mycolor=colors;
        }
        int steps = (maxCol + maxRow) / 3 + 1;
        //==============================================
        int colorinit = image[firstIndex][secondIndex];
        if(!checkColor(colors,colorinit))
        {
            image[firstIndex][secondIndex] = color;
            tmp.points.add(new point(firstIndex, secondIndex));
        }
        //image = initialize(image, neighbours, colors, color, firstIndex, secondIndex);
        //======initialize=============================
        tmp.image = image;
        tmp.mycolor = color;
        //while(!tmp.change)
        {
            for(int i=0;i<maxRow;i++)
            {
                for(int j=0;j<maxCol;j++)
                {
                    if(image[i][j]==color)
                    {
                        tmp = initialize(tmp, neighbours, mycolor, color, i, j);
                    }
                }
            }
            //steps--;
            for(int i=maxRow-1;i>-1;i--)
            {
                for(int j=maxCol-1;j>-1;j--)
                {
                    if(image[i][j]==color)
                    {
                        tmp = initialize(tmp, neighbours, mycolor, color, i, j);
                    }
                }
            }
        }
    } 
    private boolean checkColor(int[] colors,int color)
    {
        if(colors.length == 0)
        {
            return false;
        }
        for(int i=0;i<colors.length;i++)
        {
            if(colors[i]== color)
            {
                return true;
            }
        }
        return false;
    }

    private boolean checkmove(boolean [][] neighbours, int x, int y)
    {
        point ret = new point();
        /*if(neighbours[0][0]==true)
        {
            ret.x = 1;
            ret.y = 1;
        }
        else if(neighbours[0][1]==true)
        {
            ret.y = 1;
        }
        else if(neighbours[0][2]==true)
        {
            ret.x = 1;
            ret.y = 1;
        }
        else if(neighbours[1][0]==true)
        {
            ret.x = 1;
        }
        else if(neighbours[1][2]==true)
        {
            ret.x = 1;
        }
        else if(neighbours[2][0]==true)
        {
            ret.x = 1;
            ret.y = 1;
        }  
        else if(neighbours[2][1]==true)
        {
            ret.y = 1;
        }
        else if(neighbours[2][1]==true)
        {
            ret.y = 1;
            ret.x = 1;
        }*/
        if(neighbours[x][y]==true)
        {
            return true;
        }
        return false;
    }

    private host initialize( host image, boolean [][] neighbours, int[] colors, int color, int firstIndex, int secondIndex )
    {
        int getColor;
        int maxX = image.image.length - 1;
        int maxY = image.image[maxX].length - 1;
        point pkt = new point();
        int pickcolor = image.image[firstIndex][secondIndex];
        if (!findonlist(image.points, firstIndex, secondIndex) && checkColor(colors, pickcolor))
        {
            return image;
        }
        
        getColor = image.image[firstIndex][secondIndex];
        // x y
        if(!checkColor(colors, getColor) && neighbours[2][2] == true)
        {
            image.image[firstIndex][secondIndex] = color;
            image.points.add(new point(firstIndex,secondIndex));
        }
        if( firstIndex > 0 && secondIndex < maxY)
        {
            getColor = image.image[firstIndex-pkt.x][secondIndex+pkt.y];
            // x-1 y+1
            if(!checkColor(colors, getColor) && checkmove(neighbours, 1-pkt.x, 1+pkt.y))
            {
                image.image[firstIndex-pkt.x][secondIndex+pkt.y] = color;
                image.points.add(new point(firstIndex-pkt.x,secondIndex+pkt.y));
            }
        }
        if(secondIndex < maxY)
        {
            getColor = image.image[firstIndex][secondIndex+pkt.y];
            // x y+1
            if(!checkColor(colors, getColor) && checkmove(neighbours, 1, 1+pkt.y))
            {
                image.image[firstIndex][secondIndex+pkt.y] = color;
                image.points.add(new point(firstIndex,secondIndex+pkt.y));
            }
        }
        if(secondIndex < maxY && firstIndex < maxX)
        {
            getColor = image.image[firstIndex+pkt.x][secondIndex+pkt.y];
            // x+1 y+1
            if(!checkColor(colors, getColor) && checkmove(neighbours, 1+pkt.x, 1+pkt.y))
            {
                image.image[firstIndex+pkt.x][secondIndex+pkt.y] = color;
                image.points.add(new point(firstIndex+pkt.x,secondIndex+pkt.y));
            }
        }
        if(firstIndex > 0)
        {
            getColor = image.image[firstIndex-pkt.x][secondIndex];
            // x-1 y
            if(!checkColor(colors, getColor) && checkmove(neighbours, 1 -pkt.x, 1))
            {
                image.image[firstIndex-pkt.x][secondIndex] = color;
                image.points.add(new point(firstIndex-pkt.x,secondIndex));
            }
        }
        if(firstIndex < maxX )
        {
            getColor = image.image[firstIndex+pkt.x][secondIndex];
            // x+1 y
            if(!checkColor(colors, getColor) && checkmove(neighbours, 1+pkt.x, 1))
            {
                image.image[firstIndex+pkt.x][secondIndex] = color;
                image.points.add(new point(firstIndex+pkt.x,secondIndex));
            }
        }
        if(firstIndex > 0 && secondIndex > 0)
        {
            getColor = image.image[firstIndex-pkt.x][secondIndex-pkt.y];
            // x-1 y-1
            if(!checkColor(colors, getColor) && checkmove(neighbours, 1-pkt.x, 1-pkt.y))
            {
                image.image[firstIndex-pkt.x][secondIndex-pkt.y] = color;
                image.points.add(new point(firstIndex-pkt.x,secondIndex-pkt.y));
            }
        }
        if (secondIndex > 0)
        {
            getColor = image.image[firstIndex][secondIndex-pkt.y];
            // x y-1
            if(!checkColor(colors, getColor) && checkmove(neighbours, 1, 1-pkt.y))
            {
                image.image[firstIndex][secondIndex-pkt.y] = color;
                image.points.add(new point(firstIndex,secondIndex-pkt.y));
            }
        }
        if(firstIndex < maxX && secondIndex > 0)
        {
            // x+1 y-1
            getColor = image.image[firstIndex+pkt.x][secondIndex-pkt.y];
            if(!checkColor(colors, getColor) && checkmove(neighbours, 1+pkt.x, 1-pkt.y))
            {
                image.image[firstIndex+pkt.x][secondIndex-pkt.y] = color;
                image.points.add(new point(firstIndex+pkt.x,secondIndex-pkt.y));
            }
        }
        return image;
    }
    
    private boolean findonlist(List<point> list,int x, int y)
    {
        for(int i=0 ; i<list.size();i++)
        {
            if(list.get(i).x == x && list.get(i).y == y)
            {
                return true;
            }
        }
        return false;
    }
            
}