package com.epam.lastoviak.online_store.db.dao;

import com.epam.lastoviak.online_store.db.DBManager;
import com.epam.lastoviak.online_store.db.dto.Product;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AccountOrderDao {
    private static final String SQL_ADD_NEW_ORDER = "" +
            "INSERT INTO account_order (account_id) " +
            "VALUES(?) ";
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


    public boolean registerBuy(Map<Product, Integer> cart, int accountId) {
        boolean ans=false;
        Connection connection = null;
        Set<Map.Entry<Product, Integer>> entryCart = cart.entrySet();
        List<Product> updatedProductList;
        try {
            connection = dbManager.getConnection();
            if (connection != null) {
                updatedProductList = new ProductDAO().selectForRegister(connection, entryCart);

                int orderId = addNewOrder(accountId, connection);
                System.out.println("orderId" + orderId);
                if (orderId == 0) {
                    connection.rollback();
                    return false;
                }

                boolean isOrderProductAdded = new OrderProductDao().addOrderProduct(cart, connection, orderId);
                System.out.println("isOrderProductAdded"+isOrderProductAdded);

                if (!isOrderProductAdded) {
                    connection.rollback();
                    return false;
                }
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

//    private final static class OrderFiller implements DTOFiller<Order> {
//
//        @Override
//        public Order fill(ResultSet rs) {
//
//            try {
//                Order order = new Order();
//                order.setId(rs.getInt(DTO_ID));
//                order.setAccountId(rs.getInt(PRODUCT_NAME));
//                order.setLastUpdate(rs.getTimestamp(PRODUCT_PRICE));
//                order.setCreateTime(rs.getTimestamp(PRODUCT_PRICE));
//                product.setAmount(rs.getInt(PRODUCT_AMOUNT));
//                product.setCategoryId(rs.getInt(PRODUCT_CATEGORY_ID));
//                product.setLastUpdate(rs.getTimestamp(PRODUCT_LAST_UPDATE));
//                //product.setLastUpdate(rs.getTimestamp(PRODUCT_LAST_UPDATE));
//                return product;
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//                throw new IllegalArgumentException(throwables);
//            }
//        }
//    }
}
