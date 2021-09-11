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
        HttpSession httpSession = request.getSession();
        //log
        String categoryId = request.getParameter("categoryId");
        System.out.println(categoryId);
        if (categoryId != null) {
            httpSession.setAttribute("categoryId", categoryId);
        } else {
            categoryId = (String) httpSession.getAttribute("categoryId");
        }
        System.out.println(categoryId);
        //log

        int page = 1;
        int recordsPerPage = 2;
        int numbOfRecords = 0;

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int limit = (page - 1) * recordsPerPage;
        List<Product> productList = null;

        if (request.getParameter("filter") != null) {
            switch (request.getParameter("filter")) {
                case "ON":
                    httpSession.setAttribute("filter", "ON");
                    setFilterToSession(request);
                    break;
                case "OFF":
                    httpSession.removeAttribute("filter");
                    removeFilterFromSession(request);
                    break;
            }

        }
        if (httpSession.getAttribute("filter") != null) {
            StringBuilder generatedQuery = new FilteredProductsSqlGenerator().generateSqlQuery(request);
            System.out.println(generatedQuery);
            numbOfRecords = productDAO.getNumberOfFilteredRecords(generatedQuery.toString());
            System.out.println(numbOfRecords);
            productList = productDAO.getProductsByGeneratedSql(generatedQuery
                    .append("LIMIT ").append(limit).append(", ").append(recordsPerPage).toString());
            System.out.println(productList);
        } else {
            numbOfRecords = productDAO.getNumberOfRecords(Integer.parseInt(categoryId));
            System.out.println(numbOfRecords);
            productList = productDAO.getProductsByCategoryIdWithLimit(Integer.parseInt(categoryId), limit, recordsPerPage);

        }
        int numbOfPages = (int) Math.ceil(numbOfRecords * 1.0 / recordsPerPage);

        System.out.println(productList.toString());
        //log
//request.getRequestURI()

        //log

        request.setAttribute("productList", productList);
        request.setAttribute("numbOfPages", numbOfPages);
        request.setAttribute("currentPage", page);
        //log

//request.getA
        return path;
    }

    private void setFilterToSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        System.out.println(Arrays.toString(request.getParameterValues("manufacturer"))+"aa");
        session.setAttribute("manufacturer", request.getParameterValues("manufacturer"));
        session.setAttribute("maxPrice", request.getParameter("maxPrice"));
        session.setAttribute("minPrice", request.getParameter("minPrice"));
        session.setAttribute("orderBy", request.getParameter("orderBy"));
    }

    private void removeFilterFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("manufacturer");
        session.removeAttribute("maxPrice");
        session.removeAttribute("minPrice");
        session.removeAttribute("orderBy");

    }


    private static class FilteredProductsSqlGenerator {


        public StringBuilder generateSqlQuery(HttpServletRequest request) {
            System.out.println("start");
            HttpSession session = request.getSession();
            StringBuilder sb = new StringBuilder();
            int categoryId = Integer.parseInt((String) session.getAttribute("categoryId"));
            String[] manufacturer=(String[]) session.getAttribute("manufacturer");
           // System.out.println(manufacturer[0]);
            System.out.println(manufacturer!=null);
            if (manufacturer!=null) {
                sb.append(Arrays.stream(manufacturer).map(x->String.valueOf(Integer.parseInt(x))).
                                collect(Collectors.joining(",","manufacturer IN(",") ")));

            }
            String minPrice = (String) session.getAttribute("minPrice");
            System.out.println(minPrice.isEmpty());
            if (!minPrice.isEmpty()) {
                sb.append("AND price>").append(Integer.parseInt((String) session.getAttribute("minPrice")));
            }
            String maxPrice = (String) session.getAttribute("maxPrice");
            System.out.println(maxPrice);
            if (!maxPrice.isEmpty()) {
                sb.append("AND price<").append(Integer.parseInt((String) session.getAttribute("maxPrice")));
            }
            sb.append("AND category_id=").append(categoryId).append(" ");
            String orderBy = (String) session.getAttribute("orderBy");
            System.out.println(orderBy);
            if (!"null".equals(orderBy)) {
                sb.append("ORDER BY ");
                switch ((String) session.getAttribute("orderBy")) {
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
                        sb.append("name");
                        break;
                    case "nameReverse":
                        sb.append("name DESC ");
                        break;
                    default:
                        sb.append("amount DESC ");
                        break;
                }
            }

            return sb;
        }
    }
}

