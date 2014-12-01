<%@page import="com.liferay.portal.service.UserServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.model.User"%>
<%@page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../../init.jspf" %>
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %>
<%
        ThemeDisplay tDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        User currentUser = UserServiceUtil.getUserById(tDisplay.getUserId());
        String idCurrentUser = String.valueOf(currentUser.getUserId());
%>
<portlet:renderURL var="backUrl" >
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_GROUP_DOC%>" /> 
    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
</portlet:renderURL>
<portlet:renderURL var="backToCourseUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_COURSE%>" /> 
    <portlet:param name="<%= PARAM_ID_USER%>" value="<%= idCurrentUser%>" />
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
</portlet:renderURL>

<div class="admin-right"> 
    <span class="sideMape"><a href="${backToCourseUrl}"><spring:message code="label-course" /></a> > <c:out value="${shortcutC}" /> > <a href="${backUrl}"><spring:message code="mal-all-group-documents" /></a> > <spring:message code="label-remove-doc" /></span> 
    <h3><spring:message code="title-materials-for-study" /></h3> <br/>
    <liferay-ui:success key="SuccessKeyDeleted" message="lms-msg-dummy-deleted" />
    <h4><c:out value="${groupDocPto.name}" /></h4>
    <p style="text-align: justify;"><c:out value="${groupDocPto.description}" escapeXml="false" /></p><br/>
    <table id="tableDms">
        <c:set var="number" value="${1}" />
        <c:set var="size" value="${sizeItems}" /> 

        <spring:message code="title-remove" var="remove"/>
        <c:forEach items="${itemDocPto}" var="item" >
            <c:choose>
                <c:when test="${number%2 == 0}">
                    <portlet:actionURL name="<%= ACTION_DELETE_ITEM_DOC%>" var="deleteUrl">
                        <portlet:param name="<%= PARAM_ID_ITEM_DOC%>" value="${item.id}" />
                        <portlet:param name="<%= PARAM_ID_GROUP%>" value="${idGroup}" />
                        <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
                    </portlet:actionURL>
                    <portlet:actionURL name="<%= ACTION_DELETE_ITEM_DOC_FROM_REPO%>" var="deleteWithRepoUrl">
                        <portlet:param name="<%= PARAM_ID_ITEM_DOC%>" value="${item.id}" />
                        <portlet:param name="<%= PARAM_ID_GROUP%>" value="${idGroup}" />
                        <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
                    </portlet:actionURL> 
                    <td style="margin-left: 20px;padding-top: 10px;">
                        <c:choose>
                            <c:when test="${item.isLink == false}">
                                <a href="${item.link}" target="_blank"><img src="<%= request.getContextPath()%>${item.nameIcon}">&nbsp;<c:out value="${item.name}" /></a>&nbsp;&nbsp;<a href="${removeUrl}" onclick="if (confirm('<spring:message code="lms-msg-dele-question" arguments="${item.name}" htmlEscape="true" />')) {
                                            if (confirm('<spring:message code="alert-delete-from-repo" arguments="${item.name}" htmlEscape="true" />')) {
                                                window.location = '${deleteWithRepoUrl}';
                                            } else {
                                                window.location = '${deleteUrl}';
                                            }
                                        }
                                        return false;"><img src="<%= request.getContextPath()%>/images/no.png" title="${remove}"></a>
                                </c:when>
                                <c:otherwise>
                                <a href="${item.link}" target="_blank"><img src="<%= request.getContextPath()%>${item.nameIcon}">&nbsp;<c:out value="${item.name}" /></a>&nbsp;&nbsp;<a href="${removeUrl}" onclick="if (confirm('<spring:message code="lms-msg-dele-question" arguments="${item.name}" htmlEscape="true" />')) {
                                            window.location = '${deleteUrl}';
                                        }
                                        return false;"><img src="<%= request.getContextPath()%>/images/no.png" title="${remove}"></a>
                                </c:otherwise>
                            </c:choose>
                    </td></tr>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${size!=number}">
                            <portlet:actionURL name="<%= ACTION_DELETE_ITEM_DOC%>" var="deleteUrl">
                                <portlet:param name="<%= PARAM_ID_ITEM_DOC%>" value="${item.id}" />
                                <portlet:param name="<%= PARAM_ID_GROUP%>" value="${idGroup}" />
                                <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
                            </portlet:actionURL> 
                            <portlet:actionURL name="<%= ACTION_DELETE_ITEM_DOC_FROM_REPO%>" var="deleteWithRepoUrl">
                                <portlet:param name="<%= PARAM_ID_ITEM_DOC%>" value="${item.id}" />
                                <portlet:param name="<%= PARAM_ID_GROUP%>" value="${idGroup}" />
                                <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
                            </portlet:actionURL> 
                            <tr><td style="padding-top: 10px;">
                                    <c:choose>
                                        <c:when test="${item.isLink == false}">
                                            <a href="${item.link}" target="_blank"><img src="<%= request.getContextPath()%>${item.nameIcon}">&nbsp;<c:out value="${item.name}" /></a>&nbsp;&nbsp;<a href="${removeUrl}" onclick="if (confirm('<spring:message code="lms-msg-dele-question" arguments="${item.name}" htmlEscape="true" />')) {
                                                        if (confirm('<spring:message code="alert-delete-from-repo" arguments="${item.name}" htmlEscape="true" />')) {
                                                            window.location = '${deleteWithRepoUrl}';
                                                        } else {
                                                            window.location = '${deleteUrl}';
                                                        }
                                                    }
                                                    return false;"><img src="<%= request.getContextPath()%>/images/no.png" title="${remove}"></a>
                                            </c:when>
                                            <c:otherwise>
                                            <a href="${item.link}" target="_blank"><img src="<%= request.getContextPath()%>${item.nameIcon}">&nbsp;<c:out value="${item.name}" /></a>&nbsp;&nbsp;<a href="${removeUrl}" onclick="if (confirm('<spring:message code="lms-msg-dele-question" arguments="${item.name}" htmlEscape="true" />')) {
                                                        window.location = '${deleteUrl}';
                                                    }
                                                    return false;"><img src="<%= request.getContextPath()%>/images/no.png" title="${remove}"></a>
                                            </c:otherwise>
                                        </c:choose>
                                </td>
                            </c:when>
                            <c:when test="${size==number}">
                                <portlet:actionURL name="<%= ACTION_DELETE_ITEM_DOC%>" var="deleteUrl">
                                    <portlet:param name="<%= PARAM_ID_ITEM_DOC%>" value="${item.id}" />
                                    <portlet:param name="<%= PARAM_ID_GROUP%>" value="${idGroup}" />
                                    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
                                </portlet:actionURL> 
                                <portlet:actionURL name="<%= ACTION_DELETE_ITEM_DOC_FROM_REPO%>" var="deleteWithRepoUrl">
                                    <portlet:param name="<%= PARAM_ID_ITEM_DOC%>" value="${item.id}" />
                                    <portlet:param name="<%= PARAM_ID_GROUP%>" value="${idGroup}" />
                                    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
                                </portlet:actionURL> 
                            <tr><td style="padding-top: 10px;"><c:choose>
                                        <c:when test="${item.isLink == false}">
                                            <a href="${item.link}" target="_blank"><img src="<%= request.getContextPath()%>${item.nameIcon}">&nbsp;<c:out value="${item.name}" /></a>&nbsp;&nbsp;<a href="${removeUrl}" onclick="if (confirm('<spring:message code="lms-msg-dele-question" arguments="${item.name}" htmlEscape="true" />')) {
                                                if (confirm('<spring:message code="alert-delete-from-repo" arguments="${item.name}" htmlEscape="true" />')) {
                                                    window.location = '${deleteWithRepoUrl}';
                                                } else {
                                                    window.location = '${deleteUrl}';
                                                }
                                            }
                                            return false;"><img src="<%= request.getContextPath()%>/images/no.png" title="${remove}"></a>
                                            </c:when>
                                            <c:otherwise>
                                            <a href="${item.link}" target="_blank"><img src="<%= request.getContextPath()%>${item.nameIcon}">&nbsp;<c:out value="${item.name}" /></a>&nbsp;&nbsp;<a href="${removeUrl}" onclick="if (confirm('<spring:message code="lms-msg-dele-question" arguments="${item.name}" htmlEscape="true" />')) {
                                                            window.location = '${deleteUrl}';
                                                        }
                                                        return false;"><img src="<%= request.getContextPath()%>/images/no.png" title="${remove}"></a>
                                            </c:otherwise>
                                        </c:choose>
                                </td>
                                <td></td></tr>
                            </c:when>   
                        </c:choose>
                    </c:otherwise>
                </c:choose> 
                <c:set var="number" value="${number+1}" />
            </c:forEach>
    </table> 
    <br/><br/>
    <button class="btnl"  onclick="window.location = '${backUrl}';
            return false;"><spring:message code="common-btn-back" /></button>
</div>