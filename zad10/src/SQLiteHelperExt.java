import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author raven
 */
public class SQLiteHelperExt implements SQLiteHelperExtInterface
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
        public cell()
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    Object fillField(Class<?> typ,String name, ResultSet res, Class cls, Object ob) throws IllegalArgumentException, IllegalAccessException, SQLException, NoSuchFieldException, InstantiationException
    {
        //Object ob = cls.newInstance();
        Field fi = cls.getDeclaredField(name);
        if( typ == int.class)
        {
            fi.setInt(ob, res.getInt(name));
            
        }
        else if(typ==Integer.class)
        {
            fi.set(ob, (Integer)res.getInt(name));
        }
        else if(typ==boolean.class)
        {
            fi.setBoolean(ob, res.getBoolean(name));
        }
        else if (typ==Boolean.class)
        {
            fi.set(ob, (Boolean)res.getBoolean(name));
        }
        else if(typ==double.class)
        {
            fi.setDouble(ob, res.getDouble(name));
        }
        else if (typ == Double.class)
        {
            fi.set(ob, (Double)res.getDouble(name));
        }
        else if(typ == char.class||typ==Character.class)
        {
            fi.set(ob, res.getString(name));
        }
        else if(typ == String.class)
        {
            fi.set(ob,res.getString(name));
        }
        else if(typ==long.class)
        {
            fi.setLong(ob, res.getLong(name));
        }
        else if(typ==Long.class)
        {
            fi.set(ob, new Long(res.getLong(name)));
        }
        else if (typ == short.class)
        {
            fi.setShort(ob, res.getShort(name));
        }
        else if (typ == Short.class)
        {
            fi.setShort(ob, (Short)res.getShort(name));
        }
        else if (typ==float.class)
        {
            fi.setFloat(ob, res.getFloat(name));
        }
        else if (typ==Float.class)
        {
            fi.set(ob, (Float)res.getFloat(name));
        }
        return ob;
    }
    
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<Object> select(Connection connection, String tableName)
    {
        List<Object> ret = new ArrayList<>();
        Statement stat;
        try
        {
            Class c = Class.forName(tableName);
            Field[] FieldList = c.getFields();
            stat = connection.createStatement();
            ResultSet res = stat.executeQuery("SELECT * FROM "+tableName);
            int tmp = res.getRow();
            while(res.next())
            {
                Object obj = c.newInstance();
                for(int i=0;i<FieldList.length;i++)
                {
                    obj = fillField(FieldList[i].getType(), FieldList[i].getName(), res, c,obj);
                }
                ret.add(obj);
            }
        }
        catch (ClassNotFoundException ex)
        {
            Logger.getLogger(SQLiteHelperExt.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SecurityException ex)
        {
            Logger.getLogger(SQLiteHelperExt.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            Logger.getLogger(SQLiteHelperExt.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            Logger.getLogger(SQLiteHelperExt.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalArgumentException ex)
        {
            Logger.getLogger(SQLiteHelperExt.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(SQLiteHelperExt.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NoSuchFieldException ex)
        {
            Logger.getLogger(SQLiteHelperExt.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public String createTable(Object o)
    {
        String TableCommand = "CREATE TABLE IF NOT EXISTS ";
        boolean badd = false;
        boolean iladd = false;
        boolean sadd = false;
        boolean dfadd = false;
        List<cell> cells = new ArrayList<>();
        ClassName = o.getClass().getName();
        TableCommand += ClassName;
        TableCommand += " ( ";
                Field[] FieldList = o.getClass().getDeclaredFields();
        for(int i=0;i<FieldList.length;i++)
        {
            if(Modifier.isPublic(FieldList[i].getModifiers()) && (FieldList[i].getType() == Long.class || FieldList[i].getType() == long.class 
                || FieldList[i].getType() == String.class || FieldList[i].getType() == boolean.class || FieldList[i].getType() == Boolean.class || 
                FieldList[i].getType() == Integer.class || FieldList[i].getType() == int.class || FieldList[i].getType() == Float.class || FieldList[i].getType() == float.class
                || FieldList[i].getType() == Double.class || FieldList[i].getType() == double.class))
            {
                cells.add(new cell(FieldList[i].getName(),FieldList[i].getType()));
            }
        }
        int i = 0;
        for (cell cell : cells)
        {   
            if(i==0)
            {
                TableCommand += cell.name + " ";
            }
            else
            {
                TableCommand += "," + cell.name + " ";
            }
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
            i++;
        }
        TableCommand += ");";
        return TableCommand;
    }

    @Override
    public String insert(Object o)
    {
        String InsertString = "INSERT INTO " + o.getClass().getName();
        InsertString += " VALUES( ";
        Field[] FieldList = o.getClass().getDeclaredFields();
        Class source = o.getClass();
        boolean added = false;
        for(int i=0;i<FieldList.length;i++)
        {
            if(Modifier.isPublic(FieldList[i].getModifiers()) && (FieldList[i].getType() == Long.class || FieldList[i].getType() == long.class 
                || FieldList[i].getType() == String.class || FieldList[i].getType() == boolean.class || FieldList[i].getType() == Boolean.class || 
                FieldList[i].getType() == Integer.class || FieldList[i].getType() == int.class || FieldList[i].getType() == Float.class || FieldList[i].getType() == float.class
                || FieldList[i].getType() == Double.class || FieldList[i].getType() == double.class))
            {
                if(i!=0 && added)
                {
                    InsertString += " ,";
                }
                added =true;
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
