package com.epam.lastoviak.online_store.web.command;

import com.epam.lastoviak.online_store.db.dao.AccountDao;
import com.epam.lastoviak.online_store.db.dto.Account;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.epam.lastoviak.online_store.web.Path.CHANGE_USER_STATUS;
import static com.epam.lastoviak.online_store.web.Path.ERROR_PAGE;

public class FindAccountByIdCommand extends Command{
    private static final Logger log = Logger.getLogger(LoginCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String path = ERROR_PAGE;
        String errorMassage = null;

        //log.debug("Command start");
        System.out.println("start");

        HttpSession session =request.getSession();

        int accountId=Integer.parseInt(request.getParameter("accountId"));

        Account account=new AccountDao().getAccountById(accountId);

        if(account==null){
            errorMassage = "This account does not exist";
            session.setAttribute("errorMessage", errorMassage);
            //log.error("error: " + errorMassage);
            System.out.println(errorMassage);
            return path;
        }

        path=CHANGE_USER_STATUS;

        request.setAttribute("account",account);

        return path;

    }
}
