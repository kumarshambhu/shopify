package com.ecommerce.app.shopify.filter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "Authenticator", urlPatterns = {"/*"})
public class Authenticator implements Filter {

    private static final Logger logger = Logger.getLogger(Authenticator.class.getName());
    private FilterConfig filterConfig = null;

    public Authenticator() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {

            HttpServletRequest req = (HttpServletRequest) request;
            //Authentication & Authorization.
            if (req.getRequestURI().contains("/login") && req.getParameter("action") != null && req.getParameter("action").equals("Sign Up")) {
                //Do nothing.
            } else if (req.getRequestURI().contains("/login") && req.getParameter("action") != null && req.getParameter("action").equals("Register")) {
                //Do nothing.
            } else if (req.getRequestURI().contains("/home") || req.getRequestURI().contains("/order") || req.getRequestURI().contains("/product")) {
                //Dont create a new session here if it doesnt exist.
                HttpSession session = req.getSession(false);
                RequestDispatcher dispatcher;
                if (session != null) {
                    if (session.getAttribute("uid") == null) {
                        if (req.getRequestURI().equals("login")) {
                            dispatcher = request.getRequestDispatcher("/login");
                        } else {
                            dispatcher = request.getRequestDispatcher("/login?nextUrl=" + req.getRequestURI());
                        }
                        dispatcher.forward(request, response);
                        return;
                    }
                } else {
                    request.setAttribute("flash_msg", "Session timed out!");
                    dispatcher = request.getRequestDispatcher("/login");
                    dispatcher.forward(request, response);
                    return;
                }
            }
            chain.doFilter(request, response);


        } catch (IOException | ServletException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Authenticator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    @Override
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }
}
