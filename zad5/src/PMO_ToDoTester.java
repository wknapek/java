
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class PMO_ToDoTester {
	private ToDoListsInterface todo;

	@Before
	public void init() {
		todo = new ToDoLists();
	}

	private void createList(String listname) {
		try {
			todo.createToDoList(listname);
		} catch (Exception e) {
			fail("Utworzono liste prawidlowo, a pojawil sie wyjatek");
		}
	}

	@Test
	public void todoListDoubleCreation() {

		createList("List1");

		try {
			todo.createToDoList("List1");
		} catch (ToDoListsInterface.AlreadyExistsException e) {
			return;
		}

		fail("Zlecono utworzenie listy o istniejacej juz nazwie, nie bylo wyjatku");
	}

	@Test
	public void todoNoListTest() {
		Set<String> emptyList = todo.getLists();

		assertNotNull(
				"Nie ma zadnej listy, ale to nie powod do uzywania null!",
				emptyList);
		assertEquals("Nie utworzono zadnej listy, rozmiar powinien wynosic 0",
				0, emptyList.size());
	}

	@Test
	public void getListTest() {
		List<String> lists = new ArrayList<String>() {
			{
				add("L1");
				add("L2");
				add("L3");
			}
		};

		for (String s : lists)
			createList(s);

		Set<String> result = todo.getLists();

		for (String s : lists)
			if (!result.contains(s))
				fail("Wsrod zwroconych przez getLists() nie ma " + s);
	}

	@Test
	public void getItemsEmptyTest() {
		createList("A1");
		createList("A2");

		try {
			List<String> result = todo.getItems("A1");
			assertNotNull("getItems() zwraca null");
			assertEquals("Nie dodano pozycji na liscie", 0, result.size());
		} catch (ToDoListsInterface.DoesNotExistException e) {
			fail("Pojawil sie wyjatek po getItems na utworzonej liscie");
		}
	}

	@Test
	public void getItemsEmptyTest2() {
		try {
			todo.getItems("Ala ma kota");
			fail("Wykonano getItems dla listy, ktorej nie ma, powinien pojawic sie wyjatek");
		} catch (ToDoListsInterface.DoesNotExistException e) {
		} catch (Exception e) {
			fail("Wykonano getItems dla listy, ktorej nie ma, pojawil sie nieoczekiwany wyjatek "
					+ e.getMessage());
		}
	}

	private void addItemToListOK(String itemName, String listName) {
		try {
			todo.addItemToList(itemName, listName);
		} catch (Exception e) {
			fail("Dodano prawidlowa pozycje do istniejacej listy, a mam wyjatek");
		}
	}

	@Test
	public void getItemsTest() {
		createList("A1");
		createList("A2");

		List<String> lists = new ArrayList<String>() {
			{
				add("I1");
				add("I2");
				add("I3");
			}
		};

		for (String s : lists)
			addItemToListOK(s, "A1");
		for (String s : lists)
			addItemToListOK(s, "A2");

		List<String> result = null;
		try {
			result = todo.getItems("A1");
		} catch (Exception e) {
			fail("Pobieranie pozycji z istniejacej listy, a zlapano wyjatek "
					+ e.getMessage());
		}

		assertNotNull("Zamiast listy pozycji jest NULL");

		for (String s : lists)
			if (!result.contains(s))
				fail("Wsrod zwroconych przez getItems() nie ma " + s);
	}

	@Test
	public void getItemsExceptionsTest() {

		createList("A1");

		List<String> lists = new ArrayList<String>() {
			{
				add("I1");
				add("I2");
				add("I3");
			}
		};

		for (String s : lists)
			addItemToListOK(s, "A1");

		try {
			todo.addItemToList("I1", "A1");
			fail("Dodano istniejaca pozycje do listy - nie ma wyjatku");
		} catch (ToDoListsInterface.AlreadyExistsException e) {
		} catch (Exception e) {
			fail("Dodano istniejaca pozycje do listy - nieoczekiwany wyjatek "
					+ e.getMessage());
		}

		try {
			todo.addItemToList("I1", "A123");
			fail("Dodano pozycje do nieistniejacej listy - nie ma wyjatku");
		} catch (ToDoListsInterface.DoesNotExistException e) {
		} catch (Exception e) {
			fail("Dodano pozycje do nieistniejacej listy - nieoczekiwany wyjatek "
					+ e.getMessage());
		}
	}

	@Test
	public void getUniqTest() {
		createList("A1");
		createList("A2");

		List<String> lists = new ArrayList<String>() {
			{
				add("I1");
				add("I2");
				add("I3");
			}
		};

		for (String s : lists) {
			addItemToListOK(s, "A1");
			addItemToListOK(s, "A2");
		}

		Set<Integer> ids = new HashSet<>();

		for (String s : lists) {
			try {
				ids.add(todo.getUniqItemId(s, "A1"));
				ids.add(todo.getUniqItemId(s, "A2"));
			} catch (Exception e) {
				fail("W trakcie pobierania prawidlowych danych getUniqItemId zwraca wyjatek "
						+ e.getMessage());
			}
		}

		assertEquals("UniqID: 2 listy po 3 pozycje", 6, ids.size());

		try {
			todo.getUniqItemId("a", "A1");
			fail("getUniqItemId - bledna nazwa pozycji - brak wyjateku ");
		} catch (ToDoListsInterface.DoesNotExistException e) {
		} catch (Exception e) {
			fail("getUniqItemId - bledna nazwa pozycji - zwrocona zly wyjatek "
					+ e.getMessage());
		}

		try {
			todo.getUniqItemId("I1", "A");
			fail("getUniqItemId - bledna nazwa listy - brak wyjateku ");
		} catch (ToDoListsInterface.DoesNotExistException e) {
		} catch (Exception e) {
			fail("getUniqItemId - bledna nazwa listy - zwrocona zly wyjatek "
					+ e.getMessage());
		}
	}

	@Test
	public void getStateAndChangeStateTest() {
		createList("A1");
		addItemToListOK("I1", "A1");

		try {
			Integer id = todo.getUniqItemId("I1", "A1");

			assertNotNull("getUniqItem zwrocilo NULL");

			ToDoListsInterface.ItemState state = todo.getItemState(id);

			assertEquals("Poczatkowy stan pozycji to UNCHECKED",
					ToDoListsInterface.ItemState.UNCHECKED, state);

			todo.checkItem(id);

			state = todo.getItemState( id );
			assertEquals("Zmieniono stan pozycji do CHECKED",
					ToDoListsInterface.ItemState.CHECKED, state);
		} catch (Exception e) {
			fail("W trakcie poprawnych operacji pojawil sie wyjatek "
					+ e.getMessage());
		}
	}

	@Test
	public void getStateExceptionTest() {
		try {
			todo.getItemState(1243);
			fail("getItemState dla blednej pozycji - brak wyjatku");
		} catch (ToDoListsInterface.DoesNotExistException ex) {
		} catch (Exception e) {
			fail("getItemState dla blednej pozycji - zly wyjatek "
					+ e.getMessage());
		}
	}

	@Test
	public void getChangeStateTestExceptions() {
		try {
			todo.checkItem(1243);
			fail("checkItem dla blednej pozycji - brak wyjatku");
		} catch (ToDoListsInterface.DoesNotExistException ex) {
		} catch (Exception e) {
			fail("checkItem dla blednej pozycji - zly wyjatek "
					+ e.getMessage());
		}
	}

}