package com.epam.lastoviak.online_store.db.dao;

import com.epam.lastoviak.online_store.db.DBManager;
import com.epam.lastoviak.online_store.db.DTOFiller;
import com.epam.lastoviak.online_store.db.dto.Product;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private DBManager dbManager=DBManager.getInstance();
    private static final String SQL_ADD_NEW_PRODUCT =
            "INSERT INTO product (name, price, amount, category_id) VALUES (?,?,?,?)";
    private static final String SQL_FIND_PRODUCT_BY_ID =
            "SELECT * FROM product WHERE id=?";
    private static final String SQL_CHANGE_PRODUCT_PRICE =
            "UPDATE product  SET price = ? WHERE id = ?";


    private static final String DTO_ID = "id";
    private static final String PRODUCT_NAME = "name";
    private static final String PRODUCT_PRICE = "price";
    private static final String PRODUCT_AMOUNT = "amount";
    private static final String PRODUCT_CATEGORY_ID = "category_id";
    private static final String PRODUCT_LAST_UPDATE = "last_update";





    private boolean addProduct(Product product){
        boolean ans = false;
        Connection connection = null;
        PreparedStatement pstm = null;

        try {
            connection = dbManager.getConnection();
            if (connection!=null) {
                pstm = connection.prepareStatement(SQL_ADD_NEW_PRODUCT);
                int m = 0;
                pstm.setString(++m, product.getName());
                pstm.setBigDecimal(++m, product.getPrice());
                pstm.setInt(++m, product.getAmount());
                pstm.setInt(++m, product.getCategoryId());
                if(pstm.executeUpdate()>0){
                    ans=true;
                }
                connection.commit();

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

    public Product getProductById(int id) {
        Product product = null;
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ProductFiller productFiller=new ProductFiller();

        try {
            connection = dbManager.getConnection();
            if (connection!=null) {
                pstm = connection.prepareStatement(SQL_FIND_PRODUCT_BY_ID);
                pstm.setInt(1, id);
                rs = pstm.executeQuery();
                if(rs.next()) {
                    product = productFiller.fill(rs);
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
        return product;
    }

    public List<Product> getProductsByCategoryId(int categoryId) {
        List<Product> productList=new ArrayList<>();
        Product product = null;
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ProductFiller productFiller=new ProductFiller();

        try {
            connection = dbManager.getConnection();
            if (connection!=null) {
                pstm = connection.prepareStatement(SQL_FIND_PRODUCT_BY_ID);
                pstm.setInt(1, categoryId);
                rs = pstm.executeQuery();
                while(rs.next()) {
                        product = productFiller.fill(rs);
                    productList.add(product);
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
        return productList;
    }

    public boolean changeProductPriceById(int id, BigDecimal price) {
        boolean  ans=false;
        Connection connection = null;
        PreparedStatement pstm = null;
        try {
            connection = dbManager.getConnection();
            if(connection!=null) {
                pstm = connection.prepareStatement(SQL_CHANGE_PRODUCT_PRICE);
                pstm.setBigDecimal(1, price);
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


    private final static class ProductFiller implements DTOFiller<Product> {

        @Override
        public Product fill(ResultSet rs) {

            try {
                Product product = new Product();
                product.setId(rs.getInt(DTO_ID));
                product.setName(rs.getString(PRODUCT_NAME));
                product.setPrice(rs.getBigDecimal(PRODUCT_PRICE));
                product.setAmount(rs.getInt(PRODUCT_AMOUNT));
                product.setCategoryId(rs.getInt(PRODUCT_CATEGORY_ID));
                product.setLastUpdate(rs.getTimestamp(PRODUCT_LAST_UPDATE));
                return product;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                throw new IllegalArgumentException(throwables);
            }
        }
    }
}
