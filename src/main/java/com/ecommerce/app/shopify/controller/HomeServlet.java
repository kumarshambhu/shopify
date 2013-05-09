package com.ecommerce.app.shopify.controller;

import com.ecommerce.app.shopify.dao.DaoImpl;
import com.ecommerce.app.shopify.domain.Product;
import com.ecommerce.app.shopify.domain.Profile;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Home", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(HomeServlet.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            String action = request.getParameter("action");

            if (action != null) {
                switch (action) {
                    case "DeleteFromCart":
                        defaultAction(request, response);
                        break;
                    case "Cart":
                        cart(request, response);
                        break;
                    case "Image":
                        retrieveImage(request, response);
                        break;
                    case "EditUser":
                        editUser(request, response);
                        break;
                    case "Update Profile":
                        updateUser(request, response);
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

    public void editUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //unlock
        if (request.getParameter("profileId") != null) {
            Long profileId = Long.parseLong(request.getParameter("profileId"));
            Profile profile = DaoImpl.INSTANCE.getProfile(profileId);
            request.setAttribute("profile", profile);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/tool/profile-add.jsp");
            dispatcher.forward(request, response);
        } else {
            request.setAttribute("error", "No profile Id provided!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/tool/error.jsp");
            dispatcher.forward(request, response);
        }


    }

    public void updateUser(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Long profileId = null;
        if (!request.getParameter("profileId").equals("")) {
            profileId = Long.parseLong(request.getParameter("profileId"));
        }
        String uname = request.getParameter("uname");
        String pwd = request.getParameter("pwd");
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String country = request.getParameter("country");
        Long pincode = Long.parseLong(request.getParameter("pincode"));
        Long mobile = Long.parseLong(request.getParameter("mobile"));
        String status = "ACTIVE";

        Profile profile = new Profile(uname, pwd, null, Boolean.TRUE, name, gender, email, address, city, state, country, pincode, mobile, status, null);
        if (profileId != null) {
            profile.setProfileId(profileId);
        }
        if (DaoImpl.INSTANCE.updateProfile(profile) == Boolean.FALSE) {
            logger.log(Level.INFO, "Profile updated successfuly : {0}", profile.getName());
        } else {
            logger.log(Level.INFO, "Profile update failed : {0}", profile.getName());
        }
        response.sendRedirect("/shopify/home");

    }

    public void defaultAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Product> productLst = DaoImpl.INSTANCE.getProducts();
        request.setAttribute("productLst", productLst);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/tool/home.jsp");
        dispatcher.forward(request, response);
    }

    public void cart(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Product> productLst = DaoImpl.INSTANCE.getProducts();
        request.setAttribute("productLst", productLst);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/tool/cart.jsp");
        dispatcher.forward(request, response);
    }

    public void retrieveImage(HttpServletRequest request, HttpServletResponse response) throws Exception {

        InputStream inputStream = null;
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            Long id = Long.parseLong(request.getParameter("id"));
            Product product = DaoImpl.INSTANCE.getProductById(id);
            response.setContentType("image/jpg");
            byte[] imageByteArry = product.getImageByteArry();
            outputStream.write(imageByteArry, 0, imageByteArry.length);
        } catch (Exception ex) {
            logger.severe(ex.toString());
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }

        }

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
