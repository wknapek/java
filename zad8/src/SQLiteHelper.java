import static java.lang.Compiler.command;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        String TableCommand = "CREATE TABLE IF NOT EXISTS";
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
            if(Modifier.isPublic(FieldList[i].getModifiers()))
            {
                cells.add(new cell(FieldList[i].getName(),FieldList[i].getType()));
            }
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
        TableCommand += ");";
        return TableCommand;
    }

    @Override
    public
    String insert(Object o)
    {
        String InsertString = "INSERT INTO " + o.getClass().getName();
        InsertString += " VALUES( ";
        Field[] FieldList = o.getClass().getDeclaredFields();
        Class source = o.getClass();
        for(int i=0;i<FieldList.length;i++)
        {
            if(Modifier.isPublic(FieldList[i].getModifiers()))
            {
                if(i!=0)
                {
                    InsertString += " ,";
                }
                if(FieldList[i].getType()==boolean.class || FieldList[i].getType()==Boolean.class)
                {
                    try {
                        boolean bvalue = (boolean) FieldList[i].get(o);
                        InsertString += String.valueOf((bvalue) ? 1 : 0);
                    }
                    catch (IllegalArgumentException ex) {
                        Logger.getLogger(SQLiteHelper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    catch (IllegalAccessException ex) {
                        Logger.getLogger(SQLiteHelper.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                if(FieldList[i].getType()==int.class || FieldList[i].getType()==Integer.class)
                {
                    try
                    {
                        int ivalue = (int) FieldList[i].get(o);
                        InsertString+= String.valueOf(ivalue);
                    }
                    catch (IllegalArgumentException ex)
                    {
                        Logger.getLogger(SQLiteHelper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    catch (IllegalAccessException ex)
                    {
                        Logger.getLogger(SQLiteHelper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if(FieldList[i].getType()==Long.class || FieldList[i].getType()==long.class)
                {
                    try {
                        long lvalue = (long) FieldList[i].get(o);
                        InsertString += String.valueOf(lvalue);
                    }
                    catch (IllegalArgumentException ex) {
                        Logger.getLogger(SQLiteHelper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    catch (IllegalAccessException ex) {
                        Logger.getLogger(SQLiteHelper.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                if(FieldList[i].getType()== Double.class || FieldList[i].getType()== double.class)
                {
                    try
                    {
                        double dvalue = (double) FieldList[i].get(o);
                        InsertString += String.valueOf(dvalue);
                    }
                    catch (IllegalArgumentException ex)
                    {
                        Logger.getLogger(SQLiteHelper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    catch (IllegalAccessException ex)
                    {
                        Logger.getLogger(SQLiteHelper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if(FieldList[i].getType()==Float.class || FieldList[i].getType()== float.class)
                {
                    try
                    {
                        float fvalue = (float) FieldList[i].get(o);
                        InsertString += String.valueOf(fvalue);
                    }
                    catch (IllegalArgumentException ex)
                    {
                        Logger.getLogger(SQLiteHelper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    catch (IllegalAccessException ex)
                    {
                        Logger.getLogger(SQLiteHelper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if(FieldList[i].getType()==String.class)
                {
                    System.err.println("string");
                    try
                    {
                        String svalue = FieldList[i].get(o).toString();
                        InsertString += "'"+svalue+"'";
                    }
                    catch (IllegalArgumentException ex)
                    {
                        Logger.getLogger(SQLiteHelper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    catch (IllegalAccessException ex)
                    {
                        Logger.getLogger(SQLiteHelper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        InsertString += ");";
        return InsertString;
    }
    
}
