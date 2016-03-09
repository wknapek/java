
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

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
      if(instance == null) 
      {
         instance = new IDCreator();
      }
      return instance;
   }
   public int getID()
   {
       return IDcounter++;
   }
}

public class ToDoListsExt implements ToDoListsExtInterface
{
    private Map<String ,List<listElem>> myMap = new TreeMap<>();
    private static int counter = 0;
    private IDCreator creatorID = IDCreator.getInstance();
    private connections conect = new connections();
    private List<connection> linkedLists = new LinkedList<>();
    static synchronized int getID()
    {
        return counter++;
    }

    @Override
    public
    void connectListToList(String listNameSrc, String listNameDst) throws ToDoListsInterface.DoesNotExistException
    {
        if(linkedLists.size()==0)
        {
            linkedLists.add(new connection(listNameSrc,listNameDst));
        }
        else
        {
            for(int i=0;i<linkedLists.size();i++)
            {
                if(linkedLists.get(i).connTo == listNameDst)
                {
                    
                }
            }
        }
        List<listElem> Dest = myMap.get(listNameDst);
        List<listElem> Src  = myMap.get(listNameSrc);
        conect.Add(listNameDst, listNameSrc);
        
        if(Dest == null || Src == null)
        {
            throw new ToDoListsInterface.DoesNotExistException();
        }
        for(int i=0; i < Src.size();i++)
        {
            listElem tmp = Src.get(i);
            tmp.LinkedWith = listNameDst;
            Dest.add(tmp);
        }
        myMap.replace(listNameDst, Dest);
        //IgnoreList.add(listNameSrc);
    }

    @Override
    public
    void uncheckItem(Integer itemID) throws ToDoListsInterface.DoesNotExistException
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
                    mylist.get(i).State = ToDoListsInterface.ItemState.UNCHECKED;
                    gooID = true;
                }
            }
        }
        if(!gooID)
        {
            throw new ToDoListsInterface.DoesNotExistException();
        }
    }

    @Override
    public
    boolean allChecked(String listName) throws ToDoListsInterface.DoesNotExistException
    {
        boolean all = false;
        List<listElem> checklist = myMap.get(listName);
        for(int i =0;i<checklist.size();i++)
        {
            if(checklist.get(i).State==ToDoListsInterface.ItemState.CHECKED)
            {
                all &= true;
            }
            else
            {
                all = false;
            }
            if(checklist.get(i).State==ToDoListsInterface.ItemState.CHECKED && all == false)
            {
                all = true;
            }
        }
        return all;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    class listElem
    {
        String      Item;
        int         Iditem;
        ToDoListsInterface.ItemState   State;
        String      FromList;
        String      LinkedWith;

        listElem()
        {
            this.Iditem = creatorID.getID();//getID();
            this.State = ToDoListsInterface.ItemState.UNCHECKED;
        }
        listElem(String item)
        {
            this.Item = item;
            this.Iditem = creatorID.getID();//getID();
            this.State = ToDoListsInterface.ItemState.UNCHECKED;
        }
        listElem(String item, String list)
        {
            this.Item = item;
            this.Iditem = creatorID.getID();//getID();
            this.State = ToDoListsInterface.ItemState.UNCHECKED;
            this.FromList = list;
        }
        listElem(String item, String list, String linked)
        {
            this.Item = item;
            this.Iditem = creatorID.getID();//getID();
            this.State = ToDoListsInterface.ItemState.UNCHECKED;
            this.FromList = list;
            this.LinkedWith = linked;
        }
    }
    
    class connection
    {
        String connFrom;
        String connTo;

        public connection(String from,String to)
        {
            this.connFrom = from;
            this.connTo = to;
        }
    }
    
    class connections
    {
        private Map<String,List<String>> conn;

        public connections()
        {
            conn = new HashMap<>();
        }
        public void Add(String k,String v)
        {
            if(conn.containsKey(k))
            {
                conn.get(k).add(v);
            }
            else
            {
                conn.put(k, new ArrayList<String>());
                conn.get(k).add(v);
            }
        }
        public boolean isConnected(String d,String s)
        {
            boolean ret = false;
            List<String> tmp = conn.get(d);
            if(tmp == null)
            {
                return false;
            }
            for(int i = 0 ;i < tmp.size();i++)
            {
                if(tmp.get(i)==s)
                {
                    ret = true;
                }
            }
            return ret;
        }
        
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void createToDoList(String name) throws ToDoListsInterface.AlreadyExistsException
    {
        if(myMap.containsKey(name))
        {
            throw new ToDoListsInterface.AlreadyExistsException();
        }
        myMap.put(name, new ArrayList<listElem>());
    }

    @Override
    public void addItemToList(String itemName, String listName) throws ToDoListsInterface.AlreadyExistsException, ToDoListsInterface.DoesNotExistException
    {
        if(!myMap.containsKey(listName))
        {
            throw new ToDoListsInterface.DoesNotExistException();
        }
        List<listElem> myList = myMap.get(listName);
        if(myList!=null)
        {
            for(int i = 0; i <myList.size();i++)
            {
                if(myList.get(i).Item.equals(itemName))
                {
                    throw new ToDoListsInterface.AlreadyExistsException();
                }
            }
        }
        myList.add(new listElem(itemName,listName));
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
    public List<String> getItems(String listName) throws ToDoListsInterface.DoesNotExistException
    {
        List<String> myList = new ArrayList<>();
        List<listElem> myItems = new ArrayList<>();
        myItems = myMap.get(listName);
        if(myItems==null)
        {
            throw new ToDoListsInterface.DoesNotExistException();
        }
        for(int i = 0; i < myItems.size(); i++)
        {
            if(conect.isConnected(listName,myItems.get(i).FromList))
            {
                myList.add(myItems.get(i).Item);
            }
            else if(myItems.get(i).FromList == listName)
            {
                myList.add(myItems.get(i).Item);
            }
        }
        return myList;
    }

    @Override
    public Integer getUniqItemId(String itemName, String listName) throws ToDoListsInterface.DoesNotExistException
    {
        int Id = -1;
        List<listElem> myList = new ArrayList<>();
        myList = myMap.get(listName);
        if(myList == null)
        {
            throw new ToDoListsInterface.DoesNotExistException();
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
            throw new ToDoListsInterface.DoesNotExistException();
        }
        return Id;
    }

    @Override 
    public ToDoListsInterface.ItemState getItemState(Integer itemID) throws ToDoListsInterface.DoesNotExistException
    {
        ToDoListsInterface.ItemState tmp = null;
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
            throw new ToDoListsInterface.DoesNotExistException();
        }
        return tmp;
    }

    @Override
    public void checkItem(Integer itemID) throws ToDoListsInterface.DoesNotExistException
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
                    mylist.get(i).State = ToDoListsInterface.ItemState.CHECKED;
                    gooID = true;
                }
            }
        }
        if(!gooID)
        {
            throw new ToDoListsInterface.DoesNotExistException();
        }
    }
    
}
