//package com.hackerone.demo.reporting.config;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//import org.h2.jdbc.JdbcSQLException;
//import org.springframework.context.annotation.Configuration;

//import java.sql.SQLException;
//
//import org.h2.tools.Server;
//import org.springframework.context.annotation.Bean;

//@Configuration
//public class H2DbConfig {
	
	
//    @Bean(initMethod = "start", destroyMethod = "stop")
//    public Server inMemoryH2DatabaseServer() throws SQLException {
//        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9091");
//    }
	
	
//    public static Connection connect (String dbPath, String userName, String password)
//                    throws ClassNotFoundException, SQLException, JdbcSQLException  {
//
//        Class.forName("org.h2.Driver");
//        String dbURL= dbPath + ";IGNORECASE=TRUE;MODE=MySQL;IFEXISTS=TRUE";
//
//        return DriverManager.getConnection(dbURL,userName,password);
//    }
//
//}
