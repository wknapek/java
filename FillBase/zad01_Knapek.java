
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
            x=0;
            y=0;
        }
    }
    public void fill( int[][] image, boolean [][] neighbours, int[] colors, int color, int firstIndex, int secondIndex ) 
    {
        int maxCol = image.length -1;
        int maxRow = image[maxCol].length - 1;
        int getColor;
        int steps = (maxCol + maxRow) / 3 + 1;
        //==============================================
        image = initialize(image, neighbours, colors, color, firstIndex, secondIndex);
        //======initialize=============================
        while(steps >0 )
        {
            for(int i=1;i<maxRow;i++)
            {
                for(int j=1;j<maxCol;j++)
                {
                    if(image[i][j]==color)
                    {
                        image = initialize(image, neighbours, colors, color, firstIndex, secondIndex);
                    }
                }
            }
            steps--;
        }
    } 
    private boolean checkColor(int[] colors,int color)
    {
        for(int i=0;i<colors.length;i++)
        {
            if(colors[i]== color)
            {
                return true;
            }
        }
        return false;
    }

    private point checkmove(boolean [][] neighbours)
    {
        point ret = new point();
        if(neighbours[0][0]==true)
        {
            ret.x = -1;
            ret.y = -1;
        }
        else if(neighbours[0][1]==true)
        {
            ret.y = -1;
        }
        else if(neighbours[0][2]==true)
        {
            ret.x = 1;
            ret.y = -1;
        }
        else if(neighbours[1][0]==true)
        {
            ret.x = -1;
        }
        else if(neighbours[1][2]==true)
        {
            ret.x = 1;
        }
        else if(neighbours[2][0]==true)
        {
            ret.x = -1;
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
        } 
        return ret;
    }

    private int[][] initialize( int[][] image, boolean [][] neighbours, int[] colors, int color, int firstIndex, int secondIndex )
    {
        int getColor;
        point pkt = checkmove(neighbours);
        getColor = image[firstIndex][secondIndex];
        if(!checkColor(colors, getColor))
        {
            image[firstIndex][secondIndex] = color;
        }
        getColor = image[firstIndex-pkt.x][secondIndex+pkt.y];
        if(!checkColor(colors, getColor))
        {
            image[firstIndex-pkt.x][secondIndex+pkt.y] = color;
        }
        getColor = image[firstIndex][secondIndex+pkt.y];
        if(!checkColor(colors, getColor))
        {
            image[firstIndex][secondIndex+pkt.y] = color;
        }
        getColor = image[firstIndex+pkt.x][secondIndex+pkt.y];
        if(!checkColor(colors, getColor))
        {
            image[firstIndex+pkt.x][secondIndex+pkt.y] = color;
        }
        getColor = image[firstIndex-pkt.x][secondIndex];
        if(!checkColor(colors, getColor))
        {
            image[firstIndex-pkt.x][secondIndex] = color;
        }
        getColor = image[firstIndex+pkt.x][secondIndex];
        if(!checkColor(colors, getColor))
        {
            image[firstIndex+pkt.x][secondIndex] = color;
        }
        getColor = image[firstIndex-pkt.x][secondIndex+pkt.y];
        if(!checkColor(colors, getColor))
        {
            image[firstIndex-pkt.x][secondIndex+pkt.y] = color;
        }
        getColor = image[firstIndex][secondIndex+pkt.y];
        if(!checkColor(colors, getColor))
        {
            image[firstIndex][secondIndex+pkt.y] = color;
        }
        getColor = image[firstIndex+pkt.x][secondIndex+pkt.y];
        if(!checkColor(colors, getColor))
        {
            image[firstIndex+pkt.x][secondIndex+pkt.y] = color;
        }
        return image;
    }
}