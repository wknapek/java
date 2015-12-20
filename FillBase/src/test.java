
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
    public static void main(String[] args) 
    {
                OtelloHelperInterface.Disk[][] testTab = 
                           {{null      ,null      ,null      ,null      ,null      ,null      ,null       ,null}, //0
                            {null      ,null      ,null      ,null      ,null      ,null      ,null       ,null}, //1
                            {null      ,null      ,null      ,null      ,null      ,OtelloHelperInterface.Disk.BLACK,null       ,null}, //2
                            {null      ,null      ,null      ,OtelloHelperInterface.Disk.BLACK,OtelloHelperInterface.Disk.BLACK,null      ,null       ,null}, //3
                            {null,      OtelloHelperInterface.Disk.WHITE,OtelloHelperInterface.Disk.WHITE,OtelloHelperInterface.Disk.WHITE,OtelloHelperInterface.Disk.WHITE,null      ,null       ,null}, //4
                            {null,      null,      null,      null      ,OtelloHelperInterface.Disk.WHITE,null      ,null       ,null}, //5
                            {null,      null,      null,      null      ,null      ,null      ,null       ,null}, //6
                            {null,      null,      null,      null      ,null      ,null      ,null       ,null}, //7
                                                                                                               };
                          //  1          2          3          4          5          6          7           8
       reversi rev = new reversi();
        OtelloHelperInterface.Position[] test = rev.analyze(testTab, OtelloHelperInterface.Disk.BLACK);
    }
}

    
