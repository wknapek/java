
public class PMO_OtelloBordGenerator {
	private ReversiBoardInterface.Disk board[][] = new ReversiBoardInterface.Disk[8][8];
	
	void setBlack( int idx1, int idx2 ) {
		board[ idx1 ][ idx2 ] = ReversiBoardInterface.Disk.BLACK;
	}
	
	void setBlack( ReversiBoardInterface.Position pos ) {
		setBlack( pos.getIndex1(), pos.getIndex2() );
	}
	
	void setWhite( int idx1, int idx2 ) {
		board[ idx1 ][ idx2 ] = ReversiBoardInterface.Disk.WHITE;
	}
	
	void setWhite( ReversiBoardInterface.Position pos ) {
		setWhite( pos.getIndex1(), pos.getIndex2() );
	}
	
	boolean isEmpty( ReversiBoardInterface.Position pos ) {
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
		if ( board[ idx1 ][ idx2 ] == ReversiBoardInterface.Disk.BLACK ) return " *";
		else return " O";
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		PMO_OtelloBordGenerator obg = new PMO_OtelloBordGenerator();
		
		for ( int i = 0; i < 8; i++ ) 
			for ( int j = 0; j < 8; j++ )
				obg.board[i][j] = board[i][j];
		
		return obg;
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
	
	public ReversiBoardInterface.Disk[][] getBoard() {
		return board;
	}
}