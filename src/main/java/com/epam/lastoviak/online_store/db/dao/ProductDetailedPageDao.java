package com.epam.lastoviak.online_store.db.dao;

import com.epam.lastoviak.online_store.db.DBManager;
import com.epam.lastoviak.online_store.db.DTOFiller;
import com.epam.lastoviak.online_store.db.Fields;
import com.epam.lastoviak.online_store.db.dto.ProductDetailedPage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDetailedPageDao {
    private static final String SQL_GET_PRODUCT_DETAILED_PAGE_BY_PRODUCT_ID_AND_LANGUAGE_ID =
            "SELECT product.id,product.name,product.price,product.max_speed,product.max_load,pdl.description,man.name FROM product " +
                    "INNER JOIN product_description_language pdl ON product.id=pdl.product_id " +
                    "INNER JOIN manufacturer man on manufacturer_id=man.id " +
                    "WHERE product.id=? AND pdl.language_id=?";


    DBManager dbManager = DBManager.getInstance();

    public ProductDetailedPage getPDPbyProductIdAndLanguageId(int productId, int languageId) {
        ProductDetailedPage pdp = null;
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ProductDetailedPageFiller pdpFiller = new ProductDetailedPageFiller();

        try {
            connection = dbManager.getConnection();
            if (connection != null) {
                pstm = connection.prepareStatement(SQL_GET_PRODUCT_DETAILED_PAGE_BY_PRODUCT_ID_AND_LANGUAGE_ID);
                pstm.setInt(1, productId);
                pstm.setInt(2, languageId);
                rs = pstm.executeQuery();
                if (rs.next()) {
                    pdp = pdpFiller.fill(rs);

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

        return pdp;
    }

    private final static class ProductDetailedPageFiller implements DTOFiller<ProductDetailedPage> {


        @Override
        public ProductDetailedPage fill(ResultSet rs) {

            try {
                ProductDetailedPage productDetailedPage = new ProductDetailedPage();

                productDetailedPage.setId(rs.getInt(Fields.ID));
                productDetailedPage.setPrice(rs.getBigDecimal(Fields.PRODUCT_PRICE));
                productDetailedPage.setName(rs.getString(Fields.PRODUCT_NAME));
                productDetailedPage.setMaxSpeed(rs.getInt(Fields.PRODUCT_MAX_SPEED));
                productDetailedPage.setMaxLoad(rs.getInt(Fields.PRODUCT_MAX_LOAD));
                productDetailedPage.setManufacturer(rs.getString(Fields.MANUFACTURER_NAME_PDP));
                productDetailedPage.setDescription(rs.getString(Fields.PRODUCT_DESC_LANG_DESCRIPTION));
                return productDetailedPage;

            } catch (SQLException throwables) {
                throwables.printStackTrace();
                throw new IllegalArgumentException(throwables);
            }
        }
    }
}
