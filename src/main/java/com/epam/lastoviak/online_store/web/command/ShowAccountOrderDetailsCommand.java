package com.epam.lastoviak.online_store.web.command;

import com.epam.lastoviak.online_store.db.dao.AccountOrderProductDao;
import com.epam.lastoviak.online_store.db.dto.AccountOrderProduct;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static com.epam.lastoviak.online_store.web.Path.ACCOUNT_ORDER_DETAILED_PAGE;
import static com.epam.lastoviak.online_store.web.Path.ERROR_PAGE;

public class ShowAccountOrderDetailsCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String path = ERROR_PAGE;
        String errorMassage = null;

        int accountOrderId = Integer.parseInt(request.getParameter("accountOrderId"));
        System.out.println("accountOrderId>>" + accountOrderId);

        List<AccountOrderProduct> accountOrderProductList=new AccountOrderProductDao().getAccountOrderProductListByAccountOrderId(accountOrderId);
        System.out.println(accountOrderProductList);

        request.setAttribute("accountOrderProductList",accountOrderProductList);

       path=ACCOUNT_ORDER_DETAILED_PAGE;

       return path;
    }
}
