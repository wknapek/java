import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/*
zad 5 ver1
 */
/**
 *
 * @author raven
 */

class IDCreator 
{
   private static IDCreator instance = null;
   private static int IDcounter = 0;
   protected IDCreator() 
   {
      // Exists only to defeat instantiation.
   }
   public static IDCreator getInstance() 
   {
      if(instance == null) {
         instance = new IDCreator();
      }
      return instance;
   }
   public int getID()
   {
       return IDcounter++;
   }
}


public class ToDoLists implements ToDoListsInterface
{
    private Map<String ,List<listElem>> myMap = new TreeMap<>();
    private static int counter = 0;
    private IDCreator creatorID = IDCreator.getInstance();
    static synchronized int getID()
    {
        return counter++;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    class listElem
    {
        String      Item;
        int         Iditem;
        ItemState   State;

        listElem()
        {
            this.Iditem = creatorID.getID();//getID();
            this.State = ItemState.UNCHECKED;
        }
        listElem(String item)
        {
            this.Item = item;
            this.Iditem = creatorID.getID();//getID();
            this.State = ItemState.UNCHECKED;
        }
    }
    @Override
    public void createToDoList(String name) throws AlreadyExistsException
    {
        if(myMap.containsKey(name))
        {
            throw new AlreadyExistsException();
        }
        myMap.put(name, new ArrayList<listElem>());
    }

    @Override
    public void addItemToList(String itemName, String listName) throws AlreadyExistsException, DoesNotExistException
    {
        if(!myMap.containsKey(listName))
        {
            throw new DoesNotExistException();
        }
        List<listElem> myList = myMap.get(listName);
        if(myList!=null)
        {
            for(int i = 0; i <myList.size();i++)
            {
                if(myList.get(i).Item.equals(itemName))
                {
                    throw new AlreadyExistsException();
                }
            }
        }
        myList.add(new listElem(itemName));
        myMap.replace(listName, myList);
    }

    @Override
    public Set<String> getLists()
    {
        Set<String> mySet = new TreeSet<>();
        myMap.keySet().stream().forEach((key) ->
        {
            mySet.add(key);
        });
        return mySet;
    }

    @Override
    public List<String> getItems(String listName) throws DoesNotExistException
    {
        List<String> myList = new ArrayList<>();
        List<listElem> myItems = new ArrayList<>();
        myItems = myMap.get(listName);
        if(myItems==null)
        {
            throw new DoesNotExistException();
        }
        for(int i = 0; i < myItems.size(); i++)
        {
            myList.add(myItems.get(i).Item);
        }
        return myList;
    }

    @Override
    public Integer getUniqItemId(String itemName, String listName) throws DoesNotExistException
    {
        int Id = -1;
        List<listElem> myList = new ArrayList<>();
        myList = myMap.get(listName);
        if(myList == null)
        {
            throw new DoesNotExistException();
        }
        for(int i = 0; i < myList.size();i++)
        {
            if(myList.get(i).Item == itemName)
            {
                Id = myList.get(i).Iditem;
            }
        }
        if(Id == -1)
        {
            throw new DoesNotExistException();
        }
        return Id;
    }

    @Override 
    public ItemState getItemState(Integer itemID) throws DoesNotExistException
    {
        ItemState tmp = null;
        List<listElem> mylist;
        for(Map.Entry<String, List<listElem>> entry : myMap.entrySet())
        {
            //Entry myEntry = (Entry) iterator.next();
            mylist = entry.getValue();//(List<listElem>) myEntry.getValue();
            for(int i =0 ;i < mylist.size();i++)
            {
                if(mylist.get(i).Iditem == itemID)
                {
                    tmp = mylist.get(i).State;
                }
            }
        }
        if(tmp == null)
        {
            throw new DoesNotExistException();
        }
        return tmp;
    }

    @Override
    public void checkItem(Integer itemID) throws DoesNotExistException
    {
        boolean gooID = false;
        List<listElem> mylist;
        for(Map.Entry<String, List<listElem>> entry : myMap.entrySet())
        {
            mylist = entry.getValue();
            for(int i =0 ;i < mylist.size();i++)
            {
                if(mylist.get(i).Iditem == itemID)
                {
                    mylist.get(i).State = ItemState.CHECKED;
                    gooID = true;
                }
            }
        }
        if(!gooID)
        {
            throw new DoesNotExistException();
        }
    }
    
}
