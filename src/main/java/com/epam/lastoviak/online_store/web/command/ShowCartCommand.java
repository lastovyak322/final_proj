package com.epam.lastoviak.online_store.web.command;

import com.epam.lastoviak.online_store.db.dao.ProductDAO;
import com.epam.lastoviak.online_store.db.dto.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

import static com.epam.lastoviak.online_store.web.Path.CART_PAGE;
import static com.epam.lastoviak.online_store.web.Path.ERROR_PAGE;

public class ShowCartCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String path = ERROR_PAGE;
        String errorMassage = null;


        HttpSession session = request.getSession();

        Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");
        if (cart == null) {
            return CART_PAGE;
        }

        List<Product> updatedProductList=new ProductDAO().getUpdatedProductListFromCart(cart.entrySet());
        if (updatedProductList.size() == 0) {
            errorMassage = "Oops something wrong";
            session.setAttribute("errorMessage", errorMassage);
            //log.error("error: " + errorMassage);
            System.out.println(errorMassage);
            return path;
        }
        updatedProductList.forEach(x->cart.put(x,cart.get(x)));

        path=CART_PAGE;

        return path;
    }
}
