package com.epam.lastoviak.online_store.web.command;

import com.epam.lastoviak.online_store.db.dao.ProductDAO;
import com.epam.lastoviak.online_store.db.dto.Product;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


import static com.epam.lastoviak.online_store.web.Path.PRODUCT_LIST_PAGE;


public class ShowListOfProductCommand extends Command {

    private static final Logger log = Logger.getLogger(ShowListOfProductCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        ProductDAO productDAO = new ProductDAO();
        String path = PRODUCT_LIST_PAGE;

        //log
        String categoryId=request.getParameter("categoryId");
        request.setAttribute("categoryId",categoryId );


        if (request.getParameter("filter") != null) {
            switch (request.getParameter("filter")) {
                case "doFilter":
                    request.setAttribute("filter", "doFilter");
                    setFilterToRequest(request);
                    break;
                case "resetFilter":
                    request.removeAttribute("filter");
                    removeFilterFromRequest(request);
                    break;
            }

        }

        int page = 1;
        int recordsPerPage = 2;
        int numbOfRecords;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int limit = (page - 1) * recordsPerPage;
        List<Product> productList ;


        if (request.getAttribute("filter") != null) {
            String generatedQuery = new FilteredProductsSqlGenerator().generateSqlQuery(request,limit,recordsPerPage);
            System.out.println(generatedQuery);
            numbOfRecords = productDAO.getNumberOfFilteredRecords(generatedQuery);
            System.out.println(numbOfRecords);
            productList = productDAO.getProductsByGeneratedSql(generatedQuery,limit,recordsPerPage);
            System.out.println(productList);
        } else {
            numbOfRecords = productDAO.getNumberOfRecords(Integer.parseInt(categoryId));
            productList = productDAO.getProductsByCategoryIdWithLimit(Integer.parseInt(categoryId), limit, recordsPerPage);

        }
        int numbOfPages = (int) Math.ceil(numbOfRecords * 1.0 / recordsPerPage);

        System.out.println(productList.toString());
        //log


        request.setAttribute("productList", productList);
        request.setAttribute("numbOfPages", numbOfPages);
        request.setAttribute("currentPage", page);
        //log

        return path;
    }

    private void setFilterToRequest(HttpServletRequest request) {
        request.setAttribute("manufacturer", request.getParameterValues("manufacturer"));
        request.setAttribute("maxPrice", request.getParameter("maxPrice"));
        request.setAttribute("minPrice", request.getParameter("minPrice"));
        request.setAttribute("orderBy", request.getParameter("orderBy"));
    }

    private void removeFilterFromRequest(HttpServletRequest request) {
        request.removeAttribute("manufacturer");
        request.removeAttribute("maxPrice");
        request.removeAttribute("minPrice");
        request.removeAttribute("orderBy");

    }


    private static class FilteredProductsSqlGenerator {


        public String generateSqlQuery(HttpServletRequest request,int limit,int recordsPerPage) {
            System.out.println("start");
            HttpSession session = request.getSession();
            StringBuilder sb = new StringBuilder();
            int categoryId = Integer.parseInt((String) request.getAttribute("categoryId"));
            String[] manufacturer = (String[]) request.getAttribute("manufacturer");



            // System.out.println(manufacturer[0]);
            System.out.println(manufacturer != null);
            if (manufacturer != null) {
                String generatedManufacturerParr = Arrays.stream(request.getParameterValues("manufacturer")).
                        collect(Collectors.joining("&manufacturer=", "manufacturer=", ""));
                request.setAttribute("manufacturer", generatedManufacturerParr);

                sb.append(Arrays.stream(manufacturer).map(x -> String.valueOf(Integer.parseInt(x))).
                        collect(Collectors.joining(",", "manufacturer_id IN(", ")")));

            }
            String minPrice = (String) request.getAttribute("minPrice");
            System.out.println(minPrice.isEmpty());
            if (!minPrice.isEmpty()) {
                sb.append(" AND price>").append(Integer.parseInt((String) request.getAttribute("minPrice")));
            }

            String maxPrice = (String) request.getAttribute("maxPrice");
            System.out.println(maxPrice);
            if (!maxPrice.isEmpty()) {
                sb.append(" AND price<").append(Integer.parseInt((String) request.getAttribute("maxPrice")));
            }

            sb.append(" AND category_id=").append(categoryId).append(" ");

            String orderBy = (String) request.getAttribute("orderBy");
            System.out.println(orderBy);
            if (!"null".equals(orderBy)) {
                sb.append("ORDER BY ");
                switch ((String) request.getAttribute("orderBy")) {
                    case "cheap":
                        sb.append("price ");
                        break;
                    case "expensive":
                        sb.append("price DESC ");
                        break;
                    case "old":
                        sb.append("last_update ");
                        break;
                    case "new":
                        sb.append("last_update DESC ");
                        break;
                    case "name":
                        sb.append("name ");
                        break;
                    case "nameReverse":
                        sb.append("name DESC ");
                        break;
                    default:
                        sb.append("amount DESC ");
                        break;
                }
            }
            return sb.toString();
        }
    }
}

