package com.email_system;


import java.sql.*;

public class conn {
        Connection c;
        static Statement sment;
        conn() {
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                c = DriverManager.getConnection("jdbc:mysql:///Email_System","root", "Manas@2201");
                sment = c.createStatement();
            }catch(Exception e) {
                    e.printStackTrace();
            }

        }
       
}