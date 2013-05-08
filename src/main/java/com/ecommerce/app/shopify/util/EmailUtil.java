package com.ecommerce.app.shopify.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public enum EmailUtil {

    INSTANCE;
    private static final Logger logger = Logger.getLogger(EmailUtil.class.getName());
    Properties properties = loadProperties(this.getClass());

    public static Properties loadProperties(Class cl) {
        Properties properties = new Properties();
        ClassLoader loader = cl.getClassLoader();
        try {
            InputStream in = loader.getResourceAsStream("shopify.properties");
            if (in != null) {
                properties.load(in);
            }
        } catch (IOException ex) {
            logger.severe(ex.getMessage());
        }
        return properties;
    }

    public String getProperty(String propName) {
        return properties.getProperty(propName);
    }

    public void sendMail(final String subject, final String body, final String toEmail, final String ccEmail, final String bccEmail) {
        Thread thread = new Thread("Notification Thread") {
            @Override
            public void run() {
                try {
                    logger.log(Level.INFO, "Sending email: ToEmail: {0}, ccEmail: {1}, bccEmail: {2}", new Object[]{toEmail, ccEmail, bccEmail});
                    Email email = new HtmlEmail();
                    email.setHostName(getProperty("EMAIL_SERVER"));
                    email.setSmtpPort(465);
                    email.setSSLOnConnect(true);
                    email.setAuthenticator(new DefaultAuthenticator(getProperty("EMAIL_USER"), getProperty("EMAIL_PWD")));
                    email.setFrom(getProperty("EMAIL_FROM"));
                    email.setSubject(subject);
                    email.setMsg(body);
                    if (toEmail != null) {
                        setRecipients(toEmail, email, Const.INSTANCE.TO_ADDR);
                    }
                    if (ccEmail != null) {
                        setRecipients(ccEmail, email, Const.INSTANCE.CC_ADDR);
                    }
                    if (bccEmail != null) {
                        setRecipients(bccEmail, email, Const.INSTANCE.BCC_ADDR);
                    }
                    email.send();
                } catch (EmailException ex) {
                    logger.severe(ex.getMessage());
                }
            }
        };
        thread.start();
    }

    private void setRecipients(String recipients, Email email, String addressType) throws EmailException {
        String recipientsArray[] = null;
        if (recipients != null) {
            recipientsArray = recipients.split(",");
        }
        String as[] = recipientsArray;
        for (int i = 0; i < as.length; i++) {
            String recipient = as[i];
            if (isValidEmailAddress(recipient)) {
                if (addressType.equals(Const.INSTANCE.TO_ADDR)) {
                    email.addTo(recipient);
                }
                if (addressType.equals(Const.INSTANCE.CC_ADDR)) {
                    email.addCc(recipient);
                }
                if (addressType.equals(Const.INSTANCE.BCC_ADDR)) {
                    email.addBcc(recipient);
                }
            }
        }
    }

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}
