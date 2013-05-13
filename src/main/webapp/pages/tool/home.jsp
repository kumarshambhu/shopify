<%@page contentType="text/html" pageEncoding="UTF-8"%><%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"    "http://www.w3.org/TR/html4/loose.dtd"><html>    <head>        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        <title>Shopify - Online Store</title>        <%@include file="/pages/tool/common/include.jspf"%>        <script type="text/javascript" src="${BASE_URL}/pages/resources/js/custom/home.js"></script>    </head>    <body class="ui-widget">        <form id="home" action="${BASE_URL}/home" method="POST">            <table>                <tr>                    <td>                        <!-- Header                    -->                        <%@include file="/pages/tool/common/header.jspf" %>                    </td>                </tr>                <tr>                    <td>                        <!-- Main                    -->                        <table class="page-content-setting">                            <tr>                                <td valign="top" align="center">                                        <table>                                        <tr>                                            <td align="center" colspan="2">                                                <br/>                                                <br/>                                            </td>                                        </tr>                                        <tr>                                            <td align="center">                                                <img src="${BASE_URL}/pages/resources/images/shopify-home.png" height="200"/>                                            </td>                                            <td align="center" width="400px" valign="top">                                                <div id="shopping_cart">                                                    <%@include file="/pages/tool/cart.jsp"%>                                                </div>                                            </td>                                        </tr>                                        <tr>                                            <td align="center" colspan="2">                                                <br/>                                                <label style="color:red">${flash_msg}</label>                                                <br/>                                            </td>                                        </tr>                                        <tr>                                            <td align="left" colspan="2">                                                <table>                                                    <c:forEach var="product" items="${productLst}">                                                        <tr>                                                            <td >                                                                <table>                                                                    <tr>                                                                        <td width="15%">                                                                            <c:choose>                                                                                <c:when test="${product.imageByteArry == null}">                                                                                    <img src="${BASE_URL}/pages/resources/images/no-image.jpg" width="100" height="100" />                                                                                </c:when>                                                                                <c:otherwise>                                                                                    <img src="${BASE_URL}/home?action=Image&id=${product.productId}" width="100" height="100"/>                                                                                </c:otherwise>                                                                            </c:choose>                                                                         </td>                                                                        <td width="65%">                                                                            <h3>                                                                                <c:choose>                                                                                    <c:when test="${sessionScope.urole == 'ADMIN'}">                                                                                        <a href="${BASE_URL}/product?action=EditProduct&productId=${product.productId}">${product.code}: ${product.name}</a>                                                                                    </c:when>                                                                                    <c:otherwise>                                                                                        ${product.code}: ${product.name}                                                                                    </c:otherwise>                                                                                </c:choose>                                                                            </h3>                                                                            Price: ${product.price}                                                                            <br/>                                                                            Description: ${product.description}                                                                            <br/>                                                                        </td>                                                                        <td>                                                                            Quantity: <input id="qty_${product.productId}" type="text" size="5" value="0"/>                                                                            <input type="button" id="${product.productId}" value="Add to Cart" class="ui-button ui-widget ui-state-default ui-corner-all"/>                                                                        </td>                                                                    </tr>                                                                </table>                                                            </td>                                                        </tr>                                                    </c:forEach>                                                </table>                                            </td>                                        </tr>                                    </table>                                </td>                            </tr>                        </table>                    </td>                </tr>                <tr>                    <td>                        <!-- Footer -->                        <%@include file="/pages/tool/common/footer.jspf" %>                    </td>                </tr>            </table>        </form>    </body></html>