/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author raven
 */
public class Zad8
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        SQLiteHelper sqltest = new SQLiteHelper();
        testclass test = new testclass();
        sqltest.createTable(test);
        sqltest.insert(test);
    }
    
}
