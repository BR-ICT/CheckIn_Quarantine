/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.api.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Wattana
 */
public class ConnectPG {

    public static Connection ConnectionPG() throws Exception {

        Class.forName("org.postgresql.Driver");
        String connstring = "jdbc:postgresql://192.200.9.208:5432/bkrdb";
        Connection conn = DriverManager.getConnection(connstring, "postgres", "ictiicctt");

        //Class.forName(jdbcClassName);
        return conn;

    }

    public static Connection ConnectionDBAS400() throws Exception {

        String jdbcClassName = "com.ibm.as400.access.AS400JDBCDriver";
        String url = "jdbc:as400://192.200.9.190;";

        Class.forName(jdbcClassName);
        return DriverManager.getConnection(url, "M3SRVICT", "ICT12345");
    }

    public static Connection ConnectionDB2() throws Exception {

        String jdbcClassName = "com.ibm.jtopenlite.database.jdbc.JDBCDriver";
        String url = "jdbc:jtopenlite://192.200.9.190";

        Class.forName(jdbcClassName);
        return DriverManager.getConnection(url, "M3SRVICT", "ICT12345");
    }
}
