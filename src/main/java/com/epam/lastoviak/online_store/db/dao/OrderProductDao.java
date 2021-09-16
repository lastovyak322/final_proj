package com.epam.lastoviak.online_store.db.dao;

import com.epam.lastoviak.online_store.db.DBManager;
import com.epam.lastoviak.online_store.db.dto.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

public class OrderProductDao {

    DBManager dbManager = DBManager.getInstance();
    private static final String SQL_ADD_PRODUCTS_TO_ORDER_PRODUCT =
            "INSERT INTO account_order_product (account_order_id, product_id, price, amount) VALUES(?,?,?,?)";

    public boolean addOrderProduct(Map<Product, Integer> cart, Connection connection, int orderId) {
        boolean ans = false;
        Set<Map.Entry<Product, Integer>> cartEntry = cart.entrySet();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pstm = connection.prepareStatement(SQL_ADD_PRODUCTS_TO_ORDER_PRODUCT);
            int m = 0;
            for (Map.Entry<Product, Integer> x : cartEntry) {
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
            ans=true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }finally {
            dbManager.closePreparedStatement(pstm);
        }
        return ans;
    }
}
