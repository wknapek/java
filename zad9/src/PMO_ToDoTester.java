import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

public class PMO_ToDoTester {
	private ToDoListsExtInterface todo;

	@Before
	public void init() {
		todo = new ToDoListsExt();
	}

	private void createList(String listname) {
		try {
			todo.createToDoList(listname);
		} catch (Exception e) {
			fail("Utworzono liste prawidlowo, a pojawil sie wyjatek");
		}
	}

	private void addItemToList(String item, String list) {
		try {
			todo.addItemToList(item, list);
			java.util.List<java.lang.String> result = todo.getItems(list);
			assertTrue("Dodany element powinien byc na liscie",
					result.contains(item));
		} catch (Exception e) {
			fail("Do utworzonej listy dodano prawidlowo pozycje, a pojawil sie wyjatek");
		}
	}

	@Test
	public void getItemsTest2() {
		try {
			createList("A");
			createList("B");
			addItemToList("A1", "A");
			addItemToList("A2", "A");
			addItemToList("B1", "B");
			addItemToList("B2", "B");
			todo.connectListToList("A", "B");
			java.util.List<java.lang.String> result = todo.getItems("B");
			assertNotNull("Nie powinno byc null w wyniku getItems", result);
			assertTrue("Element z listy podlaczonej powinien sie pojawic",
					result.contains("A1"));
			assertTrue("Element z listy podlaczonej powinien sie pojawic",
					result.contains("A2"));
			assertEquals("Rozmiar listy nie jest zgodny z oczekiwanym", 4,
					result.size());
		} catch (Exception e) {
			fail("Operacje zlecane sa poprawnie, a tu wyjatek "
					+ e.getMessage());
		}
	}

	@Test
	public void getItemsTest2R() {
		try {
			createList("A");
			createList("B");
			addItemToList("A1", "A");
			addItemToList("A2", "A");
			addItemToList("B1", "B");
			addItemToList("B2", "B");
			todo.connectListToList("A", "B");
			addItemToList("A2", "B");
		} catch (Exception e) {
			fail("Operacje zlecane sa poprawnie, a tu wyjatek "
					+ e.getMessage());
		}
	}

	@Test
	public void getItemsTest3() {
		createList("A");
		createList("B");
		createList("C");
		addItemToList("A1", "A");
		addItemToList("A2", "A");
		addItemToList("B1", "B");
		addItemToList("B2", "B");
		addItemToList("C1", "C");
		addItemToList("C2", "C");
		try {
			todo.connectListToList("A", "B");
			todo.connectListToList("C", "B");
			java.util.List<java.lang.String> result = todo.getItems("B");
			assertNotNull("Nie powinno byc null w wyniku getItems", result);
			assertTrue("Element z listy podlaczonej powinien sie pojawic",
					result.contains("A1"));
			assertTrue("Element z listy podlaczonej powinien sie pojawic",
					result.contains("A2"));
			assertTrue("Element z listy podlaczonej powinien sie pojawic",
					result.contains("C1"));
			assertTrue("Element z listy podlaczonej powinien sie pojawic",
					result.contains("C2"));
			assertEquals("Rozmiar listy nie jest zgodny z oczekiwanym", 6,
					result.size());
		} catch (Exception e) {
			fail("Operacje zlecane sa poprawnie, a tu wyjatek "
					+ e.getMessage());
		}
	}

	@Test
	public void getItemsTest3B() {
		createList("A");
		createList("B");
		createList("C");
		addItemToList("A1", "A");
		addItemToList("A2", "A");
		addItemToList("B1", "B");
		addItemToList("B2", "B");
		addItemToList("C1", "C");
		addItemToList("C2", "C");
		try {
			todo.connectListToList("A", "B");
			todo.connectListToList("B", "C");
			java.util.List<java.lang.String> result = todo.getItems("C");
			assertNotNull("Nie powinno byc null w wyniku getItems", result);
			assertTrue("Element z listy podlaczonej powinien sie pojawic",
					result.contains("B1"));
			assertTrue("Element z listy podlaczonej powinien sie pojawic",
					result.contains("B2"));
			assertFalse(
					"Element z niepodlaczonej listy nie powinien sie pojawic",
					result.contains("A1"));
			assertFalse(
					"Element z niepodlaczonej listy nie powinien sie pojawic",
					result.contains("A2"));
			assertEquals("Rozmiar listy nie jest zgodny z oczekiwanym", 4,
					result.size());
		} catch (Exception e) {
			fail("Operacje zlecane sa poprawnie, a tu wyjatek "
					+ e.getMessage());
		}
	}

	@Test
	public void allCheckedTest() {
		createList("A");
		createList("B");
		createList("C");
		addItemToList("A1", "A");
		addItemToList("A2", "A");
		addItemToList("B1", "B");
		addItemToList("B2", "B");
		addItemToList("C1", "C");
		try {

			assertFalse("Wszystkie nowotworzone pozycje sa UNCHECKED",
					todo.allChecked("A"));
			assertFalse("Wszystkie nowotworzone pozycje sa UNCHECKED",
					todo.allChecked("B"));

			todo.checkItem(todo.getUniqItemId("A1", "A"));
			todo.checkItem(todo.getUniqItemId("A2", "A"));

			assertTrue("Wszystkie pozycje ustawione na CHECKED",
					todo.allChecked("A"));

			todo.connectListToList("B", "A");

			assertFalse("Podlaczona lista ma pozycje UNCHECKED",
					todo.allChecked("A"));

			todo.checkItem(todo.getUniqItemId("B1", "B"));
			todo.checkItem(todo.getUniqItemId("B2", "B"));

			assertTrue("Wszystkie pozycje ustawione na CHECKED",
					todo.allChecked("A"));
			todo.connectListToList("C", "B");
			assertTrue(
					"Podlaczenie listy do listy do nas podlaczonej nie powinno zmienic wyniku",
					todo.allChecked("A"));
		} catch (Exception e) {
			fail("Operacje zlecane sa poprawnie, a tu wyjatek "
					+ e.getMessage());
		}
	}

	@Test
	public void unCheckedTest() {
		createList("A");
		createList("B");
		addItemToList("A1", "A");
		addItemToList("A2", "A");
		try {

			assertFalse("Wszystkie nowotworzone pozycje sa UNCHECKED",
					todo.allChecked("A"));

			todo.checkItem(todo.getUniqItemId("A1", "A"));
			todo.checkItem(todo.getUniqItemId("A2", "A"));

			assertTrue("Wszystkie pozycje ustawione na CHECKED",
					todo.allChecked("A"));

			todo.uncheckItem(todo.getUniqItemId("A2", "A"));

			assertFalse("Nie wszystkie pozycje ustawione na CHECKED",
					todo.allChecked("A"));
		} catch (Exception e) {
			fail("Operacje zlecane sa poprawnie, a tu wyjatek "
					+ e.getMessage());
		}
	}

	@Test(timeout = 1000)
	public void loopTest() {
		createList("A");
		createList("B");
		addItemToList("A1", "A");
		addItemToList("B1", "B");
		try {
			todo.connectListToList("A", "B");
			todo.connectListToList("B", "A");
			java.util.List<java.lang.String> result = todo.getItems("B");
			assertEquals("Powinny byc dwie pozycje na liscie getItems", 2,
					result.size());
			result = todo.getItems("B");
			assertEquals("Powinny byc dwie pozycje na liscie getItems", 2,
					result.size());

		} catch (Exception e) {
			fail("Operacje zlecane sa poprawnie, a tu wyjatek "
					+ e.getMessage());
		}
	}
}