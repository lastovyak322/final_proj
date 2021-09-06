package com.epam.lastoviak.online_store.db;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBManager {
    private static final Logger log = Logger.getLogger(DBManager.class);
    private static DBManager instance;

    private DBManager() {
    }

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        Connection con = null;
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/test");
            con = ds.getConnection();
        } catch (NamingException ex) {
            log.error("Cannot obtain a connection from the pool", ex);
        }
        return con;
    }

    public void closeResultSet(ResultSet rs) {
        try {
            if (rs!=null) {
                rs.close();
            }
        } catch (SQLException  throwables) {
            throwables.printStackTrace();
        }
    }

    public void closePreparedStatement(PreparedStatement pstm) {
        try {
            if(pstm!=null) {
                pstm.close();
            }
        } catch (SQLException  throwables) {
            //log
            throwables.printStackTrace();
        }
    }

    public void commit(Connection con) {
        try {
            con.commit();
        } catch (SQLException  throwables) {
            throwables.printStackTrace();
        }
    }

    public void closeConnection(Connection con) {
        try {
            if(con!=null) {
                con.close();
            }
        } catch (SQLException  throwables) {
            throwables.printStackTrace();
        }
    }


    public void rollback(Connection con) {
        try {
            if (con!=null) {
                con.rollback();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
