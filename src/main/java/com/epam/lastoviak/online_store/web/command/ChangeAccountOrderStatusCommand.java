package com.epam.lastoviak.online_store.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.lastoviak.online_store.web.Path.ERROR_PAGE;

public class ChangeAccountOrderStatusCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String path = ERROR_PAGE;
        String errorMassage = null;


        return null;
    }
}
