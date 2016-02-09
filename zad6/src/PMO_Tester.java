import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class PMO_Tester {
	private ReversiBoardInterfaceExt rbi;
	private PMO_OtelloBordGenerator generator;
	private ReversiBoardInterface.Disk BLACK = ReversiBoardInterface.Disk.BLACK;
	private ReversiBoardInterface.Disk WHITE = ReversiBoardInterface.Disk.WHITE;

	@Before
	public void ini() {
		rbi = new ReversiBoardExt();
		generator = new PMO_OtelloBordGenerator();
	}

	private void generateSimpleState() {
		generator.setWhite(3, 2);
		generator.setWhite(2, 3);
		generator.setWhite(1, 4);
		generator.setWhite(3, 4);
		
		generator.setBlack( 3, 3 );
		generator.setBlack( 4, 4 );
		generator.setBlack( 5, 3 );
		generator.setBlack( 4, 3 );
	}

	@Test
	public void test1() {
		generateSimpleState();
		rbi.setGameState( generator.getBoard(), BLACK );
		
		try {
			rbi.move( new ReversiBoardInterface.Position( 1, 3 ) );
			ReversiBoardInterface.Disk[][] result = rbi.getBoard();
			
			// sprawdzenie poprawnosci stanu po wykonanym ruchu
			assertEquals( "Na pozycji 1,3 powinien byc czarny", BLACK, result[1][3] );
			assertEquals( "Na pozycji 2,3 powinien byc czarny", BLACK, result[2][3] );
			assertEquals( "Po ruchu czarnych jest ruch bialych", WHITE,  rbi.nextPlayer() );
			
			rbi.undo();
			
			result = rbi.getBoard();
			assertNull( "Na pozycji 1,3 powinien byc null", result[1][3] );
			assertEquals( "Na pozycji 2,3 powinien byc bialy", WHITE, result[2][3] );
			assertEquals( "Powracamy do ruchu czarnych", BLACK,  rbi.nextPlayer() );
			
			rbi.move( new ReversiBoardInterface.Position( 1, 3 ) ); // ponawiamy ruch
			result = rbi.getBoard();
			assertEquals( "Na pozycji 1,3 powinien byc ponownie czarny", BLACK, result[1][3] );
			assertEquals( "Na pozycji 2,3 powinien byc ponownie czarny", BLACK, result[2][3] );
			assertEquals( "Po ruchu czarnych jest ruch bialych", WHITE,  rbi.nextPlayer() );
			
		} catch (Exception e) {
			e.printStackTrace();
			fail( "Operacje prawidlowe - nie powinno byc wyjatku " + e.getMessage() );
		}
	}

	@Test
	public void test1R() {
		generateSimpleState();
		rbi.setGameState( generator.getBoard(), BLACK );
		
		try {
			rbi.move( new ReversiBoardInterface.Position( 1, 3 ) );
			ReversiBoardInterface.Disk[][] result = rbi.getBoard();
			
			assertEquals( "Na pozycji 1,3 powinien byc czarny", BLACK, result[1][3] );
			assertEquals( "Na pozycji 2,3 powinien byc czarny", BLACK, result[2][3] );
			assertEquals( "Po ruchu czarnych jest ruch bialych", WHITE,  rbi.nextPlayer() );
			
			rbi.undo();			
			rbi.redo();
			
			result = rbi.getBoard();
			assertEquals( "Na pozycji 1,3 powinien byc czarny", BLACK, result[1][3] );
			assertEquals( "Na pozycji 2,3 powinien byc czarny", BLACK, result[2][3] );
			assertEquals( "Po ruchu czarnych jest ruch bialych", WHITE,  rbi.nextPlayer() );
			
		} catch (Exception e) {
			e.printStackTrace();
			fail( "Operacje prawidlowe - nie powinno byc wyjatku " + e.getMessage() );
		}
	}
	
	
	private void testBoardState( ReversiBoardInterface.Disk[][] result, int i1, int i2, ReversiBoardInterface.Disk expected ) {
		assertEquals( "Blad na planszy na pozycji " + i1 + "," + i2, expected, result[i1][i2] );
	}
	
	private void startTests( List<ReversiBoardInterface.Position> positions, ReversiBoardInterface.Disk expected ) {
		ReversiBoardInterface.Disk[][] result = rbi.getBoard();
		for ( ReversiBoardInterface.Position position : positions ) 
			testBoardState( result, position.getIndex1(), position.getIndex2(), expected);
	}
	
	@Test
	public void test3() {
		generateSimpleState();
		rbi.setGameState( generator.getBoard(), BLACK );
		
		try {
			rbi.move( new ReversiBoardInterface.Position( 1, 3 ) );
			rbi.move( new ReversiBoardInterface.Position( 5, 4 ) );
			rbi.move( new ReversiBoardInterface.Position( 3, 5 ) );

			testsFor3Moves();
			
			rbi.undo();			
			rbi.undo();			
			rbi.undo();			

			startTests( new ArrayList<ReversiBoardInterface.Position>() {{
				add( new ReversiBoardInterface.Position(3, 3));
				add( new ReversiBoardInterface.Position(4, 3));
				add( new ReversiBoardInterface.Position(5, 3));
				add( new ReversiBoardInterface.Position(4, 4)); }}, BLACK );
			
			startTests( new ArrayList<ReversiBoardInterface.Position>() {{
				add( new ReversiBoardInterface.Position(1, 4));
				add( new ReversiBoardInterface.Position(2, 3));
				add( new ReversiBoardInterface.Position(3, 2));
				add( new ReversiBoardInterface.Position(3, 4)); }}, WHITE );
			
			startTests( new ArrayList<ReversiBoardInterface.Position>() {{
				add( new ReversiBoardInterface.Position(1, 3));
				add( new ReversiBoardInterface.Position(5, 4));
				add( new ReversiBoardInterface.Position(3, 5)); }}, null );			
			
			assertEquals( "Kolejny gracz to czarny ", BLACK, rbi.nextPlayer() );
			assertEquals( "Remis 4:4, a wynik bialych", 4, rbi.getResult( WHITE ));
			assertEquals( "Remis 4:4, a wynik czarnych", 4, rbi.getResult( BLACK ));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail( "Operacje prawidlowe - nie powinno byc wyjatku " + e.getMessage() );
		}
	}
	
	
	private void testsFor3Moves() throws ReversiBoardInterface.CanNotContinue {
		startTests( new ArrayList<ReversiBoardInterface.Position>() {{
			add( new ReversiBoardInterface.Position(1, 3));
			add( new ReversiBoardInterface.Position(2, 3));
			add( new ReversiBoardInterface.Position(3, 3));
			add( new ReversiBoardInterface.Position(5, 3));
			add( new ReversiBoardInterface.Position(2, 3));
			add( new ReversiBoardInterface.Position(3, 4));
			add( new ReversiBoardInterface.Position(3, 5));
		}}, BLACK );

		startTests( new ArrayList<ReversiBoardInterface.Position>() {{
			add( new ReversiBoardInterface.Position(1, 4));
			add( new ReversiBoardInterface.Position(3, 2));
			add( new ReversiBoardInterface.Position(4, 3));
			add( new ReversiBoardInterface.Position(5, 4));
		}}, WHITE );			
		
		assertEquals( "Kolejny gracz to bialy ", WHITE, rbi.nextPlayer() );
		assertEquals( "Czarne prowadza 7:4", 7, rbi.getResult( BLACK ));
		assertEquals( "Biale przegrywaja 4:7", 4, rbi.getResult( WHITE ));		
	}
	
	@Test
	public void test3R() {
		generateSimpleState();
		rbi.setGameState( generator.getBoard(), BLACK );
		
		try {
			rbi.move( new ReversiBoardInterface.Position( 1, 3 ) );
			rbi.move( new ReversiBoardInterface.Position( 5, 4 ) );
			rbi.move( new ReversiBoardInterface.Position( 3, 5 ) );

			testsFor3Moves();
			
			rbi.undo();			
			rbi.undo();			
			rbi.undo();			
			rbi.redo();
			rbi.redo();
			rbi.redo();

			testsFor3Moves();
		} catch (Exception e) {
			e.printStackTrace();
			fail( "Operacje prawidlowe - nie powinno byc wyjatku " + e.getMessage() );
		}
	}

	@Test
	public void testExceptions() {
		generateSimpleState();
		rbi.setGameState( generator.getBoard(), BLACK );
		try {
			rbi.move( new ReversiBoardInterface.Position( 1, 3 ) );
			rbi.move( new ReversiBoardInterface.Position( 5, 4 ) );
			rbi.move( new ReversiBoardInterface.Position( 3, 5 ) );

			rbi.undo();			
			rbi.undo();			
			rbi.undo();			
			rbi.undo();			
			fail( "Gracze wykonali 3 posuniecia, potem 4x undo - powinien byc wyjatek!" );
		} catch ( ReversiBoardInterfaceExt.IllegalOperationException e ) {
		}
		catch (Exception e) {
			e.printStackTrace();
			fail( "Nie spodziewano sie tego wyjatku " + e.getMessage() );
		}
		
	}
	
	@Test
	public void testExceptionsR() {
		generateSimpleState();
		rbi.setGameState( generator.getBoard(), BLACK );
		try {
			rbi.move( new ReversiBoardInterface.Position( 1, 3 ) );
			rbi.move( new ReversiBoardInterface.Position( 5, 4 ) );
			rbi.move( new ReversiBoardInterface.Position( 3, 5 ) );

			rbi.undo();			
			rbi.undo();			
			
			rbi.redo();
			rbi.redo();
			rbi.redo();			
			
			fail( "Gracze wykonali 3 posuniecia, potem 2x undo i 3x redo - powinien byc wyjatek!" );
		} catch ( ReversiBoardInterfaceExt.IllegalOperationException e ) {
		}
		catch (Exception e) {
			e.printStackTrace();
			fail( "Nie spodziewano sie tego wyjatku " + e.getMessage() );
		}
		
	}
	
	@Test
	public void testExceptionsR2() {
		generateSimpleState();
		rbi.setGameState( generator.getBoard(), BLACK );
		try {
			rbi.move( new ReversiBoardInterface.Position( 1, 3 ) );
			rbi.move( new ReversiBoardInterface.Position( 5, 4 ) );
			rbi.move( new ReversiBoardInterface.Position( 3, 5 ) );

			rbi.undo();			
			rbi.undo();			
			rbi.undo();			
			
			rbi.redo();
			
			assertEquals( "Graja czarne,biale,czarne potem 3x undo i 1x redo, powinien grac bialy", WHITE, rbi.nextPlayer() );
			rbi.move( new ReversiBoardInterface.Position( 5, 2 ) ); // zmiania histrii
			
			rbi.redo();			
			fail( "Gracze wykonal inne posuniecie niz zapisano w historii, a redo nadal dziala." );
		} catch ( ReversiBoardInterfaceExt.IllegalOperationException e ) {
		}
		catch (Exception e) {
			e.printStackTrace();
			fail( "Nie spodziewano sie tego wyjatku " + e.getMessage() );
		}
		
	}
}