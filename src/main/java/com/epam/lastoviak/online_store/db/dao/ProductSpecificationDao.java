package com.epam.lastoviak.online_store.db.dao;

import com.epam.lastoviak.online_store.db.DBManager;

import com.epam.lastoviak.online_store.db.DTOFiller;
import com.epam.lastoviak.online_store.db.dto.Product;
import com.epam.lastoviak.online_store.db.dto.ProductSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductSpecificationDao {
    private static final String SQL_ADD_NEW_PRODUCT_SPECIFICATION =
            "INSERT INTO product_specifications (product_id,max_speed, max_load, power_reserve, manufacturer) VALUES (?,?,?,?,?)";
    private static final String SQL_FIND_PRODUCT_SPECIFICATION_BY_ID =
            "SELECT * FROM product_specifications WHERE product_id =?";
    DBManager dbManager = DBManager.getInstance();

    private boolean addProductSpecification(ProductSpecification productSpecification) {
        boolean ans = false;
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            connection = dbManager.getConnection();
            if (connection != null) {
                pstm = connection.prepareStatement(SQL_ADD_NEW_PRODUCT_SPECIFICATION);
                int m = 0;
                pstm.setInt(++m, productSpecification.getProductId());
                pstm.setDouble(++m, productSpecification.getMaxSpeed());
                pstm.setInt(++m, productSpecification.getMaxLoad());
                pstm.setInt(++m, productSpecification.getPowerReserve());
                pstm.setString(++m, productSpecification.getManufacturer());
                if (pstm.executeUpdate() > 0) {
                    ans = true;
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
            dbManager.closeResultSet(rs);
        }
        return ans;
    }

    public ProductSpecification getProductSpecificationById(int id) {
        ProductSpecification productSpecification = new ProductSpecification();
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;


        try {
            connection = dbManager.getConnection();
            if (connection != null) {
                pstm = connection.prepareStatement(SQL_FIND_PRODUCT_SPECIFICATION_BY_ID);
                pstm.setInt(1, id);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    productSpecification = new ProductSpecificationFiller().fill(rs);
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
        return productSpecification;
    }

    private final static class ProductSpecificationFiller implements DTOFiller<ProductSpecification> {

        @Override
        public ProductSpecification fill(ResultSet rs) {

            try {
                ProductSpecification productSpecification = new ProductSpecification();
                productSpecification.setProductId(rs.getInt("product_id"));
                productSpecification.setMaxSpeed(rs.getInt("max_speed"));
                productSpecification.setMaxLoad(rs.getInt("max_load"));
                productSpecification.setMaxLoad(rs.getInt("power_reserve"));
                productSpecification.setManufacturer(rs.getString("manufacturer"));

                return productSpecification;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                throw new IllegalArgumentException(throwables);
            }
        }
    }
}
