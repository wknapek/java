import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OtelloTester {

	private OtelloHelperInterface otello;
	private PMO_OtelloBordGenerator board;
	private OtelloHelperInterface.Position[] result;
	private OtelloHelperInterface.Disk BLACK = OtelloHelperInterface.Disk.BLACK;
	private OtelloHelperInterface.Disk WHITE = OtelloHelperInterface.Disk.WHITE;

	@Before
	public void start() {
		board = new PMO_OtelloBordGenerator();
		otello = new OtelloHelper();
		PMO_SystemOutRedirect.startRedirectionToNull();
	}

	private boolean positionsEqual(OtelloHelperInterface.Position p1,
			OtelloHelperInterface.Position p2) {
		return (p1.getIndex1() == p2.getIndex1())
				&& (p1.getIndex2() == p2.getIndex2());
	}

	private String pos2String(OtelloHelperInterface.Position pos) {
		return "[ " + pos.getIndex1() + ", " + pos.getIndex2() + " ]";
	}

	private void testPositionExists(OtelloHelperInterface.Position test,
			OtelloHelperInterface.Position[] testedPositions) {
		for (OtelloHelperInterface.Position pos : testedPositions) {
			if (positionsEqual(test, pos))
				return;
		}
		fail("Wsrod zwroconych propozycji ruchu nie znaleziono "
				+ pos2String(test));
	}

	private OtelloHelperInterface.Position createPosition(int idx1, int idx2) {
		return new OtelloHelperInterface.Position(idx1, idx2);
	}

	private void runTest(PMO_OtelloBordGenerator board,
			OtelloHelperInterface.Disk disk) {
		PMO_SystemOutRedirect.println(board.toString());
		result = otello.analyze(board.getBoard(), disk);
		PMO_SystemOutRedirect.returnToStandardStream();
	}

	@Test(timeout = 1000)
	public void simpleTest1() {

		runTest(board, BLACK);

		assertEquals("Zaczynaja czarne i maja 4 mozliwosci ruchu!", 4,
				result.length);

		testPositionExists(createPosition(2, 4), result);
		testPositionExists(createPosition(3, 5), result);
		testPositionExists(createPosition(4, 2), result);
		testPositionExists(createPosition(5, 3), result);

	}

	@Test(timeout = 1000)
	public void simpleTest2() {
		board.setBlack(4, 3);
		board.setBlack(5, 3);
		runTest(board, WHITE);
		assertEquals("Graja biale", 3, result.length);

		testPositionExists(createPosition(5, 4), result);
		testPositionExists(createPosition(3, 2), result);
		testPositionExists(createPosition(5, 2), result);
	}

	private void showArray(OtelloHelperInterface.Position[] arr) {
		for (OtelloHelperInterface.Position pos : arr) {
			System.out.println(pos2String(pos));
		}
	}

	@Test(timeout = 1000)
	public void simpleTest3() {
		board.setBlack(3, 3);
		board.setBlack(4, 3);
		board.setBlack(5, 3);
		board.setWhite(3, 4);
		board.setWhite(4, 4);
		board.setWhite(5, 4);
		runTest(board, BLACK);

		assertEquals("Graja czarne", 5, result.length);

		testPositionExists(createPosition(3, 5),
				Arrays.copyOfRange(result, 0, 2));
		testPositionExists(createPosition(5, 5),
				Arrays.copyOfRange(result, 0, 2));
		testPositionExists(createPosition(2, 5), result);
		testPositionExists(createPosition(4, 5), result);
		testPositionExists(createPosition(6, 5), result);
	}

	@Test(timeout = 1000)
	public void simpleTest4() {
		board.setBlack(3, 5);
		board.setBlack(3, 4);
		board.setBlack(3, 3);
		board.setBlack(4, 4);
		board.setBlack(4, 3);
		board.setBlack(5, 3);
		board.setWhite(5, 4);
		runTest(board, WHITE);

		assertEquals("Graja biale", 3, result.length);

		testPositionExists(createPosition(2, 4),
				Arrays.copyOfRange(result, 0, 1));
		testPositionExists(createPosition(5, 2), result);
		testPositionExists(createPosition(3, 2), result);
	}

	@Test(timeout = 1000)
	public void simpleTest4B() {
		board.setBlack(3, 7);
		board.setBlack(3, 6);
		board.setBlack(3, 5);
		board.setBlack(3, 4);
		board.setBlack(3, 3);
		board.setBlack(3, 2);
		board.setBlack(3, 1);
		board.setBlack(3, 0);

		board.setBlack(4, 4);
		board.setBlack(4, 3);

		board.setBlack(5, 3);
		board.setBlack(5, 7);
		board.setBlack(6, 7);
		board.setBlack(1, 1);
		board.setBlack(2, 1);

		board.setWhite(4, 1);
		board.setWhite(4, 7);
		board.setWhite(6, 3);
		runTest(board, WHITE);

		assertEquals("Graja biale", 5, result.length);

		testPositionExists(createPosition(2, 3),
				Arrays.copyOfRange(result, 0, 1));
		testPositionExists(createPosition(0, 1),
				Arrays.copyOfRange(result, 1, 2));
		testPositionExists(createPosition(7, 7),
				Arrays.copyOfRange(result, 2, 3));
		testPositionExists(createPosition(2, 5), result);
		testPositionExists(createPosition(2, 7), result);
	}

	@Test(timeout = 1000)
	public void simpleTest5() {
		board.setBlack(3, 7);
		board.setBlack(3, 6);
		board.setBlack(3, 5);
		board.setBlack(3, 4);
		board.setBlack(3, 3);
		board.setBlack(3, 2);
		board.setBlack(3, 1);
		board.setBlack(4, 4);
		board.setBlack(4, 3);
		board.setBlack(5, 3);
		board.setWhite(3, 0);
		runTest(board, WHITE);

		assertNotNull("Brak ruchu - tablica o rozmiarze zero!", result);
		assertEquals("Graja biale i nie maja ruchu", 0, result.length);
	}

	@Test(timeout = 1000)
	public void simpleTest6() {
		board.setBlack(3, 7);
		board.setBlack(3, 6);
		board.setBlack(3, 5);
		board.setBlack(3, 4);
		board.setBlack(3, 3);
		board.setBlack(3, 2);
		board.setBlack(3, 1);
		board.setBlack(4, 4);
		board.setBlack(4, 3);
		board.setBlack(5, 3);
		runTest(board, WHITE);

		assertNotNull("Brak ruchu - tablica o rozmiarze zero!", result);
		assertEquals("Graja biale i nie maja ruchu", 0, result.length);
	}
}