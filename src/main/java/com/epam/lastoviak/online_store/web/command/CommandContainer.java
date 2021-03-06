package com.epam.lastoviak.online_store.web.command;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {
    private static final Logger log = Logger.getLogger(CommandContainer.class);

    private static Map<String,Command> commands=new HashMap<>();

    static {
        commands.put("register",new RegisterCommand());
        commands.put("login",new LoginCommand());
        commands.put("logout",new LogoutCommand());
        commands.put("showProductsByCategory",new ShowListOfProductCommand());
        commands.put("productDetailedPage",new ShowProductDetailedPageCommand());
        commands.put("addToCart",new AddToCartCommand());
        commands.put("decreaseAmount",new DecreaseAmountInCartCommand ());
        commands.put("deleteProduct",new DeleteProductFromCartCommand ());
        commands.put("findAccount",new FindAccountByIdCommand ());
        commands.put("changeAccountStatus",new ChangeAccountStatusCommand ());
        commands.put("registerBuy",new RegisterBuyCommand ());
        commands.put("getAllAccountOrders",new GetAllAccountOrdersCommand());
        commands.put("accountOrderDetailedPage",new ShowAccountOrderDetailsCommand ());
        commands.put("getAccountOrdersByAccountId",new GetAccountOrdersByAccountIdCommand ());
        commands.put("showCart",new ShowCartCommand ());
    }

    public static Command getCommand(String commandName){
        //log
        Command command=commands.get(commandName);
        return command;
    }
}
