package com.epam.lastoviak.online_store;

import com.epam.lastoviak.online_store.db.DBManager;
import com.epam.lastoviak.online_store.db.dao.AccountDao;
import com.epam.lastoviak.online_store.web.command.Command;
import com.epam.lastoviak.online_store.web.command.CommandContainer;

import java.io.*;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "controller", value = "/hello")
public class Controller extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        process(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//log
        String commandName = request.getParameter("command");
        //log

        Command command = CommandContainer.getCommand(commandName);
        //log

        String path = command.execute(request, response);
        //log

        if (path != null) {
            if (command.isRedirectMode()){
                response.sendRedirect(path);
                //log
                System.out.println("redirect");
            }else {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
                requestDispatcher.forward(request, response);
                //log
                System.out.println("forward");
            }
        }
    }

}