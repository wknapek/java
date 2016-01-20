import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OtelloTester {

	private BetterOtelloHelperInterface otello;
	private PMO_OtelloBordGenerator board;
	private Map<Integer, Set<List<BetterOtelloHelperInterface.Position>>> result;
	private BetterOtelloHelperInterface.Disk BLACK = BetterOtelloHelperInterface.Disk.BLACK;
	private BetterOtelloHelperInterface.Disk WHITE = BetterOtelloHelperInterface.Disk.WHITE;

	@Before
	public void start() {
		board = new PMO_OtelloBordGenerator();
		otello = new BetterOtelloHelper();
		PMO_SystemOutRedirect.startRedirectionToNull();
	}

	private String pos2String(BetterOtelloHelperInterface.Position pos) {
		return "[ " + pos.getIndex1() + ", " + pos.getIndex2() + " ]";
	}

	private void testMapSize(int mapSize) {
		assertEquals("Rozmiar mapy", mapSize, result.size());
	}

	private void testSetSize(int mapKey, int setSize) 
    {
		assertTrue("Mapa powinna zawierac klucz " + mapKey,
				result.containsKey(mapKey));
		assertEquals("Rozmiar zbioru rownowaznych kontynuacji dla klucza "
				+ mapKey, setSize, result.get(mapKey).size());
	}

	private BetterOtelloHelperInterface.Position createPosition(int idx1,
			int idx2) {
		return new BetterOtelloHelperInterface.Position(idx1, idx2);
	}

	private void runTest(PMO_OtelloBordGenerator board,
			BetterOtelloHelperInterface.Disk disk) {
		PMO_SystemOutRedirect.println(board.toString());
		try {
			result = otello.analyze(board.getBoard(), disk);
			assertNotNull("Wynik pracy analizatora nie moze byc NULL", result);
		} catch (Exception e) {
			PMO_SystemOutRedirect.returnToStandardStream();
			fail("W trakcie testu doszlo do wyjatku " + e.getMessage());
		}
		PMO_SystemOutRedirect.returnToStandardStream();
	}

	private List<BetterOtelloHelperInterface.Position> createList(
			final int idx1, final int idx2) {
		return new ArrayList<BetterOtelloHelperInterface.Position>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 982311327753493177L;

			{
				add(createPosition(idx1, idx2));
			}
		};
	}

	// sprawdzamy czy pozycja wystepuje wsrod odpowiedzi o jednej pozycj
	private boolean containsPosition(
			BetterOtelloHelperInterface.Position position,
			Set<List<BetterOtelloHelperInterface.Position>> positionsSet) {
		for (List<BetterOtelloHelperInterface.Position> posl : positionsSet) {
			if (posl.size() == 1) {
				if (theSamePositionsTest(position, posl.get(0))) {
					return true;
				}
			}
		}
		return false;
	}

	// sprawdzamy czy dwie pozycje sa takie same
	private boolean theSamePositionsTest(
			BetterOtelloHelperInterface.Position p1,
			BetterOtelloHelperInterface.Position p2) {
		return (p1.getIndex1() == p2.getIndex1())
				&& (p1.getIndex2() == p2.getIndex2());
	}

	// sprawdzamy dwie listy pod wzgledem rozmiaru i zawartosci pozycji
	private boolean compareLists(
			List<BetterOtelloHelperInterface.Position> required,
			List<BetterOtelloHelperInterface.Position> found) {
		if (found.size() != required.size())
			return false;

		for (int i = 0; i < required.size(); i++) {
			if (!theSamePositionsTest(required.get(i), found.get(i)))
				return false;
		}

		return true;
	}

	// sprawdzamy, czy ktorakolwiek pozycja w zbiorze zgodna jest z wymagana
	// lista
	private boolean containPositions(
			List<BetterOtelloHelperInterface.Position> required,
			Set<List<BetterOtelloHelperInterface.Position>> found) {
		for (List<BetterOtelloHelperInterface.Position> foundList : found) {
			if (compareLists(required, foundList))
				return true;
		}
		return false;
	}

	private void testSet(int key,
			Set<List<BetterOtelloHelperInterface.Position>> positions) 
    {
		testSetSize(key, positions.size());
		for (List<BetterOtelloHelperInterface.Position> oneMove : positions) {
			if (oneMove.size() == 1) { // test ruchow pojedynczych
				assertTrue(
						"Ruch na pozycje "
								+ pos2String(oneMove.get(0))
								+ " jest poprawny i powinien byc na liscie ruchow zdobywajacych "
								+ key + " punktow",
						containsPosition(oneMove.get(0), result.get(key)));
			}
			if (oneMove.size() > 1) {
				assertTrue("Brak wymaganej listy polozen",
						containPositions(oneMove, result.get(key)));
			}
			// TODO przypadek braku ruchu
		}

	}

	private void orderTest() {
		int key = -1;
		
		for ( Map.Entry< Integer, Set<List<BetterOtelloHelperInterface.Position>>> entry : result.entrySet() ) {
			if ( key == -1 ) {
				key = entry.getKey();
				continue;
			}
			assertTrue( "Kolejny klucz mapy powinien byc mniejszy od poprzeniego", ( entry.getKey() < key ) ) ;
			key = entry.getKey();
		}
	}
	
	@Test(timeout = 1000)
	public void simpleTest1() {

		runTest(board, BLACK);

		testMapSize(1);

		Set<List<BetterOtelloHelperInterface.Position>> positions = new HashSet<>();
		positions.add(createList(2, 4));
		positions.add(createList(3, 5));
		positions.add(createList(4, 2));
		positions.add(createList(5, 3));

		try {
			testSet(1, positions);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(timeout = 1000)
	public void simpleTest2() {
		board.setBlack(4, 3);
		board.setBlack(5, 3);
		runTest(board, WHITE);

		testMapSize(1);

		Set<List<BetterOtelloHelperInterface.Position>> positions = new HashSet<>();
		positions.add(createList(5, 4));
		positions.add(createList(3, 2));
		positions.add(createList(5, 2));

		try {
			testSet(1, positions);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showArray(BetterOtelloHelperInterface.Position[] arr) {
		for (BetterOtelloHelperInterface.Position pos : arr) {
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
		
		testMapSize(2);
		orderTest();

		Set<List<BetterOtelloHelperInterface.Position>> positions = new HashSet<>();
		positions.add(createList(2, 5));
		positions.add(createList(4, 5));
		positions.add(createList(6, 5));

		try {
			testSet(1, positions);
		} catch (Exception e) {
			e.printStackTrace();
		}

		positions = new HashSet<>();
		positions.add(createList(5, 5));
		positions.add(createList(3, 5));

		try {
			testSet(2, positions);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		board.setBlack(1, 0);

		board.setWhite(0, 0);
		board.setWhite(4, 0);
		board.setWhite(4, 7);
		board.setWhite(6, 3);
		runTest(board, WHITE);

		testMapSize(3);
		orderTest();

		Set<List<BetterOtelloHelperInterface.Position>> positions = new HashSet<>();
		positions.add(createList(2, 3));
		testSet(3, positions);

		positions = new HashSet<>();
		positions.add(createList(2, 0));
		positions.add(createList(2, 2));
		positions.add(createList(7, 7));
		testSet(2, positions);

		positions = new HashSet<>();
		positions.add(createList(2, 7));
		positions.add(createList(2, 5));
		testSet(1, positions);
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

		testMapSize(0);
	}

	@Test(timeout = 1000)
	public void simpleTest10() {
		PMO_SystemOutRedirect.println( "UWAGA: test krytyczny - niezaliczenie tego testu = niezaliczenie projektu!");
		board.setBlack(3, 0);
		board.setBlack(3, 3);
		board.setBlack(3, 4);
		board.setBlack(4, 0);
		board.setBlack(4, 1);
		board.setBlack(4, 2);
		board.setBlack(4, 3);
		board.setBlack(4, 4);

		board.setBlack(5, 0);
		board.setBlack(5, 4);
		board.setBlack(6, 4);
		board.setBlack(7, 4);

		board.setWhite(2, 0);
		board.setWhite(6, 0);
		board.setWhite(1, 4);
		board.setWhite(2, 4);

		runTest(board, BLACK);

		
		testMapSize(3);
		orderTest();

		Set<List<BetterOtelloHelperInterface.Position>> positions = new HashSet<>();
		positions.add(new ArrayList<BetterOtelloHelperInterface.Position>() {
			private static final long serialVersionUID = -7734263178935013364L;

			{
				add(createPosition(0, 4));
				add(createPosition(7, 0));
				add(createPosition(1, 0));
			}
		});

		testSet(4, positions);

		positions = new HashSet<>();
		positions.add(new ArrayList<BetterOtelloHelperInterface.Position>() {
			private static final long serialVersionUID = 3758119936605219019L;

			{
				add(createPosition(0, 4));
				add(createPosition(1, 0));
			}
		});
		testSet(3, positions);

		positions = new HashSet<>();
		positions.add(createList(1, 5));
		positions.add(createList(1, 0));
		positions.add(createList(7, 0));
		testSet(1, positions);

		PMO_SystemOutRedirect.println( "UWAGA: test krytyczny - dotarlismy do konca testow");
	
	}

	@Test(timeout = 1000)
	public void simpleTest11() {
		PMO_SystemOutRedirect.println( "UWAGA: test krytyczny - niezaliczenie tego testu = niezaliczenie projektu!");
		board.setBlack(2, 2);
		board.setBlack(2, 3);
		board.setBlack(2, 4);
		board.setBlack(2, 5);
		board.setBlack(1, 6);
		board.setBlack(3, 4);
		board.setBlack(3, 3);
		board.setBlack(5, 4);
		board.setBlack(4, 3);
		board.setBlack(1, 0);
		board.setBlack(0, 0);
		board.setBlack(0, 3);
		board.setBlack(1, 2);
		board.setBlack(3, 2);
		board.setBlack(6, 5);
		board.setBlack(7, 6);

		board.setWhite(2, 6);
		board.setWhite(2, 1);
		board.setWhite(3, 0);

		runTest(board, BLACK);
		
		testMapSize(2);
		orderTest();

		Set<List<BetterOtelloHelperInterface.Position>> positions = new HashSet<>();
		positions.add(new ArrayList<BetterOtelloHelperInterface.Position>() {
			private static final long serialVersionUID = -7734263178935013364L;

			{
				add(createPosition(2, 7));
				add(createPosition(2, 0));
				add(createPosition(4, 0));
			}
		});

		testSet(3, positions);

		positions = new HashSet<>();
		positions.add(createList(3, 6));
		positions.add(createList(2, 0));
		testSet(1, positions);

		PMO_SystemOutRedirect.println( "UWAGA: test krytyczny - dotarlismy do konca testow");
	
	}
}