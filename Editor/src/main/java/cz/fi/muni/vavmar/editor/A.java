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
public class A {
    private List<Object> list = new ArrayList<Object>();

    public A() {
        list.add(new Object());
    }

    
    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }
    
    public void printList(){
        System.out.println("A list: " + list);
    }
}
