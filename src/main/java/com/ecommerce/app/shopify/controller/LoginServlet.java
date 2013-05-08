package com.ecommerce.app.shopify.controller;

import com.ecommerce.app.shopify.dao.DaoImpl;
import com.ecommerce.app.shopify.domain.Authenticate;
import com.ecommerce.app.shopify.domain.Profile;
import com.ecommerce.app.shopify.util.Const;
import com.ecommerce.app.shopify.util.EmailUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

@WebServlet(name = "Login", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(HomeServlet.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            String action = request.getParameter("action");

            if (action != null) {
                switch (action) {
                    case "Login":
                        login(request, response);
                        break;
                    case "Forgot Password":
                        forgotPwd(request, response);
                        break;
                    case "Captcha":
                        validateCaptcha(request, response);
                        break;
                    case "Verify":
                        verifyAccount(request, response);
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

    public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String ipAddress = request.getRemoteAddr();
        logger.log(Level.INFO, "Login Attempt from :{0}", ipAddress);
        String uname = request.getParameter("uname");
        String pwd = request.getParameter("pwd");
        Boolean accLocked = false;

        //Dont try to move this to authenticator.java
        if (DaoImpl.INSTANCE.checkBlackList(ipAddress)) {
            //You just encountered black list ip. So dont do anything else
            logger.log(Level.INFO, "Black Listed IP Found: {0}", ipAddress);
            request.setAttribute("error", "Your IP has been black listed, please try after sometime!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/tool/error.jsp");
            dispatcher.forward(request, response);
            return;
        }


        if (uname != null && pwd != null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                //Track rouge IP address from cauing havoc. 
                Map<String, Integer> ipTrackMap;
                if (session.getAttribute("ipTrackMap") != null) {
                    ipTrackMap = (Map) session.getAttribute("ipTrackMap");
                    if (ipTrackMap.containsKey(ipAddress)) {
                        ipTrackMap.put(ipAddress, (ipTrackMap.get(ipAddress)) + 1);
                        if (ipTrackMap.get(ipAddress) == Const.INSTANCE.IP_ATTEMPT_INTERVAL) {
                            //Add IP to blacklist.
                            DaoImpl.INSTANCE.addIpToBlackList(ipAddress);
                        }
                    } else {
                        ipTrackMap.put(ipAddress, 1);
                    }
                } else {
                    ipTrackMap = new HashMap();
                    ipTrackMap.put(ipAddress, 1);
                }
                session.setAttribute("ipTrackMap", ipTrackMap);

                //Need to track each user so that 6 attempts causes them to enter verification code.
                //Key value pair.
                Map<String, Integer> loginAttemptMap;
                if (session.getAttribute("loginAttemptMap") != null) {
                    loginAttemptMap = (Map) session.getAttribute("loginAttemptMap");
                    if (loginAttemptMap.containsKey(uname)) {
                        loginAttemptMap.put(uname, (loginAttemptMap.get(uname)) + 1);
                        if (loginAttemptMap.get(uname) == Const.INSTANCE.LOCK_ATTEMPT_INTERVAL) {
                            //Update the db and lock the account.
                            //Check if user exists.
                            DaoImpl.INSTANCE.lockAccount(uname);
                            accLocked = true;
                        }
                    } else {
                        loginAttemptMap.put(uname, 1);
                    }
                } else {
                    loginAttemptMap = new HashMap();
                    loginAttemptMap.put(uname, 1);
                }
                session.setAttribute("loginAttemptMap", loginAttemptMap);
            }

            //Authenticate the user with username and pwd combination.
            Authenticate auth = DaoImpl.INSTANCE.checkLogin(uname, pwd);
            if (auth != null) {

                if (auth.getAccLock() == Boolean.TRUE) {
                    //Show verification page.
                    String uuid = UUID.randomUUID().toString();
                    //Save this uuid into the DB as verification code.
                    DaoImpl.INSTANCE.saveVerficiationCode(auth.getProfileId(), uuid);
                    //Mail this verification code to the user.
                    Profile profile = DaoImpl.INSTANCE.getProfile(auth.getProfileId());
                    EmailUtil.INSTANCE.sendMail("Shopify: Verification Token", "Your one time token is: " + uuid, profile.getEmail(), null, null);
                    //Goto Verification code Page
                    request.setAttribute("profile_id", auth.getProfileId());
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/tool/verification.jsp");
                    dispatcher.forward(request, response);

                } else {
                    setSucessfulSession(session, auth.getProfileId());
                    defaultAction(request, response);
                }

            } else {
                if (accLocked == Boolean.TRUE) {
                    request.setAttribute("fail_msg", "Invalid login credentials! This account is locked!");
                } else {
                    request.setAttribute("fail_msg", "Invalid login credentials");
                }
                defaultAction(request, response);
            }
        } else {
            defaultAction(request, response);
        }



    }

    public void setSucessfulSession(HttpSession session, Long profileId) throws Exception {
        //All sucess, set the user id in session and default action will show home page.
        logger.log(Level.INFO, " User Logged In: {0} at {1}", new Object[]{profileId, new Date()});
        session.setAttribute("uid", profileId);
        Profile profile = DaoImpl.INSTANCE.getProfile(profileId);
        session.setAttribute("user", profile.getName());
        session.setAttribute("urole",profile.getUrole());

    }

    public void forgotPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("Forgot Pwd!");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/tool/home.jsp");
        dispatcher.forward(request, response);
    }

    public void validateCaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String remoteAddr = request.getRemoteAddr();
        logger.log(Level.INFO, "Validating Captcha for: {0}", remoteAddr);
        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
        reCaptcha.setPrivateKey(Const.INSTANCE.PRIVATE_CAPTCHA_KEY);
        //https://developers.google.com/recaptcha/docs/java
        String challenge = request.getParameter("recaptcha_challenge_field");
        String uresponse = request.getParameter("recaptcha_response_field");
        logger.log(Level.INFO, "remoteAddr: {0} ,challenge: {1} ,uresponse: {2}", new Object[]{remoteAddr, challenge, uresponse});
        ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);
        try (PrintWriter out = response.getWriter()) {
            out.print(reCaptchaResponse.isValid());
        }

    }

    public void verifyAccount(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //unlock
        String verificationCode = request.getParameter("verification_code");
        Long profileId = Long.parseLong(request.getParameter("profile_id").toString());
        if (DaoImpl.INSTANCE.unlockAccount(profileId, verificationCode) == Boolean.TRUE) {
            //Set session id and normal flow to indicate successfull login.
            HttpSession session = request.getSession(false);
            setSucessfulSession(session, profileId);
        }

        defaultAction(request, response);

    }

    public void defaultAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //Create a session if one doesnt exist.
        HttpSession session = request.getSession(true);
        String nextUrl = request.getParameter("nextUrl");

        //If authenticated goto
        if (session.getAttribute("uid") != null) {
            if (nextUrl == null) {
                RequestDispatcher dispatcher;
                dispatcher = request.getRequestDispatcher("/home");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect(nextUrl);
            }
        } else {
            Integer loginAttempt = 0;

            if (session.getAttribute("loginAttempt") != null) {
                loginAttempt = (Integer) session.getAttribute("loginAttempt");
                loginAttempt++;
            }
            session.setAttribute("loginAttempt", loginAttempt);
            session.setAttribute("CAPTACH_ATTEMPT_INTERVAL", Const.INSTANCE.CAPTACH_ATTEMPT_INTERVAL);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/tool/login.jsp");
            dispatcher.forward(request, response);
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
