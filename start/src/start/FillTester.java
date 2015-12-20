package start;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runners.MethodSorters;
import org.junit.Rule;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FillTester {

	@Rule
	public TestName name = new TestName();

	private FillBase fb;

	@Before
	public void create() {
		PMO_SystemOutRedirect.startRedirectionToNull();
		fb = new FillBaseImplementation();
		PMO_SystemOutRedirect.returnToStandardStream();
	}

	private void runFill(int[][] image, boolean[][] neighbours, int[] colors,
			int color, int firstIndex, int secondIndex) {
		System.out.println("- - - - - - - - - - - - - - - - - - - - -");
		System.out.println("- - >  Kod testuje metoda: "
				+ name.getMethodName());
		System.out.println("- - - - - - - - - - - - - - - - - - - - -");
		try {
			showNeighboursTable(neighbours);
			System.out.println( "Before:");
			show(image);
			PMO_SystemOutRedirect.startRedirectionToNull();
			fb.fill(image, neighbours, colors, color, firstIndex, secondIndex);
		} catch (Exception e) {
			PMO_SystemOutRedirect.returnToStandardStream();
			System.out
					.println("BLAD: W trakcie wykonania metody fill doszlo do wyjatku");
			System.out.println("BLAD: Zgloszono wyjatek " + e.toString());
			org.junit.Assert
					.fail("Nie powinno dojsc do pojawienia sie wyjatku");
		} finally {
			PMO_SystemOutRedirect.returnToStandardStream();
			System.out.println( "After:");
			show(image);
		}
	}

	private static void show(int[][] image) {

		String[] i2s = { "   ", " . ", " x ", " + ", " # ", " o ", " @ ", " ! " };

		for (int i = 0; i < image[0].length; i++) {
			System.out.print("|");
			for (int j = 0; j < image.length; j++) {
				System.out.print(i2s[image[j][i]]);
			}
			System.out.println("|");
		}

	}

	private static String boolConver(boolean b) {
		return b ? " T " : " F ";
	}

	private static void showNeighboursTable(boolean[][] n) {
		System.out.println("| " + boolConver(n[0][0]) + " | "
				+ boolConver(n[1][0]) + " | " + boolConver(n[2][0]) + " |");
		System.out.println("| " + boolConver(n[0][1]) + " | "
				+ boolConver(n[1][1]) + " | " + boolConver(n[2][1]) + " |");
		System.out.println("| " + boolConver(n[0][2]) + " | "
				+ boolConver(n[1][2]) + " | " + boolConver(n[2][2]) + " |");
	}

	private void allFilled(int[][] image, int color) {
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {
				assertEquals("Oczekiwano koloru wypelnienia", color,
						image[i][j]);
			}
	}

	@Test(timeout = 500)
	public void fillAllTest1() {
		int[][] img = new int[8][8];
		boolean[][] ngbrs = new boolean[][] { { true, true, true },
				{ true, true, true }, { true, true, true } };

		runFill(img, ngbrs, new int[] {}, 1, 4, 4);
		allFilled(img, 1);
		runFill(img, ngbrs, new int[] {}, 2, 0, 0);
		allFilled(img, 2);
		runFill(img, ngbrs, new int[] {}, 3, 7, 7);
		allFilled(img, 3);
	}

	@Test(timeout = 500)
	public void fillAllTest2() {
		int[][] img = new int[8][8];
		boolean[][] ngbrs = new boolean[][] { { false, true, false },
				{ true, true, true }, { false, true, false } };
		runFill(img, ngbrs, new int[] {}, 1, 4, 4);
		allFilled(img, 1);
		runFill(img, ngbrs, new int[] {}, 2, 0, 0);
		allFilled(img, 2);
		runFill(img, ngbrs, new int[] {}, 3, 7, 7);
		allFilled(img, 3);
	}

	@Test(timeout = 500)
	public void horizontal() {
		int[][] img = new int[5][5];
		boolean[][] ngbrs = new boolean[3][3];
		ngbrs[0][1] = true;
		ngbrs[2][1] = true;
		runFill(img, ngbrs, new int[] {}, 1, 2, 2);

		assertEquals(
				"Wypelniamy jedna linie w poziomie w lewo od pozycji startowej",
				1, img[0][2]);
		assertEquals(
				"Wypelniamy jedna linie w poziomie w prawo od pozycji startowej",
				1, img[4][2]);
		assertEquals("Wypelniamy jedna linie w poziomie, a nie w pionie", 0,
				img[2][0]);
		assertEquals("Wypelniamy jedna linie w poziomie, a nie w pionie", 0,
				img[2][4]);
	}

	@Test(timeout = 500)
	public void vertical() {
		int[][] img = new int[5][5];
		boolean[][] ngbrs = new boolean[3][3];
		ngbrs[1][0] = true;
		ngbrs[1][2] = true;

		runFill(img, ngbrs, new int[] {}, 1, 2, 2);

		assertEquals(
				"Wypelniamy jedna linie w pionie w gore od pozycji startowej",
				1, img[2][4]);
		assertEquals(
				"Wypelniamy jedna linie w pionie w dol od pozycji startowej",
				1, img[2][0]);
		assertEquals("Wypelniamy jedna linie w pionie, a nie w poziomie", 0,
				img[0][2]);
		assertEquals("Wypelniamy jedna linie w pionie, a nie w poziomie", 0,
				img[4][2]);
	}

	@Test(timeout = 500)
	public void left() {
		int[][] img = new int[5][5];
		boolean[][] ngbrs = new boolean[3][3];
		ngbrs[0][1] = true;

		runFill(img, ngbrs, new int[] {}, 1, 2, 2);

		assertEquals(
				"Wypelniamy jedna linie w poziomie w lewo od pozycji startowej",
				1, img[0][2]);
		assertEquals("Nie wypelniamy linii w prawo (rosnacy pierwszy indeks)",
				0, img[0][4]);
	}

	@Test(timeout = 500)
	public void fillWithBlockade1() {
		int[][] img = new int[6][6];
		boolean[][] ngbrs = new boolean[3][3];
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				ngbrs[i][j] = true;

		int c = 1;

		img[3][2] = c;
		img[3][3] = c;
		img[4][1] = c;
		img[5][1] = c;
		img[4][4] = c;
		img[5][4] = c;

		runFill(img, ngbrs, new int[] {}, c, 1, 2);

		assertEquals(
				"Obrazek na pozycji [0][0] powinien byc wypelniony kolorem", c,
				img[0][0]);
		assertEquals(
				"Obrazek na pozycji [0][5] powinien byc wypelniony kolorem", c,
				img[0][5]);

		String message = "Kolor, ktorym wypelniamy obrazek nie jest chroniony, calosc powinna byc wypelniona";
		assertEquals(message, c, img[4][2]);
		assertEquals(message, c, img[4][3]);
		assertEquals(message, c, img[5][2]);
		assertEquals(message, c, img[5][3]);
	}

	@Test(timeout = 500)
	public void fillWithBlockade2() {
		int[][] img = new int[6][6];
		boolean[][] ngbrs = new boolean[3][3];
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				ngbrs[i][j] = true;

		int c = 1;

		img[3][2] = c;
		img[3][3] = c;
		img[4][1] = c;
		img[5][1] = c;
		img[4][4] = c;
		img[5][4] = c;

		runFill(img, ngbrs, new int[] { c }, c, 1, 2);

		assertEquals(
				"Obrazek na pozycji [0][0] powinien byc wypelniony kolorem", c,
				img[0][0]);
		assertEquals(
				"Obrazek na pozycji [0][5] powinien byc wypelniony kolorem", c,
				img[0][5]);

		String message = "Kolor, ktorym wypelniamy obrazek jest chroniony ";
		message += "- ale jest przejscie -> calosc powinna zostac wypelniona";
		assertEquals(message, c, img[4][2]);
		assertEquals(message, c, img[4][3]);
		assertEquals(message, c, img[5][2]);
		assertEquals(message, c, img[5][3]);
	}

	@Test(timeout = 500)
	public void fillWithBlockade3() {
		int[][] img = new int[6][6];
		boolean[][] ngbrs = new boolean[3][3];
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				ngbrs[i][j] = true;

		ngbrs[0][0] = false;
		ngbrs[2][2] = false;
		ngbrs[2][0] = false;
		ngbrs[0][2] = false;

		int c = 1;

		img[3][2] = c;
		img[3][3] = c;
		img[4][1] = c;
		img[5][1] = c;
		img[4][4] = c;
		img[5][4] = c;

		runFill(img, ngbrs, new int[] { c }, c, 1, 2);

		assertEquals(
				"Obrazek na pozycji [0][0] powinien byc wypelniony kolorem", c,
				img[0][0]);
		assertEquals(
				"Obrazek na pozycji [0][5] powinien byc wypelniony kolorem", c,
				img[0][5]);

		String message = "Kolor, ktorym wypelniamy obrazek jest chroniony";
		message += "- nie ma przejscia -> calosc nie powinna zostac wypelniona";
		assertEquals(message, 0, img[4][2]);
		assertEquals(message, 0, img[4][3]);
		assertEquals(message, 0, img[5][2]);
		assertEquals(message, 0, img[5][3]);
	}

	@Test(timeout = 500)
	public void fillWithBlockade4() {
		int[][] img = new int[6][6];
		boolean[][] ngbrs = new boolean[3][3];
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				ngbrs[i][j] = true;

		ngbrs[0][0] = false;
		ngbrs[2][2] = false;
		ngbrs[2][0] = false;
		ngbrs[0][2] = false;

		int d = 2;
		int e = d + 1;

		img[3][2] = d;
		img[3][3] = e;
		img[4][1] = e;
		img[5][1] = d;
		img[4][4] = d;
		img[5][4] = e;

		int f = e + 1;

		runFill(img, ngbrs, new int[] { d, e }, f, 1, 2);

		assertEquals(
				"Obrazek na pozycji [0][0] powinien byc wypelniony kolorem", f,
				img[0][0]);
		assertEquals(
				"Obrazek na pozycji [0][5] powinien byc wypelniony kolorem", f,
				img[0][5]);

		String message = "Jest bariera, przez ktora nie ma przejscia";
		message += "Calosc nie powinna zostac wypelniona";
		assertEquals(message, 0, img[4][2]);
		assertEquals(message, 0, img[4][3]);
		assertEquals(message, 0, img[5][2]);
		assertEquals(message, 0, img[5][3]);
	}

	public static void main(String[] args) {
		show(new int[][] { { 0, 0, 0, 0 }, { 1, 0, 1, 0 }, { 0, 2, 0, 2 },
				{ 0, 0, 0, 0 }, { 4, 5, 6, 7 } });

		showNeighboursTable(new boolean[][] { { true, false, false },
				{ true, true, false }, { true, false, true } });
	}
}