/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author raven
 */
public interface OtelloHelperInterface 
{
	/**
	 * Typ wyliczeniowy reprezentujacy pionka do gry w Otello.
	 * 
	 */
	enum Disk 
    {
		BLACK, WHITE;
	}
	/**
	 * Klasa reprezentuje pozycje w dwu-wymiarowej tablicy uzywanej do zapisu
	 * stanu do gry. Uzycie przechowywanych w obiektach indeksow jest zgodne z ich nazwa
	 * czyli para indeksow index1,index2 wskazuje na pozycje w tablicy zapisana
	 * nastepujaco: tablica[index1][index2]
	 */
	class Position 
    {
		private int index1;
		private int index2;

		public Position(int i1, int i2) {
			index1 = i1;
			index2 = i2;
		}

		public int getIndex1() {
			return index1;
		}

		public int getIndex2() {
			return index2;
		}
	}

	/**
	 * Metoda wykonuje analize planszy do gry i zwraca posortowana tablice
	 * poprawnych ruchow dla gracza poslugujacego sie wskazanym kolorem piona.
	 * 
	 * @param board
	 *            aktualny stan gry - kwadratowa tablica 8x8, pozycje
	 *            nieobsadzone przez piony zawieraja null. Stan gry moze byc
	 *            dowolnie wymyslony - nie musi byc realizacja zadnej konkretnej
	 *            gry.
	 * 
	 * @param playerDisk
	 *            kolor piona, ktorym posluguje sie gracz zlecajacy wykonanie
	 *            analizy planszy
	 * @return posortowana malejaco wzgledem liczby zdobytych pionow przeciwnika
	 *         tablica poprawnych kontynuacji gry. W przypadku braku mozliwosci
	 *         ruchu - tablica o rozmiarze 0.
	 */
	Position[] analyze(Disk[][] board, Disk playerDisk);
}