package com.epam.lastoviak.online_store.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

public abstract class Command implements Serializable {
    public abstract String execute(HttpServletRequest request, HttpServletResponse response);

    public abstract boolean isRedirectMode();
}
