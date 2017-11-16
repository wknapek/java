/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication15;

import java.util.Random;
import java.util.Arrays;

/**
 *
 * @author raven
 */
public class MyArrays
{
    int[] array;
    double average;

    MyArrays() //konstruktor
    {
        array = new int[10]; // inizlalizacja tablicy jako 10 elementowa
        NumberGeneratorAndFillArray();
        MySort();
    }  

    public MyArrays(int size)
    {
        array = new int[size];
    }
    
   
    private void NumberGeneratorAndFillArray()
    {
        int max,min;
        min = -10;
        max=10;

        for (int i = 0; i < array.length; i++) 
        { // tu siê uzupe³nia
            array[i] = (int) (min + Math.random() * (max-min));
        }
    }
    
    private void MySort()
    {
        Arrays.sort(array);
    }
    
    public void Avarage()
    {
        int sum = 0; // tu kaja liczy sume
        for (int i = 0; i < array.length; i++) {
            sum = sum + array[i];
        }

        average = (double) sum / array.length;
        System.out.println("srednia z liczb w tablicy to " + average);
    }
    
    public void PrintArray()
    {
        for(int iter=0; iter<array.length;iter++)
        {
            System.out.println("Array elem of "+iter+" is: "+array[iter]);
        }
    }
    
    public void MinMaxElem()
    {
        System.out.println("Max elem is:" + array[9]);
        System.out.println("Min elem is:" + array[0]);
    }
    
    public void MinMaxCounter()
    {
       int morThan  = 0;
       int lessThan = 0;
       for (int i = 0; i < array.length; i++) {
            // tu petla która bedzie sprawdzac po kolei 10 razy zawartoæ tablicy

            if (average < array[i]) 
            {// tu jê¿eli rednia jest wieksa ni¿ wartoæ tablicy
                lessThan++;
            }
            else
            {
                morThan++;
            }
        }
        System.out.println("Number of elements grather tan avarag is: " + morThan );
        System.out.println("Number of elements less tan avarag is: " + lessThan );
    }
    
    public void odwrotne()
    {
        for(int i = array.length -1;i>=0;i--)
        {
            System.out.println(array[i]);
        }
    }
      
      
}
