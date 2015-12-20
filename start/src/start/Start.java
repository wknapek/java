/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package start;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


/**
 *
 * @author raven
 */
public
        class Start
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Result result = JUnitCore.runClasses(FillTester.class);
        
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
        /*int[][] img = new int[5][5];
		boolean[][] ngbrs = new boolean[3][3];
		ngbrs[0][1] = true;
		ngbrs[2][1] = true;
        FillBaseImplementation test = new FillBaseImplementation();
        test.fill(img, ngbrs, new int[] {}, 1, 2, 2);*/
        //int[][] img = new int[8][8];
		//boolean[][] ngbrs = new boolean[][] { { false, true, false },
		//		{ true, true, true }, { false, true, false } };
        //FillBaseImplementation test = new FillBaseImplementation();
		//test.fill(img, ngbrs, new int[] {}, 1, 4, 4);
		//allFilled(img, 1);
		//runFill(img, ngbrs, new int[] {}, 2, 0, 0);
		//allFilled(img, 2);
		//runFill(img, ngbrs, new int[] {}, 3, 7, 7);
		//allFilled(img, 3);
        /*int[][] img = new int[6][6];
		boolean[][] ngbrs = new boolean[3][3];
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				ngbrs[i][j] = true;

		int c = 1;

		img[3][2] = c;
		img[3][3] = c;
		img[4][1] = c;
		img[5][1] = c;
		img[4][4] = c;
		img[5][4] = c;
        FillBaseImplementation test = new FillBaseImplementation();
		test.fill(img, ngbrs, new int[] { c }, c, 1, 2);*/
        /*		int[][] img = new int[6][6];
		boolean[][] ngbrs = new boolean[3][3];
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				ngbrs[i][j] = true;

		ngbrs[0][0] = false;
		ngbrs[2][2] = false;
		ngbrs[2][0] = false;
		ngbrs[0][2] = false;

		int c = 1;

		img[3][2] = c;
		img[3][3] = c;
		img[4][1] = c;
		img[5][1] = c;
		img[4][4] = c;
		img[5][4] = c;
        FillBaseImplementation test = new FillBaseImplementation();

		test.fill(img, ngbrs, new int[] { c }, c, 1, 2);*/
	}
}
    
