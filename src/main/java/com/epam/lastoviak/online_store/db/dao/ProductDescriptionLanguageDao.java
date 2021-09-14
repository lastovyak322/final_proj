package com.epam.lastoviak.online_store.db.dao;

import com.epam.lastoviak.online_store.db.DBManager;
import com.epam.lastoviak.online_store.db.DTOFiller;
import com.epam.lastoviak.online_store.db.dto.Product;
import com.epam.lastoviak.online_store.db.dto.ProductDescription;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDescriptionLanguageDao {
    private DBManager dbManager = DBManager.getInstance();
    private static final String SQL_ADD_NEW_PRODUCT_DESCRIPTION =
            "INSERT INTO product_description_language (product_id, language_id, description) VALUES (?,?,?)";
    private static final String SQL_FIND_PRODUCT_DESCRIPTION_BY_LANGUAGE =
            "SELECT * FROM product_description_language WHERE product_id=? AND language_id=?";
    private static final String PRODUCT_ID ="product_id";
    private static final String LANGUAGE_ID ="language_id";
    private static final String DESCRIPTION ="description";

    public boolean addProductDescription(ProductDescription productDescription){
            boolean ans = false;
            Connection connection = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;

            try {
                connection = dbManager.getConnection();
                if (connection != null) {
                    pstm = connection.prepareStatement(SQL_ADD_NEW_PRODUCT_DESCRIPTION);
                    int m = 0;
                    pstm.setInt(++m,productDescription.getProductId());
                    pstm.setInt(++m,productDescription.getLanguageId());
                    pstm.setString(++m,productDescription.getDescription());
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

            }
            return ans;

    }

    public ProductDescription getProductById(int productId, int languageId) {
        ProductDescription productDescription = null;
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ProductDescriptionFiller productDescriptionFiller=new ProductDescriptionFiller();

        try {
            connection = dbManager.getConnection();
            if (connection != null) {
                int m=0;
                pstm = connection.prepareStatement(SQL_FIND_PRODUCT_DESCRIPTION_BY_LANGUAGE);
                pstm.setInt(++m, productId);
                pstm.setInt(++m, languageId);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    productDescription = productDescriptionFiller.fill(rs);
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
        return productDescription;
    }
    private final static class ProductDescriptionFiller implements DTOFiller<ProductDescription> {


        @Override
        public ProductDescription fill(ResultSet rs) {

            try {
                ProductDescription productDescription=new ProductDescription();
                productDescription.setProductId(rs.getInt(PRODUCT_ID));
                productDescription.setLanguageId(rs.getInt(LANGUAGE_ID));
                productDescription.setDescription(rs.getString(DESCRIPTION));
                return productDescription;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                throw new IllegalArgumentException(throwables);
            }
        }
    }
}
