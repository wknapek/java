
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OtelloTester {

	private ReversiBoardInterfaceExt otello;
	private PMO_OtelloBordGenerator board;
	private ReversiBoardInterface.Disk BLACK = ReversiBoardInterface.Disk.BLACK;
	private ReversiBoardInterface.Disk WHITE = ReversiBoardInterface.Disk.WHITE;

	@Before
	public void start() {
		board = new PMO_OtelloBordGenerator();
		PMO_SystemOutRedirect.startRedirectionToNull();
	}

	private boolean positionsEqual(ReversiBoardInterface.Position p1,
			ReversiBoardInterface.Position p2) {
		return (p1.getIndex1() == p2.getIndex1())
				&& (p1.getIndex2() == p2.getIndex2());
	}

	private String pos2String(ReversiBoardInterface.Position pos) {
		return "[ " + pos.getIndex1() + ", " + pos.getIndex2() + " ]";
	}

	private void testPositionExists(ReversiBoardInterface.Position test,
			ReversiBoardInterface.Position[] testedPositions) {
		for (ReversiBoardInterface.Position pos : testedPositions) {
			if (positionsEqual(test, pos))
				return;
		}
		fail("Wsrod zwroconych propozycji ruchu nie znaleziono "
				+ pos2String(test));
	}

	private ReversiBoardInterface.Position createPosition(int idx1, int idx2) {
		return new ReversiBoardInterface.Position(idx1, idx2);
	}

	/**
	 * Metoda ustawia stan planszy i sprawdza, ze przyjeto do wiadomosci kto ma
	 * grac jako nastepny.
	 * 
	 * @param board
	 *            plansza
	 * @param nextPlayer
	 *            gracz, ktory ma jako wykonac ruch
	 */
	private void setGameState(ReversiBoardInterface.Disk[][] board,
			ReversiBoardInterface.Disk nextPlayer) {
		try {
			otello.setGameState(board, nextPlayer);
		} catch (Exception e) {
			PMO_SystemOutRedirect
					.println("W trakcie inicjacji pojawil sie wyjatek "
							+ e.toString());
			fail("Wyjatek w trakcie inicjacji poprzez setGameState");
		}

		try {
			ReversiBoardInterface.Disk player = otello.nextPlayer();
			assertEquals("Nastepny gracz wg. systemu jest inny niz ustawiono",
					nextPlayer, player);
		} catch (Exception e) {
			PMO_SystemOutRedirect
					.println("Wykonanie nextPlayer powoduje wyjatek "
							+ e.toString());
			fail("Wyjatek w wyniku prawidlowego wykonania nextPlayer");
		}
	}

	/**
	 * Metoda testujaca
	 * 
	 * @param board
	 *            generator planszy. Za kazdym razem przed uzyciem jest
	 *            klonowany
	 * @param nextPlayer
	 *            gracz, ktory ustawiany jest jako ten, ktory ma wykonac ruch
	 * @param setGameState
	 *            czy wykonac inicjacje gry, czy to jest kontynuacja
	 * @param pos
	 *            pozycja, ktora wybiera gracz
	 * @param expectedResult
	 *            rezultat, ktorego sie spodziewamy
	 * @param correctMove
	 *            czy ruch jest poprawny
	 * @param canContinueAfterMove
	 *            czy nastepny ruch moze byc wykonany
	 * @param nextNextPlayer
	 *            kto kontynuuje gre
	 */
	private void simpleTest(PMO_OtelloBordGenerator board,
			ReversiBoardInterface.Disk nextPlayer, boolean setGameState,
			ReversiBoardInterface.Position pos, int expectedResult,
			boolean correctMove, boolean canContinueAfterMove,
			ReversiBoardInterface.Disk nextNextPlayer) {
		try {
			if (setGameState) { // inicjacja stanu planszy - tworzymy nowy
								// obiekt
				otello = new ReversiBoardExt();
				setGameState(
						((PMO_OtelloBordGenerator) board.clone()).getBoard(),
						nextPlayer);
			}
			try {
				int result = otello.move(pos); // wykonujemy ruch
				assertEquals("Spodziewano sie innej liczby zdobytych pionkow",
						expectedResult, result);

				// test mozliwosci kontynuacji
				assertEquals("Kontynuacja gry", canContinueAfterMove,
						otello.canWeContinueTheGame());

				try {
					// kto jest nastepnym graczem
					ReversiBoardInterface.Disk player = otello.nextPlayer();

					assertNotNull("Next player nie moze byc null", player);
					if (canContinueAfterMove) {
						assertEquals("Nastepny gracz ", nextNextPlayer, player);
					} else {
						fail("Gra nie moze byc kontynuowana a nextPlayer nie zglosil CanNotCountinue");
					}

				} catch (ReversiBoardInterface.CanNotContinue e) {
					if (canContinueAfterMove) {
						fail("Wykryto wyjatek CanNotContinue gdy kontynuacja jest mozliwa");
					}
				}

			} catch (ReversiBoardInterface.IllegalMove e) {
				if (correctMove) {
					fail("W przypadku poprawnego ruchu wyjatek IllegalMove nie moze wystapic");
				}
			} catch (Exception e) {
				fail("Wystapil nieoczekiwany wyjatek " + e.toString());
			}
		} catch (CloneNotSupportedException e) {
			PMO_SystemOutRedirect.returnToStandardStream();
			e.printStackTrace();
		}

	}

	// test podstaw
	/*@Test(timeout = 1000)
	public void simpleTest1() throws ReversiBoardInterfaceExt.IllegalOperationException {
		simpleTest(board, BLACK, true, createPosition(2, 4), 1, true, true,
				WHITE);
		simpleTest(board, BLACK, true, createPosition(3, 5), 1, true, true,
				WHITE);
		simpleTest(board, BLACK, true, createPosition(4, 2), 1, true, true,
				WHITE);
		simpleTest(board, BLACK, true, createPosition(5, 3), 1, true, true,
				WHITE);
        otello.undo();
	}*/

	// test - prosta gra
	@Test(timeout = 1000)
	public void simpleTest2() throws ReversiBoardInterfaceExt.IllegalOperationException {
		board.setBlack(4, 3);
		board.setBlack(5, 3);

		simpleTest(board, WHITE, true, createPosition(5, 4), 1, true, true,
				BLACK);
		simpleTest(board, BLACK, false, createPosition(3, 5), 2, true, true,
				WHITE);
        int score5 = otello.getResult(WHITE);
        int score1 = otello.getResult(BLACK);
		simpleTest(board, WHITE, false, createPosition(2, 4), 2, true, true,
				BLACK);
        int score2 = otello.getResult(WHITE);
        otello.undo();
        score1 = otello.getResult(BLACK);
        otello.redo();
		simpleTest(board, BLACK, false, createPosition(5, 5), 2, true, true,
				WHITE);
        int score3 = otello.getResult(BLACK);
		simpleTest(board, WHITE, false, createPosition(4, 2), 1, true, true,
				BLACK);
        otello.undo();
        otello.undo();
        otello.undo();
        otello.redo();
        otello.redo();
        otello.redo();
        int score4 = otello.getResult(WHITE);
        assertEquals("Czarne prowadza 6:4", 6, otello.getResult(BLACK));
		assertEquals("Biale przegrywaja 4:6", 4, otello.getResult(WHITE));
	}

	// test - prosta gra z blednymi posunieciami
	/*@Test(timeout = 1000)
	public void simpleTest3() {
		board.setBlack(4, 3);
		board.setBlack(5, 3);

		simpleTest(board, WHITE, true, createPosition(5, 4), 1, true, true,
				BLACK);
		simpleTest(board, BLACK, false, createPosition(2, 3), 2, false, true,
				BLACK);
		simpleTest(board, BLACK, false, createPosition(5, 6), 2, false, true,
				BLACK);
		simpleTest(board, BLACK, false, createPosition(3, 2), 2, false, true,
				BLACK);
		simpleTest(board, BLACK, false, createPosition(4, 5), 1, true, true,
				WHITE);

		assertEquals("Czarne prowadza 5:2", 5, otello.getResult(BLACK));
		assertEquals("Biale przegrywaja 2:5", 2, otello.getResult(WHITE));
	}

	// test - gra z blokadami
	@Test(timeout = 1000)
	public void simpleTest4() {
		for (int i = 0; i < 5; i++) {
			board.setWhite(i, 7);
			board.setWhite(i, 6);
		}

		board.setWhite(5, 7);
		board.setWhite(6, 7);
		board.setWhite(7, 7);

		board.setWhite(0, 5);
		board.setWhite(1, 5);
		board.setWhite(2, 5);

		board.setBlack(5, 6);
		board.setBlack(3, 5);
		board.setBlack(4, 5);
		board.setBlack(5, 5);

		board.setBlack(1, 4);
		board.setBlack(3, 4);
		board.setBlack(4, 4);

		for (int i = 1; i < 6; i++)
			board.setBlack(i, 3);

		board.setBlack(4, 2);
		board.setBlack(5, 2);
		board.setBlack(4, 1);
		board.setBlack(5, 1);
		board.setBlack(4, 0);

		simpleTest(board, WHITE, true, createPosition(0, 3), 1, true, true,
				WHITE);
		simpleTest(board, WHITE, false, createPosition(6, 6), 1, true, true,
				WHITE);
		simpleTest(board, WHITE, false, createPosition(6, 5), 3, true, true,
				WHITE);
		simpleTest(board, WHITE, false, createPosition(1, 2), 3, true, true,
				BLACK);

		assertEquals("Biale prowadza 28:9", 28, otello.getResult(WHITE));
		assertEquals("Czarne przegrywaja 9:28", 9, otello.getResult(BLACK));
	}

	// test - gra test braku mozliwosci kontynuacji
	@Test(timeout = 1000)
	public void simpleTest5() {
		for (int i = 0; i < 8; i++) {
			board.setBlack(i, 3);
			board.setBlack(i, 4);
			board.setBlack(i, 5);
		}
		for (int i = 3; i < 8; i++)
			board.setBlack(i, 2);

		board.setBlack(3, 1);
		board.setBlack(6, 1);
		board.setBlack(7, 6);
		board.setBlack(4, 6);
		board.setBlack(4, 7);

		board.setWhite(2, 1);
		board.setWhite(2, 2);
		board.setWhite(2, 3);

		simpleTest(board, BLACK, true, createPosition(2, 0), 3, true, false,
				WHITE);

		assertEquals("Czarne prowadza 37:0", 37, otello.getResult(BLACK));
		assertEquals("Biale przegrywaja 0:37", 0, otello.getResult(WHITE));
	}

	// test - gra 
	@Test(timeout = 1000)
	public void simpleGame() {
		simpleTest(board, BLACK, true, createPosition(5, 3), 1, true, true,
				WHITE);
		simpleTest(board, WHITE, false, createPosition(5, 2), 1, true, true,
				BLACK);
		simpleTest(board, BLACK, false, createPosition(4, 2), 1, true, true,
				WHITE);
		simpleTest(board, WHITE, false, createPosition(3, 2), 2, true, true,
				BLACK);
		simpleTest(board, BLACK, false, createPosition(2, 1), 1, true, true,
				WHITE);
		simpleTest(board, WHITE, false, createPosition(2, 2), 1, true, true,
				BLACK);
		simpleTest(board, BLACK, false, createPosition(2, 3), 2, true, true,
				WHITE);
		simpleTest(board, WHITE, false, createPosition(5, 4), 3, true, true,
				BLACK);
		simpleTest(board, BLACK, false, createPosition(5, 3), 0, false, true,
				BLACK);
		simpleTest(board, BLACK, false, createPosition(6, 2), 3, true, true,
				WHITE);
		simpleTest(board, WHITE, false, createPosition(1, 0), 2, true, true,
				BLACK);
		simpleTest(board, BLACK, false, createPosition(3, 5), 3, true, true,
				WHITE);
		simpleTest(board, WHITE, false, createPosition(7, 2), 3, true, true,
				BLACK);
		simpleTest(board, BLACK, false, createPosition(4, 1), 3, true, true,
				WHITE);
		simpleTest(board, WHITE, false, createPosition(3, 0), 1, true, true,
				BLACK);
		simpleTest(board, BLACK, false, createPosition(2, 0), 1, true, true,
				WHITE);
		simpleTest(board, WHITE, false, createPosition(2, 5), 2, true, true,
				BLACK);
		simpleTest(board, BLACK, false, createPosition(0, 0), 1, true, true,
				WHITE);
		simpleTest(board, WHITE, false, createPosition(2, 6), 3, true, true,
				BLACK);
		simpleTest(board, BLACK, false, createPosition(4, 0), 2, true, true,
				WHITE);
		simpleTest(board, WHITE, false, createPosition(3, 1), 3, true, true,
				BLACK);
		simpleTest(board, BLACK, false, createPosition(3, 6), 5, true, true,
				WHITE);

		assertEquals("Czarne prowadza 15:10", 15, otello.getResult(BLACK));
		assertEquals("Biale przegrywaja 10:15", 10, otello.getResult(WHITE));
		
		assertEquals( "Na polu 2x2 powinien byc pion czarny", BLACK, otello.getBoard()[2][2]);
		assertEquals( "Na polu 3x3 powinien byc pion czarny", BLACK, otello.getBoard()[3][3]);
		assertEquals( "Na polu 4x4 powinien byc pion bialy", WHITE, otello.getBoard()[4][4]);		
	}


	@AfterClass 
	public static void end() {
		System.out.println( "W tym zadaniu _wszystkie_ testy musza byc zaliczone!" );
	}*/
	
}