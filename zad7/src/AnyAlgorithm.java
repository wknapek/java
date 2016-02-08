
import java.util.LinkedList;
import java.util.function.BooleanSupplier;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author raven
 */
public class AnyAlgorithm implements AnyAlgorithmInterface
{
    private ExecutionListInterface myList;
    public static class singleRunnable implements ExecutableInterface
    {
        Runnable single;
        public singleRunnable(Runnable run)
        {
            single = run;
        }

        singleRunnable()
        {
            
        }
        public void setRunnable(Runnable run)
        {
            single = run;
        }
        @Override
        public
        void start()
        {
            single.run();
        }
        
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static class Fork implements ForkInterface
    {
        BooleanSupplier Condition;
        ExecutionListInterface ifTrue;
        ExecutionListInterface ifFalse;
        @Override
        public
        void set(BooleanSupplier forkCondition)
        {
            Condition = forkCondition;
        }

        @Override
        public
        void setTrueBranch(ExecutionListInterface list)
        {
            ifTrue = list;
        }

        @Override
        public
        void setFalseBranch(ExecutionListInterface list)
        {
            ifFalse = list;
        }

        @Override
        public
        void start()
        {
            if(Condition.getAsBoolean())
            {
                ifTrue.start();
            }
            else
            {
                ifFalse.start();
            }
        }
        
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static class Loop implements LoopInterface
    {
        Boolean dowhile;
        BooleanSupplier Condition;
        ExecutableInterface tasks;
        @Override
        public
        void set(ExecutionListInterface list)
        {
            tasks = list;
        }

        @Override
        public
        void set(BooleanSupplier continuationCondition, boolean preFlag)
        {
            dowhile = preFlag;
            Condition = continuationCondition;
        }

        @Override
        public
        void start()
        {
            if(dowhile)
            {
                while(Condition.getAsBoolean())
                {
                    tasks.start();
                }
            }
            else
            {
                do
                {
                    tasks.start();
                }
                while(Condition.getAsBoolean());
            }
        }
        
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static class ExecutionList implements ExecutionListInterface
    {
        LinkedList<ExecutableInterface> opList;
        public ExecutionList()
        {
            opList = new LinkedList<>();
        }
        @Override
        public
        void add(Runnable run)
        {
            opList.add(new singleRunnable(run));
        }

        @Override
        public
        void add(ExecutionListInterface list)
        {
            opList.add(list);
        }

        @Override
        public
        void add(LoopInterface loop)
        {
            opList.add(loop);
        }

        @Override
        public
        void add(ForkInterface fork)
        {
            opList.add(fork);
        }

        @Override
        public
        void start()
        {
            for(int i = 0; i < opList.size();i++)
            {
                opList.get(i).start();
            }
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public
    void setList(ExecutionListInterface list)
    {
        myList = list;
    }

    @Override
    public
    void start()
    {
        myList.start();
    }
    
}
