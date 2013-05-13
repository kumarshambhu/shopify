package com.ecommerce.app.shopify.controller;

import com.ecommerce.app.shopify.dao.DaoImpl;
import com.ecommerce.app.shopify.domain.LineItems;
import com.ecommerce.app.shopify.domain.Product;
import com.ecommerce.app.shopify.domain.Profile;
import com.ecommerce.app.shopify.domain.SaleOrder;
import com.ecommerce.app.shopify.util.EmailUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
import javax.servlet.http.HttpSession;

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
                    case "AddToCart":
                        addToCart(request, response);
                        break;
                    case "DeleteCartItem":
                        deleteCartItem(request, response);
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
                    case "Checkout":
                        checkOut(request, response);
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
            Profile profile = DaoImpl.INSTANCE.getProfileById(profileId);
            request.setAttribute("profile", profile);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/tool/profile.jsp");
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
        if (DaoImpl.INSTANCE.updateProfile(profile) == Boolean.TRUE) {
            logger.log(Level.INFO, "Profile updated successfuly : {0}", profile.getName());
            response.sendRedirect("/shopify/home");
        } else {
            request.setAttribute("error", "Profile Update Failed for " + profile.getName());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/tool/error.jsp");
            dispatcher.forward(request, response);

        }

    }

    public void defaultAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Product> productLst = DaoImpl.INSTANCE.getAllProducts();
        request.setAttribute("productLst", productLst);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/tool/home.jsp");
        dispatcher.forward(request, response);
    }

    public void deleteCartItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long lineItemId = Long.parseLong(request.getParameter("lineItemId"));
        HttpSession session = request.getSession(false);
        List<LineItems> oldLineItemsLst = (List<LineItems>) session.getAttribute("lineItemsLst");
        List<LineItems> newLineItemsLst = new ArrayList<LineItems>();
        Integer i = 1;
        for (LineItems lineItem : oldLineItemsLst) {
            if (lineItem.getLineItemId() != lineItemId) {
                lineItem.setLineItemId(i);
                i++;
                newLineItemsLst.add(lineItem);
            }
        }
        session.setAttribute("lineItemsLst", newLineItemsLst);
        response.sendRedirect("/shopify/home");
    }

    public void addToCart(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Long productId = Long.parseLong(request.getParameter("productId"));
        Integer productQty = Integer.parseInt(request.getParameter("productQty"));
        logger.log(Level.INFO, "productId: {0},productQty: {1}", new Object[]{productId, productQty});
        HttpSession session = request.getSession(false);
        LineItems lineItem = new LineItems();
        lineItem.setProductId(productId);
        lineItem.setQty(productQty);

        List<LineItems> lineItemsLst = (List<LineItems>) session.getAttribute("lineItemsLst");
        if (lineItemsLst == null) {
            lineItemsLst = new ArrayList<LineItems>();
        }

        Product product = DaoImpl.INSTANCE.getProductById(productId);
        lineItem.setLineItemId(lineItemsLst.size() + 1);
        lineItem.setName(product.getCode() + ": " + product.getName());
        lineItem.setPrice(product.getPrice() * productQty);
        lineItemsLst.add(lineItem);
        //Add the cart details to session so that its easy to acess across pages.
        session.setAttribute("lineItemsLst", lineItemsLst);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/tool/cart.jsp");
        dispatcher.forward(request, response);
    }

    public void checkOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        List<LineItems> lineItemsLst = (List<LineItems>) session.getAttribute("lineItemsLst");
        if (lineItemsLst == null) {
            request.setAttribute("error", "No Items in Cart!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/tool/error.jsp");
            dispatcher.forward(request, response);
        } else {

            Long profileId = Long.parseLong(session.getAttribute("uid").toString());
            SaleOrder saleOrder = new SaleOrder();
            saleOrder.setOrderStatus("SHIPPMENT_PENDING_DELIVERY");
            saleOrder.setProfileId(profileId);
            if (DaoImpl.INSTANCE.saveOrder(saleOrder, lineItemsLst) == Boolean.TRUE) {
                Profile profile = DaoImpl.INSTANCE.getProfileById(profileId);
                Float priceTotal = 0f;
                StringBuilder mailBody = new StringBuilder();
                mailBody.append("Order Placed By: ").append(profile.getName()).append("\n");
                mailBody.append("Your order id: ").append(saleOrder.getOrderId()).append("\n");
                mailBody.append("Order Status: ").append(saleOrder.getOrderStatus()).append("\n");
                mailBody.append("\n");
                mailBody.append("Purchase Details").append("\n");


                for (LineItems lineItem : lineItemsLst) {
                    mailBody.append("Product Name: ").append(lineItem.getName()).append(", Quantity: ").append(lineItem.getQty()).append(", Price Per Unit: ").append(lineItem.getPrice()).append(", Amount: ").append(lineItem.getQty() * lineItem.getPrice());
                    mailBody.append("\n");
                    priceTotal = priceTotal + (lineItem.getPrice() * lineItem.getQty());
                }
                mailBody.append("\n").append("Total Sum: ").append(priceTotal);
                mailBody.append("\n").append("Payment Type: CASH ON DELIVERY");
                mailBody.append("\n\n").append("Shipping Address: ").append(profile.getAddress()).append(",").append(profile.getCity()).append(",").append(profile.getState()).append(",").append(profile.getCountry()).append(",").append(profile.getPincode());
                mailBody.append("\n").append("Contact Number: ").append(profile.getMobile());
                EmailUtil.INSTANCE.sendMail("Purchase Order Placed!", mailBody.toString(), profile.getEmail(), null, null);
            } else {
                request.setAttribute("error", "Order could not be saved to DB!");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/tool/error.jsp");
                dispatcher.forward(request, response);
            }


        }

        session.setAttribute("lineItemsLst", null);
        request.setAttribute("flash_msg", "Checkout Completed!");
        defaultAction(request, response);
    }

    public void retrieveImage(HttpServletRequest request, HttpServletResponse response) throws Exception {

        InputStream inputStream = null;
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            Long id = Long.parseLong(request.getParameter("id"));
            Product product = DaoImpl.INSTANCE.getProductById(id);
//            response.setContentType("image/jpg");
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
