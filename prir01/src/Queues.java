import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author raven
 */
public class Queues implements QueuesInterface
{
    private int numThreds;
    private int[] ququelimits;
    private int avCores;
    private Map<Integer ,LinkedBlockingQueue<TaskInterface>> ListOfQueue = new HashMap<>();

    private class task extends Thread
    {
        public TaskInterface mytask;
        public long time;
        public int iCores;
        public task(TaskInterface t,int cores, long tim)
        {
            mytask = t;
            iCores = cores;
            time = tim;
            execute();
        }
        private void execute()
        {
            mytask.execute(iCores, time);
        }
    }

    @Override
    public
    void configure(int[] limits, int globalLimit)
    {
        numThreds = globalLimit;
        avCores = numThreds;
        ququelimits = limits;
    }

    @Override
    public
    void submitTask(int queue, TaskInterface task)
    {
        if(ListOfQueue.isEmpty())
        {
            ListOfQueue.put(queue, new LinkedBlockingQueue<TaskInterface>());
            LinkedBlockingQueue<TaskInterface> myq = ListOfQueue.get(queue);

            myq.add(task);
            ListOfQueue.replace(queue, myq);
        }
        else
        {
            if(ListOfQueue.containsKey(queue))
            {
                LinkedBlockingQueue<TaskInterface> myq = ListOfQueue.get(queue);
                myq.add(task);
                ListOfQueue.replace(queue, myq);
            }
            else
            {
                LinkedBlockingQueue<TaskInterface> tmp = new LinkedBlockingQueue<>();
                tmp.add(task);
                ListOfQueue.put(queue, tmp);   
            }
        }
    }

    @Override
    public
    int getAvailableCores()
    {
        return 0;
    }

    @Override
    public
    int getAvailableCores(int queue)
    {
        LinkedBlockingQueue<TaskInterface> myq = ListOfQueue.get(queue);
        //myq.element().
        return 0;
    }
    
    private void execute()
    {
        LinkedBlockingQueue<TaskInterface> taskQue;
        int numOfQue = ListOfQueue.size();
        long[] Times = new long[numOfQue];
        List<Thread> threds = new ArrayList<>();
        int i = 0;
        for (Map.Entry<Integer, LinkedBlockingQueue<TaskInterface>> entry : ListOfQueue.entrySet())
        {
            taskQue = entry.getValue();
            if(taskQue.peek().getRequiredCores() < avCores && threds.get(i).isAlive() && )
            {
                Times[i] = taskQue.peek().getRequiredTime();
                avCores -= taskQue.peek().getRequiredCores();
                threds.add(new task(taskQue.peek(), taskQue.peek().getRequiredCores(), taskQue.peek().getRequiredTime()));
            }
            else if()
            {
                
            }
            i++;
        }
    }
    
}
