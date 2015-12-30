
import java.util.ArrayList;


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
       game test = new game();
       test.setGameState(testTab, ReversiBoardInterface.Disk.WHITE);
       boolean dupa = test.canWeContinueTheGame();
       int testwynik = test.getResult(ReversiBoardInterface.Disk.WHITE);
       ReversiBoardInterface.Disk testplayer = test.nextPlayer();
       ReversiBoardInterface.Position testpos = new ReversiBoardInterface.Position(6,4);
       testwynik = test.move(testpos);
    }
}

    
