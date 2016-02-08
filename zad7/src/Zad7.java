
import java.util.concurrent.locks.Condition;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author raven
 */
public class Zad7
{
    
    public static void main(String[] args)
    {
        runTest rTest = new runTest();
        rTest.setoutput("rTest");
        runTest rTest3 = new runTest();
        rTest3.setoutput("rTest3");
        runTest rTest1T = new runTest();
        rTest1T.setoutput("rTest1T");
        runTest rTest1F = new runTest();
        rTest1F.setoutput("rTest1F");
        runTest rTest2T = new runTest();
        rTest2T.setoutput("rTest2T");
        runTest rTest2F = new runTest();
        rTest2F.setoutput("rTest2F");
        AnyAlgorithm.Fork forkTest = new AnyAlgorithm.Fork();
        ConditionTest Ctest = new ConditionTest();
        AnyAlgorithm.Loop loopTest = new AnyAlgorithm.Loop();
        AnyAlgorithm.ExecutionList listTest = new AnyAlgorithm.ExecutionList();
        AnyAlgorithm.ExecutionList listTest2 = new AnyAlgorithm.ExecutionList();
        listTest.add(rTest1F);
        listTest2.add(rTest1T);
        Ctest.setCond(true);
        forkTest.set(Ctest);
        forkTest.setFalseBranch(listTest);
        forkTest.setTrueBranch(listTest2);
        //forkTest.start();
        Ctest.setCond(false);
        loopTest.set(Ctest, false);
        loopTest.set(listTest);
        loopTest.start();
        //listTest.start();
    }
    
}
