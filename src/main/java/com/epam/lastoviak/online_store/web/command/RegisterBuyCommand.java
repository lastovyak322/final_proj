package com.epam.lastoviak.online_store.web.command;

import com.epam.lastoviak.online_store.db.dao.AccountOrderDao;
import com.epam.lastoviak.online_store.db.dto.Account;
import com.epam.lastoviak.online_store.db.dto.Product;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Map;

import static com.epam.lastoviak.online_store.web.Path.ERROR_PAGE;
import static com.epam.lastoviak.online_store.web.Path.MAIN_PAGE;

public class RegisterBuyCommand extends Command {
    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String path = ERROR_PAGE;
        String errorMassage = null;

        HttpSession session = request.getSession();
        request.setAttribute("redirect", "ON");

        Map<Product, Integer> cart = (Map<Product, Integer>) session.getAttribute("cart");

        int accountId = ((Account) session.getAttribute("account")).getId();

        boolean isSuccessful =new AccountOrderDao().registerBuy(cart,accountId);
        System.out.println(isSuccessful);
        if(!isSuccessful){
            errorMassage = "Oops something wrong";
            session.setAttribute("errorMessage", errorMassage);
            //log.error("error: " + errorMassage);
            System.out.println(errorMassage);
            return path;
        }

        session.removeAttribute("cart");

        path=MAIN_PAGE;

        return path;
    }
}
