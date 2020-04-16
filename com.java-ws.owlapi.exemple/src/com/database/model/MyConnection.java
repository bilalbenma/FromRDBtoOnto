package com.database.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bilal Benmahria (bilalox)
 */
public class MyConnection {
    private static String db_url;
    private static String db_password;
    private static String db_name;
    private static String db_port;
    private static String db_user;
    private static Connection connection;
    
    private MyConnection(){
      
    }
    
    static{
         db_url="jdbc:mysql://localhost";
        db_port = "3306";
        db_name="movielens";
        db_user="root";
        db_password="lamiae";
    }
    
    public static Connection getConnections()
    {
        try{
             String url = "" + db_url + ":" + db_port + "/" + db_name + "";
             System.out.println("db_url: "+ url );
                if(connection==null){
                    synchronized(MyConnection.class)
                    {
                        if(connection==null)
                        {
                                Class.forName("com.mysql.jdbc.Driver");
                                 connection = DriverManager.getConnection(url, db_user, db_password); 
                                 
                        }      
                   }  
                    
                    
                }
                   return connection;
        }
        catch(Exception e){
            Logger.getLogger(MyConnection.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return null;
    }
        
  }
    
    
