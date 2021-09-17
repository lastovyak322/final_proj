package com.epam.lastoviak.online_store.web.command;

import com.epam.lastoviak.online_store.db.dao.AccountOrderDao;
import com.epam.lastoviak.online_store.db.dto.AccountOrder;
import com.epam.lastoviak.online_store.db.dto.Product;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;

import static com.epam.lastoviak.online_store.web.Path.ACCOUNT_ORDERS_LIST_PAGE;
import static com.epam.lastoviak.online_store.web.Path.ERROR_PAGE;

public class GetAllAccountOrderCommand extends Command {
    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String path = ERROR_PAGE;
        String errorMassage = null;

        System.out.println("start");

        int page = 1;
        int recordsPerPage = 2;


        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        int limit = (page - 1) * recordsPerPage;

        int numberOfRecords = new AccountOrderDao().getNumberOfAllAccountOrders();

        int numbOfPages = (int) Math.ceil(numberOfRecords * 1.0 / recordsPerPage);

        List<AccountOrder> accountOrderList = new AccountOrderDao().getAllAccountOrders(limit, recordsPerPage);
        System.out.println(accountOrderList);

        request.setAttribute("accountOrderList", accountOrderList);
        request.setAttribute("numbOfPages", numbOfPages);
        request.setAttribute("currentPage", page);
        path = ACCOUNT_ORDERS_LIST_PAGE;

        return path;
    }
}
