package com.epam.lastoviak.online_store.web.command;

import com.epam.lastoviak.online_store.db.dao.ProductDAO;
import com.epam.lastoviak.online_store.db.dao.ProductDescriptionLanguageDao;
import com.epam.lastoviak.online_store.db.dao.ProductSpecificationDao;
import com.epam.lastoviak.online_store.db.dto.Product;
import com.epam.lastoviak.online_store.db.dto.ProductDescription;
import com.epam.lastoviak.online_store.db.dto.ProductSpecification;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.epam.lastoviak.online_store.web.Path.ERROR_PAGE;
import static com.epam.lastoviak.online_store.web.Path.PRODUCT_DETAILED_PAGE;

public class ShowProductDetailedPageCommand extends Command {
    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String forward = ERROR_PAGE;
        String errorMassage = null;


        HttpSession session = request.getSession();

        int productId = Integer.parseInt(request.getParameter("productId"));
        System.out.println("productId>>" + productId);

        Product product = new ProductDAO().getProductById(productId);
        System.out.println(product);

        ProductSpecification productSpecification = new ProductSpecificationDao().getProductSpecificationById(productId);
        System.out.println(productSpecification);

        ProductDescription productDescription = new ProductDescriptionLanguageDao().getProductById(productId, 1);
        System.out.println(productDescription);
        if (product == null) {
            errorMassage = "product does not exist";
            session.setAttribute("errorMessage", errorMassage);
            System.out.println(errorMassage);
            return forward;

        }
        forward = PRODUCT_DETAILED_PAGE;
        System.out.println(productDescription.getDescription());
        request.setAttribute("product", product);
        request.setAttribute("productSpecification", productSpecification);
        request.setAttribute("productDescription", productDescription);


        return forward;
    }
}
