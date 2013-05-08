<%@page contentType="text/html" pageEncoding="UTF-8"%><%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"    "http://www.w3.org/TR/html4/loose.dtd"><html>    <head>        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        <title>Shopify - Online Store</title>        <%@include file="/pages/tool/common/include.jspf"%>    </head>    <body class="ui-widget">        <form id="home" action="${BASE_URL}/Home" method="POST" name="home">            <table>                <tr>                    <td>                        <!-- Header                    -->                        <%@include file="/pages/tool/common/logo.jspf" %>                    </td>                </tr>                <tr>                    <td>                        <!-- Main                    -->                        <table class="page-content-setting">                            <tr>                                <td valign="top" align="center">                                    <br/><br/><br/><br/>                                    <img src="${BASE_URL}/pages/resources/images/error.gif" width="100" height="100"/>                                    <br/><br/><br/><br/>                                    <c:choose>                                        <c:when test="${not empty error}">                                            ${error}                                        </c:when>                                        <c:otherwise>                                            Error on page. Please contact the administrator.                                        </c:otherwise>                                    </c:choose>                                </td>                            </tr>                        </table>                    </td>                </tr>                <tr>                    <td>                        <!-- Footer -->                        <%@include file="/pages/tool/common/footer.jspf" %>                    </td>                </tr>            </table>        </form>    </body></html>