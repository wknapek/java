public interface ReversiBoardInterface1 {
	/**
	 * Typ wyliczeniowy reprezentujacy pionka do gry w Otello. Jest uzywany w
	 * tym interfejsie rowniez do wskazania gracza.
	 */
	enum Disk {
		BLACK, WHITE;
	}

	/**
	 * Klasa reprezentuje pozycje w dwu-wymiarowej tablicy uzywanej do zapisu
	 * stanu do gry. Uzycie przechowywanych w obiektach indeksow jest zgodne z
	 * ich nazwa, czyli para indeksow index1,index2 wskazuje na pozycje w
	 * tablicy zapisana nastepujaco: tablica[index1][index2]
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
	 * Wyjatek informujacy o blednym posunieciu gracza.
	 * 
	 */
	class IllegalMove extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = 9066912727591291840L;

	}

	/**
	 * Wyjatek informujacy o tym, ze gra nie moze byc kontynuowana.
	 */
	class CanNotContinue extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6041202580217822041L;

	}

	/**
	 * Metoda wykonywana przed kolejnymi. Ustawia aktualny stan gry i poprzez
	 * kolor pionka wskazuje gracza, ktory ma wykonac ruch jako nastepny.
	 * Podkreslam: gracz wskazywany jest za pomoca koloru jego pionkow.
	 * 
	 * @param board
	 *            aktualny stan planszy do gry
	 * @param nextPlayer
	 *            pionek gracza, ktory ma wykonac nastepny ruch
	 */
	void setGameState(Disk[][] board, Disk nextPlayer);

	/**
	 * Zwraca informacje, czy gra moze byc jeszcze kontynuowana.
	 * 
	 * @return true - dalsza gra jest mozliwa, false - brak mozliwosci wykonania
	 *         ruchu przez obu graczy
	 */
	boolean canWeContinueTheGame();

	/**
	 * Zwraca kolor pionka, ktorym ma zostac wykonany nastepny ruch.
	 * 
	 * @return - zwrocony kolor pionka wskazuje gracza, ktory ma wykonac kolejny
	 *         ruch.
	 * @throws CanNotContinue
	 *             wyjatek, ktory pojawia sie gry zaden z graczy nie moze
	 *             kontynuowac gry
	 */
	Disk nextPlayer() throws CanNotContinue;

	/**
	 * Metoda pozwala wskazac nastepny ruch. Ruch wykonywany jest przez tego
	 * gracza, ktory zostal wskazany przez wykonanie nextPlayer.
	 * 
	 * @param pos
	 *            polozenia na planszy, w ktorym ma zostac polozony pionek
	 * @return liczba zdobytych pionkow przeciwnika
	 * @throws IllegalMove
	 *             wyjatek weryfikowalny informujacy o bledzie gracza. Ruch nie
	 *             jest akceptowany. Gracz musi zaproponowac inne polozenie
	 *             zgodne z regulami gry i stanem planszy.
	 */
	int move(Position pos) throws IllegalMove;

	/**
	 * Metoda zwraca aktualny stan rozgrywki.
	 * 
	 * @return - dwuwymiarowa tablica 8x8 z zapisanym aktualnym stanem gry
	 */
	Disk[][] getBoard();

	/**
	 * Metoda zwraca aktualna liczbe pionkow nalezacych do gracza. Gracz
	 * wskazywany jest poprzez podanie koloru pionkow, ktorymi sie posluguje.
	 * 
	 * @param player
	 *            kolor pionkow uzywanych przez gracza, ktory chce poznac swoj
	 *            stan posiadania
	 * @return liczba pionkow na planszy, ktore sa w kolorze pionkow gracza
	 */
	int getResult(Disk player);

}