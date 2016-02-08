
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
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
public class ConditionTest implements BooleanSupplier
{
    boolean cond;
    public void setCond(boolean condition)
    {
        cond = condition;
    }
    @Override
    public boolean getAsBoolean()
    {
        return cond;
    }
    
}
