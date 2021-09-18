package com.epam.lastoviak.online_store.db.dao;

import com.epam.lastoviak.online_store.db.DBManager;
import com.epam.lastoviak.online_store.db.DTOFiller;
import com.epam.lastoviak.online_store.db.dto.Product;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class ProductDAO {


    private DBManager dbManager = DBManager.getInstance();
    private static final String SQL_CHANGE_PRODUCT_AMOUNT =
            "UPDATE product SET amount =? WHERE id=?";
    private static final String SQL_FIND_PRODUCT_BY_ID =
            "SELECT * FROM product WHERE id=?";
    private static final String SQL_FIND_PRODUCTS_BY_ID =
            "SELECT * FROM product WHERE id IN";
    private static final String SQL_GET_NUMBER_OF_RECORDS_BY_CATEGORY_ID =
            "SELECT COUNT(*) FROM product WHERE category_id=?";
    private static final String SQL_ADD_NEW_PRODUCT =
            "INSERT INTO product (name, price, amount, category_id) VALUES (?,?,?,?)";
    private static final String SQL_FIND_PRODUCT_BY_CATEGORY_ID =
            "SELECT * FROM product WHERE category_id=?";
    private static final String SQL_CHANGE_PRODUCT_PRICE =
            "UPDATE product  SET price = ? WHERE id = ?";
    private static final String SQL_GET_NUMBER_OF_FILTERED_RECORDS =
            "SELECT COUNT(*) FROM product INNER JOIN product_specifications ps on product.id = ps.product_Id WHERE ";
    private static final String SQL_FIND_QUERY_START = "SELECT id, name, price, amount, category_id, last_update " +
            "FROM product INNER JOIN product_specifications ps on product.id = ps.product_Id WHERE ";
    private static final String SQL_FIND_PRODUCT_BY_CATEGORY_ID_WITH_LIMIT =
            "SELECT * FROM product WHERE category_id=? LIMIT ?,?";
    private static final String SQL_GET_PRODUCT_AMOUNT_BY_PRODUCT_ID =
            "SELECT amount FROM product WHERE id=? ";


    private static final String DTO_ID = "id";
    private static final String PRODUCT_NAME = "name";
    private static final String PRODUCT_PRICE = "price";
    private static final String PRODUCT_AMOUNT = "amount";
    private static final String PRODUCT_CATEGORY_ID = "category_id";
    private static final String PRODUCT_LAST_UPDATE = "last_update";


    private boolean addProduct(Product product) {
        boolean ans = false;
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            connection = dbManager.getConnection();
            if (connection != null) {
                pstm = connection.prepareStatement(SQL_ADD_NEW_PRODUCT);
                int m = 0;
                pstm.setString(++m, product.getName());
                pstm.setBigDecimal(++m, product.getPrice());
                pstm.setInt(++m, product.getAmount());
                pstm.setInt(++m, product.getCategoryId());
                if (pstm.executeUpdate() > 0) {
                    ans = true;
                }
                rs = pstm.getGeneratedKeys();
                rs.next();
                product.setId(rs.getInt(1));
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

    public Product getProductById(int id) {
        Product product = null;
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ProductFiller productFiller = new ProductFiller();

        try {
            connection = dbManager.getConnection();
            if (connection != null) {
                pstm = connection.prepareStatement(SQL_FIND_PRODUCT_BY_ID);
                pstm.setInt(1, id);
                rs = pstm.executeQuery();
                if (rs.next()) {
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
        List<Product> productList = new ArrayList<>();
        Product product = null;
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ProductFiller productFiller = new ProductFiller();

        try {
            connection = dbManager.getConnection();
            if (connection != null) {
                pstm = connection.prepareStatement(SQL_FIND_PRODUCT_BY_CATEGORY_ID);
                pstm.setInt(1, categoryId);
                rs = pstm.executeQuery();
                while (rs.next()) {
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

    public List<Product> getProductsByCategoryIdWithLimit(int categoryId, int limit, int numberOfRecord) {
        List<Product> productList = new ArrayList<>();
        Product product = null;
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ProductFiller productFiller = new ProductFiller();

        try {
            connection = dbManager.getConnection();
            if (connection != null) {
                pstm = connection.prepareStatement(SQL_FIND_PRODUCT_BY_CATEGORY_ID_WITH_LIMIT);
                pstm.setInt(1, categoryId);
                pstm.setInt(2, limit);
                pstm.setInt(3, numberOfRecord);
                rs = pstm.executeQuery();
                while (rs.next()) {
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

    public List<Product> getProductsByGeneratedSql(String sqlQuery, int limit,int recordsPerPage) {
        List<Product> productList = new ArrayList<>();
        Product product = null;
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ProductFiller productFiller = new ProductFiller();
        StringBuilder generatedQuery = new StringBuilder(SQL_FIND_QUERY_START);
        generatedQuery
                .append(" ")
                .append(sqlQuery)
                .append("LIMIT ")
                .append(limit)
                .append(", ")
                .append(recordsPerPage);
        System.out.println(generatedQuery);

        try {
            connection = dbManager.getConnection();
            if (connection != null) {
                pstm = connection.prepareStatement(generatedQuery.toString());
                rs = pstm.executeQuery();
                while (rs.next()) {
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
        boolean ans = false;
        Connection connection = null;
        PreparedStatement pstm = null;
        try {
            connection = dbManager.getConnection();
            if (connection != null) {
                pstm = connection.prepareStatement(SQL_CHANGE_PRODUCT_PRICE);
                pstm.setBigDecimal(1, price);
                pstm.setInt(2, id);
                if (pstm.executeUpdate() > 0) {
                    ans = true;
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

    public int getNumberOfFilteredRecords(String sqlQuery) {

        StringBuilder generatedQuery = new StringBuilder(SQL_GET_NUMBER_OF_FILTERED_RECORDS);
        generatedQuery.append(" ").append(sqlQuery);
        System.out.println(generatedQuery);
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int ans = 0;
        try {
            connection = dbManager.getConnection();
            if (connection != null) {
                pstm = connection.prepareStatement(generatedQuery.toString());
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

    public int getNumberOfRecords(int category_id) {

        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int ans = 0;
        try {
            connection = dbManager.getConnection();
            if (connection != null) {
                pstm = connection.prepareStatement(SQL_GET_NUMBER_OF_RECORDS_BY_CATEGORY_ID);
                pstm.setInt(1, category_id);
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

    public boolean changeAmount(int productId, int amount) {

        boolean ans = false;
        Connection connection = null;
        PreparedStatement pstm = null;
        try {
            connection = dbManager.getConnection();
            if (connection != null) {
                pstm = connection.prepareStatement(SQL_CHANGE_PRODUCT_AMOUNT);
                pstm.setInt(1, amount);
                pstm.setInt(2, productId);
                if (pstm.executeUpdate() > 0) {
                    ans = true;
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



    public boolean decreaseAmountUponPurchase(Connection con, List<Product> productList, Map<Product, Integer> cart) throws Exception {
        boolean ans =false;
        Set<Map.Entry<Product, Integer>> updatedProductSet;

        productList.forEach(x -> cart.put(x, cart.remove(x)));

        updatedProductSet = cart.entrySet();

        Set<Map.Entry<Product, Integer>> notInStockProductList = updatedProductSet.stream().
                filter(x -> x.getKey().getAmount() < x.getValue()).collect(Collectors.toSet());

        PreparedStatement pstm = null;

        if (notInStockProductList.size() != 0) {
            throw new Exception("Not in stock");
        }
        try {
            pstm = con.prepareStatement(SQL_CHANGE_PRODUCT_AMOUNT);

            for (Map.Entry<Product, Integer> x : updatedProductSet) {
                pstm.setInt(1, x.getKey().getAmount() - x.getValue());
                pstm.setInt(2, x.getKey().getId());
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
        } finally {
            dbManager.closePreparedStatement(pstm);
        }

        return ans;
    }

    public List<Product> selectForRegister(Connection con, Set<Map.Entry<Product, Integer>> cartSet) {
        List<Product> productList = new ArrayList<>();
        Product product = null;
        StringJoiner sj = new StringJoiner(",", "(", ")");
        cartSet.forEach(x -> sj.add(String.valueOf(x.getKey().getId())));

        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            System.out.println("SELECT FROM product WHERE id IN"+sj.toString());
            pstm = con.prepareStatement("SELECT * FROM product WHERE id IN"+sj.toString());
            rs = pstm.executeQuery();
            while (rs.next()) {
                product = new ProductFiller().fill(rs);
                productList.add(product);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            dbManager.closeResultSet(rs);
            dbManager.closePreparedStatement(pstm);
        }
        System.out.println(productList);
        return productList;
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
                //product.setLastUpdate(rs.getTimestamp(PRODUCT_LAST_UPDATE));
                return product;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                throw new IllegalArgumentException(throwables);
            }
        }
    }
}
