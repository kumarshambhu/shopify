<%@page contentType="text/html" pageEncoding="UTF-8"%><%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"    "http://www.w3.org/TR/html4/loose.dtd"><html>    <head>        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        <title>Shopify - Online Store</title>        <%@include file="/pages/tool/common/include.jspf"%>        <script type="text/javascript" src="${BASE_URL}/pages/resources/js/custom/profile.js"></script>    </head>    <body class="ui-widget">        <form id="home" action="${BASE_URL}/home" method="POST">            <table>                <tr>                    <td>                        <!-- Header                    -->                        <%@include file="/pages/tool/common/header.jspf" %>                    </td>                </tr>                <tr>                    <td>                        <!-- Main                    -->                        <table class="page-content-setting">                            <tr>                                <td valign="top" align="center">                                    <table>                                        <tr>                                            <td align="center" colspan="2">                                                <br/>                                                <br/>                                            </td>                                        </tr>                                        <tr align="center" class="ui-widget-header">                                            <th colspan="2">                                                Sign Up                                            </th>                                        </tr>                                        <tr>                                            <td align="center" colspan="2">                                                <br/>                                                <br/>                                            </td>                                        </tr>                                        <tr align="left" class="ui-widget-content">                                            <th width="15%" align="left">                                                User Name<font color="red">*</font>                                            </th>                                            <td>                                                <input type="hidden" name="profileId" value="${profile.profileId}"/>                                                <input type="text" name="uname" value="${profile.uname}" size="25"/>                                             </td>                                        </tr>                                        <tr align="left" class="ui-widget-content">                                            <th width="15%" align="left">                                                Password<font color="red">*</font>                                            </th>                                            <td>                                                <input type="password" name="pwd" value="${profile.pwd}" size="25"/>                                             </td>                                        </tr>                                        <tr align="left" class="ui-widget-content">                                            <th width="15%" align="left">                                                Name<font color="red">*</font>                                            </th>                                            <td>                                                <input type="text" name="name" value="${profile.name}" size="45"/>                                             </td>                                        </tr>                                        <tr align="left" class="ui-widget-content">                                            <th width="15%" align="left">                                                Gender<font color="red">*</font>                                            </th>                                            <td>                                                <input type="radio" name="gender" value="M"/> Male <br/>                                                <input type="radio" name="gender" value="F"/> Female <br/>                                            </td>                                        </tr>                                        <tr align="left" class="ui-widget-content">                                            <th width="15%" align="left">                                                Email<font color="red">*</font>                                            </th>                                            <td>                                                <input type="text" id="email" name="email" value="" size="45"/>                                             </td>                                        </tr>                                        <tr align="left" class="ui-widget-content">                                            <th width="15%" align="left">                                                Address<font color="red">*</font>                                            </th>                                            <td>                                                <textarea name="address" cols="100" rows="10"></textarea>                                            </td>                                        </tr>                                        <tr align="left" class="ui-widget-content">                                            <th width="15%" align="left">                                                City<font color="red">*</font>                                            </th>                                            <td>                                                <select name="city">                                                    <option>Bangalore</option>                                                    <option>Mysore</option>                                                </select>                                            </td>                                        </tr>                                        <tr align="left" class="ui-widget-content">                                            <th width="15%" align="left">                                                State<font color="red">*</font>                                            </th>                                            <td>                                                <select name="state">                                                    <option>Karnataka</option>                                                </select>                                            </td>                                        </tr>                                        <tr align="left" class="ui-widget-content">                                            <th width="15%" align="left">                                                Country<font color="red">*</font>                                            </th>                                            <td>                                                <select name="country">                                                    <option>India</option>                                                </select>                                            </td>                                        </tr>                                        <tr align="left" class="ui-widget-content">                                            <th width="15%" align="left">                                                Pin Code<font color="red">*</font>                                            </th>                                            <td>                                                <input type="text" name="pincode" value="" size="25"/>                                             </td>                                        </tr>                                        <tr align="left" class="ui-widget-content">                                            <th width="15%" align="left">                                                Mobile<font color="red">*</font>                                            </th>                                            <td>                                                <input type="text" name="mobile" value="" size="25"/>                                             </td>                                        </tr>                                    </table>                                    <br/>                                     <c:choose>                                        <c:when test="${not empty profile.profileId}">                                            <input type="submit" name="action" id="updateProfile" value="Update Profile" class="ui-button ui-widget ui-state-default ui-corner-all" />                                        </c:when>                                        <c:otherwise>                                            <input type="submit" name="action" id="register" value="Register" class="ui-button ui-widget ui-state-default ui-corner-all" />                                        </c:otherwise>                                    </c:choose>                                    <input type="button" name="action" value="Cancel" class="ui-button ui-widget ui-state-default ui-corner-all" onclick="history.back()" />                                    <br/><br/>                                </td>                            </tr>                        </table>                    </td>                </tr>                <tr>                    <td>                        <!-- Footer -->                        <%@include file="/pages/tool/common/footer.jspf" %>                    </td>                </tr>            </table>        </form>    </body></html>