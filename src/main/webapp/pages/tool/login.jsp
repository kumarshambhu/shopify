<%@page contentType="text/html" pageEncoding="UTF-8"%><%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"    "http://www.w3.org/TR/html4/loose.dtd"><html>    <head>        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        <title>Shopify - Online Store</title>        <%@include file="/pages/tool/common/include.jspf"%>        <script type="text/javascript" src="${BASE_URL}/pages/resources/js/custom/login.js"></script>        <script type="text/javascript" src="http://www.google.com/recaptcha/api/js/recaptcha_ajax.js"></script>        <%@ page import="net.tanesha.recaptcha.ReCaptcha" %>        <%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>    </head>    <body class="ui-widget">        <form action="${BASE_URL}/login" method="POST">            <table>                <tr>                    <td>                        <!-- Header -->                        <%@include file="/pages/tool/common/logo.jspf" %>                    </td>                </tr>                <tr>                    <td>                        <!-- Main                    -->                        <table class="page-content-setting">                            <tr>                                <td valign="top" align="center">                                    <table valign="top" align="center">                                        <tr>                                            <td align="center" colspan="2">                                                <br/>                                                <br/>                                            </td>                                        </tr>                                        <tr>                                            <th align="center" colspan="2" class="ui-widget-header">                                                Login                                            </th>                                        </tr>                                        <tr>                                            <td align="center" colspan="2">                                                <br/>                                                <br/>                                            </td>                                        </tr>                                        <tr>                                            <th align="right" width="45%">                                                Username:                                            </th>                                            <td align="left">                                                <input type="text" name="uname" value="" class="ui-corner-all" size="25"/>                                            </td>                                        </tr>                                                                                <tr>                                            <th align="right">                                                Password:                                            </th>                                            <td align="left">                                                <input type="password" name="pwd" value="" class="ui-corner-all" size="25"/>                                                <input type="hidden" name="nextUrl" value="${nextUrl}"/>                                            </td>                                        </tr>                                        <tr>                                            <td colspan="2" align="center">                                                <br/>                                                <br/>                                                Attempt: <c:out value="${sessionScope.loginAttempt}"/>                                                <br/><br/>                                                <input type="hidden" id="loginAttempt" name="loginAttempt" value="${sessionScope.loginAttempt}"/>                                                <input type="hidden" id="captchaStatus" name="captchaStatus" value="false"/>                                                <c:if test="${sessionScope.loginAttempt >= CAPTACH_ATTEMPT_INTERVAL}">                                                    <%                                                        ReCaptcha c = ReCaptchaFactory.newReCaptcha("6Lfi4uASAAAAAIwjht475u4bJZwdjbplDjLEoVTn", "6Lfi4uASAAAAAJaUdRAoHwIWYP3Qnb0Fvx7WOXqZ", false);                                                        out.print(c.createRecaptchaHtml(null, null));                                                    %>                                                </c:if>                                            </td>                                        </tr>                                        <tr>                                            <td align="center" colspan="2">                                                <br/>                                                <br/>                                            </td>                                        </tr>                                        <tr>                                            <th colspan="2">                                                <input type="submit" name="action" id="login" value="Login" class="ui-button ui-widget ui-state-default ui-corner-all" title="Login to Shopify."/>                                                <input type="submit" name="action" id="forgotPwd" value="Forgot Password" class="ui-button ui-widget ui-state-default ui-corner-all" title="Email forgotten password to user."/>                                                <input type="submit" name="action" id="signUp" value="Sign Up" class="ui-button ui-widget ui-state-default ui-corner-all" title="Sign Up"/>                                                <br/>                                            </th>                                        </tr>                                        <tr>                                            <td align="center" colspan="2">                                                <br/>                                                <label style="color:red">${flash_msg}</label>                                                <br/>                                            </td>                                        </tr>                                        <tr>                                            <td align="center" colspan="2">                                                <br/>                                                <br/>                                            </td>                                        </tr>                                    </table>                                </td>                            </tr>                        </table>                    </td>                </tr>                <tr>                    <td>                        <!-- Footer -->                        <%@include file="/pages/tool/common/footer.jspf" %>                    </td>                </tr>            </table>        </form>    </body></html>