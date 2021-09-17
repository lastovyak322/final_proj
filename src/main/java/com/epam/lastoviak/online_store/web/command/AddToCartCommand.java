package com.epam.lastoviak.online_store.web.command;

import com.epam.lastoviak.online_store.db.dao.ProductDAO;
import com.epam.lastoviak.online_store.db.dto.Product;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.epam.lastoviak.online_store.web.Path.CART_PAGE;
import static com.epam.lastoviak.online_store.web.Path.ERROR_PAGE;


public class AddToCartCommand extends Command {
    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String path = ERROR_PAGE;
        String errorMassage = null;


        HttpSession session = request.getSession();
        request.setAttribute("redirect", "ON");

        Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }

        int productId = Integer.parseInt(request.getParameter("productId"));

        Product product = new ProductDAO().getProductById(productId);

        if (product == null) {
            errorMassage = "This product does not exist";
            session.setAttribute("errorMessage", errorMassage);
            //log.error("error: " + errorMassage);
            System.out.println(errorMassage);
            return path;
        }
//        if (product.getAmount() == 0) {
//            errorMassage = product.getName() + "Not in stock now";
//            session.setAttribute("errorMessage", errorMassage);
//            //log.error("error: " + errorMassage);
//            System.out.println(errorMassage);
//            return path;
//        }


        if (cart.containsKey(product)) {
            cart.put(product, cart.get(product) + 1);
        } else {
            cart.put(product, 1);
        }

        path = CART_PAGE;

        return path;
    }
}
