
public class PMO_OtelloBordGenerator {
	private BetterOtelloHelperInterface.Disk board[][] = new BetterOtelloHelperInterface.Disk[8][8];
	private BetterOtelloHelperInterface.Disk WHITE = BetterOtelloHelperInterface.Disk.WHITE;
	private BetterOtelloHelperInterface.Disk BLACK = BetterOtelloHelperInterface.Disk.BLACK;
	
	void setBlack( int idx1, int idx2 ) {
		board[ idx1 ][ idx2 ] = BLACK;
	}
	
	void setBlack( BetterOtelloHelperInterface.Position pos ) {
		setBlack( pos.getIndex1(), pos.getIndex2() );
	}
	
	void setWhite( int idx1, int idx2 ) {
		board[ idx1 ][ idx2 ] = WHITE;
	}
	
	void setWhite( BetterOtelloHelperInterface.Position pos ) {
		setWhite( pos.getIndex1(), pos.getIndex2() );
	}
	
	void setEmpty( int idx1, int idx2 ) {
		board[ idx1 ][ idx2 ] = null;
	}
	
	void setEmpty( BetterOtelloHelperInterface.Position pos ) {
		setEmpty( pos.getIndex1(), pos.getIndex2() );
	}
	
	void fill( BetterOtelloHelperInterface.Disk disk ) {
		for ( int i = 0; i < 8; i++ )
			for ( int j = 0; j < 8; j++ )
				board[ i ][ j ] = disk;
	}
	
	boolean isEmpty( BetterOtelloHelperInterface.Position pos ) {
		return ( board[ pos.getIndex1()][ pos.getIndex2()] == null );
	}
	
	public PMO_OtelloBordGenerator() {
		setBlack( 4, 4 );
		setBlack( 3, 3 );
		setWhite( 4, 3 );
		setWhite( 3, 4 );
	}
	
	private String getDisk( int idx1, int idx2 ) {
		if ( board[ idx1 ][ idx2 ] == null ) return " .";
		if ( board[ idx1 ][ idx2 ] == BLACK ) return " *";
		else return " O";
	}
	
	@Override
	public String toString() {
		String s = "";
		for ( int idx2 = 7; idx2 >= 0; idx2 -- ) {
			for ( int idx1 = 0; idx1 < 8; idx1 ++ ) {
				s += getDisk( idx1, idx2 );
			}
			s += "\n";
		}
		return s;
	}
	
	public static void main(String[] args) {
		new PMO_OtelloBordGenerator();
	}
	
	public BetterOtelloHelperInterface.Disk[][] getBoard() {
		return board;
	}
}