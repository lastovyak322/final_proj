package com.epam.lastoviak.online_store.db.dao;

import com.epam.lastoviak.online_store.db.DBManager;
import com.epam.lastoviak.online_store.db.DTOFiller;
import com.epam.lastoviak.online_store.db.dto.Account;

import java.sql.*;

import static com.epam.lastoviak.online_store.db.Fields.*;

public class AccountDao {
    private static final String SQL_FIND_ACCOUNT_BY_ID = "SELECT * FROM account WHERE id=?";;
    DBManager dbManager = DBManager.getInstance();
    private static final String SQL_REGISTER_NEW_ACCOUNT =
            "INSERT INTO account (password, email, name, phone,role_id,status_id) VALUES (?,?,?,?,?,?)";
    private static final String SQL_FIND_ACCOUNT_BY_PHONE =
            "SELECT * FROM account WHERE phone=?";
    private static final String SQL_FIND_ACCOUNT_BY_EMAIL =
            "SELECT * FROM account WHERE email=?";
    private static final String SQL_CHANGE_ACCOUNT_PASSWORD =
            "UPDATE account  SET password = ? WHERE id = ?";
    private static final String SQL_CHANGE_ACCOUNT_STATUS =
            "UPDATE account  SET status_id = ? WHERE id = ?";
    private static final String SQL_CHANGE_ACCOUNT_ROLE =
            "UPDATE account  SET role_id = ?, last_update=DEFAULT WHERE id = ?";

    public Account getAccountById(int id) {
        Account account = null;
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        AccountFiller accountFiller = new AccountFiller();

        try {
            connection = dbManager.getConnection();
            if (connection != null) {
                pstm = connection.prepareStatement(SQL_FIND_ACCOUNT_BY_ID);
                pstm.setInt(1, id);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    account = accountFiller.fill(rs);

                }
                dbManager.commit(connection);
            }


        } catch (SQLException throwables) {
            //log
            dbManager.rollback(connection);
            throwables.printStackTrace();
        } finally {
            dbManager.closeResultSet(rs);
            dbManager.closePreparedStatement(pstm);
            dbManager.closeConnection(connection);
        }

        return account;
    }



    public boolean registerAccount(Account account) {
        boolean ans = false;
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs=null;

        try {
            connection = dbManager.getConnection();
            if (connection!=null) {
                pstm = connection.prepareStatement(SQL_REGISTER_NEW_ACCOUNT,
                        Statement.RETURN_GENERATED_KEYS);
                int m = 0;
                pstm.setString(++m, account.getPassword());
                pstm.setString(++m, account.getEmail());
                pstm.setString(++m, account.getName());
                pstm.setString(++m, account.getPhone());
                pstm.setInt(++m,account.getRoleId());
                pstm.setInt(++m,account.getStatusId());
                if(pstm.executeUpdate()>0){
                    ans=true;
                }
                rs=pstm.getGeneratedKeys();
                rs.next();
                account.setId(rs.getInt(1));
                System.out.println(account);
                connection.commit();
            }
        } catch (SQLException throwables) {
            //log
            dbManager.rollback(connection);
            ans = false;
            throwables.printStackTrace();
        } finally {
            dbManager.closeResultSet(rs);
            dbManager.closePreparedStatement(pstm);
            dbManager.closeConnection(connection);
        }
        return ans;
    }

    public Account findAccountByUniqueField(String field, String fieldName) {
        Account account = null;
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        AccountFiller accountFiller = new AccountFiller();
        String query=null;

        switch (fieldName){
            case ACCOUNT_PHONE:
                query= SQL_FIND_ACCOUNT_BY_PHONE;
                break;
            case ACCOUNT_EMAIL:
                query=SQL_FIND_ACCOUNT_BY_EMAIL;
                break;
            default:
                query="";
        }

        try {
            connection = dbManager.getConnection();
            if (connection!=null) {
                pstm = connection.prepareStatement(query);
                pstm.setString(1, field);
                rs = pstm.executeQuery();
                if(rs.next()) {
                    account = accountFiller.fill(rs);
                }
                dbManager.commit(connection);
            }

        } catch (SQLException throwables) {
            //log
            dbManager.rollback(connection);
            throwables.printStackTrace();
        } finally {
            dbManager.closeResultSet(rs);
            dbManager.closePreparedStatement(pstm);
            dbManager.closeConnection(connection);
        }
        return account;
    }


    public boolean changePass(int id, String password) {
        boolean ans=false;
        Connection connection = null;
        PreparedStatement pstm = null;
        try {
            connection = dbManager.getConnection();
            if (connection!=null) {
                pstm = connection.prepareStatement(SQL_CHANGE_ACCOUNT_PASSWORD);
                pstm.setString(1, password);
                pstm.setInt(2, id);
                if(pstm.executeUpdate()>0){
                    ans=true;
                }
                dbManager.commit(connection);
            }

        } catch (SQLException throwables) {
            //log
            dbManager.rollback(connection);
            ans = false;
            throwables.printStackTrace();
        } finally {
            dbManager.closePreparedStatement(pstm);
            dbManager.closeConnection(connection);
        }

        return ans;
    }

    public boolean changeAccountStatusById(int id, int statusId) {
        boolean  ans=false;
        Connection connection = null;
        PreparedStatement pstm = null;
        try {
            connection = dbManager.getConnection();
            if(connection!=null) {
                pstm = connection.prepareStatement(SQL_CHANGE_ACCOUNT_STATUS);
                pstm.setInt(1, statusId);
                pstm.setInt(2, id);
                if(pstm.executeUpdate()>0){
                    ans=true;
                }
                dbManager.commit(connection);
            }
        } catch (SQLException throwables) {
            //log
            dbManager.rollback(connection);
            ans=false;
            throwables.printStackTrace();
        }finally {
            dbManager.closePreparedStatement(pstm);
            dbManager.closeConnection(connection);
        }
        return ans;
    }

    public boolean changeAccountRole(int id, int roleId) {
        boolean  ans=false;
        Connection connection = null;
        PreparedStatement pstm = null;
        try {
            connection = dbManager.getConnection();
            if(connection!=null) {
                pstm = connection.prepareStatement(SQL_CHANGE_ACCOUNT_ROLE);
                pstm.setInt(1, roleId);
                pstm.setInt(2, id);
                if(pstm.executeUpdate()>0){
                    ans=true;
                }
                dbManager.commit(connection);
            }

        } catch (SQLException throwables) {
            //log
            dbManager.rollback(connection);
            ans=false;
            throwables.printStackTrace();
        }finally {
            dbManager.closePreparedStatement(pstm);
            dbManager.closeConnection(connection);
        }
        return ans;
    }





    private final static class AccountFiller implements DTOFiller<Account> {

        @Override
        public Account fill(ResultSet rs) {

            try {
                Account account = new Account();
                account.setPassword(rs.getString(ACCOUNT_PASSWORD));
                account.setEmail(rs.getString(ACCOUNT_EMAIL));
                account.setName(rs.getString(ACCOUNT_NAME));
                account.setPhone(rs.getString(ACCOUNT_PHONE));
                account.setId(rs.getInt(ID));
                account.setRoleId(rs.getInt(ACCOUNT_ROLE_ID));
                account.setStatusId(rs.getInt(STATUS_ID));
                account.setCreateTime(rs.getTimestamp(CREATE_TIME));
                account.setLastUpdate(rs.getTimestamp(LAST_UPDATE));
                return account;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                throw new IllegalArgumentException(throwables);
            }
        }
    }
}
