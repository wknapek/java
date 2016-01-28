public interface ReversiBoardInterfaceExt extends ReversiBoardInterface {
	
	class IllegalOperationException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5411670003525209093L;
		
	}
	
	/**
	 * Realizuje operacje UNDO przywracajac stan planszy do wczesniejszego.
	 * Gra cofa sie o <b>jeden, poprawny ruch jednego gracza</b>. 
	 * 
	 * @throws IllegalOperationException wyjatek zwracany gdy nie mozna sie 
	 * juz cofnac w historii gry - czyli gra dotarla do stanu, ktory zostal
	 * ustawiony za pomoca wywolania metody setGameState
	 */
	void undo() throws IllegalOperationException;

	/**
	 * Realizuje operacje REDO przywracajac kolejny stan planszy. Stan
	 * zmienia sie o jedno, poprawne posuniecie jednego gracza.
	 * 
	 * @throws IllegalOperationException wyjatek zwracany gdy nie mozna sie 
	 * juz isc dalej w historii gry - czyli gra dotarla do ostatniego znanego stanu
	 * rozgrywki.
	 */	
	void redo() throws IllegalOperationException;
}