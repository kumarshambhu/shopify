<%@page contentType="text/html" pageEncoding="UTF-8"%><%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"    "http://www.w3.org/TR/html4/loose.dtd"><html>    <head>        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        <title>Shopify - Online Store</title>        <%@include file="/pages/tool/common/include.jspf"%>    </head>    <body class="ui-widget">        <form id="home" action="${BASE_URL}/product" method="POST" enctype="multipart/form-data" >            <table>                <tr>                    <td>                        <!-- Header                    -->                        <%@include file="/pages/tool/common/header.jspf" %>                    </td>                </tr>                <tr>                    <td>                        <!-- Main                    -->                        <table class="page-content-setting">                            <tr>                                <td valign="top" align="center">                                    <table>                                        <tr>                                            <td align="center" colspan="2">                                                <br/>                                                <br/>                                            </td>                                        </tr>                                        <tr align="center" class="ui-widget-header">                                            <th colspan="2">                                                Add Product                                            </th>                                        </tr>                                        <tr>                                            <td align="center" colspan="2">                                                <br/>                                                <br/>                                            </td>                                        </tr>                                        <tr align="left" class="ui-widget-content">                                            <th width="15%" align="center">                                                <c:choose>                                                    <c:when test="${product.imageByteArry == null}">                                                        <img src="${BASE_URL}/pages/resources/images/no-image.jpg" width="100" height="100" />                                                    </c:when>                                                    <c:otherwise>                                                        <img src="${BASE_URL}/home?action=Image&id=${product.productId}" width="100" height="100" style="border-style: solid;border-color: whitesmoke"/>                                                    </c:otherwise>                                                </c:choose>                                             </th>                                            <td>                                                <input type="file" name="image" />                                             </td>                                        </tr>                                        <tr align="left" class="ui-widget-content">                                            <th width="15%" align="left">                                                Product Name<font color="red">*</font>                                            </th>                                            <td>                                                <input type="hidden" name="productId" value="${product.productId}" />                                                 <input type="text" name="name" size="45" value="${product.name}" />                                             </td>                                        </tr>                                        <tr align="left" class="ui-widget-content">                                            <th width="15%" align="left">                                                Product Code<font color="red">*</font>                                            </th>                                            <td>                                                <input type="text" name="code" size="45" value="${product.code}"/>                                             </td>                                        </tr>                                        <tr align="left" class="ui-widget-content">                                            <th width="15%" align="left">                                                Product Category<font color="red">*</font>                                            </th>                                            <td>                                                <select name="category">                                                    <c:choose>                                                        <c:when test="${product.category == 'Shirts'}">                                                            <option selected>Shirts</option>                                                        </c:when>                                                        <c:otherwise>                                                            <option>Shirts</option>                                                        </c:otherwise>                                                    </c:choose>                                                    <c:choose>                                                        <c:when test="${product.category == 'Shoes'}">                                                            <option selected>Shoes</option>                                                        </c:when>                                                        <c:otherwise>                                                            <option>Shoes</option>                                                        </c:otherwise>                                                    </c:choose>                                                    <c:choose>                                                        <c:when test="${product.category == 'Watches'}">                                                            <option selected>Watches</option>                                                        </c:when>                                                        <c:otherwise>                                                            <option>Watches</option>                                                        </c:otherwise>                                                    </c:choose>                                                    <c:choose>                                                        <c:when test="${product.category == 'Trousers'}">                                                            <option selected>Trousers</option>                                                        </c:when>                                                        <c:otherwise>                                                            <option>Trousers</option>                                                        </c:otherwise>                                                    </c:choose>                                                    <c:choose>                                                        <c:when test="${product.category == 'Kurhta'}">                                                            <option selected>Kurhta</option>                                                        </c:when>                                                        <c:otherwise>                                                            <option>Kurhta</option>                                                        </c:otherwise>                                                    </c:choose>                                                    <c:choose>                                                        <c:when test="${product.category == 'Bags'}">                                                            <option selected>Bags</option>                                                        </c:when>                                                        <c:otherwise>                                                            <option>Bags</option>                                                        </c:otherwise>                                                    </c:choose>                                                </select>                                            </td>                                        </tr>                                        <tr align="left" class="ui-widget-content">                                            <th width="15%" align="left">                                                Product Price<font color="red">*</font>                                            </th>                                            <td>                                                <input type="text" name="price" size="45" value="${product.price}"/>                                             </td>                                        </tr>                                        <tr align="left" class="ui-widget-content">                                            <th width="15%" align="left">                                                Product Description                                            </th>                                            <td>                                                <textarea name="description" cols="100" rows="10">${product.description}</textarea>                                            </td>                                        </tr>                                    </table>                                     <br/>                                     <c:choose>                                        <c:when test="${not empty product.productId}">                                            <input type="submit" name="action" id="updateProduct" value="Update Product" class="ui-button ui-widget ui-state-default ui-corner-all" />                                            <input type="submit" name="action" id="deleteProduct" value="Delete Product" class="ui-button ui-widget ui-state-default ui-corner-all" />                                        </c:when>                                        <c:otherwise>                                            <input type="submit" name="action" id="saveProduct" value="Save Product" class="ui-button ui-widget ui-state-default ui-corner-all" />                                        </c:otherwise>                                    </c:choose>                                    <input type="button" name="action" value="Cancel" class="ui-button ui-widget ui-state-default ui-corner-all" onclick="history.back()" />                                    <br/><br/>                                </td>                            </tr>                        </table>                    </td>                </tr>                <tr>                    <td>                        <!-- Footer -->                        <%@include file="/pages/tool/common/footer.jspf" %>                    </td>                </tr>            </table>        </form>    </body></html>