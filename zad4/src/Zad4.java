import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Zad4
{

    /**
     * @param args the command line arguments
     * @throws java.lang.ClassNotFoundException
     */
	public static void main(String[] args) 
    {

		Result result = JUnitCore.runClasses(OtelloTester.class);

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
