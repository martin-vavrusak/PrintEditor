/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.vavmar.editor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Martin
 */
public class ReferenceTest {
    
    public static void main( String[] args )
    {
        A a = new A();
        
        a.printList();
        
        List<Object> mylist = a.getList();
        
        System.out.println("main: " + mylist);
        
        mylist = new ArrayList<Object>();
        mylist.add(new Object());
        mylist.add(new Object());
        
        a.printList();
        
        System.out.println("main: " + mylist);
    }
   
}
