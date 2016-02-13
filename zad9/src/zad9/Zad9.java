/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zad9;

/**
 *
 * @author raven
 */
public class Zad9
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        boolean test = true;
        for(int i =0; i<10 ;i++)
        {
            test &= true;
            if(i==5)
            {
                test &= false;
            }
        }
        System.out.println(test);
    }
    
}
