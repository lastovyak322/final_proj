package com.epam.lastoviak.online_store.web.command;

import com.epam.lastoviak.online_store.db.dao.AccountOrderDao;
import com.epam.lastoviak.online_store.db.dto.Account;
import com.epam.lastoviak.online_store.db.dto.AccountOrder;
import com.epam.lastoviak.online_store.web.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.epam.lastoviak.online_store.web.Path.ACCOUNT_ORDER_LIST_PAGE;
import static com.epam.lastoviak.online_store.web.Path.ERROR_PAGE;

public class GetAccountOrdersByAccountIdCommand extends Command {
    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String path = ERROR_PAGE;
        String errorMassage = null;

        System.out.println("start");

        HttpSession session = request.getSession();

        int page = 1;
        int recordsPerPage = 2;

        int accountId;
        Account account = (Account) session.getAttribute("account");

        accountId = account.getId();

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        int limit = (page - 1) * recordsPerPage;

        int numberOfRecords = new AccountOrderDao().getNumberOfAccountOrdersByAccountId(accountId);

        int numbOfPages = (int) Math.ceil(numberOfRecords * 1.0 / recordsPerPage);

        List<AccountOrder> accountOrderList = new AccountOrderDao().getAccountOrdersByAccountID(limit, numberOfRecords, accountId);
        System.out.println(accountOrderList);

        request.setAttribute("accountOrderList", accountOrderList);
        request.setAttribute("numbOfPages", numbOfPages);
        request.setAttribute("currentPage", page);
        path = Path.SELECTED_ACCOUNT_ORDER_LIST_PAGE;

        return path;
    }
}
