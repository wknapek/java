/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author raven
 */
public class runTest implements Runnable
{
    String output;
        public runTest()
        {
            output = "pusto";
        }
        public void setoutput(String nap)
        {
            output = nap;
        }
        @Override
        public void run()
        {
            System.out.println(output);
        }
        

}
