package com.epam.lastoviak.online_store.web.command;

import com.epam.lastoviak.online_store.db.dto.Product;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Map;
import java.util.stream.Collectors;

import static com.epam.lastoviak.online_store.web.Path.CART_PAGE;
import static com.epam.lastoviak.online_store.web.Path.ERROR_PAGE;

public class DeleteProductFromCartCommand extends Command {
    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String path = ERROR_PAGE;
        String errorMassage = null;

        HttpSession session = request.getSession();
        request.setAttribute("redirect", "ON");

        Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");

        int productId = Integer.parseInt(request.getParameter("productId"));


        Product product = cart.keySet().stream().filter(x -> x.getId() == productId)
                .collect(Collectors.toList()).get(0);

        if (cart.remove(product) == null) {
            errorMassage = "Oops ";
            session.setAttribute("errorMessage", errorMassage);
            //log.error("error: " + errorMassage);
            System.out.println(errorMassage);
            return path;
        }
        path = CART_PAGE;

        return path;
    }
}
