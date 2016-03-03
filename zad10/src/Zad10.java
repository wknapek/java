
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * zad 10 ver 1
 */
public class Zad10
{
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException
    {
        registerDriverAndConnect();
        SQLiteHelperExt test = new SQLiteHelperExt();
        Klasa1 k1 = new Klasa1();

        //statement.executeQuery("DROP TABLE Klasa1");
        //statement.executeQuery("DROP TABLE Klasa2");
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
        
        Klasa1 k3 = new Klasa1();
        k3.boolean1 = false;
		k3.boolean2 = true;
		k3.double1 = 2.0;
		k3.double2 = 3.0;
		k3.float1 = 4.0f;
		k3.float2 = 5.0f;
		k3.int1 = 6;
		k3.int2 = 7;
		k3.long1 = 8;
		k3.long2 = 9L;
		k3.string = "kAla ma kota oczywiscie";

		Klasa2 k2 = new Klasa2();

		k2.booleanZle = true;
		k2.byteZle = (byte) 1;
		k2.longZle = 2L;
		k2.shortZle = (short) 3;
		
		k2.doubleJedynyDobry = 123.0;
        String createT1 = test.createTable(k1);
        String createT3 = test.createTable(k3);
        String insertT3 = test.insert(k3);
		String insertT1 = test.insert(k1);
		String createT2 = test.createTable(k2);
		String insertT2 = test.insert(k2);
        statement = con.createStatement();
        exec( createT1 );
        exec( insertT1 );
        exec( createT3 );
        exec( insertT3 );
        test.select(con, "Klasa1");
    }
    
}
