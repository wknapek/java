import static java.lang.Compiler.command;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
zad 8 ver 1
 */
/**
 *
 * @author raven
 */
public class SQLiteHelper implements SQLiteHelperInterface
{
    String ClassName = null;
    class cell
    {
        String name;
        Class<?> type;
        public cell(String n,Class<?> t)
        {
            name =n;
            type=t;
        }
    }
    public
    SQLiteHelper()
    {
    }
    @Override
    public
    String createTable(Object o)
    {
        String TableCommand = "CREATE TABLE ";
        boolean badd = false;
        boolean iladd = false;
        boolean sadd = false;
        boolean dfadd = false;
        List<cell> cells = new ArrayList<>();
        ClassName = o.getClass().getName();
        TableCommand += ClassName;
        TableCommand += " (ID INT PRIMARY KEY     NOT NULL ";
                Field[] FieldList = o.getClass().getDeclaredFields();
        for(int i=0;i<FieldList.length;i++)
        {
            cells.add(new cell(FieldList[i].getName(),FieldList[i].getType()));
        }
        for (cell cell : cells)
        {
            TableCommand +=  ", " + cell.name + " ";
            if(cell.type == Integer.class || cell.type == int.class || cell.type ==  Long.class || cell.type == long.class)
            {
                TableCommand += "INTEGER";
            }
            if(cell.type == Boolean.class || cell.type == boolean.class)
            {
                TableCommand += "INTEGER";
            }
            if(cell.type == Float.class || cell.type == float.class || cell.type ==  Double.class || cell.type == double.class)
            {
                TableCommand += "REAL";
            }
            if(cell.type == String.class)
            {
                TableCommand += "TEXT";
            }
        }
        TableCommand += ")";
        return TableCommand;
    }

    @Override
    public
    String insert(Object o)
    {
        String InsertString = null;
        Field[] FieldList = o.getClass().getDeclaredFields();
        for(int i=0;i<FieldList.length;i++)
        {
            if(FieldList[i].getType()==boolean.class || FieldList[i].getType()==Boolean.class)
            {
                System.out.println("Bool/bool");
            }
            if(FieldList[i].getType()==int.class || FieldList[i].getType()==Integer.class || FieldList[i].getType()==Long.class || FieldList[i].getType()==long.class)
            {
                System.out.println("Int/int");
            }
            if(FieldList[i].getType()==Float.class || FieldList[i].getType()== float.class || FieldList[i].getType()== Double.class || FieldList[i].getType()== double.class)
            {
                System.out.println("Float/float/Double/double");
            }
            if(FieldList[i].getType()==String.class)
            {
                System.out.println("string");
            }
            //System.out.println(FieldList[i].getName());
        }
        return InsertString;
    }
    
}
