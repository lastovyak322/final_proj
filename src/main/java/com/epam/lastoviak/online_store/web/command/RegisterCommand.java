package com.epam.lastoviak.online_store.web.command;

import com.epam.lastoviak.online_store.Utils;
import com.epam.lastoviak.online_store.db.Fields;
import com.epam.lastoviak.online_store.db.dao.AccountDao;
import com.epam.lastoviak.online_store.db.dto.Account;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.epam.lastoviak.online_store.web.Path.*;

public class RegisterCommand extends Command {
    private static final Logger log = Logger.getLogger(RegisterCommand.class);

    private boolean redirectMode=false;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String path = ERROR_PAGE;
        String errorMassage = null;

       // log.debug("Command starts");
        System.out.println("Command starts");

        HttpSession session = request.getSession();


        String password = request.getParameter("password");
        //log.trace("Got request parameter: password");
        System.out.println("pass"+ password);

        String email = request.getParameter("email");
       // log.trace("Got request parameter: email >>> " + email);
        System.out.println("email" +email);

        String name = request.getParameter("name");
        //log.trace("Got request parameter: name >>> " + name);
        System.out.println("name" +name);

        String phone = request.getParameter("phone");
       // log.trace("Got request parameter: phone >>> " + phone);
        System.out.println("phone"+ phone);

        if (Utils.isEmptyForm(password, email, name, phone)) {
            errorMassage = "All fields must be filled";
            session.setAttribute("errorMessage", errorMassage);
            //log.error("error: " + errorMassage);
            System.out.println(errorMassage);
            return path;
        }
        AccountDao accountDao = new AccountDao();
        if (accountDao.findAccountByUniqueField(email, Fields.ACCOUNT_EMAIL) != null ){
            errorMassage = "Account with this email already exists";
            session.setAttribute("errorMessage", errorMassage);
            //log.error("error: " + errorMassage);
            System.out.println(errorMassage);
            return path;

        } else {
            Account account = new Account();
            account.setPassword(password);
            account.setEmail(email);
            account.setName(name);
            account.setPhone(phone);
            account.setRoleId(1);
            account.setStatusId(1);
            //log
            System.out.println(account);

            if (!accountDao.registerAccount(account)) {
                errorMassage = "Error. Please try again later";
                request.setAttribute("errorMassage", errorMassage);
                //log
                System.out.println(errorMassage);
                return path;
            } else {
                session.setAttribute("account", account);
                //log
                System.out.println(session);
                redirectMode=true;

                path = MAIN_PAGE;
            }

        }
        //log
        System.out.println(path);
        return path;

    }

    public boolean isRedirectMode() {
        return redirectMode;
    }
}
