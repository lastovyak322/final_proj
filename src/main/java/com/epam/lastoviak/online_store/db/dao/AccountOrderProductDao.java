package com.epam.lastoviak.online_store.db.dao;

import com.epam.lastoviak.online_store.db.DBManager;
import com.epam.lastoviak.online_store.db.DTOFiller;
import com.epam.lastoviak.online_store.db.dto.AccountOrderProduct;
import com.epam.lastoviak.online_store.db.dto.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AccountOrderProductDao {

    private static final String SQL_FIND_ACCOUNT_ORDER_PRODUCT_BY_ACCOUNT_ORDER_ID =
            "SELECT * FROM account_order_product WHERE account_order_id=?";
    DBManager dbManager = DBManager.getInstance();
    private static final String SQL_ADD_PRODUCTS_TO_ORDER_PRODUCT =
            "INSERT INTO account_order_product (account_order_id, product_id, price, amount) VALUES(?,?,?,?)";

//    public AccountOrderProduct getAccountOrderProductByAccountOrderId(int id) {
//        AccountOrderProduct accountOrderProduct = null;
//        Connection connection = null;
//        PreparedStatement pstm = null;
//        ResultSet rs = null;
//        AccountOrderProductFiller accountOrderProductFiller=new AccountOrderProductFiller();
//
//        try {
//            connection = dbManager.getConnection();
//            if (connection != null) {
//                pstm = connection.prepareStatement(SQL_FIND_ACCOUNT_ORDER_PRODUCT_BY_ACCOUNT_ORDER_ID);
//                pstm.setInt(1, id);
//                rs = pstm.executeQuery();
//                if (rs.next()) {
//                    accountOrderProduct = accountOrderProductFiller.fill(rs);
//
//                }
//                dbManager.commit(connection);
//            }
//
//        } catch (SQLException throwables) {
//            //log
//            dbManager.rollback(connection);
//            throwables.printStackTrace();
//        } finally {
//            dbManager.closeResultSet(rs);
//            dbManager.closePreparedStatement(pstm);
//            dbManager.closeConnection(connection);
//        }
//
//        return accountOrderProduct;
//    }
    public List<AccountOrderProduct> getAccountOrderProductListByAccountOrderId(int id) {
        List<AccountOrderProduct> accountOrderProductList = new ArrayList<>();
        AccountOrderProduct accountOrderProduct = null;
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        AccountOrderProductFiller accountOrderProductFiller=new AccountOrderProductFiller();

        try {
            connection = dbManager.getConnection();
            if (connection != null) {
                pstm = connection.prepareStatement(SQL_FIND_ACCOUNT_ORDER_PRODUCT_BY_ACCOUNT_ORDER_ID);
                pstm.setInt(1, id);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    accountOrderProduct = accountOrderProductFiller.fill(rs);
                    accountOrderProductList.add(accountOrderProduct);
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
        return accountOrderProductList;

    }


    public boolean addOrderProduct(Map<Product, Integer> cart, Connection connection, int orderId) {
        boolean ans = false;
        Set<Map.Entry<Product, Integer>> cartEntry = cart.entrySet();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pstm = connection.prepareStatement(SQL_ADD_PRODUCTS_TO_ORDER_PRODUCT);
            System.out.println(cartEntry);
            for (Map.Entry<Product, Integer> x : cartEntry) {
                int m = 0;
                pstm.setInt(++m, orderId);
                pstm.setInt(++m, x.getKey().getId());
                pstm.setBigDecimal(++m, x.getKey().getPrice());
                pstm.setInt(++m, x.getValue());
                pstm.addBatch();
            }
            int[] result = pstm.executeBatch();
            for (int x : result) {
                if (x < 0) {
                    return false;
                }
            }
            ans = true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();

        } finally {
            dbManager.closePreparedStatement(pstm);
        }
        return ans;
    }

    private final static class AccountOrderProductFiller implements DTOFiller<AccountOrderProduct> {

        @Override
        public AccountOrderProduct fill(ResultSet rs) {

            try {
                AccountOrderProduct accountOrderProduct = new AccountOrderProduct();
                accountOrderProduct.setProductId(rs.getInt("product_id"));
                accountOrderProduct.setCount(rs.getInt("amount"));
                accountOrderProduct.setAccountOrderId(rs.getInt("account_order_id"));
                accountOrderProduct.setPrice(rs.getBigDecimal("price"));
                return accountOrderProduct;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                throw new IllegalArgumentException(throwables);
            }
        }
    }

}
