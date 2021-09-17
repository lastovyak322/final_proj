package com.epam.lastoviak.online_store.web.command;

import com.epam.lastoviak.online_store.db.dao.AccountDao;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.epam.lastoviak.online_store.web.Path.CHANGE_USER_STATUS;
import static com.epam.lastoviak.online_store.web.Path.ERROR_PAGE;

public class ChangeAccountStatusCommand extends Command {
    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String path = ERROR_PAGE;
        String errorMassage = null;

        //log.debug("Command start");
        System.out.println("start");
        request.setAttribute("redirect", "ON");
        HttpSession session = request.getSession();

        int accountId = Integer.parseInt(request.getParameter("accountId"));
        int statusId = Integer.parseInt(request.getParameter("statusId"));

        if (!new AccountDao().changeAccountStatusById(accountId, statusId)) {
            errorMassage = "Oops";
            session.setAttribute("errorMessage", errorMassage);
            //log.error("error: " + errorMassage);
            System.out.println(errorMassage);
            return path;
        }
        path = CHANGE_USER_STATUS;
        return path;
    }
}
