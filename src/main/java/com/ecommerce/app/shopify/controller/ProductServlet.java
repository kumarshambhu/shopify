package com.ecommerce.app.shopify.controller;

import com.ecommerce.app.shopify.dao.DaoImpl;
import com.ecommerce.app.shopify.domain.Product;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(name = "Product", urlPatterns = {"/product"})
@MultipartConfig
public class ProductServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ProductServlet.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            String action = request.getParameter("action");

            if (action != null) {
                switch (action) {
                    case "Edit Product":
                        defaultAction(request, response);
                        break;
                    case "Save Product":
                        addProduct(request, response);
                        break;
                    case "Update Product":
                        defaultAction(request, response);
                        break;
                    case "Delete Product":
                        defaultAction(request, response);
                        break;
                    default:
                        defaultAction(request, response);
                        break;
                }
            } else {
                defaultAction(request, response);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            request.setAttribute("error", ex.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/tool/error.jsp");
            dispatcher.forward(request, response);
        }
    }

    public void defaultAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/tool/add-product.jsp");
        dispatcher.forward(request, response);
    }

    public void addProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String name = request.getParameter("name");
        String code = request.getParameter("code");
        String category = request.getParameter("category");
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        String description = request.getParameter("description");
        
        Product product = new Product();
        product.setName(name);
        product.setCode(code);
        product.setCategory(category);
        product.setPrice(price);
        product.setDescription(description);


        Part imagePart = request.getPart("image");
        if (imagePart != null) {
            // prints out some information for debugging
            product.setImagePart(imagePart);
            System.out.println(imagePart.getName());
            System.out.println(imagePart.getSize());
            System.out.println(imagePart.getContentType());

        }
        DaoImpl.INSTANCE.saveProduct(product);
        response.sendRedirect("/shopify/home");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
