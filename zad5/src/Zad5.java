
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.JUnitCore;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author raven
 */
public class Zad5
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ToDoListsInterface.AlreadyExistsException, ToDoListsInterface.DoesNotExistException
    {
		Result result = JUnitCore.runClasses(PMO_ToDoTester.class);

		PMO_SystemOutRedirect.println("-------------------------------------------");
		for (Failure failure : result.getFailures()) {
			PMO_SystemOutRedirect.println("BLAD: " + failure.toString());
		}

		PMO_SystemOutRedirect.println("-------------------------------------------");
		PMO_SystemOutRedirect.println("Wykonano      : " + result.getRunCount()
				+ " testow");
		PMO_SystemOutRedirect.println("Nie zaliczono : " + result.getFailureCount()
				+ " testow");

		PMO_SystemOutRedirect.println("-------------------------------------------");
		if (result.wasSuccessful()) {
			PMO_SystemOutRedirect.println("Testy zakonczone calkowitym sukcesem");
		} else {
			PMO_SystemOutRedirect.println("Nie wszystkie testy zostaly zaliczone");
		}
    }
}
