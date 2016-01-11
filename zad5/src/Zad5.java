
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author raven
 */
public class Zad5
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ToDoListsInterface.AlreadyExistsException, ToDoListsInterface.DoesNotExistException
    {
        ToDoLists tmp = new ToDoLists();
        tmp.createToDoList("dupa");
        tmp.addItemToList("test1", "dupa");
        tmp.addItemToList("test2", "dupa");
        tmp.createToDoList("dupa2");
        Set<String> myset = new TreeSet<>();
        myset = tmp.getLists();
        List<String> myList = new ArrayList<>();
        myList = tmp.getItems("dupa");
        int tmp1;
        tmp1 = tmp.getUniqItemId("test1", "dupa");
    }
    
}
