package com.epam.lastoviak.online_store.web.command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.epam.lastoviak.online_store.web.Path.*;

public class LogoutCommand extends Command{
    private static final Logger log = Logger.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String path = SESSION_ERROR_PAGE;

        // log.debug("Command starts");
        System.out.println("Command starts");

        HttpSession session = request.getSession(false);
        if(session!=null){
            session.invalidate();
            return MAIN_PAGE;
        }
        //log

        return path;

    }


}
