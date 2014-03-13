/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.vavmar.editor.experimental;

import cz.fi.muni.vavmar.editor.DAO.DataProvider;
import java.net.URL;
import java.net.URLClassLoader;

/**
 *
 * @author Martin
 */
public class DBTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException {
        
         ClassLoader cl = ClassLoader.getSystemClassLoader();
 
        URL[] urls = ((URLClassLoader)cl).getURLs();
 
        for(URL url: urls){
        	System.out.println(url.getFile());
        }
        
        Class.forName("org.postgresql.Driver");
        
        
        DataProvider provider = new DataProvider();
        provider.getTables();
        
        provider.getTable("C_Payment");
    }
    
}
