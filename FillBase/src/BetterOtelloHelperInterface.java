/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dom
 */
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface BetterOtelloHelperInterface {
	/**
	 * Typ wyliczeniowy reprezentujacy pionka do gry w Otello.
	 * 
	 */
	enum Disk {
		BLACK, WHITE;
	}

	/**
	 * Klasa reprezentuje pozycje w dwu-wymiarowej tablicy uzywanej do zapisu
	 * stanu do gry. Uzycie przechowywanych w obiektach indeksow jest zgodne z ich nazwa
	 * czyli para indeksow index1,index2 wskazuje na pozycje w tablicy zapisana
	 * nastepujaco: tablica[index1][index2]
	 */
	class Position {
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
	 *         mapa poprawnych kontynuacji gry. Kluczem mapy jest
	 *         liczba zdobywanych na przeciwniku pionow. W przypadku braku mozliwosci
	 *         ruchu - mapa o rozmiarze 0. 
	 *         <br>
	 *         Kontynuacje gry sa zbiorem, w ktorym
	 *         umieszczone sa listy alternatywnych posuniec prowadzacych do tej samej zdobyczy
	 *         punktowej. Najczesciej listy beda zawierac tylko jedno polozenie
	 *         piona, ale moze sie teraz zdarzyc, ze swym posunieciem
	 *         gracz zablokuje mozliwosc odpowiedzi przeciwnika i wtedy moze
	 *         zdobyc kolejne punkty stawiajac na planszy kolejnego wlasnego piona.
	 *         Metoda analizuje wiec nie tylko jeden ruch gracza, ale musi sprawdzic, czy
	 *         posuniecie nie prowadzi do zablokowania ruchu przeciwnika i mozliwosci 
	 *         zdobycia wiekszej liczby pionow. W takiej sytuacji lista staje sie wieloelementowa
	 *         i zawiera polozenia kolejnych posuniec.
	 */
	Map<Integer, Set< List< Position >>> analyze(Disk[][] board, Disk playerDisk);

}