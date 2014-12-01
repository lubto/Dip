<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@page import="com.liferay.portal.service.UserLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.User"%> 
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %> 
<%@include file="../../init.jspf" %>

<portlet:renderURL var="createUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_CREATE_FORM_COURSE%>" />
</portlet:renderURL>

<div class="admin-right">
    <liferay-ui:success key="SuccessKey" message="lms-msg-dummy-created" />
    <liferay-ui:success key="SuccessKeySaved" message="lms-msg-dummy-saved" /> 
    <liferay-ui:success key="SuccessKeyDeleted" message="lms-msg-dummy-deleted" />
      <liferay-ui:success key="KeyDeactivated" message="msg-deactivate" />  
    <liferay-ui:success key="KeyActivated" message="msg-activate" /> 

    <span class="sideMape"><spring:message code="label-course" /> > <spring:message code="label-view" /></span>  
    <h3><spring:message code="mal-all-course" /></h3>

    <liferay-ui:search-container emptyResultsMessage="lms-msg-no-items">
        <liferay-ui:search-container-results results="${allItems}" total="${fn:length(allItems)}" />

        <liferay-ui:search-container-row className="eu.lms.iface.dto.CourseDto" modelVar="item">
            <liferay-ui:search-container-column-text name="lable-name">
                <c:out value="${item.name}" />
            </liferay-ui:search-container-column-text>
            <liferay-ui:search-container-column-text name="lable-shortName">
                <c:out value="${item.shortName}" />
            </liferay-ui:search-container-column-text> 
            <liferay-ui:search-container-column-text name="lable-dateOfCreation">
                <c:out value="${item.dateOfCreation}" />
            </liferay-ui:search-container-column-text>
            <liferay-ui:search-container-column-text name="lable-category">
                <c:out value="${item.category.name}" />
            </liferay-ui:search-container-column-text>
            <liferay-ui:search-container-column-text name="lable-visible">
                <c:choose>
                    <c:when test="${item.visible == true}">
                        <spring:message code="lms-msg-visible" htmlEscape="true" />
                    </c:when>
                    <c:otherwise>
                        <spring:message code="lms-msg-unvisible" htmlEscape="true" />
                    </c:otherwise> 
                </c:choose>  
            </liferay-ui:search-container-column-text> 

            <%--<liferay-ui:search-container-column-text name="createrId">
                <c:out value="${item.createrId}" />
</liferay-ui:search-container-column-text> --%>
            <%--          <%
                       String userId=request.getRemoteUser();
                       User user= UserLocalServiceUtil.getUserById(Long.parseLong("${item.createrId}"));
                       String fullname = user.getFirstName() + " " + user.getLastName();
           %>
                       <liferay-ui:search-container-column-text name="category">
                           <c:out value="fullname" />
           </liferay-ui:search-container-column-text>--%>

            <liferay-ui:search-container-column-text name="lms-label-actions">
                <portlet:renderURL var="viewAllLessonUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_LESSON%>" />
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />
                </portlet:renderURL>
                <portlet:renderURL var="detailUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_DETAIL_COURSE%>" />
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />
                </portlet:renderURL>
                <portlet:renderURL var="editUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_EDIT_FORM_COURSE%>" />
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />
                </portlet:renderURL>
                <portlet:actionURL name="<%= ACTION_DELETE_COURSE%>" var="deleteUrl">
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />
                </portlet:actionURL>
                <portlet:renderURL var="deleteQuestionUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_DELETE_QUESTION_COURSE%>" />
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />
                </portlet:renderURL>
                 <portlet:renderURL var="addUserUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_VIEW_USERS_IN_COURSE%>" />
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />
                </portlet:renderURL> 
                <portlet:renderURL var="viewDocumentsUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_GROUP_DOC%>" />
                    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${item.id}" />
                </portlet:renderURL> 
                 <portlet:actionURL name="<%= ACTION_DE_ACTIVATE_COURSE%>" var="deactivateUrl"> 
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" /> 
                    <portlet:param name="<%= PARAM%>" value="1" />
                </portlet:actionURL>
                <portlet:actionURL name="<%= ACTION_DE_ACTIVATE_COURSE%>" var="activateUrl">  
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" /> 
                    <portlet:param name="<%= PARAM%>" value="0" />
                </portlet:actionURL>  
                
                <spring:message code="title-lesson" var="var1" />
                <spring:message code="mal-all-group-documents" var="var2" /> 
                <spring:message code="mal-deactivate-account-user" var="var3" />
                <spring:message code="mal-activate-account-user" var="var4" />
                <spring:message code="mal-add-user-to-course" var="var5" />
                
                <liferay-ui:icon-menu> 
                    <liferay-ui:icon image="add_user" url="${addUserUrl}" message="${var5}"/>
                    <liferay-ui:icon image="all_pages" url="${viewAllLessonUrl}" message="${var1}" />
                    <liferay-ui:icon image="clip" url="${viewDocumentsUrl}" message="${var2}" />
                    <liferay-ui:icon image="view" url="${detailUrl}" />
                    <liferay-ui:icon image="edit" url="${editUrl}" /> 
                     <c:choose>
                    <c:when test="${item.visible == true}">
                        <liferay-ui:icon image="activate" url="${activateUrl}" message="${var3}" /> 
                    </c:when>
                    <c:otherwise>
                        <liferay-ui:icon image="deactivate" url="${deactivateUrl}" message="${var4}" /> 
                    </c:otherwise> 
                </c:choose>  
                    <li class role="presentation" >
                        <a class="taglib-icon" href="${deleteQuestionUrl}" onclick="if (confirm('<spring:message code="lms-msg-dele-question" arguments="${item.name}" htmlEscape="true" />')) {
                                    window.location = '${deleteUrl}';
                                }
                                return false;">
                            <liferay-ui:icon image="delete" />
                        </a></li>
                    </liferay-ui:icon-menu>  
            </liferay-ui:search-container-column-text>
        </liferay-ui:search-container-row>

        <liferay-ui:search-iterator paginate="false" />
    </liferay-ui:search-container>
    <br/><br/>
    <div class="buttons">
        <button onclick="window.location = '${createUrl}';"><spring:message code="mal-btn-new-course" /></button>
    </div>
</div>