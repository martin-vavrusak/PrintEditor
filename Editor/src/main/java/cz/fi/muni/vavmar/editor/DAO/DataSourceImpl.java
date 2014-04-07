/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.vavmar.editor.DAO;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author Martin
 */
public class DataSourceImpl implements DataSource {

    private String  username;
    private String  password;
    private String  databaseName;
    private String  portNum;
    private String  hostAddress;
    private String  connectinString;
    
    public DataSourceImpl(String hostAddress, String portNum, String databaseName) {
        this(hostAddress, portNum, databaseName, null, null);
    }

    public DataSourceImpl(String hostAddress, String portNum, String databaseName , String username, String password) {
        this.username = username;
        this.password = password;
        this.databaseName = databaseName;
        this.portNum = portNum;
        this.hostAddress = hostAddress;
        this.connectinString = "jdbc:postgresql://" + hostAddress + ":" + portNum + "/" + databaseName;
    }
    
    
    
    public Connection getConnection() throws SQLException {
        if(username != null && password != null){
            return getConnection(username, password);
        } else {
            return DriverManager.getConnection(connectinString);
        }
    }

    public Connection getConnection(String username, String password) throws SQLException {
        if(username != null && password != null){
            return DriverManager.getConnection(connectinString, username, password);
        } else {
            return null;
        }
    }

    public PrintWriter getLogWriter() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setLogWriter(PrintWriter out) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setLoginTimeout(int seconds) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getLoginTimeout() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return false;
    }
    
}
