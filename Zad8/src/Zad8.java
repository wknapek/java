/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author raven
 */
/*public class Zad8
{

   
    public static void main(String[] args)
    {
        SQLiteHelper sqltest = new SQLiteHelper();
        testclass test = new testclass();
        sqltest.createTable(test);
        sqltest.insert(test);
    }
    
}*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Zad8 {
	
	private static Connection con;
	private static Statement statement;
	
	private static void registerDriverAndConnect() {
		try {
			Class.forName( "org.sqlite.JDBC" );		
			con = DriverManager.getConnection( "jdbc:sqlite:/tmp/sample.db" );
		} 
		catch ( ClassNotFoundException | SQLException e ) {
			e.printStackTrace();
		}
	}
	
	private static void exec( String SQL) throws SQLException {
		System.out.println(SQL + " -> " + statement.executeUpdate(SQL));
	}
	
	private static int showResult(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		
		System.out.println( "\n\nLiczba kolumn w tabeli " +
				rsmd.getTableName(1) + " to " + + rsmd.getColumnCount() );
		for ( int i = 1; i <= rsmd.getColumnCount(); i++ ) {
			System.out.println( " + Kolumna " + i + " label " + rsmd.getColumnLabel( i ) 
					+ ", typ " + rsmd.getColumnType( i ) + " " + rsmd.getColumnTypeName( i )
					 );
		}
		
		return rsmd.getColumnCount();
	}
	
	public static void main(String[] args) throws SQLException {
		registerDriverAndConnect();

		SQLiteHelperInterface shi = new SQLiteHelper();
        
		Klasa1 k1 = new Klasa1();

		k1.boolean1 = true;
		k1.boolean2 = false;
		k1.double1 = 1.0;
		k1.double2 = 2.0;
		k1.float1 = 1.0f;
		k1.float2 = 2.0f;
		k1.int1 = 1;
		k1.int2 = 2;
		k1.long1 = 1;
		k1.long2 = 2L;
		k1.string = "Ala ma kota oczywiscie";

		Klasa2 k2 = new Klasa2();

		k2.booleanZle = true;
		k2.byteZle = (byte) 1;
		k2.longZle = 2L;
		k2.shortZle = (short) 3;
		
		k2.doubleJedynyDobry = 123.0;

		String createT1 = shi.createTable(k1);
		String insertT1 = shi.insert(k1);
		String createT2 = shi.createTable(k2);
		String insertT2 = shi.insert(k2);
        
		statement = con.createStatement();
        statement.executeUpdate("DROP TABLE IF EXISTS Klasa1;");
        statement.executeUpdate("DROP TABLE IF EXISTS Klasa2;");
		System.out.println("K1 create table\n " + createT1 );
		exec( createT1 );
		System.out.println("K1 insert table\n " + insertT1 );
		exec( insertT1 );
		ResultSet rsT1 = statement
				.executeQuery("SELECT * FROM Klasa1");
		int cols = showResult(rsT1);
		boolean ok = true;
		if ( cols != 11 ) {
			System.out.println( "Blad: w Klasa1 powinno byc 11 kolumn, a jest " + cols );
			ok = false;
		}
		
		statement = con.createStatement();
		System.out.println("K2 create table\n " + createT2 );
		exec( createT2 );
		System.out.println("K2 insert table\n " + insertT2 );
		exec( insertT2 );
		
		ResultSet rsT2 = statement
				.executeQuery("SELECT * FROM Klasa2");
		cols = showResult( rsT2 );
		if ( cols != 1 ) {
			System.out.println( "Blad: w Klasa2 powinna byc tylko jedna kolumna, a jest " + cols);
			ok = false;
		} 
		
		System.out.println( "\n\n>>>>>>>> Wynik bardzo prostego testu " + ( ok ? "pozytywny" : "negatywny"));
		
	}
}