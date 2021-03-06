import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author raven
 */

public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ReversiBoardInterface.CanNotContinue, ReversiBoardInterface.IllegalMove 
    {
                ReversiBoardInterface.Disk[][] testTab = 
                           {{null      ,null      ,null      ,null      ,null      ,null      ,null       ,null}, //0
                            {null      ,null      ,null      ,null      ,null      ,null      ,null       ,null}, //1
                            {null      ,null      ,null      ,null      ,null      ,ReversiBoardInterface.Disk.BLACK,null       ,null}, //2
                            {null      ,null      ,null      ,ReversiBoardInterface.Disk.BLACK,ReversiBoardInterface.Disk.BLACK,null      ,null       ,null}, //3
                            {null,      ReversiBoardInterface.Disk.WHITE,ReversiBoardInterface.Disk.WHITE,ReversiBoardInterface.Disk.WHITE,ReversiBoardInterface.Disk.WHITE,null      ,null       ,null}, //4
                            {null,      null,      null,      null      ,ReversiBoardInterface.Disk.WHITE,null      ,null       ,null}, //5
                            {null,      null,      null,      null      ,null      ,null      ,null       ,null}, //6
                            {null,      null,      null,      null      ,null      ,null      ,null       ,null}, //7
                                                                                                               };
                          //  1          2          3          4          5          6          7           8
    ReversiBoardInterface.Disk[][] board = new ReversiBoardInterface.Disk[8][8];
    board[6][3] = ReversiBoardInterface.Disk.BLACK;
    board[4][3] = ReversiBoardInterface.Disk.BLACK;
    board[2][3] = ReversiBoardInterface.Disk.BLACK;
    board[6][5] = ReversiBoardInterface.Disk.BLACK;
    board[2][5] = ReversiBoardInterface.Disk.BLACK;
    board[6][7] = ReversiBoardInterface.Disk.BLACK;
    board[4][7] = ReversiBoardInterface.Disk.BLACK;
    board[2][7] = ReversiBoardInterface.Disk.BLACK;

    board[5][4] = ReversiBoardInterface.Disk.WHITE;
    board[4][4] = ReversiBoardInterface.Disk.WHITE;
    board[3][4] = ReversiBoardInterface.Disk.WHITE;
    board[5][5] = ReversiBoardInterface.Disk.WHITE;
    board[3][5] = ReversiBoardInterface.Disk.WHITE;
    board[5][6] = ReversiBoardInterface.Disk.WHITE;
    board[4][6] = ReversiBoardInterface.Disk.WHITE;
    board[3][6] = ReversiBoardInterface.Disk.WHITE;

    //board[4][5] = ReversiBoardInterface.Disk.BLACK;
		Result result = JUnitCore.runClasses(OtelloTester.class);

		PMO_SystemOutRedirect
				.println("-------------------------------------------");
		for (Failure failure : result.getFailures()) {
			PMO_SystemOutRedirect.println("BLAD: " + failure.toString());
		}

		PMO_SystemOutRedirect
				.println("-------------------------------------------");
		PMO_SystemOutRedirect.println("Liczba wykonanych testow: "
				+ result.getRunCount() + " testow");
		PMO_SystemOutRedirect.println("Nie zaliczono...........: "
				+ result.getFailureCount() + " testow");

		PMO_SystemOutRedirect
				.println("-------------------------------------------");
		if (result.wasSuccessful()) {
			PMO_SystemOutRedirect
					.println("Testy zakonczone calkowitym sukcesem");
		} else {
			PMO_SystemOutRedirect
					.println("Nie wszystkie testy zostaly zaliczone");
		}


    }
}

    
