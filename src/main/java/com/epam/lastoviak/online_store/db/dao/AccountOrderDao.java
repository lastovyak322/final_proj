package com.epam.lastoviak.online_store.db.dao;

import com.epam.lastoviak.online_store.db.DBManager;
import com.epam.lastoviak.online_store.db.DTOFiller;
import com.epam.lastoviak.online_store.db.dto.AccountOrder;
import com.epam.lastoviak.online_store.db.dto.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.epam.lastoviak.online_store.db.Fields.*;


public class AccountOrderDao {
    private static final String SQL_ADD_NEW_ORDER =
            "INSERT INTO account_order (account_id) VALUES(?) ";
    private static final String SQL_GET_ALL_ACCOUNT_ORDERS_WITH_LIMIT=
            "SELECT * FROM account_order LIMIT ?,?";
    private static final String SQL_GET_ACCOUNT_ORDERS_BY_ACCOUNT_ID=
            "SELECT * FROM account_order WHERE account_id=? LIMIT ?,?";
    private static final String SQL_GET_COUNT_OF_ALL_ACCOUNT_ORDERS=
            "SELECT COUNT(*) FROM account_order";
    private static final String SQL_CHANGE_ACCOUNT_ORDER_STATUS =
            "UPDATE account_order  SET status_id = ? WHERE id = ?";


    DBManager dbManager = DBManager.getInstance();


    private int addNewOrder(int accountId, Connection connection) {
        int id = 0;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pstm = connection.prepareStatement(SQL_ADD_NEW_ORDER,Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, accountId);
            if (pstm.executeUpdate() > 0) {
                rs = pstm.getGeneratedKeys();
                rs.next();
                id = rs.getInt(1);
            }

        } catch (SQLException throwables) {
            //log
            throwables.printStackTrace();
        } finally {
            dbManager.closePreparedStatement(pstm);
        }
        return id;
    }


    public int getNumberOfAllAccountOrders() {

        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int ans = 0;
        try {
            connection = dbManager.getConnection();
            if (connection != null) {
                pstm = connection.prepareStatement(SQL_GET_COUNT_OF_ALL_ACCOUNT_ORDERS);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    ans = rs.getInt(1);
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
        return ans;
    }

    public List<AccountOrder> getAllAccountOrders(int limit, int numberOfRecord) {
        List<AccountOrder> accountOrderList = new ArrayList<>();
        AccountOrder accountOrder = null;
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        OrderFiller orderFiller=new OrderFiller();

        try {
            connection = dbManager.getConnection();
            if (connection != null) {
                pstm = connection.prepareStatement(SQL_GET_ALL_ACCOUNT_ORDERS_WITH_LIMIT);
                pstm.setInt(1, limit);
                pstm.setInt(2, numberOfRecord);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    accountOrder = orderFiller.fill(rs);
                    accountOrderList.add(accountOrder);
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
        return accountOrderList;

    }

    public List<AccountOrder> getAccountOrdersByAccountID(int limit, int numberOfRecord,int accountId) {
        List<AccountOrder> accountOrderList = new ArrayList<>();
        AccountOrder accountOrder = null;
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        OrderFiller orderFiller=new OrderFiller();

        try {
            connection = dbManager.getConnection();
            if (connection != null) {
                pstm = connection.prepareStatement(SQL_GET_ACCOUNT_ORDERS_BY_ACCOUNT_ID);
                pstm.setInt(1, accountId);
                pstm.setInt(2, limit);
                pstm.setInt(3, numberOfRecord);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    accountOrder = orderFiller.fill(rs);
                    accountOrderList.add(accountOrder);
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
        return accountOrderList;

    }


    public boolean registerBuy(Map<Product, Integer> cart, int accountId) {
        boolean ans=false;
        Connection connection = null;
        Set<Map.Entry<Product, Integer>> entryCart = cart.entrySet();
        List<Product> updatedProductList;
        try {
            connection = dbManager.getConnection();
            if (connection != null) {

                int orderId = addNewOrder(accountId, connection);
                System.out.println("orderId" + orderId);

                if (orderId == 0) {
                    connection.rollback();
                    return false;
                }

                boolean isOrderProductAdded = new AccountOrderProductDao().addOrderProduct(cart, connection, orderId);
                System.out.println("isOrderProductAdded"+isOrderProductAdded);

                if (!isOrderProductAdded) {
                    connection.rollback();
                    return false;
                }

                updatedProductList = new ProductDAO().selectForRegister(connection, entryCart);

                boolean isAmountDecreased = new ProductDAO().decreaseAmountUponPurchase(connection, updatedProductList, cart);
                System.out.println("isAmountDecreased"+isAmountDecreased);

                if (!isAmountDecreased) {
                    connection.rollback();
                    return false;
                }
                connection.commit();
                ans = true;
            }

        } catch (Exception throwables) {
            dbManager.rollback(connection);
            throwables.printStackTrace();
        } finally {
            dbManager.closeConnection(connection);
        }

        return ans;
    }
    public boolean changeAccountOrderStatusById(int id, int statusId) {
        boolean  ans=false;
        Connection connection = null;
        PreparedStatement pstm = null;
        try {
            connection = dbManager.getConnection();
            if(connection!=null) {
                pstm = connection.prepareStatement(SQL_CHANGE_ACCOUNT_ORDER_STATUS);
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

    private final static class OrderFiller implements DTOFiller<AccountOrder> {

        @Override
        public AccountOrder fill(ResultSet rs) {

            try {
                AccountOrder order = new AccountOrder();
                order.setId(rs.getInt(ID));
                order.setStatusId(rs.getInt(STATUS_ID));
                order.setAccountId(rs.getInt(ACCOUNT_ORDER_ACCOUNT_ID));
                order.setLastUpdate(rs.getTimestamp(LAST_UPDATE));
                order.setCreateTime(rs.getTimestamp(CREATE_TIME));

                return order;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                throw new IllegalArgumentException(throwables);
            }
        }
    }
}
