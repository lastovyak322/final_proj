package com.epam.lastoviak.online_store.web.command;

import com.epam.lastoviak.online_store.Utils;
import com.epam.lastoviak.online_store.db.dao.AccountDao;
import com.epam.lastoviak.online_store.db.dto.Account;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.epam.lastoviak.online_store.web.Path.*;
import static com.epam.lastoviak.online_store.db.Fields.*;


public class LoginCommand extends Command {
    private static final Logger log = Logger.getLogger(LoginCommand.class);



    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String forward = ERROR_PAGE;
        String errorMassage = null;

        //log.debug("Command start");
        System.out.println("start");

        HttpSession session = request.getSession();


        String email = request.getParameter("email");
        //log.trace("Got request parameter: email >>> " + email);
        System.out.println(email);


        String password = request.getParameter("password");
       // log.trace("Got request parameter: password");
        System.out.println(password);


        if (Utils.isEmptyForm(email)||Utils.isEmptyForm(password)) {
            errorMassage = "email and password can`t be empty";
            session.setAttribute("errorMessage", errorMassage);
            //log.error("error: " + errorMassage);
            System.out.println(errorMassage);
            return forward;
        }

        Account account = new AccountDao().findAccountByUniqueField(email,ACCOUNT_EMAIL);
        log.trace("Got account by email >>> " + account);

        if (account == null || !password.equals(account.getPassword())) {
            errorMassage = "email or password are incorrect";
            session.setAttribute("errorMessage", errorMassage);
            //log.error("error: " + errorMassage);
            System.out.println(errorMassage);
            return forward;

        } else {
            int role_id = account.getRoleId();
            log.trace("Got role_id >>> " + role_id);
            if (role_id == 1) {
                forward = MAIN_PAGE;
            }
            if (role_id == 2) {
                forward = ADMIN_MAIN_PAGE;
            }

            session.setAttribute("account", account);
            //log
            System.out.println(account);
            session.setAttribute("role_id", role_id);
            //log
            System.out.println(account);


            //i18n

        }
        //log.debug("Command finished");
        System.out.println("finish");

        return forward;

    }


}
